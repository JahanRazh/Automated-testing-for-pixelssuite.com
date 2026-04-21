import java.io.File;
import java.io.FileOutputStream;
import java.time.Duration;

// ── ExtentReports ─────────────────────────────────────────────
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

// ── Selenium ──────────────────────────────────────────────────
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// ── TestNG ────────────────────────────────────────────────────
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * ConvertImageSizeTest
 * ---------------------
 * TC-S01: Upload a 25 MB file (above the site's stated 20 MB limit)
 *         and assert that an error/size-limit message is displayed.
 *
 * The site (pixelssuite.com/convert-to-jpg) advertises a 20 MB max
 * file size. When a file larger than 20 MB is uploaded the site
 * SHOULD reject it with an error message.
 *
 * EXPECTED  → Error message visible                 → TEST PASSES
 * ACTUAL    → No error, file silently accepted      → TEST FAILS  ← BUG
 *
 * Report → test-reports/ConvertImageSizeTest.html
 */
public class ConvertImageSizeTest {

    WebDriver     driver;
    WebDriverWait wait;

    ExtentReports extent;
    ExtentTest    extentTest;

    /** File uploaded in the test – larger than the site's 20 MB cap */
    private static final int  FILE_SIZE_MB    = 25;
    private static final long FILE_SIZE_BYTES = FILE_SIZE_MB * 1024L * 1024L;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-notifications");

        driver = new ChromeDriver(options);
        wait   = new WebDriverWait(driver, Duration.ofSeconds(20));

        // ── HTML report ──────────────────────────────────
        new File("test-reports").mkdirs();
        ExtentSparkReporter spark = new ExtentSparkReporter(
                "test-reports/ConvertImageSizeTest.html");
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("Convert Image Size Limit – Test Report");
        spark.config().setReportName(
                "DEF: 25 MB upload should be rejected (limit is 20 MB)");

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Page",         "https://www.pixelssuite.com/convert-to-jpg");
        extent.setSystemInfo("Stated limit", "20 MB");
        extent.setSystemInfo("File used",    FILE_SIZE_MB + " MB (above limit)");
        extent.setSystemInfo("Browser",      "Chrome (visible)");
    }

    // =========================================================
    //  TC-S01 – Upload 25 MB file; EXPECT a size-limit error
    //           FAIL if the site silently accepts the file  ← BUG
    // =========================================================
    @Test(description = "TC-S01: Site must reject a 25 MB file (stated limit is 20 MB)")
    public void testLargeFileIsRejected() throws Exception {

        extentTest = extent.createTest(
                "TC-S01: Site must reject a " + FILE_SIZE_MB +
                " MB file – stated limit is 20 MB");

        extentTest.log(Status.INFO,
                "Site advertises a <b>20 MB</b> maximum file size.");
        extentTest.log(Status.INFO,
                "Uploading a <b>" + FILE_SIZE_MB + " MB</b> file – site MUST show an error.");

        // ── Step 1: Create a 25 MB dummy PNG file locally ──
        File largeFile = File.createTempFile("test_25mb_", ".png");
        largeFile.deleteOnExit();
        try (FileOutputStream fos = new FileOutputStream(largeFile)) {
            byte[] pngHeader = {(byte)0x89,0x50,0x4E,0x47,0x0D,0x0A,0x1A,0x0A};
            fos.write(pngHeader);
            byte[] block     = new byte[4096];
            long   remaining = FILE_SIZE_BYTES - pngHeader.length;
            while (remaining > 0) {
                int chunk = (int) Math.min(block.length, remaining);
                fos.write(block, 0, chunk);
                remaining -= chunk;
            }
        }
        System.out.printf("[TC-S01] File created: %s | %.2f MB%n",
                largeFile.getName(), largeFile.length() / (1024.0 * 1024.0));
        extentTest.log(Status.INFO,
                "Test file created: " + largeFile.getName() +
                " (" + String.format("%.2f", largeFile.length() / (1024.0*1024.0)) + " MB)");

        // ── Step 2: Open the convert page ──────────────────
        driver.get("https://www.pixelssuite.com/convert-to-jpg");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        Thread.sleep(1500);
        extentTest.log(Status.INFO, "Opened: https://www.pixelssuite.com/convert-to-jpg");

        // ── Step 3: Upload the 25 MB file ──────────────────
        WebElement fileInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("input[type='file']")));
        fileInput.sendKeys(largeFile.getAbsolutePath());
        extentTest.log(Status.INFO, "25 MB file sent to file input – waiting for site reaction...");
        Thread.sleep(3000); // give the site time to react

        // ── Step 4: Check if an error / rejection message exists ──
        String pageText = driver.getPageSource().toLowerCase();

        boolean errorDisplayed = pageText.contains("too large")
                              || pageText.contains("exceeds")
                              || pageText.contains("max size")
                              || pageText.contains("maximum")
                              || pageText.contains("file size")
                              || pageText.contains("limit")
                              || pageText.contains("only")
                              || pageText.contains("error");

        System.out.println("[TC-S01] Error/rejection message visible: " + errorDisplayed);
        extentTest.log(Status.INFO,
                "Error / size-limit message visible on page: <b>" + errorDisplayed + "</b>");

        // ── Step 5: Assert – if NO error shown, the test FAILS (BUG) ──
        if (!errorDisplayed) {
            extentTest.log(Status.FAIL,
                    "BUG DETECTED: Site accepted a " + FILE_SIZE_MB +
                    " MB file WITHOUT showing any error. " +
                    "The stated 20 MB limit is NOT enforced.");
            Assert.fail(
                "BUG: pixelssuite.com/convert-to-jpg accepted a " + FILE_SIZE_MB +
                " MB file without displaying a size-limit error. " +
                "The site states the limit is 20 MB.");
        } else {
            extentTest.log(Status.PASS,
                    "TC-S01 PASSED – Site correctly rejected the " +
                    FILE_SIZE_MB + " MB file with an error message.");
            System.out.println("TC-S01 PASSED");
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
        if (extent != null) {
            extent.flush();
            System.out.println("[Report] test-reports/ConvertImageSizeTest.html saved");
        }
    }
}

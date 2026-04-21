import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CompressFunctionalityTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1280,800");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test(description = "TC-A11 Verify image compression functionality starts")
    public void testCompressButtonFunction() {
        driver.get("https://www.pixelssuite.com/compress-image");

        WebElement upload = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='file']")));

        try {
            java.io.File tempFile = java.io.File.createTempFile("sample_compress", ".png");
            tempFile.deleteOnExit();
            byte[] basePng = new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A, 0x00, 0x00, 0x00, 0x0D,
                    0x49, 0x48, 0x44, 0x52, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01, 0x08, 0x06, 0x00, 0x00,
                    0x00, 0x1F, 0x15, (byte) 0xC4, (byte) 0x89, 0x00, 0x00, 0x00, 0x0A, 0x49, 0x44, 0x41, 0x54, 0x78,
                    (byte) 0x9C, 0x63, 0x00, 0x01, 0x00, 0x00, 0x05, 0x00, 0x01, 0x0D, 0x0A, 0x2D, (byte) 0xB4, 0x00,
                    0x00, 0x00, 0x00, 0x49, 0x45, 0x4E, 0x44, (byte) 0xAE, 0x42, 0x60, (byte) 0x82};
            java.nio.file.Files.write(tempFile.toPath(), basePng);
            upload.sendKeys(tempFile.getAbsolutePath());
            System.out.println("Uploaded valid 1x1 PNG file.");
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        try {
            WebElement compressBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'compress')]")));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", compressBtn);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", compressBtn);
        } catch (Exception ex) {
            System.out.println("Failed to click compress button. Dumping available buttons:");
            List<WebElement> buttons = driver.findElements(By.tagName("button"));
            for (WebElement b : buttons) {
                System.out.println("BTN -> " + b.getText() + " | visible: " + b.isDisplayed());
            }
            throw ex;
        }

        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(.,'Download')]")),
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(.,'Download')]")),
                ExpectedConditions.urlContains("download")
        ));

        Assert.assertTrue(true);
        System.out.println("TC-A11 PASSED");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Automatically opens TestNG HTML report after mvn test
     */
    @AfterSuite
    public void openHtmlReport() {
        // Maven + Surefire + TestNG → Report location
        String reportPath = "target/surefire-reports/index.html";     // Most detailed report
        // String reportPath = "target/surefire-reports/emailable-report.html";  // Summary version

        File reportFile = new File(reportPath);

        if (reportFile.exists()) {
            try {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(reportFile.toURI());
                    System.out.println("✅ TestNG HTML Report opened automatically!");
                    System.out.println("Report Path: " + reportFile.getAbsolutePath());
                } else {
                    System.out.println("✅ Report generated successfully.");
                    System.out.println("Open manually: " + reportFile.getAbsolutePath());
                }
            } catch (IOException e) {
                System.err.println("❌ Could not open report automatically: " + e.getMessage());
                System.out.println("Report location: " + reportFile.getAbsolutePath());
            }
        } else {
            System.out.println("⚠️ Report file not found: " + reportFile.getAbsolutePath());
            System.out.println("Try running: mvn clean test");
        }
    }
}
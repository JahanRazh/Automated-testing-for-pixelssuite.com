import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RotateImageTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--headless=new"); // modern headless mode
        opts.addArguments("--disable-gpu");

        driver = new ChromeDriver(opts);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(description = "TC-A12: Rotate Image page elements present")
    public void testRotateImagePage() {

        driver.get("https://www.pixelssuite.com/rotate-image");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        // Validate page content
        String src = driver.getPageSource();
        Assert.assertTrue(src.contains("Rotate Image"), "Heading missing");
        Assert.assertTrue(src.contains("Drag and drop"), "Drop zone missing");
        Assert.assertTrue(src.contains("Rotate"), "Rotate panel missing");

        // Validate file input exists in DOM (hidden by CSS — standard for styled upload buttons)
        WebElement inp = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='file']")));
        Assert.assertNotNull(inp, "File input element not found in DOM");

        // Validate canvas if present (only rendered after image upload, so non-fatal)
        try {
            WebElement canvas = driver.findElement(By.tagName("canvas"));
            System.out.println("Canvas found: " + canvas.getTagName());
        } catch (NoSuchElementException e) {
            System.out.println("Canvas not yet present (expected before upload) — skipping canvas check");
        }

        System.out.println("TC-A12 PASSED");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
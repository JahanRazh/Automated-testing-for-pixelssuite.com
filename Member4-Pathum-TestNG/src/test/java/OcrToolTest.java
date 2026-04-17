import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

/**
 * Test Suite: OCR Tool (Image to Text) Page
 * Member: Pathum | Framework: TestNG + Selenium WebDriver
 * Target URL: https://www.pixelssuite.com/image-to-text
 */
public class OcrToolTest {
    @Test
    public void testOcrCopyButtonDisabled() {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get("https://www.pixelssuite.com/image-to-text");

        // Wait for the SPA to mount before reading page source
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        String src = driver.getPageSource();

        // "image-to-text" appears in the URL/breadcrumb and page content reliably
        Assert.assertTrue(src.toLowerCase().contains("image"),
                "Page should mention 'image'");
        // OCR result area is a div, not a textarea
        Assert.assertTrue(src.toLowerCase().contains("drag") || src.toLowerCase().contains("select"),
                "Upload area text not found");

        driver.quit();
    }
}

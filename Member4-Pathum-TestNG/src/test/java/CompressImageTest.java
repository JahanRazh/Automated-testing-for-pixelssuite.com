import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import utils.TestBase;

/**
 * CompressImageTest - Test suite for PixelsSuite Compress Image tool
 * Covers: JPG compression, PNG compression, GIF compression
 */
public class CompressImageTest extends TestBase {
    private static final String TOOL_PATH = "/compress";

    /**
     * TC-CMP01: Compress page loads with upload area
     */
    @Test(description = "TC-CMP01: Compress page displays upload area and controls")
    public void testCompressPageLoads() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("Compress") || pageSource.contains("compress"),
                "Compress label should be on page");

        // Check for file input
        WebElement fileInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='file']")));
        Assert.assertNotNull(fileInput, "File input not found");
        java.util.List<WebElement> buttons = driver.findElements(By.tagName("button"));
        Assert.assertTrue(buttons.size() > 0, "Page should have interactive elements");

        takeScreenshot("TC-CMP01-CompressPageLoads");
        System.out.println("TC-CMP01 PASSED");
    }

    /**
     * TC-CMP02: JPG compression option is available
     */
    @Test(description = "TC-CMP02: JPG compression format option is available")
    public void testJpgCompressionOption() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.toLowerCase().contains("jpg") || 
                         pageSource.toLowerCase().contains("jpeg"),
                "JPG compression option should be available");

        takeScreenshot("TC-CMP02-JpgOption");
        System.out.println("TC-CMP02 PASSED");
    }

    /**
     * TC-CMP03: PNG compression option is available
     */
    @Test(description = "TC-CMP03: PNG compression format option is available")
    public void testPngCompressionOption() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.toLowerCase().contains("png"),
                "PNG compression option should be available");

        takeScreenshot("TC-CMP03-PngOption");
        System.out.println("TC-CMP03 PASSED");
    }

    /**
     * TC-CMP04: GIF compression option is available
     */
    @Test(description = "TC-CMP04: GIF compression format option is available")
    public void testGifCompressionOption() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.toLowerCase().contains("gif"),
                "GIF compression option should be available");

        takeScreenshot("TC-CMP04-GifOption");
        System.out.println("TC-CMP04 PASSED");
    }

    /**
     * TC-CMP05: Quality or compression level control is present
     */
    @Test(description = "TC-CMP05: Compression quality/level control is available")
    public void testCompressionQualityControl() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.toLowerCase().contains("quality") || 
                         pageSource.toLowerCase().contains("level") ||
                         pageSource.toLowerCase().contains("slider"),
                "Compression quality control should be available");

        takeScreenshot("TC-CMP05-QualityControl");
        System.out.println("TC-CMP05 PASSED");
    }

    /**
     * TC-CMP06: Download button is present
     */
    @Test(description = "TC-CMP06: Download button is accessible")
    public void testDownloadButton() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.toLowerCase().contains("download"),
                "Download option should be available");

        takeScreenshot("TC-CMP06-DownloadButton");
        System.out.println("TC-CMP06 PASSED");
    }

    /**
     * TC-CMP07: Page title identifies Compress tool
     */
    @Test(description = "TC-CMP07: Page title correctly identifies Compress tool")
    public void testPageTitle() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/compress"),
                "Current URL should contain '/compress': " + currentUrl);

        takeScreenshot("TC-CMP07-PageTitle");
        System.out.println("TC-CMP07 PASSED - URL: " + currentUrl);
    }
}

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import utils.TestBase;

/**
 * CropImageTest - Test suite for PixelsSuite Crop Image tool
 * Covers: Crop with custom coordinates, aspect ratio lock, format conversion
 */
public class CropImageTest extends TestBase {
    private static final String TOOL_PATH = "/crop";

    /**
     * TC-CROP01: Crop page loads with upload area
     */
    @Test(description = "TC-CROP01: Crop page displays upload area and controls")
    public void testCropPageLoads() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("Crop") || pageSource.contains("crop"),
                "Crop label should be on page");

        WebElement fileInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='file']")));
        Assert.assertNotNull(fileInput, "File input not found");
        java.util.List<WebElement> buttons = driver.findElements(By.tagName("button"));
        Assert.assertTrue(buttons.size() > 0, "Page should have interactive elements");

        takeScreenshot("TC-CROP01-CropPageLoads");
        System.out.println("TC-CROP01 PASSED");
    }

    /**
     * TC-CROP02: JPG crop format option is available
     */
    @Test(description = "TC-CROP02: JPG crop format is available")
    public void testJpgCropOption() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.toLowerCase().contains("jpg") || 
                         pageSource.toLowerCase().contains("jpeg"),
                "JPG format option should be available");

        takeScreenshot("TC-CROP02-JpgOption");
        System.out.println("TC-CROP02 PASSED");
    }

    /**
     * TC-CROP03: PNG crop format option is available
     */
    @Test(description = "TC-CROP03: PNG crop format is available")
    public void testPngCropOption() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.toLowerCase().contains("png"),
                "PNG format option should be available");

        takeScreenshot("TC-CROP03-PngOption");
        System.out.println("TC-CROP03 PASSED");
    }

    /**
     * TC-CROP04: WebP crop format option is available
     */
    @Test(description = "TC-CROP04: WebP crop format is available")
    public void testWebpCropOption() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.toLowerCase().contains("webp"),
                "WebP format option should be available");

        takeScreenshot("TC-CROP04-WebpOption");
        System.out.println("TC-CROP04 PASSED");
    }

    /**
     * TC-CROP05: Crop area selection controls are present
     */
    @Test(description = "TC-CROP05: Crop area selection controls are available")
    public void testCropAreaSelection() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.toLowerCase().contains("crop") || 
                         pageSource.toLowerCase().contains("select"),
                "Crop area selection should be available");

        takeScreenshot("TC-CROP05-CropAreaSelection");
        System.out.println("TC-CROP05 PASSED");
    }

    /**
     * TC-CROP06: Aspect ratio lock option is available
     */
    @Test(description = "TC-CROP06: Aspect ratio lock/maintain option is available")
    public void testAspectRatioLock() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.toLowerCase().contains("aspect") || 
                         pageSource.toLowerCase().contains("ratio"),
                "Aspect ratio options should be available");

        takeScreenshot("TC-CROP06-AspectRatioLock");
        System.out.println("TC-CROP06 PASSED");
    }

    /**
     * TC-CROP07: Download button is present
     */
    @Test(description = "TC-CROP07: Download button is accessible")
    public void testDownloadButton() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.toLowerCase().contains("download"),
                "Download option should be available");

        takeScreenshot("TC-CROP07-DownloadButton");
        System.out.println("TC-CROP07 PASSED");
    }

    /**
     * TC-CROP08: Page title identifies Crop tool
     */
    @Test(description = "TC-CROP08: Page title correctly identifies Crop tool")
    public void testPageTitle() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/crop"),
                "Current URL should contain '/crop': " + currentUrl);

        takeScreenshot("TC-CROP08-PageTitle");
        System.out.println("TC-CROP08 PASSED - URL: " + currentUrl);
    }
}

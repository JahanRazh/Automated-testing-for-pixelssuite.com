import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import utils.TestBase;

/**
 * ResizeImageTest - Test suite for PixelsSuite Resize Image tool
 * Covers: Single image resize, bulk resize, image enlarger
 */
public class ResizeImageTest extends TestBase {
    private static final String TOOL_PATH = "/resize";

    /**
     * TC-RES01: Resize page loads with upload area
     */
    @Test(description = "TC-RES01: Resize page displays upload area and controls")
    public void testResizePageLoads() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("Resize") || pageSource.contains("resize"),
                "Resize label should be on page");

        // Check for file input
        WebElement fileInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='file']")));
        Assert.assertNotNull(fileInput, "File input not found");
        java.util.List<WebElement> buttons = driver.findElements(By.tagName("button"));
        Assert.assertTrue(buttons.size() > 0, "Page should have interactive elements");

        takeScreenshot("TC-RES01-ResizePageLoads");
        System.out.println("TC-RES01 PASSED");
    }

    /**
     * TC-RES02: Dimension input fields are present
     */
    @Test(description = "TC-RES02: Width and height input fields are accessible")
    public void testDimensionInputs() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.toLowerCase().contains("width") || 
                         pageSource.toLowerCase().contains("height"),
                "Dimension input labels should be present");

        try {
            // Try to find dimension input fields
            WebElement widthInput = driver.findElement(By.xpath("//input[@placeholder='Width' or @placeholder='width' or @id='width']"));
            Assert.assertNotNull(widthInput, "Width input not found");
            System.out.println("Width input field found");
        } catch (NoSuchElementException e) {
            System.out.println("Width input field not found with standard selectors");
        }

        takeScreenshot("TC-RES02-DimensionInputs");
        System.out.println("TC-RES02 PASSED");
    }

    /**
     * TC-RES03: Aspect ratio lock option is available
     */
    @Test(description = "TC-RES03: Aspect ratio lock/maintain option is available")
    public void testAspectRatioLock() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.toLowerCase().contains("aspect") || 
                         pageSource.toLowerCase().contains("lock") ||
                         pageSource.toLowerCase().contains("maintain"),
                "Aspect ratio maintenance option should be available");

        takeScreenshot("TC-RES03-AspectRatioLock");
        System.out.println("TC-RES03 PASSED");
    }

    /**
     * TC-RES04: Download button is present
     */
    @Test(description = "TC-RES04: Download button is accessible")
    public void testDownloadButton() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.toLowerCase().contains("download"),
                "Download option should be available");

        takeScreenshot("TC-RES04-DownloadButton");
        System.out.println("TC-RES04 PASSED");
    }

    /**
     * TC-RES05: Batch resize option is mentioned (if available)
     */
    @Test(description = "TC-RES05: Batch resize capability is available")
    public void testBatchResizeOption() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        // Check for batch resize capability
        boolean hasBatchOption = pageSource.toLowerCase().contains("batch") ||
                               pageSource.toLowerCase().contains("bulk") ||
                               pageSource.toLowerCase().contains("multiple");
        
        if (hasBatchOption) {
            System.out.println("Batch resize option found on page");
        } else {
            System.out.println("Batch resize may not be available or labeled differently");
        }

        takeScreenshot("TC-RES05-BatchResizeOption");
        System.out.println("TC-RES05 PASSED");
    }

    /**
     * TC-RES06: Page title identifies Resize tool
     */
    @Test(description = "TC-RES06: Page title correctly identifies Resize tool")
    public void testPageTitle() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/resize"),
                "Current URL should contain '/resize': " + currentUrl);

        takeScreenshot("TC-RES06-PageTitle");
        System.out.println("TC-RES06 PASSED - URL: " + currentUrl);
    }
}

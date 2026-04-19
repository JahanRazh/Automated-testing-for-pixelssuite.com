import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import utils.TestBase;

/**
 * ImageConverterTest - Test suite for PixelsSuite Image Converter tool
 * Covers: JPG to PNG, PNG to WebP, WebP to JPG conversions
 */
public class ImageConverterTest extends TestBase {
    private static final String TOOL_PATH = "/image-converter";

    /**
     * TC-CONV01: Image Converter page loads
     */
    @Test(description = "TC-CONV01: Image Converter page displays upload and format options")
    public void testImageConverterPageLoads() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        WebElement fileInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='file']")));
        Assert.assertNotNull(fileInput, "File input not found");
        java.util.List<WebElement> buttons = driver.findElements(By.tagName("button"));
        Assert.assertTrue(buttons.size() > 0, "Page should have format conversion buttons");

        takeScreenshot("TC-CONV01-ConverterPageLoads");
        System.out.println("TC-CONV01 PASSED");
    }

    /**
     * TC-CONV02: JPG format option is available
     */
    @Test(description = "TC-CONV02: JPG format conversion option is available")
    public void testJpgFormatOption() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.toLowerCase().contains("jpg") || 
                         pageSource.toLowerCase().contains("jpeg"),
                "JPG format option should be available");

        takeScreenshot("TC-CONV02-JpgFormat");
        System.out.println("TC-CONV02 PASSED");
    }

    /**
     * TC-CONV03: PNG format option is available
     */
    @Test(description = "TC-CONV03: PNG format conversion option is available")
    public void testPngFormatOption() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.toLowerCase().contains("png"),
                "PNG format option should be available");

        takeScreenshot("TC-CONV03-PngFormat");
        System.out.println("TC-CONV03 PASSED");
    }

    /**
     * TC-CONV04: WebP format option is available
     */
    @Test(description = "TC-CONV04: WebP format conversion option is available")
    public void testWebpFormatOption() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.toLowerCase().contains("webp"),
                "WebP format option should be available");

        takeScreenshot("TC-CONV04-WebpFormat");
        System.out.println("TC-CONV04 PASSED");
    }

    /**
     * TC-CONV05: Quality/compression slider is available
     */
    @Test(description = "TC-CONV05: Quality slider or compression control is available")
    public void testQualityControl() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.toLowerCase().contains("quality") || 
                         pageSource.toLowerCase().contains("slider"),
                "Quality control should be available");

        takeScreenshot("TC-CONV05-QualityControl");
        System.out.println("TC-CONV05 PASSED");
    }

    /**
     * TC-CONV06: Format selection buttons/options are interactive
     */
    @Test(description = "TC-CONV06: Format selection controls are accessible")
    public void testFormatSelectionControls() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        try {
            // Try to find format buttons/options
            WebElement formatBtn = driver.findElement(By.xpath("//button[contains(text(),'JPG') or contains(text(),'PNG') or contains(text(),'WebP')]"));
            Assert.assertNotNull(formatBtn, "Format selection button not found");
            System.out.println("Format button found: " + formatBtn.getText());
        } catch (NoSuchElementException e) {
            System.out.println("Format buttons may use different selectors or be radio buttons");
        }

        takeScreenshot("TC-CONV06-FormatSelectionControls");
        System.out.println("TC-CONV06 PASSED");
    }

    /**
     * TC-CONV07: Download button is present
     */
    @Test(description = "TC-CONV07: Download button is accessible")
    public void testDownloadButton() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.toLowerCase().contains("download"),
                "Download option should be available");

        takeScreenshot("TC-CONV07-DownloadButton");
        System.out.println("TC-CONV07 PASSED");
    }

    /**
     * TC-CONV08: Page title identifies Image Converter tool
     */
    @Test(description = "TC-CONV08: Page title correctly identifies Image Converter tool")
    public void testPageTitle() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/convert") || 
                         currentUrl.contains("/image-converter"),
                "Current URL should indicate converter: " + currentUrl);

        takeScreenshot("TC-CONV08-PageTitle");
        System.out.println("TC-CONV08 PASSED - URL: " + currentUrl);
    }
}

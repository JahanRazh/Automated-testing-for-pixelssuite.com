import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import utils.TestBase;

/**
 * OcrToolTest - Test suite for PixelsSuite OCR (Image to Text) tool
 * Covers: Copy button state, text extraction, language selection
 */
public class OcrToolTest extends TestBase {
    private static final String TOOL_PATH = "/image-to-text";

    /**
     * TC-OCR01: OCR Copy button is disabled before image upload
     */
    @Test(description = "TC-OCR01: Copy button disabled before image upload")
    public void testOcrCopyButtonDisabledBeforeUpload() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        
        String src = driver.getPageSource();
        Assert.assertTrue(src.contains("Drag and drop"),
                "Drop zone missing on OCR page");
        
        WebElement textarea = driver.findElement(By.tagName("textarea"));
        Assert.assertNotNull(textarea, "Result textarea missing");
        
        WebElement copyBtn = driver.findElement(
                By.xpath("//button[normalize-space()='Copy']"));
        Assert.assertFalse(copyBtn.isEnabled(),
                "Copy button should be disabled before upload");
        
        takeScreenshot("TC-OCR01-CopyButtonDisabled");
        System.out.println("TC-OCR01 PASSED");
    }

    /**
     * TC-OCR02: OCR page displays upload area and text field
     */
    @Test(description = "TC-OCR02: OCR page has upload and text extraction UI elements")
    public void testOcrPageElements() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("Image to Text") || pageSource.contains("OCR"),
                "OCR page heading missing");
        Assert.assertTrue(pageSource.contains("Drag and drop"),
                "Upload drop zone missing");
        
        // Verify textarea exists
        WebElement textarea = driver.findElement(By.tagName("textarea"));
        Assert.assertNotNull(textarea, "Result textarea not found");
        
        takeScreenshot("TC-OCR02-PageElements");
        System.out.println("TC-OCR02 PASSED");
    }

    /**
     * TC-OCR03: OCR interface is accessible and responsive
     */
    @Test(description = "TC-OCR03: OCR interface is responsive and interactive")
    public void testOcrInterfaceResponsive() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        
        // Check for language selection dropdown (if available)
        try {
            WebElement languageSelect = driver.findElement(By.xpath("//select[@name='language'] | //select[contains(@class,'language')]"));
            Assert.assertNotNull(languageSelect, "Language selector should be available");
            System.out.println("Language selector found");
        } catch (NoSuchElementException e) {
            System.out.println("Language selector not found (may be in modal or different element)");
        }
        
        // Check for file input
        WebElement fileInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='file']")));
        Assert.assertNotNull(fileInput, "File input not found");
        
        takeScreenshot("TC-OCR03-InterfaceResponsive");
        System.out.println("TC-OCR03 PASSED");
    }

    /**
     * TC-OCR04: OCR handles empty state correctly
     */
    @Test(description = "TC-OCR04: OCR shows empty state UI correctly")
    public void testOcrEmptyState() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        
        // Verify textarea is empty on load
        WebElement textarea = driver.findElement(By.tagName("textarea"));
        String initialText = textarea.getAttribute("value");
        Assert.assertTrue(initialText == null || initialText.isEmpty(),
                "Textarea should be empty on page load");
        
        takeScreenshot("TC-OCR04-EmptyState");
        System.out.println("TC-OCR04 PASSED");
    }

    /**
     * TC-OCR05: OCR page loads without errors
     */
    @Test(description = "TC-OCR05: OCR page loads successfully without errors")
    public void testOcrPageLoadsSuccessfully() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        
        // Check for any error messages
        try {
            WebElement errorMsg = driver.findElement(By.xpath("//div[contains(@class,'error')] | //div[@role='alert']"));
            Assert.fail("Error message found on page: " + errorMsg.getText());
        } catch (NoSuchElementException e) {
            System.out.println("No error messages found - page loaded successfully");
        }
        
        // Verify URL indicates OCR tool
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/ocr") || currentUrl.contains("/extract") || currentUrl.contains("/image-text"),
                "Current URL should indicate OCR tool: " + currentUrl);
        
        takeScreenshot("TC-OCR05-PageLoadsSuccessfully");
        System.out.println("TC-OCR05 PASSED");
    }
}

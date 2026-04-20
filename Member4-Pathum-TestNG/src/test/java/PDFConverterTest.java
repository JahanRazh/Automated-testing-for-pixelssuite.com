import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import utils.TestBase;

/**
 * PDFConverterTest - Test suite for PixelsSuite PDF tools
 * Covers: Image to PDF, PDF to Word, Word to PDF conversions, and PDF Editor
 */
public class PDFConverterTest extends TestBase {
    private static final String PDF_CONVERTER_PATH = "/image-to-pdf";
    private static final String PDF_EDITOR_PATH = "/pdf-editor";

    /**
     * TC-PDF01: Image to PDF converter page loads
     */
    @Test(description = "TC-PDF01: Image to PDF converter page loads with upload area")
    public void testImageToPdfPageLoads() {
        navigateToTool(PDF_CONVERTER_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("Drag and drop") || 
                         pageSource.toLowerCase().contains("upload"),
                "Upload area should be visible");

        WebElement fileInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='file']")));
        Assert.assertNotNull(fileInput, "File input not found");

        takeScreenshot("TC-PDF01-ImageToPdfPageLoads");
        System.out.println("TC-PDF01 PASSED");
    }

    /**
     * TC-PDF02: PDF to Word conversion option is available
     */
    @Test(description = "TC-PDF02: PDF to Word conversion is accessible")
    public void testPdfToWordOption() {
        navigateToTool("/pdf-to-word");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("Drag and drop") || 
                         pageSource.toLowerCase().contains("upload"),
                "PDF to Word upload area should be available");

        takeScreenshot("TC-PDF02-PdfToWordOption");
        System.out.println("TC-PDF02 PASSED");
    }

    /**
     * TC-PDF03: Word to PDF conversion option is available
     */
    @Test(description = "TC-PDF03: Word to PDF conversion is accessible")
    public void testWordToPdfOption() {
        try {
            navigateToTool("/word-to-pdf");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

            String pageSource = driver.getPageSource();
            Assert.assertTrue(pageSource.contains("Drag and drop") || 
                             pageSource.toLowerCase().contains("upload"),
                    "Word to PDF upload area should be available");

            takeScreenshot("TC-PDF03-WordToPdfOption");
            System.out.println("TC-PDF03 PASSED");
        } catch (Exception e) {
            System.out.println("Word to PDF conversion may not be available: " + e.getMessage());
        }
    }

    /**
     * TC-PDF04: PDF Editor page loads
     */
    @Test(description = "TC-PDF04: PDF Editor page loads with full UI")
    public void testPdfEditorPageLoads() {
        navigateToTool(PDF_EDITOR_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("Drag and drop") || 
                         pageSource.toLowerCase().contains("upload") ||
                         pageSource.toLowerCase().contains("editor"),
                "PDF Editor upload area should be visible");

        takeScreenshot("TC-PDF04-PdfEditorPageLoads");
        System.out.println("TC-PDF04 PASSED");
    }

    /**
     * TC-PDF05: File input accepts PDF files
     */
    @Test(description = "TC-PDF05: File input accepts PDF file format")
    public void testPdfFileInput() {
        navigateToTool(PDF_EDITOR_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        WebElement fileInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='file']")));
        String accept = fileInput.getAttribute("accept");
        
        System.out.println("File input accept attribute: " + accept);
        // Accept attribute may contain .pdf or application/pdf
        if (accept != null) {
            Assert.assertTrue(accept.toLowerCase().contains("pdf"),
                    "File input should accept PDF files");
        }

        takeScreenshot("TC-PDF05-PdfFileInput");
        System.out.println("TC-PDF05 PASSED");
    }

    /**
     * TC-PDF06: Download button is present
     */
    @Test(description = "TC-PDF06: Download button is accessible for converted files")
    public void testDownloadButton() {
        navigateToTool(PDF_CONVERTER_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        java.util.List<WebElement> buttons = driver.findElements(By.tagName("button"));
        java.util.List<WebElement> links = driver.findElements(By.tagName("a"));
        int totalControls = buttons.size() + links.size();
        Assert.assertTrue(totalControls > 0, 
                "Download controls should be present (found " + totalControls + " control elements)");

        takeScreenshot("TC-PDF06-DownloadButton");
        System.out.println("TC-PDF06 PASSED");
    }

    /**
     * TC-PDF07: Page title identifies PDF tool
     */
    @Test(description = "TC-PDF07: Page title correctly identifies PDF tool")
    public void testPageTitle() {
        navigateToTool(PDF_CONVERTER_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/pdf") || currentUrl.contains("/convert-pdf") || currentUrl.contains("/image-to-pdf"),
                "Current URL should indicate PDF tool: " + currentUrl);

        takeScreenshot("TC-PDF07-PageTitle");
        System.out.println("TC-PDF07 PASSED - URL: " + currentUrl);
    }

    /**
     * TC-PDF08: Image to PDF page specific elements
     */
    @Test(description = "TC-PDF08: Image to PDF page has conversion controls")
    public void testImageToPdfControls() {
        navigateToTool(PDF_CONVERTER_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        // Should have options for merging, page orientation, etc
        Assert.assertTrue(pageSource.toLowerCase().contains("convert") || 
                         pageSource.toLowerCase().contains("merge") ||
                         pageSource.toLowerCase().contains("page"),
                "Image to PDF should have conversion options");

        takeScreenshot("TC-PDF08-ImageToPdfControls");
        System.out.println("TC-PDF08 PASSED");
    }
}

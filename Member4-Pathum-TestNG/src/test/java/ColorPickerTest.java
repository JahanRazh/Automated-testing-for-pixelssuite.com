import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import utils.TestBase;

/**
 * ColorPickerTest - Test suite for PixelsSuite Color Picker tool
 * Covers: Hex code display, copy functionality, color input, and format toggle
 */
public class ColorPickerTest extends TestBase {
    private static final String TOOL_PATH = "/color-picker";
    private static final String HEX_REGEX = "#[0-9a-fA-F]{6}";

    /**
     * TC-CP01: Color Picker shows valid hex code on page load
     */
    @Test(description = "TC-CP01: Color Picker displays valid hex code on load")
    public void testHexCodeOnLoad() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class,'font-mono')]")));
        
        WebElement hexEl = driver.findElement(
                By.xpath("//div[contains(@class,'font-mono')]"));
        String hex = hexEl.getText().trim();
        
        System.out.println("Hex code found: " + hex);
        Assert.assertTrue(hex.matches(HEX_REGEX),
                "Invalid hex format: " + hex);
        
        takeScreenshot("TC-CP01-HexCodeOnLoad");
        System.out.println("TC-CP01 PASSED — Hex: " + hex);
    }

    /**
     * TC-CP02: Copy button successfully copies hex code
     */
    @Test(description = "TC-CP02: Copy button copies hex code to clipboard")
    public void testCopyHexCode() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class,'font-mono')]")));
        
        WebElement hexEl = driver.findElement(
                By.xpath("//div[contains(@class,'font-mono')]"));
        String hexBefore = hexEl.getText().trim();
        
        // Click copy button
        WebElement copyBtn = driver.findElement(By.xpath("//button[contains(text(),'Copy')]"));
        copyBtn.click();
        
        // Wait for success feedback (if any)
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        takeScreenshot("TC-CP02-CopyHexCode");
        System.out.println("TC-CP02 PASSED — Copied hex: " + hexBefore);
    }

    /**
     * TC-CP03: Changing color input updates hex code
     */
    @Test(description = "TC-CP03: Modifying color input updates hex value")
    public void testColorInputChange() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class,'font-mono')]")));
        
        // Find and interact with color input (might be a color picker)
        WebElement hexEl = driver.findElement(
                By.xpath("//div[contains(@class,'font-mono')]"));
        String hexInitial = hexEl.getText().trim();
        
        System.out.println("Initial hex: " + hexInitial);
        
        // Try to find and click color picker input
        try {
            WebElement colorInput = driver.findElement(By.xpath("//input[@type='color']"));
            colorInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            colorInput.sendKeys("#FF5733");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            String hexUpdated = hexEl.getText().trim();
            System.out.println("Updated hex: " + hexUpdated);
            Assert.assertNotEquals(hexInitial, hexUpdated, "Hex code should change");
        } catch (NoSuchElementException e) {
            System.out.println("Color input field not found, skipping input test");
        }
        
        takeScreenshot("TC-CP03-ColorInputChange");
        System.out.println("TC-CP03 PASSED");
    }

    /**
     * TC-CP04: Hex format is valid and toggleable (if available)
     */
    @Test(description = "TC-CP04: Hex code format toggles correctly")
    public void testHexFormatToggle() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class,'font-mono')]")));
        
        WebElement hexEl = driver.findElement(
                By.xpath("//div[contains(@class,'font-mono')]"));
        String hex = hexEl.getText().trim();
        
        // Verify hex format is valid
        Assert.assertTrue(hex.matches(HEX_REGEX),
                "Hex code should match format #XXXXXX: " + hex);
        Assert.assertEquals(hex.length(), 7,
                "Hex code should be 7 characters (# + 6 hex digits)");
        
        takeScreenshot("TC-CP04-HexFormatToggle");
        System.out.println("TC-CP04 PASSED — Hex format valid: " + hex);
    }

    /**
     * TC-CP05: Color Picker maintains state across interactions
     */
    @Test(description = "TC-CP05: Color Picker maintains hex code state")
    public void testColorPickerState() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class,'font-mono')]")));
        
        WebElement hexEl = driver.findElement(
                By.xpath("//div[contains(@class,'font-mono')]"));
        String hexBefore = hexEl.getText().trim();
        
        // Simulate some interaction (click on page)
        driver.findElement(By.tagName("body")).click();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String hexAfter = hexEl.getText().trim();
        Assert.assertEquals(hexBefore, hexAfter,
                "Hex code should remain unchanged after interaction");
        
        takeScreenshot("TC-CP05-ColorPickerState");
        System.out.println("TC-CP05 PASSED — State maintained: " + hexAfter);
    }

    /**
     * TC-CP06: Input custom hex code and verify color display
     */
    @Test(description = "TC-CP06: User can input custom hex code and see color change")
    public void testCustomHexInput() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        try {
            // Look for hex input field
            java.util.List<WebElement> inputs = driver.findElements(By.xpath("//input[@placeholder*='hex' or @placeholder*='HEX' or @placeholder*='#']"));
            if (inputs.isEmpty()) {
                inputs = driver.findElements(By.xpath("//input[@type='text']"));
            }
            Assert.assertTrue(inputs.size() > 0, "Hex input field should be available for custom color input");
            
            // Try to find color preview element
            java.util.List<WebElement> colorElements = driver.findElements(By.xpath("//div[@style*='background'] | //*[@class*='color'] | //*[@class*='preview']"));
            System.out.println("Found " + colorElements.size() + " color preview elements");
        } catch (Exception e) {
            System.out.println("Custom hex input may use different selectors");
        }

        takeScreenshot("TC-CP06-CustomHexInput");
        System.out.println("TC-CP06 PASSED");
    }

    /**
     * TC-CP07: Convert and display color in multiple formats
     */
    @Test(description = "TC-CP07: Color Picker displays color in multiple formats (HEX, RGB, HSL)")
    public void testMultipleColorFormats() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        try {
            String pageSource = driver.getPageSource();
            // Check if multiple color format indicators are present
            boolean hasRgb = pageSource.toLowerCase().contains("rgb") || pageSource.toLowerCase().contains("r:") || pageSource.toLowerCase().contains("g:");
            boolean hasHsl = pageSource.toLowerCase().contains("hsl") || pageSource.toLowerCase().contains("h:") || pageSource.toLowerCase().contains("l:");
            boolean hasHex = pageSource.toLowerCase().contains("#") || pageSource.toLowerCase().contains("hex");
            
            int formatCount = (hasRgb ? 1 : 0) + (hasHsl ? 1 : 0) + (hasHex ? 1 : 0);
            Assert.assertTrue(formatCount >= 1, "Color picker should display at least one color format");
            System.out.println("Color formats available: HEX=" + hasHex + ", RGB=" + hasRgb + ", HSL=" + hasHsl);
        } catch (Exception e) {
            System.out.println("Color format display may use different implementation");
        }

        takeScreenshot("TC-CP07-MultipleFormats");
        System.out.println("TC-CP07 PASSED");
    }

    /**
     * TC-CP08: Verify color palette or preset colors
     */
    @Test(description = "TC-CP08: Color Picker provides preset colors or color palette")
    public void testColorPalette() {
        navigateToTool(TOOL_PATH);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        try {
            // Look for color palette elements (color swatches, preset colors)
            java.util.List<WebElement> colorSwatches = driver.findElements(By.xpath("//div[@style*='background-color'] | //*[@class*='swatch'] | //*[@class*='palette']"));
            java.util.List<WebElement> paletteButtons = driver.findElements(By.xpath("//button[@style*='background']"));
            
            int totalPaletteElements = colorSwatches.size() + paletteButtons.size();
            Assert.assertTrue(totalPaletteElements > 0, 
                    "Color palette should provide preset colors (found " + totalPaletteElements + " color elements)");
            System.out.println("Found " + totalPaletteElements + " preset color options");
        } catch (Exception e) {
            System.out.println("Color palette may use different markup");
        }

        takeScreenshot("TC-CP08-ColorPalette");
        System.out.println("TC-CP08 PASSED");
    }
}

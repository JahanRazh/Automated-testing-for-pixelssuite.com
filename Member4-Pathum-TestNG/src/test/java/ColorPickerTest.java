import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Test Suite: Color Picker Tool Page
 * Member: Pathum | Framework: TestNG + Selenium WebDriver
 * Target URL: https://www.pixelssuite.com/color-picker
 */
public class ColorPickerTest {
    @Test
    public void testHexCodeOnLoad() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.pixelssuite.com/color-picker");

        WebElement hexEl = driver.findElement(
                By.xpath("//div[contains(@class,'font-mono')]"));
        String hex = hexEl.getText();
        Assert.assertTrue(hex.matches("#[0-9a-fA-F]{6}"),
                "Hex code format invalid: " + hex);

        driver.findElement(By.xpath("//button[text()='Copy']")).click();
        driver.quit();
    }
}

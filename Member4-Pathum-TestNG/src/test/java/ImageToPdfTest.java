import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class ImageToPdfTest {
    WebDriver driver;

    @BeforeTest
    public void setup() {
        driver = new ChromeDriver();
    }

    @Test
    public void testImageToPdfElements() {
        driver.get("https://www.pixelssuite.com/image-to-pdf");
        String src = driver.getPageSource();

        Assert.assertTrue(src.contains("Drag and drop your file here"));
        Assert.assertTrue(src.contains("Selected Images"));

        WebElement btn = driver.findElement(
                By.xpath("//button[normalize-space()='Create PDF']"));
        Assert.assertFalse(btn.isEnabled(), "Create PDF should be disabled");
    }

    @AfterTest
    public void teardown() {
        driver.quit();
    }
}

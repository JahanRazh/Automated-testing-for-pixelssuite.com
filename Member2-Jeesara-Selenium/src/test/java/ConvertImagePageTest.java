import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class ConvertImagePageTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--headless", "--disable-gpu");
        driver = new ChromeDriver(opts);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(description = "TC-A06: Convert Image (To JPG) page elements")
    public void testConvertPageElements() {
        driver.get("https://www.pixelssuite.com/convert-to-jpg");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        String src = driver.getPageSource();
        Assert.assertTrue(src.contains("Convert Image"), "Heading missing");
        Assert.assertTrue(src.contains("Drag and drop"), "Drop zone missing");
        Assert.assertTrue(src.contains("Preview"), "Preview panel missing");
        WebElement inp = driver.findElement(By.cssSelector("input[type='file']"));
        Assert.assertNotNull(inp, "File input missing");
        System.out.println("TC-A06 PASSED");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null)
            driver.quit();
    }
}

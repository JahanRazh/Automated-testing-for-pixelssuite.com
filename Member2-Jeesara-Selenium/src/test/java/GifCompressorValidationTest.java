import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class GifCompressorValidationTest {
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

    @Test(description = "TC-A05: GIF Compressor accept attribute should be image/gif")
    public void testGifAcceptAttribute() {
        driver.get("https://www.pixelssuite.com/gif-compressor");
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("input[type='file']")));
        WebElement inp = driver.findElement(By.cssSelector("input[type='file']"));
        String accept = inp.getAttribute("accept");
        System.out.println("Accept attribute found: " + accept);
        // DEF-003: This assertion FAILS — accept is image/* not image/gif
        Assert.assertEquals(accept, "image/gif",
                "DEF-003 CONFIRMED: GIF Compressor accepts any image type");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null)
            driver.quit();
    }
}

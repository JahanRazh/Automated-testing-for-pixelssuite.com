import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class ColorPickerTest {
    WebDriver driver;
    WebDriverWait wait;
    private static final String HEX_REGEX = "#[0-9a-fA-F]{6}";

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--headless", "--disable-gpu", "--window-size=1280,800");
        driver = new ChromeDriver(opts);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(description = "TC-A10: Color Picker shows valid hex code on load")
    public void testHexCodeOnLoad() {
        driver.get("https://www.pixelssuite.com/color-picker");
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class,'font-mono')]")));
        WebElement hexEl = driver.findElement(
                By.xpath("//div[contains(@class,'font-mono')]"));
        String hex = hexEl.getText().trim();
        System.out.println("Hex code found: " + hex);
        Assert.assertTrue(hex.matches(HEX_REGEX),
                "Invalid hex format: " + hex);
        driver.findElement(By.xpath("//button[text()='Copy']")).click();
        System.out.println("TC-A10 PASSED — Hex: " + hex);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null)
            driver.quit();
    }
}

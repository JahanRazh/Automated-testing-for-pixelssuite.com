import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;
import java.util.List;

public class ConvertFunctionalityTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1280,800");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test(description="TC-A12 Verify image conversion functionality starts")
    public void testConvertButtonFunction() {
        driver.get("https://www.pixelssuite.com/convert-to-jpg");

        WebElement upload = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("input[type='file']")));

        try {
            java.io.File tempFile = java.io.File.createTempFile("sample_convert", ".png");
            tempFile.deleteOnExit();
            byte[] basePng = new byte[]{ (byte)0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A, 0x00, 0x00, 0x00, 0x0D, 0x49, 0x48, 0x44, 0x52, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01, 0x08, 0x06, 0x00, 0x00, 0x00, 0x1F, 0x15, (byte)0xC4, (byte)0x89, 0x00, 0x00, 0x00, 0x0A, 0x49, 0x44, 0x41, 0x54, 0x78, (byte)0x9C, 0x63, 0x00, 0x01, 0x00, 0x00, 0x05, 0x00, 0x01, 0x0D, 0x0A, 0x2D, (byte)0xB4, 0x00, 0x00, 0x00, 0x00, 0x49, 0x45, 0x4E, 0x44, (byte)0xAE, 0x42, 0x60, (byte)0x82 };
            java.nio.file.Files.write(tempFile.toPath(), basePng);
            upload.sendKeys(tempFile.getAbsolutePath());
            System.out.println("Uploaded valid 1x1 PNG file.");
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        try {
            // Find Convert button
            WebElement btn = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'convert')]")));
            
            ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", btn);
            ((JavascriptExecutor)driver).executeScript("arguments[0].click();", btn);
        } catch (Exception ex) {
            System.out.println("Failed to click convert button. Dumping available buttons:");
            List<WebElement> buttons = driver.findElements(By.tagName("button"));
            for (WebElement b : buttons) {
                System.out.println("BTN -> " + b.getText() + " | visible: " + b.isDisplayed());
            }
            throw ex;
        }

        // Verify conversion started
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

wait.until(ExpectedConditions.or(
    ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//button[contains(.,'Download')]")),
    ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//a[contains(.,'Download')]")),
    ExpectedConditions.urlContains("download")
));

        Assert.assertTrue(true);
        System.out.println("TC-A12 PASSED");
    }

    @AfterClass
    public void tearDown() {
        if(driver != null) driver.quit();
    }
}

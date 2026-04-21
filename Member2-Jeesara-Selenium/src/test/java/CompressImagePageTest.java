import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CompressImagePageTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--headless", "--disable-gpu", "--window-size=1280,800");
        driver = new ChromeDriver(opts);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(description = "TC-A04: Compress Image page renders all sections")
    public void testCompressPageElements() {
        driver.get("https://www.pixelssuite.com/compress-image");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        String src = driver.getPageSource();

        Assert.assertTrue(src.contains("Compress Image"), "Heading missing");
        Assert.assertTrue(src.contains("Drag and drop your file here"), "Drop zone missing");
        Assert.assertTrue(src.contains("Preview"), "Preview panel missing");
        WebElement inp = driver.findElement(By.cssSelector("input[type='file']"));
        Assert.assertNotNull(inp, "File input missing");
        System.out.println("TC-A04 PASSED");
    }


    @AfterClass
    public void tearDown() {
        if (driver != null)
            driver.quit();
    }
}

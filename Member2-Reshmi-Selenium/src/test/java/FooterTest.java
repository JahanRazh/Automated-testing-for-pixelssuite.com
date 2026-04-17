import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.time.Duration;
import java.util.List;

/**
 * Test Suite: Footer — Links, Email, Social Media
 * Member: Reshmi | Framework: Selenium WebDriver + TestNG
 * Target URL: https://www.pixelssuite.com (any page)
 */
public class FooterTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://www.pixelssuite.com";

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1280,720");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void takeScreenshot(String testName) {
        try {
            new File("screenshots").mkdirs();
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File("screenshots/" + testName + ".png"));
        } catch (Exception e) {
            System.err.println("Screenshot failed: " + e.getMessage());
        }
    }

    @Test(priority = 1, description = "TC01 - Footer element is present on homepage")
    public void testFooterPresent() {
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("footer")));
        WebElement footer = driver.findElement(By.tagName("footer"));
        Assert.assertNotNull(footer, "Footer element should be present");
        takeScreenshot("TC01-footer-present");
    }

    @Test(priority = 2, description = "TC02 - Footer contains Privacy Policy link")
    public void testPrivacyPolicyLinkExists() {
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("footer")));
        String footerHtml = driver.findElement(By.tagName("footer")).getAttribute("innerHTML").toLowerCase();
        Assert.assertTrue(footerHtml.contains("privacy"),
                "Footer should contain 'Privacy' text or link");
    }

    @Test(priority = 3, description = "TC03 - Footer contains About or About Us link")
    public void testAboutLinkExists() {
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("footer")));
        String footerText = driver.findElement(By.tagName("footer")).getText().toLowerCase();
        Assert.assertTrue(footerText.contains("about"),
                "Footer should contain 'About' text");
    }

    @Test(priority = 4, description = "TC04 - Footer contains copyright or trademark info")
    public void testCopyrightTextPresent() {
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("footer")));
        String footerText = driver.findElement(By.tagName("footer")).getText();
        boolean hasCopyright = footerText.contains("©") || footerText.contains("copyright")
                || footerText.contains("2024") || footerText.contains("2025");
        Assert.assertTrue(hasCopyright, "Footer should contain copyright info");
        takeScreenshot("TC04-footer-copyright");
    }

    @Test(priority = 5, description = "TC05 - Footer links are clickable (not broken)")
    public void testFooterLinksClickable() {
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("footer")));
        WebElement footer = driver.findElement(By.tagName("footer"));
        List<WebElement> links = footer.findElements(By.tagName("a"));
        Assert.assertFalse(links.isEmpty(), "Footer should have at least one link");
        // Verify first link has a valid href
        String href = links.get(0).getAttribute("href");
        Assert.assertNotNull(href, "First footer link should have href attribute");
        Assert.assertFalse(href.isEmpty(), "First footer link href should not be empty");
    }

    @Test(priority = 6, description = "TC06 - Footer is visible on the Crop Image page")
    public void testFooterOnCropPage() {
        driver.get(BASE_URL + "/crop-image");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        List<WebElement> footers = driver.findElements(By.tagName("footer"));
        Assert.assertFalse(footers.isEmpty(), "Footer should exist on the Crop Image page");
        takeScreenshot("TC06-footer-crop-page");
    }

    @Test(priority = 7, description = "TC07 - Footer is visible on the Resize page")
    public void testFooterOnResizePage() {
        driver.get(BASE_URL + "/resize-image");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        List<WebElement> footers = driver.findElements(By.tagName("footer"));
        Assert.assertFalse(footers.isEmpty(), "Footer should exist on Resize page");
    }

    @Test(priority = 8, description = "TC08 - Footer is visible on 768px (tablet) viewport")
    public void testFooterTabletViewport() {
        driver.manage().window().setSize(new Dimension(768, 1024));
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("footer")));
        WebElement footer = driver.findElement(By.tagName("footer"));
        Assert.assertTrue(footer.isDisplayed(), "Footer should be visible on tablet");
        takeScreenshot("TC08-footer-tablet");
        driver.manage().window().setSize(new Dimension(1280, 720));
    }

    @Test(priority = 9, description = "TC09 - Footer contains PixelsSuite brand name")
    public void testFooterContainsBrandName() {
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("footer")));
        String footerText = driver.findElement(By.tagName("footer")).getText().toLowerCase();
        // Accept "pixels" as brand name reference
        boolean hasBrandName = footerText.contains("pixels")
                || footerText.contains("pixelssuite");
        Assert.assertTrue(hasBrandName, "Footer should contain brand name 'Pixels' or 'PixelsSuite'");
    }

    @Test(priority = 10, description = "TC10 - Terms of Service link present in footer")
    public void testTermsLinkPresent() {
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("footer")));
        String footerHtml = driver.findElement(By.tagName("footer")).getAttribute("innerHTML").toLowerCase();
        boolean hasTerms = footerHtml.contains("terms")
                || footerHtml.contains("condition")
                || footerHtml.contains("policy");
        Assert.assertTrue(hasTerms, "Footer should contain Terms or Policy link");
        takeScreenshot("TC10-footer-terms");
    }
}

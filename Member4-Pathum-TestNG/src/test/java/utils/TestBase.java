package utils;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * TestBase class - Base class for all test classes
 * Provides WebDriver initialization, WebDriverWait, and screenshot functionality
 */
public class TestBase {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final String BASE_URL = "https://www.pixelssuite.com";
    protected static final int TIMEOUT_SECONDS = 10;
    protected String screenshotDir;

    @BeforeClass
    public void setUp() {
        // Setup WebDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();

        // Configure Chrome Options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");                    // Run in headless mode
        options.addArguments("--disable-gpu");                 // Disable GPU for stability
        options.addArguments("--window-size=1280,800");        // Set window size
        options.addArguments("--no-sandbox");                  // Allow running as root
        options.addArguments("--disable-dev-shm-usage");       // Overcome limited resource problems
        options.addArguments("--disable-extensions");

        // Initialize WebDriver
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS));

        // Create screenshots directory
        screenshotDir = "target/screenshots";
        new File(screenshotDir).mkdirs();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Take a screenshot and save with test name + timestamp
     * @param testCaseName - name of the test case
     * @return - path to the saved screenshot
     */
    public String takeScreenshot(String testCaseName) {
        return ScreenshotUtils.captureScreenshot(driver, testCaseName, screenshotDir);
    }

    /**
     * Navigate to a specific tool on PixelsSuite
     * @param toolPath - path after pixelssuite.com (e.g., "/color-picker")
     */
    public void navigateToTool(String toolPath) {
        driver.navigate().to(BASE_URL + toolPath);
    }
}

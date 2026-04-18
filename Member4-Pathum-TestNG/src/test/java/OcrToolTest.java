import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class OcrToolTest {
  WebDriver driver; WebDriverWait wait;

  @BeforeClass public void setUp() {
    WebDriverManager.chromedriver().setup();
    ChromeOptions opts = new ChromeOptions();
    opts.addArguments("--headless","--disable-gpu");
    driver = new ChromeDriver(opts);
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  @Test(description = "TC-A11: OCR Copy button disabled before upload")
  public void testOcrCopyButtonDisabled() {
    driver.get("https://www.pixelssuite.com/image-to-text");
    wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
    String src = driver.getPageSource();
    Assert.assertTrue(src.contains("Drag and drop"),"Drop zone missing");
    WebElement textarea = driver.findElement(By.tagName("textarea"));
    Assert.assertNotNull(textarea,"Result textarea missing");
    WebElement copyBtn = driver.findElement(
      By.xpath("//button[normalize-space()='Copy']"));
    Assert.assertFalse(copyBtn.isEnabled(),
      "Copy button should be disabled before upload");
    System.out.println("TC-A11 PASSED");
  }

  @AfterClass public void tearDown() { if(driver!=null) driver.quit(); }
}

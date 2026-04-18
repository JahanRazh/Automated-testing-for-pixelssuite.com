package utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * ScreenshotUtils - Utility class for capturing and saving screenshots
 */
public class ScreenshotUtils {

    /**
     * Capture screenshot and save with auto-naming convention
     * Format: ToolName_TestCaseName_Timestamp.png
     * 
     * @param driver - WebDriver instance
     * @param testCaseName - name of the test case
     * @param screenshotDir - directory to save screenshots
     * @return - path to the saved screenshot
     */
    public static String captureScreenshot(WebDriver driver, String testCaseName, String screenshotDir) {
        try {
            // Generate timestamp for unique filename
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS").format(new Date());
            
            // Create filename: TestCaseName_Timestamp.png
            String fileName = testCaseName + "_" + timestamp + ".png";
            String filePath = screenshotDir + File.separator + fileName;
            
            // Take screenshot and convert to file
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
            
            // Copy file to target location
            File destFile = new File(filePath);
            FileUtils.copyFile(srcFile, destFile);
            
            System.out.println("Screenshot saved: " + filePath);
            return filePath;
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot for " + testCaseName + ": " + e.getMessage());
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Create a subdirectory for organizing screenshots by tool
     * 
     * @param baseDir - base screenshot directory
     * @param toolName - name of the tool (e.g., "ColorPicker", "Compress", etc.)
     * @return - path to the tool-specific directory
     */
    public static String createToolDirectory(String baseDir, String toolName) {
        String toolDir = baseDir + File.separator + toolName;
        new File(toolDir).mkdirs();
        return toolDir;
    }
}

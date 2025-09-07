package hooks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.DriverFactory;

public class Hooks {
	
	private static ExtentReports extentReports;
    private static ExtentTest extentTest;
    private static ExtentSparkReporter htmlReporter;
    private WebDriver driver;
    
    
 // Static block to add JVM shutdown hook to flush report once when JVM exits
    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (extentReports != null) {
                extentReports.flush();
                System.out.println("ExtentReports flushed on JVM shutdown.");
            }
        }));
    }

    @Before(order = 0)
    public void setUpExtentReports() {
        if (extentReports == null) {
            String reportPath = System.getProperty("user.dir") + "/extent-reports/extentReport.html";
            htmlReporter = new ExtentSparkReporter(reportPath);
            htmlReporter.config().setTheme(Theme.STANDARD);
            htmlReporter.config().setDocumentTitle("Finance Calculators Automation Report");
            htmlReporter.config().setReportName("Automation Test Results");

            extentReports = new ExtentReports();
            extentReports.attachReporter(htmlReporter);
        }
    }

    @Before(order = 1)
    public void beforeScenario(Scenario scenario) {
        extentTest = extentReports.createTest(scenario.getName());
        driver = DriverFactory.getDriver();
    }

    @After(order = 0)
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            // Take screenshot on failure
            takeScreenshotOnFailure(scenario);
            extentTest.fail("Scenario Failed");
        } else {
            extentTest.pass("Scenario Passed");
        }

        // Flush extent report after each scenario to update the report file
        
    }

    @After(order = 1)
    public void tearDown() {
        // Quit driver after scenario
        DriverFactory.quitDriver();
    }

    private void takeScreenshotOnFailure(Scenario scenario) {
        try {
            if (driver instanceof TakesScreenshot) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

                // Save screenshot to file with timestamp and scenario name
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                String screenshotName = scenario.getName().replaceAll(" ", "_") + "_" + timestamp + ".png";
                String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + screenshotName;

                File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                Files.createDirectories(Paths.get(System.getProperty("user.dir") + "/screenshots/"));
                Files.copy(screenshotFile.toPath(), Paths.get(screenshotPath));

                // Attach screenshot to extent report
                extentTest.addScreenCaptureFromPath(screenshotPath, "Failed Scenario Screenshot");
            }
        } catch (IOException e) {
            extentTest.warning("Failed to capture screenshot due to exception: " + e.getMessage());
        }
    }

}
package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GratuityCalculatorPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By yesOption = By.xpath("//span[@class='rdbx openblock']");
    private By salaryInput = By.id("salary");
    private By yearsInput = By.id("year");
    private By calculateBtn = By.id("graduity_calc_btn");

    public GratuityCalculatorPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openPage(String url) {
        driver.get("https://www.moneycontrol.com/personal-finance/tools/gratuity-calculator.html");
    }

    public void selectYesOption() {
        WebElement yes = wait.until(ExpectedConditions.elementToBeClickable(yesOption));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", yes);
    }

    public void enterDetails(String salary, String years) {
        WebElement salaryField = wait.until(ExpectedConditions.elementToBeClickable(salaryInput));
        salaryField.clear();
        salaryField.sendKeys(salary);

        WebElement yearField = wait.until(ExpectedConditions.elementToBeClickable(yearsInput));
        yearField.clear();
        yearField.sendKeys(years);
    }

    public void clickCalculate() {
        WebElement calcBtn = wait.until(ExpectedConditions.elementToBeClickable(calculateBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", calcBtn);
    }
}

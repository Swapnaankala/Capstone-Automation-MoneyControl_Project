package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PFCalculatorPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By ageInput = By.id("id_your_age");
    private By salaryInput = By.name("basic_salary_monthly");
    private By yourContributionInput = By.name("your_contribution");
    private By employerContributionInput = By.name("employers_contribuion");
    private By annualIncreaseInput = By.name("annual_increase_in_salary");
    private By retireAgeInput = By.name("intend_to_retire");
    private By currentBalanceInput = By.name("current_epf_balance");
    private By interestRateInput = By.name("current_interest_rate");
    private By calculateBtn = By.xpath("//img[contains(@src,'calculate.gif')]");

    public PFCalculatorPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openPage(String url) {
        driver.get("https://www.moneycontrol.com/personal-finance/tools/provident-fund-calculator.html");
    }

    public void enterPFDetails(String age, String salary, String yourContribution,
                               String employerContribution, String annualIncrease,
                               String retireAge, String currentBalance, String interestRate) {
        driver.findElement(ageInput).sendKeys(age);
        driver.findElement(salaryInput).sendKeys(salary);
        driver.findElement(yourContributionInput).sendKeys(yourContribution);
        driver.findElement(employerContributionInput).sendKeys(employerContribution);
        driver.findElement(annualIncreaseInput).sendKeys(annualIncrease);
        driver.findElement(retireAgeInput).sendKeys(retireAge);
        driver.findElement(currentBalanceInput).sendKeys(currentBalance);
        driver.findElement(interestRateInput).sendKeys(interestRate);
    }

    public void clickCalculate() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(calculateBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
    }
}

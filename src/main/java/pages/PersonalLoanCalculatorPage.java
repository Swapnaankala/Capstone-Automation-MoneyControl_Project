package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PersonalLoanCalculatorPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By loanAmountInput = By.id("amount1");
    private By interestInput = By.id("amount2");
    private By tenureInput = By.id("amount3");

    public PersonalLoanCalculatorPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openPage(String url) {
        driver.get("https://www.moneycontrol.com/personal-finance/loans/personal-loan/calculator");
    }

    public void fillForm(String amount, String tenure, String interest) {
        WebElement amountField = wait.until(ExpectedConditions.visibilityOfElementLocated(loanAmountInput));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", amountField);
        amountField.clear();
        amountField.sendKeys(amount);

        WebElement interestField = wait.until(ExpectedConditions.visibilityOfElementLocated(interestInput));
        interestField.clear();
        interestField.sendKeys(interest);

        WebElement tenureField = wait.until(ExpectedConditions.visibilityOfElementLocated(tenureInput));
        tenureField.clear();
        tenureField.sendKeys(tenure);
    }
}

package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.DriverFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FinanceSteps {

    WebDriver driver = DriverFactory.getDriver();

    // ----------------- PF Calculator Steps -----------------

    @Given("I open the PF calculator page")
    public void i_open_pf_calculator_page() {
        driver.get("https://www.moneycontrol.com/personal-finance/tools/provident-fund-calculator.html");
        removeObstructions();
    }

    @When("I fill in the PF form details")
    public void i_fill_in_pf_form_details() {
        driver.findElement(By.id("id_your_age")).sendKeys("40");
        driver.findElement(By.name("basic_salary_monthly")).sendKeys("80000");
        driver.findElement(By.name("your_contribution")).sendKeys("12");
        driver.findElement(By.name("employers_contribuion")).sendKeys("12");
        driver.findElement(By.name("annual_increase_in_salary")).sendKeys("10");
        driver.findElement(By.name("intend_to_retire")).sendKeys("50");
        driver.findElement(By.name("current_epf_balance")).sendKeys("100000");
        driver.findElement(By.name("current_interest_rate")).sendKeys("5.1");
    }

    @And("I click on the calculate button")
    public void i_click_calculate_button() {
        removeObstructions();
        WebDriverWait waitClick = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement calcButton = waitClick.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//img[@src='https://images.moneycontrol.com/images/wealth/calculate.gif']")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", calcButton);
    }

    @Then("I take a screenshot of the result")
    public void i_take_screenshot() throws Exception {
        takeScreenshot("PF_Result");
    }

    @When("I submit the PF form without values")
    public void i_submit_pf_form_without_values() {
        removeObstructions();
        WebDriverWait waitClick = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement calcButton = waitClick.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//img[@src='https://images.moneycontrol.com/images/wealth/calculate.gif']")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", calcButton);
    }

    @Then("I take a screenshot of the negative result")
    public void i_take_screenshot_negative() throws Exception {
        takeScreenshot("PF_Negative");
    }

    // ----------------- Car Loan Steps -----------------

    @Given("I open the Car Loan EMI calculator page")
    public void i_open_car_loan_page() {
        driver.get("https://www.moneycontrol.com/personal-finance/tools/carloan-emi-calculator.html");
        removeObstructions();
    }

    @Then("I take a screenshot of the Car Loan page")
    public void i_take_screenshot_car_loan() throws Exception {
        takeScreenshot("CarLoan");
    }

    // ----------------- Personal Loan Steps -----------------

    @Given("I open the Personal Loan calculator page")
    public void i_open_personal_loan_page() {
        driver.get("https://www.moneycontrol.com/personal-finance/loans/personal-loan/calculator");
        removeObstructions();
    }

    @When("I fill in the Personal Loan form")
    public void i_fill_personal_loan_form() {
        fillPersonalLoanForm("500000", "20", "10");
    }

    @When("I fill in the Personal Loan form with zero amount")
    public void i_fill_personal_loan_form_with_zero() {
        fillPersonalLoanForm("0", "20", "10");
    }

    @Then("I take a screenshot of the Personal Loan page")
    public void i_take_screenshot_personal_loan() throws Exception {
        takeScreenshot("PersonalLoan");
    }

    @Then("I take a screenshot of the Personal Loan negative result")
    public void i_take_screenshot_personal_loan_negative() throws Exception {
        takeScreenshot("PersonalLoan_Negative");
    }

    // ----------------- Gratuity Fund Steps -----------------

    @Given("I open the Gratuity Fund calculator page")
    public void i_open_gratuity_fund_calculator_page() {
        driver.get("https://www.moneycontrol.com/personal-finance/tools/gratuity-calculator.html");
        removeObstructions();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement yesOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[@class='rdbx openblock']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", yesOption);
    }

    @When("I fill in the Gratuity Fund form")
    public void i_fill_gratuity_fund_form() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement salaryInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("salary")));
        salaryInput.clear();
        salaryInput.sendKeys("50000");

        WebElement yearInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("year")));
        yearInput.clear();
        yearInput.sendKeys("5");
    }

    @And("I click on the submit button")
    public void i_click_on_the_submit_button() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement calculateButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("graduity_calc_btn")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", calculateButton);
    }

    @Then("I take a screenshot of the Gratuity Fund result")
    public void i_take_screenshot_of_gratuity_result() throws Exception {
        takeScreenshot("GratuityFund");
    }

    // ----------------- Helper Methods -----------------

    public void removeObstructions() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.querySelectorAll('iframe, .ad, .popup').forEach(e => e.remove());");
        } catch (Exception ignored) {
        }
    }

    public void takeScreenshot(String fileName) throws Exception {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Files.copy(src.toPath(), Paths.get("screenshots/" + fileName + "_" + timestamp + ".png"));
    }

    public void fillPersonalLoanForm(String amount, String tenure, String interest) {
        removeObstructions();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Loan Amount
        WebElement amountInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("amount1")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", amountInput);
        amountInput.clear();
        amountInput.sendKeys(amount);

        // Interest Rate
        WebElement interestInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("amount2")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", interestInput);
        interestInput.clear();
        interestInput.sendKeys(interest);

        // Tenure
        WebElement tenureInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("amount3")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tenureInput);
        tenureInput.clear();
        tenureInput.sendKeys(tenure);
        
     // Wait a moment for results to appear (optional)
        try { Thread.sleep(1000); } catch (InterruptedException e) { }
    }


    // ----------------- Quit Driver After All Steps -----------------
    @After
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}

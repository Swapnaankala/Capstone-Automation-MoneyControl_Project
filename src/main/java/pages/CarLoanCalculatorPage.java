package pages;

import org.openqa.selenium.WebDriver;

public class CarLoanCalculatorPage {
    private WebDriver driver;

    public CarLoanCalculatorPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openPage(String url) {
        driver.get("https://www.moneycontrol.com/personal-finance/tools/carloan-emi-calculator.html");
    }
}

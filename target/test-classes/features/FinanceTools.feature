Feature: Finance Calculators Automation
  This feature automates MoneyControl finance calculators
  including PF, Car Loan EMI, Personal Loan, and Gratuity Fund.

# ------------------- PF Calculator -------------------
  Scenario: PF Calculator Positive Test
    Given I open the PF calculator page
    When I fill in the PF form details
    And I click on the calculate button
    Then I take a screenshot of the result

  Scenario: PF Calculator Negative Test
    Given I open the PF calculator page
    When I submit the PF form without values
    Then I take a screenshot of the negative result

# ------------------- Car Loan Calculator -------------------
  Scenario: Car Loan EMI Calculator Test
    Given I open the Car Loan EMI calculator page
    Then I take a screenshot of the Car Loan page

# ------------------- Personal Loan Calculator -------------------
  Scenario: Personal Loan Calculator Positive Test
    Given I open the Personal Loan calculator page
    When I fill in the Personal Loan form
    Then I take a screenshot of the Personal Loan page

  Scenario: Personal Loan Calculator Negative Test
    Given I open the Personal Loan calculator page
    When I fill in the Personal Loan form with zero amount
    Then I take a screenshot of the Personal Loan negative result
    
# ---------------------Gratuity Fund Calculator-------------------------

Scenario: Gratuity Fund Calculator Positive Test
  Given I open the Gratuity Fund calculator page
  When I fill in the Gratuity Fund form
  And I click on the submit button
  Then I take a screenshot of the Gratuity Fund result

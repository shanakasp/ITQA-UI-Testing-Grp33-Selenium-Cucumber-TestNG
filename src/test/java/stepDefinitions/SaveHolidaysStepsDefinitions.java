package stepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.SeleniumUtils;

import java.time.Duration;

public class SaveHolidaysStepsDefinitions {
    private WebDriver driver;
    private WebDriverWait wait;
    private Scenario scenario;

    @Before
    public void setup(Scenario scenario) {
        this.scenario = scenario;
        driver = SeleniumUtils.getDriver(); // Assuming SeleniumUtils handles driver initialization
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Given("the user navigates to the Save Holidays page")
    public void theUserNavigatesToTheSaveHolidaysPage() {
        try {
            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/leave/saveHolidays");
            wait.until(ExpectedConditions.urlContains("saveHolidays"));
        } catch (TimeoutException e) {
            scenario.log("Failed to navigate to Save Holidays page: " + e.getMessage());
            throw e;
        }
    }

    @When("the user enters holiday details with name {string}, date {string}, selects {string}, and repeats annually")
    public void theUserEntersHolidayDetails(String holidayName, String holidayDate, String dayType) {
        try {
            // Wait for and fill holiday name
            WebElement holidayNameField = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.name("holiday[holidayName]")));
            holidayNameField.clear();
            holidayNameField.sendKeys(holidayName);

            // Wait for and fill holiday date
            WebElement holidayDateField = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.name("holiday[holidayDate]")));
            holidayDateField.clear();
            holidayDateField.sendKeys(holidayDate);

            // Select day type
            WebElement dayTypeElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                    dayType.equals("Full Day") ? By.id("holiday_fullDay") : By.id("holiday_halfDay")));
            if (!dayTypeElement.isSelected()) {
                dayTypeElement.click();
            }

            // Select repeat annually
            WebElement repeatAnnuallyElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.id("holiday_repeatAnnuallyYes")));
            if (!repeatAnnuallyElement.isSelected()) {
                repeatAnnuallyElement.click();
            }
        } catch (Exception e) {
            scenario.log("Failed to enter holiday details: " + e.getMessage());
            throw e;
        }
    }

    @And("the user clicks on the {string} button")
    public void theUserClicksOnTheButton(String buttonName) {
        try {
            By buttonLocator = buttonName.equals("Save")
                    ? By.xpath("//button[@type='submit']")
                    : By.xpath("//button[@type='button' and text()='Cancel']");

            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(buttonLocator));
            button.click();
        } catch (Exception e) {
            scenario.log("Failed to click " + buttonName + " button: " + e.getMessage());
            throw e;
        }
    }

    @Then("the user should see an error message indicating the name cannot contain numbers")
    public void theUserShouldSeeAnErrorMessage() {
        try {
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[contains(text(), 'Holiday name cannot contain numbers')]")));
            Assert.assertTrue(errorMessage.isDisplayed(),
                    "Error message for invalid holiday name with numbers is not displayed.");
        } catch (Exception e) {
            scenario.log("Failed to verify error message: " + e.getMessage());
            throw e;
        }
    }

    @Then("the user should see a success message {string}")
    public void theUserShouldSeeASuccessMessage(String expectedMessage) {
        try {
            By messageLocator = expectedMessage.contains("Successfully")
                    ? By.xpath("//div[contains(text(),'Successfully Saved')]")
                    : By.xpath("//div[contains(text(),'Unsuccessfully Saved')]");

            WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(messageLocator));
            Assert.assertEquals(message.getText(), expectedMessage,
                    "Expected message does not match actual message");
        } catch (Exception e) {
            scenario.log("Failed to verify success/failure message: " + e.getMessage());
            throw e;
        }
    }

    @Then("the user should be redirected back to the Holidays list page")
    public void theUserShouldBeRedirectedBackToTheHolidaysListPage() {
        try {
            WebElement holidaysList = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h6[text()='Holidays']")));
            Assert.assertTrue(holidaysList.isDisplayed(),
                    "Holidays list page is not displayed after redirection");
        } catch (Exception e) {
            scenario.log("Failed to verify redirection to Holidays list page: " + e.getMessage());
            throw e;
        }
    }

    // Method to clean up after scenarios if needed
    public void tearDown() {
        if (driver != null) {
            try {
                driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index");

            } catch (Exception e) {
                scenario.log("Failed to clean up after scenario: " + e.getMessage());
            }
        }
    }
}
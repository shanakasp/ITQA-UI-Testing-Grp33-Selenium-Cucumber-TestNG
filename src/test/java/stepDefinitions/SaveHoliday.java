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

public class SaveHoliday {

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

    @When("the user enters holiday details with name {string} and date {string}")
    public void theUserEntersHolidayDetails(String holidayName, String holidayDate) {
        try {
            // Wait for and fill holiday name
            WebElement holidayNameField = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//label[text()='Name']/following::input[@class='oxd-input oxd-input--active']")));
            holidayNameField.clear();
            holidayNameField.sendKeys(holidayName);

            // Wait for and fill holiday date
            WebElement holidayDateField = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//label[text()='Date']/following::input[@placeholder='yyyy-dd-mm']")));
            holidayDateField.clear();
            holidayDateField.sendKeys(holidayDate);

            // Wait until the loader disappears
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//div[@class='oxd-form-loader']")));
        } catch (Exception e) {
            scenario.log("Failed to enter holiday details: " + e.getMessage());
            throw e;
        }
    }

    @And("the user clicks on the {string} button")
    public void theUserClicksOnTheButton(String buttonName) {
        try {
            By buttonLocator = buttonName.equals("Save")
                    ? By.xpath("//button[contains(@class,'oxd-button oxd-button--medium oxd-button--secondary orangehrm-left-space')]")
                    : By.xpath("//button[@type='button' and text()='Cancel']");

            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(buttonLocator));
            button.click();
        } catch (Exception e) {
            scenario.log("Failed to click " + buttonName + " button: " + e.getMessage());
            throw e;
        }
    }

    // **Newly added step definition for success message 'required' (for when only name is provided)**
    @Then("the user should see a success message {string}")
    public void theUserShouldSeeASuccessMessage(String expectedMessage) {
        try {
            By messageLocator = By.xpath("//span[contains(@class,'oxd-text oxd-text--span oxd-input-field-error-message oxd-input-group__message') and contains(text(),'" + expectedMessage + "')]");

            WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(messageLocator));
            Assert.assertEquals(message.getText(), expectedMessage,
                    "Expected success message does not match the actual message.");
        } catch (Exception e) {
            scenario.log("Failed to verify success message: " + e.getMessage());
            throw e;
        }
    }

    // **Newly added step for handling empty date input (to show "required" message)**
    @When("the user enters holiday details with name {string} and date ''")
    public void theUserEntersHolidayDetailsWithNameAndEmptyDate(String holidayName) {
        try {
            // Wait for and fill holiday name
            WebElement holidayNameField = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//label[text()='Name']/following::input[@class='oxd-input oxd-input--active']")));
            holidayNameField.clear();
            holidayNameField.sendKeys(holidayName);

            // Wait for and leave holiday date empty
            WebElement holidayDateField = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//label[text()='Date']/following::input[@placeholder='yyyy-dd-mm']")));
            holidayDateField.clear(); // Make sure the date field is empty

            // Wait until the loader disappears
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//div[@class='oxd-form-loader']")));
        } catch (Exception e) {
            scenario.log("Failed to enter holiday details with empty date: " + e.getMessage());
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

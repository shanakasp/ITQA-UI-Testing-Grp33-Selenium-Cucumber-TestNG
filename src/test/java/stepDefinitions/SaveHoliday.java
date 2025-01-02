package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.SeleniumUtils;


public class SaveHoliday {

    private SeleniumUtils seleniumUtils;
    private Scenario scenario;

    @Before
    public void setUp(Scenario scenario) {
        this.scenario = scenario;
        seleniumUtils = new SeleniumUtils(scenario);
    }

    // Navigate to Save Holidays page
    @Given("the user navigates to the Save Holidays page")
    public void theUserNavigatesToTheSaveHolidaysPage() {
        seleniumUtils.navigateTo("https://opensource-demo.orangehrmlive.com/web/index.php/leave/saveHolidays");

        seleniumUtils.addDelay(2);
        seleniumUtils.captureScreenshot("NavigateToSaveHolidaysPage");
    }

    // Enter holiday details
    @When("the user enters holiday details with name {string} and date {string}")
    public void theUserEntersHolidayDetails(String holidayName, String holidayDate) {
        seleniumUtils.findElementWithWait(By.xpath("//label[text()='Name']/following::input[@class='oxd-input oxd-input--active']"))
                .sendKeys(holidayName);
        seleniumUtils.findElementWithWait(By.xpath("//label[text()='Date']/following::input[@placeholder='yyyy-dd-mm']"))
                .sendKeys(holidayDate);
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("EnterHolidayDetails");
    }

    // Click on Save or Cancel button
    @And("the user clicks on the {string} button")
    public void theUserClicksOnTheButton(String buttonName) {
        By buttonLocator;
        if (buttonName.equals("Save")) {
            buttonLocator = By.cssSelector("button.oxd-button.oxd-button--medium.oxd-button--secondary.orangehrm-left-space");
        } else if (buttonName.equals("Cancel")) {
            buttonLocator = By.cssSelector("button[type='button']:contains('Cancel')");
        } else {
            throw new IllegalArgumentException("Invalid button name: " + buttonName);
        }
        seleniumUtils.findClickableElement(buttonLocator).click();
        seleniumUtils.addDelay(2);
        seleniumUtils.captureScreenshot(buttonName + "ButtonClick");
    }

    // Verify the success message
    @Then("the user should see a success message")
    public void theUserShouldSeeASuccessMessage() {
        WebElement successMessage = seleniumUtils.findElementWithWait(
                By.xpath("//span[contains(@class,'oxd-text oxd-text--span oxd-input-field-error-message oxd-input-group__message')]"));
        Assert.assertTrue(successMessage.getText().contains("success"), "Expected success message does not match the actual message.");
        seleniumUtils.captureScreenshot("SuccessMessage");
    }

    // Enter holiday details with an empty date
    @When("the user enters holiday details with name {string} and date ''")
    public void theUserEntersHolidayDetailsWithEmptyDate(String holidayName) {
        seleniumUtils.findElementWithWait(By.xpath("//label[text()='Name']/following::input[@class='oxd-input oxd-input--active']"))
                .sendKeys(holidayName);
        seleniumUtils.findElementWithWait(By.xpath("//label[text()='Date']/following::input[@placeholder='yyyy-dd-mm']"))
                .clear();  // Leave the date field empty
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("EnterHolidayDetailsWithEmptyDate");
    }

    // Verify the 'required' message for both fields
    @Then("the user should see a required message near both Name and Date fields")
    public void theUserShouldSeeARequiredMessageNearBothNameAndDateFields() {
        WebElement nameRequiredMessage = seleniumUtils.findElementWithWait(
                By.xpath("//label[text()='Name']/following::span[contains(text(),'required')]"));
        Assert.assertEquals(nameRequiredMessage.getText(), "required", "Expected 'required' message not displayed near Name field.");

        WebElement dateRequiredMessage = seleniumUtils.findElementWithWait(
                By.xpath("//label[text()='Date']/following::span[contains(text(),'required')]"));
        Assert.assertEquals(dateRequiredMessage.getText(), "required", "Expected 'required' message not displayed near Date field.");
        seleniumUtils.captureScreenshot("RequiredMessages");
    }

    // Clean up after scenario
    @After
    public void tearDown() {
        if (seleniumUtils != null) {
            seleniumUtils.quitDriver();
        }
    }
}
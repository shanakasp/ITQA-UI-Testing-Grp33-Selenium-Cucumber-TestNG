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

    @Given("the user navigates to the Save Holidays page")
    public void theUserNavigatesToTheSaveHolidaysPage() {
        seleniumUtils.navigateTo("https://opensource-demo.orangehrmlive.com/web/index.php/leave/saveHolidays");
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("NavigateToSaveHolidaysPage");
    }

    @When("the user enters holiday details with name {string} and date {string}")
    public void theUserEntersHolidayDetails(String holidayName, String holidayDate) {
        seleniumUtils.findElementWithWait(By.xpath("//label[text()='Name']/following::input[@class='oxd-input oxd-input--active']"))
                .sendKeys(holidayName);
        seleniumUtils.findElementWithWait(By.xpath("//label[text()='Date']/following::input[@placeholder='yyyy-dd-mm']"))
                .sendKeys(holidayDate);
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("EnterHolidayDetails");
    }

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
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot(buttonName + "ButtonClick");
    }

    @Then("the user should see a success message")
    public void theUserShouldSeeASuccessMessage() {
        WebElement successMessage = seleniumUtils.findElementWithWait(
                By.xpath("//span[contains(@class,'oxd-text oxd-text--span oxd-input-field-error-message oxd-input-group__message')]"));
        Assert.assertTrue(successMessage.getText().contains("success"), "Expected success message does not match.");
        seleniumUtils.captureScreenshot("SuccessMessage");
    }

    @After
    public void tearDown() {
        if (seleniumUtils != null) {
            seleniumUtils.quitDriver();
        }
    }
}

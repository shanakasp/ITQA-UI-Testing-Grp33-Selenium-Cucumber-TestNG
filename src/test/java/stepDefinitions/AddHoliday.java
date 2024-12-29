package stepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.SeleniumUtils;
public class AddHoliday {

    private SeleniumUtils seleniumUtils;
    private Scenario scenario;


    @Before
    public void setUp(Scenario scenario) {
        this.scenario = scenario;
        seleniumUtils = new SeleniumUtils(scenario);
    }


    @Given("I am on the \"Add Holiday\" page")
    public void userIsOnAddHolidayPage() {
        seleniumUtils.navigateTo("https://opensource-demo.orangehrmlive.com/web/index.php/leave/saveHolidays");
        seleniumUtils.addDelay(2);
        seleniumUtils.captureScreenshot("AddHolidayPage");
    }


    @When("I enter \"{string}\" as the holiday name")
    public void userEntersHolidayName(String holidayName) {
        WebElement holidayNameField = seleniumUtils.findElementWithWait (By.xpath("//label[text()='Name']/following::input[@class='oxd-input oxd-input--active']"));
        holidayNameField.clear();
        holidayNameField.sendKeys(holidayName);
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("EnterHolidayName");
    }


    @When("I enter \"{string}\" as the date")
    public void userEntersDate(String date) {
        WebElement dateField = seleniumUtils.findElementWithWait( By.xpath("//label[text()='Date']/following::input[@placeholder='yyyy-dd-mm']"));
        dateField.clear();
        dateField.sendKeys(date);
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("EnterDate");
    }


    @When("I click the {string} button")
    public void userClicksTheButton(String buttonName) {
        By buttonLocator = buttonName.equals("Save")
                ? By.xpath("//button[contains(@class,'oxd-button--secondary') and text()=' Save ']")
                : By.xpath("//button[contains(@class,'oxd-button') and text()='" + buttonName + "']");


        WebElement button = seleniumUtils.findClickableElement(buttonLocator);
        button.click();
        seleniumUtils.addDelay(2);
        seleniumUtils.captureScreenshot("ClickButton_" + buttonName);
    }




    @Then("I should see the message \"{string}\"")
    public void userShouldSeeTheMessage(String expectedMessage) {
        WebElement successMessage = seleniumUtils.findElementWithWait(By.cssSelector(".oxd-toast-container oxd-toast-message"));
        String actualMessage = successMessage.getText();
        Assert.assertEquals(actualMessage, expectedMessage);
        seleniumUtils.captureScreenshot("SuccessMessage");
        seleniumUtils.quitDriver();
    }


    @Then("I should see an error message \"{string}\"")
    public void userShouldSeeAnErrorMessage(String expectedErrorMessage) {
        WebElement errorMessage = seleniumUtils.findElementWithWait(By.cssSelector(".oxd-text.oxd-text--span.oxd-input-field-error-message.oxd-input-group__message"));
        String actualMessage = errorMessage.getText();
        Assert.assertEquals(actualMessage, expectedErrorMessage);
        seleniumUtils.captureScreenshot("ErrorMessage");
        seleniumUtils.quitDriver();
    }
}



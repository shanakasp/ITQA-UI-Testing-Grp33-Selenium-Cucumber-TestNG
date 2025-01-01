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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    private void takeScreenshot() {
        try {
            // Capture screenshot as byte array
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            byte[] screenshotBytes = screenshot.getScreenshotAs(OutputType.BYTES);

            // Define the file path
            String folderPath = "target/screenshots";
            String fileName = "screenshot_" + System.currentTimeMillis() + ".png";
            String filePath = folderPath + File.separator + fileName;

            // Create the folder if it doesn't exist
            File screenshotDir = new File(folderPath);
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs(); // Create directory if it doesn't exist
            }

            // Write the screenshot to the file
            File screenshotFile = new File(filePath);
            try (FileOutputStream fos = new FileOutputStream(screenshotFile)) {
                fos.write(screenshotBytes); // Save screenshot to file
            }

            // Attach screenshot to the Cucumber report
            scenario.attach(screenshotBytes, "image/png", "Screenshot after Step Execution");

            // Print the path of the saved screenshot for confirmation
            System.out.println("Screenshot saved at: " + filePath);

        } catch (IOException e) {
            scenario.log("Failed to save screenshot: " + e.getMessage());
        }
    }

    @Given("the user navigates to the Save Holidays page")
    public void theUserNavigatesToTheSaveHolidaysPage() {
        try {
            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/leave/saveHolidays");
            wait.until(ExpectedConditions.urlContains("saveHolidays"));
        } catch (TimeoutException e) {
            scenario.log("Failed to navigate to Save Holidays page: " + e.getMessage());
            takeScreenshot();  // Capture screenshot if an error occurs
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
            takeScreenshot();  // Capture screenshot if an error occurs
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
            takeScreenshot();  // Capture screenshot if an error occurs
            throw e;
        }
    }

    @Then("the user should see a success message {string}")
    public void theUserShouldSeeASuccessMessage(String expectedMessage) {
        try {
            By messageLocator = By.xpath("//span[contains(@class,'oxd-text oxd-text--span oxd-input-field-error-message oxd-input-group__message') and contains(text(),'" + expectedMessage + "')]");

            WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(messageLocator));
            Assert.assertEquals(message.getText(), expectedMessage,
                    "Expected success message does not match the actual message.");
        } catch (Exception e) {
            scenario.log("Failed to verify success message: " + e.getMessage());
            takeScreenshot();  // Capture screenshot if an error occurs
            throw e;
        }
    }

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
            takeScreenshot();  // Capture screenshot if an error occurs
            throw e;
        }
    }

    @Then("the user should see a required message near both Name and Date fields")
    public void theUserShouldSeeARequiredMessageNearBothNameAndDateFields() {
        try {
            // **Locate the "required" message for the Name field**
            By nameRequiredMessageLocator = By.xpath("//label[text()='Name']/following::span[contains(text(),'required')]");
            WebElement nameRequiredMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(nameRequiredMessageLocator));
            Assert.assertEquals(nameRequiredMessage.getText(), "required",
                    "Expected 'required' message not displayed near Name field.");

            // **Locate the "required" message for the Date field**
            By dateRequiredMessageLocator = By.xpath("//label[text()='Date']/following::span[contains(text(),'required')]");
            WebElement dateRequiredMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(dateRequiredMessageLocator));
            Assert.assertEquals(dateRequiredMessage.getText(), "required",
                    "Expected 'required' message not displayed near Date field.");
        } catch (Exception e) {
            scenario.log("Failed to verify 'required' message near Name and Date fields: " + e.getMessage());
            takeScreenshot();  // Capture screenshot if an error occurs
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

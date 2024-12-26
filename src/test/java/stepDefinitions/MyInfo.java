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
import java.util.List;

public class MyInfo {
    private WebDriver driver = SeleniumUtils.getDriver();
    private SeleniumUtils seleniumUtils;
    private Scenario scenario;

    @Before
    public void setUp(Scenario scenario) {
        this.scenario = scenario;
        seleniumUtils = new SeleniumUtils(scenario);
    }

    @Given("User navigates to the {string} page")
    public void userNavigatesToThePage(String pageName) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement myInfoLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("My Info")));
            myInfoLink.click();
            seleniumUtils.addDelay(2);

            if (pageName.equals("Personal Details")) {
                WebElement personalDetailsLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Personal Details")));
                personalDetailsLink.click();
            }

            System.out.println("Navigated to " + pageName + " page.");
            seleniumUtils.addDelay(1);
            seleniumUtils.captureScreenshot("Navigated_to_" + pageName);
        } catch (Exception e) {
            System.out.println("Navigation failed: " + e.getMessage());
            seleniumUtils.captureScreenshot("Navigation_Failed");
            throw e;
        }
    }

    @When("User enters {string} in the {string} field")
    public void userEntersValueInField(String value, String fieldName) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement inputField;

            switch (fieldName) {
                case "First Name":
                    inputField = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.cssSelector("input.oxd-input.orangehrm-firstname")));
                    break;
                case "Middle Name":
                    inputField = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.cssSelector("input.oxd-input.orangehrm-middlename")));
                    break;
                case "Last Name":
                    inputField = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.cssSelector("input.oxd-input.orangehrm-lastname")));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field name: " + fieldName);
            }

            // Clear existing text using Ctrl+A and Backspace
            inputField.click();
            if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                inputField.sendKeys(Keys.COMMAND + "a");
            } else {
                inputField.sendKeys(Keys.CONTROL + "a");
            }
            inputField.sendKeys(Keys.BACK_SPACE);

            // Enter new value
            inputField.sendKeys(value);

            System.out.println("Entered '" + value + "' in " + fieldName + " field");
            seleniumUtils.captureScreenshot("Entered_" + fieldName.replace(" ", "_"));

        } catch (Exception e) {
            System.out.println("Failed to enter value in " + fieldName + ": " + e.getMessage());
            seleniumUtils.captureScreenshot("Input_Failed_" + fieldName.replace(" ", "_"));
            throw e;
        }
    }

    @When("User clicks the save button myInfo")
    public void userClicksTheSaveButton() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
            WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button.oxd-button--secondary[type='submit']")
            ));

            saveButton.click();
            System.out.println("Save button clicked successfully");
            seleniumUtils.addDelay(1);
            seleniumUtils.captureScreenshot("Save_Button_Clicked");

        } catch (Exception e) {
            System.out.println("Error clicking save button: " + e.getMessage());
            seleniumUtils.captureScreenshot("Save_Button_Click_Failed");
            throw e;
        }
    }


    @Then("User sees {string} myInfo")
    public void userSeesResult(String expectedResult) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            if (expectedResult.startsWith("Error message:")) {
                // Check for error message
                WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.className("oxd-input-field-error-message")));
                String actualError = errorMessage.getText().trim();
                String expectedError = expectedResult.replace("Error message:", "").trim().replace("\"", "");
                Assert.assertEquals(actualError, expectedError,
                        "Error message mismatch. Expected: " + expectedError + ", Actual: " + actualError);
            } else if (expectedResult.equals("Save successfully")) {
                // Check that no error message is present
                var errorMessages = driver.findElements(By.className("oxd-input-field-error-message"));
                Assert.assertTrue(errorMessages.isEmpty(), "Error message is displayed, success should not occur.");

                // Check for success message
                WebElement successMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.className("oxd-toast-content")));
                Assert.assertTrue(successMessage.isDisplayed(),
                        "Success message not displayed after save operation");
            }

            seleniumUtils.captureScreenshot("Validation_Result");

        } catch (Exception e) {
            System.out.println("Validation failed: " + e.getMessage());
            seleniumUtils.captureScreenshot("Validation_Failed");
            throw e;
        }
    }



    @And("User navigates to the dashboard after checking myInfo")
    public void userNavigatesToTheDashboardAfterCheckingMyInfo() {
        seleniumUtils.navigateTo("https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index");
    }
}
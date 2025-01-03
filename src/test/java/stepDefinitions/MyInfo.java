package stepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.SeleniumUtils;

import java.time.Duration;
import java.util.regex.Pattern;

public class MyInfo {
    private WebDriver driver = SeleniumUtils.getDriver();
    private SeleniumUtils seleniumUtils;
    private Scenario scenario;
    private String lastEnteredFirstName;
    private String lastEnteredMiddleName;
    private String lastEnteredLastName;

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
                    lastEnteredFirstName = value;
                    break;
                case "Middle Name":
                    inputField = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.cssSelector("input.oxd-input.orangehrm-middlename")));
                    lastEnteredMiddleName = value;
                    break;
                case "Last Name":
                    inputField = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.cssSelector("input.oxd-input.orangehrm-lastname")));
                    lastEnteredLastName = value;
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

            // Define name validation pattern (letters, spaces, and hyphens only)
            Pattern namePattern = Pattern.compile("^[a-zA-Z\\s-]+$");

            // Check if any of the entered names contain invalid characters
            boolean isFirstNameValid = namePattern.matcher(lastEnteredFirstName).matches();
            boolean isMiddleNameValid = lastEnteredMiddleName == null || lastEnteredMiddleName.isEmpty() ||
                    namePattern.matcher(lastEnteredMiddleName).matches();
            boolean isLastNameValid = namePattern.matcher(lastEnteredLastName).matches();

            if (expectedResult.equals("Invalid name format")) {
                // Wait for success message to verify the test should fail
                try {
                    WebElement successMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.className("oxd-toast-content")));

                    if (!isFirstNameValid || !isMiddleNameValid || !isLastNameValid) {
                        Assert.fail("Test should fail: Invalid characters were accepted. Success message shown when it shouldn't be.");
                    }
                } catch (TimeoutException e) {
                    // Success message not found, which is expected for invalid input
                    System.out.println("No success message shown for invalid input, as expected.");
                }
            } else if (expectedResult.equals("Save successfully")) {
                // Verify all names are valid before checking success message
                Assert.assertTrue(isFirstNameValid && isMiddleNameValid && isLastNameValid,
                        "Invalid characters found in name fields");

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
}
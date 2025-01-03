package stepDefinitions.Prince_204166X;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.SeleniumUtils;

import java.time.Duration;

public class ChangePasswordStepDefinition {
    private SeleniumUtils seleniumUtils;
    private Scenario scenario;

    @Before
    public void setup(Scenario scenario) {
        this.scenario = scenario;
        seleniumUtils = new SeleniumUtils(scenario);
    }


    @When("User navigates to Change Password")
    public void userNavigatesToChangePassword() {
        WebElement userDropdown = seleniumUtils.findClickableElement(
                By.xpath("//span[@class='oxd-userdropdown-tab']")
        );
        userDropdown.click();
        seleniumUtils.captureScreenshot("UserDropdownOpened");

        WebElement changePasswordLink = seleniumUtils.findClickableElement(
                By.xpath("//a[contains(@class, 'oxd-userdropdown-link') and text()='Change Password']")
        );
        changePasswordLink.click();
        seleniumUtils.captureScreenshot("ChangePasswordPageOpened");
    }

    @And("User enters current password {string}")
    public void userEntersCurrentPassword(String currentPassword) {
        WebElement currentPasswordField = seleniumUtils.findElementWithWait(
                By.xpath("(//input[@type='password'])[1]")
        );
        currentPasswordField.sendKeys(currentPassword);
        seleniumUtils.captureScreenshot("CurrentPasswordEntered");
    }

    @And("User enters new password {string}")
    public void userEntersNewPassword(String newPassword) {
        WebElement newPasswordField = seleniumUtils.findElementWithWait(
                By.xpath("(//input[@type='password'])[2]")
        );
        newPasswordField.sendKeys(newPassword);
        seleniumUtils.captureScreenshot("NewPasswordEntered");
    }

    @And("User confirms new password {string}")
    public void userConfirmsNewPassword(String newPassword) {
        WebElement confirmPasswordField = seleniumUtils.findElementWithWait(
                By.xpath("(//input[@type='password'])[3]")
        );
        confirmPasswordField.sendKeys(newPassword);
        seleniumUtils.captureScreenshot("ConfirmPasswordEntered");
    }

    @Then("User clicks Save button")
    public void userClicksSaveButton() {
        WebElement saveButton = seleniumUtils.findClickableElement(
                By.xpath("//button[@type='submit']")
        );
        saveButton.click();
        seleniumUtils.captureScreenshot("PasswordSavedButtonClicked");
    }

    @Then("User is logged out automatically")
    public void userIsLoggedOutAutomatically() {
        seleniumUtils.addDelay(4); // Wait for any animations/transitions

        // Set a very short timeout just for this check
        seleniumUtils.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

        try {
            // Quick check for Update Password page - if found, fail immediately
            boolean isStillOnUpdatePassword = seleniumUtils.getDriver().findElements(
                    By.xpath("//h6[contains(text(), 'Update Password')]")
            ).isEmpty();

            if (isStillOnUpdatePassword) {
                seleniumUtils.captureScreenshot("FailedToLogout");
                Assert.fail("Password change failed: User was not logged out automatically and is still seeing the Update Password page");
            }
        } finally {
            // Restore normal timeout
            seleniumUtils.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        }

        seleniumUtils.captureScreenshot("LogoutPage");
        seleniumUtils.navigateTo("https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index");
    }



}
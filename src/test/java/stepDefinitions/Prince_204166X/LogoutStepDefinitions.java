package stepDefinitions.Prince_204166X;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.testng.Assert;
import utils.SeleniumUtils;

public class LogoutStepDefinitions {

    private SeleniumUtils seleniumUtils;
    private Scenario scenario;

    @Before
    public void setUp(Scenario scenario) {
        this.scenario = scenario;
        seleniumUtils = new SeleniumUtils(scenario);
    }



    @When("User clicks Profile button check")
    public void clickProfileDropdown() {
        try {
            seleniumUtils.findClickableElement(
                    By.xpath("//i[@class='oxd-icon bi-caret-down-fill oxd-userdropdown-icon']")
            ).click();
            seleniumUtils.addDelay(1);
            seleniumUtils.captureScreenshot("ProfileDropdown");
        } catch (Exception e) {
            seleniumUtils.captureScreenshot("ProfileDropdownError");
            throw e;
        }
    }

    @Then("User clicks on logout button check")
    public void clickLogoutButton() {
        try {
            seleniumUtils.findClickableElement(
                    By.xpath("//a[@role='menuitem' and contains(@href, '/auth/logout')]")
            ).click();
            seleniumUtils.addDelay(2);
            seleniumUtils.captureScreenshot("Logout");
        } catch (Exception e) {
            seleniumUtils.captureScreenshot("LogoutError");
            throw e;
        }
    }

    @Then("User logs out to login page check")
    public void verifyLoginPageAfterLogout() {
        try {
            seleniumUtils.findElementWithWait(By.name("username"));
            boolean isLoginPageReached = seleniumUtils.getCurrentUrl().contains("login");
            Assert.assertTrue(isLoginPageReached, "Logout failed: Not redirected to login page");
            seleniumUtils.addDelay(1);
            seleniumUtils.captureScreenshot("LoginPageAfterLogout");
        } catch (Exception e) {
            seleniumUtils.captureScreenshot("LogoutVerificationError");
            throw e;
        }
    }

    @Then("User clicks back button from browser check")
    public void clickBackButton() {
        try {
            seleniumUtils.getDriver().navigate().back();
            seleniumUtils.addDelay(2);
            seleniumUtils.captureScreenshot("BackButtonClicked");
        } catch (Exception e) {
            seleniumUtils.captureScreenshot("BackNavigationError");
            throw e;
        }
    }

    @When("User should not see {string} text again check")
    public void verifyNoDashboardAccess(String dashboardText) {
        try {
            // Attempt to find dashboard text, which should not be present
            boolean isDashboardPresent = seleniumUtils.getCurrentUrl().contains("dashboard");
            Assert.assertFalse(isDashboardPresent, "Dashboard should not be accessible after logout and going back");

            seleniumUtils.captureScreenshot("LogoutVerification");
        } catch (Exception e) {
            seleniumUtils.captureScreenshot("DashboardAccessError");
            throw e;
        }
    }

    @And("User terminates the process")
    public void terminateProcess() {
        // Close the browser driver
        if (seleniumUtils != null) {
            seleniumUtils.quitDriver();
            // Exit the process after browser closure
            System.exit(0);
        }
    }


    @After
    public void tearDown() {
        if (scenario.isFailed()) {
            seleniumUtils.captureScreenshot("Failure");
            seleniumUtils.quitDriver();


//            System.exit(1);
        }
        seleniumUtils.quitDriver();
    }

}
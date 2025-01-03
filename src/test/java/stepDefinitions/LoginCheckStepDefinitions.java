package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.testng.Assert;
import utils.SeleniumUtils;

public class LoginCheckStepDefinitions {

    private SeleniumUtils seleniumUtils;
    private Scenario scenario;

    @Before
    public void setUp(Scenario scenario) {
        this.scenario = scenario;
        seleniumUtils = new SeleniumUtils(scenario);
    }

    @Given("User is on OrangeHRM login page")
    public void userIsOnLoginPage() {
        seleniumUtils.navigateTo("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        seleniumUtils.addDelay(2);
        seleniumUtils.captureScreenshot("LoginPage");
    }

    @When("User enters username {string}")
    public void userEntersUsername(String username) {
        seleniumUtils.findElementWithWait(By.name("username"))
                .sendKeys(username);
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("EnterUsername");
    }

    @When("User enters password {string}")
    public void userEntersPassword(String password) {
        if (!seleniumUtils.isPasswordValid(password)) {
            System.err.println("Password does not meet criteria.");
            seleniumUtils.captureScreenshot("InvalidPassword");
        }

        seleniumUtils.findElementWithWait(By.name("password"))
                .sendKeys(password);
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("EnterPassword");
    }

    @When("User clicks login button")
    public void userClicksLoginButton() {
        seleniumUtils.findClickableElement(By.cssSelector("button[type='submit']"))
                .click();
        seleniumUtils.addDelay(2);
        seleniumUtils.captureScreenshot("LoginButton");
    }

    @Then("User sees {string}")
    public void userSeesResult(String expectedResult) {
        try {
            if (expectedResult.equals("is redirected to the dashboard")) {
                seleniumUtils.addDelay(2);
                boolean isDashboardReached = seleniumUtils.getCurrentUrl().contains("dashboard");
                Assert.assertTrue(isDashboardReached, "Login failed: Not redirected to dashboard");
                seleniumUtils.captureScreenshot("DashboardSuccess");
            } else if (expectedResult.equals("Invalid credentials")) {
                String actualErrorText = seleniumUtils.findElementWithWait(
                        By.xpath("//div[contains(@class, 'oxd-alert--error')]//p[contains(@class, 'oxd-alert-content-text')]")
                ).getText();
                Assert.assertEquals(actualErrorText, "Invalid credentials", "Unexpected error message.");
                seleniumUtils.captureScreenshot("InvalidCredentialsError");
            } else {
                throw new AssertionError("Unexpected result: " + expectedResult);
            }
        } catch (Exception e) {
            seleniumUtils.captureScreenshot("UnexpectedFailure");
            throw e;
        }
    }


}
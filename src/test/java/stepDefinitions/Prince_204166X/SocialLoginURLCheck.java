package stepDefinitions.Prince_204166X;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.SeleniumUtils;

import java.util.ArrayList;
import java.util.List;

public class SocialLoginURLCheck {
    private SeleniumUtils seleniumUtils;
    private Scenario scenario;
    private WebDriver driver;

    // Expected Social Media URLs
    private static final String TWITTER_URL = "x.com/orangehrm";
    private static final String FACEBOOK_URL = "facebook.com/OrangeHRM";
    private static final String YOUTUBE_URL = "youtube.com/c/OrangeHRMInc";
    private static final String LINKEDIN_URL = "linkedin.com/company/orangehrm";

    @Before
    public void setUp(Scenario scenario) {
        this.scenario = scenario;
        seleniumUtils = new SeleniumUtils(scenario);
        driver = seleniumUtils.getDriver();
    }

    @After
    public void tearDown() {
        if (scenario.isFailed()) {
            seleniumUtils.captureScreenshot("Social_Media_Links_Failure");
        }
        seleniumUtils.quitDriver();
    }

    @Given("User is on OrangeHRM login")
    public void userIsOnOrangeHRMLogin() {
        seleniumUtils.navigateTo("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        // Wait for login page to load
        seleniumUtils.findElementWithWait(By.xpath("//h5[contains(@class, 'orangehrm-login-title') and text()='Login']"));

        scenario.log("Navigated to OrangeHRM login page");
    }

    @When("User clicks on LinkedIn icon")
    public void userClicksOnLinkedInIcon() {
        WebElement linkedinIcon = seleniumUtils.findClickableElement(
                By.xpath("//a[contains(@href, 'linkedin.com')]")
        );
        linkedinIcon.click();
        scenario.log("Clicked on LinkedIn icon");
    }

    @Then("LinkedIn page should open with correct URL")
    public void linkedinPageShouldOpenWithCorrectURL() {
        verifyAndCapturePageScreenshot(LINKEDIN_URL, "LinkedIn");
    }

    @When("User clicks on Facebook icon")
    public void userClicksOnFacebookIcon() {
        WebElement facebookIcon = seleniumUtils.findClickableElement(
                By.xpath("//a[contains(@href, 'facebook.com')]")
        );
        facebookIcon.click();
        scenario.log("Clicked on Facebook icon");
    }

    @Then("Facebook page should open with correct URL")
    public void facebookPageShouldOpenWithCorrectURL() {
        verifyAndCapturePageScreenshot(FACEBOOK_URL, "Facebook");
    }

    @When("User clicks on Twitter icon")
    public void userClicksOnTwitterIcon() {
        WebElement twitterIcon = seleniumUtils.findClickableElement(
                By.xpath("//a[contains(@href, 'twitter.com') or contains(@href, 'x.com')]")
        );
        twitterIcon.click();
        scenario.log("Clicked on Twitter icon");
    }

    @Then("Twitter page should open with correct URL")
    public void twitterPageShouldOpenWithCorrectURL() {
        verifyAndCapturePageScreenshot(TWITTER_URL, "Twitter");
    }

    @When("User clicks on YouTube icon")
    public void userClicksOnYouTubeIcon() {
        WebElement youtubeIcon = seleniumUtils.findClickableElement(
                By.xpath("//a[contains(@href, 'youtube.com')]")
        );
        youtubeIcon.click();
        scenario.log("Clicked on YouTube icon");
    }

    @Then("YouTube page should open with correct URL")
    public void youtubePageShouldOpenWithCorrectURL() {
        verifyAndCapturePageScreenshot(YOUTUBE_URL, "YouTube");
    }

    // Helper method to verify new tab URL and capture screenshot
    private void verifyAndCapturePageScreenshot(String expectedPartialUrl, String platformName) {
        // Wait for new tab to open
        seleniumUtils.addDelay(2);

        // Get all window handles
        List<String> browserTabs = new ArrayList<>(driver.getWindowHandles());

        // Switch to the new tab
        driver.switchTo().window(browserTabs.get(1));

        // Wait for URL to load
        seleniumUtils.addDelay(5); // Increased delay to ensure page fully loads

        // Get current URL
        String currentURL = seleniumUtils.getCurrentUrl();

        // Log current URL for debugging
        scenario.log(platformName + " Current URL: " + currentURL);

        // Verify URL contains expected partial URL
        Assert.assertTrue(currentURL.toLowerCase().contains(expectedPartialUrl.toLowerCase()),
                "URL does not contain expected content. Expected: " + expectedPartialUrl +
                        ", Actual: " + currentURL);

        // Capture screenshot of the page
        seleniumUtils.captureScreenshot(platformName + "_Social_Media_Page");

        // Close the tab and switch back to the original tab
        driver.close();
        driver.switchTo().window(browserTabs.get(0));
    }
}
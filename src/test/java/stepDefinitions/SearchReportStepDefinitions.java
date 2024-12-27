package stepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.SeleniumUtils;

public class SearchReportStepDefinitions {

    private SeleniumUtils seleniumUtils;

    private Scenario scenario;

    @Before
    public void setUp(Scenario scenario) {
        this.scenario = scenario;
        seleniumUtils = new SeleniumUtils(scenario);
    }


    @When("User navigates to Reports module")
    public void userNavigatesToPimModule() {
        seleniumUtils.navigateTo("https://opensource-demo.orangehrmlive.com/web/index.php/pim/viewDefinedPredefinedReports");
        seleniumUtils.addDelay(2);
        seleniumUtils.captureScreenshot("NavigateToReports");
    }

    @And("User enters report name {string}")
    public void userEntersReportName(String reportName) {
        WebElement reportInput = seleniumUtils.findElementWithWait(By.cssSelector("input[placeholder='Type for hints...']"));
        reportInput.sendKeys(reportName);
        seleniumUtils.addDelay(2);
        seleniumUtils.captureScreenshot("EnterReportName");
    }

    @And("User selects a report from suggestions")
    public void userSelectsReportFromSuggestions() {
        WebElement suggestionBox = seleniumUtils.findElementWithWait(By.cssSelector("div[role='listbox']"));

        seleniumUtils.waitForCondition(driver -> !suggestionBox.findElements(By.cssSelector("div")).isEmpty(), 10);

        WebElement firstSuggestion = suggestionBox.findElement(By.cssSelector("div"));
        firstSuggestion.click();

        seleniumUtils.addDelay(2);
        seleniumUtils.captureScreenshot("SelectReport");
    }

    @And("User clicks the search button")
    public void userClicksSearchButton() {
        seleniumUtils.findClickableElement(By.cssSelector("button[type='submit']"))
                .click();
        seleniumUtils.addDelay(3);
        seleniumUtils.captureScreenshot("SearchButton");
    }

    @Then("User verifies the {string} in the table")
    public void userVerifiesReportResults(String expectedResult) {
        if ("0".equals(expectedResult)) {
            WebElement errorMessage = seleniumUtils.findElementWithWait(By.cssSelector("span.oxd-input-field-error-message"));
            String actualErrorText = errorMessage.getText();

            Assert.assertEquals(actualErrorText, "Invalid", "Unexpected error message.");

            seleniumUtils.captureScreenshot("InvalidReportError");
        } else {
            WebElement tableBody = seleniumUtils.findElementWithWait(By.cssSelector("div.oxd-table-body"));
            Assert.assertTrue(tableBody.isDisplayed(), "Table body is not displayed.");

            WebElement firstReport = tableBody.findElement(By.cssSelector("div.oxd-table-row div.oxd-table-cell:nth-child(2) div"));
            String reportText = firstReport.getText();
            Assert.assertNotNull(reportText, "Report text is not found.");
            seleniumUtils.captureScreenshot("VerifyReportResults");
        }
    }

    @And("User navigates back to the dashboard")
    public void userNavigatesToDashboard() {
        seleniumUtils.navigateTo("https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index");
        seleniumUtils.addDelay(2);
        seleniumUtils.captureScreenshot("NavigateToDashboard");
    }
}

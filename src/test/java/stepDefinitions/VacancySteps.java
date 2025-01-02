package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.SeleniumUtils;

import java.time.Duration;

public class VacancySteps {
    private final SeleniumUtils seleniumUtils;
    private final WebDriver driver;
    private final WebDriverWait wait;

    public VacancySteps() {
        seleniumUtils = new SeleniumUtils(null); // Pass Scenario object if needed
        driver = SeleniumUtils.getDriver();
        wait = seleniumUtils.getWait();
    }

    @Given("I navigate to the Recruitment Job Vacancy page")
    public void navigateToJobVacancyPage() {
        seleniumUtils.navigateTo("https://opensource-demo.orangehrmlive.com/web/index.php/recruitment/viewJobVacancy");
    }

    @When("I enter {string} in the Job Title field")
    public void enterJobTitle(String jobTitle) {
        WebElement jobTitleField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Job Title']")));
        jobTitleField.clear();
        jobTitleField.sendKeys(jobTitle);
    }

    @When("I click the {string} button")
    public void clickButton(String buttonName) {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'" + buttonName + "')]")));
        button.click();
    }

    @Then("I should see results matching {string}")
    public void verifySearchResults(String jobTitle) {
        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'" + jobTitle + "')]")));
        assert result.isDisplayed() : "Expected job title not found in search results.";
    }

    @When("I fill in the required fields for a new job vacancy")
    public void fillNewJobVacancyForm(io.cucumber.datatable.DataTable dataTable) {
        var data = dataTable.asMaps(String.class, String.class).get(0);

        WebElement jobTitleField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='jobTitle']")));
        jobTitleField.clear();
        jobTitleField.sendKeys(data.get("Job Title"));

        WebElement hiringManagerField = driver.findElement(By.xpath("//input[@name='hiringManager']"));
        hiringManagerField.clear();
        hiringManagerField.sendKeys(data.get("Hiring Manager"));

        WebElement descriptionField = driver.findElement(By.xpath("//textarea[@name='description']"));
        descriptionField.clear();
        descriptionField.sendKeys(data.get("Description"));
    }

    @Then("I should see {string} in the job vacancies list")
    public void verifyJobVacancyInList(String jobTitle) {
        WebElement jobListing = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'" + jobTitle + "')]")));
        assert jobListing.isDisplayed() : "Expected job vacancy not found in the list.";
    }

    @When("I select the job vacancy {string}")
    public void selectJobVacancy(String jobTitle) {
        WebElement jobRow = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'" + jobTitle + "')]/ancestor::div[contains(@class, 'job-row')]")));
        jobRow.click();
    }

    @When("I update the Hiring Manager to {string}")
    public void updateHiringManager(String newManager) {
        WebElement hiringManagerField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='hiringManager']")));
        hiringManagerField.clear();
        hiringManagerField.sendKeys(newManager);
    }

    @Then("I should see the updated Hiring Manager as {string}")
    public void verifyUpdatedHiringManager(String newManager) {
        WebElement managerCell = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'" + newManager + "')]")));
        assert managerCell.isDisplayed() : "Updated hiring manager not found.";
    }

    @Then("{string} should no longer appear in the vacancies list")
    public void verifyJobVacancyNotInList(String jobTitle) {
        boolean isPresent = driver.findElements(By.xpath("//div[contains(text(),'" + jobTitle + "')]")).size() > 0;
        assert !isPresent : "Job vacancy still appears in the list.";
    }
}

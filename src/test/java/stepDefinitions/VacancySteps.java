package stepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.SeleniumUtils;

import java.util.List;
import java.util.Random;

public class VacancySteps {
    private SeleniumUtils seleniumUtils;
    private WebDriver driver;
    private WebDriverWait wait;
    private Scenario scenario;

    @Before
    public void setUp(Scenario scenario) {
        this.scenario = scenario;
        this.seleniumUtils = new SeleniumUtils(scenario);
        this.driver = seleniumUtils.getDriver();
        this.wait = seleniumUtils.getWait();
    }

    @Given("User clicks on Recruitment in side menu bar for Recruitment")
    public void clickBuzzMenu() {
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("BeforeSearch");

        // Locate the search input
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".oxd-input.oxd-input--active[placeholder='Search']")));
        searchInput.sendKeys("Recruitment");

        seleniumUtils.captureScreenshot("AfterSearchInput");

        // Updated selector to match the actual HTML structure
        WebElement reqMenuItem = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a.oxd-main-menu-item[href='/web/index.php/recruitment/viewRecruitmentModule']")));

        // Try multiple approaches to click the element
        try {
            reqMenuItem.click();
        } catch (ElementClickInterceptedException e) {
            // If direct click fails, try JavaScript click
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", reqMenuItem);
        } catch (Exception e) {
            // If JavaScript click fails, try Actions class
            Actions actions = new Actions(driver);
            actions.moveToElement(reqMenuItem).click().perform();
        }

        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("AfterClickRecruitmentMenu");

        // Updated header verification
        WebElement recruitmentHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".oxd-text.oxd-text--h6.oxd-topbar-header-breadcrumb-module")));
        Assert.assertEquals(recruitmentHeader.getText(), "Recruitment", "Navigation to Recruitment page failed.");

        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("VerifyNavigation");
    }

    @Then("User clicks on vacancies")
    public void user_clicks_on_vacancies() {
        seleniumUtils.captureScreenshot("BeforeVacanciesClick");

        // Updated selector to specifically target the Vacancies link
        WebElement vacanciesTab = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@class, 'oxd-topbar-body-nav-tab-item') and text()='Vacancies']")));
        vacanciesTab.click();

        // Wait for URL to change to ensure navigation
        wait.until(ExpectedConditions.urlContains("/recruitment/viewJobVacancy"));
        seleniumUtils.captureScreenshot("AfterVacanciesClick");
    }


    @And("Click on search button for see all the available jobs")
    public void clickSearchButton() {
        try {
            // Locate the search button
            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button.oxd-button--secondary[type='submit']")));

            // Scroll the button into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", searchButton);

            // Add a small delay to allow scroll to complete
            Thread.sleep(500);

            seleniumUtils.addDelay(1);
            seleniumUtils.captureScreenshot("VacancySearch");

            // Click using JavaScript
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchButton);

            System.out.println("Search button clicked successfully");

        } catch (Exception e) {
            seleniumUtils.captureScreenshot("SearchButtonClickError");
            throw new RuntimeException("Failed to click the search button", e);
        }
    }


    @Given("User clicks on delete for job vacancies")
    public void userClicksOnDeleteForJobVacancies() {
        try {
            // Locate the delete button
            WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button.oxd-icon-button.oxd-table-cell-action-space")));

            // Scroll the button into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", deleteButton);

            // Add a small delay to allow scroll to complete
            Thread.sleep(500);

            // Click the delete button using JavaScript
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteButton);

            System.out.println("Delete button clicked successfully");

        } catch (Exception e) {
            seleniumUtils.captureScreenshot("DeleteButtonError");
            throw new RuntimeException("Failed to click the delete button", e);
        }
    }

    @Then("User confirms delete job vacancy")
    public void userConfirmsDeleteJobVacancy() {
        try {
            // Locate the confirm delete button
            WebElement confirmDeleteButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button.oxd-button--label-danger.orangehrm-button-margin")));

            // Scroll the button into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", confirmDeleteButton);

            // Add a small delay to allow scroll to complete
            Thread.sleep(500);

            // Click the confirm delete button using JavaScript
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", confirmDeleteButton);
            seleniumUtils.captureScreenshot("ConfirmDeleteButtonJobVacancy");
            System.out.println("Confirm delete button clicked successfully");
            seleniumUtils.addDelay(2);
            seleniumUtils.captureScreenshot("AfterConfirmDeleteButtonJobVacancy");
        } catch (Exception e) {
            seleniumUtils.captureScreenshot("ConfirmDeleteButtonError");
            throw new RuntimeException("Failed to click the confirm delete button", e);
        }
    }



}
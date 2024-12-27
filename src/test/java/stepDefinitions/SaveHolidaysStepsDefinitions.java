package stepDefinitions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SaveHolidaysStepsDefinitions {
    WebDriver driver;

    @BeforeAll
    public void setup() {
        // Setup code that will be run before all tests
        ChromeOptions options = new ChromeOptions();
        options.setBinary("/C:\\Users\\Windows\\AppData\\Local\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");  // Path to Brave browser
        driver = new ChromeDriver(options);
    }

    @Given("the user navigates to the Save Holidays page")
    public void the_user_navigates_to_the_save_holidays_page() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/leave/saveHolidays");
    }

    @When("the user enters holiday details with name {string}, date {string}, selects {string}, and repeats annually")
    public void the_user_enters_holiday_details(String holidayName, String holidayDate, String dayType) {
        WebElement holidayNameField = driver.findElement(By.name("holiday[holidayName]"));
        holidayNameField.sendKeys(holidayName);

        WebElement holidayDateField = driver.findElement(By.name("holiday[holidayDate]"));
        holidayDateField.sendKeys(holidayDate);

        WebElement fullDayRadio = driver.findElement(By.id("holiday_fullDay"));
        WebElement halfDayRadio = driver.findElement(By.id("holiday_halfDay"));
        if (dayType.equals("Full Day")) {
            fullDayRadio.click();
        } else {
            halfDayRadio.click();
        }

        WebElement repeatAnnuallyYesRadio = driver.findElement(By.id("holiday_repeatAnnuallyYes"));
        repeatAnnuallyYesRadio.click();
    }

    @And("the user clicks on the {string} button")
    public void the_user_clicks_on_the_button(String buttonName) {
        WebElement button = null;
        if (buttonName.equals("Save")) {
            button = driver.findElement(By.xpath("//button[@type='submit']"));
        } else if (buttonName.equals("Cancel")) {
            button = driver.findElement(By.xpath("//button[@type='button' and text()='Cancel']"));
        }
        button.click();
    }

    @Then("the user should see an error message indicating the name cannot contain numbers")
    public void the_user_should_see_an_error_message() {
        // Assuming the error message appears for invalid holiday name
        WebElement errorMessage = driver.findElement(By.xpath("//span[contains(text(), 'Holiday name cannot contain numbers')]"));
        Assertions.assertTrue(errorMessage.isDisplayed(), "Error message for invalid holiday name with numbers is not displayed.");
    }

    @Then("the user should see a success message {string}")
    public void the_user_should_see_a_success_message(String expectedMessage) {
        WebElement successMessage = driver.findElement(By.xpath("//div[contains(text(),'Successfully Saved')]"));
        Assertions.assertEquals(expectedMessage, successMessage.getText());
    }


    @Then("the user should be redirected back to the Holidays list page")
    public void the_user_should_be_redirected_back_to_the_holidays_list_page() {
        WebElement holidaysList = driver.findElement(By.xpath("//h6[text()='Holidays']"));
        Assertions.assertTrue(holidaysList.isDisplayed());
    }

    @AfterAll
    public void navigateToDashboard() {
        if (driver != null) {
            // Navigate to the dashboard
            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index");
        }
    }

}



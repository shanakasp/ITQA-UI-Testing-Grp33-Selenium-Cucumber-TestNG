package stepDefinitions.extra;

import io.cucumber.java.After;
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

public class AdminCreateEmployee {
    private SeleniumUtils seleniumUtils;
    private WebDriver driver;
    private WebDriverWait wait;
    private Scenario scenario;

    // Scenario variables to store parameterized data
    private String username;
    private String password;
    private String employeeName;
    private String userRole;
    private String status;
    private int initialUserCount;


    @Before
    public void setUp(Scenario scenario) {
        this.scenario = scenario;
        this.seleniumUtils = new SeleniumUtils(scenario);
        this.driver = seleniumUtils.getDriver();
        this.wait = seleniumUtils.getWait();
    }

    @Given("User clicks on Admin in side menu bar")
    public void clickAdminMenu() {
        try {
            // Wait for Admin menu item to be clickable
            WebElement adminMenuItem = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@class, 'oxd-main-menu-item') and @href='/web/index.php/admin/viewAdminModule']")
            ));

            // Capture screenshot before clicking
            seleniumUtils.captureScreenshot("Before_Admin_Menu_Click");

            // Click on Admin menu item
            adminMenuItem.click();

            // Capture screenshot after clicking
            seleniumUtils.captureScreenshot("After_Admin_Menu_Click");

            // Verify navigation to Admin page
            WebElement adminHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h6[contains(@class, 'oxd-topbar-header-breadcrumb-module') and text()='Admin']")
            ));
            Assert.assertTrue(adminHeader.isDisplayed(), "Failed to navigate to Admin page");

        } catch (Exception e) {
            scenario.log("Error in clicking Admin menu: " + e.getMessage());
            seleniumUtils.captureScreenshot("Admin_Menu_Click_Error");
            throw new RuntimeException("Failed to click Admin menu", e);
        }
    }
    @Then("User checks current user count")
    public void user_checks_current_user_count() {
        try {
            // Log the step for debugging
            scenario.log("Attempting to check the user count.");

            // Wait for the user count element to be visible
            WebElement userCountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[contains(@class, 'oxd-text--span') and contains(text(), 'Records Found')]")
            ));

            // Log element presence
            scenario.log("User count element located.");

            // Extract the text content
            String countText = userCountElement.getText();
            scenario.log("Raw user count text: " + countText);

            // Parse the count
            initialUserCount = Integer.parseInt(countText.replaceAll("[^0-9]", ""));
            scenario.log("Parsed user count: " + initialUserCount);

            // Capture screenshot for verification
            seleniumUtils.captureScreenshot("Initial_User_Count");

        } catch (NoSuchElementException e) {
            scenario.log("User count element not found: " + e.getMessage());
            seleniumUtils.captureScreenshot("User_Count_Element_Not_Found");
            throw new RuntimeException("Failed to locate user count element", e);

        } catch (NumberFormatException e) {
            scenario.log("Error parsing user count: " + e.getMessage());
            seleniumUtils.captureScreenshot("User_Count_Parse_Error");
            throw new RuntimeException("Failed to parse user count text", e);

        } catch (Exception e) {
            scenario.log("Unexpected error: " + e.getMessage());
            seleniumUtils.captureScreenshot("User_Count_Error");
            throw new RuntimeException("Failed to check user count", e);
        }
    }


    @Then("User clicks delete on the records table")
    public void user_clicks_delete_on_the_records_table() {
        try {
            // Wait for the delete button in the second row
            WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//button[.//i[contains(@class, 'bi-trash')]])[2]")
            ));

            // Capture screenshot before clicking delete
            seleniumUtils.captureScreenshot("Before_Delete_Button_Click");

            // Click the delete button
            deleteButton.click();

            // Wait for the confirmation popup
            WebElement confirmDeletePopup = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class, 'oxd-modal-container')]")
            ));
            Assert.assertTrue(confirmDeletePopup.isDisplayed(), "Delete confirmation popup did not appear");

            // Capture screenshot of delete confirmation popup
            seleniumUtils.captureScreenshot("Delete_Confirmation_Popup");

        } catch (Exception e) {
            scenario.log("Error clicking delete button: " + e.getMessage());
            seleniumUtils.captureScreenshot("Delete_Button_Click_Error");
            throw new RuntimeException("Failed to click delete button", e);
        }
    }


    @Then("User clicks confirm delete on popup")
    public void user_clicks_confirm_delete_on_popup() {
        try {
            // Wait for the Yes, Delete button
            WebElement confirmDeleteButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'oxd-button--label-danger') and contains(text(), 'Yes, Delete')]")
            ));

            // Capture screenshot before confirming delete
            seleniumUtils.captureScreenshot("Before_Confirm_Delete");

            // Click confirm delete
            confirmDeleteButton.click();

            // Wait for success toast message
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class, 'oxd-toast-container')]//div[contains(@class, 'oxd-toast-message')]")
            ));
            Assert.assertTrue(successMessage.isDisplayed(), "Success message not shown after deletion");

            // Add a small delay to ensure page updates
            seleniumUtils.addDelay(2);

            // Capture screenshot after delete confirmation
            seleniumUtils.captureScreenshot("After_Confirm_Delete");

        } catch (Exception e) {
            scenario.log("Error confirming delete: " + e.getMessage());
            seleniumUtils.captureScreenshot("Confirm_Delete_Error");
            throw new RuntimeException("Failed to confirm delete", e);
        }
    }


    @Then("User checks relevant user delete or not")
    public void user_checks_relevant_user_delete() {
        try {
            // Wait for the user count text element
            WebElement userCountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[contains(@class, 'oxd-text--span') and contains(text(), 'Records Found')]")
            ));

            // Extract the number of records
            String countText = userCountElement.getText();
            int currentCount = Integer.parseInt(countText.replaceAll("[^0-9]", ""));

            // Verify that the count has decreased by 1
            Assert.assertEquals(currentCount, initialUserCount - 1, "User count did not decrease as expected");

            scenario.log("Initial user count: " + initialUserCount + ", Current user count: " + currentCount);

            // Capture screenshot of updated user count
            seleniumUtils.captureScreenshot("Updated_User_Count");

        } catch (Exception e) {
            scenario.log("Error checking user count after deletion: " + e.getMessage());
            seleniumUtils.captureScreenshot("User_Count_Verification_Error");
            throw new RuntimeException("Failed to verify user deletion", e);
        }
    }

    @Then("User clicks edit on the records table")
    public void user_clicks_edit_on_the_records_table() {
        try {
            // Wait for the edit button to be clickable
            WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'oxd-icon-button') and .//i[contains(@class, 'bi-pencil-fill')]]")
            ));

            // Capture screenshot before clicking
            seleniumUtils.captureScreenshot("Before_Edit_Button_Click");

            // Click the edit button
            editButton.click();

            // Wait for the Edit User page to load
            WebElement editUserTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h6[contains(@class, 'orangehrm-main-title') and text()='Edit User']")
            ));
            Assert.assertTrue(editUserTitle.isDisplayed(), "Edit User page did not load");

            // Capture screenshot after clicking
            seleniumUtils.captureScreenshot("After_Edit_Button_Click");

        } catch (Exception e) {
            scenario.log("Error in clicking edit button: " + e.getMessage());
            seleniumUtils.captureScreenshot("Edit_Button_Click_Error");
            throw new RuntimeException("Failed to click edit button", e);
        }
    }

    @Then("User changes user name in admin edit to {string}")
    public void user_changes_username_in_admin_edit(String newUsername) {
        try {
            // Wait for the username input field
            WebElement usernameInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//label[contains(@class, 'oxd-input-field-required') and text()='Username']" +
                            "/ancestor::div[contains(@class, 'oxd-input-group')]" +
                            "//input[contains(@class, 'oxd-input--active')]")
            ));

            // Add explicit wait
            Thread.sleep(500);

            // Select all text and delete
            usernameInput.sendKeys(Keys.CONTROL + "a");
            usernameInput.sendKeys(Keys.DELETE);

            // Additional clear to ensure field is empty
            usernameInput.clear();

            // Add another short delay
            Thread.sleep(300);

            // Enter new username
            usernameInput.sendKeys(newUsername);

            // Capture screenshot after changing username
            seleniumUtils.captureScreenshot("Username_Changed");
        } catch (Exception e) {
            scenario.log("Error changing username: " + e.getMessage());
            seleniumUtils.captureScreenshot("Username_Change_Error");
            throw new RuntimeException("Failed to change username", e);
        }
    }

    @Then("User clicks save button on Edit User Page")
    public void user_clicks_save_button_on_edit_user_page() {
        try {
            // Wait for the Save button to be clickable
            WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@type='submit' and contains(@class, 'oxd-button--secondary') and contains(text(), 'Save')]")
            ));

            // Scroll to the Save button to ensure it's in view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", saveButton);

            // Optional: Highlight the button for debugging
            ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", saveButton);

            // Capture a screenshot before clicking
            seleniumUtils.captureScreenshot("Before_Save_Button_Click");

            // Click the Save button
            saveButton.click();

            // Wait for the "System Users" page to load
            WebElement systemUsersTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h5[contains(@class, 'oxd-table-filter-title') and text()='System Users']")
            ));
            Assert.assertTrue(systemUsersTitle.isDisplayed(), "System Users page did not load after clicking Save");

            // Capture a screenshot after clicking
            seleniumUtils.captureScreenshot("After_Save_Button_Click");

        } catch (Exception e) {
            scenario.log("Error in clicking the Save button: " + e.getMessage());
            seleniumUtils.captureScreenshot("Save_Button_Click_Error");
            throw new RuntimeException("Failed to click the Save button", e);
        }

    }


        @Then("User clicks on the Add button in Admin Page")
    public void clickAddButton() {
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class, 'oxd-button--secondary') and .//i[contains(@class, 'bi-plus')]]")
        ));
        addButton.click();
        seleniumUtils.addDelay(2);
        seleniumUtils.captureScreenshot("After_Add_Button_Click");
    }

    @Then("User should see the Admin User management page")
    public void verifyAddUserPage() {
        WebElement addUserTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h6[contains(@class, 'orangehrm-main-title') and text()='Add User']")
        ));
        Assert.assertTrue(addUserTitle.isDisplayed(), "Add User page not loaded");
        seleniumUtils.captureScreenshot("Add_User_Page");
    }

    @When("User selects user role as {string}")
    public void selectUserRole(String userRole) {
        this.userRole = userRole;
        WebElement userRoleDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//div[contains(@class, 'oxd-select-text-input')])[1]")
        ));
        userRoleDropdown.click();

        WebElement roleOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'oxd-select-option')]/span[text()='" + userRole + "']")
        ));
        roleOption.click();
        seleniumUtils.addDelay(1);
    }

    @When("User sets the employee name to {string}")
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
        WebElement employeeNameInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[@placeholder='Type for hints...']")
        ));
        employeeNameInput.sendKeys(employeeName);
        seleniumUtils.addDelay(2);

        WebElement suggestionOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'oxd-autocomplete-option')]/span")
        ));
        suggestionOption.click();
    }

    @When("User selects status as {string}")
    public void selectStatus(String status) {
        this.status = status;
        WebElement statusDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//div[contains(@class, 'oxd-select-text-input')])[2]")
        ));
        statusDropdown.click();

        WebElement statusOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'oxd-select-option')]/span[text()='" + status + "']")
        ));
        statusOption.click();
    }

    @When("User sets the username to {string}")
    public void setUsername(String username) {
        this.username = username;
        WebElement usernameInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//input[contains(@class, 'oxd-input')])[2]")
        ));
        usernameInput.sendKeys(username);
    }

    @When("User sets the password to {string}")
    public void setPassword(String password) {
        this.password = password;
        WebElement passwordInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//input[@type='password'])[1]")
        ));
        passwordInput.sendKeys(password);
    }

    @When("User confirms the password as {string}")
    public void confirmPassword(String password) {
        WebElement confirmPasswordInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//input[@type='password'])[2]")
        ));
        confirmPasswordInput.sendKeys(password);
    }

    @When("User clicks the Save button")
    public void clickSaveButton() {
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@type='submit']")
        ));
        saveButton.click();
        seleniumUtils.addDelay(3);
        seleniumUtils.captureScreenshot("After_Save_Button_Click");
    }

    @Then("User should be redirected to the System Users page")
    public void verifySystemUsersPage() {
        WebElement breadcrumb = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h6[contains(@class, 'oxd-topbar-header-breadcrumb-module') and text()='Admin']")
        ));
        Assert.assertTrue(breadcrumb.isDisplayed(), "System Users page not loaded");
        seleniumUtils.captureScreenshot("System_Users_Page");
    }

    @Then("User should see a success message")
    public void verifySaveSuccessMessage() {
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'oxd-toast-container')]//div[contains(@class, 'oxd-toast-message')]")
        ));
        Assert.assertTrue(successMessage.isDisplayed(), "Success message not shown");
        seleniumUtils.captureScreenshot("Success_Message");
    }

    @After
    public void tearDown() {
        // Navigate to dashboard instead of closing the browser
        seleniumUtils.navigateTo("https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index");
        seleniumUtils.captureScreenshot("FinalDashboardScreen");
    }
}
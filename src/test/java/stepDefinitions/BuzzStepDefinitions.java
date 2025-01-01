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

public class Buzz {
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

    @Given("User clicks on Buzz in side menu bar")
    public void clickBuzzMenu() {
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("BeforeSearch");

        // Locate the search input
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".oxd-input.oxd-input--active[placeholder='Search']")));
        searchInput.sendKeys("Buzz");

        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("AfterSearchInput");

        // Click on the first result in the side menu
        WebElement buzzMenuItem = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".oxd-main-menu-item-wrapper .oxd-main-menu-item[href='/web/index.php/buzz/viewBuzz']")));
        buzzMenuItem.click();

        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("AfterClickBuzzMenu");

        // Verify navigation by checking the header
        WebElement buzzHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".oxd-text.oxd-text--h6.oxd-topbar-header-breadcrumb-module")));
        Assert.assertEquals(buzzHeader.getText(), "Buzz", "Navigation to Buzz page failed.");

        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("VerifyNavigation");
    }

    @When("User clicks Most Recent Posts")
    public void clickMostRecentPosts() {
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("BeforeClickMostRecentPosts");

        WebElement mostRecentButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".orangehrm-post-filters-button .bi-clock-history")));
        mostRecentButton.click();

        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("AfterClickMostRecentPosts");

        // Ensure page remains on Buzz
        verifyBuzzPage();
    }

    @When("User clicks Most Liked Posts")
    public void clickMostLikedPosts() {
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("BeforeClickMostLikedPosts");

        WebElement mostLikedButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".orangehrm-post-filters-button .bi-heart-fill")));
        mostLikedButton.click();

        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("AfterClickMostLikedPosts");

        // Ensure page remains on Buzz
        verifyBuzzPage();
    }

    @When("User clicks Most Commented Posts")
    public void clickMostCommentedPosts() {
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("BeforeClickMostCommentedPosts");

        WebElement mostCommentedButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".orangehrm-post-filters-button .bi-chat-dots-fill")));
        mostCommentedButton.click();

        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("AfterClickMostCommentedPosts");

        // Ensure page remains on Buzz
        verifyBuzzPage();
    }

    private void verifyBuzzPage() {
        WebElement buzzHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".oxd-text.oxd-text--h6.oxd-topbar-header-breadcrumb-module")));
        Assert.assertEquals(buzzHeader.getText(), "Buzz", "Unexpectedly navigated away from Buzz page.");
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("VerifyBuzzPage");
    }

    @When("User add post {string}")
    public void addPost(String postContent) {
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("BeforeAddingPost");

        // Locate the text area and ensure it is focused
        WebElement postTextArea = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".oxd-buzz-post-input[placeholder=\"What's on your mind?\"]")));

        postTextArea.click(); // Click to focus on the text area
        seleniumUtils.addDelay(1); // Short delay to ensure the focus is set
        postTextArea.sendKeys(postContent); // Enter the post content

        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("AfterEnteringPostContent");
    }


    @Then("User clicks on add post button")
    public void clickAddPostButton() {
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("BeforeClickingAddPostButton");

        // Click on the add post button
        WebElement addPostButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".oxd-button.oxd-button--medium.oxd-button--main[type='submit']")));
        addPostButton.click();

        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("AfterClickingAddPostButton");
    }

    @When("User clicks three dots button")
    public void clickThreeDotsButton() {
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("BeforeClickingThreeDots");

        // Locate and click the three dots button
        WebElement threeDotsButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".oxd-icon-button .bi-three-dots")));
        threeDotsButton.click();

        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("AfterClickingThreeDots");
    }

    @And("User click edit post from dropdown")
    public void clickEditPostFromDropdown() {
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("BeforeClickingEditPost");

        // Locate and click the Edit Post option from dropdown
        WebElement editPostOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//p[text()='Edit Post']/..")));
        editPostOption.click();

        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("AfterClickingEditPost");
    }

    @And("Edit post like {string}")
    public void editPostContent(String newPostContent) {
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("BeforeEditingPost");

        // Locate the text area, clear existing content, and enter new post content
        WebElement editPostTextArea = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".oxd-buzz-post--composing .oxd-buzz-post-input")));
        editPostTextArea.clear();
        editPostTextArea.sendKeys(newPostContent);

        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("AfterEditingPostContent");
    }

    @Then("Click on Post button")
    public void clickEditPostButton() {
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("BeforeClickingEditPostButton");

        // Click on the Post button
        WebElement editPostButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".oxd-form-actions.orangehrm-buzz-post-modal-actions .oxd-button.oxd-button--medium.oxd-button--main[type='submit']")));
        editPostButton.click();

        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("AfterClickingEditPostButton");
    }

    @When("User add heart to a post")
    public void addHeartToPost() {
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("BeforeAddingHeart");

        try {
            // Locate and click the heart button
            WebElement heartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".orangehrm-heart-icon")));
            heartButton.click();
            seleniumUtils.addDelay(1);
            seleniumUtils.captureScreenshot("AfterAddingHeart");
        } catch (Exception e) {
            // Log the exception and continue to the next step
            System.out.println("Failed to add heart to post: " + e.getMessage());
            // Optionally, capture a screenshot or add further error logging
            seleniumUtils.captureScreenshot("HeartButtonError");
        }


        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("ProceedingToNextStepAfterHeartButton");
    }


    @When("User add comment to a post")
    public void addCommentToPost() {
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("BeforeAddingComment");
        WebElement commentButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".oxd-icon.bi-chat-text-fill")));
        commentButton.click();
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("AfterAddingComment");
    }

    @Then("User writes comment {string}")
    public void writeComment(String comment) {
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("BeforeWritingComment");

        try {
            // Use a more robust locator strategy
            WebElement commentInput = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//input[@placeholder='Write your comment...']")
            ));

            // Clear any existing text first
            commentInput.clear();

            // Send the comment
            commentInput.sendKeys(comment);

            // Press Enter key
            commentInput.sendKeys(Keys.ENTER);

            seleniumUtils.addDelay(1);
            seleniumUtils.captureScreenshot("AfterWritingComment");
        } catch (Exception e) {
            // Log the error or take appropriate action
            System.out.println("Failed to write comment: " + e.getMessage());
            throw e;
        }
    }

    @And("User click delete post from dropdown")
    public void clickDeletePostFromDropdown() {
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("BeforeClickingDeletePost");

        // Locate and click the Edit Post option from dropdown
        WebElement editPostOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//p[text()='Delete Post']/..")));
        editPostOption.click();

        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("AfterClickingDeletePost");
    }


    @Then("User confirms delete comment")
    public void confirmDeleteComment() {
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("BeforeConfirmingDeleteComment");
        WebElement confirmDeleteButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(., 'Yes, Delete')]")));
        confirmDeleteButton.click();
        seleniumUtils.addDelay(1);
        seleniumUtils.captureScreenshot("AfterConfirmingDeleteComment");
    }
}

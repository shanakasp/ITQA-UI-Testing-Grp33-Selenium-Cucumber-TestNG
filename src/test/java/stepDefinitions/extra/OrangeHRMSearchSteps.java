package stepDefinitions.extra;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.SeleniumUtils;

import java.util.List;
import java.util.Map;

public class OrangeHRMSearchSteps {
    private SeleniumUtils seleniumUtils;

    public OrangeHRMSearchSteps(SeleniumUtils seleniumUtils) {
        this.seleniumUtils = seleniumUtils;
    }

    @Then("User performs multiple searches")
    public void performMultipleSearches(DataTable searchTable) {
        List<Map<String, String>> searches = searchTable.asMaps(String.class, String.class);

        for (Map<String, String> searchData : searches) {
            String searchTerm = searchData.get("searchTerm");
            String expectedModule = searchData.get("expectedModule");

            // Find and interact with search input
            WebElement searchInput = seleniumUtils.findElementWithWait(
                    By.cssSelector("input[placeholder='Search']")
            );

            // Clear any existing text and enter search term
            searchInput.clear();
            searchInput.sendKeys(searchTerm);

            // Wait for search results
            seleniumUtils.addDelay(2);

            // Find and click on the search result
            By searchResultLocator = By.xpath(
                    "//a[contains(@class, 'oxd-main-menu-item') and .//span[contains(text(), '" + searchTerm + "')]]"
            );
            WebElement searchResult = seleniumUtils.findClickableElement(searchResultLocator);
            searchResult.click();

            // Verify the page navigation and module header
            By moduleHeaderLocator = By.cssSelector("h6.oxd-text.oxd-text--h6.oxd-topbar-header-breadcrumb-module");
            WebElement moduleHeader = seleniumUtils.findElementWithWait(moduleHeaderLocator);

            // Take screenshot for each search step
            seleniumUtils.captureScreenshot("Search_" + searchTerm);

            // Verify the module header matches the expected module
            Assert.assertEquals(
                    moduleHeader.getText(),
                    expectedModule,
                    "Module header does not match expected module for search: " + searchTerm
            );

            // Navigate back to dashboard if not the last search
            if (searches.indexOf(searchData) < searches.size() - 1) {
                // Assuming there's a dashboard/home button
                WebElement dashboardButton = seleniumUtils.findClickableElement(
                        By.cssSelector("a[href='/web/index.php/dashboard/index']")
                );
                dashboardButton.click();
            }
        }
    }
}
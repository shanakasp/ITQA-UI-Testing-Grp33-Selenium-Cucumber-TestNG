package stepDefinitions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.SeleniumUtils;

public class BuzzStepDefinitions {
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

}
}



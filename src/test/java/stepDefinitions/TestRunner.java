package stepDefinitions;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepDefinitions",
        plugin = {
                "pretty",
                "json:target/cucumber.json",
                "html:target/cucumber-reports/cucumber.html"
        },tags = "@LoginPageURLCheck or @Login or @SaveHoliday"
        //tags = "@LoginPageURLCheck or @Login or @ChangePassword or @BuzzComplete or  @SearchReport or @LogoutCheck or @InputValidationMyInfo or @SaveHoliday"




)
public class TestRunner extends AbstractTestNGCucumberTests {
        @Override
        @DataProvider(parallel = false)
        public Object[][] scenarios() {
                return super.scenarios();
        }
}


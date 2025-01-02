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
                "html:target/cucumber-reports/cucumber.html",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        tags =  "@LoginPageURLCheck or @Login or @ChangePassword or @InputValidationMyInfo or @SearchReport or @SaveHoliday or @JobVacancy or @BuzzComplete or @LogoutCheck"

        //          tags =  "@LoginPageURLCheck or @Login or @ChangePassword or @InputValidationMyInfo or @SearchReport or @SaveHoliday or @JobVacancy or @BuzzComplete or @LogoutCheck"
)
public class TestRunner extends AbstractTestNGCucumberTests {
        @Override
        @DataProvider(parallel = false)
        public Object[][] scenarios() {
                return super.scenarios();
        }
}
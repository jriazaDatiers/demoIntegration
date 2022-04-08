package StepDefinitions;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin={"json:target/cucumber-reports/cucumber.json"},
        features = {"src/test/resources/features"},
        tags = "@TEST_MSFDEM-63 or @TEST_MSFDEM-57"
)
public class RunCucumberTest {
}

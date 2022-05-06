package StepDefinitions;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin={"junit:target/cucumber-reports/cucumber.xml"},
        features = {"src/test/resources/features"},
        tags = "@TEST_MSFDEM-126"
)
public class RunCucumberTest {
}

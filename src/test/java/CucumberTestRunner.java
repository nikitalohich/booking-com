import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.AfterClass;
import util.DriverFactory;
import testutil.Screenshoter;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "steps",
        plugin = {"pretty", "com.epam.reportportal.cucumber.ScenarioReporter"}
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {

    @AfterClass
    public void tearDown() {
        DriverFactory.getInstance().getDriver().quit();
        Screenshoter.clean();
    }
}
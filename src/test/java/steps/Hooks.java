package steps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import testutil.Screenshoter;

public class Hooks {

    @After
    public void takeScreenshotForFailure(Scenario scenario) {
        if (scenario.isFailed()) {
            Screenshoter.take();
        }
    }

}

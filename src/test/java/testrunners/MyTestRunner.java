package testrunners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"src/test/java/AppFeatures"},
		glue = {"steps"},
		plugin = {"pretty",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
		},
		monochrome=true,
		tags= "@regression"
		)

public class MyTestRunner {

}

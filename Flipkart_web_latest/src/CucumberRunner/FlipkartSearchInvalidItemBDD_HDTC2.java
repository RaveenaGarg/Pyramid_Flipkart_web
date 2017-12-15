package CucumberRunner;

import org.junit.runner.RunWith;
import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.TestNGCucumberRunner;

@RunWith(Cucumber.class)
@CucumberOptions (
//		monochrome = true,
		//features = "featureFile/HomeDepotInvalid.feature",
		features = "featureFile/Flipkart.feature", 
		plugin = {"pretty", "html:target/cucumber-pretty","json:target/cucumber.json"},
		glue = ""
		
		)


public class FlipkartSearchInvalidItemBDD_HDTC2 {
	
	
	@Test
	public void RunCukes()
	{
		new TestNGCucumberRunner(getClass()).runCukes();
	}

}

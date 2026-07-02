package cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features="src/test/java/cucumber",glue="rahulshettyacademy.StepDefinitions" //features->location of feature files, glue=location of stepdefinitions.java, monochrome=true makes cucumber reports readable
		,monochrome=true,plugin= {"html:target/cucumber.html"}, //plugin={"key(html/json report format): "location of report to be generated"}
		tags="@Regression"    //only runs regression tagged tcs/scenarios
		)              
public class TestNGTestRunner extends AbstractTestNGCucumberTests{ //extend this AbstractTESTNGCucumberTests so that cucumber will be able to execute testng tests, you can iignore if not using testng/if using junit(cucumber comes inbuilt with junit)

	
	//can run in jenkins also add all profiles from pom.xml configure project and click build now
	//can run profiles in maven by this cmd mvn test -PCucumberTests  -P profile -D parameters like browserName=Chrome

}

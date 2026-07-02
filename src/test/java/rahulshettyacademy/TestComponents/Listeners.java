package rahulshettyacademy.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import rahulshettyacademy.resources.ExtentTestReporterNG;

//Important add <listeners> <listner
public class Listeners extends BaseTest implements ITestListener { //ITestListener from TESTNG extends BaseTest to use screenshot code 
	
	ExtentReports extent=ExtentTestReporterNG.getReporterObject();
	ExtentTest test;
	ThreadLocal<ExtentTest> tloc=new ThreadLocal<ExtentTest>(); //we are sending ExtentTest
	
	    @Override
	    public void onTestStart(ITestResult result) {
	    	
	     test=extent.createTest(result.getMethod().getMethodName());  //we need to create this ExtentTest object for each testcase
	     tloc.set(test); //Pass the ExtentTest object 'test'  In Thread Local a Map with key(unique thread id of the test) & value eg: Map1(threadid:SubmitOrderTest) Map2(threadid:ErrorValidationsTest)
	    }

	    @Override
	    public void onTestSuccess(ITestResult result) {
	       //test.log(Status.PASS, "This Test Passed");
	    }

	    @Override
	    public void onTestFailure(ITestResult result) {
	        //System.out.println("Test Failed: " + result.getName());
	        // Example: take screenshot here
	    	
	    	String screenshotPath = null;
	    	
	    
	    	//test.fail(result.getThrowable()); //getThrowable prints the exception or error message
	    	tloc.get().fail(result.getThrowable()); //using threadlocal safe class instead of test
	    
	    		try {
					driver= (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());  //itestresult. getTestClass from testng.xml . get the real java class. get the field called driver's value from the intance/testmethod it is being run on
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	    	
	    	
	    	
	    	
	    	//Take a Screenshot + Attach to Report
	    	try {
				screenshotPath=getScreenshot(result.getMethod().getMethodName(), driver);
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
	    	
	    	//test.addScreenCaptureFromPath(screenshotPath,result.getMethod().getMethodName());
	    	tloc.get().addScreenCaptureFromPath(screenshotPath, result.getMethod().getMethodName());//using threadlocal safe class instead of test
	    }

	    @Override
	    public void onTestSkipped(ITestResult result) {
	      
	    }

	    @Override
	    public void onStart(ITestContext context) {
	        
	    }

	    @Override
	    public void onFinish(ITestContext context) {
	       extent.flush(); //Important for generating the reports 
	    }

}

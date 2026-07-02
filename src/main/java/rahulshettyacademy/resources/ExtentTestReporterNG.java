package rahulshettyacademy.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentTestReporterNG {
	
	public static ExtentReports getReporterObject() {  //if we give static we ca call the method directly by using classname no need to create object i.e, ExtentTestReporterNG.getReporterObject();
		
		String filePath=System.getProperty("user.dir")+"\\reports\\index.html";
		
		ExtentSparkReporter reporter=new ExtentSparkReporter(filePath);
		reporter.config().setReportName("Web Automation Results");
		reporter.config().setDocumentTitle("Test Results");
		
		ExtentReports extent=new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Rahul Shetty");
		
		return extent; //returning ExtentReports extent object
		
	}

}

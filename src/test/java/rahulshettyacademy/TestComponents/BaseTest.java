package rahulshettyacademy.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageobjects.LoginPage;

public class BaseTest {  //all pageobjects extend AbstractComponents similarly all Testcases in src/test/java extends BaseTest.. BaseTestClass is used for handling invocation,browser setup,launching app,etc
	
	public WebDriver driver; //keep this public/protected for ez access
	public LoginPage loginPg; //this variable can be used by child class since child class extends parent class give access modifier public
	
	public WebDriver initializeDriver() throws IOException {
		
		Properties prop =new Properties();	//System.getProperty("user.dir") gets this path ="D:\\eclipse-java-2026-03-R-win32-x86_64\\eclipse\\Selenium_Workspace\\SeleniumFrameworkDesign" it gets the path of the Project
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\rahulshettyacademy\\resources\\GlobalData.properties");
		prop.load(fis);
		
		//String browserName=prop.getProperty("browser");//this gets the property from globaldata.properties file
		
		//mvn test -Dbrowser=Firefox -PPurchase here -D stands for maven system parameter and -P is used for profile -D & -P should be capital Note:We have to be in D:\eclipse-java-2026-03-R-win32-x86_64\eclipse\Selenium_Workspace\SeleniumFrameworkDesign> folder to execute the code
		//Ternary operator here we are checking if we are passing browser from mvn commandline argument System.getProperty gets that value else if it is null we use prop.getProperty to get value from properties file
		String browserName= System.getProperty("browser")!=null ? System.getProperty("browser") : prop.getProperty("browser");
		//if(browserName.equalsIgnoreCase("chrome")) {
		if(browserName.contains("chrome")) {   //checking if cmdline contains chromeword
			WebDriverManager.chromedriver().setup();
			ChromeOptions options=new ChromeOptions();
			if(browserName.contains("headless")) {   //checking if command contains headless
				options.addArguments("headless");
			}
			driver=new ChromeDriver(options); //if headless launch chrome in headless else options will be empty & chrome will launch normally
			driver.manage().window().setSize(new Dimension(1440,900)); //if headless mode execution, some elements may not appear properly so setting the size or resolution to fullscreen mode
		}
		else if(browserName.equalsIgnoreCase("firefox")) {
			//firefoxcode
			System.setProperty("webdriver.gecko.driver", "D:\\Selenium-Rahul Shetty\\geckodriver-v0.36.0-win64\\geckodriver.exe");
			driver=new FirefoxDriver(); //Assign Firefox driver
		}
		else if(browserName.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edgedriver", "D:\\Selenium-Rahul Shetty\\edgedriver_win64\\msedgedriver.exe");
			driver=new EdgeDriver();
		}
		
		//after initializing the driver then you can maximize
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		
		return driver;
	}
	
	@BeforeMethod(alwaysRun=true) //need to set alwaysRun=True so that this method will always run even though we are running only 1 single group
	public LoginPage launchApplication() throws IOException {  //LoginPage=LandingPage
		
		driver=initializeDriver();  //we need to pass driver object below,we are creating/initializing the driver in initializeDriver() above
		
		loginPg=new LoginPage(driver);
		loginPg.goToURL();
		return loginPg;
	}
	
	@AfterMethod(alwaysRun=true) //need to set alwaysRun=True so that this method will always run even though we are running only 1 single group
	public void tearDown() {
		System.out.println("Closing browser from Parent Class @AfterMethod");
		//driver.quit();
		driver.close();
	}
	
	public List<HashMap<String,String>> getJsonDatatoMap(String file) throws IOException { //method to convert json data file to a hashmap
		
		//step1: convert the json file to a String
		//String jsonContent=FileUtils.readFileToString(new File(System.getProperty("user.dir")+"\\src\\test\\java\\rahulshettyacademy\\data\\PurchaseOrder.json"),StandardCharsets.UTF_8);
		String jsonContent=FileUtils.readFileToString(new File(file),StandardCharsets.UTF_8); //can send any file from submitorderpom test to be converted to json not just purchaseorder.json
		
		//Step 2:Add Jackson Data bind dependency and convert the String from step1(jsonContent) to a HashMap or List<HashMap<String,String>>
		ObjectMapper mapper=new ObjectMapper();
		List<HashMap<String,String>> data=mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>() {});//write correct syntax
		
		return data;  //output will be [ {map1,map2} ] list of jsons
		
		
	}
	
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		
		TakesScreenshot ts=(TakesScreenshot) driver;
		File src=ts.getScreenshotAs(OutputType.FILE);
		File dest=new File(System.getProperty("user.dir")+"//reports//"+testCaseName+".png"); //eg:D:\eclipse-java-2026-03-R-win32-x86_64\eclipse\Selenium_Workspace\SeleniumFrameworkDesign\SubmitOrderTest.png
		FileUtils.copyFile(src, dest);
		return System.getProperty("user.dir")+"//reports//"+testCaseName+".png";//returns the file path of the created screenshot //testCaseName=@Test method name eg:SubmitOrderTest
	}//ExtentReports seperate section not part of the framework covered in first 20 min in next lecture, later we see how to integrate/attach the screenshot to html/extent reports
	
	

}

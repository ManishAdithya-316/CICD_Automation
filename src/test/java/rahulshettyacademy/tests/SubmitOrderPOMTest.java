package rahulshettyacademy.tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.LoginPage;
import rahulshettyacademy.pageobjects.MyOrders;
import rahulshettyacademy.pageobjects.OrderConfirmationPage;
import rahulshettyacademy.pageobjects.ProductCatalog;

public class SubmitOrderPOMTest extends BaseTest{ //write main test code under src/test/java and page objects,utilities,etc under src/main/java
	
	String productName="ZARA COAT 3"; //global class variable which can be accessed by all test methods
	
	//public static void main(String[] args) throws InterruptedException { //Since launchApplication() is a non static method we can't use psvm() so convert it/make it TestNG
		
	@Test(dataProvider="getData",groups= {"Purchase"})                      //@BeforeMethod & @AfterMethod methods automatically get executed since they are in Parent Class
	//public void SubmitOrderTest(String username,String password, String pdtName) throws InterruptedException, IOException { 
	public void SubmitOrderTest(HashMap<String,String> input) throws InterruptedException {
		
		//maven-quistart-archetype -> GroupId means compnany name, artifact id means project folder/project name
		//remove dependency management & just keep all dependencies under <dependencies>
	
		
		//WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
		
		//@BeforeMethod handles these 2 lines so need to create loginPg object
		//LoginPage lp=launchApplication(); //Since launchApplication() is a non static method we can't use psvm() so make it TestNG LandingPage.. we need to pass driver object
		//lp.goToURL();
		
								//loginPg variable is coming from Parent class BaseTest & can be directly accessed since it is public
		//ProductCatalog pdtCatalog=loginPg.loginToApp("adithya.hulugundi@test.com", "Shiva@316");
		//ProductCatalog pdtCatalog=loginPg.loginToApp(username, password); //parameterizing using dataprovider
		ProductCatalog pdtCatalog=loginPg.loginToApp(input.get("email"), input.get("password")); //parameterizing using dataprovider,using HashMap
		
		
		
		//ProductCatalog pdtCatalog=new ProductCatalog(driver); not needed since we are returning/encapsulating this page in LoginPage's loginToApp() method
		//List<WebElement> products=pdtCatalog.getProductsList(); //same as 4 lines of code WebDriverWait wait =new WebDriverWait(driver,Duration.ofSeconds(5)); wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));//waiting for a single element to load / can use below step also////wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".mb-3")));
		 														//List<WebElement> products=driver.findElements(By.cssSelector(".mb-3"));
		//pdtCatalog.addPdtToCart(productName);
		//pdtCatalog.addPdtToCart(pdtName); //parameterizing using dataprovider
		pdtCatalog.addPdtToCart(input.get("productName"));
	CartPage cartpg=pdtCatalog.clickGoToCartBtn_Header(); //calling directly from Parent AbstractComponents.java(Inheritance) no need to create seperate method in ProductCatalogPage Note since Cart button is in header it is accessible/common to by all page objects so place it in AbstractComponents and use inheritance
		
		
		//CartPage cartpg=new CartPage(driver); no need since it returned above by AbstractComponents class
		//Boolean match=cartpg.checkCartProductVisibility(productName);
		Boolean match=cartpg.checkCartProductVisibility(input.get("productName")); //use pdtName for data provider input.get("keyinquotes")
		
		Assert.assertTrue(match);//Tip:All validations & assertions to be in main test all action methods in PageObjects 
		
		CheckoutPage chkPg=cartpg.clickCheckout();
		
		//CheckoutPage chkPg=new CheckoutPage(driver); no need since it is returned by cart page
		chkPg.selectIndiaOption();
		OrderConfirmationPage oc=chkPg.clickPlaceOrder();
		
		//OrderConfirmationPage oc=new OrderConfirmationPage(driver); no need since it is returned by CheckoutPage
		
		String confirmMsg=oc.getConfirmationMsg();
		Assert.assertTrue(confirmMsg.equalsIgnoreCase("THANKYOU FOR THE ORDER."));//in html <h1> is  Thankyou for the order. </h1> where as in screen it is THANKYOU FOR THE ORDER. Selenium checks what is displayed on the screen not the html tag.. so just use equalsIgnoreCase for safety
	
	}
	
	@Test(dependsOnMethods= {"SubmitOrderTest"}) //this test runs only after SubmitOrderTest method
	public void checkOrderHistory() {
		
		ProductCatalog pdtCatalog=loginPg.loginToApp("adithya.hulugundi@test.com", "Shiva@316");
		MyOrders myOrdersPg=pdtCatalog.clickOrdersHeaderBtn(); //From any page object we can click on Orders btn since it is defined in AbstractComponents and each PageObject child class extends AbstractComponents
		
		Assert.assertTrue(myOrdersPg.verifyOrdersDisplay(productName));//same as these 2 lines:boolean match=myOrdersPg.verifyOrdersDisplay(productName); //Assert.assertTrue(match);
		
	}
	

	
//1)Directly returning  testdata in Object[][]
//	@DataProvider 
//	public Object[][] getData() {
//		return new Object[][] { {"adithya.hulugundi@test.com","Shiva@316","ZARA COAT 3"}, {"arjun.mehta@example.com","Test@1234","ADIDAS ORIGINAL"} }; //return new Object[][] { {dataset1} , {dataset2} }
//	}
	

//2)Using HashMap putting the data to a hashmap and putting map1,map2 in Object[][]	
//	@DataProvider
//	public Object[][] getData() {
//		HashMap<String,String> map1=new HashMap<String,String>();
//		map1.put("email","adithya.hulugundi@test.com");
//		map1.put("password", "Shiva@316");
//		map1.put("productName", "ZARA COAT 3");
//		
//		HashMap<String,String> map2=new HashMap<String,String>();
//		map2.put("email", "arjun.mehta@example.com");
//		map2.put("password", "Test@1234");
//		map2.put("productName", "ADIDAS ORIGINAL");
//		
//		return new Object[][] { {map1},{map2} };
//		
//	}
	
	
	//3)Using External Json file Tip:Run purchase.xml in testSuites package in src/main/java
	@DataProvider
	public Object[][] getData() throws IOException {
												//just pass file string & call the method from parent class BaseTest
		List<HashMap<String,String>> data=getJsonDatatoMap(System.getProperty("user.dir")+"\\src\\test\\java\\rahulshettyacademy\\data\\PurchaseOrder.json");//don't use new File (new File(System.getProperty("user.dir")+"\\src\\test\\java\\rahulshettyacademy\\data\\PurchaseOrder.json")
		
		return new Object[][] { {data.get(0)},{data.get(0)} };
		
	}
	
	
}	

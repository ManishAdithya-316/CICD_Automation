package rahulshettyacademy.tests;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.LoginPage;
import rahulshettyacademy.pageobjects.OrderConfirmationPage;
import rahulshettyacademy.pageobjects.ProductCatalog;

import rahulshettyacademy.TestComponents.RetryListener;


//Note: If we have 100 testcases we do not need to write 100 java classes we group testcases like 5tcs for LoginPageError validations 1 class for it then 15 for submit order test etc
public class ErrorValidationsTest extends BaseTest{ //write main test code under src/test/java and page objects,utilities,etc under src/main/java
	
	
		
	@Test(groups= {"ErrorValidations"},retryAnalyzer=RetryListener.class)                      //@BeforeMethod & @AfterMethod methods automatically get executed since they are in Parent Class
	//manually import import rahulshettyacademy.TestComponents.RetryListener; we have to use this parameter retryAnalyzer in all flaky tests which we want to retry again

	public void LoginErrorMsgTest() throws InterruptedException, IOException { 
		
		//loginPg variable is coming from Parent class BaseTest & can be directly accessed since it is public
		//ProductCatalog pdtCatalog=loginPg.loginToApp("adithya.hulugundi@test.com", "Shiva@316"); No need of ProductCatalog pdtCatalog bcoz we don't need it for this test 
		//String productName="ZARA COAT 3"; this also not needed we are just validating the loginage error message
		
		loginPg.loginToApp("wrongEmail@test.com", "wrongpassword");
		
		Assert.assertEquals("Incorrect email or password.", loginPg.getLoginErrorMsgTxt() );
		
		//Assert.assertEquals("Icoect mail  password.", loginPg.getLoginErrorMsgTxt());//will fail and take screenshot in itestlistener code and can check the report
		
	}
	
	@Test
	public void CheckoutProductErrorTest() throws InterruptedException {
		
		ProductCatalog pdtCatalog=loginPg.loginToApp("arjun.mehta@example.com", "Test@1234");//note we use 2 different emails 1)adithya.hulugundi@test.com/Shiva@316 2)arjun.mehta@example.com/Test@1234 to test parrelel execution since we can't add same product to cart from same account in 2 different places
		String productName="ZARA COAT 3";
		pdtCatalog.addPdtToCart(productName);
		CartPage cartpg=pdtCatalog.clickGoToCartBtn_Header();
		
		Boolean match=cartpg.checkCartProductVisibility("ZARAA COAT 333");//Boolean match=cartpg.checkCartProductVisibility(productName); passing invalid productName
	}
	
}	

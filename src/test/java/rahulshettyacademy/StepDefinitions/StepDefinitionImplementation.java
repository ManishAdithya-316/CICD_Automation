package rahulshettyacademy.StepDefinitions;

import java.io.IOException;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.LoginPage;
import rahulshettyacademy.pageobjects.OrderConfirmationPage;
import rahulshettyacademy.pageobjects.ProductCatalog;

public class StepDefinitionImplementation extends BaseTest{ //BaseTest contains commonly used methods
	
	//making all page objects accessible to all methods using public
	public LoginPage loginPg;
	public ProductCatalog pdtCatalog;
	public CartPage cartpg;
	public CheckoutPage chkPg;
	public OrderConfirmationPage oc_pg;
	
	//note all stepdefinitions are case sesitive
	@Given("I have launched the application and landed on ecommerce webpage")  //#Background step executes befor every test/scenario method similar to TestNG @BeforeMethod
	public void I_have_launched_the_app() throws IOException {
		loginPg=launchApplication();
	}
	
	@Given("^I have logged in with username (.+) and password (.+)$") //Here (.+) means regular expression data you can use {string} also but (.+) expects any datatype. Whenever we are using regular expressions like (.+) it is recommended to use ^ & $ at start and end of the sentence
	public void i_have_logged_in_with_username_and_password(String username,String password) { //do not forget parameters from feature file
		pdtCatalog=loginPg.loginToApp(username, password); 
	}
	
	@When("^I add the product (.+) to the Cart$")
	public void i_add_product_to_Cart(String product_name) throws InterruptedException { 
		pdtCatalog.addPdtToCart(product_name);
	}
	
	@When("^Checkout (.+) visibility in the cart and submit the order$")  //you can use @And or @When / any previous keyword like Given,When,Then And is used for Positive tcs and But is used for negative testcases like But error message is displayed
	public void checkout_visibility_in_cart_submit_order(String product_name) {
		CartPage cartpg=pdtCatalog.clickGoToCartBtn_Header();
		Boolean match=cartpg.checkCartProductVisibility(product_name); 
		
		Assert.assertTrue(match);
		
		chkPg=cartpg.clickCheckout();
		
		
		chkPg.selectIndiaOption();
		oc_pg=chkPg.clickPlaceOrder();
	}
	
	@Then("verify {string} message is displayed in ConfirmationPage") //Note here since data is static "Message" & not coming from Examples keyword you can't use (.+) also no need for ^$ you can directly use datatype in {} ie, {string}
	public void verify_confirmation_msg(String msg) {
		String confirmMsg=oc_pg.getConfirmationMsg();
		Assert.assertTrue(confirmMsg.equalsIgnoreCase(msg));
		driver.close();
	}
	
	@Then("verify that {string} error message is displayed")
	public void verify_error_msg(String msg) {
		Assert.assertEquals("Incorrect email or password.", loginPg.getLoginErrorMsgTxt() );
		driver.close();
	}

}

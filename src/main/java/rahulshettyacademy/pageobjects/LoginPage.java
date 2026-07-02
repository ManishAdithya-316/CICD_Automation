package rahulshettyacademy.pageobjects;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.abstractcomponents.AbstractComponents;

public class LoginPage extends AbstractComponents{  //LandingPage  
	
	WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		//This constructor will be the first code which runs when object is initialized
		
		super(driver); //we need to pass the driver object to AbstractComponents parent class from every child class using super();
		
		this.driver=driver;      //this.driver refers to the class variable
		
		PageFactory.initElements(driver, this);  //use this driver to assign/initialize @FindBy objects 
	}
	
	//can ctrl+click FindBy class to view locator syntax
	@FindBy(id="userEmail")
	WebElement userEmail; //these 2 lines same as driver.findElement(By.id("userEmail")), PageFactory automattically constructs this on the fly bcoz we give initElements() in constructor instead of using driver.findElement(By....) every time

	@FindBy(id="userPassword")
	WebElement password;
	
	@FindBy(id="login")
	WebElement loginBtn;
	
	
	@FindBy(css="[class*='flyInOut']")             //@FindBy(id="toast-container") doesnt work there is one more div element which contains error msg which appears & disappears
	WebElement loginErrorMsg;
	
	
	//Action methods
	public ProductCatalog loginToApp(String email,String userPassword) {  //pass values to page object from the main test
		userEmail.sendKeys(email);
		password.sendKeys(userPassword);
		waitForElementToBeClickable(loginBtn);
		try {
		loginBtn.click();   //after we click login button new Page called ProductCatalog will be displayed we are returning that pageobject from here
		}catch(ElementClickInterceptedException e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",loginBtn);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginBtn);
		}
		ProductCatalog pc=new ProductCatalog(driver);
		return pc;
		
	}
	
	public void goToURL() {
		driver.get("https://rahulshettyacademy.com/client/");
	}
	
	public String getLoginErrorMsgTxt() {
		waitForWebElementToAppear(loginErrorMsg);
		return loginErrorMsg.getText();  //return the errorMsg for validations
	}
	
}



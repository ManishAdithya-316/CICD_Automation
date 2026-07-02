package rahulshettyacademy.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.abstractcomponents.AbstractComponents;

public class CheckoutPage extends AbstractComponents {
	
	WebDriver driver;
	
	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//input[@placeholder='Select Country']")
	private WebElement countryText;        //We are using encapsulation by setting webelement fields private & action methods public so that even though we create object of the page(Eg: Checkout page) we must not be able to access it's fields(WebElements)
	//(Eg:CheckoutPage cp=new CheckoutPage(driver); cp.placeOrderBtn.click();X) we should only access the designed action methods to prevent misuse of WebElements i.e, placing order by just clicking webelement instead of calling placeOrder() method which returns a page object
	
	//@FindBy(css="section.ta-results")
	//WebElement countrySuggestions;
	
	private By countrySuggestions=By.cssSelector("section.ta-results");
	
	@FindBy(xpath="(//button[contains(@class,'ta-item')])[2]")
	private WebElement indiaOption;
	
	@FindBy(css=".action__submit")
	private WebElement placeOrderBtn;
	
	public void selectIndiaOption() {
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")),"Ind").build().perform();   //a.sendKeys(WebElement,keystosend) same as sendkeys //build().perform() mandatory
		
		waitForElementToAppear(countrySuggestions);
		indiaOption.click();
			
	}
	
	public OrderConfirmationPage clickPlaceOrder() {
		placeOrderBtn.click(); //after click place order btn we return OrderConfirmationPage object
		OrderConfirmationPage oc=new OrderConfirmationPage(driver);
		return oc;
	}
	

	
}

package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.abstractcomponents.AbstractComponents;

public class CartPage extends AbstractComponents {

	WebDriver driver;
	
	public CartPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css=".cartSection h3")
	List<WebElement> cartProducts;
	
	@FindBy(css=".totalRow button")
	WebElement checkoutBtn;
	
	public boolean checkCartProductVisibility(String pdtName) {
		boolean match_flag=cartProducts.stream().anyMatch(prod->prod.getText().equalsIgnoreCase(pdtName));
		return match_flag;
	}
	
	public CheckoutPage clickCheckout() {
		try {
		checkoutBtn.click();
		}catch(ElementClickInterceptedException e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",checkoutBtn);
		}
		CheckoutPage chkpg=new CheckoutPage(driver); //creating & returning CheckoutPage class pg object bcoz clicking on checkoutbtn goes to checkoutpage tip:don't forget to pass driver 
		return chkpg;
	}
}

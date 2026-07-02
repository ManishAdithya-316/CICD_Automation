package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.abstractcomponents.AbstractComponents;

public class ProductCatalog extends AbstractComponents {
	
	WebDriver driver;
	
	public ProductCatalog(WebDriver driver) {
		
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	//List<WebElement> products=driver.findElements(By.cssSelector(".mb-3"));
	@FindBy(css=".mb-3")  //use correct locators
	List<WebElement> products;   //use List<WebElement> for multiple elements
	
	@FindBy(css=".ng-animating")  //use page factory when we have driver.findElement(By.cssSelector(".ng-animating")) if we have only By locator then we just use By abc=By.cssSelector("abc")
	WebElement spinner;
	
	By productsBy=By.cssSelector(".mb-3"); //By class object/locator, same as above used in Parent  //use correct locators .mb-3 not .mb3
	
	By reqdPdt=By.cssSelector("b");  //use By when webdriver scope is limited to webelement & uses By locator eg: product.findElement(By.cssSelector("b"))
	By addToCartBtn=By.cssSelector("button:last-of-type");
	By toastMsg=By.cssSelector("#toast-container");
	
	public List<WebElement> getProductsList() {
		waitForElementToAppear(productsBy);  //passing by locator to the method
		return products;   //returning products list after waiting for them to appear
	}
	
	public WebElement getProductByName(String productName) {
		
		WebElement prod=getProductsList().stream().filter(product->product.findElement(reqdPdt)  //can use products.stream() here directly also but inside getProductsList() method we are waiting then returning products list
				.getText().equals(productName)).findFirst().orElse(null);
		return prod;
		
	}
	
	public void addPdtToCart(String productName) throws InterruptedException {
		
		waitForElementToAppear(addToCartBtn);
		waitForElementToBeClickable(getProductByName(productName).findElement(addToCartBtn));
		try {
			getProductByName(productName).findElement(addToCartBtn).click();//prod.findElement(By.cssSelector("button:last-of-type")).click();  //here we are calling above method getProductByName("ZARA COAT3")  from here not from the main SubmitOrderPOMTest
	    } catch (ElementClickInterceptedException e) {
	        // Fallback: force JS click
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", getProductByName(productName).findElement(addToCartBtn));
	    }
		
		
		waitForElementToAppear(toastMsg); //expects By locator
		waitForElementToDisappear(spinner); //expects WebElement
		
	}
	
	//public void goToCartPage() { //calling directly from Parent AbstractComponents.java(Inheritance) no need to create seperate method in ProductCatalogPage Note since Cart button is in header it is accessible/common to by all page objects so place it in AbstractComponents and use inheritance
	//	clickGoToCartBtn_Header();
	//}
	

}

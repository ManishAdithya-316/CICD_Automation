package rahulshettyacademy.abstractcomponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.MyOrders;

public class AbstractComponents { //Class which can contain common/reusable methods like switchingFrames,switching windows,handling alerts, waits,etc

	WebDriver driver;
	
	public AbstractComponents(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="[routerlink*='cart']")
	WebElement goToCartBtn; 
	
	@FindBy(css="[routerlink*='myorders']")
	WebElement ordersBtn;
		
	
	
	//WebDriverWait wait =new WebDriverWait(driver,Duration.ofSeconds(5));
	//wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
	public void waitForElementToAppear(By findBy) {  //here findBy is a variable which is of class By locator
		
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));  //visibilityOfElementLocated(findBy locator)
	}
	
	
	public void waitForWebElementToAppear(WebElement ele) {  //here ele is a variable which is a WebElement (driver.findElement(By.cssSelector(".mb3")));
		
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(ele));  //.visibilityOf(WebELEMENT)
	}
	
	public void waitForElementToDisappear(WebElement ele) throws InterruptedException {
		
		
		/*wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating"))); //invisibilityOfElementLocated(By.xpath,etc)*/
		/*wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating")))); //invisibilityOf(WebElement or driver.findElement(By.xpath,etc))*/
		
		//Backend there is additonal spinner, webdriver waits for this spinner to handle load/load all services in the backend for 4 sec.. to avoid waiting don't rely on the spinner & use Thread.sleep(1000);
		//WebDriverWait wait2=new WebDriverWait(driver,Duration.ofSeconds(5));
		//wait2.until(ExpectedConditions.invisibilityOf(ele));  //here we pass webelement not Bylocator
		
		Thread.sleep(1000); //Add Throws declaration everywhere..	
		
	}
	
	public void waitForElementToBeClickable(WebElement ele) { //Method Overriding here WebElement is used
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.elementToBeClickable(ele));
	}
	
	public void waitForElementToBeClickable(By ele) { //Method Overriding here By locator is used
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.elementToBeClickable(ele));
	}
	
	
	public CartPage clickGoToCartBtn_Header() { //calling directly from Parent AbstractComponents.java(Inheritance) no need to create seperate method in ProductCatalogPage Note since Cart button is in header it is accessible/common to by all page objects so place it in AbstractComponents and use inheritance
		waitForElementToBeClickable(goToCartBtn);
		try {
			goToCartBtn.click();
	    } catch (ElementClickInterceptedException e) {
	        // Fallback: force JS click
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",goToCartBtn);//clicking on Cart button goes to CartPage so we are returning CartPage Object here
	    }
		CartPage cartpg=new CartPage(driver);
		return cartpg;
	}
	
	public MyOrders clickOrdersHeaderBtn() {
		waitForElementToBeClickable(ordersBtn);
		try {
		ordersBtn.click(); //clicking on Orders button goes to MyOrders page so we are returning MyOrders Object here
		}
		catch (ElementClickInterceptedException e) {
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", ordersBtn);
		}
		MyOrders myOrders=new MyOrders(driver);
		return myOrders;
	}
	
}

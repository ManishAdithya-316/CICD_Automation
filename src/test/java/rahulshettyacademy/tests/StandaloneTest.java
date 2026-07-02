package rahulshettyacademy.tests;

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

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageobjects.LoginPage;

public class StandaloneTest { //write main test code under src/test/java and page objects,utilities,etc under src/main/java
	
	public static void main(String[] args) {
		
		//maven-quistart-archetype -> GroupId means compnany name, artifact id means project folder/project name
		//remove dependency management & just keep all dependencies under <dependencies>
		
		WebDriverManager.chromedriver().setup();//is part of the WebDriverManager library by Boni García. It’s a convenience tool that automatically manages browser driver binaries for Selenium.
		//1)Detects your Chrome version installed on the system.2)Downloads the matching ChromeDriver binary (if not already present).
		//3)Sets the system property webdriver.chrome.driver internally, so you don’t have to manually point to chromedriver.exe.4)Keeps drivers updated — no more mismatches between browser and driver versions.
		WebDriver driver=new ChromeDriver();
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get("https://rahulshettyacademy.com/client/");
		
		LoginPage lp=new LoginPage(driver); //LandingPage.. we need to pass driver object
		
		driver.findElement(By.id("userEmail")).sendKeys("adithya.hulugundi@test.com");    //or arjun.mehta@example.com/Test@1234
		driver.findElement(By.id("userPassword")).sendKeys("Shiva@316");
		driver.findElement(By.id("login")).click();
		
		String productName="ZARA COAT 3";
		
		WebDriverWait wait =new WebDriverWait(driver,Duration.ofSeconds(5));
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));//waiting for a single element to load / can use below step also
		//wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".mb-3")));
		List<WebElement> products=driver.findElements(By.cssSelector(".mb-3"));
		
		WebElement prod=products.stream().filter(product->product.findElement(By.cssSelector("b"))
				.getText().equals(productName)).findFirst().orElse(null);                             //from products list find the first product/webelement which has zara coat 3 text else return null
		prod.findElement(By.cssSelector("button:last-of-type")).click(); //.mb-3 button:last-of-type last-of-type means ignore the first button (view) & take/find the last button
	
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container"))); //Waiting for Product added successfully toast msg.. we can inspect it quickly
		//ng-animating class used for loading icon waiting for it to disappear
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating"))); //invisibilityOfElementLocated(By.xpath,etc)
		//wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating")))); //invisibilityOf(WebElement or driver.findElement(By.xpath,etc))
	
		wait.until(ExpectedConditions.invisibilityOfElementLocated( By.cssSelector(".ngx-spinner-overlay")));//Element click interception wait for this to be invisible
		
		//clicking on cart button using regular expression [attribute *(contains) = 'partialtextvalue' ]
		WebElement cartBtn=wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[routerlink*='cart']")));//after waiting for overlay to be invisible wait for cart button to be clickable then click it to avoid errors
		//driver.findElement(By.cssSelector("[routerLink*='cart']")).click();
		try {
		cartBtn.click();
		}catch(ElementClickInterceptedException e) {
			wait.until(ExpectedConditions.invisibilityOfElementLocated( By.cssSelector(".ngx-spinner-overlay")));//Element click interception wait for this to be invisible
			cartBtn.click(); // retry once overlay is gone
		}
		
		
		List<WebElement> cartProducts=driver.findElements(By.cssSelector(".cartSection h3"));
	Boolean match=	cartProducts.stream().anyMatch(cartProduct->cartProduct.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match); //checking if there is anyMatch or any cart item ith name ZARA COAT 3 use anyMatch() if you want to just check condition and filter() if you want to capture/return webelement
		
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Select Country']")));
		//WebElement countryTxt=driver.findElement(By.xpath("//input[@placeholder='Select Country']"));
		//countryTxt.sendKeys("Ind");
		//driver.findElement(By.xpath("(//span[contains(text(),'India')])[2]")).click();		
		//driver.findElement(By.xpath("//a[contains(text(),'Place Order')]")).click();
		
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")),"Ind").build().perform();   //a.sendKeys(WebElement,keystosend) same as sendkeys //build().perform() mandatory
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("section.ta-results")))); //TagName.classname note:just waiting until results section is displayed no need to wait for all the individual result elements eg:India,British,etc
		
		//css = button.ta-item:nth-child(3) or xpath= (//button[contains(@class,'ta-item')])[2]
		driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();       //clicking on India option
		
		driver.findElement(By.cssSelector(".action__submit")).click();  //clicking on Place Order button
		
		String confirmMsg=driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMsg.equalsIgnoreCase("THANKYOU FOR THE ORDER."));//in html <h1> is  Thankyou for the order. </h1> where as in screen it is THANKYOU FOR THE ORDER. Selenium checks what is displayed on the screen not the html tag.. so just use equalsIgnoreCase for safety
		
		
		
		
	}
	
}	

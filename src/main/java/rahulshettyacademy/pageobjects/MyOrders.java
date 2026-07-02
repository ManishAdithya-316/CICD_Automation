package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.abstractcomponents.AbstractComponents;

public class MyOrders extends AbstractComponents{
	
	public WebDriver driver;
	
	public MyOrders(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(css="tr td:nth-child(3)") //use doublequotes not single
	List<WebElement> orderHistoryItems;
	
	public boolean verifyOrdersDisplay(String productName) {
		Boolean match=orderHistoryItems.stream().anyMatch(item->item.getText().equalsIgnoreCase(productName));//stream().anyMatch() checks for matches
		return match;
	}

}

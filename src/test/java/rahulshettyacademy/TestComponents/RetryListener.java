package rahulshettyacademy.TestComponents;

import java.io.IOException;
import java.util.HashMap;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.Test;

public class RetryListener implements IRetryAnalyzer {  //@Test(groups= {"ErrorValidations"},retryAnalyzer=RetryListener.class)   public void LoginErrorMsgTest() throws InterruptedException, IOException { code }
	//manually import import rahulshettyacademy.TestComponents.RetryListener; we have to use this parameter retryAnalyzer=className in all flaky tests which we want to retry again
	int count=0;
	int maxTry=1;
	@Override
	public boolean retry(ITestResult result) { //This code will execute as long as output is true
		// TODO Auto-generated method stub
		
		if(count<maxTry) {
			
			count++;
			return true;  
		}
		
		return false;
	}

}

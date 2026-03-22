package com.w2a.testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;

public class BankManagerLoginTest extends TestBase{

   @Test
   public void loginAsBankManager() throws InterruptedException {
	   log.debug("Bank manager Login sucessfully");
	   
	   driver.findElement(By.xpath(OR.getProperty("BtmLogin"))).click();
	   Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addcustomerbutton"))), "Login Not Successful");
			
		}

	

	

}

package com.w2a.testcases;

import java.util.Hashtable;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.w2a.base.TestBase;

public class AddCustomerTest extends TestBase {

	@Test(dataProvider = "getData")
	public void addCustomerTest(Hashtable<String, String> data) throws InterruptedException {

		// Step 1: Click on Add Customer Button
		driver.findElement(By.cssSelector(OR.getProperty("addcustomerbutton"))).click();

		// Step 2: Optimized Data Input (No more manual trim here)
		driver.findElement(By.xpath(OR.getProperty("firstname"))).sendKeys(data.get("first name")); 
		driver.findElement(By.xpath(OR.getProperty("lastname"))).sendKeys(data.get("last name"));
		driver.findElement(By.xpath(OR.getProperty("postcode"))).sendKeys(data.get("post code"));
		driver.findElement(By.xpath(OR.getProperty("postcode"))).sendKeys(data.get("post code"));

		// Step 3: Click Add Button
		driver.findElement(By.xpath(OR.getProperty("addbutton"))).click();

		// Step 4: Professional Alert Handling
		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Thread.sleep(5000);
		alert.accept();
		
	}

	@DataProvider
	public Object[][] getData() {
		String sheetName = "AddCustomerTest";
		if (excel == null) return new Object[0][0];

		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);

		Object[][] data = new Object[rows - 1][1];

		for (int rowNum = 2; rowNum <= rows; rowNum++) {
			Hashtable<String, String> table = new Hashtable<String, String>();

			for (int colNum = 0; colNum < cols; colNum++) {
				
				// OPTIMIZATION: Key aur Value dono ko yahan hi trim kar diya
				// Ab Test method mein null ya space ka panga nahi aayega
				String key = excel.getCellData(sheetName, colNum, 1).trim();
				String value = excel.getCellData(sheetName, colNum, rowNum).trim();
				
				table.put(key, value);
			}
			data[rowNum - 2][0] = table;
		}
		return data;
	}
}
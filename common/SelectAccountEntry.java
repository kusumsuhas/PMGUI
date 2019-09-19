package com.fedex.pmgui.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fedex.pmgui.driverscript.DriverScript;

public class SelectAccountEntry {
	
	DriverScript driverScript= new DriverScript();
	WebDriver driver ;
	String file;
	public SelectAccountEntry(WebDriver driver)
	{
		this.driver=driver;
	}
	
	/**gotoAccountEntry()----- This method takes to the AccountEntry screen.
	 * 
	 * @throws InterruptedException
	 */
	
	public void gotoAccountEntry() throws InterruptedException 
	{		
		file=DriverScript.selectAccEntry;
		Actions inbox = new Actions(driver);
		inbox.moveToElement(driver.findElement(By.xpath(driverScript.objRepository("inbox",file)))).build().perform();
		driver.findElement(By.xpath(driverScript.objRepository("PricingImplementationUser",file))).click();
		Thread.sleep(3000);
		
	    Actions accountEntry = new Actions(driver);
        accountEntry.moveToElement(driver.findElement(By.xpath(driverScript.objRepository("PricingMaintenance",file)))).build().perform();
        driver.findElement(By.xpath(driverScript.objRepository("AccountEntry",file))).click();
        Thread.sleep(3000);
	}
	}

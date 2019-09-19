package com.fedex.pmgui.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.fedex.pmgui.driverscript.DriverScript;

public class AddServices {
	DriverScript driverScript= new DriverScript();
	WebDriver driver ;
	String file;
	
	public AddServices(WebDriver driver)
	{
		this.driver=driver;
	}

	/**addService(String selectService)-----
	 * This method is selecting the desired services from the excel sheet. 
	 * @param selectService
	 * @throws InterruptedException
	 */
	public void addService(String selectService) throws InterruptedException{

		file=DriverScript.addServices;

		Actions add = new Actions(driver);
		add.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SummaryAdd",file)))).build().perform();
		add.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SummaryAdd",file)))).build().perform();
		Thread.sleep(15000);

		Actions service = new Actions(driver);
	//	service.doubleClick(driver.findElement(By.xpath("//span/div/div/ul//*[contains(text(),'"+selectService+"')]"))).build().perform();
		service.doubleClick(driver.findElement(By.xpath("//li[contains(text(),'"+selectService+"')]"))).build().perform();
		Thread.sleep(5000);

		driver.findElement(By.xpath((driverScript.objRepository("Continue",file)))).click();
		Thread.sleep(10000);
	}

}
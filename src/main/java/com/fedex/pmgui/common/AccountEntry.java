package com.fedex.pmgui.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fedex.pmgui.driverscript.DriverScript;

public class AccountEntry {

	DriverScript driverScript= new DriverScript();
	WebDriver driver ;
	String file;
	
	public AccountEntry(final WebDriver driver)
	{
		this.driver=driver;
	}

/**cENIorEAN(final String AccountType,final String AccountNumber)-----
 * This method is selecting the input CENI/EAN No from the excel sheet.
 * @param AccountType
 * @param AccountNumber
 * @throws InterruptedException
 */

	public void cENIorEAN(final String AccountType,final String AccountNumber) throws InterruptedException{
		//Select Account Number
		file=DriverScript.accEntry;
		driver.findElement(By.xpath(driverScript.objRepository("input",file))).clear();
		final Select accEntry = new Select(driver.findElement(By.xpath(driverScript.objRepository("CENIorEAN",file))));
		accEntry.selectByVisibleText(AccountType);
		Thread.sleep(10000);
		
		driver.findElement(By.xpath(driverScript.objRepository("input",file))).sendKeys(AccountNumber);
		driver.findElement(By.id(driverScript.objRepository("LookupAdd",file))).click();
		Thread.sleep(5000);
		driver.findElement(By.id(driverScript.objRepository("Continue",file))).click();
		/*Thread.sleep(5000);*/

		WebDriverWait wait = new WebDriverWait(driver,5000);
		wait.until(ExpectedConditions.titleIs("ePRS - Service Selection"));
		
		
	


}}
package com.fedex.pmgui.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fedex.pmgui.driverscript.DriverScript;


public class ServiceSelection
{

	DriverScript driverScript= new DriverScript();
	WebDriver driver ;
	String file;
	
	public ServiceSelection(WebDriver driver)
	{
		this.driver=driver;
	}



	/**regionCountryOpcoSelection(final String Region, final String Country, final String ServiceTab, final String ServiceGroup)----
	 * This method selects the Region, Country and Freight Opcos for the account no.
	 * @param Region
	 * @param Country
	 * @param ServiceTab
	 * @param ServiceGroup
	 * @throws Exception
	 */
	public void regionCountryOpcoSelection(final String Region, final String Country, final String ServiceTab, final String ServiceGroup) throws Exception
	{

		file=DriverScript.serviceSelection;
		//selecting region

		driver.findElement(By.xpath(driverScript.objRepository("AllRegions",file))).click();
		Thread.sleep(5000);

		driver.findElement(By.xpath(driverScript.objRepository("AllRegions",file))).click();
		Thread.sleep(5000);

		//Select the required region
		driver.findElement(By.xpath("//label[contains(text(),'" + Region + "')]/../input")).click();
		Thread.sleep(10000);

		//Remove the selected countries from the list
		if(!(driver.findElement((By.xpath(driverScript.objRepository("SelectedCountries",file)))).getText().equalsIgnoreCase("")))
		{
			Actions deselect = new Actions(driver);
			deselect.moveToElement(driver.findElement(By.xpath(driverScript.objRepository("RemoveSelectedCountry",file)))).doubleClick().build().perform();
		}
		Thread.sleep(10000);

		//Select desired countries
		Actions selectCountry = new Actions(driver);
		selectCountry.doubleClick(driver.findElement(By.xpath("//span/div/div/ul//*[contains(text(),'"+Country+"')]"))).build().perform();
		Thread.sleep(10000);

		//Select Freight OpCos
		driver.findElement(By.xpath(driverScript.objRepository("AllOpCos",file))).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath(driverScript.objRepository("FreightOpCo",file))).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath(driverScript.objRepository("Refresh",file))).click();
		Thread.sleep(5000);
		
		//selecting services
		selectTab(ServiceTab);	
		Thread.sleep(10000);

		driver.findElement(By.xpath("//span[contains(text(),'" +ServiceGroup + "')]/../input")).click();
		Thread.sleep(10000);

		driver.findElement(By.xpath(driverScript.objRepository("Continue",file))).click();
		//Thread.sleep(5000);
		WebDriverWait wait = new WebDriverWait(driver,5000);
		wait.until(ExpectedConditions.titleIs("ePRS - Pricing Summary"));
		
	}


	/**selectTab(String ServiceTab)-----
	 * This method selects the desired service for the given CENI/EAN as per the test scenario. 
	 * 
	 * @param ServiceTab
	 * @throws Exception
	 */
	public void selectTab(String ServiceTab) throws Exception
	{

		//selecting service tabs
		if(!ServiceTab.equalsIgnoreCase(""))
		{
			if(ServiceTab.equalsIgnoreCase("Intra Country"))	
			{
				driver.findElement(By.xpath(driverScript.objRepository("IntraCountry",file))).click();
			}
			else if(ServiceTab.equalsIgnoreCase("International"))	
			{
				driver.findElement(By.xpath(driverScript.objRepository("International",file))).click();
			}
			else if(ServiceTab.equalsIgnoreCase("Surcharges"))	
			{
				driver.findElement(By.xpath(driverScript.objRepository("Surcharges",file))).click();
			}
		}		

		Thread.sleep(10000);		
	}

}
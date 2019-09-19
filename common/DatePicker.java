package com.fedex.pmgui.common;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.fedex.pmgui.driverscript.DriverScript;


public class DatePicker {

	DriverScript driverScript= new DriverScript();
	WebDriver driver ;
	String file;

	public DatePicker(WebDriver driver)
	{
		this.driver=driver;
	}

	/**selectdateVDED()----- This method is selecting Start and End dates on
	 * volume discount and Earned discount screen.
	 * @return
	 * @throws Exception
	 */
	public String[] selectDateVDED() throws Exception{

		file=DriverScript.datePicker;
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		String StartDate = dateFormat.format(date);

		String[] a=StartDate.split("/");
		Calendar c = Calendar.getInstance();
		c.setTime(date); 
		c.add(Calendar.DATE, 365);
		date = c.getTime();
		String EndDate = dateFormat.format(date);

		String[] b=EndDate.split("/");

		String sDate, eDate;
		sDate = driverScript.objRepository("StartDateVDED",file);
		eDate = driverScript.objRepository("EndDateVDED",file);

		//select Start Date
		driver.findElement(By.xpath(sDate)).click();
		Thread.sleep(2000);

		//get current date time with Date()			                         
		if(a[1].substring(0,1).contains("0"))
		{
			String s=a[1].substring(1);

			driver.findElement(By.linkText(s)).click();
			Thread.sleep(2000);
		}
		else
		{	
			driver.findElement(By.linkText(a[1])).click();
			Thread.sleep(2000);
		}

		Thread.sleep(2000);

		//select End Date       

		driver.findElement(By.xpath(eDate)).click();               
		Thread.sleep(2000);

		//Select End year
		Select year = new Select(driver.findElement(By.xpath(driverScript.objRepository("year",file))));
		year.selectByValue(b[2]);

		//Select End month	    
		int endMonth = Integer.parseInt(b[0]);
		endMonth=endMonth-1;
		String endMonth1=Integer.toString(endMonth);
		Select Month = new Select(driver.findElement(By.xpath(driverScript.objRepository("month",file))));
		Month.selectByValue(endMonth1);

		//Click End Date	        
		if(b[1].substring(0,1).contains("0"))
		{
			String s=b[1].substring(1);

			driver.findElement(By.linkText(s)).click();
			Thread.sleep(2000);
		}
		else
		{
			driver.findElement(By.linkText(b[1])).click();
			Thread.sleep(2000);
		}

		String[] array={StartDate,EndDate};
		return array;


	}

	/**
	 * selectdateDIMS()----- This method is selecting Start and End dates on 
	 * DIMs discount screen.
	 * @return
	 * @throws Exception
	 */
	public String[] selectDateDIMS() throws Exception{

		file=DriverScript.datePicker;
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		String StartDate = dateFormat.format(date);

		String[] a=StartDate.split("/");
		Calendar c = Calendar.getInstance();
		c.setTime(date); 
		c.add(Calendar.DATE, 365);
		date = c.getTime();
		String EndDate = dateFormat.format(date);

		String[] b=EndDate.split("/");

		String sDate, eDate;
		sDate = driverScript.objRepository("StartDateDIMs",file);
		eDate = driverScript.objRepository("EndDateDIMs",file);

		//select Start Date
		driver.findElement(By.xpath(sDate)).click();
		Thread.sleep(2000);

		//get current date time with Date()
		if(a[1].substring(0,1).contains("0"))
		{
			String s=a[1].substring(1);

			driver.findElement(By.linkText(s)).click();
			Thread.sleep(2000);
		}
		else
		{	
			driver.findElement(By.linkText(a[1])).click();
			Thread.sleep(2000);
		}

		Thread.sleep(2000);

		//select End Date	        
		driver.findElement(By.xpath(eDate)).click();               
		Thread.sleep(2000);

		//Select End year
		Select year = new Select(driver.findElement(By.xpath(driverScript.objRepository("year",file))));
		year.selectByValue(b[2]);

		//Select End month
		int endMonth = Integer.parseInt(b[0]);
		endMonth=endMonth-1;
		String endMonth1=Integer.toString(endMonth);
		Select Month = new Select(driver.findElement(By.xpath(driverScript.objRepository("month",file))));
		Month.selectByValue(endMonth1);

		//Click End Date
		if(b[1].substring(0,1).contains("0"))
		{
			String s=b[1].substring(1);

			driver.findElement(By.linkText(s)).click();
			Thread.sleep(2000);
		}
		else
		{
			driver.findElement(By.linkText(b[1])).click();
			Thread.sleep(2000);
		}
		String[] array={StartDate,EndDate};
		return array;

	}

	/**selectdateSurcharges()----- This method is selecting Start and End dates on 
	 * Surcharges discount screen.
	 * @return
	 * @throws Exception
	 */

	public String[] selectDateSurcharges() throws Exception{

		file=DriverScript.datePicker;
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		String StartDate = dateFormat.format(date);

		String[] a=StartDate.split("/");
		Calendar c = Calendar.getInstance();
		c.setTime(date); 
		c.add(Calendar.DATE, 365);
		date = c.getTime();
		String EndDate = dateFormat.format(date);

		String[] b=EndDate.split("/");

		String sDate, eDate;
		sDate = driverScript.objRepository("StartDateSurcharge",file);
		eDate = driverScript.objRepository("EndDateSurcharge",file);

		//select Start Date
		Thread.sleep(5000);
		driver.findElement(By.xpath(sDate)).click();
		Thread.sleep(2000);

		//get current date time with Date()			           

		if(a[1].substring(0,1).contains("0"))
		{
			String s=a[1].substring(1);

			driver.findElement(By.linkText(s)).click();
			Thread.sleep(2000);
		}
		else
		{	
			driver.findElement(By.linkText(a[1])).click();
			Thread.sleep(2000);
		}

		Thread.sleep(2000);

		//select End Date
		driver.findElement(By.xpath(eDate)).click();               
		Thread.sleep(2000);

		//Select End year
		Select year = new Select(driver.findElement(By.xpath(driverScript.objRepository("year",file))));
		year.selectByValue(b[2]);

		//Select End month
		int endMonth = Integer.parseInt(b[0]);
		endMonth=endMonth-1;
		String endMonth1=Integer.toString(endMonth);
		Select Month = new Select(driver.findElement(By.xpath(driverScript.objRepository("month",file))));
		Month.selectByValue(endMonth1);

		//Click End Date
		if(b[1].substring(0,1).contains("0"))
		{
			String s=b[1].substring(1);

			driver.findElement(By.linkText(s)).click();
			Thread.sleep(2000);
		}
		else
		{
			driver.findElement(By.linkText(b[1])).click();
			Thread.sleep(2000);
		}

		String[] array={StartDate,EndDate};
		return array;
	}

	@SuppressWarnings("unused")
	/**selectdateGrace()----- This method is selecting grace periods in
	 * Surcharges discount screen.
	 * @return
	 * @throws Exception
	 */

	public void selectDateGrace() throws Exception{
		
		file=DriverScript.datePicker;
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		String StartDate = dateFormat.format(date);

		String[] a=StartDate.split("/");
		Calendar c = Calendar.getInstance();
		c.setTime(date); 
		c.add(Calendar.DATE, 365);
		date = c.getTime();
		String EndDate = dateFormat.format(date);

		String[] b=EndDate.split("/");

		String sDate, eDate;
		sDate = driverScript.objRepository("StartDateGrace",file);
		eDate = driverScript.objRepository("EndDateGrace",file);

		//select Start Date
		driver.findElement(By.xpath(sDate)).click();
		Thread.sleep(2000);

		//get current date time with Date()
		if(a[1].substring(0,1).contains("0"))
		{
			String s=a[1].substring(1);

			driver.findElement(By.linkText(s)).click();
			Thread.sleep(2000);
		}
		else
		{	
			driver.findElement(By.linkText(a[1])).click();
			Thread.sleep(2000);
		}
		Thread.sleep(10000);

	}

}

package com.fedex.pmgui.aggregationLineage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;


public class Aggregation {
	
	WebDriver driver;
	
	public Aggregation(WebDriver driver)
	{
		this.driver=driver;
	}

	public void aggregationTest() throws InterruptedException, IOException {

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
		driver.get("https://test.secure.fedex.com/L3/pricing/eprs/inbox/inbox.xhtml");
		driver.findElement(By.xpath(".//*[@id='login']")).sendKeys("883368");
		driver.findElement(By.xpath(".//*[@id='password']")).sendKeys("883368");
		driver.findElement(By.xpath(".//*[@id='submit']")).click();
		Thread.sleep(10000);
		
		Actions a= new Actions(driver);
		a.moveToElement(driver.findElement(By.xpath("//span[text()='Inbox']"))).build().perform();
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//span[text()='Pricing Implementation User']")).click();
		Thread.sleep(8000);

		Actions agg = new Actions(driver);
		agg.moveToElement(driver.findElement(By.xpath("//span[text()='Pricing Maintenance']"))).build().perform();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[text()='New/Update Aggregation']")).click();
		Thread.sleep(7000);

		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'RvnueAggTitleTxt')]")).isDisplayed());

		Assert.assertTrue(driver.findElement(By.xpath(".//*[contains(@id,'SrchAggAccSrchBtn')]")).isEnabled());

		driver.findElement(By.xpath(".//*[contains(@id,'SrchAggAccNoVal')]/span[1]/input")).sendKeys("8963115");

		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'SrchAggSrchTxt')]")).isDisplayed());


		//**********Update aggregation **************************************//

		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'aggDtlsBtnUpdtAggr')]")).isDisplayed());

		driver.findElement(By.xpath("//*[contains(@id,'aggDtlsBtnUpdtAggr')]")).click();


		Select action_options = new Select(driver.findElement(By.xpath("//*[contains(@id,'aggDtlsSlctActnVal')]")));

		action_options.getAllSelectedOptions();
		System.out.println(action_options);

		//*******************************************************************//


		//*********************Details Tab***********************************//
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'aggDtlsAggrIdTxt')]")).isDisplayed());

		String Details_Agg_id=driver.findElement(By.xpath("//*[contains(@id,'aggDtlsAggrIdVal')]")).getText();
		System.out.println("Details Tab Aggregation ID : "+ Details_Agg_id);
		//LOGGER.i("Details Tab Aggregation ID :");
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'aggDtlsAggrNmTxt')]")).isDisplayed());

		String Details_Agg_name=driver.findElement(By.xpath("aggDtlsAggrNmVal")).getText();
		System.out.println("Details Tab Aggregation Name : "+ Details_Agg_name);

		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'aggDtlsPnlGrp3')]")).isDisplayed());//pending

		String Details_Agg_id1=driver.findElement(By.xpath("//*[contains(@id,'aggDtlsAggrIdVal')]")).getText();
		System.out.println("Details Tab Aggregation ID : "+ Details_Agg_id1);

		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'aggDtlsStrtDateTxt')]")).isDisplayed());

		String Details_startdate=driver.findElement(By.xpath("//*[contains(@id,'aggDtlsSrchEffDt_input')]")).getText();
		System.out.println("Details Tab start date : "+ Details_startdate);

		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'aggDtlsEndDateTxt')]")).isDisplayed());

		String Details_end_date=driver.findElement(By.xpath("//*[contains(@id,'aggDtlsSrchEndDt_input')]")).getText();
		System.out.println("Details Tab end date  "+ Details_end_date);

		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'aggDtlsCalFrqncyTxt')]")).isDisplayed());

		String Details_Calc_freq=driver.findElement(By.xpath("//*[contains(@id,'aggDtlsCalFrqncyVal')]")).getText();
		System.out.println("Details Tab Calcukation frequency : "+ Details_Calc_freq);

		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'aggDtlsPrdCalTxt')]")).isDisplayed());

		String Details_periods=driver.findElement(By.xpath("//*[contains(@id,'aggDtlsPrdCalVal')]")).getText();
		System.out.println("Details Tab Periods used in calculation : "+ Details_periods);


		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'aggDtlsTypeTxt')]")).isDisplayed());

		String Details_type=driver.findElement(By.xpath("//*[contains(@id,'aggDtlsTypeVal')]")).getText();
		System.out.println("Details Tab type : "+ Details_type);

		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'aggDtlsCurrTxt')]")).isDisplayed());

		String Details_cuurency=driver.findElement(By.xpath("//*[contains(@id,'aggDtlsCurrVal')]")).getText();
		System.out.println("Details Tab Currency "+ Details_cuurency);

		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'aggDtlsCntrOpcoTxt')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'aggDtlsSlctOpcoChkBox')]/tbody/tr/td[1]/input")).isEnabled());


		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'aggDtlsSlctOpcoChkBox')]/tbody/tr/td[2]/input")).isEnabled());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'aggDtlsSlctOpcoChkBox')]/tbody/tr/td[3]/input")).isEnabled());


		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'aggDtlsIndTxt')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'aggDtlsIndTxt')]")).isEnabled());


		//*******************************************************************//
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'SrchAggAccSrchBtn')]")).isDisplayed());

		Assert.assertTrue(driver.findElement(By.xpath(".//*[contains(@id,'SrchAggAccSrchBtn')]")).isDisplayed());

		driver.findElement(By.xpath(".//*[contains(@id,'SrchAggAccSrchBtn')]")).click();
		Assert.assertFalse(driver.findElement(By.xpath("//*[contains(@id,'RvnueAggDECBtnContinue1')]")).isEnabled());

		Assert.assertFalse(driver.findElement(By.xpath(".//*[@id='aggregationDtlsForm:RvnueAggDECBtn']")).isDisplayed());

		Thread.sleep(2000);
		driver.findElement(By.xpath(".//*[contains(@id,'0:SrchAggIDLnk')]")).click();
		Assert.assertFalse(driver.findElement(By.xpath("//*[contains(@id,'aggDtlsBtnUpdtAggr')]")).isDisplayed());

		driver.findElement(By.xpath("//*[contains(@id,'RvnueAggtbPn2Lbl')]")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'AggAvgsStrtTxt')]")).isDisplayed());

		String avg_start_date=driver.findElement(By.xpath("//*[contains(@id,'AggAvgsRaaStrtVal_input')]")).getText();
		System.out.println("Average start date : "+ avg_start_date);

		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'AggAvgsEndTxt')]")).isDisplayed());

		String avg_end_date=driver.findElement(By.xpath("//*[contains(@id,'AggAvgsRaaEndVal_input')]")).getText();
		System.out.println("Average end date : "+ avg_end_date);

		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'AggAvgsAggIdTxt')]")).isDisplayed());

		String aggregation_id=driver.findElement(By.xpath("//*[contains(@id,'AggAvgsAggIdVal')]")).getText();
		System.out.println("Aggregation Id : "+ aggregation_id);

		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'AggAvgsAvgStrtDateTxt')]")).isDisplayed());

		String start_date=driver.findElement(By.xpath("//*[contains(@id,'AggAvgsAvgStrtDateVal')]")).getText();
		System.out.println(" Start date : "+ start_date);

		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'AggAvgsEndTxt')]")).isDisplayed());

		String end_date=driver.findElement(By.xpath("//*[contains(@id,'AggAvgsAvgEndDateVal')]")).getText();
		System.out.println(" End date : "+end_date);

		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'AggAvgsStrtTxt')]")).isDisplayed());

		String cal_frequency=driver.findElement(By.xpath("//*[contains(@id,'AggAvgsAvgCalFrqcyval')]")).getText();
		System.out.println("Calculation Frequency : "+ cal_frequency);

		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'AggAvgsAvgCalFrqcyval')]")).isDisplayed());

		String periods=driver.findElement(By.xpath("//*[contains(@id,'AggAvgsAvgCalFrqcyval')]")).getText();
		System.out.println("Periods Used in calculation : "+ periods);

		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'AggAvgsCurrAvgVal')]")).isDisplayed());

		String currency=driver.findElement(By.xpath("//*[contains(@id,'AggAvgsCurrAvgVal')]")).getText();
		System.out.println("Currency : "+ currency);

	}


	//    //*************************Pricing Audit Trail*********************************//
	//    
	//    Assert.assertTrue(driver.findElement(By.xpath("//form//table//tr[1]//td[1]//a")).isDisplayed());
	//    driver.findElement(By.xpath("//form//table//tr[1]//td[1]//a")).click();
	//    
	//    Thread.sleep(2000);
	//    
	//      Assert.assertTrue(driver.findElement(By.xpath("//form//div//div//div//span[text()='Pricing Audit Trail']")).isDisplayed());
	//      File AuditTrail = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	//FileUtils.copyFile(AuditTrail, new File("./target/screenshots/AuditTrail.jpeg"));
	//    
	//    
	//    //*****************************************************************************//
	//    //***********************Miscellaneous****************************************//
	//    driver.findElement(By.xpath("//em[text()='Miscellaneous']")).click();
	//    
	//        Assert.assertTrue(driver.findElement(By.xpath("//em[text()='Customer Attributes']")).isDisplayed());
	//    
	//    
	//      Assert.assertTrue(driver.findElement(By.xpath("//em[text()='Manual Admin Data']")).isDisplayed());
	//    
	//    
	//      Assert.assertTrue(driver.findElement(By.xpath("//em[text()='TD Line Haul']")).isDisplayed());
	//                    
	//    
	//    
	//    //***********************Manual Admin DATA **************************************//
	//    
	//    
	//    
	//
	//                    Assert.assertFalse(driver.findElement(By.xpath("//*[contains(@id,'ManualAdminLabel')]")).isDisplayed());
	//    
	//    Assert.assertFalse(driver.findElement(By.xpath("//label[text()='Inheritance Blocked']/../..")).isDisplayed());
	//    
	//    
	//    
	//                    Assert.assertFalse(driver.findElement(By.xpath("//label[text()='Inheriting From']/../..")).isDisplayed());
	//    
	//    
	//                    Assert.assertFalse(driver.findElement(By.xpath("//label[text()='Date Segment']/../..")).isDisplayed());
	//    
	//    
	//                    Assert.assertTrue(driver.findElement(By.xpath("//label[text()='Date Segment']/../../../../../..//label[text()='Start Date']/../..//input")).isDisplayed());
	//    
	//    
	//                    Assert.assertTrue(driver.findElement(By.xpath("//label[text()='Date Segment']/../../../../../..//label[text()='End Date']/../..//input")).isDisplayed());
	//    
	//    
	//                    Assert.assertTrue(driver.findElement(By.xpath("//label[text()='Agreement Number']/../../..//tr//td[2]//input")).isDisplayed());
	//    
	//    Assert.assertTrue(driver.findElement(By.xpath("//label[text()='Value']/../..//select")).isDisplayed());
	//    
	//    
	//                    Assert.assertTrue(driver.findElement(By.xpath("//label[text()='Geography Type']/../..//select")).isDisplayed());
	//    
	//    
	//                    Assert.assertFalse(driver.findElement(By.xpath("//label[text()='Deleted?']/../..")).isDisplayed());
	//    
	//    
	//    
	//    //*******************************************************************************//


}

package com.fedex.pmgui.surcharges;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.log4testng.Logger;

import com.fedex.pmgui.common.AccountEntry;
import com.fedex.pmgui.common.AddServices;
import com.fedex.pmgui.common.DatePicker;
import com.fedex.pmgui.common.LoginPage;
import com.fedex.pmgui.common.SelectAccountEntry;
import com.fedex.pmgui.common.ServiceSelection;
import com.fedex.pmgui.driverscript.DriverScript;

public class Surcharges {

	DriverScript driverScript= new DriverScript();
	WebDriver driver ;
	String file, requirement;
	String id, password, accountType, accountNumber, region, country, serviceTab, serviceGroup, selectService,copyService;
	String agreementNumber,proposalNumber,minimumWeight,maxWeight,stateMatrix,currencytype,subjGRI,plusminus1,dir1,type1,name1,state1,county1,country1;
	String plusminus2,dir2,type2,name2,state2,county2,country2,servicedayslow,servicedayshigh,excptclassmaxwgt,discApplyAll,addLines,type,addLinesSurcharges;
	String minWt,maxwt,minWt1,maxWt1,amount,minamount,maxamount,comments,currencyType,mnl,unit,rank,rank1,increasedecrease1,discountType,surcharge,increasedecrease2,exceptionClass,class_NMFC_HR,class_NMFC_LR,exceptionClassAll,county;
	String status,accountCENI,pricingDiscountType,pricingSummary,proposalNbr,startDate,endDate,requestName;
	String [] date;
	XSSFWorkbook workbook;
	XSSFSheet worksheet;
	XSSFRow row;
	static final Logger LOGGER = Logger.getLogger(Surcharges.class);

	public Surcharges(WebDriver driver)
	{
		this.driver=driver;
	}

	/**surchargesTest()----- This method runs the test cases for surcharges type of discounts.
	 * 
	 * @throws Exception
	 */
	public void surchargesTest() throws Exception
	{
		try{
			file=DriverScript.surcharges;
			FileInputStream FileInputStream = new FileInputStream(DriverScript.workSheetPath);
			workbook = new XSSFWorkbook(FileInputStream);

			worksheet = workbook.getSheet("Surcharges");

			for (int k=21;k<=worksheet.getLastRowNum();k++)
			{
				row = worksheet.getRow(k);
				setVariable( worksheet,row);

				LoginPage Login = new LoginPage(driver);
				Login.login(id, password);
				Thread.sleep(10000);

				SelectAccountEntry AE = new SelectAccountEntry(driver);
				AE.gotoAccountEntry();
				Thread.sleep(5000);

				AccountEntry AccountNo = new AccountEntry(driver);
				AccountNo.cENIorEAN(accountType, accountNumber);
				Thread.sleep(5000);
				

				ServiceSelection SS = new ServiceSelection(driver);
				
				SS.regionCountryOpcoSelection(region, country, serviceTab, serviceGroup);
				Thread.sleep(10000);
			    
				
				surcharges();
				createProposal();
				logOut();

			}
		}catch(Exception e)
		{
			LOGGER.error("Proposal was not created successfully"+ e.getMessage());
		}

	}

	/** surcharges()-----This method navigates to the surcharges discount page  
	 * and fills the required fields.
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void surcharges() throws InterruptedException, IOException{

		Actions a = new Actions(driver);
		a.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SurchargeTab",file)))).build().perform();
		Thread.sleep(20000);
		
//GUI Validation of Copy Service starts here
		
/*
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_FreightVolume - WeightBreakDiscounts - CopyService - Surcharges
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_FreightVolume - WeightBreakDiscounts - CopyService - Surcharges - Edits
 */
		
				if (!copyService.equalsIgnoreCase(""))
				{
					driver.findElement(By.xpath("((//span[text()='US LTL Intra-Country Surcharges'])[1]/../..//a[text()='Details'])[1]")).click();
					//driver.findElement(By.linkText("Details")).click();
					Thread.sleep(8000);
					
//					driver.findElement(By.xpath("//*[contains(@id,'summaryTabSetcnt')]//tr[1]//a[Text()='Details'][1]")).click();
//					Thread.sleep(5000);

					driver.findElement(By.xpath("//*[contains(@id,'CopyService')]")).click();
					Thread.sleep(3000);
					Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'confirmDialogcopyForfxfSurcharges_main')]//label[text()='If you Copy this Service with unsaved changes, the unsaved changes will not be copied.  Do you still want to Copy the Service']")).isDisplayed());

					driver.findElement(By.xpath("//input[contains(@id,'noCopySurcharge')]")).click();
					Assert.assertEquals(driver.findElement(By.xpath("//span[text()='FXF Surcharge Detail']")).getText(),"FXF Surcharge Detail" );
					
					//driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjcopyService')]")).click();
					driver.findElement(By.xpath("//*[contains(@id,'CopyService')]")).click();
					Thread.sleep(3000);
					driver.findElement(By.xpath("//input[contains(@id,'yesCopySurcharge')]")).click();
					Thread.sleep(6000);

					//Assert.assertEquals(driver.findElement(By.xpath("//span[Text()='copy service']")).getText(),"copy service");

					//driver.findElement(By.xpath("//label[Text()='Enter BDS']//..//..//td[2]//input")).sendKeys("service");
					Thread.sleep(3000);
			
					//String service = driver.findElement(By.xpath("//div//tbody//tr[3]//span//..//..//td[2]//span")).getAttribute("value");
                    
					Actions selectService = new Actions(driver);
					selectService.doubleClick(driver.findElement(By.xpath("//li[contains(text(),'US IC Priority TP Intra')]"))).build().perform();

					//clicked on save
					driver.findElement(By.xpath("//*[contains(@id,'copyCancel1')]//..//..//td[2]//input")).click();
					WebElement  element = driver.findElement(By.xpath("//*[contains(@id,'copyCancel1')]//..//..//td[2]//input"));
					JavascriptExecutor executor = (JavascriptExecutor)driver;
					executor.executeScript("arguments[0].click();", element);
					Thread.sleep(5000);

					if(driver.findElement(By.xpath("//*[contains(@id,'copyServiceDialog_main')]//form//div[1]//ul//li//span[text()='The following services already exists for all or part of the date range  ']")).isDisplayed())
					{
						Reporter.log("Error Message: The following services already exists for all or part of the date range");
						Actions selService = new Actions(driver);
						selService.doubleClick(driver.findElement(By.xpath("//li[contains(text(),'US IC Priority TP Intra')]"))).build().perform();
						Thread.sleep(6000);
						//driver.findElement(By.xpath("//label[Text()='Enter BDS']//..//..//td[2]//input")).clear();
						selService.doubleClick(driver.findElement(By.xpath("//li[contains(text(),'AK - IP')]"))).build().perform();
						driver.findElement(By.xpath("//*[contains(@id,'copyCancel1')]//..//..//td[2]//input")).click();
					}
					
					Assert.assertEquals(driver.findElement(By.xpath("//li//span[Text()='Record has been updated successfully']")).getText(),"Record has been updated successfully");

					
					driver.findElement(By.xpath("//*[contains(@id,'copyCancel1')]")).click();
					Thread.sleep(3000);
					driver.findElement(By.xpath("//*[contains(@id,'cancel2')]")).click();
					Thread.sleep(3000);
					driver.findElement(By.xpath("//*[contains(@id,'fxfSurchargeDetailForm:yes2')]")).click();
					Thread.sleep(5000);
				}
				
//GUI Validation of Copy Service ends here
//Test Case Name[1]:M_PRC_FXFR_PricingMaintenance_FreightSurchargeDetailPage_LaunchPoints
				
		AddServices addsrvc = new AddServices(driver);
		addsrvc.addService(selectService);

		try {
			DatePicker d = new DatePicker(driver);
			date = d.selectDateSurcharges();
			DriverScript.setVariable("StartDate",worksheet,row,date[0]);
			DriverScript.setVariable("EndDate",worksheet,row,date[1]);
			Reporter.log("Start date and end date is editable");
		} catch (Exception e) {
			LOGGER.error("Didn't capture the startdate and enddate"+ e.getMessage());
		}
		
		Assert.assertEquals(driver.findElement(By.xpath("//span[text()='FXF Surcharge Detail']")).getText(),"FXF Surcharge Detail" );
       
		//Test Case Name[1]:M_PRC_FXFR_PricingMaintenance_FreightSurchargeDetailPage_Layout
		//Test Case Name[1]:M_PRC_FXFR_PricingMaintenance_FreightSurchargeDetailPage_PremiumServicesUpcharge
				Reporter.log("Pricing Audit trail is displayed");
				File Link = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(Link, new File("./target/screenshots/Surcharges/Pricingaudit.jpeg"));
				Reporter.log("Intra Country is visible");
				
		        Reporter.log("US LTL Intra-Country Surcharges is displayed");
		        File service = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(service, new File("./target/screenshots/Surcharges/Service.jpeg"));
				
				driver.findElement(By.xpath("(//*[contains(@id,'fxfSurchargeDetailForm:saveAndClose')])[1]")).isDisplayed();
				Reporter.log("Save button is displayed");
				Reporter.log("Cancel Button is displayed");
				driver.findElement(By.xpath(" (//*[contains(@id,'fxfSurchargeDetailForm:surchargeTabSet_nav')])")).isDisplayed();
				Reporter.log("Following tabs are visible: 1. Surcharge ,2. COD, 3. Prem Serv Upcharges ,4. H/U Wgt Waivers");
				Reporter.log("ExceptionPricing  link is displayed");
				File ExceptionPricing = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(ExceptionPricing, new File("./target/screenshots/Surcharges/ExceptionPricing.jpeg"));
	//Test Case Name[1]:M_PRC_FXFR_PricingMaintenance_FreightSurchargeDetailPage_PMGUIFlow_FXFSurcharge_Subgroup
	//Test Case Name[1]:M_PRC_FXFR_PricingMaintenance_FreightSurchargeDetailPage_PMGUIFlow_FXFSurcharge_HUWeightWaivers		
				
				 Assert.assertTrue(driver.findElement(By.xpath(" (//*[contains(@id,'fxfSurchargeDetailForm:inheritingFrom')])[1]")).isDisplayed());
				 Reporter.log("Inheriting From is non-editable");
				 Assert.assertTrue(driver.findElement(By.xpath("(//*[contains(@id,'fxfSurchargeDetailForm:agmtNbr')])[1]")).isDisplayed());
				 Reporter.log("Agreement Number is non-editable");
				 Assert.assertTrue(driver.findElement(By.xpath("(//*[contains(@id,'fxfSurchargeDetailForm:services')])[1]")).isDisplayed());
				 Reporter.log("Service is non-editable");
				 Assert.assertTrue(driver.findElement(By.xpath("(//*[contains(@id,'fxfSurchargeDetailForm:inheritanceBlocked')])[1]")).isDisplayed());
				 Reporter.log("Inheritance Blocked is non-editable");
				 Assert.assertTrue(driver.findElement(By.xpath("(//*[contains(@id,'fxfSurchargeDetailForm:prpslNbr')])[1]")).isDisplayed());
				 Reporter.log("Proposal  Number is non-editable");
				 Assert.assertTrue(driver.findElement(By.xpath("(//*[contains(@id,'fxfSurchargeDetailForm:prpslType')])[1]")).isDisplayed());
				 Reporter.log("Proposal Type is non-editable");
				 Assert.assertTrue(driver.findElement(By.xpath("(//*[contains(@id,'fxfSurchargeDetailForm:deleted')])[1]")).isDisplayed());
				 Reporter.log("Deleted ? is non-editable");
		
		driver.findElement(By.xpath(driverScript.objRepository("MinWeight",file))).sendKeys(minimumWeight);
		driver.findElement(By.xpath(driverScript.objRepository("MaxWeight",file))).sendKeys(maxWeight);
		driver.findElement(By.xpath(driverScript.objRepository("StateMatrix",file))).sendKeys(stateMatrix);

		Select currtype = new Select(driver.findElement(By.xpath(driverScript.objRepository("CurrencyType",file))));
		currtype.selectByValue(currencyType);

		Select sbjcttogri = new Select(driver.findElement(By.xpath(driverScript.objRepository("SubjectGRI",file))));
		sbjcttogri.selectByVisibleText(subjGRI);
		Thread.sleep(2000);

		driver.findElement(By.xpath(driverScript.objRepository("Fromto1",file))).sendKeys(plusminus1);
		driver.findElement(By.xpath(driverScript.objRepository("Dir1",file))).sendKeys(dir1);
		driver.findElement(By.xpath(driverScript.objRepository("Type1",file))).sendKeys(type1);
		driver.findElement(By.xpath(driverScript.objRepository("Name1",file))).sendKeys(name1);
		driver.findElement(By.xpath(driverScript.objRepository("State1",file))).sendKeys(state1);
		//driver.findElement(By.xpath(driverScript.objRepository("County1",file))).sendKeys(County1);
		driver.findElement(By.xpath(driverScript.objRepository("Country1",file))).sendKeys(country1);

		driver.findElement(By.xpath(driverScript.objRepository("Fromto2",file))).sendKeys(plusminus2);
		driver.findElement(By.xpath(driverScript.objRepository("Dir2",file))).sendKeys(dir2);
		driver.findElement(By.xpath(driverScript.objRepository("Type2",file))).sendKeys(type2);
		driver.findElement(By.xpath(driverScript.objRepository("Name2",file))).sendKeys(name2);
		driver.findElement(By.xpath(driverScript.objRepository("State2",file))).sendKeys(state2);
		//driver.findElement(By.xpath(driverScript.objRepository("County2",file))).sendKeys(County2);
		driver.findElement(By.xpath(driverScript.objRepository("Country2",file))).sendKeys(country2);

		driver.findElement(By.xpath(driverScript.objRepository("ServiceDaysLow",file))).sendKeys(servicedayslow);
		driver.findElement(By.xpath(driverScript.objRepository("ServiceDaysHigh",file))).sendKeys(servicedayshigh);

		Select exceptionAll = new Select (driver.findElement(By.xpath(driverScript.objRepository("ExClassAll",file))));
		exceptionAll.selectByValue(exceptionClassAll);

		driver.findElement(By.xpath(driverScript.objRepository("ExClassAll",file))).sendKeys(excptclassmaxwgt);

		try{
			if(!addLines.equalsIgnoreCase(""))
			{
				driver.findElement(By.xpath(driverScript.objRepository("AddLines",file))).sendKeys(addLines);
				driver.findElement(By.xpath(driverScript.objRepository("AddLinesClick",file))).click();
				Select Class_NMFC = new Select(driver.findElement(By.xpath(driverScript.objRepository("Type",file))));
				Class_NMFC.selectByVisibleText(type);

				driver.findElement(By.xpath(driverScript.objRepository("ClassNMFCLR",file))).sendKeys(class_NMFC_LR);
				driver.findElement(By.xpath(driverScript.objRepository("ClassNMFCHR",file))).sendKeys(class_NMFC_HR);

				Select ExpClass = new Select(driver.findElement(By.xpath(driverScript.objRepository("ExceptionClass",file))));
				ExpClass.selectByValue(exceptionClass);
		}}catch(Exception e){
				Reporter.log("NMFC is not applied");
		}
		

 /** All field validations starts here
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts*/
 

//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_DiscountPercent_AppliesToAll
		
		Assert.assertTrue(driver.findElement(By.xpath("//label[contains(@id,'DiscountApplytoAll11')]")).isDisplayed());
		Reporter.log("Discount Apply to all is displayed");
		driver.findElement(By.xpath(driverScript.objRepository("DiscApplyAll",file))).sendKeys(discApplyAll);
		File DiscApplyAll = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(DiscApplyAll, new File("./target/screenshots/Surcharges/Discount Apply To All.jpeg"));
		
//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_AppliesAllIncrease-Decrease
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("IncrDecr",file))).isDisplayed());
		Reporter.log("Increase/Decrease is displayed");
		Select incDec = new Select(driver.findElement(By.xpath(driverScript.objRepository("IncrDecr",file))));
		incDec.getOptions();
		incDec.selectByValue(increasedecrease2);
		File IncDec = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(IncDec, new File("./target/screenshots/Surcharges/IncreaseDecrease.jpeg"));

//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_ValidSurcharges
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("Surcharge",file))).isDisplayed());
		Reporter.log("Surcharge dropdown is displayed and enabled");
		Select srchrg = new Select(driver.findElement(By.xpath(driverScript.objRepository("Surcharge",file))));
		srchrg.selectByVisibleText(surcharge);
		srchrg.getAllSelectedOptions();
		File Surcharge = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(Surcharge, new File("./target/screenshots/Surcharges/Surcharge.jpeg"));

		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("MinWeight1",file))).isEnabled());
		Reporter.log("Minimum Weight text box is enabled with default value:");
		Reporter.log(driver.findElement(By.xpath(driverScript.objRepository("MinWeight1",file))).getAttribute("value"));
		driver.findElement(By.xpath(driverScript.objRepository("MinWeight1",file))).clear();
		driver.findElement(By.xpath(driverScript.objRepository("MinWeight1",file))).sendKeys(minWt);
		File Minwgt = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(Minwgt, new File("./target/screenshots/Surcharges/Minimum Weight.jpeg"));
		
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("MaxWeight1",file))).isEnabled());
		Reporter.log("Maximum Weight text box is enabled with default value:");
		Reporter.log(driver.findElement(By.xpath(driverScript.objRepository("MaxWeight1",file))).getAttribute("value"));
		driver.findElement(By.xpath(driverScript.objRepository("MaxWeight1",file))).clear();
		driver.findElement(By.xpath(driverScript.objRepository("MaxWeight1",file))).sendKeys(maxwt);
		File Maxwgt = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(Maxwgt, new File("./target/screenshots/Surcharges/Maximum Weight.jpeg"));
		
//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_ValidDiscount_RateTypes
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("discountType",file))).isEnabled());
		Reporter.log("Discount Type drop down is enabled");
		Select tpe = new Select(driver.findElement(By.xpath(driverScript.objRepository("discountType",file))));
		tpe.selectByValue(discountType);
		Thread.sleep(5000);
		tpe.getAllSelectedOptions();
		File DiscType = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(DiscType, new File("./target/screenshots/Surcharges/Discount Type.jpeg"));

		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("Amount",file))).isEnabled());
		Reporter.log("Amount text box is enabled");
		driver.findElement(By.xpath(driverScript.objRepository("Amount",file))).sendKeys(amount);
		File amt = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(amt, new File("./target/screenshots/Surcharges/Amount.jpeg"));


		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("IncrDecr1",file))).isEnabled());
		Reporter.log("Increase/Decrease for Surcharge is enabled");
		Select incDec1 = new Select(driver.findElement(By.xpath(driverScript.objRepository("IncrDecr1",file))));
		incDec1.selectByValue(increasedecrease1);
		incDec1.getAllSelectedOptions();
		File IncDec1 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(IncDec1, new File("./target/screenshots/Surcharges/ID.jpeg"));
		
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'rankId11')]//span//span")).isDisplayed());
		Reporter.log("Rank field is displayed");
		Select rnk = new Select(driver.findElement(By.xpath(driverScript.objRepository("Rank",file))));
		rnk.selectByValue(rank);
		rnk.getAllSelectedOptions();
		File Rank = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(Rank, new File("./target/screenshots/Surcharges/Rank.jpeg"));
		
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'unitId11')]//span//span")).isDisplayed());
		Reporter.log("Unit field is displayed");
		Select unt = new Select(driver.findElement(By.xpath(driverScript.objRepository("Unit",file))));
		unt.selectByValue(unit);
		unt.getAllSelectedOptions();
		File Unit = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(Unit, new File("./target/screenshots/Surcharges/Unit.jpeg"));
		
//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_Minimum_ChargeAmount
		driver.findElement(By.xpath("//span[text()='Min Amt ']")).getText();
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("MinAmount",file))).isEnabled());
		Reporter.log("Minimum Amount field is enabled");
		driver.findElement(By.xpath(driverScript.objRepository("MinAmount",file))).sendKeys(minamount);
		File MinAmt = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(MinAmt, new File("./target/screenshots/Surcharges/Minimum Amount.jpeg"));
		
//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_Maximum_ChargeAmount
		driver.findElement(By.xpath("//span[text()='Max Amt ']")).getText();
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("MaxAmount",file))).isEnabled());
		Reporter.log("Maximum Amount field is enabled");
		driver.findElement(By.xpath(driverScript.objRepository("MaxAmount",file))).sendKeys(maxamount);
		File MaxAmt = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(MaxAmt, new File("./target/screenshots/Surcharges/Maximum Amount.jpeg"));
		

//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_NonCWT_SetsToMeasure
//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_RateType_SetsToManual
		
		try{

			WebElement rateManual = driver.findElement(By.xpath(driverScript.objRepository("RateManually",file)));
			
			if(discountType.contentEquals("RATE_PER_HOUR") || discountType.contentEquals("RATE_PER_DOCUMENT") || discountType.contentEquals("RATE_PER_DIMENSION") || discountType.contentEquals("RATE_PER_PERSON") || discountType.contentEquals("RATE_PER_PERSON_PER_HOUR") || discountType.contentEquals("RATE_PER_PERMIT") || discountType.contentEquals("PERMIT_FLAT_CHARGE") || discountType.contentEquals("RATE_PER_CHECK")|| discountType.contentEquals("RATE_PER_VEHICLE") || discountType.contentEquals("RATE_PER_UNIT_OF_EQUIPMENT_PER_DAY")|| discountType.contentEquals("RATE_PER_KILOMETER") || discountType.contentEquals("RATE_PER_STOP"))
				if(!rateManual.isEnabled())
					Reporter.log("Rate Manually is automatically selected for a surcharge that is a Rate / Unit of Measure.");

//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_RateManual_FlagValidation
//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_RateManual_Populated Fields
			
			if(!mnl.equalsIgnoreCase(""))
			{
				if (rateManual.isSelected())
					rateManual.click();
				Assert.assertFalse(rateManual.isSelected());

				if (!rateManual.isSelected())
					rateManual.click();
				Assert.assertTrue(rateManual.isSelected());

				Reporter.log("Rate Manually Flag is present");
				
				Reporter.log("Printing the TYPE" + driver.findElement(By.xpath(driverScript.objRepository("discountType",file))).getAttribute("value"));
				Reporter.log("Printing the Amount" + driver.findElement(By.xpath(driverScript.objRepository("Amount",file))).getAttribute("value"));
				Reporter.log("Printing the Increase/Decrease" + driver.findElement(By.xpath(driverScript.objRepository("IncrDecr1",file))).getAttribute("value"));
				Reporter.log("Printing the Rank" + driver.findElement(By.xpath(driverScript.objRepository("Rank",file))).getAttribute("value"));
				Reporter.log("Printing the Unit" + driver.findElement(By.xpath(driverScript.objRepository("Unit",file))).getAttribute("value"));

				WebElement checkIncDec = driver.findElement(By.xpath(driverScript.objRepository("IncrDecr1",file)));
				if(!checkIncDec.isEnabled())
					Reporter.log("I/D is non-editable");
				WebElement checkUnit = driver.findElement(By.xpath(driverScript.objRepository("Unit",file)));
				if(!checkUnit.isEnabled())
					Reporter.log("Unit is non-editable");
				
				File RateMnl = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(RateMnl, new File("./target/screenshots/Surcharges/Elements status when RM is selected.jpeg"));

			}}catch(Exception e){
				Reporter.log("Rate Manually doesn't need to be selected");
			}
		
//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_InformationalFreeFormText

		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("Comments",file))).isEnabled());
		Reporter.log("Freeform text/Comments field is enabled");
		driver.findElement(By.xpath(driverScript.objRepository("Comments",file))).sendKeys(comments);

		
		
		
									/**
								 	* Codes for only GUI validation starts
								 	*/
		
		
		
		
/*
* Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_MaintainIndvSurcharge
* Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_SurchargeWeightRange
* 
*/

		try{

			if (!addLinesSurcharges.equalsIgnoreCase(""))
			{
				driver.findElement(By.xpath("//div[5]//div//div//div//div//table//tr//td[3]//input[@type='submit'][@value='Add Lines']//..//..//td[2]//input")).sendKeys(addLinesSurcharges);
				driver.findElement(By.xpath("//div[5]//div//div//div//div//table//tr//td[3]//input[@type='submit'][@value='Add Lines']")).click();
				Thread.sleep(5000);
				Select srchrg1 = new Select(driver.findElement(By.xpath("//select[contains(@id,'1:surchargedropdown')]")));
				srchrg1.selectByVisibleText(surcharge);

				driver.findElement(By.xpath("//select[contains(@id,'1:surchargedropdown')]//..//..//td[3]//input")).clear();			
				driver.findElement(By.xpath("//select[contains(@id,'1:surchargedropdown')]//..//..//td[4]//input")).clear();

				Select tpe1 = new Select(driver.findElement(By.xpath("//select[contains(@id,'1:SurchargeTypedropDown11')]")));
				tpe1.selectByValue(discountType);

				driver.findElement(By.xpath("//select[contains(@id,'1:surchargedropdown')]//..//..//td[6]//input")).sendKeys(amount);

				Select incDec11 = new Select(driver.findElement(By.xpath("//select[contains(@id,'1:increaseDecrease11')]")));
				incDec11.selectByValue(increasedecrease1);

				//Assert.assertTrue(driver.findElement(By.xpath("//select[contains(@id,'1:rankDropDown11')]")).isEnabled());
				//Select rnk1 = new Select(driver.findElement(By.xpath("//select[contains(@id,'1:rankDropDown11')]")));
				//rnk1.selectByValue(rank);

				if(!driver.findElement(By.xpath("//select[contains(@id,'1:unitDropDown11')]")).isEnabled())
				{
					Select unt1 = new Select(driver.findElement(By.xpath("//select[contains(@id,'1:unitDropDown11')]")));
					unt1.selectByValue(unit);
				}
				
				Actions xyz = new Actions(driver);
				xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
				Thread.sleep(5000);
				
				xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
				Thread.sleep(10000);

				if(driver.findElement(By.xpath("//ul/li/span[text()='Minimum Weight: Required field was not entered']")).isDisplayed())
				{
					Reporter.log("Error message: Minimum Weight: Required field was not entered");
					driver.findElement(By.xpath("//select[contains(@id,'1:surchargedropdown')]//..//..//td[3]//input")).clear();
					driver.findElement(By.xpath("//select[contains(@id,'1:surchargedropdown')]//..//..//td[3]//input")).sendKeys(minWt);

					driver.findElement(By.xpath("//select[contains(@id,'1:surchargedropdown')]//..//..//td[4]//input")).clear();
					driver.findElement(By.xpath("//select[contains(@id,'1:surchargedropdown')]//..//..//td[4]//input")).sendKeys(maxwt);
				}

				xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
				Thread.sleep(5000);
				xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
				Thread.sleep(10000);
//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_RankHierarchyForPercents_AppliedToTotal

				if(driver.findElement(By.xpath("//div//div//div//ul/li/span[text()='May not have duplicate rank numbers']")).isDisplayed())
				{
					Reporter.log("Error Message: May not have duplicate rank numbers");
					Select rnk2 = new Select(driver.findElement(By.xpath(driverScript.objRepository("Rank",file))));
					rnk2.selectByIndex(0);
					Select rnk3 = new Select(driver.findElement(By.xpath("//select[contains(@id,'1:rankDropDown11')]")));
					rnk3.selectByIndex(0);
					
					xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
					Thread.sleep(5000);
					xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
					Thread.sleep(10000);
					
					if(driver.findElement(By.xpath("//div//div//div//ul/li/span[text()='Surcharge Rank: Required field was not entered']")).isDisplayed())
					{
						Reporter.log("Error Message: Surcharge Rank: Required field was not entered");
						Select rnk4 = new Select(driver.findElement(By.xpath(driverScript.objRepository("Rank",file))));
						rnk4.selectByValue(rank);
						Select rnk5 = new Select(driver.findElement(By.xpath("//select[contains(@id,'1:rankDropDown11')]")));
						rnk5.selectByValue(rank1);
					}
					
					xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
					Thread.sleep(5000);
					xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
					Thread.sleep(10000);
				}

				if(driver.findElement(By.xpath("//ul/li/span[text()='Weight Ranges may not overlap for the same Surcharge.']")).isDisplayed())
				{
					Reporter.log("Error Message: Weight Ranges may not overlap for the same Surcharge.");
					driver.findElement(By.xpath("//select[contains(@id,'1:surchargedropdown')]//..//..//td[3]//input")).clear();
					driver.findElement(By.xpath("//select[contains(@id,'1:surchargedropdown')]//..//..//td[3]//input")).sendKeys(minWt1);
					driver.findElement(By.xpath("//select[contains(@id,'1:surchargedropdown')]//..//..//td[4]//input")).clear();
					driver.findElement(By.xpath("//select[contains(@id,'1:surchargedropdown')]//..//..//td[4]//input")).sendKeys(maxWt1);
				}
							
				xyz.doubleClick(driver.findElement(By.xpath("//input[contains(@id,'fxfSurchargeDetailForm:save22')]"))).build().perform();
				Thread.sleep(10000);
				
				try{
					driver.findElement(By.xpath(driverScript.objRepository("Overlay",file))).click();
					Thread.sleep(10000);
					driver.findElement(By.linkText("Details")).click();
					Thread.sleep(5000);
				}catch(Exception e){
					Reporter.log("Overlay button doesn't exist");
				}
	
			}
			
//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_Maximum_NumberOfAmounts
			
			if(requirement.equalsIgnoreCase("Testing for Surcharges limit"))
			{
				driver.findElement(By.xpath("//div[5]//div//div//div//div//table//tr//td[3]//input[@type='submit'][@value='Add Lines']//..//..//td[2]//input")).sendKeys("150");
				driver.findElement(By.xpath("//div[5]//div//div//div//div//table//tr//td[3]//input[@type='submit'][@value='Add Lines']")).click();

				//Selecting Surcharge Name
				int i=2;
				for(;i<=75;i++) 
				{
					Select srchrg1 = new Select(driver.findElement(By.xpath("//*[contains(@id,'surCharge')]//..//..//..//..//tbody/tr[" + i + "]/td[2]//select")));
					srchrg1.selectByIndex(i-1);
				}
				
				for(int j=1;j<=76;j++) 
				{	
					Select srchrg1 = new Select(driver.findElement(By.xpath("//*[contains(@id,'surCharge')]//..//..//..//..//tbody/tr["+ i + "]/td[2]//select")));
					srchrg1.selectByIndex(j);
					i++;
				}

				//Selecting minimum and maximum Weight

				for(int k=2;k<=75;k++)
				{
					driver.findElement(By.xpath("//*[contains(@id,'surchargedropdown')]//..//..//..//tr["+k+"]//td[4]//input")).clear();
					driver.findElement(By.xpath("//*[contains(@id,'surchargedropdown')]//..//..//..//tr["+k+"]//td[4]//input")).sendKeys(maxwt);
				}
				for(int l=76;l<=151;l++)
				{
					driver.findElement(By.xpath("//*[contains(@id,'surchargedropdown')]//..//..//..//tr["+l+"]//td[3]//input")).clear();
					driver.findElement(By.xpath("//*[contains(@id,'surchargedropdown')]//..//..//..//tr["+l+"]//td[3]//input")).sendKeys(minWt1);
					driver.findElement(By.xpath("//*[contains(@id,'surchargedropdown')]//..//..//..//tr["+l+"]//td[4]//input")).clear();
					driver.findElement(By.xpath("//*[contains(@id,'surchargedropdown')]//..//..//..//tr["+l+"]//td[4]//input")).sendKeys(maxWt1);
				}

				//Selecting Discount Type

				for(int m=2;m<=75;m++)
				{
					Select tpe1 = new Select(driver.findElement(By.xpath("//*[contains(@id,'RateType11')]//..//..//..//..//tbody//tr[" + m + "]//td[5]//select")));
					tpe1.selectByIndex(11);
					Thread.sleep(5000);
				}
				for(int n=76;n<=151;n++)
				{
					Select tpe1 = new Select(driver.findElement(By.xpath("//*[contains(@id,'RateType11')]//..//..//..//..//tbody//tr[" + n + "]//td[5]//select")));
					tpe1.selectByIndex(12);
					Thread.sleep(5000);
				}

				Actions xyz = new Actions(driver);
				xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
				Thread.sleep(5000);
				
				xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
				Thread.sleep(5000);
				
				if(driver.findElement(By.xpath("//div//div//div//ul//li//span[text()='Maximum of 150 Surcharge rows has been exceeded']")).isDisplayed())
				{
					driver.findElement(By.xpath("//*[contains(@id,'surCharge')]//..//..//..//..//tbody//tr[2]//td[1]//input")).click();
					driver.findElement(By.xpath("//div[5]//div//div//div//div//table//tr//td[1]//input[@type='submit'][@value='Delete Selected Lines']")).click();
				}
			}

//test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_DiscountPercent_UnallowableFields
			
			if(discountType.contentEquals("FLAT_CHARGE") || discountType.contentEquals("DISCOUNT_PERCENT"))
			{
				driver.findElement(By.xpath(driverScript.objRepository("MaxAmount",file))).sendKeys("11111.11");
				driver.findElement(By.xpath(driverScript.objRepository("MinAmount",file))).sendKeys("99999.99");

				Actions xyz = new Actions(driver);
				xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
				Thread.sleep(5000);
				
				xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
				Thread.sleep(5000);

				if(driver.findElement(By.xpath("//ul/li/span[text()='Minimum Charge or Maximum Charge may not be entered for Discount % or Flat Charge']")).isDisplayed())
					Reporter.log("Error Message: Minimum Charge or Maximum Charge may not be entered for Discount % or Flat Charge");

				driver.findElement(By.xpath(driverScript.objRepository("MaxAmount",file))).clear();
				driver.findElement(By.xpath(driverScript.objRepository("MinAmount",file))).clear();
			}

//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_AppliesAllIncrease-Decrease
			if(increasedecrease2.contentEquals("I"))
			{
				incDec.selectByValue("I");
				driver.findElement(By.xpath(driverScript.objRepository("DiscApplyAll",file))).clear();

				Actions xyz = new Actions(driver);
				xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
				Thread.sleep(5000);
				
				xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
				Thread.sleep(5000);

				if(driver.findElement(By.xpath("//div//div//div//ul//li//span[text()='Discount Apply To all: Required field was not entered']")).isDisplayed())
					Reporter.log("Error Message: Discount Apply To all: Required field was not entered");
				Thread.sleep(4000);
				driver.findElement(By.xpath(driverScript.objRepository("DiscApplyAll",file))).sendKeys("100.753");
			}
			
//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_IndividualSurchargeNotAllowed

			if(discountType.contentEquals("DISCOUNT_PERCENT"))
			{
				driver.findElement(By.xpath(driverScript.objRepository("DiscApplyAll",file))).clear();
				driver.findElement(By.xpath(driverScript.objRepository("DiscApplyAll",file))).sendKeys(discApplyAll);

				Actions xyz = new Actions(driver);				
				xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
				Thread.sleep(5000);
				
				xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
				Thread.sleep(5000);

				if(driver.findElement(By.xpath("//div//div//div//ul/li/span[text()='May not enter both Discount Apply to all and individual surcharge']")).isDisplayed())
					Reporter.log("Error Message: May not enter both Discount Apply to all and individual surcharge");
				driver.findElement(By.xpath(driverScript.objRepository("DiscApplyAll",file))).clear();
			

				if(surcharge.equalsIgnoreCase(""))
				{
					System.out.println(srchrg.getAllSelectedOptions());
					srchrg.selectByValue(" ");
		
					xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
					Thread.sleep(5000);
				
					xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
					Thread.sleep(5000);
				
					if(driver.findElement(By.xpath("//div//div//div//ul/li/span[text()='May not enter both Discount Apply to all and individual surcharge']")).isDisplayed())
						Reporter.log("Error Message: May not enter both Discount Apply to all and individual surcharge");
					driver.findElement(By.xpath(driverScript.objRepository("DiscApplyAll",file))).clear();
					incDec.getOptions();
					incDec.selectByIndex(0);

					xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
					Thread.sleep(5000);

					xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
					Thread.sleep(5000);
				
					if(driver.findElement(By.xpath("//div//div//div//ul/li/span[text()='Surcharge Required field was not entered']")).isDisplayed())
						Reporter.log("Error Message: Surcharge Required field was not entered");
					srchrg.selectByVisibleText(surcharge);
				}
			}

			if(!discountType.contentEquals("WAIVED"))
			{
				driver.findElement(By.xpath("//select[contains(@id,'1:surchargedropdown')]//..//..//td[6]//input")).clear();

				Actions xyz = new Actions(driver);				
				xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
				Thread.sleep(5000);
				
				xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
				Thread.sleep(5000);

				if(driver.findElement(By.xpath("//div//div//div//ul/li/span[text()='Discount/Rate Amount is required when type is not waived']")).isDisplayed())
					Reporter.log("Error Message: Discount/Rate Amount is required when type is not waived");

				driver.findElement(By.xpath("//select[contains(@id,'1:surchargedropdown')]//..//..//td[6]//input")).sendKeys(amount);
			}

			
//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_DiscountRate_AmountValidation
			if(discountType.contentEquals("WAIVED"))
			{
				driver.findElement(By.xpath("//select[contains(@id,'1:surchargedropdown')]//..//..//td[6]//input")).clear();
				driver.findElement(By.xpath("//select[contains(@id,'1:surchargedropdown')]//..//..//td[6]//input")).sendKeys(amount);

				Actions xyz = new Actions(driver);				
				xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
				Thread.sleep(10000);
				
				xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
				Thread.sleep(5000);

				if(driver.findElement(By.xpath("//div//div//div//ul/li/span[text()='Surcharge Amount may not be entered if Surcharge Waived']")).isDisplayed())
					Reporter.log("Error Message: Surcharge Amount may not be entered if Surcharge Waived");

				driver.findElement(By.xpath("//select[contains(@id,'1:surchargedropdown')]//..//..//td[6]//input")).clear();
			}			

			if(discountType.contentEquals("RATE_PER_ACTUAL_UNIT_WEIGHT") && unit.contentEquals("CWT"))
			{
				driver.findElement(By.xpath("//select[contains(@id,'1:surchargedropdown')]//..//..//td[6]//input")).clear();
				driver.findElement(By.xpath("//select[contains(@id,'1:surchargedropdown')]//..//..//td[6]//input")).sendKeys("-80");

				Actions xyz = new Actions(driver);
				xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
				Thread.sleep(5000);
				
				xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
				Thread.sleep(5000);

				if(driver.findElement(By.xpath("//span[contains(@id,'warningDialog_main')]")).isDisplayed())
					driver.findElement(By.xpath("//input[@id='fxfSurchargeDetailForm:ok']")).click();

				driver.findElement(By.xpath("//select[contains(@id,'1:surchargedropdown')]//..//..//td[6]//input")).clear();
				driver.findElement(By.xpath("//select[contains(@id,'1:surchargedropdown')]//..//..//td[6]//input")).sendKeys(amount);
			}
			
			if(discountType.contentEquals("RATE_PER_ACTUAL_UNIT_WEIGHT") && !unit.contentEquals("CWT"))
			{
				WebElement checkRMFlag = driver.findElement(By.xpath(driverScript.objRepository("RateManually",file)));

					Assert.assertFalse(checkRMFlag.isEnabled());
					Reporter.log("Error Message: Rate Manually Flag is disabled for NonCWT unit");
			}
			
//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_UnitOfMeasurePerRate_UnitOfMeasure
			
			if(discountType.contentEquals("RATE_PER_ACTUAL_UNIT_WEIGHT"))
			{
				if(driver.findElement(By.xpath(driverScript.objRepository("Unit",file))).isEnabled())
					Reporter.log("Unit of Measure for Rate per Unit of Measure/Dimensions Unit Type is present");
				unt.selectByValue(unit);
				unt.getAllSelectedOptions();
			}
			
			if(!discountType.contentEquals("RATE_PER_ACTUAL_UNIT_WEIGHT"))
			{
				WebElement checkUnit = driver.findElement(By.xpath(driverScript.objRepository("Unit",file)));
				Assert.assertFalse(checkUnit.isEnabled());
				Reporter.log("Error Message: Unit is disabled for a type other than Rate / Unit of Measure");
			}


//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_DiscountPercent_Increase-DecreaseFlag
			if(discountType.contentEquals("DISCOUNT_PERCENT"))
			{
				incDec1.selectByIndex(0);
				Actions xyz = new Actions(driver);
				xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
				Thread.sleep(5000);
				
				xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
				Thread.sleep(5000);

				if(driver.findElement(By.xpath("//div//div//div//ul/li/span[text()='Increase/Decrease must be entered for Discount %']")).isDisplayed())
					Reporter.log("Error Message: Increase/Decrease must be entered for Discount %");
				incDec1.selectByValue(increasedecrease1);
			}

			if(!discountType.contentEquals("DISCOUNT_PERCENT"))
			{
				incDec1.selectByValue(increasedecrease1);
				Actions xyz = new Actions(driver);
				xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
				Thread.sleep(5000);

				xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
				Thread.sleep(5000);
				
				if(driver.findElement(By.xpath("//div//div//div//ul/li/span[text()='Increase/Decrease may only be entered for Discount %']")).isDisplayed())
					Reporter.log("Error Message: Increase/Decrease may only be entered for Discount %");
				incDec1.selectByIndex(0);
			}

//test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_DimensionsUnitType
			if(discountType.contentEquals("RATE_PER_DIMENSION"))
			{
				unt.selectByIndex(0);
				Actions xyz = new Actions(driver);
				xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
				Thread.sleep(5000);
				
				xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
				Thread.sleep(5000);

				if(driver.findElement(By.xpath("//div//div//div//ul/li/span[text()='Surcharge Unit: Required field was not entered']")).isDisplayed())
					Reporter.log("Error Message: Surcharge Unit: Required field was not entered");
				unt.selectByValue(unit);
				unt.getAllSelectedOptions();
			}

			if(!discountType.contentEquals("RATE_PER_DIMENSION"))
			{
				if(!driver.findElement(By.xpath("//span[Text()='Unit']")).isEnabled())
					Reporter.log("Message: Dimension Unit field is not enabled for Type other than Rate / Dimension");
				else
					Reporter.log("Message: Unit field is enabled for RATE_PER_ACTUAL_UNIT_WEIGHT");
			}

			if(!discountType.contentEquals("TOTAL_CHARGE_PERCENT"))
			{
				if(!driver.findElement(By.xpath(driverScript.objRepository("Rank",file))).isEnabled())
					Reporter.log("Message: Rank can only be provided for discount type Total Charge %'");
			}


		}catch(Exception e){
			Reporter.log("Message: Regression Testing of GUI is not being performed now");
		}

		
		
		
										/**
										 * Codes for only GUI validation ends
										 */
		
		
		

		Actions b = new Actions(driver);
		b.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
		//WebElement  element=driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)));
//		JavascriptExecutor executor = (JavascriptExecutor)driver;
//		executor.executeScript("arguments[0].click();", element);
//		Thread.sleep(20000);

		try{
			driver.findElement(By.xpath(driverScript.objRepository("Warning",file))).isDisplayed();
			driver.findElement(By.xpath(driverScript.objRepository("WarningConfirm",file))).click();
			Thread.sleep(5000);
			Actions c = new Actions(driver);
			c.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
			Thread.sleep(20000);
		}catch (Exception e){
			Reporter.log("Pricing change exists for the CENIs selected");
		}

		try{
			driver.findElement(By.xpath(driverScript.objRepository("Overlay",file))).click();
		}catch(Exception e){
			Reporter.log("Overlay button doesn't exist");
		}

		Thread.sleep(10000);
	}


	/**createProposal()----- This method creates the proposal ID and copies it into the excel sheet. 
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	private void createProposal() throws InterruptedException, IOException {
		driver.findElement(By.xpath(driverScript.objRepository("PricingContinue",file))).click();
		Thread.sleep(20000);
		String ProposalID;
		ProposalID = driver.findElement(By.name(driverScript.objRepository("proposalNo",file))).getAttribute("value");
		Reporter.log(ProposalID);
		try{
			DriverScript.setVariable("proposalID",worksheet,row,ProposalID);
		}catch(Exception e){
			LOGGER.error("Couldn't capture the Proposal ID"+ e.getMessage());
		}
		Reporter.log("Surchages detail screen is displayed");
		driver.findElement(By.xpath(driverScript.objRepository("ReqNo",file))).sendKeys(requestName);
		Thread.sleep(2000);
		driver.findElement(By.xpath(driverScript.objRepository("AgreeNo",file))).sendKeys(agreementNumber);
		Thread.sleep(2000);
		driver.findElement(By.xpath(driverScript.objRepository("SaveSubmit",file))).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath(driverScript.objRepository("Confirm",file))).click();
		Thread.sleep(30000);
		try{
			driver.findElement(By.xpath(driverScript.objRepository("Confirmation",file))).isDisplayed();
			result("PASS");	
		}catch(Exception e){
			result("Fail");

		}
	}


	/**logout()----- This method logs out of the application.
	 * @throws InterruptedException
	 */
	private void logOut() throws InterruptedException
	{
		driver.findElement(By.xpath(driverScript.objRepository("Logout",file))).click();
		Reporter.log("PMGUI application terminates here");
		Thread.sleep(10000);
		//		driver.close();
	}

	/**setVariable(XSSFSheet Sheet,XSSFRow r) -----
	 * 	This method gets the test data from test data sheet.
	 * @param Sheet
	 * @param r
	 */
	@SuppressWarnings("static-access")
	private void setVariable(XSSFSheet Sheet,XSSFRow r)   //Get test data from the test data sheet
	{
		DriverScript driverScript=new DriverScript();

		id= driverScript.getVariable("ID",Sheet,r);
		password= driverScript.getVariable("password",Sheet,r);
		region= driverScript.getVariable("Region",Sheet,r);
		serviceTab= driverScript.getVariable("ServiceTab",Sheet,r);
		serviceGroup= driverScript.getVariable("ServiceGroup",Sheet,r);
		country= driverScript.getVariable("Country",Sheet,r);
		accountType= driverScript.getVariable("AccountType",Sheet,r);
		accountNumber = driverScript.getVariable("AccountNumber",Sheet,r);
		selectService = driverScript.getVariable("selectService",Sheet,r);
		status=driverScript.getVariable("Status",Sheet,r);
		accountNumber=driverScript.getVariable("AccountNumber",Sheet,r);
		copyService=driverScript.getVariable("Copy Service",Sheet,r);
		pricingDiscountType=driverScript.getVariable("Pricing_DiscountType",Sheet,r);
		pricingSummary=driverScript.getVariable("PricingSummary",Sheet,r);
		proposalNbr=driverScript.getVariable("ProposalNbr",Sheet,r);
		startDate=driverScript.getVariable("StartDate",Sheet,r);
		endDate=driverScript.getVariable("EndDate",Sheet,r);
		minimumWeight =driverScript.getVariable("Minimum_Weight",Sheet,r);
		maxWeight =driverScript.getVariable("Max_Weight",Sheet,r);
		stateMatrix=driverScript.getVariable("State_Matrix",Sheet,r);
		currencyType=driverScript.getVariable("CurrencyType",Sheet,r);
		subjGRI=driverScript.getVariable("Subj_GRI",Sheet,r);
		plusminus1=driverScript.getVariable("Plus_minus1",Sheet,r);
		dir1=driverScript.getVariable("Dir1",Sheet,r);
		type1=driverScript.getVariable("Type1",Sheet,r);
		name1=driverScript.getVariable("Name1",Sheet,r);
		state1=driverScript.getVariable("state1",Sheet,r);
		county=driverScript.getVariable("County",Sheet,r);
		country1=driverScript.getVariable("Country1",Sheet,r);
		plusminus2=driverScript.getVariable("Plus_minus2",Sheet,r);
		dir2=driverScript.getVariable("Dir2",Sheet,r);
		type2=driverScript.getVariable("Type2",Sheet,r);
		name2=driverScript.getVariable("Name2",Sheet,r);
		state2=driverScript.getVariable("state2",Sheet,r);
		county2=driverScript.getVariable("County2",Sheet,r);
		country2=driverScript.getVariable("Country2",Sheet,r);
		servicedayslow=driverScript.getVariable("service_days_low",Sheet,r);
		servicedayshigh=driverScript.getVariable("service_days_high",Sheet,r);
		exceptionClassAll=driverScript.getVariable("ExceptionClass_All",Sheet,r);
		excptclassmaxwgt=driverScript.getVariable("Excpt_class_max_wgt",Sheet,r);
		addLines=driverScript.getVariable("AddLines",Sheet,r);
		type=driverScript.getVariable("Type",Sheet,r);
		class_NMFC_LR=driverScript.getVariable("Class_NMFC_LR",Sheet,r);
		class_NMFC_HR=driverScript.getVariable("Class_NMFC_HR",Sheet,r);
		exceptionClass=driverScript.getVariable("ExceptionClass",Sheet,r);
		discApplyAll=driverScript.getVariable("Disc_Apply_All",Sheet,r);
		increasedecrease2=driverScript.getVariable("Increase_Decrease",Sheet,r);
		requirement=driverScript.getVariable("Requirement",Sheet,r);
		surcharge=driverScript.getVariable("Surcharge",Sheet,r);
		discountType=driverScript.getVariable("DiscountType",Sheet,r);
		minWt=driverScript.getVariable("Min_Wt",Sheet,r);
		maxwt=driverScript.getVariable("max_wt",Sheet,r);
		addLinesSurcharges=driverScript.getVariable("addLinesSurcharges",Sheet,r);
		minWt1=driverScript.getVariable("minWt1",Sheet,r);
		maxWt1=driverScript.getVariable("maxWt1",Sheet,r);
		amount=driverScript.getVariable("amount",Sheet,r);
		increasedecrease1=driverScript.getVariable("I_D",Sheet,r);
		rank=driverScript.getVariable("Rank",Sheet,r);
		rank1=driverScript.getVariable("Rank1",Sheet,r);
		unit=driverScript.getVariable("Unit",Sheet,r);
		minamount=driverScript.getVariable("min_amount",Sheet,r);
		maxamount=driverScript.getVariable("max_amount",Sheet,r);
		mnl=driverScript.getVariable("Mnl",Sheet,r);
		comments=driverScript.getVariable("Comments",Sheet,r);
		requestName = driverScript.getVariable("RequestName",Sheet,r);
		agreementNumber=driverScript.getVariable("AgreementNumber",Sheet,r);

	}

	/** result(final String Status)----- This method prints the final status of the test cases
	 * into the excel sheet.
	 * @param Status
	 * @throws IOException
	 */
	public void result(final String Status) throws IOException
	{	
		try{
			DriverScript.setVariable("Status",worksheet,row,Status);
		}
		catch(Exception e)
		{
			LOGGER.error("Couldn't print the status in the excelsheet"+ e.getMessage());
		}
		FileOutputStream fileOut =  new FileOutputStream(DriverScript.workSheetPath);
		workbook.write(fileOut);
		fileOut.close();
	}

}

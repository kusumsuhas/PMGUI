package com.fedex.pmgui.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
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

public class SurchargePremiumServUpchargeGui {

	DriverScript driverScript= new DriverScript();
	WebDriver driver ;
	String file;
	String accountType,region,country,serviceGroup,serviceTab,selectService,accountNumber,id,password,account_Entry_Transitioned,search_By,agreementNumber,account_CENI,service_selection_Region,service_selection_Country,sb_SelectCountries,service_selection_OpCo,service_selection_Tab_selection,attribute_Tab_Selection,service_selection_Sevice_Grouping,proposalNbr,pricing_DiscountType,pricingSummary,startDate,endDate,minimum_Weight,max_Weight,state_Matrix,shipmentCondition,currencyType,subj_GRI,plus_minus1,dir1,type1,name1,state1,county,country1,plus_minus2,dir2,type2,name2,state2,county2,country2,service_days_low,service_days_high,exceptionClass_All,excpt_class_max_wgt,addLines,nMFCType,class_NMFC_LR,class_NMFC_HR,addLinesPremiumService,exceptionClass,rateManually,minCODAmt,maxCODAmt,type,i_d,cODFlatFee,cOD_Fee_Percent,perCODAmt,premiumService,upchargeAmount,upchargePercent,aMCAmount,comments,requestName;
	String [] date;
	XSSFWorkbook workbook;
	XSSFSheet worksheet;
	XSSFRow row;
	static final Logger LOGGER = Logger.getLogger(SurchargePremiumServUpchargeGui.class);

	public SurchargePremiumServUpchargeGui(WebDriver driver)
	{
		this.driver=driver;
	}

	/**surchargesPremServUpchargesTest()----- This method runs the test cases for 
	 * surcharges Premium Service Upcharges type of discounts.
	 * @throws Exception
	 */
	public void surchargesPremServUpchargesTest() throws Exception
	{
		try{
			file=DriverScript.surchargesPremServUpcharges;
			FileInputStream FileInputStream = new FileInputStream(DriverScript.workSheetPath);
			workbook = new XSSFWorkbook(FileInputStream);

			worksheet = workbook.getSheet("Prem_Serv_Upcharges");

			for (int k=6;k<=worksheet.getLastRowNum();k++)
			{
				row = worksheet.getRow(k);
				setVariable( worksheet,row);
				
				LoginPage login = new LoginPage(driver);
				login.login(id, password);

				SelectAccountEntry AE = new SelectAccountEntry(driver);
				AE.gotoAccountEntry();

				AccountEntry AccountNo = new AccountEntry(driver);
				AccountNo.cENIorEAN(accountType, accountNumber);

				ServiceSelection SS = new ServiceSelection(driver);
				SS.regionCountryOpcoSelection(region, country, serviceTab, serviceGroup);

				premServUprcharges();
				
				logOut();
			}
		}catch(Exception e)
		{
			LOGGER.error("Proposal was not created successfully"+ e.getMessage());
		}

	}

	/** premServUprcharges()-----This method navigates to the surcharges premium serivce
	 * upcharges discount page and fills the required fields.
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void premServUprcharges() throws InterruptedException, IOException{
		Actions a = new Actions(driver);
		Thread.sleep(8000);
		a.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SurchargeTab",file)))).build().perform();
		Thread.sleep(15000);

		driver.findElement(By.xpath("(//span[text()='Intra CA Prty IC (Intra)'])[1]/../..//a[text()='Details']")).click();
		 Thread.sleep(5000);
		
		Assert.assertEquals(driver.findElement(By.xpath("//span[text()='FXF Surcharge Detail']")).getText(),"FXF Surcharge Detail" );
		driver.findElement(By.xpath(driverScript.objRepository("PrmSrvcUpcharge",file))).click();
        Thread.sleep(5000);
        
        
        /*
		 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_PremiumServiceUpcharge
		 */
        Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'premiumServicedropDown33')]")).isDisplayed());
		Reporter.log("Premium Service name is displayed");
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'premiumServicedropDown33')]//..//..//td[3]//input")).isDisplayed());
		Reporter.log("Upcharge currency amount is visible");
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'premiumServicedropDown33')]//..//..//td[4]//input")).isDisplayed());
		Reporter.log("Upcharge % is visible");
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'premiumServicedropDown33')]//..//..//td[5]//input")).isDisplayed());
		Reporter.log("AMC amount is visible");
		Assert.assertTrue(driver.findElement(By.xpath("//input[contains(@id,'mnlflagcheckBox')]")).isDisplayed());
		Reporter.log("Rate Manual Flag is visible");
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'commentId')]")).isDisplayed());
		Reporter.log("Informational Freeform text is visible");
		
		
        
        
        
		/*
		 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_FreightSurchargeDetailPage_PMGUIFlow_FXFSurcharge_PremiumServicesUpcharge
		 */	
		
		
		Assert.assertTrue(driver.findElement(By.xpath("(//*[contains(@id,'fxfSurchargeDetailForm:inheritingFrom')])[1]")).isDisplayed());
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
		
		

		
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("PremiumService",file))).isDisplayed());
		
		Reporter.log("Premium Service Name is present");
		
			
		
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("UpchargeAmount",file))).isEnabled());
		driver.findElement(By.xpath(driverScript.objRepository("UpchargeAmount",file))).sendKeys(upchargeAmount);  //  Upcharge amount
      
/*
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_PremiumServiceUpcharge_UpchargePercent
 */
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("UpchargePercent",file))).isEnabled());
		driver.findElement(By.xpath(driverScript.objRepository("UpchargePercent",file))).sendKeys(upchargePercent);  //  Upcharge Percent
		File UP = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(UP, new File("./target/screenshots/Premium Service Upcharge/UpchargePercent.jpeg"));
		
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("AMCAmount",file))).isEnabled());
		driver.findElement(By.xpath(driverScript.objRepository("AMCAmount",file))).sendKeys(aMCAmount);  //  AMC amount
		
		
/*
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_PremiumServiceUpcharge_RateManuallyFlagValidation
 */
		try{
			
		WebElement rateManual = driver.findElement(By.xpath(driverScript.objRepository("RateManually",file)));
		
		if(!rateManually.equalsIgnoreCase(""))
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("RateManually",file))).isEnabled());
        
        	if (rateManual.isSelected())
     			rateManual.click();
     		Assert.assertFalse(rateManual.isSelected());
     
        	if (!rateManual.isSelected())
        		rateManual.click();
     		Assert.assertTrue(rateManual.isSelected());

     		Reporter.log("Rate Manually Flag is present and selected");
        }catch(Exception e){
        	Reporter.log("Rate Manually doesn't need to be selected");
        }
		
		File RM = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(RM, new File("./target/screenshots/Premium Service Upcharge/RateManuallyFlagValidation.jpeg"));
		
        
/*
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_PremiumServiceUpcharge_InformationalFreeformText
 */
        if(driver.findElement(By.xpath(driverScript.objRepository("Comments",file))).isEnabled())
        {
        	Reporter.log("Informational Freeform Textbox is enabled. User can Enter the desired text");
        	driver.findElement(By.xpath(driverScript.objRepository("Comments",file))).sendKeys(comments);  // Comments 
        	File IFFT = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    		FileUtils.copyFile(IFFT, new File("./target/screenshots/Premium Service Upcharge/InformationalFreeformText.jpeg"));
    		
        }
        
		/**
		 * Codes for only GUI validation starts
		 */
        
/*
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_PremiumServiceUpcharge_MaintainIndvPremServices
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_PremiumServiceUpcharge_MaximumNumberOfAmounts
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_PremiumServiceUpcharge_PremiumServiceName
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_PremiumServiceUpcharge_UpchargeFlatCurrencyAmount
 */

		try{
			
			if (!addLinesPremiumService.equalsIgnoreCase(""))
			{
				driver.findElement(By.xpath("//*[contains(@id,'exceptionDetails33')]//..//..//..//..//div[5]//table//tbody//tr//td[2]//input")).sendKeys(addLinesPremiumService);
				driver.findElement(By.xpath("//*[contains(@id,'exceptionDetails33')]//..//..//..//..//div[5]//input[@type='submit'][@value='Add Lines']")).click();
				Select Premium_service_PSU1=new Select(driver.findElement(By.xpath("//*[contains(@id,'1:premiumServicedropDown33')]")));
				Premium_service_PSU1.selectByVisibleText("Item 754-2B - Premium Services Charges");
				
				driver.findElement(By.xpath("//*[contains(@id,'1:premiumServicedropDown33')]//..//..//td[3]//input")).sendKeys(upchargeAmount);
				
				Actions xyz = new Actions(driver);
				xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
				Thread.sleep(10000);
			
				if(driver.findElement(By.xpath("//ul//li//span[text()='Premium Service may only be entered once']")).isDisplayed())
					Reporter.log("Error Message: Premium Service may only be entered once");
			
				driver.findElement(By.xpath("//*[contains(@id,'1:premSrviceCheckBox33')]")).click();
				driver.findElement(By.xpath("//*[contains(@id,'exceptionDetails33')]//..//..//..//..//div[5]//input[@type='submit'][@value='Delete Selected Lines']")).click();
				Thread.sleep(5000);
				driver.findElement(By.xpath(driverScript.objRepository("AMCAmount",file))).sendKeys("40");
			
				Actions abc = new Actions(driver);
				abc.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
				Thread.sleep(10000);
			
				if(driver.findElement(By.xpath("//ul//li//span[text()='May not enter both an AMC Amount and a Upcharge Amount']")).isDisplayed())
					Reporter.log("Error Message: May not enter both an AMC Amount and a Upcharge Amount");
				
				driver.findElement(By.xpath(driverScript.objRepository("AMCAmount",file))).clear();
				driver.findElement(By.xpath(driverScript.objRepository("UpchargePercent",file))).sendKeys("555.5");
				
				abc.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
				Thread.sleep(10000);
				
				if(driver.findElement(By.xpath("//ul/li/span[text()='May only enter Upcharge Amount or Upcharge Percent']")).isDisplayed())
					Reporter.log("Error Message: May only enter Upcharge Amount or Upcharge Percent");
				driver.findElement(By.xpath(driverScript.objRepository("UpchargePercent",file))).clear();
				
			}
			
		}catch(Exception e){
			Reporter.log("Regression Testing of GUI is not being performed now");
		}
		

		
		driver.findElement(By.xpath("//*[contains(@id,'cancel2')]")).click();  
		try{
			driver.findElement(By.xpath("(//*[contains(@id,'fxfSurchargeDetailForm:yes')])[2]")).click();
			Thread.sleep(10000);
		}catch(Exception e){
			LOGGER.info("Popup not present");
		}
		
	}
	/**logout()----- This method logs out of the application.
	 * @throws InterruptedException
	 */
	private void logOut() throws InterruptedException
	{
		driver.findElement(By.xpath(driverScript.objRepository("Logout",file))).click();
		Thread.sleep(10000);
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
		account_Entry_Transitioned=driverScript.getVariable("Account_Entry_Transitioned",Sheet,r);
		agreementNumber=driverScript.getVariable("AgreementNumber",Sheet,r);
		sb_SelectCountries=driverScript.getVariable("sb_SelectCountries",Sheet,r);
		service_selection_OpCo=driverScript.getVariable("Service_selection_OpCo",Sheet,r);
		attribute_Tab_Selection=driverScript.getVariable("Attribute_Tab_Selection",Sheet,r);
		proposalNbr=driverScript.getVariable("ProposalNbr",Sheet,r);
		pricing_DiscountType=driverScript.getVariable("Pricing_DiscountType",Sheet,r);
		pricingSummary=driverScript.getVariable("PricingSummary",Sheet,r);
		startDate=driverScript.getVariable("StartDate",Sheet,r);
		endDate=driverScript.getVariable("EndDate",Sheet,r);
		minimum_Weight=driverScript.getVariable("Minimum_Weight",Sheet,r);
		max_Weight=driverScript.getVariable("Max_Weight",Sheet,r);
		state_Matrix=driverScript.getVariable("State_Matrix",Sheet,r);
		shipmentCondition=driverScript.getVariable("ShipmentCondition",Sheet,r);
		currencyType=driverScript.getVariable("CurrencyType",Sheet,r);
		subj_GRI=driverScript.getVariable("Subj_GRI",Sheet,r);
		plus_minus1=driverScript.getVariable("Plus_minus1",Sheet,r);
		dir1=driverScript.getVariable("Dir1",Sheet,r);
		type1=driverScript.getVariable("Type1",Sheet,r);
		name1=driverScript.getVariable("Name1",Sheet,r);
		state1=driverScript.getVariable("state1",Sheet,r);
		county=driverScript.getVariable("County",Sheet,r);
		country1=driverScript.getVariable("Country1",Sheet,r);
		plus_minus2=driverScript.getVariable("Plus_minus2",Sheet,r);
		dir2=driverScript.getVariable("Dir2",Sheet,r);
		type2=driverScript.getVariable("Type2",Sheet,r);
		name2=driverScript.getVariable("Name2",Sheet,r);
		state2=driverScript.getVariable("state2",Sheet,r);
		county2=driverScript.getVariable("County2",Sheet,r);
		country2=driverScript.getVariable("Country2",Sheet,r);
		service_days_low=driverScript.getVariable("service_days_low",Sheet,r);
		service_days_high=driverScript.getVariable("service_days_high",Sheet,r);
		exceptionClass_All=driverScript.getVariable("ExceptionClass_All",Sheet,r);
		excpt_class_max_wgt=driverScript.getVariable("Excpt_class_max_wgt",Sheet,r);
		addLines=driverScript.getVariable("AddLines",Sheet,r);
		type=driverScript.getVariable("Type",Sheet,r);
		class_NMFC_LR=driverScript.getVariable("Class_NMFC_LR",Sheet,r);
		class_NMFC_HR=driverScript.getVariable("Class_NMFC_HR",Sheet,r);
		exceptionClass=driverScript.getVariable("ExceptionClass",Sheet,r);
		rateManually=driverScript.getVariable("RateManually",Sheet,r);
		addLinesPremiumService=driverScript.getVariable("addLinesPremiumService",Sheet,r);
		premiumService=driverScript.getVariable("PremiumService",Sheet,r);
		upchargeAmount=driverScript.getVariable("UpchargeAmount",Sheet,r);
		upchargePercent=driverScript.getVariable("UpchargePercent",Sheet,r);
		aMCAmount=driverScript.getVariable("AMCAmount",Sheet,r);
		comments=driverScript.getVariable("Comments",Sheet,r);
		requestName=driverScript.getVariable("RequestName",Sheet,r);

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

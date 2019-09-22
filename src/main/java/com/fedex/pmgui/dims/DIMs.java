package com.fedex.pmgui.dims;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import junit.framework.Assert;

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
import org.testng.Reporter;
import org.testng.log4testng.Logger;

import com.fedex.pmgui.common.AccountEntry;
import com.fedex.pmgui.common.AddServices;
import com.fedex.pmgui.common.DatePicker;
import com.fedex.pmgui.common.LoginPage;
import com.fedex.pmgui.common.SelectAccountEntry;
import com.fedex.pmgui.common.ServiceSelection;
import com.fedex.pmgui.driverscript.DriverScript;
import com.fedex.pmgui.volumediscounts.VDWgtBrkDiscAdj;


public class DIMs{

	DriverScript driverScript= new DriverScript();
	WebDriver driver ;
	String file;
	String id, password, accountType, accountNumber, region, country, serviceTab, serviceGroup, selectService, copyService, agreementNumber, proposalNumber,proposalType,geographyView,rateManually,alternationFlag,ratingClass, dIMFactor, minimumPCF,pCF1,pCF2,pCF3,pCF4,pCF5,pCF6,pCF7,pCF8,pCF9,pCF10,pCF11,pCF12,pCF13,pCF14,pCF15,pCF16,pCF17,pCF18,comments;
	String [] date;
	String requestName;
	XSSFWorkbook workbook;
	XSSFSheet worksheet;
	XSSFRow row;
	static final Logger LOGGER = Logger.getLogger(DIMs.class);

	public DIMs(WebDriver driver)
	{
		this.driver=driver;
	}

	/**dimsTest()----- This method runs the test cases for DIMs type of discounts.
	 * 
	 * @throws Exception
	 */
	public void dimsTest() throws Exception
	{
		try
		{
			file=DriverScript.dIMS;
			FileInputStream FileInputStream = new FileInputStream(DriverScript.workSheetPath);
			workbook = new XSSFWorkbook(FileInputStream);
			
			worksheet = workbook.getSheet("DIMs");
			for (int k=7;k<=worksheet.getLastRowNum();k++)
			{
				row = worksheet.getRow(k);
				setVariable( worksheet,row);
				
				LoginPage login = new LoginPage(driver);
				login.login(id, password);
				Thread.sleep(5000);

				SelectAccountEntry AE = new SelectAccountEntry(driver);
				AE.gotoAccountEntry();
				Thread.sleep(10000);

				AccountEntry AccountNo = new AccountEntry(driver);
				AccountNo.cENIorEAN(accountType, accountNumber);
				Thread.sleep(10000);

				ServiceSelection SS = new ServiceSelection(driver);
				SS.regionCountryOpcoSelection(region, country, serviceTab, serviceGroup);
				Thread.sleep(10000);

//				VDWgtBrkDiscAdj weightbreak = new VDWgtBrkDiscAdj(driver);
//				weightbreak.weightBrkDiscount(worksheet,row);
//				Thread.sleep(5000);

				dIMS();
				//createProposal();
				logOut();
			}

		}catch(Exception e)
		{
			LOGGER.error("Proposal was not created successfully"+ e.getMessage());
		}

	}

	/** dIMS()-----This method navigates to the DIMs discount page and fills the required fields. 
	 * 
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void dIMS() throws InterruptedException, IOException{

		Actions dimstab = new Actions(driver);
		dimstab.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("DIMsTab",file)))).build().perform();
		Thread.sleep(10000);
		
//GUI Validation of Copy Service starts here
		
/*
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_FreightVolume - WeightBreakDiscounts - CopyService - DIMs
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_FreightVolume - WeightBreakDiscounts - CopyService - DIMs - Edits
 */
		
				if (!copyService.equalsIgnoreCase(""))
				{
					
					//driver.findElement(By.xpath("//*[contains(@id,'summaryTabSetcnt')]//tr[1]//a[Text()='Details'][1]")).click();
					//driver.findElement(By.linkText("Details")).click();
					driver.findElement(By.xpath("((//span[text()='Intra US Prty IC (Intra)'])[1]/../..//a[text()='Details'])[1]")).click();
					Thread.sleep(5000);
					
					driver.findElement(By.xpath("//*[contains(@id,'copyservice')]")).click();
					Thread.sleep(3000);
					Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'confirmDialogcopy1_main')]//label[text()='If you Copy this Service with unsaved changes, the unsaved changes will not be copied.  Do you still want to Copy the Service']")).isDisplayed());
					Reporter.log("Message: If you Copy this Service with unsaved changes, the unsaved changes will not be copied.  Do you still want to Copy the Service");
					
					driver.findElement(By.xpath("//input[contains(@id,'noCopy1')]")).click();
					Assert.assertEquals(driver.findElement(By.xpath("//span[text()='DIM Details']")).getText(),"DIM Details");
					
					driver.findElement(By.xpath("//*[contains(@id,'copyservice')]")).click();
					Thread.sleep(3000);
					driver.findElement(By.xpath("//*[contains(@id,'yesCopy1')]")).click();
					Thread.sleep(3000);
					
					File UpdateSuccess = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
					FileUtils.copyFile(UpdateSuccess, new File("./target/screenshots/DIMs/Copy service.jpeg"));

					Actions selectService = new Actions(driver);
					selectService.doubleClick(driver.findElement(By.xpath("//li[contains(text(),'Intra US Prty TP (Intra)')]"))).build().perform();//service
					Thread.sleep(6000);
				
					//clicked on save
					driver.findElement(By.xpath("//*[contains(@id,'copyCancel1')]//..//..//td[2]//input")).click();
					WebElement  element = driver.findElement(By.xpath("//*[contains(@id,'copyCancel1')]//..//..//td[2]//input"));
					JavascriptExecutor executor = (JavascriptExecutor)driver;
					executor.executeScript("arguments[0].click();", element);
					Thread.sleep(5000);
		      
					
					if(driver.findElement(By.xpath("//*[contains(@id,'copyServiceDialog_main')]//form//div[1]//ul//li//span[text()='The following services already exists for all or part of the date range  ']")).isDisplayed())
					{
						Reporter.log("Error Message: The following services already exists for all or part of the date range");
						Actions selectService1 = new Actions(driver);
						selectService1.doubleClick(driver.findElement(By.xpath("//li[contains(text(),'Intra US Prty TP (Intra)')]"))).build().perform();//service
						Thread.sleep(3000);
//						selectService1.doubleClick(driver.findElement(By.xpath("//li[contains(text(),'Intra US Prty TP (Intra)')]"))).build().perform();//service
//						Thread.sleep(3000);
						Actions selService2 = new Actions(driver);
						selService2.doubleClick(driver.findElement(By.xpath("//li[contains(text(),'AK - OP')]"))).build().perform();
						Thread.sleep(6000);
						
						driver.findElement(By.xpath("//*[contains(@id,'copyCancel1')]//..//..//td[2]//input")).click();
						Thread.sleep(5000);
						
						File UpdateSuccess1 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						FileUtils.copyFile(UpdateSuccess1, new File("./target/screenshots/DIMs/Record has been updated successfully for new BDS.jpeg"));
						Reporter.log("Success message : Record has been updated successfully");
					}
					
					
					
					driver.findElement(By.xpath("//*[contains(@id,'copyCancel1')]")).click();
					Thread.sleep(3000);
					driver.findElement(By.xpath("//*[contains(@id,'cancelBottom')]")).click();
					Thread.sleep(3000);
					driver.findElement(By.xpath("//*[contains(@id,'confirmDialog_main')]//input[contains(@id,'yes')]")).click();
					Thread.sleep(5000);
					
				}
				
//GUI Validation of Copy Service ends here

		AddServices addSrvc = new AddServices(driver);
		addSrvc.addService(selectService);

		try {
			DatePicker d = new DatePicker(driver);
			date = d.selectDateDIMS();
			DriverScript.setVariable("StartDate",worksheet,row,date[0]);
			DriverScript.setVariable("EndDate",worksheet,row,date[1]);
		} catch (Exception e) {
			LOGGER.error("Didn't capture the startdate and enddate"+ e.getMessage());

		}

		Thread.sleep(5000);
		
/*
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_FreightDIMS_DIMSDataFields
 */
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'rateManlyLbl')]")).isDisplayed());
		Reporter.log("Rate Manually Label is present");
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("RateManually",file))).isEnabled());
		Select RM = new Select(driver.findElement(By.xpath(driverScript.objRepository("RateManually",file))));
		RM.selectByValue(rateManually);
		Thread.sleep(2000);
		

		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'AFlagLbl')]")).isDisplayed());
		Reporter.log("Alternate Flag Label is present");
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("AlternateFlag",file))).isEnabled());
		Select AlternateFlag = new Select(driver.findElement(By.xpath(driverScript.objRepository("AlternateFlag",file))));
		AlternateFlag.selectByValue(alternationFlag);
		Thread.sleep(2000);
		
/*
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_FreightDIMS_RatingClass
 */
		if(!ratingClass.equalsIgnoreCase(""))
		{
			Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("RatingClass",file))).isEnabled());
			Select RC = new Select(driver.findElement(By.xpath(driverScript.objRepository("RatingClass",file))));
			RC.selectByValue(ratingClass);
			RC.getAllSelectedOptions();
			Thread.sleep(2000);
		}

		if(driver.findElement(By.xpath("//*[contains(@id,'dimFctrTxt')]")).isDisplayed())
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("dimfact",file))).isEnabled());
		driver.findElement(By.xpath(driverScript.objRepository("dimfact",file))).sendKeys(dIMFactor);
		
		File RatingClass = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(RatingClass, new File("./target/screenshots/DIMs/RatingClass.jpeg"));
		
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'dimFctrUntTxt')]")).isDisplayed());
		Reporter.log("DIM Factor input box is present");
		
/*
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_FreightDIMS_MinimumPCF
 */
		if(driver.findElement(By.xpath("//*[contains(@id,'minimumPCFTxt')]")).isDisplayed())
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("MinPCF",file))).isEnabled());
		Reporter.log("Minimum PCF is enabled");
		driver.findElement(By.xpath(driverScript.objRepository("MinPCF",file))).sendKeys(minimumPCF);
		
		File MinimumPCF = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(MinimumPCF, new File("./target/screenshots/DIMs/MinimumPCF.jpeg"));
		
/*
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_FreightDIMS_DIMFactor
 */
		if(!dIMFactor.equalsIgnoreCase("") && !minimumPCF.equalsIgnoreCase(""))
			Assert.assertTrue(driver.findElement(By.xpath("//ul/li//span[text()='Both Dim Factor and Minimum PCF may not be entered']")).isDisplayed());
		
		if(dIMFactor.equalsIgnoreCase("") && minimumPCF.equalsIgnoreCase(""))
			Assert.assertTrue(driver.findElement(By.xpath("//ul/li//span[text()='Either Dim Factor or Minimum PCF is required']")).isDisplayed());
		
		File DIMFactor = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(DIMFactor, new File("./target/screenshots/DIMs/DIMFactor.jpeg"));
/*
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_FreightDIMS_PoundsPerCubicFoot_EachClass
 */
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'classClmLbl')]")).isDisplayed());
		Reporter.log("Class label is displayed");
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'pcfClmLbl')]")).isDisplayed());
		Reporter.log("PCF label is displayed");
		
		if(!pCF1.equalsIgnoreCase(""))
		{
			driver.findElement(By.xpath("//*[contains(@id,'PCFDetails:0:pcfValTxt')]")).sendKeys(pCF1);
			driver.findElement(By.xpath("//*[contains(@id,'PCFDetails:1:pcfValTxt')]")).sendKeys(pCF2);
			driver.findElement(By.xpath("//*[contains(@id,'PCFDetails:2:pcfValTxt')]")).sendKeys(pCF3);
			driver.findElement(By.xpath("//*[contains(@id,'PCFDetails:3:pcfValTxt')]")).sendKeys(pCF4);
			driver.findElement(By.xpath("//*[contains(@id,'PCFDetails:4:pcfValTxt')]")).sendKeys(pCF5);
			driver.findElement(By.xpath("//*[contains(@id,'PCFDetails:5:pcfValTxt')]")).sendKeys(pCF6);
			driver.findElement(By.xpath("//*[contains(@id,'PCFDetails:6:pcfValTxt')]")).sendKeys(pCF7);
			driver.findElement(By.xpath("//*[contains(@id,'PCFDetails:7:pcfValTxt')]")).sendKeys(pCF8);
    		driver.findElement(By.xpath("//*[contains(@id,'PCFDetails:8:pcfValTxt')]")).sendKeys(pCF9);
    		driver.findElement(By.xpath("//*[contains(@id,'PCFDetails:9:pcfValTxt')]")).sendKeys(pCF10);
    		driver.findElement(By.xpath("//*[contains(@id,'PCFDetails:10:pcfValTxt')]")).sendKeys(pCF11);
    		driver.findElement(By.xpath("//*[contains(@id,'PCFDetails:11:pcfValTxt')]")).sendKeys(pCF12);
    		driver.findElement(By.xpath("//*[contains(@id,'PCFDetails:12:pcfValTxt')]")).sendKeys(pCF13);
    		driver.findElement(By.xpath("//*[contains(@id,'PCFDetails:13:pcfValTxt')]")).sendKeys(pCF14);
    		driver.findElement(By.xpath("//*[contains(@id,'PCFDetails:14:pcfValTxt')]")).sendKeys(pCF15);
    		driver.findElement(By.xpath("//*[contains(@id,'PCFDetails:15:pcfValTxt')]")).sendKeys(pCF16);
    		driver.findElement(By.xpath("//*[contains(@id,'PCFDetails:16:pcfValTxt')]")).sendKeys(pCF17);
    		driver.findElement(By.xpath("//*[contains(@id,'PCFDetails:17:pcfValTxt')]")).sendKeys(pCF18);
    		
    		File PCF = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    		FileUtils.copyFile(PCF, new File("./target/screenshots/DIMs/PoundsPerCubicFoot_EachClass.jpeg"));
		}

		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("Comments",file))).isEnabled());
		Reporter.log("Freeform Text/Comment section is present");
		driver.findElement(By.xpath(driverScript.objRepository("Comments",file))).sendKeys(comments);
        
		driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file))).click();
		Thread.sleep(30000);

		try{
			driver.findElement(By.xpath(driverScript.objRepository("Warning",file))).isDisplayed();
			driver.findElement(By.xpath(driverScript.objRepository("WarningConfirm",file))).click();
			Thread.sleep(5000);
			Actions b = new Actions(driver);
			b.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
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
		Thread.sleep(10000);
		
	}


	/**setVariable(XSSFSheet Sheet,XSSFRow r) -----
	 * 	This method gets the test data from test data sheet.
	 * @param Sheet
	 * @param r
	 */

	@SuppressWarnings("static-access")
	private void setVariable(XSSFSheet Sheet,XSSFRow r)
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
		agreementNumber=driverScript.getVariable("AgreementNumber",Sheet,r);
		proposalNumber=driverScript.getVariable("ProposalNumber",Sheet,r);
		proposalType=driverScript.getVariable("ProposalType",Sheet,r);
		geographyView=driverScript.getVariable("GeographyView",Sheet,r);
		copyService=driverScript.getVariable("Copy Service",Sheet,r);
		rateManually=driverScript.getVariable("RateManually",Sheet,r);
		alternationFlag=driverScript.getVariable("AlternationFlag",Sheet,r);
		ratingClass=driverScript.getVariable("RatingClass",Sheet,r);
		dIMFactor=driverScript.getVariable("DIMFactor",Sheet,r);
		minimumPCF =driverScript.getVariable("MinimumPCF",Sheet,r);
		requestName =driverScript.getVariable("RequestName",Sheet,r);
		
		pCF1=DriverScript.getVariable("PCF1",Sheet,r);
    	pCF2=DriverScript.getVariable("PCF2",Sheet,r);
    	pCF3=DriverScript.getVariable("PCF3",Sheet,r);
    	pCF4=DriverScript.getVariable("PCF4",Sheet,r);
    	pCF5=DriverScript.getVariable("PCF5",Sheet,r);
    	pCF6=DriverScript.getVariable("PCF6",Sheet,r);
    	pCF7=DriverScript.getVariable("PCF7",Sheet,r);
    	pCF8=DriverScript.getVariable("PCF8",Sheet,r);
    	pCF9=DriverScript.getVariable("PCF9",Sheet,r);
    	pCF10=DriverScript.getVariable("PCF10",Sheet,r);
    	pCF11=DriverScript.getVariable("PCF11",Sheet,r);
    	pCF12=DriverScript.getVariable("PCF12",Sheet,r);
    	pCF13=DriverScript.getVariable("PCF13",Sheet,r);
    	pCF14=DriverScript.getVariable("PCF14",Sheet,r);
    	pCF15=DriverScript.getVariable("PCF15",Sheet,r);
    	pCF16=DriverScript.getVariable("PCF16",Sheet,r);
    	pCF17=DriverScript.getVariable("PCF17",Sheet,r);
    	pCF18=DriverScript.getVariable("PCF18",Sheet,r);
		comments=driverScript.getVariable("Comments",Sheet,r);


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


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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
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

public class SurchargesHUWgtWaiversGui {

	DriverScript driverScript= new DriverScript();
	WebDriver driver ;
	String file;
	String accountEntryTransitioned,selectService,password,searchBy,agreementNumber,accountCENI,proposalNbr,pricingDiscountType,pricingSummary,startDate,endDate,minimumWeight,maxWeight,stateMatrix,shipmentCondition,currencyType,subjGRI,plusminus1,dir1,type1,name1,state1,county,country1,plus_minus2,dir2,type2,name2,state2,county2,country2,servicedayslow,servicedayshigh,exceptionClassAll,addLines,nmfcType,excptclassmaxwgt,type,classNMFCLR,classNMFCHR,exceptionClass,rateManually,huType,totalWaiveallHUWgt,averageHUWeight,maxNoOfHU, maxTotalOfHUWgt, maxPerHUWeight, excessWgtClass,excessWgtFlag,rateManuallyFlag,comments,accountType,region,country,serviceGroup,serviceTab,accountNumber,id,requestName;
	String [] date;
	XSSFWorkbook workbook;
	XSSFSheet worksheet;
	XSSFRow row;
	

	static final Logger LOGGER = Logger.getLogger(SurchargesHUWgtWaiversGui.class);

	public SurchargesHUWgtWaiversGui(WebDriver driver)
	{
		this.driver=driver;
	}

	/**surchargesHUwgtWaiversTest()----- This method runs the test cases for 
	 * surcharges HUweight Waivers type of discounts.
	 * @throws Exception
	 */
	public void surchargesHUwgtWaiversTest() throws Exception
	{
		try{
			file=DriverScript.surchargesHUWgtWaivers;
		FileInputStream FileInputStream = new FileInputStream(DriverScript.workSheetPath);
		 workbook = new XSSFWorkbook(FileInputStream);

		 worksheet = workbook.getSheet("HU_wgt_Waivers");

		for (int k=5;k<=worksheet.getLastRowNum();k++)
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

		huWeightWaivers();
		
		logOut();

		}
		}catch(Exception e)
		{
			LOGGER.error("Proposal was not created successfully"+ e.getMessage());
		}

	}

	/** huWeightWaivers()-----This method navigates to the surcharges huWeightWaivers
	 *  discount page and fills the required fields.
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void huWeightWaivers() throws InterruptedException, IOException{
		Actions a = new Actions(driver);
        a.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SurchargeTab",file)))).build().perform();
        a.doubleClick(driver.findElement(By.xpath("//td//tr//div//em[text()='Surcharges']"))).build().perform();
        driver.findElement(By.xpath("//td//tr//div//em[text()='Surcharges']")).click();
        driver.findElement(By.xpath("//em[text()='Surcharges']")).click(); 
        Thread.sleep(20000);
        
        driver.findElement(By.xpath("(//span[text()='Intra US Prty TP (Intra)'])[1]/../..//a[text()='Details']")).click();
		 Thread.sleep(5000);

        
/*Test case name: [1]M_PRC_FXFR_PricingMaintenance_FreightSurchargeDetailPage_HUWeightWaivers
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_HUWeightWaivers
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_HUWeightWaivers_HUType
 */
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("HUType",file))).isEnabled());
		Reporter.log("Handling Unit Type is enabled");
		Select Type_HU= new Select(driver.findElement(By.xpath(driverScript.objRepository("HUType",file))));  // HU type
		Type_HU.getAllSelectedOptions();
		Type_HU.selectByValue(huType);  //HU_Type
		File huType = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(huType, new File("./target/screenshots/HU Weight Waivers/HU Type.jpeg"));
		
/*
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_HUWeightWaivers_TotalWaiveAllHUWeight
 */
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("TotalWaiverHUWgt",file))).isEnabled());
		Reporter.log("Totally Waive all Handling Units is enabled");
		Select All_HU_Wgt= new Select(driver.findElement(By.xpath(driverScript.objRepository("TotalWaiverHUWgt",file))));  // total waiver HU wgt
		All_HU_Wgt.getAllSelectedOptions();
		All_HU_Wgt.selectByValue(totalWaiveallHUWgt);  //TotalWaive_all_HU_Wgt
		File TWAHUW = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(TWAHUW, new File("./target/screenshots/HU Weight Waivers/TotalWaiveAllHUWeight.jpeg"));
		Thread.sleep(20000);
		
/*
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_HUWeightWaivers_AvgHUWeight
 */
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("AvgHUWeight",file))).isEnabled());
		Reporter.log("Average Handling Unit Weight is enabled. User can provide input data.");
        driver.findElement(By.xpath(driverScript.objRepository("AvgHUWeight",file))).sendKeys(averageHUWeight);
        File AvgHUWght = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(AvgHUWght, new File("./target/screenshots/HU Weight Waivers/AvgHUWeight.jpeg"));
        
/*
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_HUWeightWaivers_Max_NumberOfHUWeight
 */
        Assert.assertTrue(driver.findElement(By.xpath("//label[text()='Maximum # of H/U']")).isDisplayed());
        if(totalWaiveallHUWgt.contentEquals("NO"))
        	driver.findElement(By.xpath("//input[contains(@id,'maxNoHUWgtHU')]")).sendKeys(maxNoOfHU);
        Reporter.log("Max Number of Handling Units is present");
        File MaxNoHU = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(MaxNoHU, new File("./target/screenshots/HU Weight Waivers/Max_NumberOfHUWeight.jpeg"));
        
        
/*
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_HUWeightWaivers_Max_WeightWaivedPerHU
 */
        Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'maxTotalHUWgtLabelHU')]")).isDisplayed());
        if(totalWaiveallHUWgt.contentEquals("NO"))
        	driver.findElement(By.xpath("//input[contains(@id,'maxTotalHUWgtHU')]")).sendKeys(maxTotalOfHUWgt);
        Reporter.log("Max Weight Waived per Shipment is present");
        File MaxWWperHU = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(MaxWWperHU, new File("./target/screenshots/HU Weight Waivers/Max_WeightWaivedPerHU.jpeg"));
        
        
/*
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_HUWeightWaivers_Max_WeightWaivedPerShipment
 */
        Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'maxPerHUWgtLabelHU')]")).isDisplayed());
        if(totalWaiveallHUWgt.contentEquals("NO"))
        	driver.findElement(By.xpath("//input[contains(@id,'maxPerHUWgtHU')]")).sendKeys(maxPerHUWeight);
        Reporter.log("Max Weight Waived per Handling Unit is present");
        File MaxWWperShipment = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(MaxWWperShipment, new File("./target/screenshots/HU Weight Waivers/Max_WeightWaivedPerShipment.jpeg"));
               
        
/*
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_HUWeightWaivers_ExcessWeightClass
 */
        Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("ExcessWgtClass",file))).isDisplayed());
        Reporter.log("Excess Weight Class is enabled");
        Select ExcessWgtClass= new Select(driver.findElement(By.xpath(driverScript.objRepository("ExcessWgtClass",file))));  // Excess_Wgt_Class
        ExcessWgtClass.getAllSelectedOptions();
        ExcessWgtClass.selectByValue(excessWgtClass);
        File ExcessWC = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(ExcessWC, new File("./target/screenshots/HU Weight Waivers/ExcessWeightClass.jpeg"));
         
/*
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_HUWeightWaivers_RateManualFlag
 */
        WebElement rateManually = driver.findElement(By.xpath(driverScript.objRepository("RateManually",file)));

        if(driver.findElement(By.xpath(driverScript.objRepository("RateManually",file))).isEnabled())
        {
        	if (rateManually.isSelected())
     			rateManually.click();
     		Assert.assertFalse(rateManually.isSelected());
     		
        	if (!rateManually.isSelected())
        		rateManually.click();
     		Assert.assertTrue(rateManually.isSelected());
     		
     		Reporter.log("Rate Manually Flag is present and can be selected/unselected");
        }
        
        File RM = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(RM, new File("./target/screenshots/HU Weight Waivers/RateManualFlag.jpeg"));
        

/*
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_HUWeightWaivers_ExcessWeightFlag
 */
        Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("ExcessWgtFlag",file))).isEnabled());
        Reporter.log("Excess Weight Flag is enabled");
        Select ExcessWgtFlag= new Select(driver.findElement(By.xpath(driverScript.objRepository("ExcessWgtFlag",file))));  // Excess_Wgt_Flag
        ExcessWgtFlag.selectByValue(excessWgtFlag);
        ExcessWgtFlag.getAllSelectedOptions();
        File EWF = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(EWF, new File("./target/screenshots/HU Weight Waivers/ExcessWeightFlag.jpeg"));
        
/*
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_HUWeightWaivers_InfoFreeformText
 */
        if(driver.findElement(By.xpath(driverScript.objRepository("Comments",file))).isEnabled())
        {
        	Reporter.log("Informational Freeform Textbox is enabled. User can Enter the desired text");
        	driver.findElement(By.xpath(driverScript.objRepository("Comments",file))).sendKeys(comments);  // Comments 
        }
        File infoFFT = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(infoFFT, new File("./target/screenshots/HU Weight Waivers/InfoFreeformText.jpeg"));
        
		
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
		Reporter.log("PMGUI Application Ended");
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
		minimumWeight=driverScript.getVariable("Minimum_Weight",Sheet,r);
		maxWeight=driverScript.getVariable("Max_Weight",Sheet,r);
		stateMatrix=driverScript.getVariable("State_Matrix",Sheet,r);
		shipmentCondition=driverScript.getVariable("ShipmentCondition",Sheet,r);
		currencyType=driverScript.getVariable("CurrencyType",Sheet,r);
		subjGRI=driverScript.getVariable("Subj_GRI",Sheet,r);
		plusminus1=driverScript.getVariable("Plus_minus1",Sheet,r);
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
		servicedayslow=driverScript.getVariable("service_days_low",Sheet,r);
		servicedayshigh=driverScript.getVariable("service_days_high",Sheet,r);
		exceptionClassAll=driverScript.getVariable("ExceptionClass_All",Sheet,r);
		excptclassmaxwgt=driverScript.getVariable("Excpt_class_max_wgt",Sheet,r);
		addLines=driverScript.getVariable("addLines",Sheet,r);
		type=driverScript.getVariable("Type",Sheet,r);
		classNMFCLR=driverScript.getVariable("Class_NMFC_LR",Sheet,r);
		classNMFCHR=driverScript.getVariable("Class_NMFC_HR",Sheet,r);
		exceptionClass=driverScript.getVariable("ExceptionClass",Sheet,r);
		rateManually=driverScript.getVariable("RateManually",Sheet,r);
		huType=driverScript.getVariable("HU_Type",Sheet,r);
		totalWaiveallHUWgt=driverScript.getVariable("TotalWaive_all_HU_Wgt",Sheet,r);
		averageHUWeight=driverScript.getVariable("Average_HU_Weight",Sheet,r);
		maxNoOfHU =driverScript.getVariable("MaxNoOfHU",Sheet,r);
		maxTotalOfHUWgt =driverScript.getVariable("MaxTotalOfHUWgt",Sheet,r);
		maxPerHUWeight =driverScript.getVariable("MaxPerHUWeight",Sheet,r);
		excessWgtClass=driverScript.getVariable("Excess_Wgt_Class",Sheet,r);
		excessWgtFlag=driverScript.getVariable("Excess_Wgt_Flag",Sheet,r);
		rateManuallyFlag=driverScript.getVariable("RateManuallyFlag",Sheet,r);
		comments=driverScript.getVariable("Comments",Sheet,r);
		requestName=driverScript.getVariable("RequestName",Sheet,r);
		agreementNumber = driverScript.getVariable("AgreementNumber",Sheet,r);
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

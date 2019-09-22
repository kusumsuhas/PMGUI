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
import com.fedex.pmgui.volumediscounts.VDWgtBrkRates;

import org.testng.Assert;

public class WeightBreakRatesGui {

	DriverScript driverScript= new DriverScript();
	WebDriver driver;
	String file;
	String id, password, accountType, accountNumber, region, country, serviceTab, serviceGroup, selectService,copyService;
	String startDate,endDate,minimumWeight,maximumWeight,stateMatrix,shipmentCond,currencyType,weightUnitType,premiumSvcApply,surchargeRules,arbitraryCharges,subjecttoGRI,authority,number,item,itemSuffix,adjustment_Percentage,adjustmentPoints,increase_Decrease,from_to1,dir1,type1,name1,state1,county1,country1,from_to2,dir2,type2,name2,state2,county2,country2,serviceDaysLow,serviceDaysHigh,exceptionClass_All,exceptionClassMaxWt,addLines,nmfcType, classNMFCLR,classNMFCHR,exceptionClass1,type,NMFCLowerRange,NMFCHigherRange,exceptionClass,manuallyRate,alternate,maximumCharge,rates,shipmentWghtBrkMin, shipmentWghtBrkMax,rateperCWT,flatCharge,rateperCubicFoot,aMC,comments,requestName,agreementNumber;
	String [] date;
	XSSFWorkbook workbook;
	XSSFSheet worksheet;
	XSSFRow row;
	static final Logger LOGGER = Logger.getLogger(VDWgtBrkRates.class);


	public WeightBreakRatesGui(WebDriver driver)
	{
		this.driver=driver;
	}


	/**vdwgtbrkRatesTest()----- This method runs the test cases for 
	 * volume discount weight break Rates type of discounts.
	 * @throws Exception
	 */
	public void vdwgtbrkRatesTest() throws Exception
	{
		try{
			file=DriverScript.vdWgtBrkRates;
			FileInputStream FileInputStream = new FileInputStream(DriverScript.workSheetPath);
			workbook = new XSSFWorkbook(FileInputStream);

			worksheet = workbook.getSheet("VD_wgtbrk_Rates");

			for (int k=18;k<=worksheet.getLastRowNum();k++)
			{
				row = worksheet.getRow(k);
				setVariable( worksheet,row);
                Reporter.log("PMGUI Application started");
                
				LoginPage login = new LoginPage(driver);
				login.login(id, password);

				SelectAccountEntry AE = new SelectAccountEntry(driver);
				AE.gotoAccountEntry();

				AccountEntry AccountNo = new AccountEntry(driver);
				AccountNo.cENIorEAN(accountType, accountNumber);

				ServiceSelection SS = new ServiceSelection(driver);
				SS.regionCountryOpcoSelection(region, country, serviceTab, serviceGroup);

				weightBrkRatesTest();
				
				logOut();
				
				Reporter.log("PMGUI Application Ended");
			}
		}catch(Exception e)
		{
			LOGGER.error("Proposal was not created successfully"+ e.getMessage());
		}
		
	}

	/** weightBrkRates()-----
	 * This method navigates to the volume discount weight break Rates
	 * discount page and fills the required fields. 
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	
	
	
	public void weightBrkRatesTest() throws InterruptedException, IOException{

		driver.findElement(By.xpath(driverScript.objRepository("VDTab",file))).click();
		Thread.sleep(10000);		
		
// GUI test case starts here
		
	
			
			driver.findElement(By.xpath("(//span[text()='Weight Break Rates'])[1]/../..//a[text()='Details']")).click();
			Thread.sleep(5000);
			
//[1]M_PRC_FXFR_PricingMaintenance_WeightBreakRates_FlatChargeFormat
			driver.findElement(By.xpath(driverScript.objRepository("FlatCharge",file))).clear();
			driver.findElement(By.xpath(driverScript.objRepository("RatePerCWT",file))).clear();
			driver.findElement(By.xpath(driverScript.objRepository("RatePerCubicFoot",file))).clear();
			
			driver.findElement(By.xpath(driverScript.objRepository("FlatCharge",file))).sendKeys("12345.67");
			
			Actions a = new Actions(driver);
			a.doubleClick(driver.findElement(By.xpath("//*[contains(@id,'save2')]"))).build().perform();
			Thread.sleep(10000);
			
			driver.findElement(By.xpath("(//span[text()='Do you want to overlay pricing?'])/../../..//input[@value='Yes']")).click();
			driver.findElement(By.xpath("//em[text()='Wgt Brk Rates']")).click();
			Thread.sleep(8000);
			
			try{
				driver.findElement(By.xpath("(//label[text()='Do you want to Save this tab data ?'])/..//input[@value='No']")).click();
				Thread.sleep(8000);
				}catch(Exception e){
					LOGGER.info("No pop-up window appeared");
				}
			
			//Assert.assertEquals(driver.findElement(By.xpath("(//*[text()[contains(.,'Record has been updated successfully')]])[2]")).getText(),"Record has been updated successfully");	
			 //Assert.assertTrue(driver.findElement(By.xpath("(//*[text()[contains(.,'Record has been updated successfully')]])[2]")).isDisplayed());
			Reporter.log("The system accepts a numeric entry in the Flat Charge field in the following format: XXXXX.XX");
			
			driver.findElement(By.xpath(driverScript.objRepository("FlatCharge",file))).clear();
			driver.findElement(By.xpath(driverScript.objRepository("RatePerCWT",file))).clear();
			driver.findElement(By.xpath(driverScript.objRepository("FlatCharge",file))).sendKeys("12345");
			a.doubleClick(driver.findElement(By.xpath("//*[contains(@id,'save2')]"))).build().perform();
			Thread.sleep(8000);
			
			try{
				driver.findElement(By.xpath(driverScript.objRepository("Overlay",file))).click();
			}catch(Exception e){	
				LOGGER.error("Overlay button doesn't exist"+ e.getMessage());
			}
			
			driver.findElement(By.xpath("//em[text()='Wgt Brk Rates']")).click();
			Thread.sleep(8000);
			
			try{
				driver.findElement(By.xpath("(//label[text()='Do you want to Save this tab data ?'])/..//input[@value='No']")).click();
				Thread.sleep(8000);
				}catch(Exception e){
					LOGGER.info("No pop-up window appeared");
				}
			Reporter.log("The system allows the numeric  entry of a Flat Charge for each line item within the Rate frame.");
			//Assert.assertEquals(driver.findElement(By.xpath("//div//ul//li[Text()='	Record has been updated successfully ']")).getText(),"Record has been updated successfully");	
			Assert.assertTrue(driver.findElement(By.xpath("(//*[text()[contains(.,'Record has been updated successfully')]])[2]")).isDisplayed());
			driver.findElement(By.xpath(" (//em[text()='Wgt Brk Rates'])")).click();
			Thread.sleep(1000);
			
			try{
			driver.findElement(By.xpath("(//label[text()='Do you want to Save this tab data ?'])/..//input[@value='No']")).click();
			Thread.sleep(2000);
			}catch(Exception e){
				LOGGER.info("No pop-up window appeared");
			}
			
			File FlatCharge = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(FlatCharge, new File("./target/screenshots/VD Weight Break rates/FlatChargeFormat.jpeg"));
			
//[2]M_PRC_FXFR_PricingMaintenance_WeightBreakRates_InformationalFreeformText
			driver.findElement(By.xpath(driverScript.objRepository("Comments",file))).clear();
			driver.findElement(By.xpath(driverScript.objRepository("Comments",file))).sendKeys("OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK ");
			
			a.doubleClick(driver.findElement(By.xpath("//*[contains(@id,'save2')]"))).build().perform();
			Thread.sleep(10000);
			
			if(driver.findElement(By.xpath("(//*[text()[contains(.,'Invalid format entered - Comments, valid format is 1000 characters')]])[2]")).isDisplayed())
			{
				driver.findElement(By.xpath(driverScript.objRepository("Comments",file))).clear();
				driver.findElement(By.xpath(driverScript.objRepository("Comments",file))).sendKeys(comments);
			}
			File InformationalFreeformText = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(InformationalFreeformText, new File("./target/screenshots/VD Weight Break rates/InformationalFreeformText.jpeg"));
			
			Reporter.log("The system does not allow the entry of free form text within the Comments field over 1000");
			a.doubleClick(driver.findElement(By.xpath("//*[contains(@id,'save2')]"))).build().perform();
			Thread.sleep(10000);
			
			try{
				driver.findElement(By.xpath(driverScript.objRepository("Overlay",file))).click();
				Thread.sleep(10000);
			}catch(Exception e){	
				LOGGER.info("Overlay button doesn't exist"+ e.getMessage());
			}
			
//[3]M_PRC_FXFR_PricingMaintenance_WeightBreakRates_Maximum_Charge
			driver.findElement(By.xpath(driverScript.objRepository("MaxCharge",file))).clear();
			a.doubleClick(driver.findElement(By.xpath("//*[contains(@id,'save2')]"))).build().perform();
			Thread.sleep(10000);
			
			try{
				driver.findElement(By.xpath(driverScript.objRepository("Overlay",file))).click();
				Thread.sleep(10000);
			}catch(Exception e){	
				LOGGER.info("Overlay button doesn't exist"+ e.getMessage());
			}
			

			if(driver.findElement(By.xpath("(//*[text()[contains(.,'Record has been updated successfully')]])[2]")).isDisplayed())
			{
				driver.findElement(By.xpath(driverScript.objRepository("MaxCharge",file))).clear();
				driver.findElement(By.xpath(driverScript.objRepository("MaxCharge",file))).sendKeys("12345.67");
			}
			Reporter.log("The system allows the entry of numeric data within the Maximum Charge field in the following format:  XXXXX.XX.");
			
			a.doubleClick(driver.findElement(By.xpath("//*[contains(@id,'save2')]"))).build().perform();
			Thread.sleep(10000);
			
			File Maximum_Charge = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(Maximum_Charge, new File("./target/screenshots/VD Weight Break rates/MaximumCharge.jpeg"));
			
			
			try{
				driver.findElement(By.xpath(driverScript.objRepository("Overlay",file))).click();
				Thread.sleep(10000);
			}catch(Exception e){	
				LOGGER.info("Overlay button doesn't exist"+ e.getMessage());
			}
			
//[4]M_PRC_FXFR_PricingMaintenance_WeightBreakRates_RateManually_FlagValidation
			
				WebElement ratemanualFlag = driver.findElement(By.xpath(driverScript.objRepository("ManualRate",file)));
				
				if (ratemanualFlag.isSelected())
					ratemanualFlag.click();
				Assert.assertFalse(ratemanualFlag.isSelected());

				if (!ratemanualFlag.isSelected())
					ratemanualFlag.click();
				Assert.assertTrue(ratemanualFlag.isSelected());
				Reporter.log("Manually Flag and it allows the user to select or de-select the checkbox");
				Thread.sleep(3000);
				
				File RatemanualFlag = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(RatemanualFlag, new File("./target/screenshots/VD Weight Break rates/ManualFlag.jpeg"));
			
//[5]M_PRC_FXFR_PricingMaintenance_WeightBreakRates_RateManually_RequiredFields
				
				if (!ratemanualFlag.isSelected())
					ratemanualFlag.click();
				Assert.assertTrue(ratemanualFlag.isSelected());
			Select DT = new Select(driver.findElement(By.xpath(driverScript.objRepository("Alternate",file))));
			DT.selectByIndex(0);
			
			Actions a1 = new Actions(driver);
			a1.doubleClick(driver.findElement(By.xpath("//*[contains(@id,'save2')]"))).build().perform();
			Thread.sleep(10000);
			Reporter.log("System does not allow user to proceed without alternation flag");

			if(driver.findElement(By.xpath("//*[text()[contains(.,'Alternate:  Required field was not entered ')]]")).isDisplayed())
			{
				Select DT1 = new Select(driver.findElement(By.xpath(driverScript.objRepository("Alternate",file))));
				DT1.selectByIndex(1);
			}
			a.doubleClick(driver.findElement(By.xpath("//*[contains(@id,'save2')]"))).build().perform();
			Thread.sleep(10000);
			
			try{
				driver.findElement(By.xpath(driverScript.objRepository("Overlay",file))).click();
				Thread.sleep(10000);
			}catch(Exception e){	
				LOGGER.info("Overlay button doesn't exist"+ e.getMessage());
			}
			Assert.assertTrue(driver.findElement(By.xpath("(//*[text()[contains(.,'Record has been updated successfully')]])[2]")).isDisplayed());
			
			File RateManually_RequiredFields = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(RateManually_RequiredFields, new File("./target/screenshots/VD Weight Break rates/RateManually_RequiredFields.jpeg"));

			driver.findElement(By.xpath("//em[text()='Wgt Brk Rates']")).click();
			Thread.sleep(8000);
			
			try{
				driver.findElement(By.xpath("(//label[text()='Do you want to Save this tab data ?'])/..//input[@value='No']")).click();
				Thread.sleep(2000);
				}catch(Exception e){
					LOGGER.info("No pop-up window appeared");
				}
//[6]M_PRC_FXFR_PricingMaintenance_WeightBreakRates_WeightBreak_RangeRequired
				driver.findElement(By.xpath(driverScript.objRepository("FlatCharge",file))).clear();
				driver.findElement(By.xpath(driverScript.objRepository("RatePerCubicFoot",file))).clear();
				driver.findElement(By.xpath(driverScript.objRepository("RatePerCWT",file))).clear();
				
				driver.findElement(By.xpath("(//input[@value='Save'])[2]")).click();
				Assert.assertTrue(driver.findElement(By.xpath("(//*[text()[contains(.,'Rate per CWT, Flat Charge, or Rate per Cubic Foot must be entered for a Weight Break Range')]])[2]")).isDisplayed());				
				Reporter.log("Error Message: Rate per CWT, Flat Charge, or Rate per Cubic Foot must be entered for a Weight Break Range");
				
				driver.findElement(By.xpath(driverScript.objRepository("RatePerCWT",file))).sendKeys("20");
				a.doubleClick(driver.findElement(By.xpath("//*[contains(@id,'save2')]"))).build().perform();
				Thread.sleep(10000);
				
				try{
					driver.findElement(By.xpath(driverScript.objRepository("Overlay",file))).click();
					Thread.sleep(10000);
				}catch(Exception e){	
					LOGGER.info("Overlay button doesn't exist"+ e.getMessage());
				}
				Assert.assertTrue(driver.findElement(By.xpath("(//*[text()[contains(.,'Record has been updated successfully')]])[2]")).isDisplayed());
				

				driver.findElement(By.xpath("//em[text()='Wgt Brk Rates']")).click();
				Thread.sleep(8000);
				
				try{
					driver.findElement(By.xpath("(//label[text()='Do you want to Save this tab data ?'])/..//input[@value='No']")).click();
					Thread.sleep(2000);
					}catch(Exception e){
						LOGGER.info("No pop-up window appeared");
					}
//[7]M_PRC_FXFR_PricingMaintenance_WeightBreakRates_Rate_PerCWT-PerCubicFootFormat
				driver.findElement(By.xpath(driverScript.objRepository("RatePerCWT",file))).clear();
				driver.findElement(By.xpath(driverScript.objRepository("RatePerCWT",file))).sendKeys("1234.67");
				driver.findElement(By.xpath("(//input[@value='Save'])[2]")).click();
				Reporter.log("System allows XXXX.XX numeric value");
				try{
					driver.findElement(By.xpath(driverScript.objRepository("Overlay",file))).click();
					Thread.sleep(10000);
				}catch(Exception e){	
					LOGGER.info("Overlay button doesn't exist"+ e.getMessage());
				}
				
				driver.findElement(By.xpath("//em[text()='Wgt Brk Rates']")).click();
				Thread.sleep(2000);
				
				try{
					driver.findElement(By.xpath("(//label[text()='Do you want to Save this tab data ?'])/..//input[@value='No']")).click();
					Thread.sleep(8000);
					}catch(Exception e){
						LOGGER.info("No pop-up window appeared");
					}
				
				driver.findElement(By.xpath(driverScript.objRepository("RatePerCWT",file))).clear();
				
				driver.findElement(By.xpath(driverScript.objRepository("RatePerCubicFoot",file))).clear();
				driver.findElement(By.xpath(driverScript.objRepository("RatePerCubicFoot",file))).sendKeys("12.67");				
				Reporter.log("The system accepts a numeric entry in the Rate per Cubic Foot field in the following format: XX.XX");
				
				driver.findElement(By.xpath("(//input[@value='Save'])[2]")).click();
				try{
					driver.findElement(By.xpath(driverScript.objRepository("Overlay",file))).click();
					Thread.sleep(10000);
				}catch(Exception e){	
					LOGGER.info("Overlay button doesn't exist"+ e.getMessage());
				}
				
				driver.findElement(By.xpath("//em[text()='Wgt Brk Rates']")).click();
				Thread.sleep(8000);
				
				try{
					driver.findElement(By.xpath("(//label[text()='Do you want to Save this tab data ?'])/..//input[@value='No']")).click();
					Thread.sleep(8000);
					}catch(Exception e){
						LOGGER.info("No pop-up window appeared");
					}
				
//[8]M_PRC_FXFR_PricingMaintenance_WeightBreakRates_RateRequired
				driver.findElement(By.xpath("//*[contains(@id,'wtBrkRatesdownSec')]//div[7]//input")).click();
				Thread.sleep(8000);
				driver.findElement(By.xpath("//*[contains(@id,'addWeightCountTextHeaderAddRowsManageWtBrkPopupFXGSP4_')]")).sendKeys("2");
				Thread.sleep(2000);
				driver.findElement(By.xpath("(//input[@value='Add' and @type='submit'])[2]")).click();//add
				
				driver.findElement(By.xpath("(//*[contains(@id,'addWeightCountTextLowerManageWtBrkPopupFXGSP4_')])[1]")).clear();
				driver.findElement(By.xpath("(//*[contains(@id,'addWeightCountTextLowerManageWtBrkPopupFXGSP4_')])[2]")).clear();
				
				driver.findElement(By.xpath("(//*[contains(@id,'addWeightCountTextLowerManageWtBrkPopupFXGSP4_')])[1]")).sendKeys("10");
				driver.findElement(By.xpath("(//*[contains(@id,'addWeightCountTextLowerManageWtBrkPopupFXGSP4_')])[2]")).sendKeys("20");
				Thread.sleep(2000);
				driver.findElement(By.xpath("(//input[@value='Refresh' and @type='submit'])[3]")).click();//refresh click
				//driver.findElement(By.xpath("//*[contains(@id,'bottomSaveAndClickButtonManageWtBrkPopupFXGSP4')]")).click();
				driver.findElement(By.xpath("(//input[@value='Save and Close' and @type='submit'])[4]")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("(//input[@value='Save and Close' and @type='submit'])[4]")).click();

				Thread.sleep(5000);
				
				//save and close
				Thread.sleep(5000);
				//Rate per CWT
				driver.findElement(By.xpath("//*[contains(@id,'RateDetails_dataTable:0:wtBrkRatesSecRates2Grp')]//input")).sendKeys("2");
				driver.findElement(By.xpath("//*[contains(@id,'RateDetails_dataTable:1:wtBrkRatesSecRates2Grp')]//input")).sendKeys("2");
				driver.findElement(By.xpath("//*[contains(@id,'RateDetails_dataTable:2:wtBrkRatesSecRates2Grp')]//input")).sendKeys("2");
				//Flat Charge
				driver.findElement(By.xpath("//*[contains(@id,'RateDetails_dataTable:0:wtBrkRatesSecRates3Grp')]//input")).sendKeys("3");
				driver.findElement(By.xpath("//*[contains(@id,'RateDetails_dataTable:1:wtBrkRatesSecRates3Grp')]//input")).sendKeys("3");
				driver.findElement(By.xpath("//*[contains(@id,'RateDetails_dataTable:2:wtBrkRatesSecRates3Grp')]//input")).sendKeys("3");
				//Rate per cubic Foot
				driver.findElement(By.xpath("//*[contains(@id,'RateDetails_dataTable:0:wtBrkRatesSecRates4Grp')]//input")).sendKeys("4");
				driver.findElement(By.xpath("//*[contains(@id,'RateDetails_dataTable:1:wtBrkRatesSecRates4Grp')]//input")).sendKeys("4");
				driver.findElement(By.xpath("//*[contains(@id,'RateDetails_dataTable:2:wtBrkRatesSecRates4Grp')]//input")).sendKeys("4");
				
				
				driver.findElement(By.xpath("(//input[@value='Save'])[2]")).click();
				Thread.sleep(6000);
				//Error check
				Assert.assertTrue(driver.findElement(By.xpath("(//*[text()[contains(.,'May only enter one of the following per Weight Break Range: Rate per CWT')]])[2]")).isDisplayed());
				Reporter.log("Error Msg:May only enter one of the following per Weight Break Range: Rate per CWT, Flat Charge, or Rate per Cubic Foot");
				
				//Rate per CWT
				driver.findElement(By.xpath("//*[contains(@id,'RateDetails_dataTable:0:wtBrkRatesSecRates2Grp')]//input")).clear();
				driver.findElement(By.xpath("//*[contains(@id,'RateDetails_dataTable:1:wtBrkRatesSecRates2Grp')]//input")).clear();
				driver.findElement(By.xpath("//*[contains(@id,'RateDetails_dataTable:2:wtBrkRatesSecRates2Grp')]//input")).clear();
				//Flat Charge
				driver.findElement(By.xpath("//*[contains(@id,'RateDetails_dataTable:0:wtBrkRatesSecRates3Grp')]//input")).clear();
				driver.findElement(By.xpath("//*[contains(@id,'RateDetails_dataTable:1:wtBrkRatesSecRates3Grp')]//input")).clear();
				driver.findElement(By.xpath("//*[contains(@id,'RateDetails_dataTable:2:wtBrkRatesSecRates3Grp')]//input")).clear();
				//Rate per cubic Foot
				driver.findElement(By.xpath("//*[contains(@id,'RateDetails_dataTable:0:wtBrkRatesSecRates4Grp')]//input")).clear();
				driver.findElement(By.xpath("//*[contains(@id,'RateDetails_dataTable:1:wtBrkRatesSecRates4Grp')]//input")).clear();
				driver.findElement(By.xpath("//*[contains(@id,'RateDetails_dataTable:2:wtBrkRatesSecRates4Grp')]//input")).clear();
				
				driver.findElement(By.xpath("(//input[@value='Save'])[2]")).click();
				Thread.sleep(6000);
				//Error check
				Assert.assertTrue(driver.findElement(By.xpath("(//*[text()[contains(.,'Rate per CWT, Flat Charge, or Rate per Cubic Foot')]])[2]")).isDisplayed());
				Reporter.log("Rate per CWT, Flat Charge, or Rate per Cubic Foot must be entered for a Weight Break Range");
				//Flat Charge
				driver.findElement(By.xpath("//*[contains(@id,'RateDetails_dataTable:0:wtBrkRatesSecRates3Grp')]//input")).sendKeys("30");
				driver.findElement(By.xpath("//*[contains(@id,'RateDetails_dataTable:1:wtBrkRatesSecRates3Grp')]//input")).sendKeys("30");
				driver.findElement(By.xpath("//*[contains(@id,'RateDetails_dataTable:2:wtBrkRatesSecRates3Grp')]//input")).sendKeys("30");
				
				a.doubleClick(driver.findElement(By.xpath("//*[contains(@id,'save2')]"))).build().perform();
				Thread.sleep(10000);
				
				try{
					driver.findElement(By.xpath(driverScript.objRepository("Overlay",file))).click();
					Thread.sleep(10000);
				}catch(Exception e){	
					LOGGER.info("Overlay button doesn't exist"+ e.getMessage());
				}
				
				driver.findElement(By.xpath("//em[text()='Wgt Brk Rates']")).click();
				Thread.sleep(8000);
				


		
//[9]M_PRC_FXFR_PricingMaintenance_WeightBreakRates_WeightBreak_RatesEffectiveDates
		
		Assert.assertTrue(driver.findElement(By.xpath("//label[text()='Date Segment']/../../../../../..//label[text()='Start Date']/../..//input")).isDisplayed());
		
		File RatesEffectiveDates = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(RatesEffectiveDates, new File("./target/screenshots/VD Weight Break rates/RatesEffectiveDates.jpeg"));
		Reporter.log("The system displays the Effective Start and Effective End Date for the Weight Break Rates.");
		
		
		
//[10]M_PRC_FXFR_PricingMaintenance_WeightBreakRates
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("ManualRate",file))).isDisplayed());
		
        
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("Alternate",file))).isDisplayed());
		
		
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("MaxCharge",file))).isDisplayed());
		
		Reporter.log("Maximum Charge Field is displayed");
		
		Assert.assertTrue(driver.findElement(By.xpath("(//label[text()='Shipment Weight Break Minimum'])[1]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("(//label[text()='Shipment Weight Break Maximum'])[1]")).isDisplayed());
		Reporter.log("Shipment Weight Break minimum and maximum is visible");
		
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("RatePerCubicFoot",file))).isDisplayed());
		
		Reporter.log("RatePerCubicFoot is visible");
		
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("RatePerCWT",file))).isDisplayed());
		
		Reporter.log("RatePerCWT is visible");
		
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("FlatCharge",file))).isDisplayed());
		Reporter.log("FlatCharge is visible");
		File GUIElements = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(GUIElements, new File("./target/screenshots/VD Weight Break rates/All GUI Elements.jpeg"));
	    Assert.assertTrue(driver.findElement(By.xpath("(//label[text()='Shipment Weight Break Minimum'])[3]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("(//label[text()='Shipment Weight Break Maximum'])[3]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("AMC",file))).isDisplayed());
		Reporter.log("AMC is visible");
		
		// click cancel to go to pricing screen 
		driver.findElement(By.xpath("//*[contains(@id,'cancel2')]")).click();  
		try{
			driver.findElement(By.xpath("(//*[contains(@id,'yes1')])[1]")).click();
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
		Reporter.log("PMGUI Application ended");
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
		copyService = driverScript.getVariable("Copy Service",Sheet,r);
		minimumWeight=driverScript.getVariable("MinimumWeight",Sheet,r);
		maximumWeight=driverScript.getVariable("MaximumWeight",Sheet,r);
		stateMatrix=driverScript.getVariable("StateMatrix",Sheet,r);
		shipmentCond=driverScript.getVariable("ShipmentCond",Sheet,r);
		weightUnitType=driverScript.getVariable("WeightUnitType",Sheet,r);
		currencyType=driverScript.getVariable("CurrencyType",Sheet,r);
		premiumSvcApply=driverScript.getVariable("PremiumSvcApply",Sheet,r);
		surchargeRules =driverScript.getVariable("SurchargeRules",Sheet,r);
		arbitraryCharges=driverScript.getVariable("ApplyArbitraryCharges",Sheet,r);
		subjecttoGRI=driverScript.getVariable("SubjecttoGRI",Sheet,r);
		authority=driverScript.getVariable("Authority",Sheet,r);
		number=driverScript.getVariable("Number",Sheet,r);
		item=driverScript.getVariable("Item",Sheet,r);
		itemSuffix=driverScript.getVariable("ItemSuffix",Sheet,r);
		adjustment_Percentage=driverScript.getVariable("Adjustment_Percentage",Sheet,r);
		adjustmentPoints=driverScript.getVariable("AdjustmentPoints",Sheet,r);
		increase_Decrease=driverScript.getVariable("Increase_Decrease",Sheet,r);
		from_to1=driverScript.getVariable("From_to1",Sheet,r);
		dir1=driverScript.getVariable("Dir1",Sheet,r);
		type1=driverScript.getVariable("Type1",Sheet,r);
		name1=driverScript.getVariable("Name1",Sheet,r);
		state1=driverScript.getVariable("State1",Sheet,r);
		county1=driverScript.getVariable("County1",Sheet,r);
		country1=driverScript.getVariable("Country1",Sheet,r);
		from_to2=driverScript.getVariable("From_to2",Sheet,r);
		dir2=driverScript.getVariable("Dir2",Sheet,r);
		type2=driverScript.getVariable("Type2",Sheet,r);
		name2=driverScript.getVariable("Name2",Sheet,r);
		state2=driverScript.getVariable("State2",Sheet,r);
		county2=driverScript.getVariable("County2",Sheet,r);
		country2=driverScript.getVariable("Country2",Sheet,r);
		serviceDaysLow=driverScript.getVariable("ServiceDaysLow",Sheet,r);
		serviceDaysHigh=driverScript.getVariable("ServiceDaysHigh",Sheet,r);
		exceptionClass_All=driverScript.getVariable("ExceptionClass_All",Sheet,r);
		exceptionClassMaxWt=driverScript.getVariable("ExceptionClassMaxWt",Sheet,r);
		nmfcType=driverScript.getVariable("Type",Sheet,r);
		addLines=driverScript.getVariable("AddLines",Sheet,r);
		classNMFCLR=driverScript.getVariable("Class_NMFC_LR",Sheet,r);
		classNMFCHR=driverScript.getVariable("Class_NMFC_HR",Sheet,r);
		exceptionClass1=driverScript.getVariable("ExceptionClass1",Sheet,r);
		alternate=driverScript.getVariable("Alternate",Sheet,r);
		manuallyRate=driverScript.getVariable("ManuallyRate",Sheet,r);
		maximumCharge=driverScript.getVariable("MaximumCharge",Sheet,r);
		rates=driverScript.getVariable("Rates",Sheet,r);
		shipmentWghtBrkMin=driverScript.getVariable("ShipmentWeightBreakMinimum",Sheet,r);
		shipmentWghtBrkMax=driverScript.getVariable("ShipmentWeightBreakMaximum",Sheet,r);
		rateperCWT=driverScript.getVariable("RateperCWT",Sheet,r);
		flatCharge=driverScript.getVariable("FlatCharge",Sheet,r);
		rateperCubicFoot=driverScript.getVariable("RateperCubicFoot",Sheet,r);
		aMC=driverScript.getVariable("AMC",Sheet,r);
		comments=driverScript.getVariable("Comments",Sheet,r);
		requestName=driverScript.getVariable("RequestName",Sheet,r);
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

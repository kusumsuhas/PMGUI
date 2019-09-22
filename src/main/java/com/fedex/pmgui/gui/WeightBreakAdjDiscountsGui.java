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
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
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


public class WeightBreakAdjDiscountsGui {

	DriverScript driverScript= new DriverScript();
	WebDriver driver ;
	String file;
	String id, password, accountType, accountNumber, region, country, serviceTab, serviceGroup, selectService,copyService;
	String status,accountCENI,mCLineofRatesDiscountType,subjecttoGRI,mCLineofRatesDiscount,agreementNumber,breakMaximum,discountType,discountMCLineofRatesAdjType,mCLineofRatesAdjustment,increaseDecrease1,shipmentWeightBreakMinimum,shipmentWeightBreakMaximum,adjustmentType,increaseDecrease2,zoneAuthority,increaseDecrease3,zoneAuthorityNumber,aMCType,pricingDiscountType,rateScaleType,carrierName,rateScaleNumber,baseRateManualFlag,mCLineofRatesAdjType,pricingSummary,maximumCharge,adjustment,maxLineRateWeight,discount,mileageAuthorityNo,proposalNbr,startDate,endDate,minimumWeight,maximumWeight,stateMatrix,shipmentCond,currencyType,premiumSvcApply,surchargeRules,applyArbitraryCharges,fuelSurcharge,authority,number,item,itemSuffix,adjustmentPercentage,adjustmentPoints, adjShipmentWeightBreakMaximum, increaseDecrease4,from_to1,dir1,type1,name1,state1,county1,country1,from_to2,dir2,type2,name2,state2,county2,country2,serviceDaysLow,serviceDaysHigh,zone, exceptionClassAll,exceptionClassMaxWt,addLines,nmfcType, classNMFCLR,classNMFCHR,exceptionClass1,type,classNMFCRangeLowerRange,classNMFCRangeHigherRange,exceptionClass,hUtype,alternate,adjShipmentWeightBreakMinimum,mileageAuthority,mileageAuthorityhash,mileage,minimumMileage,maximumMileage,hUMinimum,hUMaximum,minWgtperHU,maxWgtPerHU,rateType,rate,aMC,maxChrgShp,rateMnl,comments,requestName;
	String [] date;
	XSSFWorkbook workbook;
	XSSFSheet worksheet;
	XSSFRow row;
	static final Logger LOGGER = Logger.getLogger(VDWgtBrkDiscAdj.class);

	public WeightBreakAdjDiscountsGui(WebDriver driver)
	{
		this.driver=driver;
	}



	public void vdWgtbrkDiscAdjTest() throws Exception
	{
		try{
			file=DriverScript.vdWgtBrkDiscAdj;
			FileInputStream FileInputStream = new FileInputStream(DriverScript.workSheetPath);
			workbook = new XSSFWorkbook(FileInputStream);

			worksheet = workbook.getSheet("VD_Wgt_brk_Disc_Adj");

			for (int k=34;k<=worksheet.getLastRowNum();k++)
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

				weightBrkDiscount();
			
				logOut();
			}
		}catch(Exception e)
		{
			LOGGER.error("Proposal was not created successfully"+ e.getMessage());
		}
	}


	public void weightBrkDiscount()throws InterruptedException, IOException{
		
		
		setVariable(worksheet,row); 
		file=DriverScript.vdWgtBrkDiscAdj;
		
		
		
		driver.findElement(By.xpath(driverScript.objRepository("VDTab",file))).click();
		Thread.sleep(5000);
		 driver.findElement(By.xpath("(//span[text()='Intra US Prty IC (Inter)'])[1]/../..//a[text()='Details']")).click();
		 Thread.sleep(5000);
		
//GUI Validation of Copy Service starts here
		
/*
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_FreightVolume - WeightBreakDiscounts - CopyService - VolumeDiscounts
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_FreightVolume - WeightBreakDiscounts - CopyService - VolumeDiscounts - Edits
 */
		
		if (!copyService.equalsIgnoreCase(""))
		{
			
			driver.findElement(By.linkText("Details")).click();
			Thread.sleep(5000);
			
			driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjcopyService')]")).click();
			Thread.sleep(3000);
			Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'confirmDialogcopyForVD')]//div//div[2]//label[text()='If you Copy this Service with unsaved changes, the unsaved changes will not be copied.  Do you still want to Copy the Service']")).isDisplayed());
			Reporter.log("Message: If you Copy this Service with unsaved changes, the unsaved changes will not be copied.  Do you still want to Copy the Service");
			
			
			driver.findElement(By.xpath("//*[contains(@id,'confirmDialogcopyForVD')]//table//td[2]//input")).click();
			Assert.assertEquals(driver.findElement(By.xpath("//span[text()='Volume Discounts Details']")).getText(),"Volume Discounts Details");
			
			driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjcopyService')]")).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//*[contains(@id,'confirmDialogcopyForVD')]//table//td[1]//input")).click();
			Thread.sleep(10000);

			Assert.assertEquals(driver.findElement(By.xpath("//span[contains(@id,'copyServiceDialog_main')]")).getText(),"copy service");

			//left to right
			WebElement source = driver.findElement(By.xpath("//li[contains(text(),'Intra US Prty TP (Intra)')]")); 
			WebElement destination = driver.findElement(By.xpath("(//div//ul[@class='if-list-body dg-textGroup ui-sortable'])[2]"));
			(new Actions(driver)).dragAndDrop(source, destination).perform();
			Thread.sleep(7000);

			//clicked on save
			driver.findElement(By.xpath("//*[contains(@id,'copyCancel1')]//..//..//td[2]//input")).click();
			WebElement  element = driver.findElement(By.xpath("//*[contains(@id,'copyCancel1')]//..//..//td[2]//input"));
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", element);
			Thread.sleep(5000);
            
			if(driver.findElement(By.xpath("//*[contains(@id,'copyServiceDialog_main')]//form//div[1]//ul//li//span[text()='The following services already exists for all or part of the date range  ']")).isDisplayed())
			{
			//right to left	
			Reporter.log("Error Message: The following services already exists for all or part of the date range");
			WebElement source1 = driver.findElement(By.xpath("//li[contains(text(),'Intra US Prty TP (Intra)')]")); 
			WebElement destination1 = driver.findElement(By.xpath("(//div//ul[@class='if-list-body dg-textGroup ui-sortable'])[1]"));
			(new Actions(driver)).dragAndDrop(source1, destination1).perform();
			Thread.sleep(7000);
			
			//left to right
			WebElement source2 = driver.findElement(By.xpath("//li[contains(text(),'HI - IC')]")); 
			WebElement destination2 = driver.findElement(By.xpath("(//div//ul[@class='if-list-body dg-textGroup ui-sortable'])[2]"));
			(new Actions(driver)).dragAndDrop(source2, destination2).perform();
			Thread.sleep(7000);
			
			driver.findElement(By.xpath("//*[contains(@id,'copyCancel1')]//..//..//td[2]//input")).click();
			Thread.sleep(5000);
			}
			//Assert.assertEquals(driver.findElement(By.xpath("//*[@id='copyServiceDialog_main']//li//span[Text()='Record has been updated successfully']")).getText(),"Record has been updated successfully");
			File UpdateSuccess = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(UpdateSuccess, new File("./target/screenshots/VD WeightBreakAdj/Record has been updated successfully for new BDS.jpeg"));
			
			
			driver.findElement(By.xpath("//*[contains(@id,'copyCancel1')]")).click();
			Thread.sleep(8000);
			
		}
		
			//[1]Test Case Name:M_PRC_FXFR_PricingMaintenance_FreightVolume - WeightBreakAdjustments - WeightBreakAdjustmentTypes
            Select Type = new Select(driver.findElement(By.xpath("//*[contains(@id,'0:wtBrkDisAdjadjsType')]/select"))); 
            Reporter.log("getoptions: "+Type.getAllSelectedOptions()+"");


            
      

//[1]Test Case Name:M_PRC_FXFR_PricingMaintenance_FreightVolume - WeightBreakAdjustments - FreeformText - 1000MaxCharacters

            AssertJUnit.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsinputTextArea')]")).isDisplayed());
            driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsinputTextArea')]")).sendKeys("jhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhgggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggghhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
            driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
            
            File FT = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(FT, new File("./target/screenshots/VD Weight Break Adjustments/FreeformText.jpeg"));
			
            Thread.sleep(8000);
            //Assert.assertEquals(driver.findElement(By.xpath("//*[contains(@id,'errmesgs')]/li")).getText(), "                Invalid format entered - Comments, valid format is 1000 characters ");
            Reporter.log("Comments, valid format is 1000 characters");
            if(driver.findElement(By.xpath("//ul//*[text()[contains(.,'Comments, valid format is 1000 characters')]]")).isDisplayed())
            {
                            driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsinputTextArea')]")).clear(); 
                            driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsinputTextArea')]")).sendKeys("Comments");
                            driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
            }
            

            try{
            	driver.findElement(By.xpath("//*[contains(@id,'continueButtonOverlayFxfVD')]")).click();
			}catch(Exception e){	
				System.out.println("Overlay button doesn't exist"+ e.getMessage());
			}

            AssertJUnit.assertTrue(driver.findElement(By.xpath("(//*[text()[contains(.,'Record has been updated successfully')]])[2]")).isDisplayed());
			

          //[1]Test Case Name:M_PRC_FXFR_PricingMaintenance_FreightVolume - WeightBreakAdjustments - Options


            AssertJUnit.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsMCTypeMenu')]")).isDisplayed());
            Reporter.log("MC Line of Rates Adj Type is displayed");
            AssertJUnit.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsMCVal')]")).isDisplayed());
            Reporter.log("MC Line of Rates Adjustment is displayed");
            AssertJUnit.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsIorDMenu')]")).isDisplayed());
            Reporter.log("Increase/Decrease is displayed");
            Select IncDec = new Select(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsIorDMenu')]")));
            IncDec.selectByIndex(0);
            
//            Assert.assertTrue(!driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjslwrVal')]//span[text()='1.0']")).isEnabled());
//
//            Assert.assertTrue(!driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsUprVal')]")).isEnabled());

            AssertJUnit.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'0:wtBrkDisAdjadjsType')]/select")).isDisplayed());
            Reporter.log("Adjustment Type is displayed");
            AssertJUnit.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkAdjustmentDetails')]/div/table//td[4]//input")).isDisplayed());
            Reporter.log("Adjustment is displayed");
            AssertJUnit.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsIncrDecr')]/select")).isDisplayed());
            Reporter.log("Incr/Decr is dispalyed");
            AssertJUnit.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsinputTextArea')]")).isDisplayed());
            
            File options = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(options, new File("./target/screenshots/VD Weight Break Adjustments/options.jpeg"));
            
           // driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsinputTextArea')]")).click();



//[1]Test Case Name:M_PRC_FXFR_PricingMaintenance_FreightVolume - WeightBreakAdjustments - WeightBreakAdjustmentRequired
//[1]Test Case Name:M_PRC_FXFR_PricingMaintenance_FreightVolume - WeightBreakAdjustments - MCAdjustmentTypes

            Select Adj_type1 = new Select(driver.findElement(By.xpath("//*[contains(@id,':0:wtBrkDisAdjadjsType')]/select")));
            Adj_type1.selectByValue("Adjustment %");
            

            driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkAdjustmentDetails:0:wtBrkDisAdjadjs')]//..//..//td[4]//div//input")).clear(); 
            
            Select IncDec9 = new Select(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsIorDMenu')]")));
            IncDec9.selectByIndex(0);
            Reporter.log("MC Line of Rates Adjustment must be entered if Type is Adjustment % ");
            driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();

            Thread.sleep(5000);

            AssertJUnit.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'errmesgs')]/li")).isDisplayed());

            Thread.sleep(5000);
            
            Select Adj_type2 = new Select(driver.findElement(By.xpath("//*[contains(@id,':0:wtBrkDisAdjadjsType')]/select")));
            Adj_type2.selectByValue("No Adjustment");

            driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkAdjustmentDetails:0:wtBrkDisAdjadjs')]//..//..//td[4]//div//input")).sendKeys("20");

            driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();

            Thread.sleep(5000);
            Reporter.log("Must enter Increase/Decrease for MC Line of Rates Adjustment ");
            AssertJUnit.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'errmesgs')]/li")).isDisplayed());
            File adjustRequird = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(adjustRequird, new File("./target/screenshots/VD Weight Break Adjustments/AdjustmentRequired.jpeg"));

     
//[1]Test Case Name:M_PRC_FXFR_PricingMaintenance_FreightVolume - WeightBreakAdjustments - MCAdjustmentIncreaseDecrease



            Select MCLRAT = new Select(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsMCTypeMenu')]")));
            MCLRAT.selectByValue("Adjustment %");
            
            driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsMCVal')]")).clear();
            driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsMCVal')]")).sendKeys("50");

            Select IncDec3 = new Select(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsIorDMenu')]")));
            IncDec3.selectByIndex(0);
            Thread.sleep(2000);

            driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
           

            Thread.sleep(6000);
            Reporter.log("Error Msg:Must enter Increase/Decrease for MC Line of Rates Adjustment");
            AssertJUnit.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'errmesgs')]/li")).isDisplayed());

            Thread.sleep(6000);
            Select IncDec4 = new Select(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsIorDMenu')]")));
            IncDec4.selectByIndex(1);
            Select IncDec10 = new Select(driver.findElement(By.xpath("//*[contains(@id,':wtBrkDisAdjwgtBrkAdjustmentDetails:0:wtBrkDisAdjadjsIncrDecr')]/select")));
            IncDec10.selectByIndex(0);
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkAdjustmentDetails:0:wtBrkDisAdjadjs')]//..//..//td[4]//div//input")).clear();
            driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();

            Thread.sleep(9000);
         
            try{
            	driver.findElement(By.xpath("//*[contains(@id,'continueButtonOverlayFxfVD')]")).click();
			}catch(Exception e){	
				System.out.println("Overlay button doesn't exist"+ e.getMessage());
			}

            AssertJUnit.assertTrue(driver.findElement(By.xpath("(//*[text()[contains(.,'Record has been updated successfully')]])[2]")).isDisplayed());



            Select MCLRAT1 = new Select(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsMCTypeMenu')]")));
            MCLRAT1.selectByValue("No Adjustment");
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsMCVal')]")).clear();
            Thread.sleep(1000);
            Select IncDec5 = new Select(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsIorDMenu')]")));
            IncDec5.selectByIndex(1);

            driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();

            Thread.sleep(6000);
            Reporter.log("May not enter Increase/Decrease without MC Line of Rates Adjustment");
            AssertJUnit.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'errmesgs')]/li")).isDisplayed());
            Thread.sleep(2000);
            
            Select IncDec6 = new Select(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsIorDMenu')]")));
            IncDec6.selectByIndex(0);
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();

            Thread.sleep(7000);

            try{
            	driver.findElement(By.xpath("//*[contains(@id,'continueButtonOverlayFxfVD')]")).click();
			}catch(Exception e){	
				System.out.println("Overlay button doesn't exist"+ e.getMessage());
			}

            AssertJUnit.assertTrue(driver.findElement(By.xpath("(//*[text()[contains(.,'Record has been updated successfully')]])[2]")).isDisplayed());
//[1]Test Case Name:M_PRC_FXFR_PricingMaintenance_FreightVolume - WeightBreakAdjustments - MCAdjustmentTypeRequired             


            Select MCLRAT2 = new Select(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsMCTypeMenu')]")));
            MCLRAT2.selectByValue("Adjustment %");           
            driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsMCVal')]")).clear();
            Select IncDec7 = new Select(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsIorDMenu')]")));
            IncDec7.selectByIndex(1);
            driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();

            Thread.sleep(7000);

            Reporter.log("Error Msg: MC Line of Rates Adjustment must be entered if Type is Adjustment % ");
            AssertJUnit.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'errmesgs')]/li")).isDisplayed());
            Thread.sleep(4000);
            driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsMCVal')]")).sendKeys("50");
            driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();

            Thread.sleep(7000);

            try{
            	driver.findElement(By.xpath("//*[contains(@id,'continueButtonOverlayFxfVD')]")).click();
			}catch(Exception e){	
				System.out.println("Overlay button doesn't exist"+ e.getMessage());
			}

            //AssertJUnit.assertTrue(driver.findElement(By.xpath("(//*[text()[contains(.,'Record has been updated successfully')]])[2]")).isDisplayed());
            Thread.sleep(7000);

            Select MCLRAT3 = new Select(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsMCTypeMenu')]")));
            MCLRAT3.selectByValue("No Adjustment");         
            driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsMCVal')]")).sendKeys("50");
            Select IncDec8 = new Select(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsIorDMenu')]")));
            IncDec8.selectByIndex(1);
            driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();

            Thread.sleep(7000);
            
           // File MCadjustRequird = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			//FileUtils.copyFile(MCadjustRequird, new File("./target/screenshots/VD Weight Break Adjustments/MCAdjustmentRequired.jpeg"));
           
            Reporter.log("MC Line of Rates not allowed if Type is No Adjustment ");
            AssertJUnit.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'errmesgs')]/li")).isDisplayed());
            Thread.sleep(7000);
            driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsMCVal')]")).clear();
            Select IncDec2 = new Select(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsIorDMenu')]")));
            IncDec2.selectByIndex(0);
            driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
            System.out.println("******************************Validation complete*******************************");
            Thread.sleep(7000);

            try{
            	driver.findElement(By.xpath("//*[contains(@id,'continueButtonOverlayFxfVD')]")).click();
			}catch(Exception e){	
				System.out.println("Overlay button doesn't exist"+ e.getMessage());
			}
            //Assert.assertTrue(driver.findElement(By.xpath("(//*[text()[contains(.,'Record has been updated successfully')]])[2]")).isDisplayed());
            Thread.sleep(7000);
            
            
            
          //[1]Test Case Name:M_PRC_FXFR_PricingMaintenance_FreightVolume - WeightBreakAdjustments - WeightBreakRangeFormat
         driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjadjsmanageWeightBreaks')]")).click();
         Thread.sleep(5000);
         driver.findElement(By.xpath("//*[contains(@id,'addWeightCountTextHeaderAddRowsManageWtBrkPopupFXGSP1_')]")).clear();
         driver.findElement(By.xpath("//*[contains(@id,'addWeightCountTextHeaderAddRowsManageWtBrkPopupFXGSP1_')]")).sendKeys("1");
         driver.findElement(By.xpath("(//input[@value='Add' and @type='submit'])[2]")).click();//add
         Thread.sleep(5000);   
         driver.findElement(By.xpath("//*[contains(@id,'addWeightCountTextLowerManageWtBrkPopupFXGSP1_')]")).clear();
         driver.findElement(By.xpath("//*[contains(@id,'addWeightCountTextLowerManageWtBrkPopupFXGSP1_')]")).sendKeys("123.45");   
         driver.findElement(By.xpath("(//input[@value='Refresh' and @type='submit'])[3]")).click(); 
         Thread.sleep(3000);
         Reporter.log("Invalid format entered - Lower, valid format is 999999");
         
         
         driver.findElement(By.xpath("//*[contains(@id,'addWeightCountTextHeaderAddRowsManageWtBrkPopupFXGSP1_')]")).clear();
         driver.findElement(By.xpath("//*[contains(@id,'addWeightCountTextHeaderAddRowsManageWtBrkPopupFXGSP1_')]")).sendKeys("1");
         driver.findElement(By.xpath("(//input[@value='Add' and @type='submit'])[2]")).click();//add
         Thread.sleep(5000);   
         driver.findElement(By.xpath("//*[contains(@id,'addWeightCountTextLowerManageWtBrkPopupFXGSP1_')]")).clear();
         driver.findElement(By.xpath("//*[contains(@id,'addWeightCountTextLowerManageWtBrkPopupFXGSP1_')]")).sendKeys("123.45");   
         driver.findElement(By.xpath("(//input[@value='Refresh' and @type='submit'])[3]")).click(); 
         Thread.sleep(3000);
         driver.findElement(By.xpath("(//input[@value='Save and Close' and @type='submit'])[4]")).click();
			Thread.sleep(3000);

        try{
        	driver.findElement(By.xpath("(//input[@value='Save and Close' and @type='submit'])[4]")).click();
		}catch(Exception e){	
			System.out.println("save and close done already"+ e.getMessage());
			
			//Freeform Text and Discount Type testing starts here
			/*
			 * Test case name: [1]Comments, valid format is 1000 characters
			 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_FreightVolume - WeightBreakDiscounts - FreeformText - WeightBreakRangeRequired
			 */
						WebDriverWait wait = new WebDriverWait(driver, 30);
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Volume Discounts Details']")));

						driver.findElement(By.xpath(driverScript.objRepository("Comments",file))).clear();
						driver.findElement(By.xpath(driverScript.objRepository("Comments",file))).sendKeys("OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK ");
						
						Select DT = new Select(driver.findElement(By.xpath(driverScript.objRepository("DiscountType",file))));
						DT.selectByIndex(0);
						
						Actions a = new Actions(driver);
						a.doubleClick(driver.findElement(By.xpath("//*[contains(@id,'save2')]"))).build().perform();
									
						WebElement  element1=driver.findElement(By.xpath("//*[contains(@id,'save2')]"));
						JavascriptExecutor executor1 = (JavascriptExecutor)driver;
						executor1.executeScript("arguments[0].click();", element1);
						
						Thread.sleep(5000);
						
						if(driver.findElement(By.xpath("//ul//*[text()[contains(.,'Required field was not entered')]]")).isDisplayed())
						{
							Reporter.log("'Error Message: Discount Type: Required field was not entered");
							Select DT1 = new Select(driver.findElement(By.xpath(driverScript.objRepository("DiscountType",file))));
							DT1.selectByValue(discountType);
						}
						
						Actions b = new Actions(driver);
						b.doubleClick(driver.findElement(By.xpath("//*[contains(@id,'save2')]"))).build().perform();
						Thread.sleep(10000);
						Actions f = new Actions(driver);
						f.doubleClick(driver.findElement(By.xpath("//*[contains(@id,'save2')]"))).build().perform();
						Thread.sleep(10000);
						Actions d  = new Actions(driver);
						d.doubleClick(driver.findElement(By.xpath("//*[contains(@id,'save2')]"))).build().perform();
						Thread.sleep(10000);
						
						if(driver.findElement(By.xpath("//ul//*[text()[contains(.,'Comments, valid format is 1000 characters')]]")).isDisplayed())
						{
							Reporter.log("Error Msg:Comments, valid format is 1000 characters");
							wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(driverScript.objRepository("Comments",file))));
							driver.findElement(By.xpath(driverScript.objRepository("Comments",file))).clear();
							driver.findElement(By.xpath(driverScript.objRepository("Comments",file))).sendKeys(comments);
						}
						
						Actions c = new Actions(driver);
						c.doubleClick(driver.findElement(By.xpath("//*[contains(@id,'save2')]"))).build().perform();
						Thread.sleep(10000);
						
						try{
							driver.findElement(By.xpath(driverScript.objRepository("Overlay",file))).click();
							Thread.sleep(10000);
						}catch(Exception e1){	
							Reporter.log("Overlay button doesn't exist");
						}

			//Freeform Text and Discount Type testing ends here

			//Manage weight break testing starts

			/*
			 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_FreightVolume - WeightBreakDiscounts - Maximum#ofAmounts
			 */
						
						WebElement  element11=driver.findElement(By.xpath(driverScript.objRepository("ManageDisWgtBrk",file)));
						JavascriptExecutor executor11 = (JavascriptExecutor)driver;
						executor11.executeScript("arguments[0].click();", element11);
						Thread.sleep(10000);
						driver.findElement(By.xpath("//input[contains(@id,'addWeightCountTextHeaderAddRowsManageWtBrkPopupFXGSP_')]")).sendKeys("11");
						driver.findElement(By.xpath("//input[contains(@id,'addWeightHeaderButtonManageWtBrkPopupFXGSP_')]")).click();
						
						driver.findElement(By.xpath("//input[contains(@id,'0:addWeightCountTextLowerManageWtBrkPopupFXGSP_')]")).clear();
						driver.findElement(By.xpath("//input[contains(@id,'1:addWeightCountTextLowerManageWtBrkPopupFXGSP_')]")).clear();
						driver.findElement(By.xpath("//input[contains(@id,'2:addWeightCountTextLowerManageWtBrkPopupFXGSP_')]")).clear();
						driver.findElement(By.xpath("//input[contains(@id,'3:addWeightCountTextLowerManageWtBrkPopupFXGSP_')]")).clear();
						driver.findElement(By.xpath("//input[contains(@id,'4:addWeightCountTextLowerManageWtBrkPopupFXGSP_')]")).clear();
						driver.findElement(By.xpath("//input[contains(@id,'5:addWeightCountTextLowerManageWtBrkPopupFXGSP_')]")).clear();
						driver.findElement(By.xpath("//input[contains(@id,'6:addWeightCountTextLowerManageWtBrkPopupFXGSP_')]")).clear();
						driver.findElement(By.xpath("//input[contains(@id,'7:addWeightCountTextLowerManageWtBrkPopupFXGSP_')]")).clear();
						driver.findElement(By.xpath("//input[contains(@id,'8:addWeightCountTextLowerManageWtBrkPopupFXGSP_')]")).clear();
						driver.findElement(By.xpath("//input[contains(@id,'9:addWeightCountTextLowerManageWtBrkPopupFXGSP_')]")).clear();
						driver.findElement(By.xpath("//input[contains(@id,'10:addWeightCountTextLowerManageWtBrkPopupFXGSP_')]")).clear();
						
						driver.findElement(By.xpath("//input[contains(@id,'0:addWeightCountTextLowerManageWtBrkPopupFXGSP_')]")).sendKeys("10");
						driver.findElement(By.xpath("//input[contains(@id,'1:addWeightCountTextLowerManageWtBrkPopupFXGSP_')]")).sendKeys("20");
						driver.findElement(By.xpath("//input[contains(@id,'2:addWeightCountTextLowerManageWtBrkPopupFXGSP_')]")).sendKeys("30");
						driver.findElement(By.xpath("//input[contains(@id,'3:addWeightCountTextLowerManageWtBrkPopupFXGSP_')]")).sendKeys("40");
						driver.findElement(By.xpath("//input[contains(@id,'4:addWeightCountTextLowerManageWtBrkPopupFXGSP_')]")).sendKeys("50");
						driver.findElement(By.xpath("//input[contains(@id,'5:addWeightCountTextLowerManageWtBrkPopupFXGSP_')]")).sendKeys("60");
						driver.findElement(By.xpath("//input[contains(@id,'6:addWeightCountTextLowerManageWtBrkPopupFXGSP_')]")).sendKeys("70");
						driver.findElement(By.xpath("//input[contains(@id,'7:addWeightCountTextLowerManageWtBrkPopupFXGSP_')]")).sendKeys("80");
						driver.findElement(By.xpath("//input[contains(@id,'8:addWeightCountTextLowerManageWtBrkPopupFXGSP_')]")).sendKeys("90");
						driver.findElement(By.xpath("//input[contains(@id,'9:addWeightCountTextLowerManageWtBrkPopupFXGSP_')]")).sendKeys("100");
						driver.findElement(By.xpath("//input[contains(@id,'10:addWeightCountTextLowerManageWtBrkPopupFXGSP_')]")).sendKeys("110");

						driver.findElement(By.xpath(driverScript.objRepository("PopUp",file))).click();
						Thread.sleep(10000);
						driver.findElement(By.xpath(driverScript.objRepository("SaveMngWgtBrk",file))).click();
						Thread.sleep(10000);
						
						Select DT1 = new Select(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkDiscountDetails')]/div/table/tbody//tr[1]//td[3]//select")));
						DT1.selectByValue(discountType);
						Select DT2 = new Select(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkDiscountDetails')]/div/table/tbody//tr[2]//td[3]//select")));
						DT2.selectByValue(discountType);
						Select DT3 = new Select(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkDiscountDetails')]/div/table/tbody//tr[3]//td[3]//select")));
						DT3.selectByValue(discountType);
						Select DT4 = new Select(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkDiscountDetails')]/div/table/tbody//tr[4]//td[3]//select")));
						DT4.selectByValue(discountType);
						Select DT5 = new Select(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkDiscountDetails')]/div/table/tbody//tr[5]//td[3]//select")));
						DT5.selectByValue(discountType);
						Select DT6 = new Select(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkDiscountDetails')]/div/table/tbody//tr[6]//td[3]//select")));
						DT6.selectByValue(discountType);
						Select DT7 = new Select(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkDiscountDetails')]/div/table/tbody//tr[7]//td[3]//select")));
						DT7.selectByValue(discountType);
						Select DT8 = new Select(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkDiscountDetails')]/div/table/tbody//tr[8]//td[3]//select")));
						DT8.selectByValue(discountType);
						Select DT9 = new Select(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkDiscountDetails')]/div/table/tbody//tr[9]//td[3]//select")));
						DT9.selectByValue(discountType);
						Select DT10 = new Select(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkDiscountDetails')]/div/table/tbody//tr[10]//td[3]//select")));
						DT10.selectByValue(discountType);
						Select DT11 = new Select(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkDiscountDetails')]/div/table/tbody//tr[11]//td[3]//select")));
						DT11.selectByValue(discountType);
						Select DT12 = new Select(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkDiscountDetails')]/div/table/tbody//tr[12]//td[3]//select")));
						DT12.selectByValue(discountType);
						
						driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkDiscountDetails')]/div/table/tbody//tr[1]//td[4]//input")).sendKeys(discount);
						driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkDiscountDetails')]/div/table/tbody//tr[2]//td[4]//input")).sendKeys(discount);
						driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkDiscountDetails')]/div/table/tbody//tr[3]//td[4]//input")).sendKeys(discount);
						driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkDiscountDetails')]/div/table/tbody//tr[4]//td[4]//input")).sendKeys(discount);
						driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkDiscountDetails')]/div/table/tbody//tr[5]//td[4]//input")).sendKeys(discount);
						driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkDiscountDetails')]/div/table/tbody//tr[6]//td[4]//input")).sendKeys(discount);
						driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkDiscountDetails')]/div/table/tbody//tr[7]//td[4]//input")).sendKeys(discount);
						driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkDiscountDetails')]/div/table/tbody//tr[8]//td[4]//input")).sendKeys(discount);
						driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkDiscountDetails')]/div/table/tbody//tr[9]//td[4]//input")).sendKeys(discount);
						driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkDiscountDetails')]/div/table/tbody//tr[10]//td[4]//input")).sendKeys(discount);
						driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkDiscountDetails')]/div/table/tbody//tr[11]//td[4]//input")).sendKeys(discount);
						driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjwgtBrkDiscountDetails')]/div/table/tbody//tr[12]//td[4]//input")).sendKeys(discount);
						
						
						a.doubleClick(driver.findElement(By.xpath("//*[contains(@id,'save2')]"))).build().perform();
						Thread.sleep(10000);
						
						if(driver.findElement(By.xpath("//ul//*[text()[contains(.,'Maximum number of amounts for Weight Break Discounts has been exceeded')]]")).isDisplayed())
						{
							Reporter.log("Error Msg:Maximum number of amounts for Weight Break Discounts has been exceeded");
							WebElement  element111=driver.findElement(By.xpath(driverScript.objRepository("ManageDisWgtBrk",file)));
							JavascriptExecutor executor111 = (JavascriptExecutor)driver;
							executor111.executeScript("arguments[0].click();", element111);
							Thread.sleep(10000);
							
							driver.findElement(By.xpath("//*[contains(@id,'existingWtBrkDataTableManageWtBrkPopupFXGSP')]//tbody//tr[2]//td[1]//input")).click();
							driver.findElement(By.xpath("//*[contains(@id,'existingWtBrkDataTableManageWtBrkPopupFXGSP')]//tbody//tr[3]//td[1]//input")).click();
							driver.findElement(By.xpath("//*[contains(@id,'existingWtBrkDataTableManageWtBrkPopupFXGSP')]//tbody//tr[4]//td[1]//input")).click();
							driver.findElement(By.xpath("//*[contains(@id,'existingWtBrkDataTableManageWtBrkPopupFXGSP')]//tbody//tr[5]//td[1]//input")).click();
							driver.findElement(By.xpath("//*[contains(@id,'existingWtBrkDataTableManageWtBrkPopupFXGSP')]//tbody//tr[6]//td[1]//input")).click();
							driver.findElement(By.xpath("//*[contains(@id,'existingWtBrkDataTableManageWtBrkPopupFXGSP')]//tbody//tr[7]//td[1]//input")).click();
							driver.findElement(By.xpath("//*[contains(@id,'existingWtBrkDataTableManageWtBrkPopupFXGSP')]//tbody//tr[8]//td[1]//input")).click();
							driver.findElement(By.xpath("//*[contains(@id,'existingWtBrkDataTableManageWtBrkPopupFXGSP')]//tbody//tr[9]//td[1]//input")).click();
							driver.findElement(By.xpath("//*[contains(@id,'existingWtBrkDataTableManageWtBrkPopupFXGSP')]//tbody//tr[10]//td[1]//input")).click();
							driver.findElement(By.xpath("//*[contains(@id,'existingWtBrkDataTableManageWtBrkPopupFXGSP')]//tbody//tr[11]//td[1]//input")).click();
							driver.findElement(By.xpath("//*[contains(@id,'existingWtBrkDataTableManageWtBrkPopupFXGSP')]//tbody//tr[12]//td[1]//input")).click();
							
							
							driver.findElement(By.xpath(driverScript.objRepository("PopUp",file))).click();
							Thread.sleep(10000);
							driver.findElement(By.xpath(driverScript.objRepository("SaveMngWgtBrk",file))).click();
							Thread.sleep(10000);
						}
						
						Select DT13 = new Select(driver.findElement(By.xpath(driverScript.objRepository("DiscountType",file))));
						DT13.selectByValue(discountType);
						driver.findElement(By.xpath(driverScript.objRepository("Discount",file))).sendKeys(discount);
						
						a.doubleClick(driver.findElement(By.xpath("//*[contains(@id,'save2')]"))).build().perform();
						Thread.sleep(8000);
						
						try{
							driver.findElement(By.xpath(driverScript.objRepository("Overlay",file))).click();
							Thread.sleep(8000);
						}catch(Exception e1){	
							Reporter.log("Overlay button doesn't exist");
						}
			//Manage weight break testing ends
						
			//Validation without entering the Maximum Charge Amount for Transportation starts
			/*
			 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_FreightVolume - WeightBreakDiscounts - MaximumChargeAmount
			 */
						
						driver.findElement(By.xpath(driverScript.objRepository("MaximumCharge",file))).clear();
						
						a.doubleClick(driver.findElement(By.xpath("//*[contains(@id,'save2')]"))).build().perform();
						Thread.sleep(10000);
						
						try{
							driver.findElement(By.xpath(driverScript.objRepository("Overlay",file))).click();
							Thread.sleep(10000);
						}catch(Exception e1){	
							Reporter.log("Overlay button doesn't exist");
						}
						
			//Validation without entering the Maximum Charge Amount for Transportation ends
						
			//Validation Weight Break Discount Range without entering the MC Line Discount Type starts

			/*
			 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_FreightVolume - WeightBreakDiscounts - MCDiscountRequired
			 */
						
						Select MCLRDT = new Select(driver.findElement(By.xpath(driverScript.objRepository("MCLineofRatesDiscountType",file))));
						MCLRDT.selectByIndex(0);
						
						a.doubleClick(driver.findElement(By.xpath("//*[contains(@id,'save2')]"))).build().perform();
						Thread.sleep(10000);
						
						if(driver.findElement(By.xpath("//ul//*[text()[contains(.,'MC Line Disc Type must be entered when Weight Break Range is entered')]]")).isDisplayed())
						{
							Reporter.log("Error Msg:MC Line Disc Type must be entered when Weight Break Range is entered");
							MCLRDT.selectByValue(mCLineofRatesDiscountType);
							a.doubleClick(driver.findElement(By.xpath("//*[contains(@id,'save2')]"))).build().perform();
							Thread.sleep(10000);
							
							try{
								driver.findElement(By.xpath(driverScript.objRepository("Overlay",file))).click();
								Thread.sleep(10000);
							}catch(Exception e1){	
								Reporter.log("Overlay button doesn't exist");
							}
						}

			//Validation Weight Break Discount Range without entering the MC Line Discount Type ends
						
			//Validation Weight Break Discount Range without entering the MC Line Discount Type starts

			/*
			 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_FreightVolume - WeightBreakDiscounts - AMCTypeRequired
			 */
						Select AMC_Type = new Select(driver.findElement(By.xpath(driverScript.objRepository("AMCType",file))));
						AMC_Type.selectByIndex(0);
						
						a.doubleClick(driver.findElement(By.xpath("//*[contains(@id,'save2')]"))).build().perform();
						Thread.sleep(10000);
						
						if(driver.findElement(By.xpath("//ul//*[text()[contains(.,'AMC Type must be entered when Weight Break Range is entered')]]")).isDisplayed())
						{
							Reporter.log("Error Msg:AMC Type must be entered when Weight Break Range is entered");
							AMC_Type.selectByValue(aMCType);
							a.doubleClick(driver.findElement(By.xpath("//*[contains(@id,'save2')]"))).build().perform();
							Thread.sleep(10000);
							
							try{
								driver.findElement(By.xpath(driverScript.objRepository("Overlay",file))).click();
								Thread.sleep(10000);
							}catch(Exception e1){	
								Reporter.log("Overlay button doesn't exist");
							}
						}
			
			//close
			driver.findElement(By.xpath("(//input[@value='Cancel'])[2]")).click();	
			Thread.sleep(3000);
			driver.findElement(By.xpath("(//input[@value='Yes'])[1]")).click();
			Thread.sleep(5000);
		}}
		




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
		rateScaleNumber=driverScript.getVariable("RateScaleNumber",Sheet,r);
		baseRateManualFlag=driverScript.getVariable("BaseRateManualFlag",Sheet,r);
		rateScaleType=driverScript.getVariable("RateScaleType",Sheet,r);
		carrierName=driverScript.getVariable("CarrierName",Sheet,r);
		minimumWeight=driverScript.getVariable("MinimumWeight",Sheet,r);
		maximumWeight=driverScript.getVariable("MaximumWeight",Sheet,r);
		stateMatrix=driverScript.getVariable("StateMatrix",Sheet,r);
		shipmentCond=driverScript.getVariable("ShipmentCond",Sheet,r);
		currencyType =driverScript.getVariable("CurrencyType",Sheet,r);
		premiumSvcApply=driverScript.getVariable("PremiumSvcApply",Sheet,r);
		surchargeRules=driverScript.getVariable("SurchargeRules",Sheet,r);
		applyArbitraryCharges=driverScript.getVariable("ApplyArbitraryCharges",Sheet,r);
		subjecttoGRI=driverScript.getVariable("SubjecttoGRI",Sheet,r);
		authority=driverScript.getVariable("Authority",Sheet,r);
		number=driverScript.getVariable("Number",Sheet,r);
		item=driverScript.getVariable("Item",Sheet,r);
		itemSuffix=driverScript.getVariable("ItemSuffix",Sheet,r);
		adjustmentPercentage=driverScript.getVariable("Adjustment_Percentage",Sheet,r);
		adjustmentPoints=driverScript.getVariable("AdjustmentPoints",Sheet,r);
		increaseDecrease1=driverScript.getVariable("Increase_Decrease1",Sheet,r);
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
		zoneAuthority=driverScript.getVariable("ZoneAuthority",Sheet,r);
		zoneAuthorityNumber=driverScript.getVariable("ZoneAuthorityNumber",Sheet,r);
		zone = driverScript.getVariable("Zone",Sheet,r);
		exceptionClassAll=driverScript.getVariable("ExceptionClass_All",Sheet,r);
		exceptionClassMaxWt=driverScript.getVariable("ExceptionClassMaxWt",Sheet,r);
		nmfcType=driverScript.getVariable("Type",Sheet,r);
		addLines=driverScript.getVariable("AddLines",Sheet,r);
		classNMFCLR=driverScript.getVariable("Class_NMFC_LR",Sheet,r);
		classNMFCHR=driverScript.getVariable("Class_NMFC_HR",Sheet,r);
		exceptionClass1=driverScript.getVariable("ExceptionClass1",Sheet,r);
		aMCType=driverScript.getVariable("AMCType",Sheet,r);
		aMC=driverScript.getVariable("AMC",Sheet,r);
		increaseDecrease2=driverScript.getVariable("Increase_Decrease2",Sheet,r);
		mCLineofRatesDiscountType=driverScript.getVariable("MCLineofRatesDiscountType",Sheet,r);
		mCLineofRatesDiscount=driverScript.getVariable("MCLineofRatesDiscount",Sheet,r);
		maxLineRateWeight=driverScript.getVariable("MaxLineRateWeight",Sheet,r);
		maximumCharge=driverScript.getVariable("MaximumCharge",Sheet,r);
		shipmentWeightBreakMinimum=driverScript.getVariable("ShipmentWeightBreakMinimum",Sheet,r);
		shipmentWeightBreakMaximum=driverScript.getVariable("ShipmentWeightBreakMaximum",Sheet,r);
		discountType=driverScript.getVariable("DiscountType",Sheet,r);
		discount=driverScript.getVariable("Discount",Sheet,r);
		discountMCLineofRatesAdjType=driverScript.getVariable("DiscountMCLineofRatesAdjType",Sheet,r);
		mCLineofRatesAdjType=driverScript.getVariable("MCLineofRatesAdjType",Sheet,r);
		mCLineofRatesAdjustment=driverScript.getVariable("MCLineofRatesAdjustment",Sheet,r);
		increaseDecrease3=driverScript.getVariable("Increase_Decrease3",Sheet,r);
		adjShipmentWeightBreakMinimum=driverScript.getVariable("ShipmentWeightBreakMinimum",Sheet,r);
		adjShipmentWeightBreakMaximum=driverScript.getVariable("ShipmentWeightBreakMaximum",Sheet,r);
		adjustmentType=driverScript.getVariable("AdjustmentType",Sheet,r);
		adjustment=driverScript.getVariable("Adjustment",Sheet,r);
		increaseDecrease4=driverScript.getVariable("Increase_Decrease4",Sheet,r);
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
	
	WebElement getStaleElem(By by, WebDriver driver) {
	    try {
	        return driver.findElement(by);
	    } catch (StaleElementReferenceException | NoSuchElementException e) {
	        System.out.println("Attempting to recover from " + e.getClass().getSimpleName() + "...");
	        return getStaleElem(by, driver);
	    }
	}
	
	WebElement getStaleElemByCss(String css, WebDriver driver) {
	    try {
	        return driver.findElement(By.cssSelector(css));
	    } catch (StaleElementReferenceException e) {
	        System.out.println("Attempting to recover from StaleElementReferenceException ...");
	        return getStaleElemByCss(css, driver);
	    } catch (NoSuchElementException ele) {
	         System.out.println("Attempting to recover from NoSuchElementException ...");
	         return getStaleElemByCss(css, driver);
	    }
	}

}

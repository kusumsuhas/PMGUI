package com.fedex.pmgui.volumediscounts;
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
import org.testng.Reporter;
import org.testng.log4testng.Logger;

import com.fedex.pmgui.common.AccountEntry;
import com.fedex.pmgui.common.AddServices;
import com.fedex.pmgui.common.DatePicker;
import com.fedex.pmgui.common.LoginPage;
import com.fedex.pmgui.common.SelectAccountEntry;
import com.fedex.pmgui.common.ServiceSelection;
import com.fedex.pmgui.driverscript.DriverScript;


public class VDWgtBrkDiscAdj {

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

	public VDWgtBrkDiscAdj(WebDriver driver)
	{
		this.driver=driver;
	}


	/**vdWgtbrkDiscAdjTest()----- This method runs the test cases for 
	 * VD weight break discount and adjustment type of discounts.
	 * @throws Exception
	 */
	public void vdWgtbrkDiscAdjTest() throws Exception
	{
		try{
			file=DriverScript.vdWgtBrkDiscAdj;
			FileInputStream FileInputStream = new FileInputStream(DriverScript.workSheetPath);
			workbook = new XSSFWorkbook(FileInputStream);

			worksheet = workbook.getSheet("VD_Wgt_brk_Disc_Adj");

			for (int k=35;k<=worksheet.getLastRowNum();k++)
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

				weightBrkDiscount(worksheet,row);
				createProposal();
				logOut();
			}
		}catch(Exception e)
		{
			LOGGER.error("Proposal was not created successfully"+ e.getMessage());
		}
	}

	/** weightBrkDiscount(XSSFSheet worksheet,XSSFRow row)-----
	 * This method navigates to the volume discount weight break and adjustment
	 * discount page and fills the required fields. 
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void weightBrkDiscount(XSSFSheet worksheet,XSSFRow row) throws InterruptedException, IOException{
		setVariable(worksheet,row); 
		file=DriverScript.vdWgtBrkDiscAdj;
		driver.findElement(By.xpath(driverScript.objRepository("VDTab",file))).click();
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
			
			
//GUI Validation of Copy Service ends here
			
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
			}catch(Exception e){	
				Reporter.log("Overlay button doesn't exist"+ e.getMessage());
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
			}catch(Exception e){	
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
			}catch(Exception e){	
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
				}catch(Exception e){	
					Reporter.log("Overlay button doesn't exist"+ e.getMessage());
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
				}catch(Exception e){	
					Reporter.log("Overlay button doesn't exist"+ e.getMessage());
				}
			}

//Validation Weight Break Discount Range without entering the MC Line Discount Type ends

			driver.findElement(By.xpath("//*[contains(@id,'cancel2')]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//*[contains(@id,'confirmDialog1_main')]//input[contains(@id,'yes1')]")).click();
			Thread.sleep(5000);
		}

		AddServices AS = new AddServices(driver);
		AS.addService(selectService);


		try {
			DatePicker d = new DatePicker(driver);
			date = d.selectDateVDED();
			DriverScript.setVariable("StartDate",worksheet,row,date[0]);
			DriverScript.setVariable("EndDate",worksheet,row,date[1]);
		} catch (Exception e) {
			LOGGER.error("Didn't capture the startdate and enddate"+ e.getMessage());
		}
		Thread.sleep(5000);

		Select RST = new Select(driver.findElement(By.xpath(driverScript.objRepository("RateScaleType",file))));
		RST.selectByValue(rateScaleType);
		Thread.sleep(10000);
		Select CN = new Select(driver.findElement(By.xpath(driverScript.objRepository("CarrierName",file))));
		CN.selectByValue(carrierName);
		Thread.sleep(10000);
		Select RSN = new Select(driver.findElement(By.xpath(driverScript.objRepository("RateScaleNumber",file))));
		RSN.selectByValue(rateScaleNumber);
		Thread.sleep(10000);
		driver.findElement(By.xpath(driverScript.objRepository("ManualFlag",file))).click();

		try
		{
			String cz=driver.findElement(By.xpath(driverScript.objRepository("ClassZipTab",file))).getText();
			if(!cz.equalsIgnoreCase(""))
			{
				cz=driver.findElement(By.xpath(driverScript.objRepository("ClassZipTab",file))).getText();
				DriverScript.setVariable("Class_Zip_tab",worksheet,row,cz);
			}
			if(driver.findElement(By.xpath(driverScript.objRepository("AuthorityTab",file)))!= null)
			{
				String Auth=driver.findElement(By.xpath(driverScript.objRepository("AuthorityTab",file))).getText();
				DriverScript.setVariable("Authority_tab",worksheet,row,Auth);
			}
			if(driver.findElement(By.xpath(driverScript.objRepository("NumberTab",file)))!= null)
			{
				String numb=driver.findElement(By.xpath(driverScript.objRepository("NumberTab",file))).getText();
				DriverScript.setVariable("Number_tab",worksheet,row,numb);
			}
			if(driver.findElement(By.xpath(driverScript.objRepository("SectionTab",file)))!= null)
			{
				String sec=driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjsecTxt')]")).getText();
				DriverScript.setVariable("Section_tab",worksheet,row,sec);
			}
		}catch(NoSuchElementException t){
			Reporter.log("ClassZip, Authority, Number and Section were not displayed");
		}

		driver.findElement(By.xpath(driverScript.objRepository("MinWeight",file))).sendKeys(minimumWeight);
		driver.findElement(By.xpath(driverScript.objRepository("MaxWeight",file))).sendKeys(maximumWeight);
		driver.findElement(By.xpath(driverScript.objRepository("StateMatrix",file))).sendKeys(stateMatrix);

		Select SC = new Select(driver.findElement(By.xpath(driverScript.objRepository("ShipmentCond",file))));
		SC.selectByValue(shipmentCond);
		Thread.sleep(2000);

		Select CT = new Select(driver.findElement(By.xpath(driverScript.objRepository("CurrencyType",file))));
		CT.selectByValue(currencyType);
		Thread.sleep(2000);

		Select PSA = new Select(driver.findElement(By.xpath(driverScript.objRepository("PreminumSA",file))));
		PSA.selectByValue(premiumSvcApply);
		Thread.sleep(2000);

		Select SR = new Select(driver.findElement(By.xpath(driverScript.objRepository("SurchargeRule",file))));
		SR.selectByValue(surchargeRules);
		Thread.sleep(2000);

		Select AAC = new Select(driver.findElement(By.xpath(driverScript.objRepository("ApplyAC",file))));
		AAC.selectByValue(applyArbitraryCharges);
		Thread.sleep(2000);

		Select STG = new Select(driver.findElement(By.xpath(driverScript.objRepository("SubjectGRI",file))));
		STG.selectByVisibleText(subjecttoGRI);
		Thread.sleep(2000);

		driver.findElement(By.xpath(driverScript.objRepository("Authority",file))).sendKeys(authority);
		driver.findElement(By.xpath(driverScript.objRepository("Number",file))).sendKeys(number);
		driver.findElement(By.xpath(driverScript.objRepository("Item",file))).sendKeys(item);
		driver.findElement(By.xpath(driverScript.objRepository("ItemSuffix",file))).sendKeys(itemSuffix);
		driver.findElement(By.xpath(driverScript.objRepository("AdjPercentage",file))).sendKeys(adjustmentPercentage);
		driver.findElement(By.xpath(driverScript.objRepository("AdjPoint",file))).sendKeys(adjustmentPoints);

		Select IncDec1 = new Select(driver.findElement(By.xpath(driverScript.objRepository("IncDec1",file))));
		IncDec1.selectByValue(increaseDecrease1);
		Thread.sleep(2000);

		driver.findElement(By.xpath(driverScript.objRepository("Fromto1",file))).sendKeys(from_to1);
		driver.findElement(By.xpath(driverScript.objRepository("Dir1",file))).sendKeys(dir1);
		driver.findElement(By.xpath(driverScript.objRepository("Type1",file))).sendKeys(type1);
		driver.findElement(By.xpath(driverScript.objRepository("Name1",file))).sendKeys(name1);
		driver.findElement(By.xpath(driverScript.objRepository("State1",file))).sendKeys(state1);
		//driver.findElement(By.xpath(driverScript.objRepository("County1",file))).sendKeys(County1);
		driver.findElement(By.xpath(driverScript.objRepository("Country1",file))).sendKeys(country1);

		driver.findElement(By.xpath(driverScript.objRepository("Fromto2",file))).sendKeys(from_to2);
		driver.findElement(By.xpath(driverScript.objRepository("Dir2",file))).sendKeys(dir2);
		driver.findElement(By.xpath(driverScript.objRepository("Type2",file))).sendKeys(type2);
		driver.findElement(By.xpath(driverScript.objRepository("Name2",file))).sendKeys(name2);
		driver.findElement(By.xpath(driverScript.objRepository("State2",file))).sendKeys(state2);
		//driver.findElement(By.xpath(driverScript.objRepository("County2",file))).sendKeys(County2);
		driver.findElement(By.xpath(driverScript.objRepository("Country2",file))).sendKeys(country2);

		driver.findElement(By.xpath(driverScript.objRepository("ServiceDaysLow",file))).sendKeys(serviceDaysLow);
		driver.findElement(By.xpath(driverScript.objRepository("ServiceDaysHigh",file))).sendKeys(serviceDaysHigh);
		driver.findElement(By.xpath(driverScript.objRepository("ZoneAuthority",file))).sendKeys(zoneAuthority);
		driver.findElement(By.xpath(driverScript.objRepository("ZoneAuthorityNumber",file))).sendKeys(zoneAuthorityNumber);
		driver.findElement(By.xpath(driverScript.objRepository("Zone",file))).sendKeys(zone);

		Select EC_ALL = new Select(driver.findElement(By.xpath(driverScript.objRepository("ExceptionClassAll",file))));
		EC_ALL.selectByValue(exceptionClassAll);
		Thread.sleep(2000);

		driver.findElement(By.xpath(driverScript.objRepository("ExClassMaxWt",file))).sendKeys(exceptionClassMaxWt);

		try{
			if(!addLines.equalsIgnoreCase(""))
			{
			driver.findElement(By.xpath(driverScript.objRepository("AddLines",file))).sendKeys(addLines);
			driver.findElement(By.xpath(driverScript.objRepository("AddLinesClick",file))).click();

			Select NMFC = new Select(driver.findElement(By.xpath(driverScript.objRepository("NMFC_Class",file))));
			NMFC.selectByValue(nmfcType);

			driver.findElement(By.xpath(driverScript.objRepository("ClassNMFCLR",file))).sendKeys(classNMFCLR);
			driver.findElement(By.xpath(driverScript.objRepository("ClassNMFCHR",file))).sendKeys(classNMFCHR);

			Select ExpClass = new Select(driver.findElement(By.xpath(driverScript.objRepository("ExceptionClass1",file))));
			ExpClass.selectByValue(exceptionClass1);
			}}catch(Exception e){
				Reporter.log("Error Msg:NMFC is not applied"); 
			}

/*
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_FreightVolume - WeightBreakDiscounts
 */
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("AMCType",file))).isEnabled());
		Reporter.log("AMC Type is enabled");
		Select AMC_Type = new Select(driver.findElement(By.xpath(driverScript.objRepository("AMCType",file))));
		AMC_Type.selectByValue(aMCType);
		File AMCTypeScreenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(AMCTypeScreenshot, new File("./target/screenshots/VD WeightBreakAdj/AMC Type.jpeg"));
		Thread.sleep(2000);
		
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("AMC",file))).isEnabled());
		Reporter.log("AMC is enabled");
		driver.findElement(By.xpath(driverScript.objRepository("AMC",file))).sendKeys(aMC);

		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("IncDec2",file))).isEnabled());
		Reporter.log("Increase-Decrease is enabled");
		Select IncDec2 = new Select(driver.findElement(By.xpath(driverScript.objRepository("IncDec2",file))));
		IncDec2.selectByValue(increaseDecrease2);
		File AMCIncDecScreenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(AMCIncDecScreenshot, new File("./target/screenshots/VD WeightBreakAdj/AMC IncDec.jpeg"));
		Thread.sleep(2000);

		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("MCLineofRatesDiscountType",file))).isEnabled());
		Reporter.log("MC Line of Rates Discount Type is enabled");
		Select MCLRDT = new Select(driver.findElement(By.xpath(driverScript.objRepository("MCLineofRatesDiscountType",file))));
		MCLRDT.selectByValue(mCLineofRatesDiscountType);
		File MCLRDTScreenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(MCLRDTScreenshot, new File("./target/screenshots/VD WeightBreakAdj/MC Line of Rates Discount Type.jpeg"));
		Thread.sleep(2000);

		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("MCLineofRatesDiscount",file))).isEnabled());
		Reporter.log("MC Line of Rates Discount is enabled");
		driver.findElement(By.xpath(driverScript.objRepository("MCLineofRatesDiscount",file))).sendKeys(mCLineofRatesDiscount);
		
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("MaxLineRateWeight",file))).isEnabled());
		Reporter.log("Max Line Rate Weight is enabled");
		driver.findElement(By.xpath(driverScript.objRepository("MaxLineRateWeight",file))).sendKeys(maxLineRateWeight);
		
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("MaximumCharge",file))).isEnabled());
		Reporter.log("Maximum Charge is enabled");
		driver.findElement(By.xpath(driverScript.objRepository("MaximumCharge",file))).sendKeys(maximumCharge);
		Thread.sleep(2000);

		WebElement  element1=driver.findElement(By.xpath(driverScript.objRepository("ManageDisWgtBrk",file)));
		JavascriptExecutor executor1 = (JavascriptExecutor)driver;
		executor1.executeScript("arguments[0].click();", element1);
		Thread.sleep(10000);
		driver.findElement(By.xpath(driverScript.objRepository("PopUp",file))).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath(driverScript.objRepository("SaveMngWgtBrk",file))).click();
		Thread.sleep(10000);

		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("DiscountType",file))).isEnabled());
		Reporter.log("Discount Type is enabled");
		Select DT = new Select(driver.findElement(By.xpath(driverScript.objRepository("DiscountType",file))));
		DT.selectByValue(discountType);
		File DiscountTypeScreenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(DiscountTypeScreenshot, new File("./target/screenshots/VD WeightBreakAdj/Discount Type.jpeg"));
		Thread.sleep(2000);
/*
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_FreightVolume - WeightBreakDiscounts - TruckloadMinimum
 */
//Verify that the minimum and maximum Shipment weight breaks are disabled
		//Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjdscntlwrVal')]")).isDisplayed());
		//Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'wtBrkDisAdjdscntUprVal')]")).isDisplayed());
		
		File MinandMaxWghtBrk = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(MinandMaxWghtBrk, new File("./target/screenshots/VD WeightBreakAdj/Min and Max Weight Breaks.jpeg"));
				
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("Discount",file))).isEnabled());
		driver.findElement(By.xpath(driverScript.objRepository("Discount",file))).sendKeys(discount);
		
		Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("Comments",file))).isEnabled());
		driver.findElement(By.xpath(driverScript.objRepository("Comments",file))).sendKeys(comments);

		Select MCLRAT = new Select(driver.findElement(By.xpath(driverScript.objRepository("MCLineofRatesAdjType",file))));
		MCLRAT.selectByValue(mCLineofRatesAdjType);
		Thread.sleep(2000);

		driver.findElement(By.xpath(driverScript.objRepository("MCLineofRatesAdjustment",file))).sendKeys(mCLineofRatesAdjustment);

		Select IncDec3 = new Select(driver.findElement(By.xpath(driverScript.objRepository("IncDec3",file))));
		IncDec3.selectByValue(increaseDecrease3);
		Thread.sleep(2000);

		driver.findElement(By.xpath(driverScript.objRepository("ManageAdjWgtBrk",file))).click();
		element1=driver.findElement(By.xpath(driverScript.objRepository("ManageAdjWgtBrk",file)));
		executor1 = (JavascriptExecutor)driver;
		executor1.executeScript("arguments[0].click();", element1);
		Thread.sleep(10000);
		driver.findElement(By.xpath(driverScript.objRepository("PopUp2",file))).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath(driverScript.objRepository("SaveMngAdjWgtBrk",file))).click();
		Thread.sleep(10000);

		Select AT = new Select(driver.findElement(By.xpath(driverScript.objRepository("AdjustmentType",file))));
		AT.selectByValue(adjustmentType);
		Thread.sleep(2000);

		driver.findElement(By.xpath(driverScript.objRepository("Adjustment",file))).sendKeys(adjustment);

		Select IncDec4 = new Select(driver.findElement(By.xpath(driverScript.objRepository("IncDec4",file))));
		IncDec4.selectByValue(increaseDecrease4);
		Thread.sleep(2000);

		driver.findElement(By.xpath(driverScript.objRepository("AdjComments",file))).sendKeys(comments);
		Actions a = new Actions(driver);
		a.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
		Thread.sleep(20000);

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
			Thread.sleep(10000);
		}catch(Exception e){	
			Reporter.log("Overlay button doesn't exist");
		}
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
		LOGGER.debug(ProposalID);
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

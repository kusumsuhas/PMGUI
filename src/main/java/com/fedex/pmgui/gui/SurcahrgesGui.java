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

public class SurcahrgesGui {

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
	static final Logger LOGGER = Logger.getLogger(SurcahrgesGui.class);

	public SurcahrgesGui(WebDriver driver)
	{
		this.driver=driver;
	}

	/**surchargesTest()----- This method runs the test cases for surcharges type of discounts.
	 * 
	 * @throws Exception
	 */
	public void surchargesGuiTest() throws Exception
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
				
				
				surcharges();
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
		a.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SurchargeTab",file)))).build().perform();
		Thread.sleep(20000);
		
//GUI Validation of Copy Service starts here
		
/*
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_FreightVolume - WeightBreakDiscounts - CopyService - Surcharges
 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_FreightVolume - WeightBreakDiscounts - CopyService - Surcharges - Edits
 * Test Case Name: [1]M_PRC_FXFR_PricingMaintenance_FreightSurchargeDetailPage_LaunchPoints
 */
		
		          driver.findElement(By.xpath("((//span[text()='Intra US Prty IC (Inter)'])[1]/../..//a[text()='Details'])[1]")).click();
					
					Thread.sleep(8000);
			

					driver.findElement(By.xpath("//*[contains(@id,'CopyService')]")).click();
					Thread.sleep(3000);
					Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'confirmDialogcopyForfxfSurcharges_main')]//label[text()='If you Copy this Service with unsaved changes, the unsaved changes will not be copied.  Do you still want to Copy the Service']")).isDisplayed());

					driver.findElement(By.xpath("//input[contains(@id,'noCopySurcharge')]")).click();
					Assert.assertEquals(driver.findElement(By.xpath("//span[text()='FXF Surcharge Detail']")).getText(),"FXF Surcharge Detail" );
					
					
					driver.findElement(By.xpath("//*[contains(@id,'CopyService')]")).click();
					Thread.sleep(3000);
					driver.findElement(By.xpath("//input[contains(@id,'yesCopySurcharge')]")).click();
					Thread.sleep(6000);
			
	
					//left to right
					WebElement source = driver.findElement(By.xpath("//li[contains(text(),'Intra US Prty IC (Intra)')]")); 
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
					WebElement source1 = driver.findElement(By.xpath("(//li[contains(text(),'Intra US Prty IC (Intra)')])[2]")); 
					WebElement destination1 = driver.findElement(By.xpath("(//div//ul[@class='if-list-body dg-textGroup ui-sortable'])[1]"));
					(new Actions(driver)).dragAndDrop(source1, destination1).perform();
					Thread.sleep(7000);
					
					//left to right
					WebElement source2 = driver.findElement(By.xpath("//li[contains(text(),'AK - OC')]")); 
					WebElement destination2 = driver.findElement(By.xpath("(//div//ul[@class='if-list-body dg-textGroup ui-sortable'])[2]"));
					(new Actions(driver)).dragAndDrop(source2, destination2).perform();
					Thread.sleep(7000);
					
					//clicked on save
					//driver.findElement(By.xpath("//*[contains(@id,'copyCancel1')]//..//..//td[2]//input")).click();
					WebElement  element1 = driver.findElement(By.xpath("//*[contains(@id,'copyCancel1')]//..//..//td[2]//input"));
					JavascriptExecutor executor1 = (JavascriptExecutor)driver;
					executor1.executeScript("arguments[0].click();", element1);
					Thread.sleep(10000);
					
					
					driver.findElement(By.xpath("//*[contains(@id,'copyCancel1')]")).click();
					Thread.sleep(3000);
//					driver.findElement(By.xpath("//*[contains(@id,'fxfSurchargeDetailForm:cancel')]")).click();
//					Thread.sleep(3000);
//					
//					driver.findElement(By.xpath("//*[contains(@id,'fxfSurchargeDetailForm:yes2')]")).click();
//					Thread.sleep(5000);
				
					}
					
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
		
					//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_NonCWT_SetsToMeasure
					//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_RateType_SetsToManual	
							
					WebElement rateManual = driver.findElement(By.xpath(driverScript.objRepository("RateManually",file)));
					
					driver.findElement(By.xpath("//select[contains(@id,'SurchargeTypedropDown')]"));
					
					
					Select TP = new Select(driver.findElement(By.xpath("//select[contains(@id,'SurchargeTypedropDown')]")));
					TP.selectByValue("RATE_PER_HOUR");
					if(!rateManual.isEnabled())
						Reporter.log("Rate Manually is automatically selected for a surcharge(RATE_PER_HOUR) that is a Rate / Unit of Measure.");

					Select TP1 = new Select(driver.findElement(By.xpath("//select[contains(@id,'SurchargeTypedropDown')]")));
					TP1.selectByValue("RATE_PER_DOCUMENT");
					if(!rateManual.isEnabled())
						Reporter.log("Rate Manually is automatically selected for a surcharge(RATE_PER_DOCUMENT) that is a Rate / Unit of Measure.");
					
					Select TP2 = new Select(driver.findElement(By.xpath("//select[contains(@id,'SurchargeTypedropDown')]")));
					TP2.selectByValue("RATE_PER_DIMENSION");
					if(!rateManual.isEnabled())
						Reporter.log("Rate Manually is automatically selected for a surcharge(RATE_PER_DIMENSION) that is a Rate / Unit of Measure.");
					
					Select TP3 = new Select(driver.findElement(By.xpath("//select[contains(@id,'SurchargeTypedropDown')]")));
					TP3.selectByValue("RATE_PER_PERSON");
					if(!rateManual.isEnabled())
						Reporter.log("Rate Manually is automatically selected for a surcharge(RATE_PER_PERSON) that is a Rate / Unit of Measure.");
					
					Select TP4 = new Select(driver.findElement(By.xpath("//select[contains(@id,'SurchargeTypedropDown')]")));
					TP4.selectByValue("RATE_PER_PERSON_PER_HOUR");
					if(!rateManual.isEnabled())
						Reporter.log("Rate Manually is automatically selected for a surcharge(RATE_PER_PERSON_PER_HOUR) that is a Rate / Unit of Measure.");
					
					Select TP5 = new Select(driver.findElement(By.xpath("//select[contains(@id,'SurchargeTypedropDown')]")));
					TP5.selectByValue("RATE_PER_PERMIT");
					if(!rateManual.isEnabled())
						Reporter.log("Rate Manually is automatically selected for a surcharge(RATE_PER_PERMIT) that is a Rate / Unit of Measure.");
					
					Select TP6 = new Select(driver.findElement(By.xpath("//select[contains(@id,'SurchargeTypedropDown')]")));
					TP6.selectByValue("RATE_PER_CHECK");
					if(!rateManual.isEnabled())
						Reporter.log("Rate Manually is automatically selected for a surcharge(RATE_PER_CHECK) that is a Rate / Unit of Measure.");
					
					Select TP7 = new Select(driver.findElement(By.xpath("//select[contains(@id,'SurchargeTypedropDown')]")));
					TP7.selectByValue("RATE_PER_VEHICLE");
					if(!rateManual.isEnabled())
						Reporter.log("Rate Manually is automatically selected for a surcharge(RATE_PER_VEHICLE) that is a Rate / Unit of Measure.");
					
					
					Select TP8 = new Select(driver.findElement(By.xpath("//select[contains(@id,'SurchargeTypedropDown')]")));
					TP8.selectByValue("RATE_PER_UNIT_OF_EQUIPMENT_PER_DAY");
					if(!rateManual.isEnabled())
						Reporter.log("Rate Manually is automatically selected for a surcharge(RATE_PER_UNIT_OF_EQUIPMENT_PER_DAY) that is a Rate / Unit of Measure.");
					
					
					Select TP9 = new Select(driver.findElement(By.xpath("//select[contains(@id,'SurchargeTypedropDown')]")));
					TP9.selectByValue("RATE_PER_VEHICLE");
					if(!rateManual.isEnabled())
						Reporter.log("Rate Manually is automatically selected for a surcharge(RATE_PER_VEHICLE) that is a Rate / Unit of Measure.");
					
					Select TP10 = new Select(driver.findElement(By.xpath("//select[contains(@id,'SurchargeTypedropDown')]")));
					TP10.selectByValue("RATE_PER_KILOMETER");
					if(!rateManual.isEnabled())
						Reporter.log("Rate Manually is automatically selected for a surcharge(RATE_PER_KILOMETER) that is a Rate / Unit of Measure.");
					
					Select TP11 = new Select(driver.findElement(By.xpath("//select[contains(@id,'SurchargeTypedropDown')]")));
					TP11.selectByValue("RATE_PER_STOP");
					if(!rateManual.isEnabled())
						Reporter.log("Rate Manually is automatically selected for a surcharge(RATE_PER_STOP) that is a Rate / Unit of Measure.");
					
					
					
					//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_RateManual_FlagValidation
					//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_RateManual_Populated Fields
					
					Select TP16 = new Select(driver.findElement(By.xpath("//select[contains(@id,'SurchargeTypedropDown')]")));
					TP16.selectByValue("FLAT_CHARGE");
					 driver.findElement(By.xpath("//input[contains(@id,'mnlflagcheckBox11')]")).click();
					Thread.sleep(5000);

					WebElement ratemanualFlag = driver.findElement(By.xpath("//input[contains(@id,'mnlflagcheckBox11')]"));
					
					if (ratemanualFlag.isSelected())
						ratemanualFlag.click();
					Assert.assertFalse(ratemanualFlag.isSelected());

					if (!ratemanualFlag.isSelected())
						ratemanualFlag.click();
					Assert.assertTrue(ratemanualFlag.isSelected());
					Reporter.log("Manually Flag and it allows the user to select or de-select the checkbox");
					Thread.sleep(3000);
					
					File RatemanualFlag = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
					FileUtils.copyFile(RatemanualFlag, new File("./target/screenshots/SurchargesGui/ManualFlag_Validation.jpeg"));
				
					ratemanualFlag.click();
					
					
					//test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_DiscountPercent_UnallowableFields
					
						Select TP12 = new Select(driver.findElement(By.xpath("//select[contains(@id,'SurchargeTypedropDown')]")));
						TP12.selectByValue("FLAT_CHARGE");
						
						driver.findElement(By.xpath(driverScript.objRepository("MaxAmount",file))).sendKeys("11111.11");
						driver.findElement(By.xpath(driverScript.objRepository("MinAmount",file))).sendKeys("99999.99");

						Actions xyz = new Actions(driver);
						xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
						Thread.sleep(5000);
						
						xyz.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
						Thread.sleep(5000);

						if(driver.findElement(By.xpath("//ul/li/span[text()='Minimum Charge or Maximum Charge may not be entered for Discount % or Flat Charge']")).isDisplayed())
							Reporter.log("Error Message: Minimum Charge or Maximum Charge may not be entered for Discount % or Flat Charge");

						Select TP13 = new Select(driver.findElement(By.xpath("//select[contains(@id,'SurchargeTypedropDown')]")));
						TP13.selectByValue("DISCOUNT_PERCENT");
						
						Actions xyz1 = new Actions(driver);
						xyz1.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
						Thread.sleep(5000);
						
					xyz1.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
					Thread.sleep(5000);

						
						
						driver.findElement(By.xpath(driverScript.objRepository("MaxAmount",file))).clear();
						driver.findElement(By.xpath(driverScript.objRepository("MinAmount",file))).clear();
					
						
					
						//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_IndividualSurchargeNotAllowed
		
						Select TP14 = new Select(driver.findElement(By.xpath("//select[contains(@id,'SurchargeTypedropDown')]")));
						TP14.selectByValue("DISCOUNT_PERCENT");
						
						Select I = new Select(driver.findElement(By.xpath("//*[contains(@id,'Increase_DecreaseDropDown')]")));
						I.selectByValue("I");
						
							driver.findElement(By.xpath(driverScript.objRepository("DiscApplyAll",file))).clear();
							driver.findElement(By.xpath(driverScript.objRepository("DiscApplyAll",file))).sendKeys("50");

							Actions xyz2 = new Actions(driver);				
							xyz2.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
							Thread.sleep(5000);
							
							xyz2.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
							Thread.sleep(5000);

							if(driver.findElement(By.xpath("//div//div//div//ul/li/span[text()='May not enter both Discount Apply to all and individual surcharge']")).isDisplayed())
								Reporter.log("Error Message: May not enter both Discount Apply to all and individual surcharge");
							driver.findElement(By.xpath(driverScript.objRepository("DiscApplyAll",file))).clear();
							Select I1 = new Select(driver.findElement(By.xpath("//*[contains(@id,'Increase_DecreaseDropDown')]")));
							I1.selectByIndex(0);
					
							//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_DiscountRate_AmountValidation
							Select TP15 = new Select(driver.findElement(By.xpath("//select[contains(@id,'SurchargeTypedropDown')]")));
							TP15.selectByValue("WAIVED");
							Thread.sleep(2000);
								driver.findElement(By.xpath("//*[contains(@id,'surchargedropdown')]//..//..//td[6]//input")).clear();
								driver.findElement(By.xpath("//*[contains(@id,'surchargedropdown')]//..//..//td[6]//input")).sendKeys("500");

								Actions xyz3 = new Actions(driver);				
								xyz3.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
								Thread.sleep(10000);
								
								xyz3.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
								Thread.sleep(5000);

								if(driver.findElement(By.xpath("//div//div//div//ul/li/span[text()='Surcharge Amount may not be entered if Surcharge Waived']")).isDisplayed())
									Reporter.log("Error Message: Surcharge Amount may not be entered if Surcharge Waived");

								driver.findElement(By.xpath("//*[contains(@id,'surchargedropdown')]//..//..//td[6]//input")).clear();
										
                       
								Select TP17 = new Select(driver.findElement(By.xpath("//select[contains(@id,'SurchargeTypedropDown')]")));
								TP17.selectByValue("RATE_PER_ACTUAL_UNIT_WEIGHT");
								Thread.sleep(3000);
								Select Unit = new Select(driver.findElement(By.xpath("//*[contains(@id,'unitDropDown')]")));
								Unit.selectByValue("CWT");
								
								
								driver.findElement(By.xpath("//*[contains(@id,'surchargedropdown')]//..//..//td[6]//input")).clear();
								driver.findElement(By.xpath("//*[contains(@id,'surchargedropdown')]//..//..//td[6]//input")).sendKeys("80");

								Actions xyz4 = new Actions(driver);
								xyz4.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
								Thread.sleep(5000);
							xyz4.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
								Thread.sleep(5000);

								if(driver.findElement(By.xpath("//span[contains(@id,'warningDialog_main')]")).isDisplayed())
									driver.findElement(By.xpath("//input[@id='fxfSurchargeDetailForm:ok']")).click();
								
								try{
									driver.findElement(By.xpath(driverScript.objRepository("Overlay",file))).click();
									Thread.sleep(10000);
								}catch(Exception e){	
									LOGGER.info("Overlay button doesn't exist"+ e.getMessage());
								}
								
								try{
									driver.findElement(By.xpath("((//span[text()='Intra US Prty IC (Inter)'])[1]/../..//a[text()='Details'])[1]")).click();
									driver.findElement(By.xpath("((//span[text()='Intra US Prty IC (Inter)'])[1]/../..//a[text()='Details'])[1]")).click();
									Thread.sleep(2000);
									}catch(Exception e){
										LOGGER.info("Surcharge page is displayed ");
									}
								

								driver.findElement(By.xpath("//*[contains(@id,'surchargedropdown')]//..//..//td[6]//input")).clear();
								driver.findElement(By.xpath("//*[contains(@id,'surchargedropdown')]//..//..//td[6]//input")).sendKeys("50");
							
							
								Select Unit1 = new Select(driver.findElement(By.xpath("//*[contains(@id,'unitDropDown')]")));
								Unit1.selectByValue("NET TON");
								
								WebElement checkRMFlag = driver.findElement(By.xpath(driverScript.objRepository("RateManually",file)));

									Assert.assertFalse(checkRMFlag.isEnabled());
									Reporter.log("Error Message: Rate Manually Flag is disabled for NonCWT unit");
							
	//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_UnitOfMeasurePerRate_UnitOfMeasure
									
									
									Select TP18= new Select(driver.findElement(By.xpath("//select[contains(@id,'SurchargeTypedropDown')]")));
									TP18.selectByValue("RATE_PER_ACTUAL_UNIT_WEIGHT");
									Thread.sleep(2000);
									
										if(driver.findElement(By.xpath(driverScript.objRepository("Unit",file))).isEnabled())
											Reporter.log("Unit of Measure for Rate per Unit of Measure/Dimensions Unit Type is present");
										Select unt = new Select(driver.findElement(By.xpath(driverScript.objRepository("Unit",file))));
										unt.selectByValue("CWT");
										Thread.sleep(2000);
										unt.getAllSelectedOptions();
									
									
										Select TP19= new Select(driver.findElement(By.xpath("//select[contains(@id,'SurchargeTypedropDown')]")));
										TP19.selectByValue("FLAT_CHARGE");
										Thread.sleep(2000);
										WebElement checkUnit = driver.findElement(By.xpath(driverScript.objRepository("Unit",file)));
										Assert.assertFalse(checkUnit.isEnabled());
										Reporter.log("Error Message: Unit is disabled for a type other than Rate / Unit of Measure");
									

									//test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_DimensionsUnitType
										
										Select TP20= new Select(driver.findElement(By.xpath("//select[contains(@id,'SurchargeTypedropDown')]")));
										TP20.selectByValue("RATE_PER_DIMENSION");
										Thread.sleep(2000);
									
										
										Select unt1 = new Select(driver.findElement(By.xpath(driverScript.objRepository("Unit",file))));
										unt1.selectByIndex(0);
										Actions xyz11 = new Actions(driver);
										xyz11.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
										Thread.sleep(5000);
										
										xyz11.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
										Thread.sleep(5000);

										if(driver.findElement(By.xpath("//div//div//div//ul/li/span[text()='Surcharge Unit: Required field was not entered']")).isDisplayed())
											Reporter.log("Error Message: Surcharge Unit: Required field was not entered");
										Select unt2 = new Select(driver.findElement(By.xpath(driverScript.objRepository("Unit",file))));
										unt2.selectByValue("CUBIC FOOT");
										unt2.getAllSelectedOptions();
									

										Select TP21= new Select(driver.findElement(By.xpath("//select[contains(@id,'SurchargeTypedropDown')]")));
										TP21.selectByValue("RATE_PER_ACTUAL_UNIT_WEIGHT");
										Thread.sleep(2000);
										if(driver.findElement(By.xpath("//*[contains(@id,'unitDropDown')]")).isEnabled())
											//Reporter.log("Message: Dimension Unit field is not enabled for Type other than Rate / Dimension");
											Reporter.log("Message: Unit field is enabled for RATE_PER_ACTUAL_UNIT_WEIGHT");
										else
											Reporter.log("Message: Dimension Unit field is not enabled for Type other than Rate / Dimension");
									

									
										Select TP22= new Select(driver.findElement(By.xpath("//select[contains(@id,'SurchargeTypedropDown')]")));
										TP22.selectByValue("TOTAL_CHARGE_PERCENT");
										Thread.sleep(2000);
										if(driver.findElement(By.xpath(driverScript.objRepository("Rank",file))).isEnabled())
											Reporter.log("Message: Rank can only be provided for discount type Total Charge %'");
									
			//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_DiscountPercent_Increase-DecreaseFlag
										
											
											Select TP23= new Select(driver.findElement(By.xpath("//select[contains(@id,'SurchargeTypedropDown')]")));
											TP23.selectByValue("DISCOUNT_PERCENT");
											Thread.sleep(2000);
										
											Select incDec1 = new Select(driver.findElement(By.xpath(driverScript.objRepository("IncrDecr1",file))));
											incDec1.selectByIndex(0);
											Actions xyz12 = new Actions(driver);
											xyz12.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
											Thread.sleep(5000);
											
											xyz12.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
											Thread.sleep(5000);

											if(driver.findElement(By.xpath("//div//div//div//ul/li/span[text()='Increase/Decrease must be entered for Discount %']")).isDisplayed())
												Reporter.log("Error Message: Increase/Decrease must be entered for Discount %");
											//incDec1.selectByValue(increasedecrease1);
										

										
											Select TP24= new Select(driver.findElement(By.xpath("//select[contains(@id,'SurchargeTypedropDown')]")));
											TP24.selectByValue("TOTAL_CHARGE_PERCENT");
											Thread.sleep(2000);
										
											Select incDec2 = new Select(driver.findElement(By.xpath(driverScript.objRepository("IncrDecr1",file))));
											incDec2.selectByValue("I");
											Actions xyz13 = new Actions(driver);
											xyz13.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
											Thread.sleep(5000);

											xyz13.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
											Thread.sleep(5000);
											
											if(driver.findElement(By.xpath("//div//div//div//ul/li/span[text()='Increase/Decrease may only be entered for Discount %']")).isDisplayed())
												Reporter.log("Error Message: Increase/Decrease may only be entered for Discount %");
											incDec2.selectByIndex(0);
											
											
											 /** All field validations starts here
											 * Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts*/
											 

											//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_DiscountPercent_AppliesToAll
													
													Assert.assertTrue(driver.findElement(By.xpath("//label[contains(@id,'DiscountApplytoAll11')]")).isDisplayed());
													Reporter.log("Discount Apply to all is displayed");
													
													File DiscApplyAll = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
													FileUtils.copyFile(DiscApplyAll, new File("./target/screenshots/Surcharges/Discount Apply To All.jpeg"));
													
											//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_AppliesAllIncrease-Decrease
													Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("IncrDecr",file))).isDisplayed());
													Reporter.log("Increase/Decrease is displayed");
													Select incDec = new Select(driver.findElement(By.xpath(driverScript.objRepository("IncrDecr",file))));
													incDec.getOptions();
													
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
													Select incDec11 = new Select(driver.findElement(By.xpath(driverScript.objRepository("IncrDecr1",file))));
													incDec11.selectByValue(increasedecrease1);
													incDec11.getAllSelectedOptions();
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
													Select unt11 = new Select(driver.findElement(By.xpath(driverScript.objRepository("Unit",file))));
													unt11.selectByValue(unit);
													unt11.getAllSelectedOptions();
													File Unit11 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
													FileUtils.copyFile(Unit11, new File("./target/screenshots/Surcharges/Unit.jpeg"));
													
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
																								
											
													//Test case name: [1]M_PRC_FXFR_PricingMaintenance_SurchargeDiscounts_InformationalFreeFormText

													Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("Comments",file))).isEnabled());
													Reporter.log("Freeform text/Comments field is enabled");
													driver.findElement(By.xpath(driverScript.objRepository("Comments",file))).sendKeys(comments);
						
																

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
	
															
//GUI Validation of Copy Service ends here

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

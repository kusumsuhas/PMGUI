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
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.log4testng.Logger;

import com.fedex.pmgui.common.AccountEntry;
import com.fedex.pmgui.common.AddServices;
import com.fedex.pmgui.common.DatePicker;
import com.fedex.pmgui.common.LoginPage;
import com.fedex.pmgui.common.SelectAccountEntry;
import com.fedex.pmgui.common.ServiceSelection;
import com.fedex.pmgui.driverscript.DriverScript;

import org.testng.Assert;
import org.testng.Reporter;

public class SurchargesCODGui {

	DriverScript driverScript= new DriverScript();
	WebDriver driver ;
	String file;
	String id, password, accountType, accountNumber, region, country, serviceTab, serviceGroup, selectService;
	String agreementNumber,proposalNbr,pricingDiscountType,pricingSummary,startDate,endDate,minimumWeight,maxWeight,stateMatrix,shipmentCondition,currencyType,subjGRI,plusminus1,dir1,type1,name1,state1,county1,country1,plusminus2,dir2,type2,name2,state2,county2,country2,servicedayslow,servicedayshigh,exceptionClassAll,excptclassmaxwgt,addLines,nmfcType, classNMFCLR,classNMFCHR,exceptionClass,rateManually,minCODAmt,maxCODAmt,codType,incrDecr,cODFlatFee,codFeePercent,perCODAmt,minCharge,maxCharge,comments,requestName,copyService;
	String [] date;
	XSSFWorkbook workbook;
	XSSFSheet worksheet;
	XSSFRow row;

	static final Logger LOGGER = Logger.getLogger(SurchargesCODGui.class);

	public SurchargesCODGui(WebDriver driver)
	{
		this.driver=driver;
	}


	public void surchargesCODTest() throws Exception
	{
		try{
			file= DriverScript.surchargesCOD;
			FileInputStream FileInputStream = new FileInputStream(DriverScript.workSheetPath);
			workbook = new XSSFWorkbook(FileInputStream);

			worksheet = workbook.getSheet("COD");

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

				cashOnDeliveryGui();
				logOut();
			}
		}catch(Exception e)
		{
			LOGGER.error("Gui validation was not successfull"+ e.getMessage());
		}

	}

	public void cashOnDeliveryGui() throws InterruptedException, IOException{

		Actions a = new Actions(driver);
		a.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SurchargeTab",file)))).build().perform();
		a.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SurchargeTab",file)))).build().perform();
		driver.findElement(By.xpath("//em[text()='Surcharges']")).click();
		driver.findElement(By.xpath("//em[text()='Surcharges']")).click();
		driver.findElement(By.xpath("//em[text()='Surcharges']")).sendKeys(Keys.ENTER);
	
		Thread.sleep(20000);
		
		
			
     driver.findElement(By.xpath("(//span[text()='Intra US Prty IC (Inter)'])[1]/../..//a[text()='Details']")).click();
	 Thread.sleep(5000);
	 
	 
//[1]Test Case Name:M_PRC_FXFR_PricingMaintenance_FreightSurchargeDetailPage_PMGUIFlow_FXFSurcharge_CODSubgroup
	 
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
	
//[1]Test Case Name:M_PRC_FXFR_PricingMaintenance_CODDiscounts_CODFlatFee
	 Select Type4 = new Select(driver.findElement(By.xpath(driverScript.objRepository("Type",file))));
     Type4.selectByIndex(1);  
     
     Select I4 = new Select(driver.findElement(By.xpath(driverScript.objRepository("IncrDecr", file))));
     I4.selectByIndex(0);
	
     driver.findElement(By.xpath("//*[contains(@id,'CODTypedropDown')]//..//..//td[6]//input")).clear();
     driver.findElement(By.xpath("//*[contains(@id,'CODTypedropDown')]//..//..//td[6]//input")).sendKeys("12345.67");
     driver.findElement(By.xpath("//*[contains(@id,'CODTypedropDown')]//..//..//td[7]//input")).clear();
     
     driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
     Thread.sleep(4000);
     
     try{
     	driver.findElement(By.xpath("//*[contains(@id,'continueButtonOverlayFxfSurcharge')]")).click();
 	}catch(Exception e){	
 		System.out.println("Overlay button doesn't exist"+ e.getMessage());
 	}	
     
     Reporter.log("The field is labeled: 'COD Flat Fee' and the system accepts data in the following format:  XXXXX.XX");
     driver.findElement(By.xpath("//*[contains(@id,'CODTypedropDown')]//..//..//td[6]//input")).clear(); 
     driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
     Thread.sleep(4000);//////////////
     //Assert.assertTrue(driver.findElement(By.xpath("(//*[text()[contains(.,'COD Fee Amt is required with the selected COD Type')]])[3]")).isDisplayed());
     Reporter.log("Error Msg:COD Fee Amt is required with the selected COD Type");//claer and enter
     
     driver.findElement(By.xpath("//*[contains(@id,'CODTypedropDown')]//..//..//td[6]//input")).sendKeys("123");
     
     driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
     Thread.sleep(4000);
     
     try{
     	driver.findElement(By.xpath("//*[contains(@id,'continueButtonOverlayFxfSurcharge')]")).click();
 	}catch(Exception e){	
 		System.out.println("Overlay button doesn't exist"+ e.getMessage());
 	}	
     
	 
//[1]Test Case Name:M_PRC_FXFR_PricingMaintenance_CODDiscounts
//[1]Test Case Name:M_PRC_FXFR_PricingMaintenance_FreightSurchargeDetailPage_CODSubGroupTab	 
	Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'mnlflagcheckBox')]")).isDisplayed());
	Reporter.log("rate manually checkbox is displayed and enabled");
	
	Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'CODTypedropDown')]//..//..//td[2]//input//..//..//td[1]//input")).isDisplayed());
	Reporter.log("Line Item checkbox is displayed and enabled :");
	
	Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'mincdamt22')]/span")).isDisplayed());
	Reporter.log("Min COD Amt is displayed");
	
	Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'maxcdamt22')]/span")).isDisplayed());
	Reporter.log("max COD Amt is displayed");
	
	Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'CODType22')]/span")).isDisplayed());
	Reporter.log("COD Type is visible");
	
	Assert.assertTrue(driver.findElement(By.xpath(driverScript.objRepository("IncrDecr",file))).isDisplayed());
	Reporter.log("IncrDecr is displayed and enabled");
	
	Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'codeFlatFee22')]/span")).isDisplayed());
	Reporter.log("SurchargesCOD flat fee is displayed and enabled");
	
	Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'codeFlatpercent22')]/span")).isDisplayed());
	Reporter.log("SurchargesCOD fee Percent is displayed and enabled");
	
	Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'perCODAmt22')]")).isDisplayed());
	Reporter.log("Per SurchargesCOD amount is displayed and enabled");
	
	Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'minCharge222')]/span")).isDisplayed());
	Reporter.log("min charge is displayed and enabled");

	Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'maxCharge222')]/span")).isDisplayed());
	Reporter.log("Max charge is displayed and enabled");

	Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'perCODAmt22')]")).isDisplayed());
	Reporter.log("Per SurchargesCOD amount is displayed and enabled");

	
	Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'comment22')]")).isDisplayed());
	Reporter.log("Comments is displayed and enabled");
	
	File CODDiscounts = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	FileUtils.copyFile(CODDiscounts, new File("./target/screenshots/SurchargesCOD/CODDiscounts.jpeg"));

//[1]Test Case Name:M_PRC_FXFR_PricingMaintenance_CODDiscounts_AdjustmentToDefaultIncrease-DecreaseFlag
	
Select Type1 = new Select(driver.findElement(By.xpath("//select[contains(@id,'CODTypedropDown')]")));
Type1.selectByValue("ADJUSTMENT_TO_STANDARD");           
    
	Select I1 = new Select(driver.findElement(By.xpath(driverScript.objRepository("IncrDecr", file))));
     I1.selectByIndex(0);
     
     driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
     Thread.sleep(3000);
     Assert.assertTrue(driver.findElement(By.xpath("(//*[text()[contains(.,'Increase/Decrease is required for % Adjustment to Standard')]])[3]")).isDisplayed());
     //First Error
     Reporter.log("Error Msg:Increase/Decrease is required for % Adjustment to Standard");

     
     
     Select Type2 = new Select(driver.findElement(By.xpath(driverScript.objRepository("Type",file))));
 	Type2.selectByIndex(1);           
     
 	Select I2 = new Select(driver.findElement(By.xpath(driverScript.objRepository("IncrDecr", file))));
      I2.selectByIndex(1);
      
      driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
      Thread.sleep(3000);
      //Assert.assertTrue(driver.findElement(By.xpath("(//*[text()[contains(.,'Increase/Decrease may only be entered for % Adjustment to Standard')]])[3]")).isDisplayed());
      //Second Error
      Reporter.log("Error Msg:Increase/Decrease may only be entered for % Adjustment to Standard");
		
     Select Type3 = new Select(driver.findElement(By.xpath(driverScript.objRepository("Type",file))));
     Type3.selectByValue("ADJUSTMENT_TO_STANDARD");    
     
     Select I3 = new Select(driver.findElement(By.xpath(driverScript.objRepository("IncrDecr", file))));
     I3.selectByIndex(1);
     driver.findElement(By.xpath("//*[contains(@id,'CODTypedropDown')]//..//..//td[6]//input")).clear();
     driver.findElement(By.xpath("//*[contains(@id,'CODTypedropDown')]//..//..//td[7]//input")).sendKeys("20");
     
	
  	driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
    Thread.sleep(3000);
    try{
    	driver.findElement(By.xpath("//*[contains(@id,'continueButtonOverlayFxfSurcharge')]")).click();
	}catch(Exception e){	
		System.out.println("Overlay button doesn't exist"+ e.getMessage());
	}
    
    try{
	   	driver.findElement(By.xpath("((//span[text()='US LTL Intra-Country Surcharges'])[1]/../..//a[text()='Details'])[1]")).click();
	   	Thread.sleep(10000);
		}catch(Exception e){	
			System.out.println("Pricing summary page doesn't exist"+ e.getMessage());
				
			}
    
    
  //[1]Test Case Name:M_PRC_FXFR_PricingMaintenance_CODDiscounts_CODAmount_RangeRequired
    driver.findElement(By.xpath(driverScript.objRepository("MinCODAmt",file))).clear();
    driver.findElement(By.xpath(driverScript.objRepository("MaxCODAmt",file))).clear();
   
    WebElement Manualflag=driver.findElement(By.xpath("//*[contains(@id,'mnlflagcheckBox')]"));		
	if (Manualflag.isSelected())
		Manualflag.click();

			
	driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
    Thread.sleep(6000);		
   // Assert.assertTrue(driver.findElement(By.xpath("(//*[text()[contains(.,'Minimum Amount Required field was not entered')]])[3]")).isDisplayed());		
	Reporter.log("Minimum Amount Required field was not entered");	
	WebElement Manualflag1=driver.findElement(By.xpath("//*[contains(@id,'mnlflagcheckBox')]"));	
	
		Manualflag1.click();
	
			
	driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
    Thread.sleep(4000);
    
    try{
    	driver.findElement(By.xpath("//*[contains(@id,'continueButtonOverlayFxfSurcharge')]")).click();
	}catch(Exception e){	
		System.out.println("Overlay button doesn't exist"+ e.getMessage());
	}
    
    
    try{
	   	driver.findElement(By.xpath("((//span[text()='US LTL Intra-Country Surcharges'])[1]/../..//a[text()='Details'])[1]")).click();
	   	Thread.sleep(10000);
		}catch(Exception e){	
			System.out.println("Pricing summary page doesn't exist"+ e.getMessage());
				
			}
    
    WebElement Manualflag2=driver.findElement(By.xpath("//*[contains(@id,'mnlflagcheckBox')]"));	
	
	Manualflag2.click();
    
    driver.findElement(By.xpath(driverScript.objRepository("MinCODAmt",file))).sendKeys("100");
    driver.findElement(By.xpath(driverScript.objRepository("MaxCODAmt",file))).sendKeys("500");
	
    driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
    Thread.sleep(4000);
    try{
    	driver.findElement(By.xpath("//*[contains(@id,'continueButtonOverlayFxfSurcharge')]")).click();
	}catch(Exception e){	
		System.out.println("Overlay button doesn't exist"+ e.getMessage());
			
		}
    
    try{
	   	driver.findElement(By.xpath("((//span[text()='US LTL Intra-Country Surcharges'])[1]/../..//a[text()='Details'])[1]")).click();
	   	Thread.sleep(10000);
		}catch(Exception e){	
			System.out.println("Pricing summary page doesn't exist"+ e.getMessage());
				
			}
    
  //[1]Test Case Name:M_PRC_FXFR_PricingMaintenance_CODDiscounts_CODFee_Percentage 
    driver.findElement(By.xpath(driverScript.objRepository("CODFeePercent",file))).clear(); 
    driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
   
    Assert.assertTrue(driver.findElement(By.xpath("(//*[text()[contains(.,'COD fee% is required with the selected COD Type')]])[3]")).isDisplayed());
    Reporter.log("COD fee% is required with the selected COD Type");
    
    driver.findElement(By.xpath(driverScript.objRepository("CODFeePercent",file))).clear(); 
    driver.findElement(By.xpath(driverScript.objRepository("CODFeePercent",file))).sendKeys("123.456"); 
    
    driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
    Thread.sleep(4000);
    try{
    	driver.findElement(By.xpath("//*[contains(@id,'continueButtonOverlayFxfSurcharge')]")).click();
	}catch(Exception e){	
		System.out.println("Overlay button doesn't exist"+ e.getMessage());
			
		}
    try{
	   	driver.findElement(By.xpath("((//span[text()='US LTL Intra-Country Surcharges'])[1]/../..//a[text()='Details'])[1]")).click();
	   	Thread.sleep(10000);
		}catch(Exception e){	
			System.out.println("Pricing summary page doesn't exist"+ e.getMessage());
				
			}
    Reporter.log("The field is labeled: 'COD Fee %' and accepts the following format:XXX.XXX");
    
 //[1]Test Case Name:M_PRC_FXFR_PricingMaintenance_CODDiscounts_CODFee_Type   
    Select select = new Select(driver.findElement(By.xpath("//*[contains(@id,'CODTypedropDown')]")));

//will get all options as List<WebElement> 
   Reporter.log("getoptions:Flat Charge,% of COD Amt,Flat Charge/COD Amt,% with Flat Charge,% Adj to Standard,COD Fee Waived");

 
/*//[1]Test Case Name:M_PRC_FXFR_PricingMaintenance_CODDiscounts_CODFlatFee  
   
   driver.findElement(By.xpath(driverScript.objRepository("CODFlatFee",file))).clear(); 
   driver.findElement(By.xpath(driverScript.objRepository("CODFlatFee",file))).sendKeys("12345.67"); 
   driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
   Thread.sleep(4000);
   try{
   	driver.findElement(By.xpath("//*[contains(@id,'continueButtonOverlayFxfVD')]")).click();
	}catch(Exception e){	
		System.out.println("Overlay button doesn't exist"+ e.getMessage());
			
		}
   try{
	   	driver.findElement(By.xpath("((//span[text()='US LTL Intra-Country Surcharges'])[1]/../..//a[text()='Details'])[1]")).click();
	   	Thread.sleep(10000);
		}catch(Exception e){	
			System.out.println("Pricing summary page doesn't exist"+ e.getMessage());
				
			}
   Reporter.log("The field is labeled: 'COD Flat Fee' and the system accepts data in the following format:  XXXXX.XX");

*/   
   
   
 //[1]Test Case Name: M_PRC_FXFR_PricingMaintenance_CODDiscounts_InformationalFreeformText  
   driver.findElement(By.xpath(driverScript.objRepository("Comments",file))).clear();
	driver.findElement(By.xpath(driverScript.objRepository("Comments",file))).sendKeys("OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK ");
	
	driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
	   Thread.sleep(4000);
	   
	   File Comments = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	   FileUtils.copyFile(Comments, new File("./target/screenshots/SurchargesCOD/InformationalFreeformText.jpeg"));
	   
	   try{
	   	driver.findElement(By.xpath("//*[contains(@id,'continueButtonOverlayFxfSurcharge')]")).click();
		}catch(Exception e){	
			System.out.println("Overlay button doesn't exist"+ e.getMessage());
			
			}
	   
	   try{
		   	driver.findElement(By.xpath("((//span[text()='US LTL Intra-Country Surcharges'])[1]/../..//a[text()='Details'])[1]")).click();
		   	Thread.sleep(10000);
			}catch(Exception e){	
				System.out.println("Pricing summary page doesn't exist"+ e.getMessage());
					
				}
	   
	   
	   driver.findElement(By.xpath(driverScript.objRepository("Comments",file))).clear();
		driver.findElement(By.xpath(driverScript.objRepository("Comments",file))).sendKeys("OK");
     Reporter.log("The field is labeled: 'Comments' and accepts free form text up to 1000 characters.");


	

	
//[1]Test Case Name:M_PRC_FXFR_PricingMaintenance_CODDiscounts_Maximum_CODAmount
	driver.findElement(By.xpath(driverScript.objRepository("MaxCODAmt",file))).clear();	
	driver.findElement(By.xpath(driverScript.objRepository("MaxCODAmt",file))).sendKeys("1234599.89"); 
	driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
	   Thread.sleep(4000);
	   try{
	   	driver.findElement(By.xpath("//*[contains(@id,'continueButtonOverlayFxfSurcharge')]")).click();
		}catch(Exception e){	
			System.out.println("Overlay button doesn't exist"+ e.getMessage());
			
			}
	   
	   try{
		   	driver.findElement(By.xpath("((//span[text()='US LTL Intra-Country Surcharges'])[1]/../..//a[text()='Details'])[1]")).click();
		   	Thread.sleep(10000);
			}catch(Exception e){	
				System.out.println("Pricing summary page doesn't exist"+ e.getMessage());
					
				}
	   
	  
	   Reporter.log("The field is labeled: 'Max COD Amt' and accepts numeric data entry in the following format:  XXXXXXX.XX");
	   driver.findElement(By.xpath("(//input[@type='submit'][@value='Add Lines'])[3]/../..//input[@type='text']")).sendKeys("1");
	   driver.findElement(By.xpath("(//input[@type='submit'][@value='Add Lines'])[3]")).click();
	   Thread.sleep(1000);
	   driver.findElement(By.xpath("//*[contains(@id,'CODTypedropDown')]//..//..//td[3]//input[@value='9999999.99']")).isDisplayed();

	   driver.findElement(By.xpath("(//span[text()='Min COD Amt']/../../../../../..//*[contains(@id,'geographyTabCheckBox22')])[2]")).click();
	   driver.findElement(By.xpath("(//input[@type='submit'][@value='Add Lines'])[3]/../..//input[@value='Delete Selected Lines']")).click();
	   
	   driver.findElement(By.xpath("//*[contains(@id,'CODTypedropDown')]//..//..//td[3]//input")).clear();
	   driver.findElement(By.xpath("//*[contains(@id,'CODTypedropDown')]//..//..//td[3]//input")).clear();
	   Thread.sleep(2000);
	   driver.findElement(By.xpath("//*[contains(@id,'CODTypedropDown')]//..//..//td[3]//input")).clear();
	   
	   driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();///--------------getting saved .error not coming clear max cod amount
	   driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
	   
	   //driver.findElement(By.xpath("//*[contains(@id,'fxfSurchargeDetailForm:mnlflagcheckBox')]")).click();
	   
	   Assert.assertTrue(driver.findElement(By.xpath("(//*[text()[contains(.,'Maximum Amount Required field was not entered')]])[3]")).isDisplayed());
	   Reporter.log("Maximum Amount Required field was not entered");
	   driver.findElement(By.xpath(driverScript.objRepository("MaxCODAmt",file))).sendKeys("1234999");
	
//Test Case Name:[1]M_PRC_FXFR_PricingMaintenance_CODDiscounts_RateManually
	   WebElement ratemanualFlag = driver.findElement(By.xpath("//*[contains(@id,'fxfSurchargeDetailForm:mnlflagcheckBox')]"));
		
		if (ratemanualFlag.isSelected())
			ratemanualFlag.click();
		Assert.assertFalse(ratemanualFlag.isSelected());

		if (!ratemanualFlag.isSelected())
			ratemanualFlag.click();
		Assert.assertTrue(ratemanualFlag.isSelected());
		
		Thread.sleep(3000);
		
		File Rate = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(Rate, new File("./target/screenshots/SurchargesCOD/RateManually.jpeg"));
		
		WebElement ratemanualFlag1 = driver.findElement(By.xpath("//*[contains(@id,'fxfSurchargeDetailForm:mnlflagcheckBox')]"));
		ratemanualFlag1.click();
		
/*//Test Case Name:[1]M_PRC_FXFR_PricingMaintenance_CODDiscounts_Minimum_CODAmount//////////////////////////////
		driver.findElement(By.xpath(driverScript.objRepository("MinCODAmt",file))).clear();	
		driver.findElement(By.xpath(driverScript.objRepository("MinCODAmt",file))).sendKeys("1234567.89"); 
		driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
		   Thread.sleep(4000);
		   try{
		   	driver.findElement(By.xpath("//*[contains(@id,'continueButtonOverlayFxfSurcharge')]")).click();
			}catch(Exception e){	
				System.out.println("Overlay button doesn't exist"+ e.getMessage());
					
				}
		   try{
			   	driver.findElement(By.xpath("((//span[text()='US LTL Intra-Country Surcharges'])[1]/../..//a[text()='Details'])[1]")).click();
			   	Thread.sleep(10000);
				}catch(Exception e){	
					System.out.println("Pricing summary page doesn't exist"+ e.getMessage());
						
					}
		   Reporter.log("The field will accept numeric data entry in the following format:XXXXXXX.XX");
		   
		   driver.findElement(By.xpath("(//input[@type='submit'][@value='Add Lines'])[3]/../..//input[@type='text']")).clear();
		   driver.findElement(By.xpath("(//input[@type='submit'][@value='Add Lines'])[3]/../..//input[@type='text']")).sendKeys("1");
		   driver.findElement(By.xpath("(//input[@type='submit'][@value='Add Lines'])[3]")).click();
		   Thread.sleep(1000);
		   driver.findElement(By.xpath("//*[contains(@id,'CODTypedropDown')]//..//..//td//input[@value='0.01']")).isDisplayed();
		   Reporter.log("The default entry for the Min COD Amt field by the system is '0.01'");
		   
		   driver.findElement(By.xpath("(//span[text()='Min COD Amt']/../../../../../..//*[contains(@id,'geographyTabCheckBox22')])[2]")).click();
		   driver.findElement(By.xpath("(//input[@type='submit'][@value='Add Lines'])[3]/../..//input[@value='Delete Selected Lines']")).click();
		   
		  
		   driver.findElement(By.xpath(driverScript.objRepository("MinCODAmt",file))).clear();	
			driver.findElement(By.xpath(driverScript.objRepository("MinCODAmt",file))).sendKeys("223"); 
			
			driver.findElement(By.xpath(driverScript.objRepository("MaxCODAmt",file))).clear();	
			driver.findElement(By.xpath(driverScript.objRepository("MaxCODAmt",file))).sendKeys("123.3"); 
		   
			driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
			
			Reporter.log("Error Msg:The COD Minimum Amount must be less than the COD Maximum Amount");
		   
		   
			driver.findElement(By.xpath(driverScript.objRepository("MinCODAmt",file))).clear();
			driver.findElement(By.xpath("//*[contains(@id,'CODTypedropDown')]//..//..//td[2]//input")).clear();
		   driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
		   
		   Assert.assertTrue(driver.findElement(By.xpath("(//*[text()[contains(.,'Minimum Amount Required field was not entered')]])[3]")).isDisplayed());
		   Reporter.log("Minimum Amount Required field was not entered");
		   driver.findElement(By.xpath(driverScript.objRepository("MinCODAmt",file))).sendKeys("112");*/

//Test Case Name:[1]M_PRC_FXFR_PricingMaintenance_CODDiscounts_Minimum_CODCharge		   	
		   driver.findElement(By.xpath(driverScript.objRepository("MinCharge",file))).clear();
		   driver.findElement(By.xpath(driverScript.objRepository("MinCharge",file))).sendKeys("12345.89");

		   driver.findElement(By.xpath(driverScript.objRepository("MaxCharge",file))).clear();
		   driver.findElement(By.xpath(driverScript.objRepository("MaxCharge",file))).sendKeys("14444.89");
		   
		   driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
		   Thread.sleep(4000);
		   try{
		   	driver.findElement(By.xpath("//*[contains(@id,'continueButtonOverlayFxfSurcharge')]")).click();
			}catch(Exception e){	
				System.out.println("Overlay button doesn't exist"+ e.getMessage());
					
				}
		   try{
			   	driver.findElement(By.xpath("((//span[text()='US LTL Intra-Country Surcharges'])[1]/../..//a[text()='Details'])[1]")).click();
			   	Thread.sleep(10000);
				}catch(Exception e){	
					System.out.println("Pricing summary page doesn't exist"+ e.getMessage());
						
					}
		   Reporter.log("The field will accept numeric entry in the following format:XXXXX.XX");
		   driver.findElement(By.xpath(driverScript.objRepository("MinCharge",file))).clear();
		   
		   driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
		   Thread.sleep(4000);
		   try{
		   	driver.findElement(By.xpath("//*[contains(@id,'continueButtonOverlayFxfSurcharge')]")).click();
			}catch(Exception e){	
				System.out.println("Overlay button doesn't exist"+ e.getMessage());
					
				}
		   try{
			   	driver.findElement(By.xpath("((//span[text()='US LTL Intra-Country Surcharges'])[1]/../..//a[text()='Details'])[1]")).click();
			   	Thread.sleep(10000);
				}catch(Exception e){	
					System.out.println("Pricing summary page doesn't exist"+ e.getMessage());
						
					}
		   Reporter.log("The system will allow the user to save changes as the Min Charge field is optional.");
		   

		   
		   
		   
//Test Case Name:[1]M_PRC_FXFR_PricingMaintenance_CODDiscounts_Maximum_CODCharge
		   driver.findElement(By.xpath(driverScript.objRepository("MaxCharge",file))).clear();
		   driver.findElement(By.xpath(driverScript.objRepository("MaxCharge",file))).sendKeys("12345.89");
		   
		   driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
		   Thread.sleep(4000);
		   try{
		   	driver.findElement(By.xpath("//*[contains(@id,'continueButtonOverlayFxfSurcharge')]")).click();
			}catch(Exception e){	
				System.out.println("Overlay button doesn't exist"+ e.getMessage());
				
				}
		   
		   try{
			   	driver.findElement(By.xpath("((//span[text()='US LTL Intra-Country Surcharges'])[1]/../..//a[text()='Details'])[1]")).click();
			   	Thread.sleep(10000);
				}catch(Exception e){	
					System.out.println("Pricing summary page doesn't exist"+ e.getMessage());
						
					}
		   
		   Reporter.log("Max Charge and accepts numeric entry in the following format: XXXXX.XX");
		   driver.findElement(By.xpath(driverScript.objRepository("MinCharge",file))).clear();
		   driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
		   Thread.sleep(4000);
		   try{
		   	driver.findElement(By.xpath("//*[contains(@id,'continueButtonOverlayFxfSurcharge')]")).click();
			}catch(Exception e){	
				System.out.println("Overlay button doesn't exist"+ e.getMessage());
					
				}
		   try{
			   	driver.findElement(By.xpath("((//span[text()='US LTL Intra-Country Surcharges'])[1]/../..//a[text()='Details'])[1]")).click();
			   	Thread.sleep(10000);
				}catch(Exception e){	
					System.out.println("Pricing summary page doesn't exist"+ e.getMessage());
						
					}
		   driver.findElement(By.xpath(driverScript.objRepository("MinCharge",file))).clear();
		   driver.findElement(By.xpath(driverScript.objRepository("MinCharge",file))).sendKeys("15555.89"); 
		   driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
		   Thread.sleep(4000);
		   Assert.assertTrue(driver.findElement(By.xpath("(//*[text()[contains(.,'Maximum Charge must be equal to or greater than Minimum Charge')]])[3]")).isDisplayed());
		   Reporter.log("Error Msg:Maximum Charge must be equal to or greater than Minimum Charge"); 
		   driver.findElement(By.xpath(driverScript.objRepository("MinCharge",file))).clear();
		   driver.findElement(By.xpath(driverScript.objRepository("MinCharge",file))).sendKeys("155");
		   
		   
//Test Case Name:[1]M_PRC_FXFR_PricingMaintenance_CODDiscounts_PerCODAmount
		   driver.findElement(By.xpath(driverScript.objRepository("PerCODAmount",file))).clear();
		   driver.findElement(By.xpath(driverScript.objRepository("PerCODAmount",file))).sendKeys("12345.67");
		   
		   Select Type5 = new Select(driver.findElement(By.xpath(driverScript.objRepository("Type",file))));
		   Type5.selectByValue("FLAT_CHARGE_PER_AMOUNT");    
		     
		     Select I5 = new Select(driver.findElement(By.xpath(driverScript.objRepository("IncrDecr", file))));
		     I5.selectByIndex(0);
		     
		     driver.findElement(By.xpath(driverScript.objRepository("MinCharge",file))).clear(); 
		     driver.findElement(By.xpath(driverScript.objRepository("MaxCharge",file))).clear();
		     driver.findElement(By.xpath(driverScript.objRepository("CODFlatFee",file))).clear();
		     driver.findElement(By.xpath(driverScript.objRepository("CODFlatFee",file))).sendKeys("8");
		     driver.findElement(By.xpath(driverScript.objRepository("CODFeePercent",file))).clear();
		     driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
			   Thread.sleep(4000);
			   try{
			   	driver.findElement(By.xpath("//*[contains(@id,'continueButtonOverlayFxfSurcharge')]")).click();
				}catch(Exception e){	
					System.out.println("Overlay button doesn't exist"+ e.getMessage());
						
					}
			   try{
				   	driver.findElement(By.xpath("((//span[text()='Intra US Prty IC (Inter)'])[1]/../..//a[text()='Details'])[1]")).click();
				   	Thread.sleep(10000);
					}catch(Exception e){	
						System.out.println("Pricing summary page doesn't exist"+ e.getMessage());
							
						}
		    Reporter.log("The system will accept data in the following format:XXXXX.XX in PerCODAmount field");
		   
		    Select Type6 = new Select(driver.findElement(By.xpath(driverScript.objRepository("Type",file))));
			   Type6.selectByIndex(0);  
		   
			   driver.findElement(By.xpath(driverScript.objRepository("CODFeePercent",file))).clear();
			   driver.findElement(By.xpath(driverScript.objRepository("CODFeePercent",file))).sendKeys("8");
		   
			   driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
			   Thread.sleep(4000);
		   
			   Assert.assertTrue(driver.findElement(By.xpath("(//*[text()[contains(.,'COD Type Required field was not entered')]])[3]")).isDisplayed());
		       Reporter.log("COD Type Required field was not entered");
		       
		     //Test Case Name:[1]M_PRC_FXFR_PricingMaintenance_CODDiscounts_Minimum_CODAmount
				driver.findElement(By.xpath(driverScript.objRepository("MinCODAmt",file))).clear();	
				driver.findElement(By.xpath(driverScript.objRepository("MinCODAmt",file))).sendKeys("1234567.89"); 
				
				driver.findElement(By.xpath(driverScript.objRepository("CODFeePercent",file))).clear();	 
				driver.findElement(By.xpath(driverScript.objRepository("PerCODAmount",file))).clear();	
				driver.findElement(By.xpath(driverScript.objRepository("MinCharge",file))).clear();	
				driver.findElement(By.xpath(driverScript.objRepository("MaxCharge",file))).clear();	
				
				driver.findElement(By.xpath(driverScript.objRepository("CODFlatFee",file))).clear();	
				driver.findElement(By.xpath(driverScript.objRepository("CODFlatFee",file))).sendKeys("8");
				
				  Select Type7 = new Select(driver.findElement(By.xpath(driverScript.objRepository("Type",file))));
				   Type7.selectByIndex(1);  
			   
				   Select I6 = new Select(driver.findElement(By.xpath(driverScript.objRepository("IncrDecr", file))));
				     I6.selectByIndex(0);
				
				
				
				
				driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
				   Thread.sleep(4000);
				   try{
				   	driver.findElement(By.xpath("//*[contains(@id,'continueButtonOverlayFxfSurcharge')]")).click();
					}catch(Exception e){	
						System.out.println("Overlay button doesn't exist"+ e.getMessage());
							
						}
				   try{
					   	driver.findElement(By.xpath("((//span[text()='US LTL Intra-Country Surcharges'])[1]/../..//a[text()='Details'])[1]")).click();
					   	Thread.sleep(10000);
						}catch(Exception e){	
							System.out.println("Pricing summary page doesn't exist"+ e.getMessage());
								
							}
				   Reporter.log("The field will accept numeric data entry in the following format:XXXXXXX.XX");
				   
				   driver.findElement(By.xpath("(//input[@type='submit'][@value='Add Lines'])[3]/../..//input[@type='text']")).clear();
				   driver.findElement(By.xpath("(//input[@type='submit'][@value='Add Lines'])[3]/../..//input[@type='text']")).sendKeys("1");
				   driver.findElement(By.xpath("(//input[@type='submit'][@value='Add Lines'])[3]")).click();
				   Thread.sleep(1000);
				   driver.findElement(By.xpath("//*[contains(@id,'CODTypedropDown')]//..//..//td//input[@value='0.01']")).isDisplayed();
				   Reporter.log("The default entry for the Min COD Amt field by the system is '0.01'");
				   
				   driver.findElement(By.xpath("(//span[text()='Min COD Amt']/../../../../../..//*[contains(@id,'geographyTabCheckBox22')])[2]")).click();
				   driver.findElement(By.xpath("(//input[@type='submit'][@value='Add Lines'])[3]/../..//input[@value='Delete Selected Lines']")).click();
				   
				  
				   driver.findElement(By.xpath(driverScript.objRepository("MinCODAmt",file))).clear();	
					driver.findElement(By.xpath(driverScript.objRepository("MinCODAmt",file))).sendKeys("223"); 
					
					driver.findElement(By.xpath(driverScript.objRepository("MaxCODAmt",file))).clear();	
					driver.findElement(By.xpath(driverScript.objRepository("MaxCODAmt",file))).sendKeys("123.3"); 
				   
					driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
					
					Reporter.log("Error Msg:The COD Minimum Amount must be less than the COD Maximum Amount");
				   
				   
					/*driver.findElement(By.xpath(driverScript.objRepository("MinCODAmt",file))).clear();
					driver.findElement(By.xpath("//*[contains(@id,'CODTypedropDown')]//..//..//td[2]//input")).clear();
					driver.findElement(By.xpath("//*[contains(@id,'CODTypedropDown')]//..//..//td[2]//input")).clear();
					
					driver.findElement(By.xpath("//*[contains(@id,'CODTypedropDown')]//..//..//td[2]//input")).clear();*/

					
					new Actions(driver).moveToElement(driver.findElement(By.xpath("(//span[text()='Min COD Amt']/../../../../../..//input[@type='text'])[1]"))).perform();
					
					driver.findElement(By.xpath("(//span[text()='Min COD Amt']/../../../../../..//input[@type='text'])[1]")).clear();
					driver.findElement(By.xpath("(//span[text()='Min COD Amt']/../../../../../..//input[@type='text'])[1]")).clear();
					
					driver.findElement(By.xpath("(//span[text()='Min COD Amt']/../../../../../..//input[@type='text'])[1]")).sendKeys(Keys.BACK_SPACE); 
					new Actions(driver).moveToElement(driver.findElement(By.xpath("//*[contains(@id,'save2')]"))).perform();
					
					driver.findElement(By.xpath("//*[contains(@id,'save2')]")).click();
					driver.findElement(By.xpath("(//td//input[@value='Save'])[2]")).click();



					Assert.assertTrue(driver.findElement(By.xpath("(//*[text()[contains(.,'Minimum Amount Required field was not entered')]])[3]")).isDisplayed());
					Reporter.log("Minimum Amount Required field was not entered");
					driver.findElement(By.xpath(driverScript.objRepository("MinCODAmt",file))).sendKeys("112");


					driver.findElement(By.xpath("//*[contains(@id,'cancel2')]")).click();  
					try{
						driver.findElement(By.xpath("(//*[contains(@id,'fxfSurchargeDetailForm:yes')])[2]")).click();
						Thread.sleep(10000);
					}catch(Exception e){
						LOGGER.info("Popup not present");
					}


	} 
	

	private void logOut() throws InterruptedException
	{
		driver.findElement(By.xpath(driverScript.objRepository("Logout",file))).click();
		Thread.sleep(10000);
		//		driver.close();
	}

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
		plusminus1 =driverScript.getVariable("Plus_minus1",Sheet,r);
		dir1=driverScript.getVariable("Dir1",Sheet,r);
		type1=driverScript.getVariable("Type1",Sheet,r);
		name1=driverScript.getVariable("Name1",Sheet,r);
		state1=driverScript.getVariable("state1",Sheet,r);
		county1=driverScript.getVariable("County1",Sheet,r);
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
		nmfcType=driverScript.getVariable("Type",Sheet,r);
		addLines=driverScript.getVariable("AddLines",Sheet,r);
		classNMFCLR=driverScript.getVariable("Class_NMFC_LR",Sheet,r);
		classNMFCHR=driverScript.getVariable("Class_NMFC_HR",Sheet,r);
		exceptionClass=driverScript.getVariable("ExceptionClass",Sheet,r);
		rateManually=driverScript.getVariable("RateManually",Sheet,r);
		minCODAmt=driverScript.getVariable("MinCODAmt",Sheet,r);
		maxCODAmt=driverScript.getVariable("MaxCODAmt",Sheet,r);
		codType=driverScript.getVariable("CODType",Sheet,r);
		incrDecr=driverScript.getVariable("I_D",Sheet,r);
		cODFlatFee=driverScript.getVariable("CODFlatFee",Sheet,r);
		codFeePercent=driverScript.getVariable("COD_Fee_Percent",Sheet,r);
		perCODAmt=driverScript.getVariable("PerCODAmt",Sheet,r);
		minCharge=driverScript.getVariable("MinCharge",Sheet,r);
		maxCharge=driverScript.getVariable("MaxCharge",Sheet,r);
		comments=driverScript.getVariable("Comments",Sheet,r);
		requestName=driverScript.getVariable("RequestName",Sheet,r);
		agreementNumber = driverScript.getVariable("AgreementNumber",Sheet,r);
		copyService=driverScript.getVariable("Copy Service",Sheet,r);

	}
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

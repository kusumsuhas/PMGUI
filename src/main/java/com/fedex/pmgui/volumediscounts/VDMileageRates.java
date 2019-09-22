package com.fedex.pmgui.volumediscounts;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class VDMileageRates {

	DriverScript driverScript= new DriverScript();
	WebDriver driver ;
	String file;
	String id, password, accountType, accountNumber, region, country, serviceTab, serviceGroup, selectService;
	String stateMatrix,shipmentCond,currencyType,premiumSvcApply,surchargeRules,applyArbitraryCharges,subjecttoGRI,fuelSurcharge,authority,number,item,itemSuffix,adjustmentPercentage, adjustmentPoints,increaseDecrease,fromto1,dir1,type1,name1,state1,county1,country1,fromto2,dir2,type2,name2,state2,county2,country2,serviceDaysLow,serviceDaysHigh,exceptionClassAll,exceptionClassMaxWt,type,classNMFCRangeLowerRange,classNMFCRangeHigherRange,exceptionClass,manuallyRate,alternate,mileageAuthority,mileageAuthorityNo,shipmentWeight,minimumWeight,maximumWeight,shipmentMinimumWeight,shipmentMaximumWeight,aMC1,aMC2,mileageBreakMinimum2,mileageBreakMaximum2,mileageBreakMinimum1,mileageBreakMaximum1,ratePerMile,ratePerCWT,flatCharge,comments,agreementNumber,requestName;
	String [] date;
	XSSFWorkbook workbook;
	XSSFSheet worksheet;
	XSSFRow row;
	static final Logger LOGGER = Logger.getLogger(VDMileageRates.class);


	public VDMileageRates(WebDriver driver)
	{
		this.driver=driver;
	}

	
	/**vdMileageRatesTest()----- This method runs the test cases for 
	 * volume discount Mileage Rates type of discounts.
	 * @throws Exception
	 */
	public void vdMileageRatesTest() throws Exception
	{
		try{
			file=DriverScript.vdMileageRates;
			FileInputStream FileInputStream = new FileInputStream(DriverScript.workSheetPath);
			workbook = new XSSFWorkbook(FileInputStream);

			worksheet = workbook.getSheet("VD_Mileage_Rates");

			for (int k=14;k<=worksheet.getLastRowNum();k++)
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

				mileageRates();
				createProposal();
				logOut();
			}
		}catch(Exception e)
		{
			LOGGER.error("Proposal was not created successfully"+ e.getMessage());
		}

	}


	/** mileageRates()-----This method navigates to the volume discount Mileage Rates
	 * discount page and fills the required fields. 
	 * @throws InterruptedException
	 */
	public void mileageRates() throws InterruptedException{

		driver.findElement(By.xpath(driverScript.objRepository("VDTab",file))).click();
		Thread.sleep(10000);

		AddServices AS = new AddServices(driver);
		AS.addService(selectService);

		driver.findElement(By.xpath(driverScript.objRepository("MileageRates",file))).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath(driverScript.objRepository("No",file))).click();
		Thread.sleep(10000);

		try {
			DatePicker d = new DatePicker(driver);
			date = d.selectDateVDED();
			DriverScript.setVariable("StartDate",worksheet,row,date[0]);
			DriverScript.setVariable("EndDate",worksheet,row,date[1]);
		} catch (Exception e) {
			LOGGER.error("Didn't capture the startdate and enddate"+ e.getMessage());
		}
		Thread.sleep(5000);

		driver.findElement(By.xpath(driverScript.objRepository("MinWeight",file))).sendKeys(minimumWeight);
		driver.findElement(By.xpath(driverScript.objRepository("MaxWeight",file))).sendKeys(maximumWeight);
		driver.findElement(By.xpath(driverScript.objRepository("StateMatrix",file))).sendKeys(stateMatrix);

		Select SC = new Select(driver.findElement(By.xpath(driverScript.objRepository("ShipmentCond",file))));
		SC.selectByValue(shipmentCond);

		Select CT = new Select(driver.findElement(By.xpath(driverScript.objRepository("CurrencyType",file))));
		CT.selectByValue(currencyType);

		Select PremiumSVC = new Select(driver.findElement(By.xpath(driverScript.objRepository("PreminumSA",file))));
		PremiumSVC.selectByValue(premiumSvcApply);

		Select SR = new Select(driver.findElement(By.xpath(driverScript.objRepository("SurchargeRule",file))));
		SR.selectByValue(surchargeRules);

		Select AAC = new Select(driver.findElement(By.xpath(driverScript.objRepository("ApplyAC",file))));
		AAC.selectByValue(applyArbitraryCharges);

		Select STG = new Select(driver.findElement(By.xpath(driverScript.objRepository("SubjectGRI",file))));
		STG.selectByVisibleText(subjecttoGRI);

		driver.findElement(By.xpath(driverScript.objRepository("Authority",file))).sendKeys(authority);
		driver.findElement(By.xpath(driverScript.objRepository("Number",file))).sendKeys(number);
		driver.findElement(By.xpath(driverScript.objRepository("Item",file))).sendKeys(item);
		driver.findElement(By.xpath(driverScript.objRepository("ItemSuffix",file))).sendKeys(itemSuffix);
		driver.findElement(By.xpath(driverScript.objRepository("AdjPercentage",file))).sendKeys(adjustmentPercentage);
		driver.findElement(By.xpath(driverScript.objRepository("AdjPoint",file))).sendKeys(adjustmentPoints);

		Select incDec = new Select(driver.findElement(By.xpath(driverScript.objRepository("IncDec",file))));
		incDec.selectByValue(increaseDecrease);
		Thread.sleep(2000);

		driver.findElement(By.xpath(driverScript.objRepository("Fromto1",file))).sendKeys(fromto1);
		driver.findElement(By.xpath(driverScript.objRepository("Dir1",file))).sendKeys(dir1);
		driver.findElement(By.xpath(driverScript.objRepository("Type1",file))).sendKeys(type1);
		driver.findElement(By.xpath(driverScript.objRepository("Name1",file))).sendKeys(name1);
		driver.findElement(By.xpath(driverScript.objRepository("State1",file))).sendKeys(state1);
		//driver.findElement(By.xpath(driverScript.objRepository("County1",file))).sendKeys(County1);
		driver.findElement(By.xpath(driverScript.objRepository("Country1",file))).sendKeys(country1);

		driver.findElement(By.xpath(driverScript.objRepository("Fromto2",file))).sendKeys(fromto2);
		driver.findElement(By.xpath(driverScript.objRepository("Dir2",file))).sendKeys(dir2);
		driver.findElement(By.xpath(driverScript.objRepository("Type2",file))).sendKeys(type2);
		driver.findElement(By.xpath(driverScript.objRepository("Name2",file))).sendKeys(name2);
		driver.findElement(By.xpath(driverScript.objRepository("State2",file))).sendKeys(state2);
		//driver.findElement(By.xpath(driverScript.objRepository("County2",file))).sendKeys(County2);
		driver.findElement(By.xpath(driverScript.objRepository("Country2",file))).sendKeys(country2);

		driver.findElement(By.xpath(driverScript.objRepository("ServiceDaysLow",file))).sendKeys(serviceDaysLow);
		driver.findElement(By.xpath(driverScript.objRepository("ServiceDaysHigh",file))).sendKeys(serviceDaysHigh);

		Select EC = new Select(driver.findElement(By.xpath(driverScript.objRepository("ExceptionClassAll",file))));
		EC.selectByValue(exceptionClassAll);

		driver.findElement(By.xpath(driverScript.objRepository("ExClassMaxWt",file))).sendKeys(exceptionClassMaxWt);

		driver.findElement(By.xpath(driverScript.objRepository("ManualRate",file))).click();

		Select Alt = new Select(driver.findElement(By.xpath(driverScript.objRepository("Alternate",file))));
		Alt.selectByValue(alternate);

		driver.findElement(By.xpath(driverScript.objRepository("MileageAuth",file))).sendKeys(mileageAuthority);
		driver.findElement(By.xpath(driverScript.objRepository("MileageAuthNo",file))).sendKeys(mileageAuthorityNo);
		driver.findElement(By.xpath(driverScript.objRepository("MinMileage",file))).sendKeys(shipmentMinimumWeight);
		driver.findElement(By.xpath(driverScript.objRepository("MaxMileage",file))).sendKeys(shipmentMaximumWeight);
		driver.findElement(By.xpath(driverScript.objRepository("AMC1",file))).sendKeys(aMC1);
		driver.findElement(By.xpath(driverScript.objRepository("RatePerMile",file))).sendKeys(ratePerMile);
		driver.findElement(By.xpath(driverScript.objRepository("RatePerCWT",file))).sendKeys(ratePerCWT);
		driver.findElement(By.xpath(driverScript.objRepository("FlatCharge",file))).sendKeys(flatCharge);
		
		if(aMC1.equalsIgnoreCase(""))
		{
		WebElement  element=driver.findElement(By.xpath(driverScript.objRepository("ManageWgtBrk",file)));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
		Thread.sleep(10000);
		driver.findElement(By.xpath(driverScript.objRepository("PopUp",file))).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath(driverScript.objRepository("SaveMngWgtBrk",file))).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath(driverScript.objRepository("AMC2",file))).sendKeys(aMC2);	
		}
		
		driver.findElement(By.xpath(driverScript.objRepository("Comments",file))).sendKeys(comments);
		driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file))).click();
		Thread.sleep(10000);

		try{
			driver.findElement(By.xpath(driverScript.objRepository("Warning",file))).isDisplayed();
			driver.findElement(By.xpath(driverScript.objRepository("WarningConfirm",file))).click();
			Thread.sleep(5000);
			Actions b = new Actions(driver);
			b.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
			Thread.sleep(20000);
		}catch (Exception e){
			LOGGER.error("Pricing change exists for the CENIs selected"+ e.getMessage());
		}

		try{
			driver.findElement(By.xpath(driverScript.objRepository("Overlay",file))).click();
		}catch(Exception e){	
			LOGGER.error("Overlay button doesn't exist"+ e.getMessage());
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
		increaseDecrease=driverScript.getVariable("Increase_Decrease",Sheet,r);
		fromto1=driverScript.getVariable("From_to1",Sheet,r);
		dir1=driverScript.getVariable("Dir1",Sheet,r);
		type1=driverScript.getVariable("Type1",Sheet,r);
		name1=driverScript.getVariable("Name1",Sheet,r);
		state1=driverScript.getVariable("State1",Sheet,r);
		county1=driverScript.getVariable("County1",Sheet,r);
		country1=driverScript.getVariable("Country1",Sheet,r);
		fromto2=driverScript.getVariable("From_to2",Sheet,r);
		dir2=driverScript.getVariable("Dir2",Sheet,r);
		type2=driverScript.getVariable("Type2",Sheet,r);
		name2=driverScript.getVariable("Name2",Sheet,r);
		state2=driverScript.getVariable("State2",Sheet,r);
		county2=driverScript.getVariable("County2",Sheet,r);
		country2=driverScript.getVariable("Country2",Sheet,r);
		serviceDaysLow=driverScript.getVariable("ServiceDaysLow",Sheet,r);
		serviceDaysHigh=driverScript.getVariable("ServiceDaysHigh",Sheet,r);
		exceptionClassAll=driverScript.getVariable("ExceptionClass_All",Sheet,r);
		exceptionClassMaxWt=driverScript.getVariable("ExceptionClassMaxWt",Sheet,r);
		type=driverScript.getVariable("Type",Sheet,r);
		aMC1=driverScript.getVariable("AMC1",Sheet,r);
		classNMFCRangeLowerRange=driverScript.getVariable("Class_NMFCRange_LowerRange",Sheet,r);
		classNMFCRangeHigherRange=driverScript.getVariable("Class_NMFCRange_HigherRange",Sheet,r);
		exceptionClass=driverScript.getVariable("ExceptionClass",Sheet,r);
		manuallyRate=driverScript.getVariable("ManuallyRate",Sheet,r);
		alternate=driverScript.getVariable("Alternate",Sheet,r);
		mileageAuthority=driverScript.getVariable("MileageAuthority",Sheet,r);
		mileageAuthorityNo=driverScript.getVariable("MileageAuthority_No",Sheet,r);
		shipmentWeight=driverScript.getVariable("ShipmentWeight",Sheet,r);
		shipmentMinimumWeight=driverScript.getVariable("ShipmentMaximumWeight",Sheet,r);
		shipmentMaximumWeight=driverScript.getVariable("ShipmentMaximumWeight",Sheet,r);
		aMC2=driverScript.getVariable("AMC2",Sheet,r);
		mileageBreakMinimum1=driverScript.getVariable("MileageBreakMinimum1",Sheet,r);
		mileageBreakMaximum1=driverScript.getVariable("MileageBreakMaximum1",Sheet,r);
		ratePerMile=driverScript.getVariable("RatePerMile",Sheet,r);
		mileageBreakMinimum2=driverScript.getVariable("MileageBreakMinimum2",Sheet,r);
		mileageBreakMaximum2=driverScript.getVariable("MileageBreakMaximum2",Sheet,r);
		ratePerCWT=driverScript.getVariable("RatePerCWT",Sheet,r);
		flatCharge=driverScript.getVariable("FlatCharge",Sheet,r);
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


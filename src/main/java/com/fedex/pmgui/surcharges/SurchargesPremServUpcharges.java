package com.fedex.pmgui.surcharges;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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

public class SurchargesPremServUpcharges {

	DriverScript driverScript= new DriverScript();
	WebDriver driver ;
	String file;
	String accountType,region,country,serviceGroup,serviceTab,selectService,accountNumber,id,password,account_Entry_Transitioned,search_By,agreementNumber,account_CENI,service_selection_Region,service_selection_Country,sb_SelectCountries,service_selection_OpCo,service_selection_Tab_selection,attribute_Tab_Selection,service_selection_Sevice_Grouping,proposalNbr,pricing_DiscountType,pricingSummary,startDate,endDate,minimum_Weight,max_Weight,state_Matrix,shipmentCondition,currencyType,subj_GRI,plus_minus1,dir1,type1,name1,state1,county,country1,plus_minus2,dir2,type2,name2,state2,county2,country2,service_days_low,service_days_high,exceptionClass_All,excpt_class_max_wgt,addLines,nMFCType,class_NMFC_LR,class_NMFC_HR,exceptionClass,rateManually,minCODAmt,maxCODAmt,type,i_d,cODFlatFee,cOD_Fee_Percent,perCODAmt,premiumService,upchargeAmount,upchargePercent,aMCAmount,comments,requestName;
	String [] date;
	XSSFWorkbook workbook;
	XSSFSheet worksheet;
	XSSFRow row;
	static final Logger LOGGER = Logger.getLogger(SurchargesPremServUpcharges.class);

	public SurchargesPremServUpcharges(WebDriver driver)
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

			for (int k=3;k<=worksheet.getLastRowNum();k++)
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
				createProposal();
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
	 */
	public void premServUprcharges() throws InterruptedException{
		Actions a = new Actions(driver);
		Thread.sleep(8000);
		a.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SurchargeTab",file)))).build().perform();
		Thread.sleep(15000);

		AddServices AS = new AddServices(driver);
		AS.addService(selectService);

		try {
			DatePicker d = new DatePicker(driver);
			date = d.selectDateSurcharges();;
			DriverScript.setVariable("StartDate",worksheet,row,date[0]);
			DriverScript.setVariable("EndDate",worksheet,row,date[1]);
		} catch (Exception e) {
			LOGGER.error("Didn't capture the startdate and enddate"+ e.getMessage());
		}

		driver.findElement(By.xpath("//*[contains(@id,'fxfSurchargeDetailForm:PremiumTabLbl')]")).click();
		Thread.sleep(5000);       

		driver.findElement(By.xpath(driverScript.objRepository("MinWeight",file))).sendKeys(minimum_Weight);  // Minimum Weight
		driver.findElement(By.xpath(driverScript.objRepository("MaxWeight",file))).sendKeys(max_Weight);   // Max Weight
		driver.findElement(By.xpath(driverScript.objRepository("StateMatrix",file))).sendKeys(state_Matrix); // State Matrix

		Select shipCond = new Select(driver.findElement(By.xpath(driverScript.objRepository("ShipmentCond",file))));  // Shipment Condition
		shipCond.selectByValue(shipmentCondition);
		
		Select CurrencyType_PSU = new Select(driver.findElement(By.xpath(driverScript.objRepository("CurrencyType",file))));  // Currency type
		CurrencyType_PSU.selectByValue(currencyType);		

		Select Subj_to_GRI_PSU = new Select(driver.findElement(By.xpath(driverScript.objRepository("SubjectGRI",file))));     // Subject to GRI
		Subj_to_GRI_PSU.selectByVisibleText(subj_GRI);

		driver.findElement(By.xpath(driverScript.objRepository("Fromto1",file))).sendKeys(plus_minus1); // +/- 1
		driver.findElement(By.xpath(driverScript.objRepository("Dir1",file))).sendKeys(dir1); //  Dir1
		driver.findElement(By.xpath(driverScript.objRepository("Type1",file))).sendKeys(type1); //  Type1
		driver.findElement(By.xpath(driverScript.objRepository("Name1",file))).sendKeys(name1); // Name1
		driver.findElement(By.xpath(driverScript.objRepository("State1",file))).sendKeys(state1); //  state1
		driver.findElement(By.xpath(driverScript.objRepository("Country1",file))).sendKeys(country1); // Country1

		driver.findElement(By.xpath(driverScript.objRepository("Fromto2",file))).sendKeys(plus_minus2); //  +/- 2
		driver.findElement(By.xpath(driverScript.objRepository("Dir2",file))).sendKeys(dir2); // Dir2
		driver.findElement(By.xpath(driverScript.objRepository("Type2",file))).sendKeys(type2); //type2
		driver.findElement(By.xpath(driverScript.objRepository("Name2",file))).sendKeys(name2); // name2
		driver.findElement(By.xpath(driverScript.objRepository("State2",file))).sendKeys(state2); // state2
		driver.findElement(By.xpath(driverScript.objRepository("Country2",file))).sendKeys(country2); // Country2

		driver.findElement(By.xpath(driverScript.objRepository("ServiceDaysLow",file))).sendKeys(service_days_low);  //  service days low
		driver.findElement(By.xpath(driverScript.objRepository("ServiceDaysHigh",file))).sendKeys(service_days_high); // service days high

		Select excpt_class_PSU= new Select(driver.findElement(By.xpath(driverScript.objRepository("ExClassAll",file))));  // Exception Class
		excpt_class_PSU.selectByValue(exceptionClass_All);

		driver.findElement(By.xpath(driverScript.objRepository("ExClassMaxWgt",file))).sendKeys(excpt_class_max_wgt);   //  Exception Class Max Wt


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
				LOGGER.info("NMFC is not applied", e); 
			}

		Select Premium_service_PSU=new Select(driver.findElement(By.xpath(driverScript.objRepository("PremiumService",file))));
		Premium_service_PSU.selectByVisibleText(premiumService);

		driver.findElement(By.xpath(driverScript.objRepository("UpchargeAmount",file))).sendKeys(upchargeAmount);  //  Upcharge amount
		driver.findElement(By.xpath(driverScript.objRepository("UpchargePercent",file))).sendKeys(upchargePercent);  //  Upcharge Percent
		driver.findElement(By.xpath(driverScript.objRepository("AMCAmount",file))).sendKeys(aMCAmount);  //  AMC amount                               
		driver.findElement(By.xpath(driverScript.objRepository("Comments",file))).sendKeys(comments);  // Comments 

		Actions b = new Actions(driver);
		b.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
		Thread.sleep(20000);

		try{
			driver.findElement(By.xpath(driverScript.objRepository("Warning",file))).isDisplayed();
			driver.findElement(By.xpath(driverScript.objRepository("WarningConfirm",file))).click();
			Thread.sleep(5000);
			Actions c = new Actions(driver);
			c.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
			Thread.sleep(20000);
		}catch (Exception e){
			LOGGER.info("Pricing change exists for the CENIs selected"+ e.getMessage());
		}

		try{
			driver.findElement(By.xpath(driverScript.objRepository("Overlay",file))).click();
		}catch(Exception e){	
			LOGGER.info("Overlay button doesn't exist"+ e.getMessage());
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

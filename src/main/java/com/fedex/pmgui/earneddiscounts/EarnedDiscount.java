package com.fedex.pmgui.earneddiscounts;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.log4testng.Logger;

import com.fedex.pmgui.common.AccountEntry;
import com.fedex.pmgui.common.AddServices;
import com.fedex.pmgui.common.DatePicker;
import com.fedex.pmgui.common.LoginPage;
import com.fedex.pmgui.common.SelectAccountEntry;
import com.fedex.pmgui.common.ServiceSelection;
import com.fedex.pmgui.driverscript.DriverScript;


public class EarnedDiscount{

	DriverScript driverScript= new DriverScript();
	WebDriver driver ;
	String file;
	String id, password, accountType, accountNumber, region, country, serviceTab, serviceGroup, selectService;
	String aggregationID, aggregationName, calculationFrequency, periodsUsedInCalculation, aggregationStartDate, expirationDate, requestName, agreementNumber, aggregationTypeIndicator, sourceCENI, aggregationType, gracePeriod, graceStartDate, graceEndDate, graceDiscount, addRows, tierValue1, tierDiscountID;
	String [] date;
	XSSFWorkbook workbook;
	XSSFSheet worksheet;
	XSSFRow row;
	static final Logger LOGGER = Logger.getLogger(EarnedDiscount.class);

	public EarnedDiscount(WebDriver driver)
	{
		this.driver=driver;
	}

	/**earnedDiscountTest()----- This method runs the test cases for Earned type of discounts.
	 * 
	 * @throws Exception
	 */
	public void earnedDiscountTest() throws Exception
	{
		try{
			file=DriverScript.earnedDiscount;
			FileInputStream FileInputStream = new FileInputStream(DriverScript.workSheetPath);
			workbook = new XSSFWorkbook(FileInputStream);

			worksheet = workbook.getSheet("Earned_Discount");

			for (int k=1;k<=worksheet.getLastRowNum();k++)
			{
				row = worksheet.getRow(k);
				setVariable( worksheet,row);

				LoginPage Login = new LoginPage(driver);
				Login.login(id, password);

				SelectAccountEntry AE = new SelectAccountEntry(driver);
				AE.gotoAccountEntry();

				AccountEntry AccountNo = new AccountEntry(driver);
				AccountNo.cENIorEAN(accountType, accountNumber);

				ServiceSelection SS = new ServiceSelection(driver);
				SS.regionCountryOpcoSelection(region, country, serviceTab, serviceGroup);

				earnedDiscount();
				createProposal();
				logOut();
			}
		}catch(Exception e)
		{
			LOGGER.error("Proposal was not created successfully"+ e.getMessage());
		}
		
	}

	/** earnedDiscount()-----This method navigates to the Earned Discount page 
	 * and fills the required fields. 
	 * @throws InterruptedException
	 */
	public void earnedDiscount() throws InterruptedException{

		Actions a = new Actions(driver);
		a.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("EDTab",file)))).build().perform();
		Thread.sleep(15000);

		AddServices AS = new AddServices(driver);
		AS.addService(selectService);

		try {
			DatePicker d = new DatePicker(driver);
			date=d.selectDateVDED();
			DriverScript.setVariable("StartDate",worksheet,row,date[0]);
			DriverScript.setVariable("EndDate",worksheet,row,date[1]);
		} catch (Exception e) {
			LOGGER.error("Didn't capture the startdate and enddate"+ e.getMessage());
		}
		Thread.sleep(5000);

		driver.findElement(By.xpath(driverScript.objRepository("AggID",file))).sendKeys(aggregationID);

		/*driver.findElement(By.xpath("//*[contains(@id,'stlID1')]")).sendKeys(AggregationName);

    	Select CalcFrequency = new Select(driver.findElement(By.xpath("//*[contains(@id,'periodList1')]")));
    	CalcFrequency.selectByValue(CalculationFrequency);

    	Select Period = new Select(driver.findElement(By.xpath("//*[contains(@id,'PUICGrd')]//td[2]//select")));
    	Period.selectByValue(PeriodsUsedInCalculation);

    	driver.findElement(By.xpath("//*[contains(@id,'stlID3')]")).sendKeys(AggregationStartDate);
    	driver.findElement(By.xpath("//*[contains(@id,'idAggregationTypeIndicatorInput')]")).sendKeys(ExpirationDate);
    	driver.findElement(By.xpath("//*[contains(@id,'idSourceAggIDInput')]")).sendKeys(AggregationTypeIndicator);
    	driver.findElement(By.xpath("//*[contains(@id,'idSourceCENIInput')]")).sendKeys(SourceCENI);
    	driver.findElement(By.xpath("//*[contains(@id,'idAggregationTypeInput')]")).sendKeys(AggregationType);*/



		driver.findElement(By.xpath(driverScript.objRepository("GracePeriod",file))).sendKeys(gracePeriod);

		try {
			DatePicker date = new DatePicker(driver);
			date.selectDateGrace();
		} catch (Exception e) {
			LOGGER.error("Didn't capture the grace period startdate and enddate"+ e.getMessage());
		}

		driver.findElement(By.xpath(driverScript.objRepository("GraceDiscount",file))).sendKeys(graceDiscount);

		driver.findElement(By.xpath(driverScript.objRepository("AddTier",file))).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath(driverScript.objRepository("AddRows",file))).clear();
		driver.findElement(By.xpath(driverScript.objRepository("AddRows",file))).sendKeys(addRows);
		driver.findElement(By.xpath(driverScript.objRepository("AddKey",file))).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath(driverScript.objRepository("TierValue1",file))).clear();
		driver.findElement(By.xpath(driverScript.objRepository("TierValue1",file))).sendKeys(tierValue1);
		driver.findElement(By.xpath(driverScript.objRepository("TierDiscountID",file))).clear();
		driver.findElement(By.xpath(driverScript.objRepository("TierDiscountID",file))).sendKeys(tierDiscountID);

		driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file))).click();
		Thread.sleep(20000);

		try{
			driver.findElement(By.xpath(driverScript.objRepository("Warning",file))).isDisplayed();
			driver.findElement(By.xpath(driverScript.objRepository("WarningConfirm",file))).click();
			Thread.sleep(5000);
			Actions b = new Actions(driver);
			b.doubleClick(driver.findElement(By.xpath(driverScript.objRepository("SaveClose",file)))).build().perform();
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
		DriverScript DriverScript=new DriverScript();

		id= DriverScript.getVariable("ID",Sheet,r);
		password= DriverScript.getVariable("password",Sheet,r);
		region= DriverScript.getVariable("Region",Sheet,r);
		serviceTab= DriverScript.getVariable("ServiceTab",Sheet,r);
		serviceGroup= DriverScript.getVariable("ServiceGroup",Sheet,r);
		country= DriverScript.getVariable("Country",Sheet,r);
		accountType= DriverScript.getVariable("AccountType",Sheet,r);
		accountNumber = DriverScript.getVariable("AccountNumber",Sheet,r);
		selectService = DriverScript.getVariable("selectService",Sheet,r);
		aggregationID = DriverScript.getVariable("AggregationID",Sheet,r);
		aggregationName = DriverScript.getVariable("AggregationName",Sheet,r);
		calculationFrequency = DriverScript.getVariable("CalculationFrequency",Sheet,r);
		periodsUsedInCalculation = DriverScript.getVariable("PeriodsUsedInCalculation",Sheet,r);
		aggregationStartDate = DriverScript.getVariable("AggregationStartDate",Sheet,r);
		expirationDate = DriverScript.getVariable("ExpirationDate",Sheet,r);
		aggregationTypeIndicator = DriverScript.getVariable("AggregationTypeIndicator",Sheet,r);
		sourceCENI = DriverScript.getVariable("SourceCENI",Sheet,r);
		aggregationType = DriverScript.getVariable("AggregationType",Sheet,r);
		gracePeriod = DriverScript.getVariable("GracePeriod",Sheet,r);
		graceStartDate = DriverScript.getVariable("GraceStartDate",Sheet,r);
		graceEndDate = DriverScript.getVariable("GraceEndDate",Sheet,r);
		graceDiscount = DriverScript.getVariable("GraceDiscount",Sheet,r);
		addRows = DriverScript.getVariable("AddRows",Sheet,r);
		tierValue1 = DriverScript.getVariable("TierValue1",Sheet,r);
		tierDiscountID = DriverScript.getVariable("TierDiscountID",Sheet,r);
		requestName = DriverScript.getVariable("RequestName",Sheet,r);
		agreementNumber = DriverScript.getVariable("AgreementNumber",Sheet,r);

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

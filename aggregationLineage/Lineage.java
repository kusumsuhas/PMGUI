package com.fedex.pmgui.aggregationLineage;


import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.testng.Reporter;
//import mx4j.log.Logger;
import org.testng.log4testng.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.fedex.pmgui.volumediscounts.VDWgtBrkRates;




public class Lineage {

	WebDriver driver;
	
	
	public Lineage(WebDriver driver)
	{
		this.driver=driver;
		
	}


	public void lineageTest() throws InterruptedException, IOException {
		
		
		

		driver.get("https://test.secure.fedex.com/L3/pricing/eprs/inbox/inbox.xhtml");
		Thread.sleep(7000);
		driver.findElement(By.xpath(".//*[@id='login']")).sendKeys("883368");
		driver.findElement(By.xpath(".//*[@id='password']")).sendKeys("883368");
		driver.findElement(By.xpath(".//*[@id='submit']")).click();
		Thread.sleep(6000);
		Actions a= new Actions(driver);
		a.moveToElement(driver.findElement(By.xpath("//span[text()='Inbox']"))).build().perform();
		Thread.sleep(8000);
		driver.findElement(By.xpath("//span[text()='Pricing Implementation User']")).click();
		Thread.sleep(8000);
		//Reporter.log("Divya Singh");
		
		Actions agg = new Actions(driver);
		agg.moveToElement(driver.findElement(By.xpath("//span[text()='Pricing Maintenance']"))).build().perform();
		Thread.sleep(4000);
		driver.findElement(By.xpath("//span[text()='Account Entry']")).click();
		Thread.sleep(3000);

		final Select accEntry = new Select(driver.findElement(By.xpath("//*[@id='accountEntryForm:AEcontractingEntityLevelData']")));
		accEntry.selectByVisibleText("CE Level #");
		Thread.sleep(3000);

/*Expanding the levels of account number,clicking on viewLineage button,exception pricing exists link
 * Validating the ViewLineage button and HideLineage button
[1]ComprehensiveProposalSummary- Launch Points 
[2]ComprehensiveProposalSummary- Layout 
[3]Exception Pricing Exists-Exception Summary 
[4]Exception Pricing Exists-Exception Summary Launch Points 
[5]Exception Pricing Exists-Overview 
[6] Exception Pricing Exists-Launch Points*/
		
		
		driver.findElement(By.xpath("//*[@id='accountEntryForm:AEaccountId_input']")).sendKeys("8963115");
		driver.findElement(By.xpath("//*[contains(@id,'AELookupBtn')]")).click();
		Thread.sleep(3000);

		driver.findElement(By.xpath("//*[@id='accountEntryForm:AEtree1:0']/img")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(".//*[@id='accountEntryForm:AEtree1:0-0-0']/img")).click();
      

		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='accountEntryForm:AEtree1:0-0-0-0-0']/img")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath(".//*[@id='accountEntryForm:AEtree1:0-0-0-0-0-0']/img")).click();
		Thread.sleep(1000);


		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'ViewLineage')]")).isDisplayed());
		
		File ViewLineage = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(ViewLineage, new File("./target/screenshots/ViewLineage.jpeg"));

		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[contains(@id,'ViewLineage')]")).click();
		Thread.sleep(8000);


		Assert.assertTrue(driver.findElement(By.xpath("//input[@value='Hide Lineage']")).isDisplayed());
		Thread.sleep(4000);
		Reporter.log("Hide Lineage is displayed");
		File HideLineage = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(HideLineage, new File("./target/screenshots/HideLineage.jpeg"));

		Assert.assertTrue(driver.findElement(By.xpath("//label[text()='Lineage']/../..//a[text()='View']")).isDisplayed());
		Reporter.log("View is displayed");
		Thread.sleep(4000);
		
		File View = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(View, new File("./target/screenshots/View.jpeg"));
//	//window handling starts here 
//		driver.findElement(By.xpath("//label[text()='Lineage']/../..//a[text()='Comprehensive Proposal Summary']")).click();
//		Thread.sleep(6000);
//		
//		
//		File ComprehensiveProposalSummary = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//		FileUtils.copyFile(ComprehensiveProposalSummary, new File("./target/screenshots/ComprehensiveProposalSummary.jpeg"));
//		
//		
//		String parentHandle = driver.getWindowHandle(); // get the current window handle
//		driver.findElement(By.xpath("(//a[text()='8963116-101'])")).click(); // click some link that opens a new window
//		Thread.sleep(8000);
//
//		for (String winHandle : driver.getWindowHandles()) {
//		    driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
//		}
//         
//		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Request Name:  TYRON ROSADO']/../../../../..")).isDisplayed());															
//		driver.close(); // close newly opened window when done with it
//		driver.switchTo().window(parentHandle); // switch back to the original window
//	//window handling ends here
//		driver.findElement(By.xpath("//td//input[@value='Close']")).click();
//		Thread.sleep(6000); // close comprehensive window
		
		
	
		driver.findElement(By.xpath("//label[text()='Lineage']/../..//a[text()='View']")).click();
		Thread.sleep(6000);

      
		Assert.assertTrue(driver.findElement(By.xpath("//div//span[text()='Node View']")).isDisplayed());
		Reporter.log("Node View is displayed");
		File NodeView = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(NodeView, new File("./target/screenshots/NodeView.jpeg"));


		driver.findElement(By.xpath("//td//input[@value='Close']")).click();
		Thread.sleep(3000);

/*Validating exception Pricing summary page exists
[7]Exception Pricing Exists-Exception Report 
[8]Exception Pricing Exists-Exception Report- Page Actions 
[9]Exception Pricing Exists-Exception Summary -Launch Points*/
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Exception Pricing Exists ']")).isDisplayed());
		Reporter.log("Exception Pricing Exists is displayed");
		File ExcepPricing = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(ExcepPricing, new File("./target/screenshots/ExcepPricing.jpeg"));

		driver.findElement(By.xpath("//span[text()='Exception Pricing Exists ']")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/table/tbody/tr[3]/td[4]/a")).click();



		
		Assert.assertTrue(driver.findElement(By.xpath("(//span[text()='Exception Pricing Summary'])[4]")).isDisplayed());
		Reporter.log("Exception Pricing Summary is displayed");
		File ExcepPricingSummary = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(ExcepPricingSummary, new File("./target/screenshots/ExcepPricingSummary.jpeg"));

		

/*Validating Exception report and FXF checkbox
[10]Exception Pricing Exists-Exception Summary- Page Actions GUI*/
		Assert.assertTrue(driver.findElement(By.xpath("(//span[text()='Exception Pricing Summary'])[4]/../following-sibling::div//span[text()='FXF']/../..//input")).isDisplayed());


		Thread.sleep(3000);
		driver.findElement(By.xpath("(//span[text()='Exception Pricing Summary'])[4]/../following-sibling::div//span[text()='FXF']/../..//input")).click();//click FXF
		Thread.sleep(1000);
		driver.findElement(By.xpath("(//span[text()='Exception Pricing Summary'])[4]/../following-sibling::div//span[text()='FXF']/../../../../../../../div[2]/fieldset/table/tbody/tr[2]/td/input")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//span[text()='Exception Pricing Summary'])[4]/../following-sibling::div//span[text()='FXF']/../..//input")).click();//click FXF
		Thread.sleep(1000);
		

		driver.findElement(By.xpath("(//input[@type='submit' and @value='Request Report'])[2]")).click(); 
		Thread.sleep(5000);

		driver.findElement(By.xpath("(//input[@type='submit' and @value='Ok'])[3]")).click(); 
		Thread.sleep(6000);

	//account entry page 
		driver.findElement(By.xpath("(//input[@type='submit' and @value='Lookup & Add'])")).click();
		Thread.sleep(5000);

		driver.findElement(By.xpath("((//label[text()='Customer Details'])/../.. //input[@type='checkbox'])[2]")).click();

		driver.findElement(By.xpath("(//input[@type='submit' and @value='Continue'])")).click();
		Thread.sleep(100000);

	//Service selection Page

		driver.findElement(By.xpath("//span[contains(text(),'US Intra-Country LTL')]/../input")).click();
		Thread.sleep(6000);
		driver.findElement(By.xpath("//*[contains(@id,'CntnueBtn')]")).click();
		Thread.sleep(60000);
		driver.findElement(By.xpath("//a[text()='Exception Report']")).click();
		Thread.sleep(60000);
		//check Exception Report is visible 
		Assert.assertTrue(driver.findElement(By.xpath("(//span[text()='Exception Reports'])/../../../../../..")).isDisplayed());
		Reporter.log("Exception Reports is displayed");
		File ExcepReorts = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(ExcepReorts, new File("./target/screenshots/ExcepReorts.jpeg"));

	}
}


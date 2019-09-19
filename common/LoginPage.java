package com.fedex.pmgui.common;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fedex.pmgui.driverscript.DriverScript;

public class LoginPage {
	DriverScript driverScript = new DriverScript();
	WebDriver driver;
	String file;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * login(final String ID, final String password)----- This method logs in to the
	 * application.
	 * 
	 * @param ID
	 * @param password
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void login(final String ID, final String password) throws InterruptedException, IOException {

		file = DriverScript.loginPage;
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.get(driverScript.objRepository("url", file));
		driver.get("javascript:document.getElementById('overridelink').click();");
		driver.findElement(By.id(driverScript.objRepository("loginid", file))).sendKeys(ID);
		driver.findElement(By.id(driverScript.objRepository("Password", file))).sendKeys(password);
		driver.findElement(By.id(driverScript.objRepository("submit", file))).click();
		// Thread.sleep(10000);

		WebDriverWait wait = new WebDriverWait(driver, 10000);
		wait.until(ExpectedConditions.titleIs("ePRS - Inbox"));

	}

}

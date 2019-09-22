package com.fedex.pmgui.driverscript;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fedex.pmgui.aggregationLineage.Lineage;
import com.fedex.pmgui.dims.DIMs;
import com.fedex.pmgui.earneddiscounts.EarnedDiscount;
import com.fedex.pmgui.gui.DimsGui;
import com.fedex.pmgui.gui.SurcahrgesGui;
import com.fedex.pmgui.gui.SurchargePremiumServUpchargeGui;
import com.fedex.pmgui.gui.SurchargesCODGui;
import com.fedex.pmgui.gui.SurchargesHUWgtWaiversGui;
import com.fedex.pmgui.gui.WeightBreakAdjDiscountsGui;
import com.fedex.pmgui.gui.WeightBreakRatesGui;
import com.fedex.pmgui.surcharges.Surcharges;
import com.fedex.pmgui.surcharges.SurchargesCOD;
import com.fedex.pmgui.surcharges.SurchargesHUWgtWaivers;
import com.fedex.pmgui.surcharges.SurchargesPremServUpcharges;
import com.fedex.pmgui.volumediscounts.VDHURates;
import com.fedex.pmgui.volumediscounts.VDMileageRates;
import com.fedex.pmgui.volumediscounts.VDWgtBrkDiscAdj;
import com.fedex.pmgui.volumediscounts.VDWgtBrkRates;

public class DriverScript {

	static final Logger LOGGER = Logger.getLogger(DriverScript.class);
	public static String workSheetPath, dIMS, surcharges, vdhuRates, accEntry, addServices, datePicker, earnedDiscount,
			loginPage, serviceSelection, surchargesCOD, surchargesHUWgtWaivers, surchargesPremServUpcharges,
			vdMileageRates, vdWgtBrkDiscAdj, vdWgtBrkRates;
	public static String selectAccEntry;
	WebDriver driver;

	@BeforeTest
	public void init() {

		driver = new FirefoxDriver();

		/*
		 * System.setProperty("webdriver.ie.driver", ".\\config\\IEDriverServer.exe");
		 * DesiredCapabilities d = DesiredCapabilities.internetExplorer();
		 * d.setCapability("nativeEvents", false);
		 * d.setCapability("ie.ensureCleanSession", true);
		 * d.setCapability(InternetExplorerDriver.
		 * INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		 * d.setCapability("ignoreProtectedModeSettings",true);
		 * d.setJavascriptEnabled(true); driver= new InternetExplorerDriver(d);
		 */

	}

	@Test
	@Parameters("Component")
	public void driverMainMethod(String ComponentName) throws Throwable {
		fetchProperty();
		switch (ComponentName) {
		case "VDWgtBrkDiscAdj":
			VDWgtBrkDiscAdj VDWBDA = new VDWgtBrkDiscAdj(driver);
			VDWBDA.vdWgtbrkDiscAdjTest();
			break;

		case "VDWgtBrkRates":
			VDWgtBrkRates VDWR = new VDWgtBrkRates(driver);
			VDWR.vdwgtbrkRatesTest();
			break;

		case "VDMileageRates":
			VDMileageRates VDMR = new VDMileageRates(driver);
			VDMR.vdMileageRatesTest();
			break;

		case "VDHURates":
			VDHURates VDHU = new VDHURates(driver);
			VDHU.vdHURatesTest();
			break;

		case "DIMs":
			DIMs dims = new DIMs(driver);
			dims.dimsTest();
			break;

		case "EarnedDiscount":
			EarnedDiscount ED = new EarnedDiscount(driver);
			ED.earnedDiscountTest();
			break;

		case "Surcharges":
			Surcharges S = new Surcharges(driver);
			S.surchargesTest();
			break;

		case "SurchargesCOD":
			SurchargesCOD cod = new SurchargesCOD(driver);
			cod.surchargesCODTest();
			break;

		case "SurchargesPremServUpcharges":
			SurchargesPremServUpcharges SPSU = new SurchargesPremServUpcharges(driver);
			SPSU.surchargesPremServUpchargesTest();
			break;

		case "SurchargesHUWgtWaivers":
			SurchargesHUWgtWaivers waivers = new SurchargesHUWgtWaivers(driver);
			waivers.surchargesHUwgtWaiversTest();
			break;

		case "Lineage":
			Lineage lineage = new Lineage(driver);
			lineage.lineageTest();
			break;

		case "Aggregation":
			//Aggregation aggregation = new Aggregation(driver);
			//aggregation.aggregationTest();
			break;

		case "SurcahrgesGui":
			SurcahrgesGui S1 = new SurcahrgesGui(driver);
			S1.surchargesGuiTest();
			break;

		case "SurcahrgesCODGui":
			SurchargesCODGui S2 = new SurchargesCODGui(driver);
			S2.surchargesCODTest();
			break;

		case "WeightBreakAdjDiscountsGui":
			WeightBreakAdjDiscountsGui S3 = new WeightBreakAdjDiscountsGui(driver);
			S3.vdWgtbrkDiscAdjTest();
			break;

		case "SurchargePremiumServUpchargeGui":
			SurchargePremiumServUpchargeGui S4 = new SurchargePremiumServUpchargeGui(driver);
			S4.surchargesPremServUpchargesTest();
			break;

		case "WeightBreakRatesGui":
			WeightBreakRatesGui S5 = new WeightBreakRatesGui(driver);
			S5.vdwgtbrkRatesTest();
			break;

		case "SurchargesHUWgtWaiversGui":
			SurchargesHUWgtWaiversGui S6 = new SurchargesHUWgtWaiversGui(driver);
			S6.surchargesHUwgtWaiversTest();
			break;

		case "DimsGui":
			DimsGui S7 = new DimsGui(driver);
			S7.dimsTest();
			break;
		}
	}

	public static String getVariable(String ColName, Sheet sheet, Row row1) {
		Row r = sheet.getRow(0);
		try {

			for (int i = 0; i < r.getPhysicalNumberOfCells(); i++) {
				if (r.getCell(i, Row.CREATE_NULL_AS_BLANK).getStringCellValue().equalsIgnoreCase(ColName)) {
					String retString = celltype(row1.getCell(i));
					return retString;
				}
			}
		} catch (NullPointerException e) {
			LOGGER.info("Couldn't fetch data from the excel sheet", e);
		}
		return "";
	}

	public static void setVariable(String ColName, Sheet sheet, Row row1, String Value) {
		Row r = sheet.getRow(0);
		try {

			for (int i = 0; i < r.getPhysicalNumberOfCells(); i++) {
				if (r.getCell(i, Row.CREATE_NULL_AS_BLANK).getStringCellValue().equalsIgnoreCase(ColName)) {
					row1.createCell(i).setCellValue(Value);
				}
			}
		} catch (NullPointerException e) {
			LOGGER.info("Couldn't copy the desired objects into the excel sheet", e);
		}
	}

	public static String celltype(Cell cell) {
		String retString = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:

				retString = cell.getRichStringCellValue().getString();
				break;
			case Cell.CELL_TYPE_NUMERIC:

				int a = (int) cell.getNumericCellValue();

				retString = String.valueOf(a);

				break;
			case Cell.CELL_TYPE_BOOLEAN:

				retString = String.valueOf(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_BLANK:
				retString = "";
				break;
			case Cell.CELL_TYPE_FORMULA:
				retString = String.valueOf(cell.getCellFormula());
				break;
			default:
				retString = "";
			}
		}
		return retString;
	}

	public String objRepository(String eleName, String FileName) {
		String value = "";
		try {
			File file = new File(FileName);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("ObjRep");

			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element ele = (Element) nNode;
					value = ele.getElementsByTagName(eleName).item(i).getTextContent();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public static void fetchProperty() throws Throwable {
		try {
			File fileConfig = new File(".\\config\\configuration.properties");
			FileInputStream fileInput = new FileInputStream(fileConfig);
			Properties prop = new Properties();
			prop.load(fileInput);
			workSheetPath = prop.getProperty("WorkSheetPath");
			selectAccEntry = prop.getProperty("selectAccEntry");
			accEntry = prop.getProperty("AccEntry");
			addServices = prop.getProperty("addServices");
			datePicker = prop.getProperty("datePicker");
			earnedDiscount = prop.getProperty("earnedDiscount");
			loginPage = prop.getProperty("loginPage");
			serviceSelection = prop.getProperty("serviceSelection");
			dIMS = prop.getProperty("DIMS");
			surcharges = prop.getProperty("surcharges");
			surchargesCOD = prop.getProperty("surchargesCOD");
			surchargesHUWgtWaivers = prop.getProperty("surchargesHUWgtWaivers");
			surchargesPremServUpcharges = prop.getProperty("surchargesPremServUpcharges");
			vdhuRates = prop.getProperty("vdhuRates");
			vdMileageRates = prop.getProperty("vdMileageRates");
			vdWgtBrkDiscAdj = prop.getProperty("vdWgtBrkDiscAdj");
			vdWgtBrkRates = prop.getProperty("vdWgtBrkRates");
		} catch (IOException ex) {
			ex.printStackTrace();

		}

	}

}

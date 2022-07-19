package steps;

import java.util.Properties;

import Base.GlobalVariables;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import util.*;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import static util.PropertiesLoader.commonProp;

public class ApplicationHooks {

	private DriverFactory driverFactory;
	private WebDriver driver;
	private ConfigReader configReader;
	Properties prop;
	Logger log = LoggerHelper.getLogger(ApplicationHooks.class);

	@Before(order = 0)
	public void getProperty() throws Exception {
		configReader = new ConfigReader();
		prop = configReader.init_prop();
		PropertiesLoader propertiesLoader = new PropertiesLoader();
		CSVDataReader csvData = new CSVDataReader();
		propertiesLoader.commonConfigLoader();
		String csvConfig = commonProp.getProperty("csvConfigData");
		csvData.csvConfigData(csvConfig);
		GlobalVariables.environment = csvData.getCSVValue("environment");
		String executionType = csvData.getCSVValue("executionType");
		String executionMethod = csvData.getCSVValue("executionMethod");
		String excelFilePath = csvData.getCSVValue("excelInputFile");
		String ScenarioData = csvData.getCSVValue("scenarioSheetName");
		String accountData = csvData.getCSVValue("accountDetailsSheet");
		String UserData = csvData.getCSVValue("UserDataSheet");
		ExcelDataCollection.globalExcelData = ExcelDataCollection.sheetData(excelFilePath);
		ExcelDataCollection.scenarioSheetData = ExcelDataCollection.getSheettoData(ScenarioData);
		ExcelDataCollection.accountsSheetData = ExcelDataCollection.getSheettoData(accountData);
		ExcelDataCollection.usersSheetData = ExcelDataCollection.getSheettoData(UserData);
		log.info( "Executed Before scenario method.....");
	}

	public void launchBrowser() {
		String browserName = prop.getProperty("browser");
		driverFactory = new DriverFactory();
		driver = driverFactory.init_driver(browserName);
		System.out.println("Successfully launched the browser : "+ browserName);
	}


	@After(order = 1)
	public void tearDown(Scenario scenario) {
		log.info( "Executed After scenario method.....");
		if (scenario.isFailed()) {
			String screenshotName = scenario.getName().replaceAll(" ", "_");
			byte[] sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(sourcePath, "image/png", screenshotName);
			log.info( "successfully captured the failed screenshot");
		}
	}

}

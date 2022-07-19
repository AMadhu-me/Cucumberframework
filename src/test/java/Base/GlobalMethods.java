package Base;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import util.CSVDataReader;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class GlobalMethods {

    public static WebDriver driver;
    CSVDataReader csvData = new CSVDataReader();

    public void browserLaunch(String browser) throws IOException
    {
        String osName = System.getProperty("os.name").toLowerCase();
        if(osName.contains("win"))
        {
            switch(browser.toLowerCase())
            {
                case "chrome":
                    /*String filePath = System.getProperty("user.dir")+ File.separator+"RDPFiles";
                    File downloadFilepath = new File(filePath);
                    downloadFilepath.mkdir();
                    HashMap<String,Object> chromePrefs = new HashMap<>();
                    chromePrefs.put("profile.default_content_settings.popups",0);
                    chromePrefs.put("download.default_directory",testRunResultHTMLFolder);
                    chromePrefs.put("download.default_directory",filePath);

                    ChromeOptions options = new ChromeOptions();
                    options.setExperimentalOption("prefs",chromePrefs);
                    options.addArguments("--enable-automation");
                    options.addArguments("test-type=browser");
                    options.addArguments("disable-infobars");
                    options.addArguments("disable-extensions");
                    options.setExperimentalOption("useAutomationExtension",false);
                    options.addArguments("start-maximized");
                    DesiredCapabilities cap = new DesiredCapabilities();
                    cap.setCapability(ChromeOptions.CAPABILITY,options);*/

                    String chromeDriverPath=csvData.getCSVValue("ChromeDriverPath");
                    System.out.println("DriverPath: " +chromeDriverPath);
                    //System.setProperty("webdriver.chrome.driver",chromeDriverPath);
                    WebDriverManager.chromedriver().setup();
                    driver= new ChromeDriver();

                    break;
                case "nogui":
                    String filePath1 = System.getProperty("user.dir")+ File.separator+"RDPFiles";
                    File downloadFilepath1 = new File(filePath1);
                    downloadFilepath1.mkdir();
                    HashMap<String,Object> chromePrefs1 = new HashMap<>();
                    chromePrefs1.put("profile.default_content_settings.popups",0);
                    //chromePrefs1.put("download.default_directory",testRunResultHTMLFolder);
                    chromePrefs1.put("download.default_directory",filePath1);

                    ChromeOptions options1 = new ChromeOptions();
                    options1.setExperimentalOption("prefs",chromePrefs1);
                    options1.addArguments("--enable-automation");
                    options1.addArguments("test-type=headless");
                    options1.addArguments("disable-infobars");
                    options1.addArguments("disable-extensions");
                    options1.addArguments("--headless");
                    options1.addArguments("--disable-gpu");
                    options1.addArguments("--window-size=1450,600");
                    options1.addArguments("start-maximized");
                    options1.setExperimentalOption("useAutomationExtension",false);
                    options1.addArguments("start-maximized");
                    DesiredCapabilities cap1 = new DesiredCapabilities();
                    cap1.setCapability(ChromeOptions.CAPABILITY,options1);

                    String chromeDriverPath1=csvData.getCSVValue("ChromeDriverPath");
                    System.out.println("DriverPath: " +chromeDriverPath1);
                    System.setProperty("webdriver.chrome.driver",chromeDriverPath1);
                    driver= new ChromeDriver(cap1);
                    break;

                case "grid":
                    /*DesiredCapabilities capabilities = new DesiredCapabilities.chrome();
                    capabilities.setBrowserName("chrome");
                    capabilities.setPlatform(Platform.WINDOWS);
                    driver = new RemoteWebDriver(new URL("http://localhost:8080/wd/hub"),capabilities);*/
                    break;
            }
        }
    }

    public String setSiteURL(String env)
    {
        String siteURL = null;
        switch(env.toLowerCase())
        {
            case "sit":
                siteURL=csvData.getCSVValue("sit_url");
                break;
            case "stg":
            siteURL=csvData.getCSVValue("stg");
            break;
        }
        return siteURL;
    }

    public void launchURL(String environment,String browser) throws IOException {
        browserLaunch(browser);
        String url = setSiteURL(environment);
        driver.manage().window().maximize();
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void enterValues(By by , String data)
    {
        try
        {
            Thread.sleep(2000);
            driver.findElement(by).sendKeys(data);
        }catch(Exception e)
        {

        }
    }
public void getWindowScreenshot()
{
    try
    {
        Robot robot = new Robot();
        Rectangle screensize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage tmp = robot.createScreenCapture(screensize);
       // ExtentReport.imageRunpath="/"+getCurrentTimeSeconds()+".png";
       // ExtentReport.runtimeScreenshotImagePath = runTimeScenarioScreenshotFolder+ExtentReport.imageRunpath;
        //ImageIO.write(tmp,"png",new File(ExtentReport.runtimeScreenshotImagePath));
    }catch(Exception e)
    {

    }
}
    public void getScreenshot()
    {
        try
        {
            File yyy= ((TakesScreenshot) GlobalMethods.driver).getScreenshotAs(OutputType.FILE);
            //ExtentReport.imageRunpath="/"+getCurrentTimeSeconds()+".png";
           // ExtentReport.runtimeScreenshotImagePath = runTimeScenarioScreenshotFolder+ExtentReport.imageRunpath;
            //FileUtils.copyFile(yyy,new File(ExtentReport.runtimeScreenshotImagePath));
        }catch(Exception e)
        {

        }
    }
    public String encodePassword(String input)
    {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }
    public String decodePassword(String input)
    {
        byte[] decodeBytes = Base64.getDecoder().decode(input);
        return new String(decodeBytes);
    }

    public String Get24HHour() {
        return String.valueOf(LocalDateTime.now().getHour());
    }
    public String getCurrentTime() {

        DateFormat dft = new SimpleDateFormat("dd-MM-YYYY_HH-mm");
        Calendar cl = Calendar.getInstance();
        return dft.format(cl.getTime());
    }
    public String getCurrentTimeSeconds() {

        DateFormat dft = new SimpleDateFormat("dd-MM-YYYY_HH-mm-ss_a");
        Calendar cl = Calendar.getInstance();
        return dft.format(cl.getTime());
    }

    public Properties loadProp(FileReader propFilePath) throws IOException {
        Properties properties = new Properties();
        //InputStream propertiesStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propFilePath);
        properties.load(propFilePath);
        return properties;
    }
    public static ArrayList<String> getListofStories(Map<String, Map<String,String>> storyCollection, String exeType)
    {
        ArrayList<String> inputStories = new ArrayList<>();
        for(Map.Entry<String,Map<String,String>> storyPrecondition : storyCollection.entrySet())
        {
            String executionRequired = util.ExcelDataCollection.getSpecificKeyValue(storyCollection,storyPrecondition.getKey(),"executionRequired");
            if(executionRequired.equalsIgnoreCase("Yes"))
            {
                inputStories.add(util.ExcelDataCollection.getSpecificKeyValue(storyCollection,storyPrecondition.getKey(),"scenarioFilePath"));
            }
        }


        return inputStories;
    }

    public static ArrayList<String> getListofJiraStories(Map<String,Map<String,String>> jiraStoryCollection)
    {
        ArrayList<String> inputStories = new ArrayList<>();
        for(Map.Entry<String,Map<String,String>> jiraStory : jiraStoryCollection.entrySet())
        {
            Map<String,String> eachTestCase = jiraStory.getValue();
            CSVDataReader csvData = new CSVDataReader();
            String testProp = csvData.getCSVValue("executionScheme");
            if(testProp.equalsIgnoreCase("skippass"))
            {
                String preExecution = eachTestCase.get("preExecutionStatus");
                if(!preExecution.equalsIgnoreCase("pass"))
                {
                    String oneStory = eachTestCase.get("scenarioFilePath");
                    if(oneStory!=null)
                    {
                        inputStories.add(oneStory);
                    }
                }
            }else
            {
                String oneStory = eachTestCase.get("scenarioFilePath");
                if(oneStory!=null)
                {
                    inputStories.add(oneStory);
                }
            }
        }
        return  inputStories;
    }
    public String getBrowserFromJira(Map<String,Map<String,Map<String,String>>> jiraColl,String scenarioName)
    {
        String browser=null;
        for(Map.Entry<String,Map<String,Map<String,String>>> jiraCollection: jiraColl.entrySet())
        {
            Map<String,Map<String,String>> testcaseDetails =jiraCollection.getValue();
            for(Map.Entry<String,Map<String,String>> testDetails : testcaseDetails.entrySet())
            {
                String testCaseName = testDetails.getValue().get("testcaseName");
                if(testCaseName.equalsIgnoreCase(scenarioName))
                {
                    browser = testDetails.getValue().get("browser");
                }
            }
        }
        return browser;
    }
    public WebElement identifyElement(String locators)
    {
        WebElement element=null;
        String objectLocator= StringUtils.substringAfter(locators,"~");
        String objectIdentifier =StringUtils.substringBefore(locators,"~");
        if(objectIdentifier.equalsIgnoreCase("id"))
        {
            element = driver.findElement(By.xpath(objectLocator));
        }
        if(objectIdentifier.equalsIgnoreCase("xpath"))
        {
            element = driver.findElement(By.xpath(objectLocator));
        }
        if(objectIdentifier.equalsIgnoreCase("name"))
        {
            element = driver.findElement(By.xpath(objectLocator));
        }

        return element;
    }

    public boolean isElementPresent(By by){
        try{
            driver.findElement(by);
            return true;
        }
        catch(NoSuchElementException e){
            return false;
        }
    }

}






















package Base;


import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import util.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class Baseclass {

    Logger log = LoggerHelper.getLogger(Baseclass.class);
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

                    String chromeDriverPath=csvData.getCSVValue("ChromeDriverPath");
                    System.out.println("DriverPath: " +chromeDriverPath);
                    //System.setProperty("webdriver.chrome.driver",chromeDriverPath);
                    WebDriverManager.chromedriver().setup();
                    driver= new ChromeDriver();
                    log.info(dateFormat()+"-Successfully launched the browser : "+browser);
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
                siteURL=csvData.getCSVValue("stg_url");
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
        log.info(dateFormat()+"-Successfully launched the Application in : "+environment);
    }

    public String dateFormat()
    {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        String date1= dateFormat.format(date);
        return date1;
    }


}

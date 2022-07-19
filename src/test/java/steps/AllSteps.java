package steps;

import Base.Baseclass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.apache.log4j.Logger;
import org.junit.Assert;
import pages.LoginPage;
import util.CSVDataReader;
import util.ExcelDataCollection;
import util.LoggerHelper;

public class AllSteps extends Baseclass {

    CSVDataReader csvData= new CSVDataReader();
    LoginPage _loginpage = new LoginPage();
    Logger log = LoggerHelper.getLogger(AllSteps.class);

    @Given("^Launch the application URL$")
    public void application() throws Throwable {
        String browserName=csvData.getCSVValue("browser");;
        String environment =csvData.getCSVValue("environment");
        launchURL(environment,browserName);
        if(driver.getTitle().equalsIgnoreCase("Login - My Store"))
        {
            log.info(dateFormat()+"-Successfully launched the application");
        }
        else
        {
            log.info("Failed to launch the application");
            Assert.fail();
        }

    }

    @When("^Enter the Username and password$")
    public void UsernamePassword() throws Throwable {
        String loginUser = ExcelDataCollection.getSpecificKeyValue(ExcelDataCollection.usersSheetData,"LoginUser","userName");
        String password = ExcelDataCollection.getSpecificKeyValue(ExcelDataCollection.usersSheetData,"LoginUser","passWord");
        _loginpage.enterUsername(loginUser);
        _loginpage.enterPassword(password);

    }
    @When("^Click the Submit button$")
    public void ClickSubmit() throws Throwable {
        _loginpage.clickLogin();

    }

    @Then("^close the browser$")
    public void closeBrowser() throws Throwable {
        _loginpage.quitBrowser();
    }
}

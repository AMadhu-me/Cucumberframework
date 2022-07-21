package steps;

import Base.Baseclass;
import Base.GlobalVariables;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import pages.AddcustomerPage;
import pages.LoginPage;
import pages.LoginPage1;
import pages.SearchCustomerPage;
import util.CSVDataReader;
import util.ExcelDataCollection;
import util.LoggerHelper;

public class AllSteps extends Baseclass {

    CSVDataReader csvData= new CSVDataReader();
    LoginPage _loginpage = new LoginPage();
    LoginPage1 lp = new LoginPage1();
    Logger log = LoggerHelper.getLogger(AllSteps.class);
    AddcustomerPage addCust = new AddcustomerPage();
    SearchCustomerPage searchCust= new SearchCustomerPage();

    @Given("^Launch the application URL$")
    public void application() throws Throwable {
        String browserName=csvData.getCSVValue("browser");;
        String environment =csvData.getCSVValue("environment");
        System.out.println("ScenarioName :========="+GlobalVariables.scenarioName);
        String executionKey =ExcelDataCollection.getSpecificKeyValue(ExcelDataCollection.scenarioSheetData,GlobalVariables.scenarioName,"executionRequired");
        if(executionKey.equalsIgnoreCase("yes"))
        {
            launchURL(environment,browserName);
        }
        else
        {
            System.out.println("Execution is not enabled for this scenario : "+GlobalVariables.scenarioName);
            log.info("Execution is not enabled for this scenario : "+GlobalVariables.scenarioName);
            Assert.fail("Execution is not enabled for this scenario : "+GlobalVariables.scenarioName);

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
    @When("User enters Username and Password")
    public void user_enters_Email_as_and_Password_as() {
        String loginUser = ExcelDataCollection.getSpecificKeyValue(ExcelDataCollection.usersSheetData,"LoginUser3","userName");
        String password = ExcelDataCollection.getSpecificKeyValue(ExcelDataCollection.usersSheetData,"LoginUser3","passWord");
        log.info("************* Prvding user and password *****************");
        lp.setUserName(loginUser);
        lp.setPassword(password);
    }

    @When("User enters Email as {string} and Password as {string}")
    public void user_enters_Email_as_and_Password_as(String email, String password) {
        log.info("************* Prvding user and password *****************");
        lp.setUserName(email);
        lp.setPassword(password);
    }

    @When("Click on Login")
    public void click_on_Login() {
        log.info("************* click on login *****************");
        lp.clickLogin();
    }

    @Then("Page Title should be {string}")
    public void page_Title_should_be(String exptitle) throws InterruptedException {

        if(driver.getPageSource().contains("Login was unsuccessful"))
        {
            log.info("************* Login failed *****************");
            driver.close();
            junit.framework.Assert.assertTrue(false);
        }
        else
        {
            log.info("************* Login Passed *****************");
            junit.framework.Assert.assertEquals(exptitle, driver.getTitle());
        }
        Thread.sleep(3000);

    }
    @When("User click on Log out link")
    public void user_click_on_Log_out_link() throws InterruptedException {
        log.info("************* clciking on logout *****************");
        lp.clickLogout();
        Thread.sleep(3000);
    }
    @Then("User can view Dashboad")
    public void user_can_view_Dashboad() {

        log.info("********* Verifying Dashboad page title after login successful **************");
        junit.framework.Assert.assertEquals("Dashboard / nopCommerce administration",addCust.getPageTitle());
    }

    @When("User click on customers Menu")
    public void user_click_on_customers_Menu() throws InterruptedException {
        Thread.sleep(3000);
        log.info("********* Clicking on customer main menu **************");
        addCust.clickOnCustomersMenu();
    }

    @When("click on customers Menu Item")
    public void click_on_customers_Menu_Item() throws InterruptedException {
        Thread.sleep(2000);
        log.info("********* Clicking on customer sub menu **************");
        addCust.clickOnCustomersMenuItem();
    }

    @When("click on Add new button")
    public void click_on_Add_new_button() throws InterruptedException {
        addCust.clickOnAddnew();
        Thread.sleep(2000);
    }

    @Then("User can view Add new customer page")
    public void user_can_view_Add_new_customer_page() {
        junit.framework.Assert.assertEquals("Add a new customer / nopCommerce administration", addCust.getPageTitle());
    }

    @When("User enter customer info")
    public void user_enter_customer_info() throws InterruptedException {
        String email = randomestring() + "@gmail.com";
        System.out.println("Emailaddress:::::"+email);
        addCust.setEmail(email);
        addCust.setPassword("test123");
        addCust.setCustomerRoles("Registered");
        Thread.sleep(3000);

        addCust.setManagerOfVendor("Vendor 2");
        addCust.setGender("Male");
        addCust.setFirstName("Reddy");
        addCust.setLastName("J");
        addCust.setDob("7/05/1991"); // Format: D/MM/YYY
        addCust.setCompanyName("Self");
        addCust.setAdminContent("This is for testing.........");
    }

    @When("click on Save button")
    public void click_on_Save_button() throws InterruptedException {
        log.info("********* Saving customer details **************");
        addCust.clickOnSave();
        Thread.sleep(2000);
    }

    @Then("User can view confirmation message {string}")
    public void user_can_view_confirmation_message(String string) {
        junit.framework.Assert.assertTrue(driver.findElement(By.tagName("body")).getText()
                .contains("The new customer has been added successfully"));
    }

    //Searching customer by email ID.............................
    @When("Enter customer EMail")
    public void enter_customer_EMail() {
        log.info("********* Searching customer details by Email **************");
        searchCust.setEmail("OxdRv@gmail.com");
    }
    @When("Enter customer roles")
    public void enter_customer_Roles() {
        log.info("********* Entering thr Role **************");
        searchCust.setRoles("Guests");
    }

    @When("Click on search button")
    public void click_on_search_button() throws InterruptedException {
        searchCust.clickSearch();
        Thread.sleep(3000);
    }

    @Then("User should found Email in the Search table")
    public void user_should_found_Email_in_the_Search_table() {
        boolean status=searchCust.searchCustomerByEmail("eTjqv@gmail.com");
        junit.framework.Assert.assertEquals(true, status);
    }

    //steps for searching a customer by Name................
    @When("Enter customer FirstName")
    public void enter_customer_FirstName() {
        log.info("********* Searching customer details by Name **************");
        searchCust.setFirstName("Reddy");
    }

    @When("Enter customer LastName")
    public void enter_customer_LastName() {
        searchCust.setLastName("J");
    }

    @Then("User should found Name in the Search table")
    public void user_should_found_Name_in_the_Search_table() {
        boolean status=searchCust.searchCustomerByName("Reddy J");
        Assert.assertEquals(true, status);
    }
}

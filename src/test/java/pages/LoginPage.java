package pages;

import Base.Baseclass;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import util.LoggerHelper;

public class LoginPage extends Baseclass {


	By uName = By.id("email");
	By pswd = By.id("passwd");
	By loginBtn = By.id("SubmitLogin");
	Logger log = LoggerHelper.getLogger(LoginPage.class);

	public void enterUsername(String user)
	{
		driver.findElement(uName).sendKeys(user);
		log.info(dateFormat()+"-successfully entered the UserName :"+user);
	}

	public void enterPassword(String pass)
	{

		driver.findElement(pswd).sendKeys(pass);
		log.info(dateFormat()+"-successfully entered the Password");
	}

	public void clickLogin()
	{

		driver.findElement(loginBtn).click();
		log.info(dateFormat()+"-successfully clicked the login button");

	}
	public void quitBrowser()
	{
		driver.close();
		log.info(dateFormat()+"-successfully closed the browser");
	}
}

package pages;

import Base.Baseclass;
import org.openqa.selenium.By;

public class LoginPage1 extends Baseclass {

    By txtEmail = By.id("Email");
    By txtPassword = By.id("Password");
    By btnLogin = By.xpath("//*[contains(text(),'Log in')]");
    By lnkLogout = By.linkText("Logout");


    public void setUserName(String uname) {
        driver.findElement(txtEmail).clear();
        driver.findElement(txtEmail).sendKeys(uname);

    }

    public void setPassword(String pwd) {
        driver.findElement(txtPassword).clear();
        driver.findElement(txtPassword).sendKeys(pwd);
    }

    public void clickLogin() {

        driver.findElement(btnLogin).click();
    }

    public void clickLogout() {

        driver.findElement(lnkLogout).click();
    }

}


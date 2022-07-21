package pages;

import Base.Baseclass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SearchCustomerPage extends Baseclass {


	By txtEmail=By.id("SearchEmail");
	By txtFirstName=By.id("SearchFirstName");
	By txtLastName=By.id("SearchLastName");
	By btnSearch=By.id("search-customers");
	By tblSearchResults=By.xpath("//table[@role='grid']");
	By table=By.xpath("//table[@id='customers-grid']");
	By tableRows=By.xpath("//table[@id='customers-grid']//tbody/tr");
	By tableColumns=By.xpath("//table[@id='customers-grid']//tbody/tr/td");
	By customerRoles=By.className("k-multiselect-wrap k-floatwrap");

	public void setEmail(String email) {
		driver.findElement(txtEmail).clear();
		driver.findElement(txtEmail).sendKeys(email);
	}
	public void setRoles(String role) {
		driver.findElement(customerRoles).clear();
		driver.findElement(txtEmail).sendKeys(role);
	}

	public void setFirstName(String fname) {

		driver.findElement(txtFirstName).clear();
		driver.findElement(txtFirstName).sendKeys(fname);
	}

	public void setLastName(String lname) {

		driver.findElement(txtLastName).clear();
		driver.findElement(txtLastName).sendKeys(lname);
	}

	public void clickSearch() {
		driver.findElement(btnSearch).click();

	}

	public int getNoOfRows() {
		List<WebElement> rows = driver.findElements(tableRows);
		return (rows.size());
	}

	public int getNoOfColumns() {
		List<WebElement> col = driver.findElements(tableColumns);
		return (col.size());
	}

	public boolean searchCustomerByEmail(String email) {
		boolean flag = false;
		for (int i = 1; i <= getNoOfRows(); i++) {
			String emailid = driver.findElement(By.xpath("//table[@id='customers-grid']/tbody/tr[" + i + "]/td[2]"))
					.getText();
			System.out.println(emailid);

			if (emailid.equals(email)) {
				flag = true;
				break;
			}
		}

		return flag;

	}

	public boolean searchCustomerByName(String Name) {
		boolean flag = false;

		for (int i = 1; i <= getNoOfRows(); i++) {
			String name = driver.findElement(By.xpath("//table[@id='customers-grid']/tbody/tr[" + i + "]/td[3]"))
					.getText();

			
			if (Name.equals(name)) {
				flag = true;
				break;
			}
		}

		return flag;

	}

}

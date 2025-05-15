package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//input[@id='input-email']")
	public WebElement txtEmailAdd;

	@FindBy(xpath = "//input[@id='input-password']")
	public WebElement txtPassword;

	@FindBy(xpath = "//input[@value=\"Login\"]")
	public WebElement btnLogin;

	public void setEmailAddress(String email) {
		txtEmailAdd.sendKeys(email);
	}

	public void setPassword(String pwd) {
		txtPassword.sendKeys(pwd);
	}

	public void clickLogin() {
		btnLogin.click();
	}

}

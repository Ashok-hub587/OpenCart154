package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class AccountRegistrationPage extends BasePage {

	public AccountRegistrationPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//input[@id='input-firstname']")
	WebElement txtFirstName;
	@FindBy(xpath = "//input[@id='input-lastname']")
	WebElement txtLastNmae;
	@FindBy(xpath = "//input[@id='input-email']")
	WebElement txtEmail;
	@FindBy(xpath = "//select[@id='input-country']")
	WebElement drpdwnCountry;
	@FindBy(xpath = "//input[@id='input-telephone']")
	WebElement txtTelephone;
	@FindBy(xpath = "//input[@id='input-password']")
	WebElement txtPasword;
	@FindBy(xpath = "//input[@id='input-confirm']")
	WebElement txtConfirmPasword;
	@FindBy(name="agree")
	public WebElement chkPrivacyPoilicy;
	@FindBy(xpath = "//input[@class=\"btn btn-primary\"]")
	WebElement btnContinue;
	@FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement msgConfirmation;
	
	public void setUserFirstName(String fName) {
		txtFirstName.sendKeys(fName);
	}
	
	public void setLastName(String lName) {
		txtLastNmae.sendKeys(lName);
	}
	
	public void setEmail(String email) {
		txtEmail.sendKeys(email);
	}
	
	public void setTelephone(String TelephnNum) {
		txtTelephone.sendKeys(TelephnNum);
	}
	
	public void selectCountry(String country) {
		Select sObj=new Select(drpdwnCountry);
		sObj.selectByVisibleText(country);
	}
	
	public void setPassWord(String pwd) {
		txtPasword.sendKeys(pwd);
	}
	
	public void setConfirmPWd(String pwd) {
		txtConfirmPasword.sendKeys(pwd);
	}
	
	public void clickprivacyPolicy() {
		chkPrivacyPoilicy.click();
	}
	
	public void clickContinue() {
		btnContinue.click();
	}
	
	public String getConfirmationMsg() {
		try{
			return msgConfirmation.getText();
		}catch (Exception e) {
			return (e.getMessage());
		}
	}
}

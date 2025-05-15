package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage{
	
	public MyAccountPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath="//h2[normalize-space()='My Account']")
	public WebElement msgHeading;
	
	@FindBy(xpath="//a[normalize-space()='Newsletter']/following-sibling::a")
	public WebElement likLogout;
	
	public boolean isMyAccountExists() {
		try {
		return msgHeading.isDisplayed();
		}catch(Exception e)
		{
			return false;
		}
	}
	
	JavascriptExecutor js=(JavascriptExecutor) driver;
	
	public void clickLogout() {
		//likLogout.click();
		js.executeScript("arguments[0].click()", likLogout);
	}
	
}

package testCases;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {

	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class,groups = "DataDriven") // If Data provider method is created in																// another class
	public void valid_LoginDDT(String email, String pwd, String exp) throws InterruptedException {

		logger.info("************** Started TC003_LoginDDT ******** ");
		try {
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();

			LoginPage lp = new LoginPage(driver);
			lp.setEmailAddress(email);
			lp.setPassword(pwd);
			lp.clickLogin();

			MyAccountPage macp = new MyAccountPage(driver);
			boolean targetpage = macp.isMyAccountExists();

			JavascriptExecutor js = (JavascriptExecutor) driver;

			/*
			 * Data is valid - login success - test pass - logout login failed - test fail
			 * 
			 * Data is invalid- login success -test fail -Logout login failed - test success
			 */

			if (exp.equalsIgnoreCase("Valid")) {
				if (targetpage == true) {
					js.executeScript("arguments[0].scrollIntoView()", macp.likLogout);
					macp.clickLogout();
					Assert.assertTrue(true, "Test Pass");
				} else {
					Assert.assertTrue(false, "Test fail");
				}
			}

			if (exp.equalsIgnoreCase("Invalid")) {
				if (targetpage == true) {
					macp.clickLogout();
					Assert.assertTrue(false, "Test fail");
				} else {
					Assert.assertTrue(true, "Test Pass");
				}
			}
		} catch (Exception e) {
			Assert.fail();
		}
		logger.info("************** Finished TC003_LoginDDT ******** ");
	}
}

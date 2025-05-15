package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;

public class TC002_LoginTest extends BaseClass {

	@Test(groups={"Smoke","Master"})
	public void verify_Login() {

		logger.info("********** Started TC002_LoginTest ***********");
		try {
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();

			LoginPage lp = new LoginPage(driver);
			lp.setEmailAddress(p.getProperty("userName"));
			lp.setPassword(p.getProperty("password"));
			lp.clickLogin();
			logger.info("*********** CLICKED ON LOGIN BUTTON *******");

			MyAccountPage macp = new MyAccountPage(driver);
			boolean targetpage = macp.isMyAccountExists();
			Assert.assertTrue(targetpage, "TEST FAILED");
		} catch (Exception e) {
			Assert.fail();
		}
		 logger.info("*********** FINISHED TC002_LoginTest ************");
	}
}

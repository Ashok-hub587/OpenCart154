package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;

public class TC001_AccountRegistrationTest extends BaseClass {

	@Test(groups= {"Regression","Master"})
	public void verify_Account_Registration() {
		logger.info("******* Starting TC001_AccountRegistrationTest **********");
		try {
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickRegister();

			logger.info("******* Providing Couatomer Information **********");
			AccountRegistrationPage regPage = new AccountRegistrationPage(driver);
			regPage.setUserFirstName(randomString(5));
			regPage.setLastName(randomString(5));
			regPage.setEmail(randomString(8) + "@email.com");
			regPage.setTelephone(randomNumber());
			String password = randomAlphaNumeric();
			regPage.setPassWord(password);
			regPage.setConfirmPWd(password);
			regPage.clickprivacyPolicy();
			regPage.clickContinue();
			logger.info("Validating expected message........");
			String confrMsg = regPage.getConfirmationMsg();
			if (confrMsg.equals("Your Account Has Been Created!")) {
				Assert.assertTrue(true);
			} else {
				logger.error("Test failed........");
				logger.debug("Debug logs.....");
				Assert.assertTrue(false);
			}
			logger.info("******* Finished TC001_AccountRegistrationTest **********");
		} catch (Exception e) {
			Assert.fail();
		}
	}

}

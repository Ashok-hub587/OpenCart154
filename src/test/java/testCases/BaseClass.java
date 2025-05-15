package testCases;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseClass {

	public static WebDriver driver;
	public Logger logger;
	Properties p;

	@SuppressWarnings({ "deprecation" })
	@BeforeClass(groups = { "Smoke", "Regression", "Master" })
	@Parameters({ "os", "browser" })
	public void setUp(String os, String br) throws IOException {

		FileReader file = new FileReader("./src/test/resources/config.properties");
		p = new Properties();
		p.load(file);

		logger = LogManager.getLogger(this.getClass());

		if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {

			DesiredCapabilities capabilities = new DesiredCapabilities();
			// OS
			switch (os.toLowerCase()) {
			case ("windows"):capabilities.setPlatform(Platform.WIN11);break;
			case ("mac"):capabilities.setPlatform(Platform.MAC);break;
			case "linux":capabilities.setPlatform(Platform.LINUX);break;
			default:System.out.println("No Matching OS");return;
			}
			
			// Browser
			switch (br.toLowerCase()) {
			case "chrome":capabilities.setBrowserName("chrome");break;
			case "edge":capabilities.setBrowserName("MicrosoftEdge");break;
			case "firefox":capabilities.setBrowserName("Firefox");break;
			default:System.out.println("No match browser");return;
			}
			driver = new RemoteWebDriver(new URL("http://172.22.0.1:4444/wd/hub"), capabilities);
		}
		

		if (p.getProperty("execution_env").equalsIgnoreCase("local")) {
			switch (br.toLowerCase()) {
			case "chrome":driver = new ChromeDriver();break;
			case "firefox":driver = new FirefoxDriver();break;
			case "edge":driver = new EdgeDriver();break;
			default:System.out.println("Invalid browser..!");return;
			}
		}
		
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get(p.getProperty("appUrl"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20l));
	}

	
	@AfterClass(groups = { "Smoke", "Regression", "Master" })
	public void tearDown() {
		driver.quit();
	}

	@SuppressWarnings("deprecation")
	public String randomString(int stringLength) {
		String generatedString = RandomStringUtils.randomAlphabetic(stringLength);
		return generatedString;
	}

	@SuppressWarnings("deprecation")
	public String randomNumber() {
		String generatedNumber = RandomStringUtils.randomNumeric(10);
		return generatedNumber;
	}

	@SuppressWarnings("deprecation")
	public String randomAlphaNumeric() {
		return RandomStringUtils.randomAlphanumeric(4, 10);
	}

	
	public String captureScreen(String tname) {
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		TakesScreenshot ts = (TakesScreenshot) driver;
		File sourceFile = ts.getScreenshotAs(OutputType.FILE);

		String targetFilePath = System.getProperty("user.dir") + "/screenshots/" + tname + "_" + timeStamp + ".png";
		File targetFile = new File(targetFilePath);

		sourceFile.renameTo(targetFile);
		if (!sourceFile.renameTo(targetFile)) {
			System.out.println("Warning: File renaming failed.");
		}

		return targetFilePath;
	}
}

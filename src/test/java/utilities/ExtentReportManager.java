package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriverException;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testCases.BaseClass;

public class ExtentReportManager implements ITestListener{

	public ExtentSparkReporter sparkReporter; //UI of reportt
	public ExtentReports extent; //populate common info on report
	public ExtentTest test;	//creating testcase entries in the report and update status of the test methods
	
	String repName;
	
	public void onStart(ITestContext testContext) {
		
		/*SimpleDtaeFromat df=newsimpleDateFormat(yyyy.MM.dd.HH.mm.ss);
		 * Date dt=new Date();
		 * String currentdatetimestamp=df. format(dt) */
		
		String timeStamp=new SimpleDateFormat(" yyyy.MM.dd.HH.mm.ss").format(new Date()); //TIME STAMP
		repName = "Test -Report" + timeStamp + ".html";
	    
		sparkReporter=new ExtentSparkReporter(System.getProperty("user.dir")+"/reports/"+ repName);
		
		sparkReporter.config().setDocumentTitle("OpenCart Automation Report"); // Title of Report
		sparkReporter.config().setReportName("OpenCart Functional Testing"); //name of report
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent= new ExtentReports();
		extent.attachReporter(sparkReporter);
		
		extent.setSystemInfo("Application", "OpenCart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Envoronment", "QA");
		
		String os= testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Opetating System", os);
		
		String browser=testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("browser", browser);
		
		java.util.List<String> includedGroups=testContext.getCurrentXmlTest().getIncludedGroups();
		if (!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includedGroups.toString());
		}
	  }
	
	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName()); //Create new entry in report
		test.assignCategory(result.getMethod().getGroups()); //to display groups in report
		test.log(Status.PASS, result.getName()+ "got successfully execuetd"); // update the status p/f/s
	}

	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName()); //from result
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.FAIL,result.getName()+" got failed");
		test.log(Status.INFO,result.getThrowable().getMessage());
		
		try {
			String imgPath=new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		} catch (WebDriverException e) {
			// TODO: handle exception
			System.out.println("Screenshot capture failed: " + e.getMessage());
	        return; // Handle failure appropriately
		}
	}
	
	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName()+" got skipped");
		test.log(Status.INFO,result.getThrowable().getMessage());
	}
	
	public void onFinish(ITestContext context) {
		extent.flush();
		
		String pathOfExtentReport=System.getProperty("user.dir")+"/reports/"+repName;
		File extentReport=new File(pathOfExtentReport);
		
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		/*try {
			@SuppressWarnings("deprecation")
			URL url=new URL("file://"+System.getProperty("user.dir")+"\\reports\\" +repName);
			
			//Craete Email Message
			ImageHtmlEmail email=new ImageHtmlEmail();
			email.setDataSourceResolver(new DataSourceUrlResolver(url));
			email.setHostName("stmp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("learning.csai@gmail.com","csai@928"));
			email.setSSLOnConnect(true);
			email.setFrom("learning.csai@gmail.com");//Sender
			email.setSubject("Test Result");
			email.setMsg("Please find Attached report....");
			email.addTo("learning.csai@gmail.com");//Receiver
			email.attach(url,"Extent Report","Please Check Report");
			email.send(); //Send Email
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}*/
	  }
}

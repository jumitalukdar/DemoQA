package Test_Scripts;

import org.apache.log4j.Logger;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Config.ObjectReader;
import TestBase.TestBase;
import Test_Pages.LoginPage;
import helper.AssertionHelper;
import helper.LoggerHelper;


public class Login_Test extends TestBase{
	private final Logger log = LoggerHelper.getLogger(Login_Test.class);
	
	LoginPage login;
	
	
	
	@DataProvider(name="testData")
	public Object[][] testData(){
		Object[][] data = getExcelData("testData.xlsx", "loginData");
		return data;
	}
	@BeforeClass
	public void beforeClass(){
		getApplicationUrl(ObjectReader.reader.getUrl());
		login = new LoginPage(driver);
	}
	@Test(dataProvider="testData")
	public void loginTest(String userName, String password, String runMode){
		
		if(runMode.equalsIgnoreCase("n")){
			throw new SkipException("Run mode for this set of data is marked N");
		}
		login.loginToApplication(userName, password);
		boolean status = login.verifySuccessLoginMsg();
		AssertionHelper.updateTestStatus(status);
		login.logout();
	}
}

package core;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import util.GenericFunction;
import Execution.Launcher;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;


public class HooksTest{
	
	private Logger logger = Logger.getLogger(HooksTest.class);
	@Before
	public void setdriver( Scenario scenario){
		HashMap<String, String> time= new HashMap<String, String>();
		SimpleDateFormat  dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		Date date = new Date();
		GenericFunction getBrowserVerison= new GenericFunction();
		String browserName;
		Launcher.ScenarioName=scenario.getName();
		String ScenarioName=Launcher.ScenarioName;
		ScenarioName=ScenarioName.substring(ScenarioName.indexOf("TCID=")+5,ScenarioName.length()-1);
		if(Launcher.dicTestSuite.keySet().contains(ScenarioName)){
			time.put("StartdateTime",dateFormat.format(date));
			BaseClass base= new BaseClass();
			WebDriver driver;
			if(Launcher.dicTestSuite.get(ScenarioName).get("Browser")==null){
				browserName=Launcher.dicConfig.get("strBrowserType");
			}else{
				browserName=Launcher.dicTestSuite.get(ScenarioName).get("Browser");
			}
			base.launchBrowser(browserName);
			driver=base.getDriver();
			Launcher.dicCucumberDriverPool.put(scenario.getName(), driver);
			browserName=browserName+" "+getBrowserVerison.getVersion(driver,false);
			time.put("BrowserDetail", browserName);
			Launcher.dicResult.put(ScenarioName, time);

		}else{
			Assert.assertTrue(false);
		}
		
	}
	
	/**
	 * This function is used to close the driver instance.
	 */
	@After
	public void Quit(Scenario scenario) {
		HashMap<String, WebDriver> driverpool= new HashMap<String, WebDriver>(Launcher.dicDriver);
		GenericFunction reportProcess= new GenericFunction();
		String ScenarioName=scenario.getName();
		SimpleDateFormat  dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		Date date = new Date();	
		WebDriver driver=null;
		
		try{
//			ScenarioName=ScenarioName.substring(ScenarioName.indexOf("TCID=")+5,ScenarioName.indexOf("_",ScenarioName.indexOf("_")+1));
			ScenarioName=ScenarioName.substring(ScenarioName.indexOf("TCID=")+5,ScenarioName.length()-1);
			if(Launcher.dicTestSuite.keySet().contains(ScenarioName)){				
				reportProcess.manageDriverPool(Launcher.dicCucumberDriverPool.get(scenario.getName()));
				driver=Launcher.dicCucumberDriverPool.get(scenario.getName());
				try{
					if(Launcher.dicConfig.get("UseSameDriverSession").equalsIgnoreCase("yes")){
						// Free driver in driver Pool.
						for(String key:driverpool.keySet()){
							if(driverpool.get(key).equals(driver)){
								Launcher.dicDriver.remove(key);					
								Launcher.dicDriver.put(key.replace("Used", "NotUsed"), driver);
								break;
							}
						}
					}else{
						Launcher.dicCucumberDriverPool.get(scenario.getName()).quit();
					}
				}catch(Exception ex){
					
				}
				
				Launcher.dicCucumberDriverPool.remove(scenario.getName());
//				ScenarioName=scenario.getName();
//				ScenarioName=ScenarioName.substring(ScenarioName.indexOf("TCID=")+5,ScenarioName.length()-1);
				Launcher.dicResult.get(ScenarioName).put("TestCaseEndTime", dateFormat.format(date));
				reportProcess.getRawDataForReport(ScenarioName);
				
			}
			
		}catch(Exception ex){
			logger.equals(String.format("Error while closing the driver /n%s",ex.getMessage()));
		}
	}
		
}

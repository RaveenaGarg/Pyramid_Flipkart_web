package Execution;

import io.appium.java_client.service.local.AppiumDriverLocalService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.TestNG;
import org.testng.collections.Lists;

import reporting.Report;
import util.CreateTestNGFile;
import util.ExcelRead;
import util.FileUtil;
import util.ReadProperty;
import util.Slack;import util.mail;

public class Launcher {
	Logger logger = Logger.getLogger(Launcher.class);
	public static HashMap<String, String> dicConfig;
	public static HashMap<String, HashMap<String, String>> dicTestSuite;
	public static HashMap<String, String> dicProjectVar;
	public static HashMap<String, String> dicTFS;
	public static HashMap<String, String> dicCommValues;
	public static HashMap<String, String> dicScreenshot=new HashMap<String, String>();
	public static HashMap<String, HashMap<String,String>> dicResult= new HashMap<String, HashMap<String,String>>();
	public static HashMap<String, HashMap<String,String>> dicSummaryReport= new HashMap<String, HashMap<String,String>>();
	public static HashMap<String, WebDriver> dicCucumberDriverPool= new HashMap<String, WebDriver>(); 
	public static HashMap<String, WebDriver> dicDriver= new HashMap<String, WebDriver>(); 
	public static AppiumDriverLocalService service;
	public static String ScenarioName;
	public static List<String> OrderedTC= new ArrayList<String>();
	
	public static void main(String[] args) {
		SimpleDateFormat  dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
		SimpleDateFormat  dateFormat1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		Date date = new Date();	
		HashMap<String, HashMap<String, String>> dicTestSuiteTemp;
		Report report = new Report(null,null);
		Logger logger = Logger.getLogger(Launcher.class);
		boolean flag=false;
		ExcelRead readData = new ExcelRead();
		// ExcelRead readData= new ExcelRead();
		CreateTestNGFile testng = new CreateTestNGFile();
		ReadProperty prop = new ReadProperty();
		
		// Creating Hasmap of framework-config.properties
		dicProjectVar = prop.propfile("Config/framework-config.properties");
		
		if (dicProjectVar.size() == 0) {
			logger.error("Error while creating hashmap for project data");
			return ;
		}
		logger.info("Reading Config File.");
		
		// Creating Hashmap of config file 
		dicConfig = readData.exlDictionary(dicProjectVar.get("config") + "Config.xlsx","Config", 1, 2);		
		if (dicConfig.size() == 0) {
			logger.error("Error while creating hashmap of Config");
			return ;
		}
		Launcher.dicConfig.put("ProjectStartTime", dateFormat.format(date));
		Launcher.dicConfig.put("ProjectStartDateTime", dateFormat1.format(date));
		//Creating Report Folders,Screenshot folder,deleting TestNG Suite file folder
		report.IntializeProject();
		
		logger.info("Reading TestSuite.");
		
		// Reading Suite(s) file and creating Hashmap
		dicTestSuite = readData.exlReadTestSuite(dicConfig.get("TestSuiteFileName"));
		if (dicTestSuite.size() == 0) {
			logger.error("Error while creating hashmap of TestSuite--No Test case with 'Y' status ");
			return;
		}
		dicTestSuiteTemp=new HashMap<String, HashMap<String,String>>(dicTestSuite);
		logger.info("Creatig testng.xml");
		// Creating TestNG xml
		flag = testng.TestNGFile(dicConfig,dicTestSuite,false);
		readData.UpdateExcel("testuitereset","","");
		logger.info("Reading CommonValues from MasterTestData.");
		// Creating Hashmap of Common values sheet   
		dicCommValues = readData.exlDictionary(dicProjectVar.get("testdata")+ dicConfig.get("MasterTestDataPath"),"CommonValues", 1, 2);
		
		///@ New Report//////////
		Report.InitializeNewReport();	
				///@ New Report///////
		// Execute testng.xml
		try {
			if(flag){
				TestNG testngsuite = new TestNG();
				File file=new File(Launcher.dicProjectVar.get("TestNG"));
				List<String> suites = Lists.newArrayList();
				String files[] = file.list();
				// Adding all xmls to Suite
				for(String suiteFile : files){
					suites.add(Launcher.dicProjectVar.get("TestNG")+suiteFile);
				}
				
				if(suites.size()>=1){
					try {
						testngsuite.setTestSuites(suites);
					    testngsuite.run();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();					}
					// Start execution
			    	
				    date = new Date();	
				    Launcher.dicConfig.put("ProjectEndTime", dateFormat.format(date));
				    Launcher.dicConfig.put("ProjectEndDateTime", dateFormat1.format(date));
			    }else{
			    	logger.error("No testng's xml file found");
			    	flag=false;
			    }
				
				Report.NewReport();
				Report.reportDicProcess();
				report.CreateSummaryReportData();
				
				
			}else{
				logger.error("Issue while creating Project Default configuration");
			}
			
			///@ End New Report/////////
			Report.EndNewReport();
			if(dicConfig.get("SendSummaryMail").equalsIgnoreCase("Yes")){
				mail sendmail= new mail();
				sendmail.sendFromGMail();
			}
			///@ End New Report/////////
		
		
		
		}catch (Exception ex) {
			logger.error(String.format("Problem while executing test cases from testng.xml. Error is %s", ex.getMessage()) );	
			///@ End New Report/////////
			try 
			{
				Report.EndNewReport();
				if(dicConfig.get("SendSummaryMail").equalsIgnoreCase("Yes")){
					mail sendmail= new mail();
					sendmail.sendFromGMail();
				}
				if(dicConfig.get("IsSlackEnable").equalsIgnoreCase("Yes"))
				{
					Slack slack= new Slack();
					slack.sendSlackMessage();
				}			
			} 
			catch (Exception e)
			{
				logger.error(String.format("Error while creating report %s", e.getMessage()) );	
			} 
		///@ End New Report/////////
		}
		// Run failed TCs
		if(dicConfig.get("RunFailedTestSuite").toLowerCase().equals("yes")){
			dicTestSuiteTemp= new HashMap<String, HashMap<String,String>>(dicTestSuite);
			TestNG testngsuite = new TestNG();
			File file=new File(Launcher.dicProjectVar.get("TestNG"));
			List<String> suites = Lists.newArrayList();
			FileUtil futil= new FileUtil();
			logger.info("Executing failed Test case");
			String files[] = file.list();
			futil.createDirectory(Launcher.dicProjectVar.get("TestNG"),true);
			System.out.println(OrderedTC);
			for(String failedTestCase: dicTestSuite.keySet()){
			
				System.out.println(dicResult.get(failedTestCase).get("TestCaseStatus"));
				if(!dicResult.get(failedTestCase).get("TestCaseStatus").equalsIgnoreCase("fail")){
					dicTestSuiteTemp.remove(failedTestCase);
				}
			}
			dicTestSuite=dicTestSuiteTemp;
			if(dicTestSuite.size()>0){
				dicTestSuiteTemp=new HashMap<String, HashMap<String,String>>(dicTestSuite);
				logger.info("Creatig testng.xml");
				// Creating TestNG xml
				flag = testng.TestNGFile(dicConfig,dicTestSuite,true);						
			}
			// Clear Suite.
			suites.clear();	
			testngsuite = new TestNG();
			for(String suiteFile : files){
				suites.add(Launcher.dicProjectVar.get("TestNG")+suiteFile);
			}				    
		    if(suites.size()>=1){
		    	testngsuite.setTestSuites(suites);					    	
			    testngsuite.run();
			    flag=true;
		    }else{
		    	logger.error("No testng's xml file found while executing Testcase in Retry mode");
		    	flag=false;
		    }					
		
		}
		
		if(dicDriver.size()>0){
			OrderedTC.clear();
			for(String key:dicDriver.keySet()){
				OrderedTC.add(key);
			}
			
			for(String mobDriver: OrderedTC){
				try{
					dicDriver.get(mobDriver).quit();
					dicDriver.remove(mobDriver);
				}catch(Exception ex){
					System.out.println(ex.getMessage());
				}
			}
		}
		if(dicConfig.get("SeleniumVariant").equalsIgnoreCase("android")||dicConfig.get("SeleniumVariant").equalsIgnoreCase("ios")){
			service.stop();
			service=null;
		}
	}
		
}

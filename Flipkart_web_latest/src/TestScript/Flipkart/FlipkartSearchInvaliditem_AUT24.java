package TestScript.Flipkart;

import library.WebFunclib;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import Execution.Launcher;

public class FlipkartSearchInvaliditem_AUT24  extends WebFunclib{
	
	String strExpResult="Expected Result";
	boolean flag;	
	
	//########################################################################################################
    //# Test Case ID: 1
    //# Test Case Name: Homedepot script
    //#------------------------------------------------------------------------------------------------------
    //# Description: Checkout product from application
    //#------------------------------------------------------------------------------------------------------
    //# Pre-conditions: NA
    //# Post-conditions: NA
    //# Limitations: NA
    //#------------------------------------------------------------------------------------------------------
    //# Owner:  Himanshu Gosain
    //# Created on: 10-20-2016
    //#------------------------------------------------------------------------------------------------------
    //# Reviewer: 
    //# Review Date: 
    //#------------------------------------------------------------------------------------------------------
    //# History Notes: 
    //########################################################################################################
	
	@Test
	public void Step1(ITestContext testContext) throws InterruptedException{		

		boolean blnStepRC=false;
		
		//Getting data from master test data file           
        String strEmailAddress = Launcher.dicCommValues.get("strEmailAddress1");
        String strPassword = Launcher.dicCommValues.get("strPassword1");
        String strProductName = dicTestData.get("strProductName");
        
        
        //STEP 1
        //Launching the URL
        blnStepRC = LaunchURL("flipkart");
        //Reporting based on the returned value
        if (blnStepRC)
        {
        	report.reportStep("Launch <b>Flipkart </b> application URL <b>'" + Launcher.dicCommValues.get("strApplicationURL") + "'</b>", "<b> Flipkart </b> home page displayed successfully.", "Pass");
        }
        else
        {
            report.reportStep("Launch <b>Flipkart </b> application URL <b>'" + Launcher.dicCommValues.get("strApplicationURL") + "'</b>",ErrDescription, "Fail");
        }

 //STEP 2 Calling function to Login into Flipkart application
         blnStepRC = flipkart_Login(strEmailAddress, strPassword);
        //Reporting based on the returned value
         if (blnStepRC)
         {
             report.reportStep("Login to <b>Flipkart </b> application with valid credentials", "User <b>'" + strEmailAddress + "'</b> is successfully logged into <b>Flipkart </b> application.", "Pass");
         }
         else
         {
         	report.reportStep("Login to <b>Flipkart </b> application with valid credentials	", ErrDescription,"Fail");
         }


        //STEP 3 Calling function to search product on Flipkart application
        blnStepRC = flipkart_SearchInvalidProduct(strProductName);
        //Reporting based on the returned value
        if (blnStepRC)
        {
        	report.reportStep("Search Product <b> " + strProductName + " </b> ", "Product <b>'" + strProductName + "'</b> is displayed.", "Pass");
        }
        else
        {
           report.reportStep("Search Product <b> " + strProductName + " </b> ", "Product <b>'" + strProductName + "'</b> not found in search result.", "Fail");
            //NavigateToHomePage();
           // SignOut();
           // return;
        }  

        //STEP 10 Calling function to sign out from the Flipkart application
        blnStepRC = flipkart_Logout();
        //Reporting based on the returned value
        if (blnStepRC)
        {
           report.reportStep("<b> Sign Out </b> from application", "User is successfully signed out.", "Pass");
        }
        else
        {
           report.reportStep("<b> Sign Out </b> from application", "User failed to Sign Out from Flipkart application", "Fail");
        }
		
		
			
	}


}

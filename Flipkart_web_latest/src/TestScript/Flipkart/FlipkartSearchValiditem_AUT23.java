package TestScript.Flipkart;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import reporting.Report;
import Execution.Launcher;
import library.WebFunclib;

public class FlipkartSearchValiditem_AUT23 extends WebFunclib {
	
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
        String strEmailAddress = Launcher.dicCommValues.get("strEmailAddress");
        String strPassword = Launcher.dicCommValues.get("strPassword");
        String strProductName = dicTestData.get("strProductName");
        String strName = dicTestData.get("strName");
        String strPhoneNmber = dicTestData.get("strPhoneNmber");
        String strPincode = dicTestData.get("strPincode");
        String strLocality = dicTestData.get("strLocality");
        String strAddress = dicTestData.get("strAddress");
        String strCity = dicTestData.get("strCity");
        String strState = dicTestData.get("strState");
        String strAddressType = dicTestData.get("strAddressType");

        
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
            report.reportStep("Launch <b> Flipkart </b> application URL <b>'" + Launcher.dicCommValues.get("strApplicationURL") + "'</b>",ErrDescription, "Fail");
        }

        
        //STEP 2 Calling function to Login into Flipkart application
         blnStepRC = flipkart_Login(strEmailAddress, strPassword);
        //Reporting based on the returned value
        if (blnStepRC)
        {
            report.reportStep("Login to <b> Flipkart </b> application with valid credentials", "User <b>'" + strEmailAddress + "'</b> is successfully logged into Flipkart application.", "Pass");
        }
        else
        {
        	report.reportStep("Login to <b> Flipkart </b> application with valid credentials	", ErrDescription,"Fail");
        }

        //STEP 3 Calling function to search product on Flipkart application
        
       
        blnStepRC = flipkart_SearchProduct(strProductName);
       
        //Reporting based on the returned value
        if (blnStepRC)
        {
        	report.reportStep("Search Product <b> " + strProductName + " </b> ", "Product <b>'" + strProductName + "'</b> is displayed in search result.", "Pass");
        }
        else
        {
           report.reportStep("Search Product <b> " + strProductName + " </b> ", "Product details page for <b>'" + strProductName + "'</b> is not displayed.", "Fail");
            //NavigateToHomePage();
           // SignOut();
           // return;
        }

        //STEP 4 Calling function to add searched item to the cart
        blnStepRC = flipkart_SelectProduct(strProductName);
        //Reporting based on the returned value
        if (blnStepRC)
        {
           report.reportStep("Select desired product from the search result", "<b> Product infromation </b> page is displayed for product <b>'" + strProductName + "'</b> ", "Pass");
        }
        else
        {
           report.reportStep("Select desired product from search results", ErrDescription, "Fail");
            //NavigateToHomePage();
            //SignOut();
           // return;
        }                                                                                

        //STEP 5 Calling function to checkout item from the added to cart screen
        blnStepRC = flipkart_AddToCart();
        //Reporting based on the returned value
        if (blnStepRC)
        {
           report.reportStep("Add the selected product to the <b> Cart </b>", "<b> Shopping Cart </b> page is displayed with selected product.", "Pass");
        }
        else
        {
           report.reportStep("Add the selected product to the <b> Cart </b>", ErrDescription, "Fail");
            //NavigateToHomePage();
            //SignOut();
            //return;
        }
        
        //STEP 5 Calling function to checkout item from the added to cart screen
        blnStepRC = flipkart_PlaceOrder();
        //Reporting based on the returned value
        if (blnStepRC)
        {
           report.reportStep("Place the order from shopping <b> Cart </b>", " <b> Order Summary </b> page is displayed for selected product.", "Pass");
        }
        else
        {
           report.reportStep("Place the order from shopping <b> Cart </b>", ErrDescription, "Fail");
            //NavigateToHomePage();
            //SignOut();
            //return;
        }


        //STEP 6 Calling function which will enter the details required for the order to be delivered
        blnStepRC = flipkart_EnterDeliveryAddress(strName, strPhoneNmber, strPincode, strLocality, strAddress, strCity, strState, strAddressType);
        //Reporting based on the returned value
        if (blnStepRC)
        {
           report.reportStep("Change shipping details on <b> Order Summary </b> page", "Shipping details changed successfully. First Name - <b>" + strName  + "</b> , Address -<b>" + strAddress + "</b>, Phone Number -<b>" + strPhoneNmber + " </b>", "Pass");
        }
        else
        {
           report.reportStep("Change shipping details on <b> Order Summary </b>  page", ErrDescription, "Fail");
            //NavigateToHomePage();
           // SignOut();
            //return;
        }

        //STEP 7 Calling function which will navigate user back to home page
        blnStepRC = flipkart_NavigateToHome();
        //Reporting based on the returned value
        if (blnStepRC)
        {
           report.reportStep("Navigate back to <b> Home </b> page", "User is successfully navigated to <b> Home </b> page.", "Pass");
        }
        else
        {
           report.reportStep("Navigate back to <b> Home </b> page",ErrDescription, "Fail");
        }

        //STEP 8 Calling function which will go to cart and remove the items from the cart
        blnStepRC = flipkart_RemoveItemFromCart();
        //Reporting based on the returned value
        if (blnStepRC)
        {
           report.reportStep("Remove all items from the <b> Cart </b>", "All items are successfully removed from <b> Cart </b>", "Pass");
        }
        else
        {
           report.reportStep("Remove all items from the <b> Cart </b>", ErrDescription, "Fail");
        }

        //STEP 10 Calling function to sign out from the flipkart application
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
package library;

import java.util.ArrayList;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;

import reporting.Report;
import Execution.Launcher;
import core.Action;
import core.BaseClass;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;


public class BDDWeblib extends WebFunclib{
	

    
    Report report;
    boolean blnFlag;
    
    //***********************************************************************************************************
	@Before(order=20000)
	public void get(Scenario scenario){
		String ScenarioName=scenario.getName();
		ScenarioName=ScenarioName.substring(ScenarioName.indexOf("TCID=")+5,ScenarioName.length()-1);
		if(Launcher.dicTestSuite.keySet().contains(ScenarioName)){
			BaseClass base= new BaseClass();
			base.getTestData(ScenarioName+"_1_"+"Browser","");
			dicTestData=base.getDicTestData();
			setDriverForCucumber(scenario.getName());
			Launcher.dicResult.get(ScenarioName).put("BrowserDetail", getBrowserDeatil(Launcher.dicTestSuite.get(ScenarioName).get("Browser")));
			
			report = new Report(browser,ScenarioName);
		}else{
			Assert.assertTrue(false);
		}
	}
	
	//**************************************Home Depot Script*****************************************************
	
	@Given("^User Launches the Flipkart$")
	public void user_Launches_the_Flipkart() throws Throwable {	
				
		blnFlag=LaunchURL("flipkart");
		if (blnFlag)
        {
        	report.reportStep("Launch Flipkart application <b>'" + Launcher.dicCommValues.get("strApplicationURL") + "'</b>", "Flipkart home page displayed successfully.", "Pass");
        }
        else
        {
            report.reportStep("Launch HomeDepot application <b>'" + Launcher.dicCommValues.get("strApplicationURL") + "'</b>",ErrDescription, "Fail");
        }
	}

	@When("^User Enters UserId And Password And Clicks On Submit Button$")
	public void user_Enters_UserId_And_Password_And_Clicks_On_Submit_Button() throws Throwable {

		String strEmailAddress = Launcher.dicCommValues.get("strEmailAddress");
	    String strPassword = Launcher.dicCommValues.get("strPassword");
        blnFlag = flipkart_Login(strEmailAddress,strPassword);				
				
        //Reporting based on the returned value
        if (blnFlag)
        {
            report.reportStep("Login to Flipkart application", "User <b>'" + strEmailAddress + "'</b> is successfully logged into Flipkart application.", "Pass");
        }
        else
        {
        	report.reportStep("Login to Flipkart application", ErrDescription,"Fail");
        }
	}

	
	@When("^User Searches The Valid Product By Entering The Product Name And Clicks On Search Button$")
	public void user_Searches_The_Valid_Product_By_Entering_The_Product_Name_And_Clicks_On_Search_Button() throws Throwable {

		String strProductName = dicTestData.get("strProductName");
		blnFlag = flipkart_SearchProduct(strProductName);
        //Reporting based on the returned value
        if (blnFlag)
        {
        	report.reportStep("Enters the product and click on search button", "Product <b>'" + strProductName + "'</b> is searching.", "Pass");
        }
        else
        {
           report.reportStep("Unable to enter product details", "Product details page for <b>'" + strProductName + "'</b> is not displayed.", "Fail");
        }
	}
	
	
	
	@When("^User Selects The Valid Product By Clicking On The Displayed Product$")
	public void user_Selects_The_Valid_Product_By_Clicking_On_The_Displayed_Product() throws Throwable {

		String strProductName = dicTestData.get("strProductName");
		blnFlag = flipkart_SelectProduct(strProductName);
        //Reporting based on the returned value
        if (blnFlag)
        {
        	report.reportStep("Select the Product", "Product <b>'" + strProductName + "'</b> is selected.", "Pass");
        }
        else
        {
           report.reportStep("Select the Product ", "Product details page for <b>'" + strProductName + "'</b> is not displayed.", "Fail");
        }
	}
	
	
	
	@When("^User Added Searched Item To Cart By Clicking Add To Cart Button$")
	public void user_Added_Searched_Item_To_Cart_By_Clicking_Add_To_Cart_Button() throws Throwable {

		blnFlag = flipkart_AddToCart();
        //Reporting based on the returned value
        if (blnFlag)
        {
           report.reportStep("Clicked on Add To Cart Button", "Clicked on Add To Cart Button successfully", "Pass");
        }
        else
        {
           report.reportStep("Clicked on Add To Cart Button", ErrDescription, "Fail");
        }
	}
	
	
	
	@When("^User place the Order By Clicking on Place Order Button$")
	public void User_place_the_Order_By_Clicking_on_Place_Order_Button() throws Throwable {

		blnFlag = flipkart_PlaceOrder();
        //Reporting based on the returned value
        if (blnFlag)
        {
           report.reportStep("Clicked on Place Order Button", "Clicked on Place Order Button successfully.", "Pass");
        }
        else
        {
           report.reportStep("Clicked on Place Order Button", ErrDescription, "Fail");
        }
	}
	
	
	
	@When("^User Enters Delivery Address And Clicks On Update Button$")
	public void user_Enters_Delivery_Address_And_Clicks_On_Update_Button() throws Throwable {

		    String strName = dicTestData.get("strName");
	        String strPhoneNmber = dicTestData.get("strPhoneNmber");
	        String strPincode = dicTestData.get("strPincode");
	        String strLocality = dicTestData.get("strLocality");
	        String strAddress = dicTestData.get("strAddress");
	        String strCity = dicTestData.get("strCity");
	        String strState = dicTestData.get("strState");
	        String strAddressType = dicTestData.get("strAddressType");
        
        
        blnFlag = flipkart_EnterDeliveryAddress(strName, strPhoneNmber, strPincode, strLocality, strAddress, strCity, strState, strAddressType);
        //Reporting based on the returned value
        if (blnFlag)
        {
           report.reportStep("Enter Delivery address and details for shipping and clicks on Update button", "Delivery Address is entered and Update Button is clicked successfully." , "Pass");
        }
        else
        {
           report.reportStep("Enter Delivery address and details for shipping and clicks on Update button", ErrDescription, "Fail");
        }
	}
	
	
	
	@When("^User Clicks On Home Depot Image$")
	public void user_Clicks_On_Home_Depot_Image() throws Throwable {

		blnFlag = flipkart_NavigateToHome();
        //Reporting based on the returned value
        if (blnFlag)
        {
           report.reportStep("Flipkart Image Clicked", "User is successfully clicked on Flipkart image.", "Pass");
        }
        else
        {
           report.reportStep("Flipkart Image Clicked",ErrDescription, "Fail");
        }
	}
	
	
	
	@When("^User Removes the Item from Cart$")
	public void User_Removes_the_Item_from_Cart() throws Throwable {

		blnFlag = flipkart_RemoveItemFromCart();
        //Reporting based on the returned value
        if (blnFlag)
        {
           report.reportStep("Remove all items added in Cart", "All items are successfully removed from Cart.", "Pass");
        }
        else
        {
           report.reportStep("Remove all items added in Cart", ErrDescription, "Fail");
        }
	}
	
	
	@When("^User Searches The Invalid Product By Entering The Product Name And Clicks On Search Button$")
	public void User_Searches_The_Invalid_Product_By_Entering_The_Product_Name_And_Clicks_On_Search_Button()
	{
		String strProductName = dicTestData.get("strProductName");
		blnFlag = flipkart_SearchInvalidProduct(strProductName);
        //Reporting based on the returned value
        if (blnFlag)
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
	}

	@When("^User Clicks On Sign Out Link$")
	public void user_Clicks_On_Sign_Out_Link() throws Throwable {

		blnFlag = flipkart_Logout();
        //Reporting based on the returned value
        if (blnFlag)
        {
           report.reportStep("Clicked On Sign Out Button", "Clicked on Sign Out Button successfully.", "Pass");
           
        }
        else
        {
           report.reportStep("Clicked On Sign Out Button", ErrDescription, "Fail");
        }
	}
    
	
	
}

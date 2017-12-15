package library;

import java.util.ArrayList;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;

import reporting.Report;
import Execution.Launcher;
import core.Action;
import core.BaseClass;
import cucumber.api.Scenario;
import cucumber.api.java.Before;


public class WebFunclib extends Action{
	
	//''@###########################################################################################################################
    //''@Function ID: 
    //''@Function Name: LaunchURL
    //''@Objective: This Function Open HomeDepot application and verify its Home Page
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Param Name: strURL
    //''@Param Desc: Application URL
    //''@Param Name: strPageName
    //''@Param Desc: Page Name which has to be verified
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Return Desc: 
    //''@     Success - True
    //''@     Failure - False
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Example: blnStatus= LaunchURL("Homedepot.com","Homedepot") OR blnStatus= LaunchAndVerifyHomePage("Homedepot.com")
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Created by[Date]: Himanshu Gosain[20-Oct-2016]
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Reviewed by[Date]: 
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@History Notes: 
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@###########################################################################################################################
	
	
		
	public boolean LaunchURL(String strPageName){
		boolean blnFlag = false;
		System.out.println();
		//Launching browser
		browser.launchApplication(Launcher.dicCommValues.get("strApplicationURL"));

        switch (strPageName.toLowerCase().trim()){
            case "homedepot":
                //Verify SignIn Button is displayed on Homedepot Home Page
                blnFlag = Page("pgeHomeDepot").Element("lnkSignInOrRegister").WaitForElement(100,1);

                break;
            case "flipkart":
                //Verify SignIn Button is displayed on Homedepot Home Page
                blnFlag = Page("pgeLogin").Element("lnkLogin").WaitForElement(100,10);

                break;
            case "":
                //Verify SignIn Button is displayed on Homedepot Home Page
                blnFlag = Page("pgeHomeDepot").Element("lnkSignInOrRegister").WaitForElement(30,1);
                break;
        }
        if (!blnFlag){
            ErrDescription = "Home page is not displayed in the browser.";
        }
        return blnFlag;

    }
	
	public boolean flipkart_Login(String strEmailAddress, String strPassword) throws InterruptedException
	{
		boolean blnFlag=false;
		
		Page("pgeLogin").Element("login_pop").WaitForElement(4,2);
		//handles login pop if it occurs automatically before clicking on login
		blnFlag=Page("pgeLogin").Element("login_pop").isVisible();
		if(blnFlag)
		{
			blnFlag = Page("pgeLogin").Element("txtEmail").WaitForElement(2);
        	//Entering Email address 
			blnFlag = Page("pgeLogin").Element("txtEmail").Type(strEmailAddress);
			if(blnFlag)
			{
				//Entering Password
				blnFlag = Page("pgeLogin").Element("txtPassword").Type(strPassword);
				if(blnFlag)
				{					
					blnFlag = Page("pgeLogin").Element("btnLogin").Click();
					if(blnFlag)
					{						
					  	//Waiting for page to load and checking that user is sign in successfully
					    blnFlag = Page("pgeHome").Element("lnkLoggedIn").WaitForElement(30,1);
					    if(!blnFlag)
					    {
					    	ErrDescription = "Unable to Sign In with valid credentials";
						}
					}       	
					else
				    {
						ErrDescription = "Unable to click on 'Login' button";
				    }						        
				}
		        else
	            {
	        		ErrDescription = "Unable to enter value in Password textbox";
	            }
	        }
			return blnFlag;
		}
		else
		{
		Thread.sleep(200);
		Page("pgeLogin").Element("lnkLogin").WaitForElement(100,10);
		
		
		//Clicking on Sign In link
        blnFlag = Page("pgeLogin").Element("lnkLogin").Click();
        if(blnFlag)
        {
        	//Verifying Sign In button is displayed
	        blnFlag = Page("pgeLogin").Element("btnLogin").WaitForElement(30);
	        if(blnFlag)
	        {
	        	blnFlag = Page("pgeLogin").Element("txtEmail").WaitForElement(2);
	        	//Entering Email address 
				blnFlag = Page("pgeLogin").Element("txtEmail").Type(strEmailAddress);
				if(blnFlag)
				{
					//Entering Password
					blnFlag = Page("pgeLogin").Element("txtPassword").Type(strPassword);
					if(blnFlag)
					{					
						blnFlag = Page("pgeLogin").Element("btnLogin").Click();
						if(blnFlag)
						{						
						  	//Waiting for page to load and checking that user is sign in successfully
						    blnFlag = Page("pgeHome").Element("lnkLoggedIn").WaitForElement(30,1);
						    if(!blnFlag)
						    {
						    	ErrDescription = "Unable to Sign In with valid credentials";
							}
						}       	
						else
					    {
							ErrDescription = "Unable to click on 'Login' button";
					    }						        
					}
			        else
		            {
		        		ErrDescription = "Unable to enter value in Password textbox";
		            }
		        }
		        else
	            {
	        		ErrDescription = "Unable to enter value in Email Address textbox";
	            }
	        }
	        else
            {
        		ErrDescription = "Unable to find Email Address textbox";
            }
        }
    	else
        {
    		ErrDescription = "Unable to click on 'Login' link";
        }
        
        return blnFlag;
		}
	}
	
	
	public boolean flipkart_SearchProduct(String strProductName) throws InterruptedException
	{
		boolean blnFlag = false;
		browser.WaitForSync(2);
		blnFlag = Page("pgeHome").Element("txtSearch").Type(strProductName);
		
		//extra
		/*
		Boolean b=Page("pgeHome").Element("txtSearch").equals(strProductName);
		System.out.println("value of gettext()  "+Page("pgeHome").Element("txtSearch").GetText());
		System.out.println("VALUE OF B = "+b);
		if(b)
			System.out.println("Correct product ");
		else
		{
			//System.out.println("value of b2 is "+b2);
			blnFlag = Page("pgeHome").Element("txtSearch").Clear();
			count++;
			System.out.print(" "+count);
			flipkart_SearchProduct(strProductName);
		}
		System.out.println("when true count= "+count);*/
		//end		
		//if(blnFlag)
		//{
		browser.WaitForSync(3);
		blnFlag = Page("pgeHome").Element("btnSearch").Click();
		browser.WaitForSync(3);
			//if(blnFlag)//to check for search button
			//{
				String strPrdXpath  = browser.GetORProteries("pgeSearch", "lnkProduct", "xpath");
				strPrdXpath = strPrdXpath.replace("PRODUCTNAME", strProductName);
				
				blnFlag = Page("").Element(strPrdXpath).WaitForElement(30,1);
				
				if(!blnFlag)
				{
					ErrDescription = "Search page for product - '" + strProductName + "' is not displayed";
				}
			//}
			//else
			//{
			//	ErrDescription = "Unable to click on Search button";
			//}	
		//}
		//else
		//{
			//ErrDescription = "Unable to enter product name - " + strProductName + " in 'Search' text box";
		//}
		
		
		return blnFlag;
	}
		
	public void scrollUp()
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0,250)", "");
	}
	
	
	public boolean flipkart_SelectProduct(String strProductName)
	{
		boolean blnFlag = false;
		
		scrollUp();
		String strPrdXpath  = browser.GetORProteries("pgeSearch", "lnkProduct", "xpath");
		strPrdXpath = strPrdXpath.replace("PRODUCTNAME", strProductName);
		System.out.println(strPrdXpath);
		browser.WaitForSync(2);
		blnFlag = Page("").Element(strPrdXpath).Click();

		//if product is found
		if (blnFlag)
		{
			browser.WaitForSync(3);
			
		//	ArrayList<String> winTabs = browser.GetWindowTabs();
			

		//  browser.SelectWindowByIndex(1,winTabs);
			System.out.println("window handle!!");
			
			
			 Set<String> handles = driver.getWindowHandles();
			String firstWinHandle = driver.getWindowHandle(); 
			 handles.remove(firstWinHandle);
			  String winHandle=(String) handles.iterator().next();
			 if (winHandle!=firstWinHandle)
			 {
			 String secondWinHandle=winHandle;
			 driver.switchTo().window(secondWinHandle);
			 }
			
		//	browser.WaitForSync(2);
					
			blnFlag = Page("pgeProductDetails").Element("btnAddToCart").WaitForElement(90,1);
			browser.WaitForSync(2);
			
			if(!blnFlag)
			{
				ErrDescription = "Product details page is not displayed for Product - " + strProductName;
			}
		}
		else
		{
			ErrDescription = "Desired product - " + strProductName + " not found in the search";
		}
		
		
		return blnFlag;
	}
	
	
	
	public boolean flipkart_AddToCart()
	{	
				
		boolean blnFlag = false;
		browser.WaitForSync(5);
	
		blnFlag = Page("pgeProductDetails").Element("btnAddToCart").Click();
		if(blnFlag)
		{
			browser.WaitForSync(5);
			blnFlag = Page("pgeShoppingCart").Element("btnPlaceOrder").WaitForElement(120,1);
			if(!blnFlag)
			{
				ErrDescription = "'Shopping Cart' page is not displayed with selected product";
			}
		}
		else
		{
			ErrDescription = "Unable to click on 'Add to Cart' button";
		}
		
		return blnFlag;
	}
	
	
	public boolean flipkart_PlaceOrder()
	{
		
					
		boolean blnFlag = false;
		browser.WaitForSync(6);
		blnFlag = Page("pgeShoppingCart").Element("btnPlaceOrder").JSClick();
		if(blnFlag)
		{
			browser.WaitForSync(4);
			blnFlag = Page("pgeCheckout").Element("lblOrderSummary").WaitForElement(60,1);
			if(!blnFlag)
			{
				ErrDescription = "'Order Summary' page is not displayed";
			}
		}
		else
		{
			ErrDescription = "Unable to click on 'Place Order' button";
		}
		
		return blnFlag;
	}
	
	
	public boolean flipkart_EnterDeliveryAddress(String strName, String strPhoneNmber, String strPincode, String strLocality, String strAddress, String strCity, String strState, String strAddressType)
	{
		boolean blnFlag = false;   
			browser.WaitForSync(2);
			
				blnFlag = Page("pgeCheckout").Element("btnEDIT").WaitForElement(30,1);
				if(blnFlag)
				{
					browser.WaitForSync(2);
					blnFlag = Page("pgeCheckout").Element("btnEDIT").Click();
					
					if(blnFlag)
					{
						blnFlag = Page("pgeCheckout").Element("txtName").WaitForElement(30,1);
					}
				}
			
			else
			{
				ErrDescription = "Unable to click on 'Change' button";
			}	
		
		
		
		blnFlag = Page("pgeCheckout").Element("txtName").Type(strName);
		if(blnFlag)
		{
			blnFlag = Page("pgeCheckout").Element("txtPhone").Type(strPhoneNmber);
			if(blnFlag)
			{
				blnFlag = Page("pgeCheckout").Element("txtPincode").Type(strPincode);
				if(blnFlag)
				{
					blnFlag = Page("pgeCheckout").Element("txtLocality").Type(strLocality);
					if(blnFlag)
					{   browser.WaitForSync(2);
						blnFlag = Page("pgeCheckout").Element("txtAddress").Type(strAddress);
						if(blnFlag)
						{
							blnFlag = Page("pgeCheckout").Element("txtCity").Type(strCity);
							if(blnFlag)
							{
								//blnFlag = Page("pgeCheckout").Element("txtState").Type(strCity);
								//if(blnFlag)
								//{
								browser.WaitForSync(4);
									if (strAddressType.contains("Work"))
									{   
										
										blnFlag = Page("pgeCheckout").Element("rdoWork").Click();
										if(blnFlag)
										{   
											browser.WaitForSync(5);
											blnFlag = Page("pgeCheckout").Element("btnSaveandDeliverHere").Click();
											if(blnFlag)
											{
												browser.WaitForSync(3);
											}
											else
											{
												ErrDescription = "Unable to select Address Type - " + strAddressType;
											}
										}
									}
								//}
								//else
								//{
								//	ErrDescription = "Unable to enter state - " + strState + " in 'State' field";
								//}
							}
							else
							{
								ErrDescription = "Unable to enter city - " + strCity + " in 'City' field";
							}
						}
						else
						{
							ErrDescription = "Unable to enter address - " + strAddress + " in 'Address' field";
						}
						
					}
					else
					{
						ErrDescription = "Unable to enter locality - " + strLocality + " in 'Locality' field";
					}
				}
				else
				{
					ErrDescription = "Unable to enter pincode - " + strPincode + " in 'Pincode' field";
				}
			}
			else
			{
				ErrDescription = "Unable to enter phone - " + strPhoneNmber + " in 'Phone' field";
			}
			
		}
		else
		{
			ErrDescription = "Unable to enter name - " + strName + " in 'Name' field";
		}
		
		return blnFlag;
	}
	
	
	public boolean flipkart_NavigateToHome()
	{
		boolean blnFlag = false;
		browser.WaitForSync(5);
		blnFlag = Page("pgeCommon").Element("lnkHome").Click();
		if(blnFlag)
		{
			blnFlag = Page("pgeHome").Element("btnSearch").WaitForElement(40,1);
			if(!blnFlag)
			{
				ErrDescription = "'Home' page is not displayed";
			}
		}
		else
		{
			ErrDescription = "Unable to click on 'Flipkart home' image";
		}
		
		return blnFlag;
	}
	
	public boolean flipkart_RemoveItemFromCart()
	{
		boolean blnFlag = false;
		
		blnFlag = Page("pgeHome").Element("lnkCart").Click();
		if(blnFlag)
		{
			    Page("pgeShoppingCart").Element("lnkRemove").WaitForElement(100, 2);
				//browser.WaitForSync(4);
				blnFlag = Page("pgeShoppingCart").Element("lnkRemove").Click();
				if (blnFlag)
				{
					blnFlag = Page("pgeShoppingCart").Element("lnkShoppingCartEmpty").WaitForElement(60,1);
					if(!blnFlag)
					{
						ErrDescription = "Unable to empty shopping cart";
					}
				}
				else
				{
					ErrDescription = "Unable to click on 'Remove' button";
				}
			
			
		}
		else
		{
			ErrDescription = "Unable to click on 'Cart'";
		}
		
		return blnFlag;
	}
	
	
	public boolean flipkart_Logout() throws InterruptedException 
	{
		
		boolean blnFlag = false;
	
		blnFlag = true;
		int i = 0;
		while(blnFlag)
		{
			Page("pgeHome").Element("lnkLoggedIn").WaitForElement(90, 1);
			browser.WaitForSync(3);
			Page("pgeHome").Element("lnkLoggedIn").MouseHover();
//			browser.WaitForSync(2);
//			Thread.sleep(2000);
			Page("pgeLogin").Element("lnkLogout").WaitForElement(90, 1);
			browser.WaitForSync(3);
			blnFlag = Page("pgeLogin").Element("lnkLogout").JSClick();
			if(blnFlag || i == 5)
				break;
			i++;
		}
		//blnFlag = Page("pgeHome").Element("lnkLoggedIn").MouseHover();
		
				
		if(blnFlag)
		{
			browser.WaitForSync(5);
			//blnFlag = Page("pgeLogin").Element("lnkLogout").WaitForElement(60,1);
			if(blnFlag)
			{
				//blnFlag =Page("pgeLogin").Element("lnkLogout").Click();
				if(blnFlag)
				{
					browser.WaitForSync(2);
					blnFlag = Page("pgeLogin").Element("lnkLogin").WaitForElement(90,1);
					if(!blnFlag)
					{
						ErrDescription = "Unable to logout from the application";
					}
				}
				else
				{
					ErrDescription = "Unable to click on 'Logout'";
				}
				
			}
			else
			{
				ErrDescription = "Flyout for 'Logout' is not displayed";
			}
		}
		else
		{
			ErrDescription = "Unable to move mouse logged in user name";
		}
		
		return blnFlag;
	}
	
	
	public boolean flipkart_SearchInvalidProduct(String strProductName)
	{
		boolean blnFlag = false;
		
		blnFlag = Page("pgeHome").Element("txtSearch").Type(strProductName);
		if(blnFlag)
		{   browser.WaitForSync(4);
			blnFlag = Page("pgeHome").Element("btnSearch").JSClick();
			
			if(blnFlag)
			{
				browser.WaitForSync(4);		
				blnFlag = Page("pgeSearch").Element("lnlNoResult").WaitForElement(60,1);
				
				if(blnFlag)
				{
					blnFlag = false;
					ErrDescription = "Search page for product - '" + strProductName + "' is not displayed";
				}
			}
			else
			{
				ErrDescription = "Unable to click on Search button";
			}	
		}
		else
		{
			ErrDescription = "Unable to enter product name - " + strProductName + " in 'Search' text box";
		}
		
		
		return blnFlag;
	}
	
	//''@###########################################################################################################################
    //''@Function ID: 
    //''@Function Name: LoginToHomeDepot
    //''@Objective: This Function will login to HomeDepot application
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Param Name: strEmailAddress
    //''@Param Desc: Registered Email address
    //''@Param Name: strPassword
    //''@Param Desc: Password
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Return Desc: 
    //''@     Success - True
    //''@     Failure - False
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Example: blnStatus= LoginToHomeDepot("test@pyramid.com","Password")
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Created by[Date]: Himanshu Gosain[20-Oct-2016]
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Reviewed by[Date]: 
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@History Notes: 
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@###########################################################################################################################
	public boolean LoginToHomeDepot(String strEmailAddress, String strPassword){
		boolean blnFlag = false;

		//Handling promotional popup
		PromotionalPopUp();
		//Clicking on Sign In link
        blnFlag = Page("pgeHomeDepot").Element("lnkSignInOrRegister").Click();
        if(blnFlag)
        {
        	PromotionalPopUp();
        	//Verifying Sign In button is displayed
	        blnFlag = Page("pgeHomeDepot").Element("lnkSignIn").WaitForElement(30);
	        if(blnFlag)
	        {
		        //Clicking on Sign In button
	        	browser.WaitForSync(2);
	        	blnFlag = Page("pgeHomeDepot").Element("lnkSignIn").Click();
	        	if(blnFlag)
		        {	
			        //Waiting for pop up to open to enter email address and password to login
	        		browser.WaitForSync(2);
			        blnFlag = Page("pgeHomeDepot").Element("txtEmailAddress").WaitForElement(30);
			        if(blnFlag)
			        {			
				        //Entering Email address 
				        blnFlag = Page("pgeHomeDepot").Element("txtEmailAddress").Type(strEmailAddress);
				        if(blnFlag)
				        {
				        	//Entering Password
					        blnFlag = Page("pgeHomeDepot").Element("txtPassword").Type(strPassword);
					        if(blnFlag)
					        {					
					        	//Clicking on SignIn button
					        	browser.WaitForSync(1);
						        blnFlag = Page("pgeHomeDepot").Element("btnSignIn").Click();
						        if(blnFlag)
						        {						
						        	browser.WaitForSync(4);
						        	blnFlag = Page("pgeHomeDepot").Element("lnkSignInOrRegister").Click();
						        	if(blnFlag)
						        	{
								        //Waiting for page to load and checking that user is sign in successfully
								        blnFlag = Page("pgeHomeDepot").Element("lnkSignOut").WaitForElement(10,1);
								        if(blnFlag)
								        {
								        	blnFlag = Page("pgeHomeDepot").Element("lnkSignInOrRegister").Click();
								        }
								        else
								        {
								        	ErrDescription = "Unable to Sign In";
								        }
						        	}
						        	else
						        	{
						        		ErrDescription="Unable to click on My Account";
						        	}
						        }
						        else
					            {
					        		ErrDescription = "Unable to click on Sign In button";
					            }						        
					        }
					        else
				            {
				        		ErrDescription = "Unable to enter value in Password textbox";
				            }
				        }
				        else
			            {
			        		ErrDescription = "Unable to enter value in Email Address textbox";
			            }
			        }
			        else
		            {
		        		ErrDescription = "Unable to find Email Address textbox";
		            }
		        }
	        	else
	            {
	        		ErrDescription = "Unable to click on Sign In link";
	            }
	        }
	        else
	        {
	        	ErrDescription = "Unable to find Sign In link";
	        }
        }
        else
        {
        	ErrDescription = "Unable to click on Sign In Or Register button";
        }
        
        return blnFlag;
    }
	
	
	
	//''@###########################################################################################################################
    //''@Function ID: 
    //''@Function Name: SearchProduct
    //''@Objective: This Function will search for the product to buy
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Param Name: strProductName
    //''@Param Desc: Name of the product to buy
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Return Desc: 
    //''@     Success - True
    //''@     Failure - False
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Example: blnStatus= SearchProduct("Referigerator")
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Created by[Date]: Himanshu Gosain[20-Oct-2016]
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Reviewed by[Date]: 
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@History Notes: 
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@###########################################################################################################################
	public boolean SearchProduct(String strProductName)
    {
		boolean blnFlag = false;

        //@ Handle No Thanks Popup
        NoThanksPopUp();

        //Verifying Search textbox is displayed
        blnFlag = Page("pgeHomeDepot").Element("txtKeyword").WaitForElement(30,1);
        if(blnFlag)
        {   
        	//Entering item to be searched in search textbox
	        blnFlag = Page("pgeHomeDepot").Element("txtKeyword").Type(strProductName);
	        if(blnFlag)
	        {
	        	browser.WaitForSync(2);
	        	//Click on Search button
		        blnFlag = Page("pgeHomeDepot").Element("btnSearch").Click();
		        browser.WaitForSync(3);
		        NoThanksPopUp();
		        if(blnFlag)
		        {
		        	//@ Handle No Thanks Popup
		        	//Waiting for the searched product to be displayed on screen
		        	browser.WaitForSync(2);
		        	blnFlag = Page("pgeSearchResult").Element("btnAddToCart").Exist();
		        	NoThanksPopUp();
		        	if(!blnFlag)
		        	{
		        		ErrDescription = "Unable to find the Searched Item";
		        	}
		        }
		        else
		        {
		        	ErrDescription = "Unable to click on Search button";
		        }
	        }
	        else
	        {
	        	ErrDescription = "Unable to enter item in Search textbox";
	        }
        }
        else
        {
        	ErrDescription = "Unable to find Search textbox";
        }

        return blnFlag;
    }
	

	//''@###########################################################################################################################
    //''@Function ID: 
    //''@Function Name: AddToCart
    //''@Objective: This Function will add the product to Cart
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Param Name: NA
    //''@Param Desc: NA
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Return Desc: 
    //''@     Success - True
    //''@     Failure - False
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Example: blnStatus= AddToCart()
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Created by[Date]: Himanshu Gosain[20-Oct-2016]
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Reviewed by[Date]: 
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@History Notes: 
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@###########################################################################################################################
	public boolean AddToCart()
    {
		boolean blnFlag = false;

        //@ Handle No Thanks Popup
        NoThanksPopUp();
        
        //Clicking on add to cart button
        browser.WaitForSync(4);
        blnFlag = Page("pgeSearchResult").Element("btnAddToCart").JSClick();
        if(blnFlag)
        {
	        //@ Handle No Thanks Popup
	        NoThanksPopUp();
	
	        //Waiting for Checkout Now Added to Cart button to be displayed
	        browser.WaitForSync(4);
	        blnFlag = Page("pgeSearchResult").Element("btnCheckoutNowAddedToCart").Exist();
	        if(!blnFlag)
	        	ErrDescription = "Unable to find Added To Cart screen";
        }
        else
        {
        	ErrDescription = "Unable to click on Add To Cart button";
        }

        return blnFlag;
    }
	
	//''@###########################################################################################################################
    //''@Function ID: 
    //''@Function Name: CheckoutFromAddedToCart
    //''@Objective: This Function will checkout the item from added to cart screen
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Param Name: NA
    //''@Param Desc: NA
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Return Desc: 
    //''@     Success - True
    //''@     Failure - False
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Example: blnStatus= CheckoutFromAddedToCart()
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Created by[Date]: Himanshu Gosain[20-Oct-2016]
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Reviewed by[Date]: 
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@History Notes: 
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@###########################################################################################################################
    
	public boolean CheckoutFromAddedToCart()
    {
    	boolean blnFlag = false;

        //@ Handle No Thanks Popup
        NoThanksPopUp();

        //Clicking on checkout now button on Add to cart page
        blnFlag = Page("pgeSearchResult").Element("btnCheckoutNowAddedToCart").Click();
        if(blnFlag)
        {
	        //@ Handle No Thanks Popup
	        NoThanksPopUp();
	
	        //Waiting for Edit Address link
	        blnFlag = Page("pgeDeliveryAddressandCalendar").Element("lnkUseAnotherAddress").WaitForElement(30);
	        if(!blnFlag)
	        	ErrDescription = "Unable to find Edit This Address link";	        
        }
        else
        {
        	ErrDescription = "Unable to click on Checkout button";
        }

        return blnFlag;
    }


    //''@###########################################################################################################################
    //''@Function ID: 
    //''@Function Name: EnterDeliveryAddress
    //''@Objective: This Function will enter details on delivery address page
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Param Name: NA
    //''@Param Desc: NA
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Return Desc: 
    //''@     Success - True
    //''@     Failure - False
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Example: blnStatus= EnterDeliveryAddress("John","Bickel","Stree1","1234567890")
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Created by[Date]: Himanshu Gosain[20-Oct-2016]
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Reviewed by[Date]: 
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@History Notes: 
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@###########################################################################################################################
    public boolean EnterDeliveryAddress(String strFirstName, String strLastName, String strAddress1, String intPhoneNumber)
    {
    	boolean blnFlag = false;

        //@ Handle No Thanks Popup
        NoThanksPopUp();

        //Checking if editthisaddress link appears, if yes clicking on the link
        blnFlag = Page("pgeDeliveryAddressandCalendar").Element("lnkUseAnotherAddress").WaitForElement(30);
        
        if(blnFlag)
        {
        	//Verifying existence of Edit This Address link on the page
	        blnFlag = Page("pgeDeliveryAddressandCalendar").Element("lnkUseAnotherAddress").Exist();
	        if (blnFlag)
	        {
	            //Clicking on editthisaddress link and waiting for form to be displayed
	        	blnFlag = Page("pgeDeliveryAddressandCalendar").Element("lnkUseAnotherAddress").Click();
	        	NoThanksPopUp();
	        	if(blnFlag)
	        	{
	        		//Checking if lnkEditAddress link appears, if yes clicking on the link
	                blnFlag = Page("pgeDeliveryAddressandCalendar").Element("lnkEditAddress").WaitForElement(30);
	                if(blnFlag)
	                {
	                	//Clicking on editthisaddress link and waiting for form to be displayed
	    	        	blnFlag = Page("pgeDeliveryAddressandCalendar").Element("lnkEditAddress").Click();
	    	        	if(blnFlag)
	    	        	{
	    	        		//Waiting for First Name field to appear on the screen
				        	blnFlag = Page("pgeDeliveryAddressandCalendar").Element("txtFirstName").WaitForElement(30);
				            if(blnFlag)
				            {
					            //Entering First Name
					        	blnFlag = Page("pgeDeliveryAddressandCalendar").Element("txtFirstName").Type(strFirstName);
					        	if(blnFlag)
					        	{
					        		//Entering Last Name
						        	blnFlag = Page("pgeDeliveryAddressandCalendar").Element("txtLastName").Type(strLastName);
						        	if(blnFlag)
						        	{
						        		//Entering Phone Number
						        		browser.WaitForSync(1);
							        	blnFlag = Page("pgeDeliveryAddressandCalendar").Element("txtPhone").Type(intPhoneNumber);
							        	if(blnFlag)
							        	{
							        		//Entering Address 1
							        		browser.WaitForSync(1);
							        		blnFlag = Page("pgeDeliveryAddressandCalendar").Element("txtAddress1").Type(strAddress1);
								        	if(blnFlag)
								        	{
								        		//Verifying visibility of Done button
								        		browser.WaitForSync(4);
								        		blnFlag = Page("pgeDeliveryAddressandCalendar").Element("lnkDone").WaitForElement(60);
								        		if(blnFlag)
								        		{
										        	blnFlag = Page("pgeDeliveryAddressandCalendar").Element("lnkDone").Click();
										        	if(blnFlag)
										        	{
										        		//Verify Updated Address Text Is Displayed
										        		browser.WaitForSync(2);
										                blnFlag = Page("pgeDeliveryAddressandCalendar").Element("eleConfirmAddress").WaitForElement(30);
										                if(blnFlag)
										            	{
										                	browser.WaitForSync(2);
										                	//Clicking on continue button
										                    String strConfirmAddress = Page("pgeDeliveryAddressandCalendar").Element("eleConfirmAddress").GetText();
										                    if(strConfirmAddress.contains(strFirstName) && strConfirmAddress.contains(strLastName))
										                    {
										                    	blnFlag=true;
										                    }
										                    else
										                    {
										                    	blnFlag=false;
										                    	ErrDescription="Unable to update the new address";
										                    }
										            	}
										                else
										            	{
										            		ErrDescription = "Unable to find Confirm Address Label";
										            	}
										        	}
										        	else
										        	{
										        		ErrDescription = "Unable to click on Update button";
										        	}
								        		}
								        		else
								        		{
								        			ErrDescription="Unable to find Done link";
								        		}
								        	}
								        	else
								        	{
								        		ErrDescription = "Unable to enter value in Address field";
								        	}
							        	}
							        	else
							        	{
							        		ErrDescription = "Unable to enter value in Phone field";
							        	}
						        	}
						        	else
						        	{
						        		ErrDescription = "Unable to enter value in Last Name field";
						        	}
					        	}
					        	else
					        	{
					        		ErrDescription = "Unable to enter value in First Name field";
					        	}
				            }
				            else
				            {
				            	ErrDescription= "Unable to find First Name textbox";
				            }
	    	        	}
	    	        	else
			            {
			            	ErrDescription= "Unable to click Edit Link";
			            }
	                }
	                else
	                {
	                	ErrDescription= "Unable to find Edit Link";
	                }	                
	        	}
	        	else
	        	{
	        		ErrDescription= "Unable to click on Use Another Address link";
	        	}
	        }
	        else
	        {
	        	ErrDescription = "Use Another Address link doest not exists";
	        }
        }
        else
        {
        	ErrDescription = "Unable to find Use Another Address link";
        }

        return blnFlag;
    }


    //''@###########################################################################################################################
    //''@Function ID: 
    //''@Function Name: NavigateToHomePage
    //''@Objective: This Function will navigate to the home page
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Param Name: NA
    //''@Param Desc: NA
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Return Desc: 
    //''@     Success - True
    //''@     Failure - False
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Example: blnStatus= NavigateToHomePage()
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Created by[Date]: Himanshu Gosain[20-Oct-2016]
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Reviewed by[Date]: 
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@History Notes: 
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@###########################################################################################################################
    public boolean NavigateToHomePage()
    {
    	boolean blnFlag = false;

        //@ Handle No Thanks Popup
        NoThanksPopUp();
        
        //Verifying if Home depot icon is visible on the screen
        blnFlag = Page("pgeHomeDepot").Element("imgHomeDepotHome").isVisible();
        if(blnFlag)
        {
        	//Clicking Home Page image to navigate to Home Page
	        blnFlag = Page("pgeHomeDepot").Element("imgHomeDepotHome").Click();
	        if(blnFlag)
	        {
		        //Waiting for Cart link/button on the page
	        	browser.WaitForSync(3);
		        blnFlag = Page("pgeHomeDepot").Element("lnkCart").WaitForElement(30);
		        if (!blnFlag)
		            blnFlag = Page("pgeHomeDepot").Element("btnCart").WaitForElement(30);
		        
		        if(!blnFlag)
		        	ErrDescription = "Unable to find Cart link or button";
	        }
	        else
	        {
	        	ErrDescription = "Unable to click on Home Image";
	        }
	        
        }
        else
        {
        	//Waiting for Home Depot Image on the page
        	blnFlag = Page("pgeHomeDepot").Element("imgHomeDepotMain").WaitForElement(30);
            if(blnFlag)
            {
            	//Clicking Home Page image to navigate to Home Page
    	        blnFlag = Page("pgeHomeDepot").Element("imgHomeDepotMain").Click();
    	        if(blnFlag)
    	        {
    		        //Waiting and checking for home page to open
    	        	browser.WaitForSync(3);
    		        blnFlag = Page("pgeHomeDepot").Element("lnkCart").WaitForElement(30);
    		        if (!blnFlag)
    		            blnFlag = Page("pgeHomeDepot").Element("btnCart").WaitForElement(30);
    		        
    		        if(!blnFlag)
    		        	ErrDescription = "Unable to find Cart link or button";
    	        }
    	        else
    	        {
    	        	ErrDescription = "Unable to click on Home Image";
    	        }
            }
            else
            {
            	ErrDescription = "Unable to find Home Image";
            }
        }

        return blnFlag;
    }


    //''@###########################################################################################################################
    //''@Function ID: 
    //''@Function Name: EmptyCart
    //''@Objective: This Function will remove the items from Cart
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Param Name: NA
    //''@Param Desc: NA
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Return Desc: 
    //''@     Success - True
    //''@     Failure - False
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Example: blnStatus= EmptyCart()
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Created by[Date]: Himanshu Gosain[20-Oct-2016]
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Reviewed by[Date]: 
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@History Notes: 
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@###########################################################################################################################
    
    public boolean EmptyCart()
    {
    	boolean blnFlag = false;

    	NoThanksPopUp();
        //Clicking on link to Cart 
        blnFlag =  Page("pgeHomeDepot").Element("lnkCart").Click();
        browser.WaitForSync(4);
        if(blnFlag)
        {
	        //Waiting for Remove link to be displayed on the page
	        boolean temp = Page("pgeSearchResult").Element("lnkRemove").WaitForElement(30);
	        if (temp)
	        {
	            //Clicking on Remove link
	        	blnFlag= Page("pgeSearchResult").Element("lnkRemove").Click();
	        }
//	        else
//	        {
//	            //Clicking on Delete link
//	        	blnFlag = Page("NoPage").Element("btnDelete").Click();
//	        }
	
	        if(blnFlag)
	        {
		        //Waiting for Empty Cart element to appear on the page
		        blnFlag = Page("pgeSearchResult").Element("eleTheShoppingCartIsEmpty.").WaitForElement(30);
	        }
	        else
	        {
	        	ErrDescription="Unable to empty the cart";
	        }
        }
        else
        {
        	ErrDescription = "Unable to click on Cart link";
        }

        return blnFlag;
    }


    //''@###########################################################################################################################
    //''@Function ID: 
    //''@Function Name: SignOut
    //''@Objective: This Function will Sign Out the user
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Param Name: NA
    //''@Param Desc: NA
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Return Desc: 
    //''@     Success - True
    //''@     Failure - False
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Example: blnStatus= SignOut()
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Created by[Date]: Himanshu Gosain[20-Oct-2016]
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Reviewed by[Date]: 
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@History Notes: 
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@###########################################################################################################################
    public boolean SignOut()
    {
    	boolean blnFlag = false;

    	//Verify thanks popup
    	NoThanksPopUp();
    	//Waiting for My Account to appear on the page
    	browser.WaitForSync(5);
    	blnFlag = Page("pgeHomeDepot").Element("lnkSignInOrRegister").WaitForElement(60);
    	if(blnFlag)
    	{
    		//Clicking on My Account link.
	    	blnFlag = Page("pgeHomeDepot").Element("lnkSignInOrRegister").Click();
	    	if(blnFlag)
	    	{	  
	    		browser.WaitForSync(3);
	    		//Verifying Sign Out link is displayed.
		    	blnFlag = Page("pgeHomeDepot").Element("lnkSignOut").WaitForElement(60,1);
		    	if(blnFlag)
		    	{
		    		//Verifying existence of Sign Out linkon the page.
			    	blnFlag = Page("pgeHomeDepot").Element("lnkSignOut").Exist();
			        if (blnFlag)
			        {
			        	//Clicking Sign Out link.
			        	blnFlag = Page("pgeHomeDepot").Element("lnkSignOut").Click();
			        	if (blnFlag)
				        {
				            //Verifying that user is signed out successfully.
			        		browser.WaitForSync(2);
					        blnFlag = Page("pgeHomeDepot").Element("lnkSignInOrRegister").WaitForElement(30);
					        if(!blnFlag)
					        	ErrDescription = "Unable to find Sign In Or Register link";
				        }
			        	else
				    	{
				    		ErrDescription = "Unable to click on Sign Out link";
				    	}
			        }
			        else
			    	{
			    		ErrDescription = "Sign Out link does not exists";
			    	}
		    	}
		    	else
		    	{
		    		ErrDescription = "Unable to find Sign Out link";
		    	}
	    	}
	    	else
	    	{
	    		ErrDescription = "Unable to click on My Account Menu";
	    	}
    	}
    	else
    	{
    		ErrDescription = "Unable to find My Account Menu";
    	}

        return blnFlag;
    }

    //''@###########################################################################################################################
    //''@Function ID: 
    //''@Function Name: NoThanksPopUp
    //''@Objective: This Function will handle the thanks popup
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Param Name: NA
    //''@Param Desc: NA
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Return Desc: 
    //''@     Success - True
    //''@     Failure - False
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Example: blnStatus= NoThanksPopUp()
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Created by[Date]: Himanshu Gosain[20-Oct-2016]
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Reviewed by[Date]: 
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@History Notes: 
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@###########################################################################################################################
    public void NoThanksPopUp()
    {
    	//Verifying No Thanks popup is displayed
    	//if (Page("pgeHomeDepot").Element("lnkNoThanks").WaitForElement(1,3))
    	if (Page("pgeHomeDepot").Element("lnkNoThanks").isVisible())
        {
    		//Clicking on No Thanks option on PopUp
            Page("pgeHomeDepot").Element("lnkNoThanks").Click();
        }
    }
	
  //''@###########################################################################################################################
    //''@Function ID: 
    //''@Function Name: PromotionalPopUp
    //''@Objective: This Function will handle the promotional popup
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Param Name: NA
    //''@Param Desc: NA
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Return Desc: 
    //''@     Success - True
    //''@     Failure - False
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Example: blnStatus= PromotionalPopUp()
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Created by[Date]: Himanshu Gosain[20-Oct-2016]
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Reviewed by[Date]: 
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@History Notes: 
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@###########################################################################################################################
    public void PromotionalPopUp()
    {
    	//Verifying No Thanks popup is displayed
    	//if (Page("pgeHomeDepot").Element("lnkNoThanks").WaitForElement(1,3))
    	if (Page("pgeHomeDepot").Element("lnkPromotionalPopUp").isVisible())
        {
    		//Clicking on No Thanks option on PopUp
            Page("pgeHomeDepot").Element("lnkPromotionalPopUp").Click();
        }
    }
	
}

package library;

import core.MobileAction;
import Execution.Launcher;

public class MobileFunclib extends MobileAction{

	public boolean m_Flipkart_LaunchURL(String strPageName)
	{
		boolean blnFlag = false;
		
		//Launching browser
		blnFlag = mbrowser.LaunchApplication(Launcher.dicCommValues.get("strApplicationURL"));
		
		if(blnFlag)
		{
	        switch (strPageName.toLowerCase().trim()){
	            case "flipkart":
	                //Verify SignIn Button is displayed on Homedepot Home MobPage
	            	blnFlag = MobPage("pgeHome").MobElement("lnkMyAccount").WaitForElement(30);
	
	                break;
	            case "":
	                //Verify SignIn Button is displayed on Homedepot Home MobPage
	            	blnFlag = MobPage("pgeHomeDepot").MobElement("imgHomeDepotHome").WaitForElement(30);
	                break;
	        }
		}
        if (!blnFlag){
            ErrDescription = "Home MobPage is not displayed in Browser.";
        }
        return blnFlag;

    }
	
	public boolean m_Flipkart_Login(String strEmail, String strPassword)
	{
		boolean blnFlag = false;
		
		blnFlag = MobPage("pgeHome").MobElement("lnkMyAccount").Click();
         if(blnFlag)
         {
         	//Clicking on My Account link
         	blnFlag = MobPage("pgeLogin").MobElement("btnSignIn").WaitForElement(30);  
         	if(blnFlag)	
         	{
         		blnFlag = MobPage("pgeLogin").MobElement("txtEmail").Type(strEmail);
         		if (blnFlag)
         		{
         			blnFlag = MobPage("pgeLogin").MobElement("txtPassword").Type(strPassword);
         			if (blnFlag)
         			{
         				blnFlag = MobPage("pgeLogin").MobElement("btnSignIn").Click();
         				if (blnFlag)
         				{
         					blnFlag = MobPage("pgeHome").MobElement("txtSearch").WaitForElement(30);
         					if(!blnFlag)
         					{
         						ErrDescription = "'Home' page is not displayed after signing in" ;
         					}
         				}
         				else
         				{
         					ErrDescription = "Unable to click on 'Sign In' button" ;
         				}
         			}
         			else
         			{
         				ErrDescription = "Unable to enter password - " + strPassword + " in 'Password' field" ;
         			}
         		}
         		else
         		{
         			ErrDescription = "Unable to enter email address - " + strEmail + " in 'Email' field" ;
         		}
         	}
         	else
         	{
         		ErrDescription = "'Sign In' page is not displayed" ;
         		
         	}
         }
         else
         {
        	 ErrDescription = "'My Account' page is not displayed";
         }
         
         return blnFlag;
         	
	}
	
	
	public boolean m_flipkart_SearchProduct(String strProductName)
	{
		boolean blnFlag = false;
		
		blnFlag = MobPage("pgeHome").MobElement("txtSearch").Type(strProductName);
		if(blnFlag)
		{
			blnFlag = MobPage("pgeHome").MobElement("").Type("ENTER");
			if(blnFlag)
			{
				String strPrdXpath  = mbrowser.GetXpath("pgeSearch", "lnkProduct");
				strPrdXpath = strPrdXpath.replace("PRODUCTNAME", strProductName);
				
				blnFlag = MobPage("").MobElement(strPrdXpath).WaitForElement(30);
				
				if(!blnFlag)
				{
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
	
	public boolean m_flipkart_SelectProduct(String strProductName)
	{
		boolean blnFlag = false;
		
		//String strPrdXpath = Page("pgeSearch").Element("lnkProduct").GetXpath();
		String strPrdXpath  = mbrowser.GetXpath("pgeSearchResult", "lnkProduct");
		strPrdXpath = strPrdXpath.replace("PRODUCTNAME", strProductName);
		
		blnFlag = MobPage("").MobElement(strPrdXpath).Click();
		
		if (blnFlag)
		{
			mbrowser.WaitForSync(2);
			
			blnFlag = MobPage("pgeProductDetails").MobElement("btnBuyNow").WaitForElement(30);
			
			if(!blnFlag)
			{
				ErrDescription = "Product details page is not displayed for following Product - " + strProductName;
			}
		}
		else
		{
			ErrDescription = "Unable to select following product - " + strProductName;
		}
		
		
		return blnFlag;
	}
	
	
	public boolean m_flipkart_BuyNow()
	{
		boolean blnFlag = false;
		
		blnFlag = MobPage("pgeProductDetails").MobElement("btnBuyNow").Click();
		if(blnFlag)
		{
			mbrowser.WaitForSync(2);
			blnFlag = MobPage("pgeCheckout").MobElement("btnChangeAddress").WaitForElement(30);
			if(!blnFlag)
			{
				ErrDescription = "'Delivery' page is not displayed";
			}
		}
		else
		{
			ErrDescription = "Unable to click on 'Buy Now' button";
		}
		
		return blnFlag;
	}
	
	public boolean m_flipkart_EnterDeliveryAddress(String strName, String strPhoneNmber, String strPincode, String strLocality, String strAddress, String strCity, String strState, String strAddressType)
	{
		boolean blnFlag = false;
		
		blnFlag = MobPage("pgeCheckout").MobElement("btnChangeAddress").WaitForElement(30);
		if(blnFlag)
		{
			blnFlag = MobPage("pgeCheckout").MobElement("btnChangeAddress").Click();
			if(blnFlag)
			{
				blnFlag = MobPage("pgeCheckout").MobElement("btnEDIT").WaitForElement(30);
				if(blnFlag)
				{
					blnFlag = MobPage("pgeCheckout").MobElement("btnEDIT").Click();
					
					if(blnFlag)
					{
						blnFlag = MobPage("pgeCheckout").MobElement("txtName").WaitForElement(30);
					}
				}
			}
			else
			{
				ErrDescription = "Unable to click on 'Change' button";
			}	
		}
		
		
		blnFlag = MobPage("pgeCheckout").MobElement("txtName").Type(strName);
		if(blnFlag)
		{
			blnFlag = MobPage("pgeCheckout").MobElement("txtPhone").Type(strPhoneNmber);
			if(blnFlag)
			{
				blnFlag = MobPage("pgeCheckout").MobElement("txtPincode").Type(strPincode);
				if(blnFlag)
				{
					blnFlag = MobPage("pgeCheckout").MobElement("txtLocality").Type(strLocality);
					if(blnFlag)
					{
						blnFlag = MobPage("pgeCheckout").MobElement("txtAddress").Type(strAddress);
						if(blnFlag)
						{
							blnFlag = MobPage("pgeCheckout").MobElement("txtCity").Type(strCity);
							if(blnFlag)
							{
								//blnFlag = Page("pgeCheckout").Element("txtState").Type(strCity);
								//if(blnFlag)
								//{
									if (strAddressType.contains("Work"))
									{
										blnFlag = MobPage("pgeCheckout").MobElement("rdoWork").Click();
										if(blnFlag)
										{
											blnFlag = MobPage("pgeCheckout").MobElement("btnSave").Click();
											if(blnFlag)
											{
												mbrowser.WaitForSync(2);
												blnFlag = MobPage("pgeCheckout").MobElement("lblOrderSummary").WaitForElement(30);
												if (!blnFlag)
												{
													ErrDescription = "'Order Summary' page is not displayed after saving address";
												}
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
	
	public boolean m_flipkart_NavigateToHome()
	{
		boolean blnFlag = false;
		
		blnFlag = MobPage("pgeCommon").MobElement("lnkHome").Click();
		if(blnFlag)
		{
			blnFlag = MobPage("pgeHome").MobElement("txtSearch").WaitForElement(30);
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
	
	public boolean m_flipkart_RemoveItemFromCart()
	{
		boolean blnFlag = false;
		
		blnFlag = MobPage("pgeHome").MobElement("lnkCart").Click();
		if(blnFlag)
		{
			blnFlag = MobPage("pgeShoppingCart").MobElement("lnkRemove").WaitForElement(30);
			if(blnFlag)
			{
				blnFlag = MobPage("pgeShoppingCart").MobElement("lnkRemove").Click();
				if (blnFlag)
				{
					blnFlag = MobPage("pgeShoppingCart").MobElement("lnkShoppingCartEmpty").WaitForElement(30);
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
				ErrDescription = "'My Cart' page is not displayed";
			}
		}
		else
		{
			ErrDescription = "Unable to click on 'Cart'";
		}
		
		return blnFlag;
	}
	
	public boolean m_flipkart_Logout()
	{
		boolean blnFlag = false;
		
		mbrowser.WaitForSync(2);
		blnFlag = MobPage("pgeLogin").MobElement("btnMyAccount").Click();
		
		if(blnFlag)
		{
			mbrowser.WaitForSync(5);
			blnFlag = MobPage("pgeLogin").MobElement("lnkLogout").WaitForElement(60);
			if(blnFlag)
			{
				blnFlag =MobPage("pgeLogin").MobElement("lnkLogout").Click();
				if(blnFlag)
				{
					mbrowser.WaitForSync(2);
					blnFlag = MobPage("pgeLogin").MobElement("btnYesLogoutPopUp").WaitForElement(90);
					if(blnFlag)
					{
						blnFlag = MobPage("pgeLogin").MobElement("btnYesLogoutPopUp").Click();
						if(blnFlag)
						{
							mbrowser.WaitForSync(2);
							blnFlag = MobPage("pgeHome").MobElement("txtSearch").WaitForElement(90);
							if(!blnFlag)
							{
								ErrDescription = "Unable to logout from application";
							}
						}
						else
						{
							ErrDescription = "Unable to click 'Yes' on logout popup";
						}
					}
					else
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
			ErrDescription = "Unable to click on 'My Account'";
		}
		
		return blnFlag;
	}
	
	
	public boolean m_flipkart_SearchInvalidProduct(String strProductName)
	{
		boolean blnFlag = false;
		
		blnFlag = MobPage("pgeHome").MobElement("txtSearch").Type(strProductName);
		if(blnFlag)
		{
			blnFlag = MobPage("pgeHome").MobElement("btnSearch").Click();
			if(blnFlag)
			{
						
				blnFlag = MobPage("pgeSearch").MobElement("lnlNoResult").WaitForElement(30);
				
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
		
		//Launching browser
		blnFlag = mbrowser.LaunchApplication(Launcher.dicCommValues.get("strApplicationURL"));
		
		if(blnFlag)
		{
	        switch (strPageName.toLowerCase().trim()){
	            case "homedepot":
	                //Verify SignIn Button is displayed on Homedepot Home MobPage
	            	blnFlag = MobPage("pgeHomeDepot").MobElement("imgHomeDepotHome").WaitForElement(30);
	
	                break;
	            case "":
	                //Verify SignIn Button is displayed on Homedepot Home MobPage
	            	blnFlag = MobPage("pgeHomeDepot").MobElement("imgHomeDepotHome").WaitForElement(30);
	                break;
	        }
		}
        if (!blnFlag){
            ErrDescription = "Home MobPage is not displayed in Browser.";
        }
        return blnFlag;

    }
	
	//''@###########################################################################################################################
    //''@Function ID: 
    //''@Function Name: IsMainMenuAvailable
    //''@Objective: This Function will verify whether Main Menu is available
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Param Name: NA
    //''@Param Desc: NA
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Return Desc: 
    //''@     Success - True
    //''@     Failure - False
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Example: blnStatus= IsMainMenuAvailable(element)
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Created by[Date]: Himanshu Gosain[20-Oct-2016]
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Reviewed by[Date]: 
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@History Notes: 
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@###########################################################################################################################
	public boolean IsMainMenuAvailable(String sORElement)
	{
		boolean blnFlag = false;

		//Switching the application to Native Mode
		blnFlag = mbrowser.SetContext("NATIVE_APP");
		//System.out.println(mbrowser.GetContext());
		if(blnFlag)
		{
			//Getting XPath of Object from OR
	        String str= mbrowser.GetXpath("pgeHomeDepot", sORElement);//MobPage("pgeHomeDepot").MobElement(sORElement).GetXpath();
	        //Waiting for Main Menu link on the page
	        blnFlag = MobPage("").MobElement(str).WaitForElement(50);
	        if(blnFlag)
	        {
	        	//Switching the application to WebView Mode
	        	blnFlag =  mbrowser.SetContext("WEBVIEW_1");
	        	if(!blnFlag)
	        		ErrDescription ="Unable to switch to Web View Mode";
	        }
	        else
			{
				ErrDescription = "Unable to click on Main Menu link";
				//Switching the application to WebView Mode
				blnFlag =  mbrowser.SetContext("WEBVIEW_1");
				if(blnFlag)
				{
	        		blnFlag= false;
				}
				else
				{
					ErrDescription ="Unable to click on Main Menu link and unable to switch to Web View Mode";
				}
			}
	        
	        blnFlag =  mbrowser.SetContext("WEBVIEW_1");
			mbrowser.WaitForSync(2);
		}
		else
		{
			ErrDescription = "Unable to switch to Native App Mode";
		}
		
        return blnFlag;
	}
	
	//''@###########################################################################################################################
    //''@Function ID: 
    //''@Function Name: IsMainMenuClicked
    //''@Objective: This Function will click on Main Menu
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Param Name: NA
    //''@Param Desc: NA
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Return Desc: 
    //''@     Success - True
    //''@     Failure - False
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Example: blnStatus= IsMainMenuClicked(element)
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Created by[Date]: Himanshu Gosain[20-Oct-2016]
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@Reviewed by[Date]: 
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@History Notes: 
    //''@---------------------------------------------------------------------------------------------------------------------------
    //''@###########################################################################################################################
	public boolean IsMainMenuClicked(String sORElement)
	{
		boolean blnFlag = false;

		//Switching the application to Native Mode
		blnFlag = mbrowser.SetContext("NATIVE_APP");
		//System.out.println(mbrowser.GetContext());
		if(blnFlag)
		{
			MobPage("pgeHomeDepot").MobElement(sORElement).WaitForElement(30);
			//Waiting for sync
			mbrowser.WaitForSync(4);
			//Getting XPath of Object from OR
	        String str= mbrowser.GetXpath("pgeHomeDepot", sORElement);//MobPage("pgeHomeDepot").MobElement(sORElement).GetXpath();
	        //System.out.println(MobPage("").MobElement(str).WaitForElement(30));

	        //Clicking on Main Menu link
	        blnFlag = MobPage("").MobElement(str).Click();
	        if(blnFlag)
	        {
	        	mbrowser.WaitForSync(1);
	        	//Switching the application to WebView Mode
	        	blnFlag =  mbrowser.SetContext("WEBVIEW_1");
	        	if(!blnFlag)
	        		ErrDescription ="Unable to switch to Web View Mode";
	        }
	        else
			{
	        	ErrDescription = "Unable to click on Main Menu link";
	        	//Switching the application to WebView Mode
				blnFlag =  mbrowser.SetContext("WEBVIEW_1");
				if(blnFlag)
				{
	        		blnFlag= false;
				}
				else
				{
					ErrDescription ="Unable to click on Main Menu link and unable to switch to Web View Mode";
				}
			}
	        
	        blnFlag =  mbrowser.SetContext("WEBVIEW_1");
			mbrowser.WaitForSync(2);
		}
		else
		{
			ErrDescription = "Unable to switch to Native App Mode";
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

		//Clicking on Main Menu Option
		blnFlag =  IsMainMenuClicked("lnkMainMenu");
	
        //Clicking on Sign In button
        if(blnFlag)
        {
        	//Verifying My Account link is displayed
            blnFlag = MobPage("pgeHomeDepot").MobElement("lnkMyAccount").WaitForElement(30);
            if(blnFlag)
            {
            	//Clicking on My Account link
            	blnFlag = MobPage("pgeHomeDepot").MobElement("lnkMyAccount").Click();
            	if(blnFlag)
            	{
            		//Verifying Sign In link is displayed
            		blnFlag = MobPage("pgeHomeDepot").MobElement("eleSignIn").WaitForElement(30);
            		if(blnFlag)
                	{
            			//Clicking on Sign In button
            	        blnFlag = MobPage("pgeHomeDepot").MobElement("eleSignIn").Click();
            	        if(blnFlag)
            	        {
            	        	//Waiting for pop up to open to enter email address and password to login
            	            blnFlag = MobPage("pgeHomeDepot").MobElement("txtEmailAddress").WaitForElement(30);
            	            if(blnFlag)
                	        {
            	            	//Entering Email address
            	                blnFlag = MobPage("pgeHomeDepot").MobElement("txtEmailAddress").Type(strEmailAddress);
            	                
            	                //Entering Password
            	                if(blnFlag)
            	                	blnFlag = MobPage("pgeHomeDepot").MobElement("txtPassword").Type(strPassword);
            	                
            	                if(blnFlag)
            	                {
            	                	//Clicking on Sign In Button
            	                	blnFlag = MobPage("pgeHomeDepot").MobElement("btnSignIn").Click();
            	                	
            	                	if(blnFlag)
            	                	{
            	                		//Waiting for page to load and checking that user is sign in successfully
            	                        blnFlag = MobPage("pgeHomeDepot").MobElement("lnkSignOut").WaitForElement(30);
            	                	}
            	                	else
                                	{
                                		ErrDescription="Unable to find Sign Out link";
                                	}
            	                }
            	                else
                            	{
                            		ErrDescription="Unable to click on Sign In button";
                            	}
                	        }
            	            else
                        	{
                        		ErrDescription="Unable to enter email or password";
                        	}
            	        }
            	        else
                    	{
                    		ErrDescription="Unable to find Email Address textbox";
                    	}
                	}
                	else
                	{
                		ErrDescription="Unable to click on Sign In link";
                	}
            	}
            	else
            	{
            		ErrDescription="Unable to find Sign In link";
            	}
            }
            else
            {
            	ErrDescription = "Unable to find My Account link";
            }
        }
        else
        {
        	ErrDescription = "Unable to click on home pageImage Icon";
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
	public boolean SearchProduct(String strProductName) throws InterruptedException
    {
		boolean blnFlag = false;
        //Clicking on Main Menu Search button
		blnFlag =  IsMainMenuClicked("lnkMainSearchMenu");
        
        if(blnFlag)
        {
        	//Verifying Search textbox is displayed
        	blnFlag = MobPage("pgeHomeDepot").MobElement("txtSearchItem").WaitForElement(30);
        	if(blnFlag)
            {
        		//Enter item to be searched in search textbox
        		blnFlag = MobPage("pgeHomeDepot").MobElement("txtSearchItem").Type(strProductName);
        		if(blnFlag)
                {
        			 //Clicking on Search button 
        			 blnFlag= MobPage("pgeHomeDepot").MobElement("btnSearch").Click();
        			 if(blnFlag)
        			 {
        				 //Verifying Add to Cart button is displayed
	        			 blnFlag= MobPage("pgeSearchResult").MobElement("btnAddToCart").IsDisplayed(15);
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
                	ErrDescription="Unable to enter in Search textbox"; 
                }
            }
        	else
            {
            	ErrDescription="Unable to find in Search textbox"; 
            }
        }
        else
        {
        	ErrDescription="Unable to click on Search link"; 
        }
        
        if(blnFlag)
        {
        	//@ Handle No Thanks Popup
            //NoThanksPopUp();
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
        blnFlag =MobPage("pgeSearchResult").MobElement("btnAddToCart").Click();
        if(blnFlag)
        {
	        //@ Handle No Thanks Popup
	        NoThanksPopUp();
	        
	        //Waiting for Checkout Now Added to Cart button to be displayed
        	blnFlag = MobPage("pgeSearchResult").MobElement("btnCheckoutNowAddedToCart").WaitForElement(30);
        	if(!blnFlag)
            {
            	ErrDescription="Unable to click on Add to Cart button"; 
            }
        }
        else
        {
        	ErrDescription="Unable to click on Add to Cart button"; 
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
    public boolean CheckoutFromAddedToCart() throws InterruptedException
    {
    	boolean blnFlag = false;
    	
    	//@ Handle No Thanks Popup
        NoThanksPopUp();

    	// Verifying CheckoutNow button is displayed
    	blnFlag=  MobPage("pgeSearchResult").MobElement("btnCheckoutNowAddedToCart").IsDisplayed(30);
    	if(blnFlag)
    	{
	        //Clicking on checkout now button on Add to cart page
	        blnFlag= MobPage("pgeSearchResult").MobElement("btnCheckoutNowAddedToCart").Click();
	        if(blnFlag)
	        {
	        	//@ Handle No Thanks Popup
	            NoThanksPopUp();
	            
	            //Verify Modify(EditAddress) button is displayed
	        	blnFlag=MobPage("pgeDeliveryAddressandCalendar").MobElement("lnkEditThisAddress").WaitForElement(120);
	        	if(!blnFlag)
		        {
		        	ErrDescription="Unable to navigate to payment detail page";
		        }
	        }
	        else
	        {
	        	ErrDescription="Unable to click on Checkout Now button";
	        }
    	}
    	else
    	{
    		ErrDescription="Unable to find Checkout Now button";
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
    public boolean EnterDeliveryAddress(String strFirstName, String strLastName, String strAddress1, String intPhoneNumber) throws InterruptedException
    {
    	boolean blnFlag = false;
    	
    	//@ Handle No Thanks Popup
        NoThanksPopUp();

        //Checking if modify link appears, if yes clicking on the link
    	blnFlag = MobPage("pgeDeliveryAddressandCalendar").MobElement("lnkEditThisAddress").IsDisplayed(120);
    	//blnFlag = MobPage("pgeDeliveryAddressandCalendar").MobElement("lnkEditThisAddress").WaitForElement(120);

        if (blnFlag)
        {
            //Clicking on modify link and waiting for form to be displayed
        	blnFlag = MobPage("pgeDeliveryAddressandCalendar").MobElement("lnkEditThisAddress").Click();
        	if(blnFlag)
        	{
        		//Waiting for First Name field to appear on the screen
        		blnFlag = MobPage("pgeDeliveryAddressandCalendar").MobElement("txtFirstName").WaitForElement(30);
        		if(blnFlag)
        		{
		            //Clearing and Entering First Name
        			mbrowser.WaitForSync(2);
        			blnFlag = MobPage("pgeDeliveryAddressandCalendar").MobElement("txtFirstName").Clear();
        			blnFlag = MobPage("pgeDeliveryAddressandCalendar").MobElement("txtFirstName").Type(strFirstName);
        			if(blnFlag)
        			{
        				//Clearing and Entering Last Name
        				blnFlag = MobPage("pgeDeliveryAddressandCalendar").MobElement("txtLastName").Clear();
        				blnFlag = MobPage("pgeDeliveryAddressandCalendar").MobElement("txtLastName").Type(strLastName);
        				if(blnFlag)
            			{
        					//Clearing and Entering Address Line1
        					blnFlag = MobPage("pgeDeliveryAddressandCalendar").MobElement("txtAddress1").Clear();
        					blnFlag = MobPage("pgeDeliveryAddressandCalendar").MobElement("txtAddress1").Type(strAddress1);
        					if(blnFlag)
                			{
        						//Clearing and Entering Phone Number
        						blnFlag = MobPage("pgeDeliveryAddressandCalendar").MobElement("txtPhone").Clear();
	            				blnFlag = MobPage("pgeDeliveryAddressandCalendar").MobElement("txtPhone").Type(intPhoneNumber);
	            				if(blnFlag)
	                			{
		    			            //Clicking on Update button
		            				blnFlag = MobPage("pgeDeliveryAddressandCalendar").MobElement("btnUpdate").Click();
		            				mbrowser.WaitForSync(3);
		            				if(!blnFlag)
		                        	{
		                        		ErrDescription="Unable to click on update button";
		                        	}
	                			}
	            				else
	                        	{
	                        		ErrDescription="Unable to enter phone number";
	                        	}
                			}
        					else
                        	{
                        		ErrDescription="Unable to enter address line1";
                        	}
            			}
        				else
                    	{
                    		ErrDescription="Unable to enter last name";
                    	}        				
        			}
        			else
                	{
                		ErrDescription="Unable to enter first name";
                	}
        		}
        		else
            	{
            		ErrDescription="Unable to find first name field";
            	}
        	}
        	else
        	{
        		ErrDescription="Unable to click on Modify button";
        	}
        }
        else
    	{
    		ErrDescription="Unable to find Modify button in shipping address";
    	}
        
        //@ Handle No Thanks Popup
        NoThanksPopUp();

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
        //Clicking on Home depot image icon
        blnFlag = MobPage("pgeHomeDepot").MobElement("imgHomeDepotHome").WaitForElement(30);
        if(blnFlag)
        {
        	//Click on Home Image to navigate to Home Page
	        blnFlag = MobPage("pgeHomeDepot").MobElement("imgHomeDepotHome").Click();
	        if(!blnFlag)
	        {
	        	//ErrDescription="Unable to click on Home Page Image";
	        	//Verifying Home Image is displayed
	        	blnFlag = MobPage("pgeHomeDepot").MobElement("imgHomeDepot").WaitForElement(30);
	        	if(blnFlag)
	            {
	        		//Click on Home Image to navigate to Home Page
	    	        blnFlag = MobPage("pgeHomeDepot").MobElement("imgHomeDepot").Click();
	    	        if(!blnFlag)
	    	        {
	    	        	ErrDescription="Unable to click on Home Page Image";
	    	        }
	            }
	        	else
	        	{
	        		ErrDescription="Unable to find Home Page Image";
	        	}
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

    	//@ Handle No Thanks Popup
        NoThanksPopUp();
    	//Verifying Main Cart Menu is displayed
    	mbrowser.WaitForSync(3);
    	blnFlag = MobPage("pgeHomeDepot").MobElement("eleMainCartMenu").IsDisplayed(120);
    	if(blnFlag)
    	{
	        //Clicking on link to Cart 
	    	blnFlag = MobPage("pgeHomeDepot").MobElement("eleMainCartMenu").Click();
			if(blnFlag)
			{
				//@ Handle No Thanks Popup
		        NoThanksPopUp();
		        //Waiting for Cart to be displayed
				mbrowser.WaitForSync(3);
				blnFlag = MobPage("pgeSearchResult").MobElement("lnkRemove").WaitForElement(30);
		        if (blnFlag)
		        {
		            //Clicking on Remove link
		        	blnFlag = MobPage("pgeSearchResult").MobElement("lnkRemove").Click();
		        	if(blnFlag)
		        	{
		        		//Waiting for Empty Cart element to appear on the page
		    	        blnFlag = MobPage("pgeSearchResult").MobElement("eleTheShoppingCartIsEmpty.").WaitForElement(30);
		        	}
		        	else
			        {
			        	ErrDescription="Unable to Remove Item from cart";
			        }
		        }
		        else
		        {
		        	ErrDescription="Unable to click on Remove Item link";
		        }		        
			}
			else
			{
				ErrDescription="Unable to click on Main Cart Menu option";
			}
    	}
    	else
    	{
    		ErrDescription ="Unable to find Main Cart Menu option";
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
		
        //Clicking on Main Menu Link
    	blnFlag =  IsMainMenuClicked("lnkMainMenu");

        if(blnFlag)
        {
        	//Waiting for My Account link is displayed
        	mbrowser.WaitForSync(3);
            blnFlag = MobPage("pgeHomeDepot").MobElement("lnkMyAccount").WaitForElement(15);
            mbrowser.WaitForSync(3);
            if(blnFlag)
            {
            	//Clicking on My Account link
            	blnFlag = MobPage("pgeHomeDepot").MobElement("lnkMyAccount").Click();
            	if(blnFlag)
            	{
            		//Verifying Sign Out link is displayed
            		mbrowser.WaitForSync(2);
            		blnFlag = MobPage("pgeHomeDepot").MobElement("lnkSignOut").IsDisplayed(15);
            		if(blnFlag)
                	{
            			//Waiting for page to load and checking that user is sign out successfully
                         blnFlag = MobPage("pgeHomeDepot").MobElement("lnkSignOut").Click();
                         mbrowser.WaitForSync(2);
            			 if(!blnFlag)
	                	 {
                    	    ErrDescription="Unable to click Sign Out link";
                    	 }            	        
                	}
                	else
                	{
                		ErrDescription="Unable to find Sign out link";
                	}
            	}
            	else
            	{
            		ErrDescription="Unable to click on My Account link";
            	}
            }
            else
            {
            	ErrDescription = "Unable to find My Account link";
            }
        }
        else
        {
        	ErrDescription = "Unable to click on Main Menu Icon";
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
    	//Verifying No Thanks PopUp is displayed
    	if (MobPage("pgeHomeDepot").MobElement("lnkNoThanks").IsDisplayed(10))
        {
    		//Clicking on No Thanks option on PopUp
    		MobPage("pgeHomeDepot").MobElement("lnkNoThanks").Click();
        }
    }
}
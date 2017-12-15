@Flipkart
Feature: Flipkart Feature

  Scenario: FlipkartDemo(TCID=TestScript.TestSuite.Flipkart.FlipkartSearchValidItemBDD_HDTC1)
		Given User Launches the Flipkart
		
		When User Enters UserId And Password And Clicks On Submit Button
		
		When User Searches The Valid Product By Entering The Product Name And Clicks On Search Button
		
		When User Selects The Valid Product By Clicking On The Displayed Product
		
		When User Added Searched Item To Cart By Clicking Add To Cart Button
		
		When User place the Order By Clicking on Place Order Button
		
		When User Enters Delivery Address And Clicks On Update Button
		
		When User Clicks On Home Depot Image
		
		When User Removes the Item from Cart
		
		When User Clicks On Sign Out Link

		Scenario: FlipkartDemo(TCID=TestScript.TestSuite.Flipkart.FlipkartSearchInvalidItemBDD_HDTC2)	
    Given User Launches the Flipkart
		
		When User Enters UserId And Password And Clicks On Submit Button
		
		When User Searches The Invalid Product By Entering The Product Name And Clicks On Search Button
		
		When User Clicks On Sign Out Link
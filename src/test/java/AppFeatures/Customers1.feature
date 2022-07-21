Feature: Customers


Scenario: Add new Customer
	Given Launch the application URL
	When User enters Email as "admin@yourstore.com" and Password as "admin"
	And Click on Login 
	Then User can view Dashboad 
	When User click on customers Menu 
	And click on customers Menu Item 
	And click on Add new button 
	Then User can view Add new customer page 
	When User enter customer info 
	And click on Save button 
	Then User can view confirmation message "The new customer has been added successfully."
	Then close the browser

	@sanity
Scenario: Search Customer by EMailID
	Given Launch the application URL
	When User enters Email as "admin@yourstore.com" and Password as "admin"
	And Click on Login 
	Then User can view Dashboad 
	When User click on customers Menu 
	And click on customers Menu Item 
	And Enter customer EMail
	When Click on search button
	Then close the browser
	
@regression
Scenario: Search Customer by Name
	Given Launch the application URL
	When User enters Email as "admin@yourstore.com" and Password as "admin"
	And Click on Login 
	Then User can view Dashboad 
	When User click on customers Menu 
	And click on customers Menu Item 
	And Enter customer FirstName
	And Enter customer LastName
	When Click on search button
	Then close the browser
	
		
	
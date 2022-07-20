Feature: Login 

@sanity
Scenario: Successful Login with Valid Credentials
	Given Launch the application URL
	When User enters Email as "admin@yourstore.com" and Password as "admin"
	And Click on Login 
	Then Page Title should be "Dashboard / nopCommerce administration" 
	When User click on Log out link 
	Then Page Title should be "Your store. Login"
	Then close the browser
	
@regression
Scenario Outline: Login Data Driven
	Given Launch the application URL
	When User enters Email as "<email>" and Password as "<password>"
	And Click on Login 
	Then Page Title should be "Dashboard / nopCommerce administration" 
	When User click on Log out link 
	Then Page Title should be "Your store. Login"
	Then close the browser
	
	Examples: 
		| email | password |
		| admin@yourstore.com	|	admin |
		| admin1@yourstore.com | admin123	|
Feature: Purchase the order from Ecommerce Website

Background: #Background step executes befor every test/scenario method similar to TestNG @BeforeMethod
Given I have launched the application and landed on ecommerce webpage

#We can use tidy gherkin plugin to generate the stepdefenitions methods if needed
@Regression
Scenario Outline: Positive Testcase of submitting the order
	Given I have logged in with username <name> and password <password>
	When I add the product <product_name> to the Cart
	And Checkout <product_name> visibility in the cart and submit the order
	Then verify "THANKYOU FOR THE ORDER." message is displayed in ConfirmationPage
	
	Examples:
		| name 					   | password  | product_name |
		|adithya.hulugundi@test.com| Shiva@316 | ZARA COAT 3  |
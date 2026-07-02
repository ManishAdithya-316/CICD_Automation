Feature: Error validations

@ErrorValidations
Scenario Outline:Running the ErrorValidationsTest.java
	#we can reuse 1st 2 steps from other feature file no need to write code
	Given I have launched the application and landed on ecommerce webpage    
	Given I have logged in with username <name> and password <password>
	Then verify that "Incorrect email or password." error message is displayed
	
	Examples:
		| name | password |
		| Invalid@email.com| 123456 |
	
	

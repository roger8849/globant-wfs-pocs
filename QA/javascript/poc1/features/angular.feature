Feature: Simple browse
  In order to test protractor
  As a developer
  I want to navigate to Angular JS home page

	@GTPFX-9 @GTPFX-1
  Scenario: Navigate with browser
    Given I navigate to Angular JS home page
    When I look at Head Title message
    Then Head Title message should read "HTML enhanced for web apps!"

  @GTPFX-10 @GTPFX-1
  Scenario: Navigate with browser2
    Given I navigate to Angular JS home page
    When I look at Head Title message
    Then Head Title message should read "HTML enhanced for web apps2!"

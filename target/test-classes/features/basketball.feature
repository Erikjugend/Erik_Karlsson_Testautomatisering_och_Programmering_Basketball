Feature:
  Background: Testing the functionality of the account-creation page of the official basketballengland page
    in the form of successfully creating an account, trying to create a user where the last name is not entered,
    testing to create a user where the repeated entry of the chosen password is not entered, and creating a user
    where the terms and conditions check box is not checked.
    Scenario Outline: Creating account on basketballengland
      Given I am using "<browser>" as browser
      And I have navigated to the account-creation page on basketballengland
      When I enter my "<date of birth>"
      And I enter my "<first name>" and "<last name>"
      And I enter my email address in both fields
      And I choose a "<password>"
      And I repeat the "<2nd password>"
      And I have ticked the "<required boxes>"
      And I press the confirm button
      Then I get the status <status>


      Examples:
      |browser|date of birth|first name|last name|password|2nd password|required boxes|status|
      |edge   |16/08/1998   |Erik      |Karlsson |hejhej98|hejhej98|true|success|
      |firefox|16/08/1998   |Erik      |Karlsson |hejhej98|hejhej98|true|success|
      |chrome |16/08/1998   |Erik      |Karlsson |hejhej98|hejhej98|true|success|
      |edge   |16/08/1998   |Erik      |         |hejhej98|hejhej98|true|no last name|
      |firefox|16/08/1998   |Erik      |         |hejhej98|hejhej98|true|no last name|
      |chrome |16/08/1998   |Erik      |         |hejhej98|hejhej98|true|no last name|
      |edge   |16/08/1998   |Erik      |Karlsson |hejhej98|hejhej|true|no identical password|
      |firefox|16/08/1998   |Erik      |Karlsson |hejhej98|hejhej|true|no identical password|
      |chrome |16/08/1998   |Erik      |Karlsson |hejhej98|hejhej|true|no identical password|
      |edge   |16/08/1998   |Erik      |Karlsson |hejhej98|hejhej98|false|no terms and conditions|
      |firefox|16/08/1998   |Erik      |Karlsson |hejhej98|hejhej98|false|no terms and conditions|
      |chrome |16/08/1998   |Erik      |Karlsson |hejhej98|hejhej98|false|no terms and conditions|
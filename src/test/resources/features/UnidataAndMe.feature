Feature: Change Elements of UniData & Me

@checkUnidataMe
Scenario: Update email, check, update back and check

  When I go to url
  When I log in with user and password
    Then I go to My Unidata and inside
    Then I update the email with newEmail
    Then I check the email newEmail
    Then I update the email with oldEmail
    Then I check the email oldEmail



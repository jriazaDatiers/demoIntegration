Feature: Sharing unicat links as Unidata User


  Scenario: Sharing unicat links as Unidata User
    When I go to url
    When I log in with user and password
    Then I go to Articles
    Then I click on the sharing link on specific article
    Then I validate the content of the product in Unicat

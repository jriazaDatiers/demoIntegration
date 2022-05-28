Feature: Share Unicat link
@shareLink
  Scenario: Share Unicat link

    When I go to url
    When I log in with user and password
    Then I go to Articles
    Then I click on specific article
    Then I validate the content of the product in Unicat
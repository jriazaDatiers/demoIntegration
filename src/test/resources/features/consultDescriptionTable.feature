Feature: Export code label image to Excel

  Scenario Outline: Export code label image to Excel

    When I go to <url>
    When I log in with user <user> and password <password>
    Then I go to Descriptions Menu
    Then I click on a Description
    Then I close the record
    Then I validate I am on the description table again



    Examples:
      |url|user|password|
      | "https://staging.unidata.msf.org/" | "ESCDO" | "Downy2020!!" |
Feature: Register Account

  Background:
    Given The base url and data to use in this feature is set

  Scenario:
    When Send request api to endpoint register using "POST" method with body:
      """
      {
        "email": "<email>",
        "full_name": "<full_name>",
        "password": "<password>",
        "department": "<department>",
        "phone_number": "<phone_number>"
      }
      """
    Then The response register endpoint status must be 200
    And The response register api schema should be match with schema "response/RegistrationResponse.json"
    Then Map Register API Response
    Then check register api data response

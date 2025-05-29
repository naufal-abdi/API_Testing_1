Feature: Register Account

  Background:
    Given The base url and data to use in this feature is set

  Scenario:
    When Register account with http "POST" request to "/api/register" with body:
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

  

  # Scenario:
  #   Given Make sure token in local storage not empty
  #   When Send a http "PUT" request to "/employee/update" with body:
  #     """
  #     {
  #       "email": "test92@test.com",
  #       "password": "test",
  #       "full_name": "Ini nama yg udh diupdate ya",
  #       "department": "Tech",
  #       "title": "Backend Engineer"
  #     }
  #     """
  #   Then The response status must be 200
  #   And Full name in the response must be "Ini nama yg udh diupdate ya"
  #   And Department in the response must be "Tech"
  #   And Title in the response must be "Backend Engineer"


  #Scenario:
  #  Given Make sure token in local storage not empty
  #  When Send a http "GET" request to "/api/department" with body:
  #    """
  #    {}
  #    """
  #  Then The response status must be 200
  #  And Full name in the response must be "Ini nama yg udh diupdate ya"
  #  And Department in the response must be "Tech"
  #  And Title in the response must be "Backend Engineer"

  # Scenario:
  #   Given Make sure token in local storage not empty
  #   When Send a http "DELETE" request to "/employee/delete" with body:
  #     """
  #     {}
  #     """
  #   Then The response status must be 200

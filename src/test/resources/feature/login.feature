Feature: Login Account

    Background:
        Given Prepare data for login test


    Scenario:
        When Login account with http "POST" request to "/api/login" with body:
            """
            {
                "email": "<email>",
                "password": "<password>"
            }
            """
        Then The response login endpoint status must be 200
        And The response login api schema should be match with schema "response/LoginResponse.json"
# Then Map API Response
# Then check api data response
# And Save the token from the response to local storage
Feature: Login Account

    Background:
        Given Prepare data for login test


    Scenario:
        When Send request api to endpoint login using "POST" method with body:
            """
            {
                "email": "<email>",
                "password": "<password>"
            }
            """
        Then The response login endpoint status must be 200
        And The response login api schema should be match with schema "response/LoginResponse.json"
        Then Map login API Response
        Then check login api data response
        And Save the token from the login api response to local storage
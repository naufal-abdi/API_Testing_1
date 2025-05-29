Feature: Get All Department

    Scenario:
        When Send request api to endpoint getAllDepartment using "GET" method
        Then The response getAllDepartment endpoint status must be 200
        And The response getAllDepartment api schema should be match with schema "response/DepartmentResponse.json"
        Then Map getAllDepartment API Response
        Then check getAllDepartment api data response
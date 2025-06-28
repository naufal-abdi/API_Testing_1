Feature: Checkout Test

    Background:
        Given Prepare data for checkout test

    Scenario:
        When Try invalid login with Empty Username and Password
        Then Try invalid login with Empty Password
        Then Try login with invalid Username and Password
        Then Try login with valid Username and Password
        When Choose product in catalog and add to cart
        Then Go to cart page and verify product list
        Then Verify product in list
        Then Click checkout button and fill buyer data
        Then Verify product in overview page
        Then Verify success message and back to catalog page
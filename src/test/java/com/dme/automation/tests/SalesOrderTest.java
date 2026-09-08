package com.dme.automation.tests;

import com.dme.automation.base.BaseTest;
import com.dme.automation.pages.LoginPage;
import com.dme.automation.pages.SalesOrderLogoutPage;
import com.dme.automation.pages.SalesOrderPage;
import com.microsoft.playwright.Page;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SalesOrderTest extends BaseTest {

    private static final String BASE_URL =
            "https://ui-dev.dmeez.in/";

    private static final String SALES_ORDER =
            "SLO1000004289";

    @Test
    public void testSalesOrderFlow() {

        // =========================================================
        // Step 1: Get credentials
        // =========================================================

        String username =
                System.getenv("DME_USERNAME");

        String password =
                System.getenv("DME_PASSWORD");

        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {

            throw new IllegalStateException(
                    "Set DME_USERNAME and DME_PASSWORD environment variables."
            );
        }

        // =========================================================
        // Step 2: Login
        // =========================================================

        System.out.println("========================================");
        System.out.println("Opening application...");
        System.out.println("========================================");

        LoginPage loginPage =
                new LoginPage(page);

        loginPage.openApplication(BASE_URL);

        loginPage.login(
                username,
                password
        );

        page.waitForTimeout(2000);

        System.out.println("LOGIN SUCCESS");


        // =========================================================
        // Step 3: Create SalesOrderPage
        // =========================================================

        SalesOrderPage salesOrderPage =
                new SalesOrderPage(page);


        // =========================================================
        // Step 4: Open Sidebar
        // =========================================================

        System.out.println("Opening sidebar...");

        page.locator(".bi")
                .first()
                .click();

        page.waitForTimeout(1000);


        // =========================================================
        // Step 5: Open Ordering
        // =========================================================

        System.out.println("Opening Ordering...");

        salesOrderPage.openOrdering();

        page.waitForTimeout(1000);


        // =========================================================
        // Step 6: Open Sales Orders
        // =========================================================

        System.out.println("Opening Sales Orders...");

        salesOrderPage.openSalesOrders();


        // =========================================================
        // Step 7: Search Sales Order
        // =========================================================

        System.out.println(
                "Searching Sales Order: "
                        + SALES_ORDER
        );

        salesOrderPage.searchSalesOrder(
                SALES_ORDER,
                "Initiated"
        );

        page.waitForTimeout(2000);

        System.out.println(
                "SALES ORDER SEARCH COMPLETED"
        );


        // =========================================================
        // Step 8: Open Sales Order Details
        // =========================================================

        System.out.println(
                "Opening Sales Order Details..."
        );

        Page salesOrderDetailsPage =
                salesOrderPage.openSalesOrder(
                        SALES_ORDER
                );

        System.out.println(
                "Sales Order Details URL: "
                        + salesOrderDetailsPage.url()
        );

        Assert.assertTrue(
                salesOrderDetailsPage.url().contains("/"),
                "Sales Order Details page was not opened"
        );

        System.out.println(
                "SALES ORDER DETAILS OPENED SUCCESSFULLY"
        );


        // =========================================================
        // Step 9: Create Page Object for Details Page
        // =========================================================

        SalesOrderPage detailsPage =
                new SalesOrderPage(
                        salesOrderDetailsPage
                );


        // =========================================================
        // Step 10: Open Clinical
        // =========================================================

        System.out.println("Opening Clinical...");

        detailsPage.openClinical();


        // =========================================================
        // Step 11: Open Insurance
        // =========================================================

        System.out.println("Opening Insurance...");

        detailsPage.openInsurance();


        // =========================================================
        // Step 12: Open Item
        // =========================================================

        System.out.println("Opening Item...");

        detailsPage.openItem();


        // =========================================================
        // Step 13: Select WIP Status
        // =========================================================

        System.out.println(
                "Selecting WIP Status..."
        );

        detailsPage.selectWipStatus("196");


        // =========================================================
        // Step 14: Select Procedure Code
        // =========================================================

        System.out.println(
                "Selecting Procedure Code..."
        );

        detailsPage.selectProcedureCode("E0470");


        // =========================================================
        // Step 15: Open Documents
        // =========================================================

        System.out.println(
                "Opening Documents..."
        );

        detailsPage.openDocuments();


        // =========================================================
        // Step 16: Logout
        // =========================================================

        System.out.println(
                "========================================"
        );

        System.out.println("Starting logout...");

        System.out.println(
                "========================================"
        );

        SalesOrderLogoutPage logoutPage =
                new SalesOrderLogoutPage(
                        salesOrderDetailsPage
                );

        logoutPage.performLogout();

        System.out.println("LOGOUT SUCCESS");


        // =========================================================
        // Step 17: Small wait for video recording
        // =========================================================

        salesOrderDetailsPage.waitForTimeout(3000);

        System.out.println(
                "========================================"
        );

        System.out.println(
                "SALES ORDER TEST COMPLETED SUCCESSFULLY"
        );

        System.out.println(
                "========================================"
        );
    }
}
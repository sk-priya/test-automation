package com.dme.automation.tests;

import com.dme.automation.base.BaseTest;
import com.dme.automation.pages.LoginPage;
import com.dme.automation.pages.SalesOrderLogoutPage;
import com.dme.automation.pages.SalesOrderPage;
import com.dme.automation.utils.ExcelReader;
import com.microsoft.playwright.Page;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class SalesOrderTest extends BaseTest {

    private static final String BASE_URL =
            "https://ui-dev.dmeez.in/";

    // =========================================================
    // Excel Configuration
    // =========================================================

    private static final String EXCEL_FILE =
            "test-data/sales-orders.xlsx";

    private static final String EXCEL_SHEET =
            "SalesOrders";


    // =========================================================
    // Sales Order Configuration
    // =========================================================

    // Status is NOT coming from Excel.
    private static final String SALES_ORDER_STATUS =
            "Initiated";

    private static final String WIP_STATUS =
            "196";

    private static final String PROCEDURE_CODE =
            "E0470";


    @Test
    public void testSalesOrderFlow() {

        // =====================================================
        // Step 1: Get Login Credentials
        // =====================================================

        String username =
                System.getenv("DME_USERNAME");

        String password =
                System.getenv("DME_PASSWORD");

        if (username == null
                || username.isBlank()
                || password == null
                || password.isBlank()) {

            throw new IllegalStateException(
                    "Set DME_USERNAME and DME_PASSWORD environment variables."
            );
        }


        // =====================================================
        // Step 2: Read Sales Order IDs from Excel
        // =====================================================

        ExcelReader excelReader =
                new ExcelReader(EXCEL_FILE);

        List<String> salesOrderIds =
                excelReader.getSalesOrderIds(
                        EXCEL_SHEET
                );

        if (salesOrderIds.isEmpty()) {

            throw new IllegalStateException(
                    "No Sales Order IDs found in Excel."
            );
        }

        System.out.println(
                "========================================"
        );

        System.out.println(
                "Sales Orders found in Excel: "
                        + salesOrderIds.size()
        );

        System.out.println(
                "========================================"
        );


        // =====================================================
        // Step 3: Process Each Sales Order Separately
        // =====================================================

        for (int i = 0;
             i < salesOrderIds.size();
             i++) {

            String salesOrderNumber =
                    salesOrderIds.get(i);

            System.out.println();
            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "PROCESSING SALES ORDER "
                            + (i + 1)
                            + " OF "
                            + salesOrderIds.size()
            );

            System.out.println(
                    "Sales Order: "
                            + salesOrderNumber
            );

            System.out.println(
                    "========================================"
            );


            // =================================================
            // Step 4: Login
            // =================================================

            System.out.println(
                    "Opening application..."
            );

            LoginPage loginPage =
                    new LoginPage(page);

            loginPage.openApplication(
                    BASE_URL
            );

            loginPage.login(
                    username,
                    password
            );

            page.waitForTimeout(2000);

            System.out.println(
                    "LOGIN SUCCESS"
            );


            // =================================================
            // Step 5: Create SalesOrderPage
            // =================================================

            SalesOrderPage salesOrderPage =
                    new SalesOrderPage(page);


            // =================================================
            // Step 6: Open Sidebar
            // =================================================

            System.out.println(
                    "Opening sidebar..."
            );

            page.locator(".bi")
                    .first()
                    .click();

            page.waitForTimeout(1000);


            // =================================================
            // Step 7: Open Ordering
            // =================================================

            System.out.println(
                    "Opening Ordering..."
            );

            salesOrderPage.openOrdering();

            page.waitForTimeout(1000);


            // =================================================
            // Step 8: Open Sales Orders
            // =================================================

            System.out.println(
                    "Opening Sales Orders..."
            );

            salesOrderPage.openSalesOrders();

            page.waitForTimeout(1000);


            // =================================================
            // Step 9: Search Sales Order
            // =================================================

            System.out.println(
                    "Searching Sales Order: "
                            + salesOrderNumber
            );

            salesOrderPage.searchSalesOrder(
                    salesOrderNumber,
                    SALES_ORDER_STATUS
            );

            page.waitForTimeout(2000);

            System.out.println(
                    "SALES ORDER SEARCH COMPLETED"
            );


            // =================================================
            // Step 10: Open Sales Order Details
            // =================================================

            System.out.println(
                    "Opening Sales Order Details..."
            );

            Page salesOrderDetailsPage =
                    salesOrderPage.openSalesOrder(
                            salesOrderNumber
                    );

            System.out.println(
                    "Sales Order Details URL: "
                            + salesOrderDetailsPage.url()
            );


            // =================================================
            // Step 11: Verify Details Page
            // =================================================

            Assert.assertTrue(
                    salesOrderDetailsPage.url().contains(
                            "/salesorder/"
                    ),
                    "Sales Order Details page was not opened for "
                            + salesOrderNumber
            );

            System.out.println(
                    "SALES ORDER DETAILS OPENED SUCCESSFULLY"
            );


            // =================================================
            // Step 12: Work on Sales Order Details
            // =================================================

            SalesOrderPage detailsPage =
                    new SalesOrderPage(
                            salesOrderDetailsPage
                    );


            // -------------------------------------------------
            // Clinical
            // -------------------------------------------------

            System.out.println(
                    "Opening Clinical..."
            );

            detailsPage.openClinical();


            // -------------------------------------------------
            // Insurance
            // -------------------------------------------------

            System.out.println(
                    "Opening Insurance..."
            );

            detailsPage.openInsurance();


            // -------------------------------------------------
            // Item
            // -------------------------------------------------

            System.out.println(
                    "Opening Item..."
            );

            detailsPage.openItem();


            // -------------------------------------------------
            // WIP Status
            // -------------------------------------------------

            System.out.println(
                    "Selecting WIP Status..."
            );

            detailsPage.selectWipStatus(
                    WIP_STATUS
            );


            // -------------------------------------------------
            // Procedure Code
            // -------------------------------------------------

            System.out.println(
                    "Selecting Procedure Code..."
            );

            detailsPage.selectProcedureCode(
                    PROCEDURE_CODE
            );


            // -------------------------------------------------
            // Documents
            // -------------------------------------------------

            System.out.println(
                    "Opening Documents..."
            );

            detailsPage.openDocuments();


            // =================================================
            // Step 13: Logout
            // =================================================

            System.out.println(
                    "Starting logout for "
                            + salesOrderNumber
            );


            SalesOrderLogoutPage logoutPage =
                    new SalesOrderLogoutPage(
                            salesOrderDetailsPage
                    );

            logoutPage.performLogout();


            System.out.println(
                    "LOGOUT SUCCESS"
            );


            // =================================================
            // Step 14: Return to Main Page
            // =================================================

            salesOrderDetailsPage.close();

            page.bringToFront();

            page.waitForTimeout(1000);


            System.out.println(
                    "Completed Sales Order: "
                            + salesOrderNumber
            );

            System.out.println(
                    "========================================"
            );
        }


        // =====================================================
        // Step 15: Complete
        // =====================================================

        System.out.println();
        System.out.println(
                "========================================"
        );

        System.out.println(
                "ALL SALES ORDERS PROCESSED SUCCESSFULLY"
        );

        System.out.println(
                "Total Sales Orders: "
                        + salesOrderIds.size()
        );

        System.out.println(
                "========================================"
        );
    }
}
package com.dme.automation.pages;

import com.dme.automation.base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class SalesOrderPage extends BasePage {

    public SalesOrderPage(Page page) {
        super(page);
    }

    // =========================================================
    // Open Ordering
    // =========================================================

    public void openOrdering() {

        Locator orderingMenu =
                page.getByRole(
                        AriaRole.LINK,
                        new Page.GetByRoleOptions()
                                .setName(" Ordering ")
                );

        orderingMenu.waitFor();
        orderingMenu.click();
    }


    // =========================================================
    // Open Sales Orders
    // =========================================================

    public void openSalesOrders() {

        Locator salesOrders =
                page.getByRole(
                        AriaRole.LINK,
                        new Page.GetByRoleOptions()
                                .setName(" Sales Orders")
                );

        salesOrders.waitFor();
        salesOrders.click();
    }


    // =========================================================
    // Search Sales Order
    // =========================================================

    public void searchSalesOrder(
            String salesOrderNumber,
            String status) {

        Locator salesOrderField =
                page.getByRole(
                        AriaRole.TEXTBOX,
                        new Page.GetByRoleOptions()
                                .setName("Sales Order No.")
                );

        salesOrderField.waitFor();

        salesOrderField.fill(
                salesOrderNumber
        );

        System.out.println(
                "Sales Order entered: "
                        + salesOrderNumber
        );


        // Status is hardcoded in the test.
        page.locator(
                "select[name=\"salesOrderStatus\"]"
        ).selectOption(status);


        System.out.println(
                "Status selected: "
                        + status
        );


        Locator searchButton =
                page.getByRole(
                        AriaRole.LINK,
                        new Page.GetByRoleOptions()
                                .setName(" Search")
                );

        searchButton.waitFor();
        searchButton.click();

        System.out.println(
                "Sales Order searched: "
                        + salesOrderNumber
        );
    }


    // =========================================================
    // Open Sales Order Details
    // =========================================================

    public Page openSalesOrder(
            String salesOrderNumber) {

        System.out.println(
                "Looking for Sales Order result: "
                        + salesOrderNumber
        );

        // Give the result page a little time to update.
        page.waitForTimeout(3000);


        // =====================================================
        // Debug: Current URL
        // =====================================================

        System.out.println(
                "Current URL: "
                        + page.url()
        );


        // =====================================================
        // Debug: Find links containing Sales Order ID
        // =====================================================

        Locator linksContainingSalesOrder =
                page.locator(
                        "a:has-text('" +
                                salesOrderNumber +
                                "')"
                );

        System.out.println(
                "Links containing Sales Order ID: "
                        + linksContainingSalesOrder.count()
        );


        // =====================================================
        // Debug: Exact role link
        // =====================================================

        Locator salesOrderLink =
                page.getByRole(
                        AriaRole.LINK,
                        new Page.GetByRoleOptions()
                                .setName(salesOrderNumber)
                                .setExact(true)
                );

        System.out.println(
                "Exact role link count: "
                        + salesOrderLink.count()
        );


        // =====================================================
        // Open Details in new tab
        // =====================================================

        return page.waitForPopup(() -> {

            salesOrderLink.waitFor();

            System.out.println(
                    "Sales Order link found: "
                            + salesOrderNumber
            );

            salesOrderLink.click();
        });
    }


    // =========================================================
    // Open Clinical
    // =========================================================

    public void openClinical() {

        page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions()
                        .setName("Clinical")
                        .setExact(true)
        ).click();

        System.out.println(
                "Clinical opened"
        );
    }


    // =========================================================
    // Open Insurance
    // =========================================================

    public void openInsurance() {

        page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions()
                        .setName("Insurance")
                        .setExact(true)
        ).click();

        System.out.println(
                "Insurance opened"
        );
    }


    // =========================================================
    // Open Item
    // =========================================================

    public void openItem() {

        page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions()
                        .setName("Item")
                        .setExact(true)
        ).click();

        System.out.println(
                "Item opened"
        );
    }


    // =========================================================
    // Select WIP Status
    // =========================================================

    public void selectWipStatus(
            String statusId) {

        page.locator(
                "select[name=\"wipStatusId\"]"
        ).selectOption(statusId);

        System.out.println(
                "WIP Status selected: "
                        + statusId
        );
    }


    // =========================================================
    // Select Procedure Code
    // =========================================================

    public void selectProcedureCode(
            String procedureCode) {

        page.locator(
                "select[name=\"proccode\"]"
        ).selectOption(procedureCode);

        System.out.println(
                "Procedure Code selected: "
                        + procedureCode
        );
    }


    // =========================================================
    // Open Documents
    // =========================================================

    public void openDocuments() {

        page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions()
                        .setName("Documents")
                        .setExact(true)
        ).click();

        System.out.println(
                "Documents opened"
        );
    }
}
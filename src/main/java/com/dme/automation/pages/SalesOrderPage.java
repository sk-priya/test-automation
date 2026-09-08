package com.dme.automation.pages;

import com.dme.automation.base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class SalesOrderPage extends BasePage {

    public SalesOrderPage(Page page) {
        super(page);
    }

    // =========================================
    // Open Ordering menu
    // =========================================

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

    // =========================================
    // Open Sales Orders
    // =========================================

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

    // =========================================
    // Search Sales Order
    // =========================================

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
        salesOrderField.fill(salesOrderNumber);

        page.locator(
                "select[name=\"salesOrderStatus\"]"
        ).selectOption(status);

        Locator searchButton =
                page.getByRole(
                        AriaRole.LINK,
                        new Page.GetByRoleOptions()
                                .setName(" Search")
                );

        searchButton.waitFor();
        searchButton.click();
    }

    // =========================================
    // Open Sales Order Details
    // =========================================

    public Page openSalesOrder(
            String salesOrderNumber) {

        return page.waitForPopup(() -> {

            Locator salesOrderLink =
                    page.getByRole(
                            AriaRole.LINK,
                            new Page.GetByRoleOptions()
                                    .setName(salesOrderNumber)
                                    .setExact(true)
                    );

            salesOrderLink.waitFor();

            salesOrderLink.click();
        });
    }

    // =========================================
    // Open Clinical
    // =========================================

    public void openClinical() {

        page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions()
                        .setName("Clinical")
                        .setExact(true)
        ).click();
    }

    // =========================================
    // Open Insurance
    // =========================================

    public void openInsurance() {

        page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions()
                        .setName("Insurance")
                        .setExact(true)
        ).click();
    }

    // =========================================
    // Open Item
    // =========================================

    public void openItem() {

        page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions()
                        .setName("Item")
                        .setExact(true)
        ).click();
    }

    // =========================================
    // Select WIP Status
    // =========================================

    public void selectWipStatus(String statusId) {

        page.locator(
                "select[name=\"wipStatusId\"]"
        ).selectOption(statusId);
    }

    // =========================================
    // Select Procedure Code
    // =========================================

    public void selectProcedureCode(
            String procedureCode) {

        page.locator(
                "select[name=\"proccode\"]"
        ).selectOption(procedureCode);
    }

    // =========================================
    // Open Documents
    // =========================================

    public void openDocuments() {

        page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions()
                        .setName("Documents")
                        .setExact(true)
        ).click();
    }
}
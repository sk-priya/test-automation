package com.dme.automation.pages;

import com.dme.automation.base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class SalesOrderLogoutPage extends BasePage {

    private final Locator userProfileName;
    private final Locator logoutButton;

    public SalesOrderLogoutPage(Page page) {

        super(page);

        // =====================================================
        // Profile link
        // =====================================================

        this.userProfileName =
                page.getByRole(
                        AriaRole.LINK,
                        new Page.GetByRoleOptions()
                                .setName("sagar jena")
                                .setExact(true)
                );


        // =====================================================
        // Sign Out button
        // =====================================================

        this.logoutButton =
                page.getByRole(
                        AriaRole.BUTTON,
                        new Page.GetByRoleOptions()
                                .setName(" Sign Out")
                );
    }


    // =========================================================
    // Verify Username
    // =========================================================

    public void verifyUsername(
            String expectedDisplayName) {

        userProfileName.waitFor();

        String actualDisplayName =
                userProfileName.innerText().trim();

        if (!actualDisplayName.equals(
                expectedDisplayName)) {

            throw new AssertionError(
                    "Expected profile name: "
                            + expectedDisplayName
                            + " but found: "
                            + actualDisplayName
            );
        }

        System.out.println(
                "Profile verified: "
                        + actualDisplayName
        );
    }


    // =========================================================
    // Click User Profile
    // =========================================================

    public void clickUserProfile() {

        userProfileName.waitFor();

        userProfileName.click();

        System.out.println(
                "User profile clicked"
        );
    }


    // =========================================================
    // Click Logout
    // =========================================================

    public void clickLogout() {

        logoutButton.waitFor(
                new Locator.WaitForOptions()
                        .setTimeout(15000)
        );

        logoutButton.click();

        System.out.println(
                "Sign Out clicked"
        );
    }


    // =========================================================
    // Complete Logout
    // =========================================================

    public void performLogout() {

        String expectedDisplayName =
                System.getenv(
                        "DME_DISPLAY_NAME"
                );

        if (expectedDisplayName == null
                || expectedDisplayName.isBlank()) {

            expectedDisplayName =
                    "sagar jena";
        }


        verifyUsername(
                expectedDisplayName
        );

        page.waitForTimeout(1000);

        clickUserProfile();

        page.waitForTimeout(1000);

        clickLogout();


        // =====================================================
        // Verify that login page appeared
        // =====================================================

        page.locator("#username").waitFor(
                new Locator.WaitForOptions()
                        .setTimeout(10000)
        );

        System.out.println(
                "Logout completed successfully"
        );
    }
}
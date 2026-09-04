package com.dme.automation.pages;

import com.dme.automation.base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LogoutPage extends BasePage {

    public LogoutPage(Page page) {
        super(page);
    }

    public void performLogout() {

        // =====================================================
        // Step 1: Get logged-in user's display name
        // =====================================================

        String displayName = System.getenv("DME_DISPLAY_NAME");

        if (displayName == null || displayName.isBlank()) {
            displayName = "sagar jena";
        }


        // =====================================================
        // Step 2: Click Profile
        // =====================================================

        Locator profileMenu = page.getByText(
            displayName,
            new Page.GetByTextOptions()
                .setExact(true)
        );

        profileMenu.waitFor(
            new Locator.WaitForOptions()
                .setTimeout(15000)
        );

        System.out.println(
            "Clicking profile: " + displayName
        );

        profileMenu.click();


        // =====================================================
        // Step 3: Click Sign Out
        // =====================================================

        Locator signOutButton = page.getByText(
            "Sign Out",
            new Page.GetByTextOptions()
                .setExact(false)
        );

        signOutButton.waitFor(
            new Locator.WaitForOptions()
                .setTimeout(15000)
        );

        System.out.println(
            "Clicking Sign Out..."
        );

        signOutButton.click();


        // =====================================================
        // Step 4: Verify Logout
        // =====================================================

        page.locator("#username").waitFor(
            new Locator.WaitForOptions()
                .setTimeout(15000)
        );

        System.out.println(
            "Logout completed successfully"
        );
    }
}
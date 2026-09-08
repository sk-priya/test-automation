package com.dme.automation.pages;

import com.dme.automation.base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SalesOrderLogoutPage extends BasePage {

    private final Locator userProfileName;
    private final Locator logoutButton;

    public SalesOrderLogoutPage(Page page) {
        super(page);

        // Specifically target the profile LINK
        // This avoids the "sagar jena" option inside assignedToId
        this.userProfileName =
                page.getByRole(
                        AriaRole.LINK,
                        new Page.GetByRoleOptions()
                                .setName("sagar jena")
                                .setExact(true)
                );

        // Sign Out button
        this.logoutButton =
                page.getByRole(
                        AriaRole.BUTTON,
                        new Page.GetByRoleOptions()
                                .setName(" Sign Out")
                );
    }

    public void verifyUsername(
            String expectedDisplayName) {

        userProfileName.waitFor();

        assertThat(userProfileName)
                .hasText(expectedDisplayName);
    }

    public void clickUserProfile() {

        userProfileName.waitFor();

        userProfileName.click();
    }

    public void clickLogout() {

        logoutButton.waitFor();

        logoutButton.click();
    }

    public void performLogout() {

        String expectedDisplayName =
                System.getenv("DME_DISPLAY_NAME");

        if (expectedDisplayName == null
                || expectedDisplayName.isBlank()) {

            expectedDisplayName = "sagar jena";
        }

        verifyUsername(expectedDisplayName);

        System.out.println(
                "Profile verified: "
                        + expectedDisplayName
        );

        clickUserProfile();

        page.waitForTimeout(1000);

        clickLogout();

        page.locator("#username").waitFor(
                new Locator.WaitForOptions()
                        .setTimeout(10000)
        );

        System.out.println(
                "Sales Order logout completed successfully"
        );
    }
}
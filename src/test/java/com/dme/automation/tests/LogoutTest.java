package com.dme.automation.tests;

import org.testng.annotations.Test;

import com.dme.automation.base.BaseTest;
import com.dme.automation.pages.LoginPage;
import com.dme.automation.pages.LogoutPage;

public class LogoutTest extends BaseTest {

    private static final String BASE_URL = "https://ui-dev.dmeez.in/";

    @Test
    public void testLogout() {

        String username = System.getenv("DME_USERNAME");
        String password = System.getenv("DME_PASSWORD");

        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {
            throw new IllegalStateException(
                    "Set DME_USERNAME and DME_PASSWORD environment variables before running."
            );
        }

        // Login
        LoginPage loginPage = new LoginPage(page);
        loginPage.openApplication(BASE_URL);
        loginPage.login(username, password);

        page.waitForTimeout(2000);

        // Logout
        LogoutPage logoutPage = new LogoutPage(page);
        logoutPage.performLogout();

        System.out.println("SUCCESS: Logout completed");
    }
}

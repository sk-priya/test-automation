package com.dme.automation.tests;

import com.dme.automation.base.BaseTest;
import com.dme.automation.pages.LoginPage;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    private static final String BASE_URL = "https://ui-dev.dmeez.in/";

    @Test
    public void loginTest() {
        String username = System.getenv("DME_USERNAME");
        String password = System.getenv("DME_PASSWORD");
        if (username == null || password == null) {
            throw new IllegalStateException("Set DME_USERNAME and DME_PASSWORD environment variables before running.");
        }
        LoginPage loginPage = new LoginPage(page);
        loginPage.openApplication(BASE_URL);
        loginPage.login(username, password);
        page.waitForTimeout(2000);
        // Uncomment to inspect menus after login:
        // page.pause();
    }
}

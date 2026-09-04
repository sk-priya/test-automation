package com.dme.automation.pages;

import com.dme.automation.base.BasePage;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage extends BasePage {

    public LoginPage(Page page) {
        super(page);
    }

    public void openApplication(String url) {
        page.navigate(url);
    }

    public void enterUsername(String username) {
        page.locator("#username").fill(username);
    }

    public void enterPassword(String password) {
        page.getByLabel("Password *").fill(password);
    }

    public void clickLogin() {
        page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Login")
        ).click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
}

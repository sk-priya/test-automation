import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.util.*;

public class Example {
  public static void main(String[] args) {
     try (Playwright playwright = Playwright.create()) {
      Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
        .setHeadless(false));
      BrowserContext context = browser.newContext();
      Page page = context.newPage();
      page.navigate("https://ui-dev.dmeez.in/");
      page.locator("#username").click();
      page.locator("#username").fill("sagar");
      page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password *")).click();
      page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password *")).press("CapsLock");
      page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password *")).fill("P");
      page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password *")).press("CapsLock");
      page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password *")).fill("Priya@1234");
      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")).click();
      page.locator(".bi").first().click();
      page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(" Patient ")).click();
      page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(" Patients")).click();
      page.locator("#depositID").click();
      page.locator("#depositID").fill("PTN1000001238");
      page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(" Search")).click();
      Page page1 = page.waitForPopup(() -> {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("PTN1000001238")).click();
      });
      page1.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Personal")).click();
      page1.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Summary")).click();
      page1.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("sagar jena")).click();
      page1.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(" Sign Out")).click();
    }
  }
}
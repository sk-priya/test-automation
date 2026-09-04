# DME Playwright Automation - Java + Maven + TestNG

## Included functionality
- Login automation
- Logout automation
- Visible Chromium browser (`headless=false`)
- Maven
- TestNG
- Page Object Model structure

## Project structure
```text
src/main/java/com/dme/automation/
├── base/
│   └── BasePage.java
└── pages/
    ├── LoginPage.java
    └── LogoutPage.java

src/test/java/com/dme/automation/
├── base/
│   └── BaseTest.java
└── tests/
    ├── LoginTest.java
    └── LogoutTest.java

testng.xml
pom.xml
```

## Configure credentials in PowerShell
```powershell
$env:DME_USERNAME="your_username"
$env:DME_PASSWORD="your_password"
```

The LogoutPage defaults to the visible profile name `sagar jena`. If another user is used, set:

```powershell
$env:DME_DISPLAY_NAME="your visible profile name"
```

## Run all tests
```powershell
mvn test
```

## Run only logout test
```powershell
mvn -Dtest=LogoutTest test
```

## Important
`LogoutPage.java` first clicks the visible profile name and then clicks `Sign Out`.
If the profile name or the exact UI locator differs in another environment, inspect the element with Playwright Inspector and update the locator.

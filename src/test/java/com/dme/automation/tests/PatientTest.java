package com.dme.automation.tests;

import com.dme.automation.base.BaseTest;
import com.dme.automation.pages.LoginPage;
import com.dme.automation.pages.LogoutPage;
import com.dme.automation.pages.PatientPage;
import com.dme.automation.utils.ExcelReader;

import com.microsoft.playwright.Page;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class PatientTest extends BaseTest {

    private static final String BASE_URL =
        "https://ui-dev.dmeez.in/";

    private static final String EXCEL_PATH =
        "test-data/patients.xlsx";

    private static final String SHEET_NAME =
        "Patients";


    // =====================================================
    // DataProvider
    // =====================================================

    @DataProvider(name = "patientData")
    public Object[][] patientData() {

        ExcelReader excelReader =
            new ExcelReader(EXCEL_PATH);

        List<String> patientIds =
            excelReader.getPatientIds(
                SHEET_NAME
            );

        Object[][] data =
            new Object[patientIds.size()][1];

        for (int i = 0;
             i < patientIds.size();
             i++) {

            data[i][0] =
                patientIds.get(i);
        }

        return data;
    }


    // =====================================================
    // Patient Test
    // =====================================================

    @Test(dataProvider = "patientData")
    public void testPatientFlow(
        String patientId
    ) {

        System.out.println(
            "========================================"
        );

        System.out.println(
            "Starting Patient Test: "
            + patientId
        );

        System.out.println(
            "========================================"
        );


        // =================================================
        // Get Login Credentials
        // =================================================

        String username =
            System.getenv("DME_USERNAME");

        String password =
            System.getenv("DME_PASSWORD");

        if (username == null
                || username.isBlank()
                || password == null
                || password.isBlank()) {

            throw new IllegalStateException(
                "Set DME_USERNAME and DME_PASSWORD "
                + "environment variables."
            );
        }


        // =================================================
        // Step 1: Login
        // =================================================

        System.out.println(
            "Opening application..."
        );

        LoginPage loginPage =
            new LoginPage(page);

        loginPage.openApplication(
            BASE_URL
        );

        loginPage.login(
            username,
            password
        );

        page.waitForTimeout(2000);

        System.out.println(
            "LOGIN SUCCESS"
        );


        // =================================================
        // Create Patient Page
        // =================================================

        PatientPage patientPage =
            new PatientPage(page);


        // =================================================
        // Step 2: Open Sidebar
        // =================================================

        System.out.println(
            "Opening sidebar..."
        );

        patientPage.openSidebar();

        page.waitForTimeout(1000);


        // =================================================
        // Step 3: Open Patient Menu
        // =================================================

        System.out.println(
            "Opening Patient..."
        );

        patientPage.openPatientMenu();

        page.waitForTimeout(1000);


        // =================================================
        // Step 4: Open Patients
        // =================================================

        System.out.println(
            "Opening Patients..."
        );

        patientPage.openPatients();

        page.waitForURL(
            "**/patientsearch"
        );

        Assert.assertTrue(
            page.url().contains(
                "/patientsearch"
            ),
            "Patients Search page was not opened"
        );

        System.out.println(
            "PATIENT SEARCH PAGE OPENED"
        );


        // =================================================
        // Step 5: Search Patient
        // =================================================

        System.out.println(
            "Searching Patient ID: "
            + patientId
        );

        patientPage.searchPatient(
            patientId
        );

        System.out.println(
            "PATIENT SEARCH COMPLETED"
        );


        // =================================================
        // Step 6: Open Patient Details
        // =================================================

        System.out.println(
            "Opening Patient Details: "
            + patientId
        );

        Page patientDetailsPage =
            patientPage.openPatient(
                patientId
            );


        // =================================================
        // Step 7: Verify Patient Details
        // =================================================

        System.out.println(
            "PATIENT DETAILS PAGE OPENED"
        );

        System.out.println(
            "Patient Details URL: "
            + patientDetailsPage.url()
        );

        Assert.assertTrue(
            patientDetailsPage.url()
                .contains("/patientdetails/"),
            "Patient Details page was not opened"
        );


        // =================================================
        // Wait 10 seconds
        // =================================================

        System.out.println(
            "Waiting 10 seconds before logout..."
        );

        patientDetailsPage.waitForTimeout(
            10000
        );


        // =================================================
        // Step 8: Logout
        // =================================================

        System.out.println(
            "Starting logout..."
        );

        LogoutPage logoutPage =
            new LogoutPage(
                patientDetailsPage
            );

        logoutPage.performLogout();


        // =================================================
        // Test Complete
        // =================================================

        System.out.println(
            "LOGOUT SUCCESS"
        );

        System.out.println(
            "Patient test completed: "
            + patientId
        );

        patientDetailsPage.waitForTimeout(
            5000
        );
    }
}
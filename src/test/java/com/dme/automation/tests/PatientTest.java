package com.dme.automation.tests;

import com.dme.automation.base.BaseTest;
import com.dme.automation.pages.LoginPage;
import com.dme.automation.pages.LogoutPage;
import com.dme.automation.pages.PatientPage;
import com.microsoft.playwright.Page;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PatientTest extends BaseTest {

    private static final String BASE_URL =
            "https://ui-dev.dmeez.in/";

    private static final String PATIENT_ID =
            "PTN1000001238";


    @Test
    public void testPatientFlow() {

        // =====================================================
        // Get Login Credentials
        // =====================================================

        String username = System.getenv("DME_USERNAME");
        String password = System.getenv("DME_PASSWORD");

        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {

            throw new IllegalStateException(
                "Set DME_USERNAME and DME_PASSWORD environment variables."
            );
        }


        // =====================================================
        // Step 1: Login
        // =====================================================

        System.out.println(
            "Opening application..."
        );

        LoginPage loginPage =
                new LoginPage(page);

        loginPage.openApplication(BASE_URL);

        loginPage.login(
            username,
            password
        );

        page.waitForTimeout(2000);

        System.out.println(
            "LOGIN SUCCESS"
        );

        System.out.println(
            "Current URL: " + page.url()
        );


        // =====================================================
        // Create Patient Page
        // =====================================================

        PatientPage patientPage =
                new PatientPage(page);


        // =====================================================
        // Step 2: Open Sidebar
        // =====================================================

        System.out.println(
            "Opening sidebar..."
        );

        patientPage.openSidebar();

        page.waitForTimeout(1000);


        // =====================================================
        // Step 3: Open Patient
        // =====================================================

        System.out.println(
            "Opening Patient..."
        );

        patientPage.openPatientMenu();

        page.waitForTimeout(1000);


        // =====================================================
        // Step 4: Open Patients
        // =====================================================

        System.out.println(
            "Opening Patients..."
        );

        patientPage.openPatients();

        page.waitForURL(
            "**/patientsearch"
        );

        System.out.println(
            "PATIENT SEARCH PAGE OPENED"
        );

        System.out.println(
            "Current URL: " + page.url()
        );

        Assert.assertTrue(
            page.url().contains("/patientsearch"),
            "Patients Search page was not opened"
        );


        // =====================================================
        // Step 5: Enter Patient ID + Search
        // =====================================================

        System.out.println(
            "Entering Patient ID: " + PATIENT_ID
        );

        patientPage.searchPatient(
            PATIENT_ID
        );

        System.out.println(
            "PATIENT SEARCH COMPLETED"
        );


        // =====================================================
        // Step 6: Open Patient Details
        // =====================================================

        System.out.println(
            "Clicking Patient ID: " + PATIENT_ID
        );

        Page patientDetailsPage =
                patientPage.openPatient(
                    PATIENT_ID
                );


        // =====================================================
        // Step 7: Verify Patient Details
        // =====================================================

        System.out.println(
            "PATIENT DETAILS PAGE OPENED"
        );

        System.out.println(
            "Patient Details URL: "
            + patientDetailsPage.url()
        );

        Assert.assertTrue(
            patientDetailsPage.url().contains(
                "/patientdetails/"
            ),
            "Patient Details page was not opened"
        );

        // Wait 10 seconds so you can see the Patient Details page
        System.out.println("Waiting 10 seconds before logout...");
        patientDetailsPage.waitForTimeout(10000);


        // =====================================================
        // Step 8: Logout
        // =====================================================

        System.out.println(
            "Starting logout..."
        );

        /*
         * IMPORTANT:
         *
         * Logout is performed on the NEW Patient Details tab,
         * not on the original Patients Search page.
         */
        LogoutPage logoutPage =
                new LogoutPage(
                    patientDetailsPage
                );

        logoutPage.performLogout();


        // =====================================================
        // Logout Successful
        // =====================================================

        System.out.println(
            "LOGOUT SUCCESS"
        );

        // Keep the browser open briefly so you can see
        // the final state.
        patientDetailsPage.waitForTimeout(5000);
    }
}
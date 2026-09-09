package com.dme.automation.utils;

import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {

    private final String filePath;

    public ExcelReader(String filePath) {
        this.filePath = filePath;
    }

    // =========================================================
    // Read Patient IDs
    // =========================================================

    public List<String> getPatientIds(String sheetName) {

        List<String> patientIds = new ArrayList<>();

        try (
                FileInputStream inputStream =
                        new FileInputStream(filePath);

                Workbook workbook =
                        WorkbookFactory.create(inputStream)
        ) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IllegalArgumentException(
                        "Sheet not found: " + sheetName
                );
            }

            DataFormatter formatter =
                    new DataFormatter();

            for (int i = 1;
                 i <= sheet.getLastRowNum();
                 i++) {

                Row row = sheet.getRow(i);

                if (row == null) {
                    continue;
                }

                Cell cell = row.getCell(0);

                if (cell == null) {
                    continue;
                }

                String patientId =
                        formatter
                                .formatCellValue(cell)
                                .trim();

                if (!patientId.isBlank()) {
                    patientIds.add(patientId);
                }
            }

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to read Excel file: "
                            + filePath,
                    e
            );
        }

        return patientIds;
    }


    // =========================================================
    // Read Sales Order IDs
    // =========================================================

    public List<String> getSalesOrderIds(String sheetName) {

        List<String> salesOrderIds =
                new ArrayList<>();

        try (
                FileInputStream inputStream =
                        new FileInputStream(filePath);

                Workbook workbook =
                        WorkbookFactory.create(inputStream)
        ) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IllegalArgumentException(
                        "Sheet not found: " + sheetName
                );
            }

            DataFormatter formatter =
                    new DataFormatter();

            // Skip row 0 because it contains the header
            for (int i = 1;
                 i <= sheet.getLastRowNum();
                 i++) {

                Row row = sheet.getRow(i);

                if (row == null) {
                    continue;
                }

                Cell cell = row.getCell(0);

                if (cell == null) {
                    continue;
                }

                String salesOrderId =
                        formatter
                                .formatCellValue(cell)
                                .trim();

                if (!salesOrderId.isBlank()) {
                    salesOrderIds.add(salesOrderId);
                }
            }

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to read Excel file: "
                            + filePath,
                    e
            );
        }

        return salesOrderIds;
    }
}
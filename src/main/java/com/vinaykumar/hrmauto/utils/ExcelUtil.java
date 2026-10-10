package com.vinaykumar.hrmauto.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ExcelUtil - Reads Excel (.xlsx) files and converts to Object[][] for @DataProvider
 */
public class ExcelUtil {

    /**
     * Reads specified sheets from Excel file and returns combined data as Object[][]
     *
     * @param filePath   Path to Excel file
     * @param sheetNames Names of sheets to read
     * @return Object[][] where each row = test scenario, each column = field value
     */
    public static Object[][] readExcelData(String filePath, String... sheetNames) throws IOException {

        // Open Excel file
        FileInputStream fileInputStream = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fileInputStream);

        // Collect all rows from all sheets
        List<Object[]> allRows = new ArrayList<>();
        int columnCount = 0;  // ← Add this

        // Loop through each sheet name
        for (String sheetName : sheetNames) {
            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                System.err.println("WARNING: Sheet '" + sheetName + "' not found");
                continue;
            }

            // Read header ONCE per sheet (before row loop)
            Row headerRow = sheet.getRow(0);
            columnCount = headerRow.getLastCellNum();

            // THEN loop through data rows
            int totalRows = sheet.getPhysicalNumberOfRows();

            for (int rowIndex = 1; rowIndex < totalRows; rowIndex++) {
                Row row = sheet.getRow(rowIndex);

                if (row == null) {
                    continue;
                }

                // Extract all the cells from this row - Create array with columnCount empty slots
                Object[] rowData = new Object[columnCount];

                for (int cellIndex = 0; cellIndex < columnCount; cellIndex++) {
                    Cell cell = row.getCell(cellIndex);

                    if (cell == null) {
                        rowData[cellIndex] = null;
                    } else {
                        // Read cell value based on type
                        CellType cellType = cell.getCellType();

                        if (cellType == CellType.STRING) {
                            rowData[cellIndex] = cell.getStringCellValue();
                        } else if (cellType == CellType.NUMERIC) {
                            // Convert numeric to String (for phone, etc.)
                            double numericValue = cell.getNumericCellValue();
                            rowData[cellIndex] = String.valueOf((long) numericValue);
                        } else if (cellType == CellType.BOOLEAN) {
                            rowData[cellIndex] = cell.getBooleanCellValue();
                        } else {
                            rowData[cellIndex] = null;
                        }
                    }
                }

                allRows.add(rowData);
            }
        }

        // Close file
        try {
            workbook.close();
            fileInputStream.close();
        } catch (IOException e) {
            System.err.println("ERROR: Could not close Excel file");
        }

        // Convert List to Object[][]
        Object[][] testData = new Object[allRows.size()][columnCount];

        for (int i = 0; i < allRows.size(); i++) {
            testData[i] = allRows.get(i);
        }

        return testData;
    }
}
package WSS.WSS_Module;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReaderTest {
	
	// Method to get data by row and column index
    public static String getDataByIndex(String filePath, int sheetIndex, int rowIndex, int columnIndex) {
        String data = null;
        try (FileInputStream fis = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
             
            XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
            XSSFRow row = sheet.getRow(rowIndex);
            Cell cell = row.getCell(columnIndex);
            data = getCellValue(cell);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    // Method to get data by row and column name
    public static String getDataByColumnName(String filePath, String sheetName, String rowIdentifier, String columnName) {
        String data = null;
        try (FileInputStream fis = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);
            XSSFRow headerRow = sheet.getRow(0); // Assuming first row is the header row

            // Get column index from column name
            Map<String, Integer> columnMap = new HashMap<>();
            for (Cell cell : headerRow) {
                columnMap.put(cell.getStringCellValue(), cell.getColumnIndex());
            }
            Integer targetColumnIndex = columnMap.get(columnName);
            if (targetColumnIndex == null) throw new IllegalArgumentException("Column '" + columnName + "' not found.");

            // Search the row using row identifier
            for (Row row : sheet) {
                Cell identifierCell = row.getCell(0); // Assuming search is based on first column
                
                if (identifierCell != null && getCellValue(identifierCell).equals(rowIdentifier)) {
                    Cell targetCell = row.getCell(targetColumnIndex);
                    data = getCellValue(targetCell);
                    break;
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    // Method to get entire column data
    public static List<String> getEntireColumnData(String filePath, int sheetIndex, String columnName) {
        List<String> columnData = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
            Row headerRow = sheet.getRow(0); // Assuming first row is header row
            int columnIndex = -1;

            // Find the column index
            for (Cell cell : headerRow) {
                if (cell.getStringCellValue().equals(columnName)) {
                    columnIndex = cell.getColumnIndex();
                    break;
                }
            }

            // Add each cell's value from that column
            for (Row row : sheet) {
                Cell cell = row.getCell(columnIndex);
                if (cell != null) {
                    columnData.add(getCellValue(cell));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return columnData;
    }

    // Method to get entire row data
    public static List<String> getEntireRowData(String filePath, int sheetIndex, int rowIndex) {
        List<String> rowData = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
            Row row = sheet.getRow(rowIndex);

            for (Cell cell : row) {
                rowData.add(getCellValue(cell));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowData;
    }

    // Method to get all data from the sheet
    public static List<List<String>> getAllDataFromSheet(String filePath, int sheetIndex) {
        List<List<String>> allData = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheetAt(sheetIndex);

            for (Row row : sheet) {
                List<String> rowData = new ArrayList<>();
                for (Cell cell : row) {
                    rowData.add(getCellValue(cell));
                }
                allData.add(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allData;
    }

    // Helper method to get cell value
    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                	// Handle the case where the cell contains a date
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Define the date format as DD/MM/YYYY
                    return dateFormat.format(cell.getDateCellValue()); // Format date and return
                } else {
                    // Handle numeric data
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == (long) numericValue) {
                        // If the number is an integer, return it as such
                        return String.valueOf((long) numericValue);
                    } else {
                        // Otherwise, return the decimal value
                        return String.valueOf(numericValue);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}

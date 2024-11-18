package WSS.WSS_Module;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class excelReader {
	public static List<Object> readDataFromExcel(String filePath, int sheetIndex, int rowIndex,int cellindex) {
        List<Object> data = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
            XSSFRow row = sheet.getRow(rowIndex);
            Cell cell = row.getCell(cellindex);
          //  for (Cell cell : row.getCell(cellindex)) 
            {
                switch (cell.getCellType()) {
                    case STRING:
                        data.add(cell.getStringCellValue());
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/DD/YYYY");
                            data.add(dateFormat.format(cell.getDateCellValue())); // Add formatted date
                        } else {
                            data.add(String.valueOf(cell.getNumericCellValue())); // Add numeric value as string
                        }
                        break;
                    default:
                        data.add(""); // Add empty string if cell is blank or has unsupported data type
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}

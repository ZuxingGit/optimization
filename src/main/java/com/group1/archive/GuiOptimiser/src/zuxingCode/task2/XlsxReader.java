package zuxingCode.task2;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class XlsxReader {

    public static void main(String[] args) {
        XlsxReader xlsxReader = new XlsxReader();
        xlsxReader.readSheet("GuiOptimiser/src/zuxingCode/task2/sbse-assignment02-nexus6.xlsx", "screenOn");
    }

    public void readSheet(String filePath, String sheetName) {
        try (FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);

            for (Row row : sheet) {
                for (Cell cell : row) {
                    String cellValue = new DataFormatter().formatCellValue(cell);
                    System.out.print(cellValue + "\t");
                }
                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

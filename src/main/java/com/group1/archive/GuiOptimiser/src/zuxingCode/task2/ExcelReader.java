package com.group1.archive.GuiOptimiser.src.zuxingCode.task2;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelReader {
    public static void main(String[] args) {
        ExcelReader excelReader = new ExcelReader();
        List<List<String>> records = excelReader.readExcel("GuiOptimiser/src/zuxingCode/task2/sbse-assignment02-nexus6.xlsx");
        int i = 0;
        do {
            System.out.println(records.get(i));
            i++;
        } while (i < 5);
        // for (List<String> record : records) {
        // for (String field : record) {
        // System.out.print(field + " ");
        // }
        // System.out.println();
        // }
    }

    public List<List<String>> readExcel(String filePath) {
        List<List<String>> records = new ArrayList<>();
        try {
            FileInputStream excelFile = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                List<String> record = new ArrayList<>();
                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    if (currentCell.getCellType() == CellType.STRING) {
                        record.add(currentCell.getStringCellValue());
                    } else if (currentCell.getCellType() == CellType.NUMERIC) {
                        record.add(String.valueOf(currentCell.getNumericCellValue()));
                    }
                }
                records.add(record);
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }
}
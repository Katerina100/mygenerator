package com.mygenerator.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {
    public static void main(String[] args) {
        Workbook book = new XSSFWorkbook();

        Sheet sheet = book.createSheet("Generated");

        Row row = sheet.createRow(0);

        Cell name = row.createCell(0);
        name.setCellValue("Вася");

        Cell birthdate = row.createCell(1);

        DataFormat format = book.createDataFormat();
        CellStyle dateStyle = book.createCellStyle();
        dateStyle.setDataFormat(format.getFormat("dd.mm.yyyy"));
        birthdate.setCellStyle(dateStyle);

        DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        try {
            Date date = formatter.parse("01/29/02");
            birthdate.setCellValue(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        sheet.autoSizeColumn(1);

        try {
            book.write(new FileOutputStream(new File("people.xlsx")));
            book.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

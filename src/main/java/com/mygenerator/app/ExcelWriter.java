package com.mygenerator.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {
    public static void main(String[] args) {
        try {
            Workbook book = new XSSFWorkbook();

            List<Person> people = new ArrayList<Person>();

            List<String> mensLasNames = Files.readAllLines(Paths.get("resources/men/mensLastNames.txt"),
                    Charset.forName("UTF-8"));
            String[] mensLastNamesArray = mensLasNames.toArray(new String[mensLasNames.size()]);

            List<String> mensFirstNames = Files.readAllLines(Paths.get("resources/men/mensFirstNames.txt"),
                    Charset.forName("UTF-8"));
            String[] mensFirstNamesArray = mensFirstNames.toArray(new String[mensFirstNames.size()]);

            List<String> mensPatronymics = Files.readAllLines(Paths.get("resources/men/mensPatronymics.txt"),
                    Charset.forName("UTF-8"));
            String[] mensPatronymicsArray = mensPatronymics.toArray(new String[mensPatronymics.size()]);

            Random rand = new Random();
            for (int i = 0; i < 30; i++) {
                people.add(new Person(mensLastNamesArray[rand.nextInt(mensLastNamesArray.length)],
                        mensFirstNamesArray[rand.nextInt(mensFirstNamesArray.length)],
                        mensPatronymicsArray[rand.nextInt(mensPatronymicsArray.length)], "01/29/02"));
            }

            createSheetContent(book, people);

            book.write(new FileOutputStream(new File("people.xlsx")));
            book.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createSheetContent(Workbook book, List<Person> people) {
        Sheet sheet = book.createSheet("Generated");

        for (int idx = 0; idx < people.size(); idx++) {
            Row row = sheet.createRow(idx);

            Cell fullName = row.createCell(0);
            fullName.setCellValue(people.get(idx).toString());
            sheet.autoSizeColumn(0);

            Cell birthDate = row.createCell(1);
            DataFormat format = book.createDataFormat();
            CellStyle dateStyle = book.createCellStyle();
            dateStyle.setDataFormat(format.getFormat("dd.mm.yyyy"));
            birthDate.setCellStyle(dateStyle);
            DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
            try {
                Date date = formatter.parse(people.get(idx).getBirthDate());
                birthDate.setCellValue(date);
                sheet.autoSizeColumn(1);
            } catch (ParseException e) {
                birthDate.setCellValue("Invalid Date");
                e.printStackTrace();
            }
        }
    }
}

package com.mygenerator.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.mygenerator.app.model.Person;
import com.mygenerator.app.util.RandomBirthDateGenerator;

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

            String[] maleLastNames = fetchResource("resources/male/maleLastNames.txt");
            String[] maleFirstNames = fetchResource("resources/male/maleFirstNames.txt");
            String[] malePatronymics = fetchResource("resources/male/malePatronymics.txt");

            String[] femaleLastNames = fetchResource("resources/female/femaleLastNames.txt");
            String[] femaleFirstNames = fetchResource("resources/female/femaleFirstNames.txt");
            String[] femalePatronymics = fetchResource("resources/female/femalePatronymics.txt");

            List<Person> people = new ArrayList<Person>();

            Random rand = new Random();
            for (int i = 0; i < 30; i++) {
                people.add(new Person(maleLastNames[rand.nextInt(maleLastNames.length)],
                        maleFirstNames[rand.nextInt(maleFirstNames.length)],
                        malePatronymics[rand.nextInt(malePatronymics.length)], RandomBirthDateGenerator.getNew()));
                people.add(new Person(femaleLastNames[rand.nextInt(femaleLastNames.length)],
                        femaleFirstNames[rand.nextInt(femaleFirstNames.length)],
                        femalePatronymics[rand.nextInt(femalePatronymics.length)], RandomBirthDateGenerator.getNew()));
            }

            createSheetContent(book, people);

            book.write(new FileOutputStream(new File("people.xlsx")));
            book.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createSheetContent(Workbook book, List<Person> people) {
        Date currentDate = new Date();

        Sheet sheet = book.createSheet("People");

        for (int idx = 0; idx < people.size(); idx++) {
            Person person = people.get(idx);

            Row row = sheet.createRow(idx);

            Cell fullName = row.createCell(0);
            fullName.setCellValue(person.toString());

            Cell birthDate = row.createCell(1);
            DataFormat format = book.createDataFormat();
            CellStyle dateStyle = book.createCellStyle();
            dateStyle.setDataFormat(format.getFormat("dd.mm.yyyy"));
            birthDate.setCellStyle(dateStyle);
            Date date = person.getBirthDate();
            birthDate.setCellValue(date);

            Cell age = row.createCell(2);
            int personAge = Period.between(getLocalDate(person.getBirthDate()), getLocalDate(currentDate)).getYears();
            age.setCellValue(personAge);

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
        }
    }

    private static String[] fetchResource(String resourceFileName) {
        List<String> resourcesArray = new ArrayList<String>();
        try {
            resourcesArray = Files.readAllLines(Paths.get(resourceFileName), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resourcesArray.toArray(new String[resourcesArray.size()]);
    }

    private static LocalDate getLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}

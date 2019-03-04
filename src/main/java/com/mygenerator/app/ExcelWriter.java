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
import com.mygenerator.app.util.RandomIndexGenerator;
import com.mygenerator.app.util.RandomValidInnGenerator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {
    public static void main(String[] args) {

        try {
            XSSFWorkbook book = new XSSFWorkbook();

            String[] maleLastNames = fetchResource("src/main/resources/male/maleLastNames.txt");
            String[] maleFirstNames = fetchResource("src/main/resources/male/maleFirstNames.txt");
            String[] malePatronymics = fetchResource("src/main/resources/male/malePatronymics.txt");

            String[] femaleLastNames = fetchResource("src/main/resources/female/femaleLastNames.txt");
            String[] femaleFirstNames = fetchResource("src/main/resources/female/femaleFirstNames.txt");
            String[] femalePatronymics = fetchResource("src/main/resources/female/femalePatronymics.txt");

            String[] peopleCountry = fetchResource("src/main/resources/people/peopleCountry.txt");
            String[] peopleRegion = fetchResource("src/main/resources/people/peopleRegion.txt");
            String[] peopleCity = fetchResource("src/main/resources/people/peopleCity.txt");
            String[] peopleStreet = fetchResource("src/main/resources/people/peopleStreet.txt");

            List<Person> people = new ArrayList<Person>();

            Random rand = new Random();

            int qnt = rand.nextInt(29) + 1;

            int qnt1 = rand.nextInt(qnt);

            int qnt2 = qnt - qnt1;

            for (int i = 0; i < qnt1; i++) {

                people.add(new Person(maleLastNames[rand.nextInt(maleLastNames.length)],
                        maleFirstNames[rand.nextInt(maleFirstNames.length)],
                        malePatronymics[rand.nextInt(malePatronymics.length)],"м", RandomBirthDateGenerator.getNew(),
                        RandomValidInnGenerator.getNew(), RandomIndexGenerator.getNew(), peopleCountry[rand.nextInt(peopleCountry.length)],
                        peopleRegion[rand.nextInt(peopleRegion.length)], peopleCity[rand.nextInt(peopleCity.length)],
                        peopleStreet[rand.nextInt(peopleStreet.length)], rand.nextInt(29) + 1, rand.nextInt(499) + 1));
            }


            for (int i = 0; i < qnt2; i++) {

                people.add(new Person(femaleLastNames[rand.nextInt(femaleLastNames.length)],
                        femaleFirstNames[rand.nextInt(femaleFirstNames.length)],
                        femalePatronymics[rand.nextInt(femalePatronymics.length)],"ж", RandomBirthDateGenerator.getNew(),
                        RandomValidInnGenerator.getNew(),RandomIndexGenerator.getNew(), peopleCountry[rand.nextInt(peopleCountry.length)],
                        peopleRegion[rand.nextInt(peopleRegion.length)], peopleCity[rand.nextInt(peopleCity.length)],
                        peopleStreet[rand.nextInt(peopleStreet.length)], rand.nextInt(29) + 1, rand.nextInt(499) + 1));
            }

            createSheetContent(book, people);

            File xlsFile = new File("people.xlsx");

            book.write(new FileOutputStream(xlsFile));

            book.close();

            System.out.printf("Сгенерирован список из %s записей%n", people.size());

            System.out.printf("Создан XLSX файл: %s%n", xlsFile.getAbsolutePath());

            PdfGenerator.generate(xlsFile.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createSheetContent(XSSFWorkbook book, List<Person> people) {
        Date currentDate = new Date();

        Sheet sheet = book.createSheet("Список людей");

        XSSFFont headerFont = book.createFont();
        headerFont.setBold(true);

        Row headerRow = sheet.createRow(0);

        Cell fullNameHeader = headerRow.createCell(0);
        fullNameHeader.setCellValue("ФИО");

        Cell ageHeader = headerRow.createCell(1);
        ageHeader.setCellValue("Возраст");

        Cell sexHeader = headerRow.createCell(2);
        sexHeader.setCellValue("Пол");

        Cell birthDateHeader = headerRow.createCell(3);
        birthDateHeader.setCellValue("Дата рождения");

        Cell innHeader = headerRow.createCell(4);
        innHeader.setCellValue("ИНН");

        Cell indexHeader = headerRow.createCell(5);
        indexHeader.setCellValue("Индекс");

        Cell countryHeader = headerRow.createCell(6);
        countryHeader.setCellValue("Страна");

        Cell regionHeader = headerRow.createCell(7);
        regionHeader.setCellValue("Область");

        Cell cityHeader = headerRow.createCell(8);
        cityHeader.setCellValue("Город");

        Cell streetHeader = headerRow.createCell(9);
        streetHeader.setCellValue("Улица");

        Cell houseHeader = headerRow.createCell(10);
        houseHeader.setCellValue("Дом");

        Cell flatHeader = headerRow.createCell(11);
        flatHeader.setCellValue("Квартира");



        for (int idx = 0; idx < people.size(); idx++) {
            Person person = people.get(idx);

            Row row = sheet.createRow(idx + 1);

            Cell fullName = row.createCell(0);
            fullName.setCellValue(person.toString());

            Cell age = row.createCell(1);
            int personAge = Period.between(getLocalDate(person.getBirthDate()), getLocalDate(currentDate)).getYears();
            age.setCellValue(personAge);

            Cell sex = row.createCell(2);
            sex.setCellValue(person.getSex());

            Cell birthDate = row.createCell(3);
            DataFormat format = book.createDataFormat();
            CellStyle dateStyle = book.createCellStyle();
            dateStyle.setDataFormat(format.getFormat("dd.mm.yyyy"));
            birthDate.setCellStyle(dateStyle);
            Date date = person.getBirthDate();
            birthDate.setCellValue(date);

            Cell inn = row.createCell(4);
            inn.setCellValue(person.getInn());

            Cell index = row.createCell(5);
            index.setCellValue(person.getIndex());

            Cell country = row.createCell(6);
            country.setCellValue(person.getCountry());

            Cell region = row.createCell(7);
            region.setCellValue(person.getRegion());

            Cell city = row.createCell(8);
            city.setCellValue(person.getCity());

            Cell street = row.createCell(9);
            street.setCellValue(person.getStreet());

            Cell house = row.createCell(10);
            house.setCellValue(person.getHouse());

            Cell flat = row.createCell(11);
            flat.setCellValue(person.getFlat());



            makeRowBoldCenter(book, headerRow);

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);
            sheet.autoSizeColumn(7);
            sheet.autoSizeColumn(8);
            sheet.autoSizeColumn(9);
            sheet.autoSizeColumn(10);
            sheet.autoSizeColumn(11);
        }
    }

    private static void makeRowBoldCenter(XSSFWorkbook book, Row row) {
        CellStyle style = book.createCellStyle();
        Font font = book.createFont();
        font.setBold(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        for (int i = 0; i < row.getLastCellNum(); i++) {
            row.getCell(i).setCellStyle(style);
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

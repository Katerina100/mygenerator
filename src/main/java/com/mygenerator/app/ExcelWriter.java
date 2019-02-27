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
import com.mygenerator.app.util.RandomValidInnGenerator;
import com.mygenerator.app.util.RandomHouseGenerator;
import com.mygenerator.app.util.RandomFlatGenerator;

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

            String[] peopleCountry = fetchResource("resources/people/peopleCountry.txt");
            String[] peopleRegion = fetchResource("resources/people/peopleRegion.txt");
            String[] peopleCity = fetchResource("resources/people/peopleCity.txt");
            String[] peopleStreet = fetchResource("resources/people/peopleStreet.txt");

            List<Person> people = new ArrayList<Person>();

            Random rand = new Random();
            for (int i = 0; i < 30; i++) {
                people.add(new Person(maleLastNames[rand.nextInt(maleLastNames.length)],
                        maleFirstNames[rand.nextInt(maleFirstNames.length)],
                        malePatronymics[rand.nextInt(malePatronymics.length)], RandomBirthDateGenerator.getNew(), RandomValidInnGenerator.getNew(), peopleCountry[rand.nextInt(peopleCountry.length)], peopleRegion[rand.nextInt(peopleRegion.length)], peopleCity[rand.nextInt(peopleCity.length)], peopleStreet[rand.nextInt(peopleStreet.length)],RandomHouseGenerator.getNew(), RandomFlatGenerator.getNew()));
                people.add(new Person(femaleLastNames[rand.nextInt(femaleLastNames.length)],
                        femaleFirstNames[rand.nextInt(femaleFirstNames.length)],
                        femalePatronymics[rand.nextInt(femalePatronymics.length)], RandomBirthDateGenerator.getNew(), RandomValidInnGenerator.getNew(), peopleCountry[rand.nextInt(peopleCountry.length)], peopleRegion[rand.nextInt(peopleRegion.length)], peopleCity[rand.nextInt(peopleCity.length)], peopleStreet[rand.nextInt(peopleStreet.length)],RandomHouseGenerator.getNew(), RandomFlatGenerator.getNew()));
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

        Sheet sheet = book.createSheet("Список людей");
        
        Row headerRow = sheet.createRow(0);
        
        Cell fullNameHeader = headerRow.createCell(0);
        fullNameHeader.setCellValue("ФИО");
        
        Cell birthDateHeader = headerRow.createCell(1);
        birthDateHeader.setCellValue("Дата рождения");
        
        Cell ageHeader = headerRow.createCell(2);
        ageHeader.setCellValue("Возраст");

        Cell innHeader = headerRow.createCell(3);
        innHeader.setCellValue("ИНН");

        Cell countryHeader = headerRow.createCell(4);
        countryHeader.setCellValue("Страна");

        Cell regionHeader = headerRow.createCell(5);
        regionHeader.setCellValue("Область");

        Cell cityHeader = headerRow.createCell(6);
        cityHeader.setCellValue("Город");

        Cell streetHeader = headerRow.createCell(7);
        streetHeader.setCellValue("Улица");

        Cell houseHeader = headerRow.createCell(8);
        houseHeader.setCellValue("Дом");

        Cell flatHeader = headerRow.createCell(9);
        flatHeader.setCellValue("Квартира");

        for (int idx = 0; idx < people.size(); idx++) {
            Person person = people.get(idx);

            Row row = sheet.createRow(idx + 1);

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

            Cell inn = row.createCell(3);
            inn.setCellValue(person.getInn());

            Cell country = row.createCell(4);
            country.setCellValue(person.getCountry());

            Cell region = row.createCell(5);
            region.setCellValue(person.getRegion());

            Cell city = row.createCell(6);
            city.setCellValue(person.getCity());

            Cell street = row.createCell(7);
            street.setCellValue(person.getStreet());

            Cell house = row.createCell(8);
            house.setCellValue(person.getHouse());

            Cell flat = row.createCell(9);
            flat.setCellValue(person.getFlat());

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

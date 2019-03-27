package com.mygenerator.app.workers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.mygenerator.app.models.Person;
import com.mygenerator.app.utils.DateHelper;
import com.mygenerator.app.utils.JsonApiParser;
import com.mygenerator.app.utils.MySqlInsert;
import com.mygenerator.app.utils.RandomBirthDateGenerator;
import com.mygenerator.app.utils.RandomIndexGenerator;
import com.mygenerator.app.utils.RandomValidInnGenerator;
import com.mygenerator.app.utils.StubContentFetcher;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelCreator {
    private File xlsFile;
    private String outXlsxFilePathName;

    private static final String URL = "jdbc:mysql://localhost:3306/mygenerator?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static final String STUBS_DIRECTORY_PATH = "src/main/stubs";

    private static final String MALE_DIRECTORY_PATH = "/male";
    private static final String FEMALE_DIRECTORY_PATH = "/female";
    private static final String PEOPLE_DIRECTORY_PATH = "/people";

    private static final String MALE_LAST_NAMES_STUB = STUBS_DIRECTORY_PATH + MALE_DIRECTORY_PATH
            + "/maleLastNames.txt";
    private static final String MALE_FIRST_NAMES_STUB = STUBS_DIRECTORY_PATH + MALE_DIRECTORY_PATH
            + "/maleFirstNames.txt";
    private static final String MALE_PATRONYMICS_STUB = STUBS_DIRECTORY_PATH + MALE_DIRECTORY_PATH
            + "/malePatronymics.txt";

    private static final String FEMALE_LAST_NAMES_STUB = STUBS_DIRECTORY_PATH + FEMALE_DIRECTORY_PATH
            + "/femaleLastNames.txt";
    private static final String FEMALE_FIRST_NAMES_STUB = STUBS_DIRECTORY_PATH + FEMALE_DIRECTORY_PATH
            + "/femaleFirstNames.txt";
    private static final String FEMALE_PATRONYMICS_STUB = STUBS_DIRECTORY_PATH + FEMALE_DIRECTORY_PATH
            + "/femalePatronymics.txt";

    private static final String COUNTRIES_STUB = STUBS_DIRECTORY_PATH + PEOPLE_DIRECTORY_PATH + "/peopleCountry.txt";
    private static final String CITIES_STUB = STUBS_DIRECTORY_PATH + PEOPLE_DIRECTORY_PATH + "/peopleCity.txt";
    private static final String REGIONS_STUB = STUBS_DIRECTORY_PATH + PEOPLE_DIRECTORY_PATH + "/peopleRegion.txt";
    private static final String STREETS_STUB = STUBS_DIRECTORY_PATH + PEOPLE_DIRECTORY_PATH + "/peopleStreet.txt";

    private static final String SEX_M = "М";
    private static final String SEX_F = "Ж";

    private static final String SHEET_NAME = "Список людей";

    private static final String HEADER_ROW_CELL_LFP = "ФИО";
    private static final String HEADER_ROW_CELL_AGE = "Возраст";
    private static final String HEADER_ROW_CELL_SEX = "Пол";
    private static final String HEADER_ROW_CELL_BD = "Дата рождения";
    private static final String HEADER_ROW_CELL_INN = "ИНН";
    private static final String HEADER_ROW_CELL_ZIP = "Индекс";
    private static final String HEADER_ROW_CELL_COUNTRY = "Страна";
    private static final String HEADER_ROW_CELL_REGION = "Область";
    private static final String HEADER_ROW_CELL_CITY = "Город";
    private static final String HEADER_ROW_CELL_STREET = "Улица";
    private static final String HEADER_ROW_CELL_BUILDING = "Дом";
    private static final String HEADER_ROW_CELL_FLAT = "Квартира";

    private static final String LIST_CREATION_CONSOLE_OUTPUT = "Сгенерирован список из %s записей%n";
    private static final String XLS_FILE_CREATION_CONSOLE_OUTPUT = "Создан XLSX файл: %s%n";

    public ExcelCreator(String outXlsxFilePathName) {
        this.outXlsxFilePathName = outXlsxFilePathName;
    }

    public void create() {
        List<Person> people;
        try {
            people = createPeopleListFromApi();
            MySqlInsert.sqlConnectInsert(people);
        } catch (Exception e) {
            people = createPeopleListFromStub();
            MySqlInsert.sqlConnectInsert(people);
            e.printStackTrace();
        }

        XSSFWorkbook book = new XSSFWorkbook();

        Sheet sheet = book.createSheet(SHEET_NAME);

        Row headerRow = sheet.createRow(0);
        String[] headerRowContent = { HEADER_ROW_CELL_LFP, HEADER_ROW_CELL_AGE, HEADER_ROW_CELL_SEX, HEADER_ROW_CELL_BD,
                HEADER_ROW_CELL_INN, HEADER_ROW_CELL_ZIP, HEADER_ROW_CELL_COUNTRY, HEADER_ROW_CELL_REGION,
                HEADER_ROW_CELL_CITY, HEADER_ROW_CELL_STREET, HEADER_ROW_CELL_BUILDING, HEADER_ROW_CELL_FLAT };

        populateHeaderRow(headerRow, headerRowContent);
        populateSheetContent(people, book, sheet);

        makeRowBoldCenter(book, headerRow);
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            sheet.autoSizeColumn(i);
        }

        this.xlsFile = new File(this.outXlsxFilePathName);

        try (FileOutputStream fileOutputStream = new FileOutputStream(this.xlsFile)) {
            book.write(fileOutputStream);
            book.close();
            System.out.printf(XLS_FILE_CREATION_CONSOLE_OUTPUT, this.xlsFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getOutXlsFileAbsolutePath() {
        return this.xlsFile.getAbsolutePath();
    }

    private ArrayList<Person> createPeopleListFromApi() throws Exception {
        ArrayList<Person> people = new ArrayList<Person>();
        Random rand = new Random();

        for (int i = 0; i < rand.nextInt(29) + 1; i++) {
            people.add(JsonApiParser.fetchRandomUser());
        }

        System.out.printf(LIST_CREATION_CONSOLE_OUTPUT, people.size());

        return people;
    }

    private ArrayList<Person> createPeopleListFromStub() {
        ArrayList<Person> people = new ArrayList<Person>();

        Random rand = new Random();
        int qnt = rand.nextInt(29) + 1;
        int qnt1 = rand.nextInt(qnt);
        int qnt2 = qnt - qnt1;

        String[] peopleCountry = StubContentFetcher.get(COUNTRIES_STUB);
        String[] peopleRegion = StubContentFetcher.get(REGIONS_STUB);
        String[] peopleCity = StubContentFetcher.get(CITIES_STUB);
        String[] peopleStreet = StubContentFetcher.get(STREETS_STUB);

        String[] maleLastNames = StubContentFetcher.get(MALE_LAST_NAMES_STUB);
        String[] maleFirstNames = StubContentFetcher.get(MALE_FIRST_NAMES_STUB);
        String[] malePatronymics = StubContentFetcher.get(MALE_PATRONYMICS_STUB);

        for (int i = 0; i < qnt1; i++) {
            Person male = new Person.Builder().setLastName(maleLastNames[rand.nextInt(maleLastNames.length)])
                    .setFirstName(maleFirstNames[rand.nextInt(maleFirstNames.length)])
                    .setPatronymic(malePatronymics[rand.nextInt(malePatronymics.length)])
                    .setBirthDate(RandomBirthDateGenerator.getNew()).setInn(RandomValidInnGenerator.getNew())
                    .setIndex(RandomIndexGenerator.getNew())
                    .setCountry(peopleCountry[rand.nextInt(peopleCountry.length)])
                    .setRegion(peopleRegion[rand.nextInt(peopleRegion.length)])
                    .setCity(peopleCity[rand.nextInt(peopleCity.length)])
                    .setStreet(peopleStreet[rand.nextInt(peopleStreet.length)]).setHouse(rand.nextInt(29) + 1)
                    .setFlat(rand.nextInt(499) + 1).setSex(SEX_M).build();
            people.add(male);
        }

        String[] femaleLastNames = StubContentFetcher.get(FEMALE_LAST_NAMES_STUB);
        String[] femaleFirstNames = StubContentFetcher.get(FEMALE_FIRST_NAMES_STUB);
        String[] femalePatronymics = StubContentFetcher.get(FEMALE_PATRONYMICS_STUB);

        for (int i = 0; i < qnt2; i++) {
            Person female = new Person.Builder().setLastName(femaleLastNames[rand.nextInt(femaleLastNames.length)])
                    .setFirstName(femaleFirstNames[rand.nextInt(femaleFirstNames.length)])
                    .setPatronymic(femalePatronymics[rand.nextInt(femalePatronymics.length)])
                    .setBirthDate(RandomBirthDateGenerator.getNew()).setInn(RandomValidInnGenerator.getNew())
                    .setIndex(RandomIndexGenerator.getNew())
                    .setCountry(peopleCountry[rand.nextInt(peopleCountry.length)])
                    .setRegion(peopleRegion[rand.nextInt(peopleRegion.length)])
                    .setCity(peopleCity[rand.nextInt(peopleCity.length)])
                    .setStreet(peopleStreet[rand.nextInt(peopleStreet.length)]).setHouse(rand.nextInt(29) + 1)
                    .setFlat(rand.nextInt(499) + 1).setSex(SEX_F).build();
            people.add(female);
        }

        System.out.printf(LIST_CREATION_CONSOLE_OUTPUT, people.size());

        return people;
    }

    private void populateHeaderRow(Row headerRow, String[] headerRowContent) {
        for (int idx = 0; idx < headerRowContent.length; idx++) {
            Cell cell = headerRow.createCell(idx);
            cell.setCellValue(headerRowContent[idx]);
        }
    }

    private void populateSheetContent(List<Person> people, XSSFWorkbook book, Sheet sheet) {
        Date currentDate = new Date();

        for (int idx = 0; idx < people.size(); idx++) {
            Person person = people.get(idx);

            Row row = sheet.createRow(idx + 1);

            Cell fullName = row.createCell(0);
            fullName.setCellValue(person.toString());

            Cell age = row.createCell(1);
            int personAge = Period
                    .between(DateHelper.getLocalDate(person.getBirthDate()), DateHelper.getLocalDate(currentDate))
                    .getYears();
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
        }
    }

    private void makeRowBoldCenter(XSSFWorkbook book, Row row) {
        CellStyle style = book.createCellStyle();
        Font font = book.createFont();
        font.setBold(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        for (int i = 0; i < row.getLastCellNum(); i++) {
            row.getCell(i).setCellStyle(style);
        }
    }
}

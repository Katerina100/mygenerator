package com.mygenerator.app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class PdfGenerator {
    public static void generate(String xlsFileAbsolutePath) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            PDFont font = PDType0Font.load(document, new File("resources/fonts/DejaVuSans.ttf"));
            contentStream.setFont(font, 12);

            File excelFile = new File(xlsFileAbsolutePath);
            Workbook workBook = WorkbookFactory.create(excelFile);
            Sheet sheet = workBook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            for (int rn = 0; rn <= sheet.getLastRowNum(); rn++) {
                contentStream.beginText();

                Row row = sheet.getRow(rn);
                List<String> values = new ArrayList<String>();
                for (int cn = 0; cn < 11; cn++) {
                    Cell cell = row.getCell(cn);
                    String val = null;
                    if (cell != null) {
                        val = formatter.formatCellValue(cell);
                    }
                    values.add(val);
                }
                for (String item : values) {
                    contentStream.showText(item);
                }
                contentStream.endText();
            }

            workBook.close();

            contentStream.close();

            File pdfFile = new File("people.pdf");
            document.save(pdfFile);

            document.close();

            System.out.printf("Создан PDF файл: %s%n", pdfFile.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

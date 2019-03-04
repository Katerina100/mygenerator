package com.mygenerator.app.workers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class PdfCreator {
    private String srcXlsxFilePathName;

    public static final String FONT_PATH = "src/main/assets/fonts/DejaVuSans.ttf";
    public static final String PDF_SEPARATOR = "  |  ";
    public static final String PDF_FILE_CREATION_CONSOLE_OUTPUT = "Создан PDF файл: %s%n";

    public PdfCreator(String srcXlsxFilePathName) {
        this.srcXlsxFilePathName = srcXlsxFilePathName;
    }

    public void create(String outPdfFilePathName) {
        try {
            PDDocument document = new PDDocument();
            float pageWidth = 12f * 72;
            float pageHeight = 8f * 72;
            PDPage page = new PDPage(new PDRectangle(pageWidth, pageHeight));
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            PDFont font = PDType0Font.load(document, new File(FONT_PATH));
            contentStream.setFont(font, 10);

            File excelFile = new File(this.srcXlsxFilePathName);
            Workbook workBook = WorkbookFactory.create(excelFile);
            Sheet sheet = workBook.getSheetAt(0);

            contentStream.beginText();
            contentStream.newLineAtOffset(20, pageHeight - 25);
            contentStream.setLeading(14.5f);

            fillContent(sheet, contentStream);

            contentStream.endText();
            contentStream.close();
            workBook.close();

            File pdfFile = new File(outPdfFilePathName);
            document.save(pdfFile);
            document.close();

            System.out.printf(PDF_FILE_CREATION_CONSOLE_OUTPUT, pdfFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillContent(Sheet sheet, PDPageContentStream contentStream) {
        DataFormatter formatter = new DataFormatter();
        for (int rn = 1; rn <= sheet.getLastRowNum(); rn++) {
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
            try {
                for (int j = 0; j < values.size(); j++) {
                    contentStream.showText(values.get(j));
                    if (j < values.size() - 1) {
                        contentStream.showText(PDF_SEPARATOR);
                    }
                }
                contentStream.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

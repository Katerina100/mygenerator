package com.mygenerator.app;

import com.mygenerator.app.utils.MySqlInsert;
import com.mygenerator.app.workers.ExcelCreator;
import com.mygenerator.app.workers.PdfCreator;


public class MyGenerator {
    public static void main(String[] args) {

        ExcelCreator excelCreator = new ExcelCreator("people.xlsx");
        excelCreator.create();

        PdfCreator pdfCreator = new PdfCreator(excelCreator.getOutXlsFileAbsolutePath());
        pdfCreator.create("people.pdf");
    }
}
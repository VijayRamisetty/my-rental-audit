package com.vj.rentalaudit.excel.writer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.vj.rentalaudit.pojo.Report;

public class ExcelWriter {

	private static String[] columns = {"itemName", "monthStartDate", "monthEndDate", "rentToPay" };


	public static void writeIntoExcel(String outputpath, List<Report> rentalForecast) throws FileNotFoundException, IOException, EncryptedDocumentException, InvalidFormatException {

		
		String sheetName = rentalForecast.get(0).getItemName();
		
		File outputFile = new File(outputpath);
		
		FileInputStream inputStream = null ;

		Workbook book ;
		
		if(outputFile.exists())
		{   System.out.println("file already exisits - adding ["+sheetName+"] to it");
			inputStream = new FileInputStream(outputFile);
			book = WorkbookFactory.create(inputStream);

		}else {
			System.out.println("file not exisits - creating one with sheet - ["+sheetName+"]");

			book =  new XSSFWorkbook();
		}
		
		Sheet sheet = book.createSheet(sheetName);


		/* CreationHelper helps us create instances of various things like DataFormat, 
	           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
		CreationHelper createHelper = book.getCreationHelper();


		// Create a Font for styling header cells
		Font headerFont = book.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.RED.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = book.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Create a Row
		Row headerRow = sheet.createRow(0);

		// Create cells
		for(int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = book.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

		// Create Other rows and cells with employees data
		int rowNum = 1;

		for(Report r : rentalForecast) {
			Row row = sheet.createRow(rowNum++);

			row.createCell(0).setCellValue(r.getItemName());
			Cell cell1 = row.createCell(1);
			cell1.setCellValue(r.getMonthStartDate());
			cell1.setCellStyle(dateCellStyle);
			Cell cell2 = row.createCell(2);
			cell2.setCellValue(r.getMonthEndDate());
			cell2.setCellStyle(dateCellStyle);

			row.createCell(3).setCellValue(r.getRentToPay());

		}

		// Resize all columns to fit the content size
		for(int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		// Write the output to a file
		FileOutputStream fileOutStream = new FileOutputStream(outputpath);
		book.write(fileOutStream);
		fileOutStream.close();

		// Closing the workbook
		book.close();
		
		if(inputStream!= null)
			inputStream.close();


	}
}

package com.vj.rentalaudit.util;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.vj.rentalaudit.pojo.LeaseEntity;

public class LeaseEntityWriter {

	
	String itemName;
	String fromDate;
	String toDate;
	int initialRent;
	int edays;
	int eMonths;
	int eYears;
	double ePercentage;
	int eAmount;
	
    private static String[] columns = {"itemName", "fromDate", "toDate", "initialRent" ,"edays", "eMonths","eYears","ePercentage" ,"eAmount"};
   
    private static List<LeaseEntity> leaseEntities =  new ArrayList<>();

	// Initializing leaseEntities data to insert into the excel file
    static {
        
    	
    	leaseEntities.add(new LeaseEntity("ItemA", "01/01/2015", "31/10/2024", 52000, 0, 0, 3, 15.0, 0));
    	//leaseEntities.add(new LeaseEntity("ItemB", "15/06/2019", "26/03/2023", 20000, 0, 3, 1, 10.0, 0));

    }

    public static void main(String[] args) throws IOException, InvalidFormatException {
       
    	createSampleWorkSheet("lease-input.xlsx");
    	
    }

	public static void createSampleWorkSheet(String fileName) throws IOException {

		// Create a Workbook
        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

        /* CreationHelper helps us create instances of various things like DataFormat, 
           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        CreationHelper createHelper = workbook.getCreationHelper();

        // Create a Sheet
        Sheet sheet = workbook.createSheet("LeaseEntitySheet");

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
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
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        // Create Other rows and cells with employees data
        int rowNum = 1;
        for(LeaseEntity le: leaseEntities) {
            Row row = sheet.createRow(rowNum++);

            // "itemName", "fromDate", "toDate", "initialRent" ,"edays", "eMonths","eYears","ePercentage" ,"eAmount";

            row.createCell(0).setCellValue(le.getItemName());

            Cell cell = row.createCell(1);
            cell.setCellValue(le.getFromDate());
            cell.setCellStyle(dateCellStyle);

            Cell cell2 = row.createCell(2);
            cell2.setCellValue(le.getToDate());
            cell2.setCellStyle(dateCellStyle);

            row.createCell(3).setCellValue(le.getInitialRent());
            row.createCell(4).setCellValue(le.getEdays());
            row.createCell(5).setCellValue(le.geteMonths());
            row.createCell(6).setCellValue(le.geteYears());
            row.createCell(7).setCellValue(le.getePercentage());
            row.createCell(8).setCellValue(le.geteAmount());

        }

		// Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();

        // Closing the workbook
        workbook.close();
	}
}
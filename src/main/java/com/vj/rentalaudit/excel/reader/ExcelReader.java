package com.vj.rentalaudit.excel.reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.vj.rentalaudit.pojo.LeaseEntity;

public class ExcelReader {
	
	// Create a DataFormatter to format and get each cell's value as String
	public static DataFormatter dataFormatter = new DataFormatter();

	public static List<LeaseEntity> readFromExcel(String file) throws IOException {

		// reading Excel file
		XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));
		System.out.println("Number of sheets: " + myExcelBook.getNumberOfSheets());
		// Getting the Sheet at index zero
		XSSFSheet myExcelSheet = myExcelBook.getSheetAt(0);
		
		//Iterating over Rows and Columns using for-each loop
		System.out.println("Processing each row in sheet (1) only ....");

		List<LeaseEntity> leaseEntities = new ArrayList<>();

		
		//displayExcel(myExcelSheet);
		
		
		
		for (Row row : myExcelSheet) {
			
			if(row.getRowNum()==0 ){
				   continue; //skip header
			}
			
			leaseEntities.add(new LeaseEntity(
			row.getCell(0).toString(),
			row.getCell(1).toString(),
			row.getCell(2).toString(),
			(int)row.getCell(3).getNumericCellValue(),
			(int)row.getCell(4).getNumericCellValue(),
			(int)row.getCell(5).getNumericCellValue(),
			(int)row.getCell(6).getNumericCellValue(),
			row.getCell(7).getNumericCellValue(),
			(int)row.getCell(8).getNumericCellValue()
			));
			
		}
		
		printLeaseEntities(leaseEntities);
		

		myExcelBook.close();

		return leaseEntities;
	}

	
	private static void printLeaseEntities(List<LeaseEntity> leaseEntities) {
		System.out.println("--------------------------------------");

		for (int i = 0; i < leaseEntities.size(); i++) {

			System.out.println(leaseEntities.get(i));
		}

		System.out.println("--------------------------------------");		
	}

	private static void displayExcel(XSSFSheet myExcelSheet) {

		for (Row row : myExcelSheet) {
			for (Cell cell : row) {
				String cellValue = dataFormatter.formatCellValue(cell);
				System.out.print(cellValue + "\t");
			}
			System.out.println();
		}
	}
}

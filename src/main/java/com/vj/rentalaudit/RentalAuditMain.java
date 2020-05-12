package com.vj.rentalaudit;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.vj.rentalaudit.calc.RentalAuditCalculator;
import com.vj.rentalaudit.excel.reader.ExcelReader;
import com.vj.rentalaudit.excel.writer.ExcelWriter;
import com.vj.rentalaudit.pojo.LeaseEntity;
import com.vj.rentalaudit.pojo.Report;
import com.vj.rentalaudit.util.LeaseEntityWriter;

public class RentalAuditMain {

	public static void main(String[] args) throws InvalidFormatException, IOException {

		System.out.println(args.length);
		
		//  arguments parsing 
		
		String inputPath ;
		String outputPath ;
		
		
		if(args.length <=0) {
	       
			System.out.println("---- How to run jar -------");
			System.out.println("java -jar my-rental-audit-0.0.1-SNAPSHOT-jar-with-dependencies.jar <input.xlsx> <output.xlsx> ");
			System.out.println("---- for demo : try below command---- ");
			System.out.println("java -jar my-rental-audit-0.0.1-SNAPSHOT-jar-with-dependencies.jar  demo");
			throw new IllegalArgumentException("Please execute as follows");
	       
	    }
	
		if(args.length == 1) 
		{
			System.out.println("Running demo ");
			
			inputPath= "lease-input.xlsx";
			outputPath= "lease-output.xlsx";
			
			// creating sample excel
			
			LeaseEntityWriter.createSampleWorkSheet(inputPath);
			
		}else {
			
			inputPath = args[0];
			outputPath = args[1];
			
		}
		
		File outputFile = new File(outputPath);
		// cleanup
		if(outputFile.exists()) outputFile.delete(); 
	
		System.out.println("RentalAudit Started....");
		
		try {
			// read  
			List<LeaseEntity> LeaseEntitiesList = ExcelReader.readFromExcel(inputPath);
			
			// process 
			
			for (int i = 0; i < LeaseEntitiesList.size(); i++) {
				
				List<Report> rentalForecast = RentalAuditCalculator.getRentalForecast(LeaseEntitiesList.get(i));

				// write 
				ExcelWriter.writeIntoExcel(outputPath , rentalForecast);
			}
			

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		System.out.println("RentalAudit completed....");
	}

}

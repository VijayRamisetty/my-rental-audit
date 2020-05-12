package com.vj.rentalaudit.calc;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vj.rentalaudit.pojo.LeaseEntity;
import com.vj.rentalaudit.pojo.Report;



public class RentalAuditCalculator {
	
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	public static final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static final DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

	
	
	

	public static double getPartialRent(String startdate, String enddate, double rentPerMonth) {
		
		
		
        LocalDate startDate = LocalDate.parse(startdate);
        LocalDate endDate = LocalDate.parse(enddate);
        
        double finalRent ;
        
        double billingdays = ChronoUnit.DAYS.between(startDate, endDate) + 1 ;
      //  System.out.println( "billingDays : "+ billingdays);
 
        double daysInMonth = startDate.lengthOfMonth();
      //  System.out.println(" daysInMonth : " + daysInMonth);
      	        
        if( ( billingdays /  daysInMonth ) == 0 )
        {
        //	System.out.println("complete month");
       // 	System.out.println(rentPerMonth);
        	finalRent = rentPerMonth;
        }
        else {
        //	System.out.println("Only "+ billingdays+"days");
        	
        	finalRent = (rentPerMonth/daysInMonth)* billingdays;
        //	System.out.println(finalRent);
        	
        } 
        
        return  finalRent;
        
}
	
	public static List<Report> getRentalForecast(LeaseEntity le){
		
		
		String itemName =le.getItemName() ;
		String from=le.getFromDate();
		String toDate=le.getToDate();
		
		double initialRent=le.getInitialRent();
		double ePercentage=le.getePercentage();


		int edays=le.getEdays();		// not in use for now
		int eMonths=le.geteMonths();
		int eYears=le.geteYears();
		
		int rentchangemonths = eMonths + ( eYears*12); 

		
		int eAmount = le.geteAmount();  // not in use for now
		
		LocalDate startDate = null ;
		LocalDate endDate  = null;
		
		if(le.getFromDate().contains("/"))
		{
			startDate = LocalDate.parse(from,formatter);
			endDate = LocalDate.parse(toDate, formatter);
		}
		else if(le.getFromDate().contains("-")) {
			startDate = LocalDate.parse(from,formatter3);
		    endDate = LocalDate.parse(toDate, formatter3);
		}
	    
	    LocalDate current = startDate;
		double tempRent = initialRent;
			
		
		List<LocalDate> batchStart = new ArrayList<LocalDate>();
		List<LocalDate> batchEnd = new ArrayList<LocalDate>();
		List<Double> batchRent = new ArrayList<Double>();
		
		// creating  batches 
		
		batchStart.add(startDate);
		batchRent.add(initialRent);
		
		while (true) {
			 current = current.plusMonths(rentchangemonths);
			 tempRent= tempRent + ((tempRent/100)* ePercentage);
			 if(current.isAfter(endDate)  ) {
				 break;
			 }
			 if(!current.isEqual(endDate) ) {
				 batchStart.add(current) ;
				 batchEnd.add(current.minusDays(1));
				 batchRent.add(tempRent);
			 } 
		}

		batchEnd.add(endDate);
		
		List<Report> preFinalList = new ArrayList<>();

		
		for (int j = 0; j < batchStart.size(); j++) {

			System.out.println("batch start : "  +  batchStart.get(j) + " batch end : "  +batchEnd.get(j)   + " batch rent pm : " + batchRent.get(j) );
		
			// converting a Batch duration to individual months  
						
			LocalDate batchStartDate = batchStart.get(j);
			LocalDate batchEndDate =  batchEnd.get(j);
			double batchRentToPay= batchRent.get(j);

			LocalDate _current = batchStartDate;
			LocalDate _currentMonthStart;
			LocalDate _currentMonthEnd; 


			Report record;
			List<Report> startAndEndList = new ArrayList<Report>();

			do {

				record = new Report();
				record.setItemName(itemName);

				// currentMonthStart 
				_currentMonthStart = _current.withDayOfMonth(1);

				record.setMonthStartDate(_currentMonthStart.toString());
				// current month end
				_currentMonthEnd = _currentMonthStart.plusMonths(1).minusDays(1);

				record.setMonthEndDate(_currentMonthEnd.toString());

				record.setRentToPay(batchRentToPay);

				startAndEndList.add(record);
				_current = _currentMonthEnd.plusDays(1); // next month
				
			}while(!_current.isAfter(batchEndDate));


			// first record handling
			Report firstRecord  = startAndEndList.get(0);
			firstRecord.setMonthStartDate(batchStartDate.toString());
			if(!batchStartDate.withDayOfMonth(1).equals(batchStartDate))
			{
				firstRecord.setPartial(true);
			}

			startAndEndList.remove(0);
			startAndEndList.add(0, firstRecord);

			// last record handling
			Report lastRecord  = startAndEndList.get(startAndEndList.size()-1);
			lastRecord.setMonthEndDate(batchEndDate.toString());
			if(!YearMonth.from(batchEndDate).atEndOfMonth().equals(batchEndDate))
			{
				lastRecord.setPartial(true);  	
			}
			
			startAndEndList.remove(startAndEndList.size()-1);
			startAndEndList.add(startAndEndList.size(), lastRecord);
			

			for (int i = 0; i <startAndEndList.size() ; i++) {

			
				// handling partial rents
				
				if(startAndEndList.get(i).isPartial()) 
				{
					Report record1   = startAndEndList.get(i);
					record1.setRentToPay(getPartialRent(record1.getMonthStartDate(), record1.getMonthEndDate(), record1.getRentToPay()));
					preFinalList.add(record1);

				}
				else {
					preFinalList.add(startAndEndList.get(i));
				}
				
			}
			
		}
		
		// pre-final list display
		boolean displayPrefinalList =  false;
		
		if(displayPrefinalList) 
		{
			displayList(preFinalList);
		}
		
		
		List<Report> finalList = new ArrayList<Report>();
			
			for (int i = 0; i < preFinalList.size()-1; i++) {
				
				if( (LocalDate.parse(preFinalList.get(i).getMonthStartDate(), formatter2).withDayOfMonth(1)).equals(LocalDate.parse(preFinalList.get(i+1).getMonthStartDate(), formatter2).withDayOfMonth(1)))
				{
					preFinalList.get(i).setRentToPay(preFinalList.get(i).getRentToPay() + preFinalList.get(i+1).getRentToPay());
					preFinalList.get(i).setMonthEndDate((preFinalList.get(i+1).getMonthEndDate()));
					
					finalList.add(preFinalList.get(i));					
					i++;
				}
				else {
					finalList.add(preFinalList.get(i));
				}
			}

			//displayList(finalList);
		
		return finalList;
	}

	
	public static void displayList(List<Report> list){
		
		System.out.println("---------" + list.size());
		
		for (int i = 0; i < list.size(); i++) {
			
			System.out.println(list.get(i));
		}
		
		System.out.println("---------");
	}
}

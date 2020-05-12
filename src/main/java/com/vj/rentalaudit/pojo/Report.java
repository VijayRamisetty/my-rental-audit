package com.vj.rentalaudit.pojo;

public class Report {

	String itemName;
	String monthStartDate;
	String monthEndDate;
	boolean isPartial;
	double rentToPay;
	
	
	
	public Report() {

	}

	public Report(String itemName, String monthStartDate, String monthEndDate, boolean isPartial, double rentToPay) {
		super();
		this.itemName = itemName;
		this.monthStartDate = monthStartDate;
		this.monthEndDate = monthEndDate;
		this.isPartial = isPartial;
		this.rentToPay = rentToPay;
	}
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getMonthStartDate() {
		return monthStartDate;
	}
	public void setMonthStartDate(String monthStartDate) {
		this.monthStartDate = monthStartDate;
	}
	public String getMonthEndDate() {
		return monthEndDate;
	}
	public void setMonthEndDate(String monthEndDate) {
		this.monthEndDate = monthEndDate;
	}
	public boolean isPartial() {
		return isPartial;
	}
	public void setPartial(boolean isPartial) {
		this.isPartial = isPartial;
	}
	public double getRentToPay() {
		return rentToPay;
	}
	public void setRentToPay(double rentToPay) {
		this.rentToPay = rentToPay;
	}

	@Override
	public String toString() {
		return "Report [itemName=" + itemName + ", monthStartDate=" + monthStartDate + ", monthEndDate=" + monthEndDate
				+ ", isPartial=" + isPartial + ", rentToPay=" + rentToPay + "]";
	}
	
	
	
	
	
}

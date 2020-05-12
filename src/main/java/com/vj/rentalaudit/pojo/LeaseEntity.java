package com.vj.rentalaudit.pojo;

public class LeaseEntity {

	String itemName;
	String fromDate;
	String toDate;
	int initialRent;
	int edays;
	int eMonths;
	int eYears;
	double ePercentage;
	int eAmount;
	
	
	public LeaseEntity(String itemName, String from, String toDate, int initialRent, int edays, int eMonths, int eYears,
			double ePercentage, int eAmount) {
		super();
		this.itemName = itemName;
		this.fromDate = from;
		this.toDate = toDate;
		this.initialRent = initialRent;
		this.edays = edays;
		this.eMonths = eMonths;
		this.eYears = eYears;
		this.ePercentage = ePercentage;
		this.eAmount = eAmount;
	}
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public int getInitialRent() {
		return initialRent;
	}
	public void setInitialRent(int initialRent) {
		this.initialRent = initialRent;
	}
	public int getEdays() {
		return edays;
	}
	public void setEdays(int edays) {
		this.edays = edays;
	}
	public int geteMonths() {
		return eMonths;
	}
	public void seteMonths(int eMonths) {
		this.eMonths = eMonths;
	}
	public int geteYears() {
		return eYears;
	}
	public void seteYears(int eYears) {
		this.eYears = eYears;
	}
	public double getePercentage() {
		return ePercentage;
	}
	public void setePercentage(double ePercentage) {
		this.ePercentage = ePercentage;
	}
	public int geteAmount() {
		return eAmount;
	}
	public void seteAmount(int eAmount) {
		this.eAmount = eAmount;
	}
	@Override
	public String toString() {
		return "LeaseEntity [itemName=" + itemName + ", from=" + fromDate + ", toDate=" + toDate + ", initialRent="
				+ initialRent + ", edays=" + edays + ", eMonths=" + eMonths + ", eYears=" + eYears + ", ePercentage="
				+ ePercentage + ", eAmount=" + eAmount + "]";
	}
	
	
	
	
}

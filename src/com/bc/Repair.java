package com.bc;

public class Repair extends Product {

	private Double partsCost;
	private Double hourlyLaborCost;
	private Double hoursWorked;


//	Constructor Method
	public Repair(String productCode, String productType, String productLabel, Double partsCost,
			Double hourlyLaborCost) {
		super(productCode, productType, productLabel);
		this.partsCost = partsCost;
		this.hourlyLaborCost = hourlyLaborCost;
	}
	
	
	public Repair(Repair repairObject, Double hoursWorked) {
		super(repairObject.getProductCode(),repairObject.getProductLabel(),repairObject.getProductType());
		
		this.hoursWorked = hoursWorked;
		this.partsCost = repairObject.getPartsCost();
		this.hourlyLaborCost = repairObject.getHourlyLaborCost();
		
	}

	

/**
	 * @return the hoursWorked
	 */
	public Double getHoursWorked() {
		return hoursWorked;
	}


	/**
	 * @param hoursWorked the hoursWorked to set
	 */
	public void setHoursWorked(Double hoursWorked) {
		this.hoursWorked = hoursWorked;
	}




	//	To String Method
	public String toString() {
		return this.getProductCode() + " " + this.getProductType() + " " + this.getProductLabel() + " "
				+ this.getPartsCost() + " " + this.getHourlyLaborCost() + "\n";

	}

	/**
	 * @return the partsCost
	 */
	public Double getPartsCost() {
		return partsCost;
	}

	/**
	 * @param partsCost the partsCost to set
	 */
	public void setPartsCost(Double partsCost) {
		this.partsCost = partsCost;
	}

	/**
	 * @return the hourlyLaborCost
	 */
	public Double getHourlyLaborCost() {
		return hourlyLaborCost;
	}

	/**
	 * @param hourlyLaborCost the hourlyLaborCost to set
	 */
	public void setHourlyLaborCost(Double hourlyLaborCost) {
		this.hourlyLaborCost = hourlyLaborCost;
	}
	
	public double getHoursCost() {
		
		
		return (this.getHourlyLaborCost() * this.hoursWorked);
		
	}
	public double getRepairCost() {
		
		
		return (this.getHoursCost() + this.getPartsCost());
		
	}

}

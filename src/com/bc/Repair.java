package com.bc;

public class Repair extends Product {

	private Double partsCost;
	private Double hourlyLaborCost;

//	Constructor Method
	public Repair(String productCode, String productType, String productLabel, Double partsCost,
			Double hourlyLaborCost) {
		super(productCode, productType, productLabel);
		this.partsCost = partsCost;
		this.hourlyLaborCost = hourlyLaborCost;
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

}

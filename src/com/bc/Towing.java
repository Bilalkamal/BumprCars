package com.bc;

public class Towing extends Product {

	private Double costPerMile;
	private Double milesTowed;



	// Constructor Method

	public Towing(String productCode, String productType, String productLabel, Double costPerMile) {
		super(productCode, productType, productLabel);
		this.costPerMile = costPerMile;
	}
	public Towing (Towing towingObject, Double milesTowed) {
		super(towingObject.getProductCode(),towingObject.getProductLabel(),towingObject.getProductType());
		this.costPerMile = towingObject.getCostPerMile();
		this.milesTowed = this.milesTowed;
		
		
	}

//	To String Method
	public String toString() {
		return this.getProductCode() + " " + this.getProductType() + " " + this.getProductLabel() + " "
				+ this.getCostPerMile() + "\n";

	}
	/**
	 * @return the milesTowed
	 */
	public Double getMilesTowed() {
		return milesTowed;
	}
	/**
	 * @param milesTowed the milesTowed to set
	 */
	public void setMilesTowed(Double milesTowed) {
		this.milesTowed = milesTowed;
	}

	/**
	 * @return the costPerMile
	 */
	public Double getCostPerMile() {
		return costPerMile;
	}

	/**
	 * @param costPerMile the costPerMile to set
	 */
	public void setCostPerMile(Double costPerMile) {
		this.costPerMile = costPerMile;
	}

	public double getTowingcost() {

		
		return (this.getCostPerMile() * milesTowed);
		
	}
	
}

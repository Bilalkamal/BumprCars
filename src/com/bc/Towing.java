package com.bc;

public class Towing extends Product {

	private Double costPerMile;

// Constructor Method
	public Towing(String productCode, String productType, String productLabel, Double costPerMile) {
		super(productCode, productType, productLabel);
		this.costPerMile = costPerMile;
	}

//	To String Method
	public String toString() {
		return this.getProductCode() + " " + this.getProductType() + " " + this.getProductLabel() + " "
				+ this.getCostPerMile() + "\n";

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

	public double getMilesCost(Double milesTowed) {

		
		return (this.getCostPerMile() * milesTowed);
		
	}
	
}

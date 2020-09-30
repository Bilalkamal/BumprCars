package com.bc;



public class Concession extends Product{
	
	private Double unitCost;
	
	public Concession(String productCode, String productType, String productLabel, Double unitCost) {
		super(productCode, productType, productLabel);
		this.unitCost = unitCost;
	}
	
	public String toString() {
		return this.getProductCode() + " " + this.getProductType() + " " + this.getProductLabel() + " " + 
				this.getUnitCost()+ "\n";
		
		
		
	}
	
	
	/**
	 * @return the unitCost
	 */
	public Double getUnitCost() {
		return unitCost;
	}

	/**
	 * @param unitCost the unitCost to set
	 */
	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}

	
	public double getConcessionsCost(Integer quantity) {
//		Products Code
		
		return (this.getUnitCost() * quantity);
		
	}

	
}

/*
 * Concession class is an object of type product include details of Concession product
 */

package com.bc;

public class Concession extends Product {

	private Double unitCost;

	private Double quantity;
	private String associatedRepair;

	public Concession(String productCode, String productType, String productLabel, Double unitCost) {
		super(productCode, productType, productLabel);
		this.unitCost = unitCost;
	}

	public Concession(Concession concessionObject, Double quantity,String associatedRepair ) {
		super(concessionObject.getProductCode(),concessionObject.getProductType(),concessionObject.getProductLabel());
		this.unitCost = concessionObject.getUnitCost();
		this.quantity = quantity;
		this.associatedRepair = concessionObject.getAssociatedRepair();

	}

	public Concession(Concession concessionObject, Double quantity) {
		super(concessionObject.getProductCode(), concessionObject.getProductType(), concessionObject.getProductLabel());
		this.unitCost = concessionObject.getUnitCost();
		this.quantity = quantity;
		this.associatedRepair = concessionObject.getAssociatedRepair();

	}	public Concession(Concession concessionObject) {
		super(concessionObject.getProductCode(), concessionObject.getProductType(), concessionObject.getProductLabel());
		this.unitCost = concessionObject.getUnitCost();
		this.quantity = concessionObject.getQuantity();
		this.associatedRepair = concessionObject.getAssociatedRepair();

	}


	
	
	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getAssociatedRepair() {
		return associatedRepair;
	}

	public void setAssociatedRepair(String associatedRepair) {
		this.associatedRepair = associatedRepair;
	}

	public String toString() {
		return this.getProductCode() + " " + this.getProductType() + " " + this.getProductLabel() + " "
				+ this.getUnitCost() + "\n";

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

	public double getConcessionCost() {
//Products Code

		return (this.getUnitCost() * quantity);

	}

}
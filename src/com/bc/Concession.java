package com.bc;



public class Concession extends Product{
	
	private Double unitCost;
	private Double cost;
	private int quantity;
	private String associatedRepair;
	
	public Concession(String productCode, String productType, String productLabel, Double unitCost) {
		super(productCode, productType, productLabel);
		this.unitCost = unitCost;
	}
	public Concession(Concession concessionObject, Integer quantity,String associatedRepair ) {
		super(concessionObject.getProductCode(),concessionObject.getProductLabel(),concessionObject.getProductType());
		
		this.unitCost = unitCost;
		this.quantity = concessionObject.getQuantity();
		this.associatedRepair = concessionObject.getAssociatedRepair();
		this.cost = concessionObject.getcost(quantity);//check this
	}
	
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getAssociatedRepair() {
		return associatedRepair;
	}
	public void setAssociatedRepair(String associatedRepair) {
		this.associatedRepair = associatedRepair;
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

	
	public double getcost(Integer quantity) {
//		Products Code
		
		return (this.getUnitCost() * quantity);
		
	}

	
}

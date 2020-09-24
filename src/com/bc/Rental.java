package com.bc;

public class Rental extends Product {

	private Double dailyCost;
	private Double deposit;
	private Double cleaningFee;

//	Constructor Method
	public Rental(String productCode, String productType, String productLabel, Double dailyCost, Double deposit,
			Double cleaningFee) {
		super(productCode, productType, productLabel);

		this.dailyCost = dailyCost;
		this.deposit = deposit;
		this.cleaningFee = cleaningFee;
	}
	
//	To String method

	public String toString() {
		return this.getProductCode() + " " + this.getProductType() + " " + this.getProductLabel() + " "
				+ this.getDailyCost() + " " + this.getDeposit() + " " + this.getCleaningFee() + "\n";

	}

	/**
	 * @return the dailyCost
	 */
	public Double getDailyCost() {
		return dailyCost;
	}

	/**
	 * @param dailyCost the dailyCost to set
	 */
	public void setDailyCost(Double dailyCost) {
		this.dailyCost = dailyCost;
	}

	/**
	 * @return the deposit
	 */
	public Double getDeposit() {
		return deposit;
	}

	/**
	 * @param deposit the deposit to set
	 */
	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	/**
	 * @return the cleaningFee
	 */
	public Double getCleaningFee() {
		return cleaningFee;
	}

	/**
	 * @param cleaningFee the cleaningFee to set
	 */
	public void setCleaningFee(Double cleaningFee) {
		this.cleaningFee = cleaningFee;
	}

}

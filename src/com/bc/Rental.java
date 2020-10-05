package com.bc;

public class Rental extends Product {

	private Double dailyCost;
	private Double deposit;
	private Double cleaningFee;
	private Integer daysRented;
	
	
//	Constructor Method
	public Rental(String productCode, String productType, String productLabel, Double dailyCost, Double deposit,
			Double cleaningFee) {
		super(productCode, productType, productLabel);

		this.dailyCost = dailyCost;
		this.deposit = deposit;
		this.cleaningFee = cleaningFee;
	}
	
	public Rental(Rental rentalObject, Integer daysRented) {
		super(rentalObject.getProductCode(),rentalObject.getProductLabel(),rentalObject.getProductType());
		
		this.daysRented = this.daysRented;
		this.dailyCost = rentalObject.getDailyCost();
		this.cleaningFee = rentalObject.getCleaningFee();
		this.deposit = rentalObject.getDeposit();
		
	}
	

//	To String method

	/**
	 * @return the daysRented
	 */
	public Integer getDaysRented() {
		return daysRented;
	}

	/**
	 * @param daysRented the daysRented to set
	 */
	public void setDaysRented(Integer daysRented) {
		this.daysRented = daysRented;
	}



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

	
	public double getRentCost() {
		
		
		return (this.dailyCost * this.daysRented) - this.deposit + this.cleaningFee;
		
	}
	
	
	
}

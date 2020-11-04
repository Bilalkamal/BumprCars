package com.bc;

import java.util.List;

/*
 * The Invoice class is a class to mimic the Invoice Object. It has an Invoice Code, which is an alphanumeric value, an ownerCode, 
 * which is a person's code responsible for billing. The third element is the Customer Code, 
 * which is connected to the customer account, and the last element is the list of prducts, bought by that customer. 
*/

public class Invoice {

	private String invoiceCode;
	private String ownerCode;
	private String customerCode;
	private List  listOfProducts;

//	Constructor
	public Invoice(String invoiceCode, String ownerCode, String customerCode, List listOfProducts) {
		super();
		this.invoiceCode = invoiceCode;
		this.ownerCode = ownerCode;
		this.customerCode = customerCode;
		this.listOfProducts = listOfProducts;
	}
	

	@Override
	public String toString() {
		return "Invoice [invoiceCode=" + invoiceCode + ", ownerCode=" + ownerCode + ", customerCode=" + customerCode
				+ ", listOfProducts=" + listOfProducts + "]";
	}

	/**
	 * @return the invoiceCode
	 */
	public String getInvoiceCode() {
		return invoiceCode;
	}

	/**
	 * @param invoiceCode the invoiceCode to set
	 */
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	/**
	 * @return the ownerCode
	 */
	public String getOwnerCode() {
		return ownerCode;
	}

	/**
	 * @param ownerCode the ownerCode to set
	 */
	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}

	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return the listOfProducts
	 */
	public List getListOfProducts() {
		return listOfProducts;
	}

	/**
	 * @param listOfProducts the listOfProducts to set
	 */
	public void setListOfProducts(List listOfProducts) {
		this.listOfProducts = listOfProducts;
	}

	public Double getBusinessTax(Double subtotal) {

		return subtotal * 0.0425;
	}

	public Double getPersonalTax(Double subtotal) {

		return subtotal * 0.08;
	}

}

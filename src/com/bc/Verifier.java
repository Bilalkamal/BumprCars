package com.bc;

import java.util.List;



public class Verifier {
	
	
	JDBCReader jr = new JDBCReader();
	
	
	List<Product> listOfProducts = jr.loadAllProducts();
	List<Person> listOfPersons = jr.loadAllPersons();
	List<Customer> listOfCustomers = jr.loadAllCustomer();
	List<Invoice> listOfInvoices = jr.loadAllInvoices();
	
	
	
	/**
	 * This method verify and return object person if the the entered person code 
	 * matches with any personCode in the ListOfPersons
	 * @param personCode
	 * @return Person object correspond to inputed personCode
	 */
	public Person verifyPerson(String personCode) {
		Person verifiedPerson = null;
		for (Person p : listOfPersons) {
			if (p.getPersonCode().equals(personCode)) {
				verifiedPerson = p;
			}
		}
		return verifiedPerson;
	}
	
	
	/**
	 * This method verify and return object Customer if the the entered customer code 
	 * matches with any customerCode in the ListOfCutomers
	 * @param customerCode
	 * @return Customer object correspond to inputed customerCode
	 */
	
	public Customer verifyCustomer(String customerCode) {
		Customer verifiedCustomer = null;
		for (Customer c : listOfCustomers) {
			if (c.getCustomerCode().equals(customerCode)) {
				verifiedCustomer = c;
			}
		}
		return verifiedCustomer;
	}
	
	/**
	 * This method verify and return object Product if the the entered Product code 
	 * matches with any ProductCode in the ListOfProdcts
	 * @param productCode
	 * @return Product object correspond to inputed productCode
	 */
	
	public Product verifyProduct(String productCode) {
		Product verifiedProduct = null;
		for (Product product : listOfProducts) {
			if (product.getProductCode().equals(productCode)) {
				verifiedProduct = product;
			}
		}
		return verifiedProduct;
	}
	
	/**
	 * This method verify and return object Invoice if the the entered Invoice code 
	 * matches with any InvoiceCode in the ListOfInvoices
	 * @param InvoiceCode
	 * @return Invoice object correspond to inputed InvoiceCode
	 */
	
	public Invoice verifyInvoice(String InvoiceCode) {
		Invoice verifiedInvoice = null;
		for (Invoice inv : listOfInvoices) {
			if (inv.getInvoiceCode().equals(InvoiceCode)) {
				verifiedInvoice = inv;
			}
		}
		return verifiedInvoice;
	}
	
	
	



}

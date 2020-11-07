package com.bc;

import java.util.List;

import jdk.nashorn.internal.codegen.CompilerConstants.Call;

public class InvoiceCalculator {

/**
 * This will get an Invoice and send back the subtotal of all products
 */
	Verifier v = new Verifier();
	
	public Double calculateInvoiceSubtotal(Invoice invoice) {
	
		List<Product> productList = invoice.getListOfProducts();
		Double invoiceSubtotal = 0.0;
		
		for (Product p: productList) {
//			Check product Type and then call the right function for that product  
			if (p.getProductType().equals("R")) {
				Rental rental = new Rental((Rental) p);
				invoiceSubtotal += calculateRentalSubtotal(rental);
			}else if (p.getProductType().equals("T")) {
				Towing towing = new Towing((Towing) p);
				invoiceSubtotal += calculateTowingSubtotal(towing);
			}if (p.getProductType().equals("F")) {
				Repair repair = new Repair((Repair) p);
				invoiceSubtotal += calculateRepairSubtotal(repair);
			}if (p.getProductType().equals("C")) {
				Concession concession = new Concession((Concession) p);
				invoiceSubtotal += calculateConcessionSubtotal(concession);
			}
			
		}
		return invoiceSubtotal;
	}
	
/**
 * This function will get a rental object and return the subtotal for it 
 */

	public Double calculateRentalSubtotal(Rental rental) {
	
		return rental.getRentCost();

	}
	public Double calculateRepairSubtotal(Repair repair) {
		
		return repair.getRepairCost();

	}
	public Double calculateConcessionSubtotal(Concession concession) {
		
		return concession.getConcessionCost();

	}
	public Double calculateTowingSubtotal(Towing towing) {
		
		return towing.getTowingcost();

	}
	
	/*
	 * This Function will calculate the total towing and Concessions discounts on an invoice
	 * The input is the invoice 
	 * The output is the total discounts 
	 */
	
	
	public Double calculateLoLoyaltyDiscount(Invoice invoice) {
		List<Product> products= v.listOfProducts;
		List<Customer> customers= v.listOfCustomers;
		Customer c = v.verifyCustomer(invoice.getCustomerCode());
		Person person = v.verifyPerson(invoice.getOwnerCode());
		if (c.getCustomerType().equals("P")) {
			 if (person.getEmailAddress().getLength() >1) {
				//calaculate the discount & return it 
				 
				 
			}
				
			}
			return 0.0;
		}
		
		
		
	
	
}

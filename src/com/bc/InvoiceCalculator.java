package com.bc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InvoiceCalculator {

/**
 * This will get an Invoice and send back the subtotal of all products
 */
	Verifier v = new Verifier();
	public Double calculateInvoiceSubtotal(Invoice invoice) {

		List<Product> productList = invoice.getListOfProducts();
		Double invoiceSubtotal = 0.0;

		for (Product p : productList) {
//			Check product Type and then call the right function for that product  
			if (p.getProductType().equals("R")) {
				Rental rental = new Rental((Rental) p);
				invoiceSubtotal += calculateRentalSubtotal(rental);
			} else if (p.getProductType().equals("T")) {
				Towing towing = new Towing((Towing) p);
				invoiceSubtotal += calculateTowingSubtotal(towing);
			}
			if (p.getProductType().equals("F")) {
				Repair repair = new Repair((Repair) p);
				invoiceSubtotal += calculateRepairSubtotal(repair);
			}
			if (p.getProductType().equals("C")) {
				Concession concession = new Concession((Concession) p);
				invoiceSubtotal += calculateConcessionSubtotal(concession);
			}

		}
		return invoiceSubtotal;
	}

	/**
	 * Input: Product (Towing, Rental, Repair, Concession These function will get a
	 * product and return the Subtotal for it Output: Item Subtotal
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

	/**
	 * This Function will calculate the total towing and Concessions discounts on an
	 * invoice The input is the invoice The output is the total discounts
	 * 
	 * @param invoice
	 * @return Total Individual Discount for an invoice
	 */
	public Double calculateIndividualDiscounts(Invoice invoice) {
		Double totalDiscountDouble = 0.0;

		List<Product> invoiceListOfProducts = invoice.getListOfProducts();

		for (Product p : invoiceListOfProducts) {
			if (p.getProductType().equals("C")) {
				Double concessionDiscount = getConcessionDiscount((Concession) p);
				totalDiscountDouble += concessionDiscount;

			} else if (p.getProductType().equals("T")) {
				Double towingDiscount = getTowingDiscount((Towing) p, invoice);
				totalDiscountDouble += towingDiscount;
			}
		}

		return totalDiscountDouble;

	}

	/**
	 * Checks if the concession has an associated repair, if so calculates a 10%
	 * discount for that concession object
	 * 
	 * @param Concession
	 * @return Discount value for a concession item
	 */
	private Double getConcessionDiscount(Concession c) {

		Double concessionDiscountDouble = 0.0;

		if (c.getAssociatedRepair() != null) {
			concessionDiscountDouble = -0.1 * (calculateConcessionSubtotal(c));
		}

		return concessionDiscountDouble;

	}

	/**
	 * Checks if the invoice has a repair and rental products, if so it calculates
	 * the discount, which is the total value of the discount
	 * 
	 * @param Towing  Product
	 * @param Invoice
	 * @return
	 */
	private Double getTowingDiscount(Towing t, Invoice invoice) {

		List<Product> productsList = invoice.getListOfProducts();
		Map<String, Integer> towingMap = new HashMap<String, Integer>();
		towingMap.put("R", 0);
		towingMap.put("F", 0);

		Double towingDiscount = 0.0;

		for (Product p : productsList) {
			if (p.getProductType().equals("R")) {

			} else if (p.getProductType().equals("f")) {
				towingMap.put("F", towingMap.get("F") + 1);
			}
		}
		if ((towingMap.get("R") > 0) && (towingMap.get("F") > 0)) {
			towingDiscount -= calculateTowingSubtotal(t);
		}

		return towingDiscount;
	}

	/**
	 * This function takes in an invoice and calculates the total taxes for the
	 * Invoice, based on the customer type.
	 * 
	 * @param Invoice
	 * @return Total Taxes for an invoice
	 */
	private Double calculateTotalTaxes(Invoice invoice) {
		Double totalTaxesForInvoice = 0.0;
		List<Product> productsList = invoice.getListOfProducts();
		Boolean isBusiness = null;
		Double itemTaxDouble = 0.0;
//		TODO: Verify customer Type 
		if (isBusiness) {
			for (Product p : productsList) {
				itemTaxDouble = calculateProductBusinessTax(p, invoice);
				totalTaxesForInvoice += itemTaxDouble;
			}
		} else {
			for (Product p : productsList) {
				itemTaxDouble = calculateProductPersonTax(p,invoice);
				totalTaxesForInvoice += itemTaxDouble;
			}
		}

		return totalTaxesForInvoice;
	}

	
	
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
		
		
		
	
	
	/**
	 * This will calculate the business tax, which is 4.25%, for a product.
	 * It takes the invoice, because the towing needs the invoice to check for dicount.
	 * @param product
	 * @param invoice
	 * @return
	 */
	private Double calculateProductBusinessTax(Product product, Invoice invoice) {

		Double itemBusinessTaxDouble = 0.0;

		if (product.getProductType().equals("R")) {
			itemBusinessTaxDouble = calculateRentalSubtotal((Rental) product);
		} else if (product.getProductType().equals("F")) {
			itemBusinessTaxDouble = calculateRepairSubtotal((Repair) product);
		} else if (product.getProductType().equals("C")) {
			itemBusinessTaxDouble = (calculateConcessionSubtotal((Concession) product)
					- getConcessionDiscount((Concession) product));
		} else if (product.getProductType().equals("T")) {
			itemBusinessTaxDouble = calculateTowingSubtotal((Towing) product)
					- getTowingDiscount((Towing) product, invoice);
		}

		return 0.0425 * itemBusinessTaxDouble;
	}
	/**
	 * This will calculate the business tax, which is 4.25%, for a product.
	 * It takes the invoice, because the towing needs the invoice to check for dicount.
	 * @param product
	 * @param invoice
	 * @return
	 */
	private Double calculateProductPersonTax(Product product, Invoice invoice) {

		Double itemBusinessTaxDouble = 0.0;

		if (product.getProductType().equals("R")) {
			itemBusinessTaxDouble = calculateRentalSubtotal((Rental) product);
		} else if (product.getProductType().equals("F")) {
			itemBusinessTaxDouble = calculateRepairSubtotal((Repair) product);
		} else if (product.getProductType().equals("C")) {
			itemBusinessTaxDouble = (calculateConcessionSubtotal((Concession) product)
					- getConcessionDiscount((Concession) product));
		} else if (product.getProductType().equals("T")) {
			itemBusinessTaxDouble = calculateTowingSubtotal((Towing) product)
					- getTowingDiscount((Towing) product, invoice);
		}

		return 0.08 * itemBusinessTaxDouble;
	}

}

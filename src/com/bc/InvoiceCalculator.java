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
	public Double getConcessionDiscount(Concession c) {

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
	public Double getTowingDiscount(Towing t, Invoice invoice) {

		List<Product> productsList = invoice.getListOfProducts();
		Map<String, Integer> towingMap = new HashMap<String, Integer>();
		towingMap.put("R", 0);
		towingMap.put("F", 0);

		Double towingDiscount = 0.0;

		for (Product p : productsList) {
			if (p.getProductType().equals("R")) {
				towingMap.put("R", towingMap.get("R") + 1);
			} else if (p.getProductType().equals("F")) {
				towingMap.put("F", towingMap.get("F") + 1);
			}
		}
		if ((towingMap.get("R") > 0) && (towingMap.get("F") > 0)) {
			towingDiscount -= calculateTowingSubtotal(t);
		}

		return towingDiscount;
	}

	/**
	 * This function calculates the total discounts on a certain invoice.
	 * 
	 * @param invoice
	 * @return Double
	 */
	public Double calculateTotalDiscounts(Invoice invoice) {

		Double invoiceTotalDiscountsDouble = 0.0;
		List<Product> productsList = invoice.getListOfProducts();

		for (Product product : productsList) {
			if (product.getProductType().equals("T")) {

				invoiceTotalDiscountsDouble += getTowingDiscount((Towing) product, invoice);
			} else if (product.getProductType().equals("C")) {
				invoiceTotalDiscountsDouble += getConcessionDiscount((Concession) product);
			}
		}
		return invoiceTotalDiscountsDouble;

	}

	/**
	 * This function takes in an invoice and calculates the total taxes for the
	 * Invoice, based on the customer type.
	 * 
	 * @param Invoice
	 * @return Total Taxes for an invoice
	 */
	public Double calculateTotalTaxes(Invoice invoice) {
		Double totalTaxesForInvoice = 0.0;
		List<Product> productsList = invoice.getListOfProducts();
		Boolean isBusiness = null;
		Double itemTaxDouble = 0.0;
//		TODO: Verify customer Type 
		Customer customer = v.verifyCustomer(invoice.getCustomerCode());

		if (customer.getCustomerType().equals("B")) {
			isBusiness = true;
		} else {
			isBusiness = false;
		}

		if (isBusiness) {
			for (Product p : productsList) {
				itemTaxDouble = calculateProductBusinessTax(p, invoice);
				totalTaxesForInvoice += itemTaxDouble;
			}
		} else {
			for (Product p : productsList) {
				itemTaxDouble = calculateProductPersonTax(p, invoice);
				totalTaxesForInvoice += itemTaxDouble;
			}
		}

		return totalTaxesForInvoice;
	}

	/**
	 * This will calculate the business tax, which is 4.25%, for a product. It takes
	 * the invoice, because the towing needs the invoice to check for dicount.
	 * 
	 * @param product
	 * @param invoice
	 * @return
	 */
	public Double calculateProductBusinessTax(Product product, Invoice invoice) {

		Double itemBusinessTaxDouble = 0.0;

		if (product.getProductType().equals("R")) {
			itemBusinessTaxDouble = calculateRentalSubtotal((Rental) product);
		} else if (product.getProductType().equals("F")) {
			itemBusinessTaxDouble = calculateRepairSubtotal((Repair) product);
		} else if (product.getProductType().equals("C")) {
			itemBusinessTaxDouble = (calculateConcessionSubtotal((Concession) product)
					+ getConcessionDiscount((Concession) product));
		} else if (product.getProductType().equals("T")) {
			itemBusinessTaxDouble = calculateTowingSubtotal((Towing) product)
					+ getTowingDiscount((Towing) product, invoice);
		}

		return 0.0425 * itemBusinessTaxDouble;
	}

	/**
	 * This will calculate the business tax, which is 4.25%, for a product. It takes
	 * the invoice, because the towing needs the invoice to check for dicount.
	 * 
	 * @param product
	 * @param invoice
	 * @return
	 */
	public Double calculateProductPersonTax(Product product, Invoice invoice) {

		Double itemBusinessTaxDouble = 0.0;

		if (product.getProductType().equals("R")) {
			itemBusinessTaxDouble = calculateRentalSubtotal((Rental) product);
		} else if (product.getProductType().equals("F")) {
			itemBusinessTaxDouble = calculateRepairSubtotal((Repair) product);
		} else if (product.getProductType().equals("C")) {
			itemBusinessTaxDouble = (calculateConcessionSubtotal((Concession) product)
					+ getConcessionDiscount((Concession) product));
		} else if (product.getProductType().equals("T")) {
			itemBusinessTaxDouble = calculateTowingSubtotal((Towing) product)
					+ getTowingDiscount((Towing) product, invoice);
		}

		return 0.08 * itemBusinessTaxDouble;
	}

	/**
	 * This function takes a product and an invoice and calculate the product's
	 * final price.
	 * 
	 * @param invoice
	 * @param p
	 * @return Double
	 */

	public Double calculateItemTotal(Invoice invoice, Product p) {
		Double itemTotal = 0.0;
		Double itemSubtotal = 0.0;
		Double itemDiscount = 0.0;
		Double itemTax = 0.0;
		Customer customer = v.verifyCustomer(invoice.getCustomerCode());

		if (p.getProductType().equals("R")) {
			Rental rental = new Rental((Rental) p);
			itemSubtotal = calculateRentalSubtotal(rental);
		} else if (p.getProductType().equals("T")) {
			Towing towing = new Towing((Towing) p);
			itemSubtotal = calculateTowingSubtotal(towing);
			itemDiscount = getTowingDiscount(towing, invoice);
		}
		if (p.getProductType().equals("F")) {
			Repair repair = new Repair((Repair) p);
			itemSubtotal = calculateRepairSubtotal(repair);
		}
		if (p.getProductType().equals("C")) {
			Concession concession = new Concession((Concession) p);
			itemSubtotal = calculateConcessionSubtotal(concession);
			itemDiscount = getConcessionDiscount(concession);
		}

		if (customer.getCustomerType().equals("B")) {
			itemTax = calculateProductBusinessTax(p, invoice);
		} else {
			itemTax = calculateProductPersonTax(p, invoice);
		}

		itemTotal = itemSubtotal + itemDiscount + itemTax;
		return itemTotal;
	}

	/**
	 * This function calculates the invoice item totals.
	 * 
	 * @param invoice
	 * @return Double
	 */

	public Double calculateInvoiceItemTotals(Invoice invoice) {

		Double invoiceItemTotalsDouble = 0.0;
		List<Product> productList = invoice.getListOfProducts();

		for (Product p : productList) {
			invoiceItemTotalsDouble += calculateItemTotal(invoice, p);
		}

		return invoiceItemTotalsDouble;
	}

	/**
	 * Checks if a business account fee is applicable to this invoice.
	 * 
	 * @param invoice
	 * @return Boolean
	 */
	public Boolean checkBusinessAccount(Invoice invoice) {

		Customer customer = v.verifyCustomer(invoice.getCustomerCode());
		if (customer.getCustomerType().equals("B")) {
			return true;
		}
		return false;

	}

	/**
	 * Checks if a loyalty discount is applicable to this invoice.
	 * 
	 * @param invoice
	 * @return Boolean
	 */
	public Boolean checkLoyaltyDiscount(Invoice invoice) {

		Person person = v.verifyPerson(invoice.getOwnerCode());
		if (person.getEmailAddress().getLength() > 1) {
			return true;
		}
		return false;

	}

	/**
	 * This function calculates the loyalty discount for an invoice.
	 * 
	 * @param invoice
	 * @return
	 */
	public Double calculateLoyaltyDiscount(Invoice invoice) {

		Double loyaltyDiscount = 0.0;
		if (checkLoyaltyDiscount(invoice)) {
			loyaltyDiscount = -0.05 * calculateInvoiceItemTotals(invoice);
		}
		return loyaltyDiscount;
	}

	/**
	 * This function calculates the grand total of an invoice after checking the
	 * business fee and loyalty discount.
	 * 
	 * @param invoice
	 * @return Double
	 */

	public Double calculateGrandTotal(Invoice invoice) {

		Double grandTotal = calculateInvoiceItemTotals(invoice);

		if (checkBusinessAccount(invoice)) {
			grandTotal += 75.0;
		} else if (checkLoyaltyDiscount(invoice)) {
			grandTotal += calculateLoyaltyDiscount(invoice);
		}
		return grandTotal;

	}

	/**
	 * This functions calculates all subtotals on a list of invoices.
	 * @param listOfInvoices
	 * @return Double
	 */
	
	public Double calculateAllInvoicesSubtotals(List<Invoice> listOfInvoices) {

		Double allInvoicesSubtotals = 0.0;
		for (Invoice invoice : listOfInvoices) {
			allInvoicesSubtotals += calculateInvoiceSubtotal(invoice);
		}
		return allInvoicesSubtotals;
	}

	/**
	 * This functions calculates all discounts on a list of invoices.
	 * @param listOfInvoices
	 * @return Double
	 */
	public Double calculateAllInvoicesDiscounts(List<Invoice> listOfInvoices) {

		Double allInvoicesDiscounts = 0.0;

		for (Invoice invoice : listOfInvoices) {
			allInvoicesDiscounts += calculateTotalDiscounts(invoice);
		}
		return allInvoicesDiscounts;
	}

	/**
	 * This functions calculates all fees on a list of invoices.
	 * @param listOfInvoices
	 * @return
	 */
	
	public Double calculateAllInvoicesFees(List<Invoice> listOfInvoices) {
		Double allInvoicesFees = 0.0;

		for (Invoice invoice : listOfInvoices) {
			if (checkBusinessAccount(invoice)) {
				allInvoicesFees += 75.0;
			}

		}
		return allInvoicesFees;

	}

	/**
	 * This functions calculates all taxes on a list of invoices.
	 * @param listOfInvoices
	 * @return Double
	 */
	public Double calculateAllInvoicesTaxes(List<Invoice> listOfInvoices) {
		Double allInvoicesTaxes = 0.0;

		for (Invoice invoice : listOfInvoices) {
			allInvoicesTaxes += calculateTotalTaxes(invoice);
		}
		return allInvoicesTaxes;
	}

	/**
	 * This functions calculates all grand totals on a list of invoices.
	 * @param listOfInvoices
	 * @return Double
	 */
	public Double calculateAllInvoicesGrandTotals(List<Invoice> listOfInvoices) {
		Double allInvoicesGrandTotals = 0.0;

		for (Invoice invoice : listOfInvoices) {
			allInvoicesGrandTotals += calculateGrandTotal(invoice);
		}
		return allInvoicesGrandTotals;
		
	}

}

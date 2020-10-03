package com.bc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DisplayFunctions {

	static WriterFunction writer = new WriterFunction();
	
//Summary Report

	public static void summaryReport(List<Invoice> lInv, List<Customer> lc, ArrayList<Person> lpers,
			List<Product> lprod) throws IOException {
		
		
		
		writer.write("Executive Summary Report: \n");
		writer.write(
				"Code			Owner				Customer Account 		Subtotal		Discount		Fees			Taxes			Total \n");

		writer.write(
				"------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- \n ");

		Double allSubtotals = 0.0;
		Double allDiscounts = 0.0;
		Double allFees = 0.0;
		Double allTaxes = 0.0;
		Double allTotals = 0.0;

		for (Invoice s : lInv) {

//			TO-DO: Convert these into functions and just call them here.

//			Check Ownercode

			String ownerCode = s.getOwnerCode();

			String ownerName = null;

			List<Double> subtotalResult = calculateSubtotal(lprod, s.getListOfProducts());
			Double itemSubtotal = Math.round(subtotalResult.get(0) * 100.0) / 100.0;
			Double itemDiscount = Math.round(subtotalResult.get(1) * 100.0) / 100.0;

			Double taxes = null;

			Double total = null;

			for (Person p : lpers) {
				if (p.getPersonCode().equals(ownerCode)) {
					ownerName =  p.getLastName()+ "," + p.getFirstName();
				}

			}

//			Check Customer Account 
			String customerCode = s.getCustomerCode();

			String customerName = null;
			Double businessFee = null;
			for (Customer c : lc) {
				if (c.getCustomerCode().equals(customerCode)) {
					customerName = c.getName();
					if (c.getCustomerType().equals("B")) {
						businessFee = 75.50;

						taxes = (itemSubtotal - itemDiscount) * 0.0425;
						taxes = Math.round(taxes * 100.0) / 100.0;

					} else if (c.getCustomerType().equals("P")) {
						businessFee = 0.0;

						taxes = (itemSubtotal - itemDiscount) * 0.08;
						taxes = Math.round(taxes * 100.0) / 100.0;
					}
				}

			}

			total = itemSubtotal + itemDiscount + businessFee + taxes;
			total = Math.round(total * 100.0) / 100.0;

			allSubtotals += itemSubtotal;
			allDiscounts += itemDiscount;
			allFees += businessFee;
			allTaxes += taxes;
			allTotals += total;

			writer.write(String.format("%-22s %-32s %-31s %-23s %-22s %-23s  %-23s %-22s \n", s.getInvoiceCode(), ownerName,
					customerName, "$  " + itemSubtotal, "$  " + itemDiscount, "$  " + businessFee, "$  " + taxes,
					"$  " + total));

		}
		writer.write(
				"\n=================================================================================================================================================================================================================== \n");

		allSubtotals = Math.round(allSubtotals * 100.0) / 100.0;
		allDiscounts = Math.round(allDiscounts * 100.0) / 100.0;
		allFees = Math.round(allFees * 100.0) / 100.0;
		allTaxes = Math.round(allTaxes * 100.0) / 100.0;
		allTotals = Math.round(allTotals * 100.0) / 100.0;

		writer.write(String.format("%-87s %-23s %-21s  %-24s %-23s %-22s \n \n \n ", "TOTALS", "$  " + allSubtotals,
				"$  " + allDiscounts, "$  " + allFees, "$  " + allTaxes, "$  " + allTotals));

	}

	
//	Invoice report function
	public static void invoiceReport(List<Invoice> lInv, List<Customer> lc, ArrayList<Person> lpers,
			List<Product> lprod) {
		

		writer.write("Invoice Details: \n =+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+" );
		
		
	}
	
//	Subtotal and Discounts function
	
	public static List<Double> calculateSubtotal(List<Product> productList, List<String> boughtList) {

//		0th element is the subtotal
		Double subTotal = 0.0;
		Double itemCost = 0.0;
//		1st element is the concession discount 
		Double concessionTotalDiscount = 0.0;
		Double concessionDiscount = 0.0;

//		3rd element is the taxes 

		List<Repair> lrep = new ArrayList<Repair>();

		for (String item : boughtList) {

			String itemTokens[] = item.split(":");

			if (itemTokens.length == 2) {

				for (Product p : productList) {

					if (p.getProductCode().equals(itemTokens[0])) {

						if (p.getProductType().equals("R")) {

							Rental r = new Rental((Rental) p, Integer.parseInt(itemTokens[1]));
							r.setDaysRented(Integer.parseInt(itemTokens[1]));
							itemCost = r.getRentCost();

							subTotal += itemCost;

						} else if (p.getProductType().equals("T")) {
							Towing t = new Towing((Towing) p, Double.parseDouble(itemTokens[1]));
							t.setMilesTowed(Double.parseDouble(itemTokens[1]));
							itemCost = t.getTowingcost();
							subTotal += itemCost;

						} else if (p.getProductType().equals("F")) {
							Repair f = new Repair((Repair) p, Double.parseDouble(itemTokens[1]));
							f.setHoursWorked(Double.parseDouble(itemTokens[1]));
							itemCost = f.getRepairCost();
							subTotal += itemCost;

							lrep.add(f);

						} else if (p.getProductType().equals("C")) {
							Concession c = new Concession((Concession) p, Integer.parseInt(itemTokens[1]));
							c.setQuantity(Integer.parseInt(itemTokens[1]));
							itemCost = c.getConcessionCost();
							subTotal += itemCost;
						}

					}
				}

			} else {

				for (Product p : productList) {
					if (p.getProductCode().equals(itemTokens[0])) {

						Concession c = new Concession((Concession) p, Integer.parseInt(itemTokens[1]), itemTokens[2]);
						c.setQuantity(Integer.parseInt(itemTokens[1]));
						itemCost = c.getConcessionCost();
						subTotal += itemCost;

						concessionDiscount = itemCost * -0.1;
						concessionTotalDiscount += concessionDiscount;

					}

				}

			}

		}

		List<Double> finalResult = new ArrayList<Double>();
		finalResult.add(subTotal);
		finalResult.add(concessionTotalDiscount);

		return finalResult;

	}

}

package com.bc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayFunctions {

//Summary Report

	public static void summaryReport(List<Invoice> lInv, List<Customer> lc, ArrayList<Person> lpers,
			List<Product> lprod) {
		
		
		
		System.out.println("Executive Summary Report: \n");
		System.out.println(
				"Code			Owner				Customer Account 		Subtotal		Discount		Fees			Taxes			Total");

		System.out.println(
				"-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

		Double allSubtotals = 0.0;
		Double allDiscounts = 0.0;
		Double allFees = 0.0;
		Double allTaxes = 0.0;
		Double allTotals = 0.0;

		for (Invoice s : lInv) {

//			TODO: Convert these into functions and just call them here.

//			Check Ownercode

			String ownerCode = s.getOwnerCode();

			String ownerName = null;

			List<Double> subtotalResult = calculateInvoiceSubtotal(lprod, s.getListOfProducts());
			Double itemSubtotal = Math.round(subtotalResult.get(0) * 100.0) / 100.0;
			Double itemDiscount = Math.round(subtotalResult.get(1) * 100.0) / 100.0;

			Double taxes = null;

			Double total = null;

			for (Person p : lpers) {
				if (p.getPersonCode().equals(ownerCode)) {
					ownerName = p.getFirstName() + ", " + p.getLastName();
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

			System.out.printf("%-22s %-32s %-31s %-23s %-22s %-23s  %-23s %-22s \n", s.getInvoiceCode(), ownerName,
					customerName, "$  " + itemSubtotal, "$  " + itemDiscount, "$  " + businessFee, "$  " + taxes,
					"$  " + total);

		}
		System.out.println(
				"===================================================================================================================================================================================================================");

		allSubtotals = Math.round(allSubtotals * 100.0) / 100.0;
		allDiscounts = Math.round(allDiscounts * 100.0) / 100.0;
		allFees = Math.round(allFees * 100.0) / 100.0;
		allTaxes = Math.round(allTaxes * 100.0) / 100.0;
		allTotals = Math.round(allTotals * 100.0) / 100.0;

		System.out.printf("%-87s %-23s %-21s  %-24s %-23s %-22s \n \n \n ", "TOTALS", "$  " + allSubtotals,
				"$  " + allDiscounts, "$  " + allFees, "$  " + allTaxes, "$  " + allTotals);

	}

//	Invoice detailed report function
	public static void invoiceReport(List<Invoice> lInv, List<Customer> lc, ArrayList<Person> lpers,
			List<Product> lprod) {

		System.out.println(
				"Invoice Details: \n =+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+");

		for (Invoice inv : lInv) {

			System.out.println("Invoice " + inv.getInvoiceCode() + "\n--------------------------------------");
			System.out.println("Owner: \n");

			String ownerCode = inv.getOwnerCode();
			String ownerName = null;
			EmailAddress emails = null;
			Address address = null;

			for (Person p : lpers) {
				if (p.getPersonCode().equals(ownerCode)) {
					ownerName = p.getLastName() + ", " + p.getFirstName();
					if (p.getEmailAddress() != null) {
						emails = p.getEmailAddress();
					} else {

						List emptyList = new ArrayList<String>();
						emptyList.add("");
						emails = new EmailAddress(emptyList);
					}

					address = p.getAddress();

				}

			}

//			TODO: Fix spacing Issues 

			System.out.printf("%20s %20s ", ownerName + "\n",
					(emails.getEmailAddress().isEmpty() ? "[]" : emails.getEmailAddress()) + "\n");

//			,   (emails.getEmailAddress().isEmpty() ? "[]" : emails.getEmailAddress())

			System.out.println(address.getStreet() + "\n" + address.getCity() + "," + address.getState()
					+ address.getZip() + " " + address.getCountry() + " \n");

//			Customer data
			System.out.println("Customer:");
			String customerCode = inv.getCustomerCode();

			String customerName = null;

			for (Customer c : lc) {
				if (c.getCustomerCode().equals(customerCode)) {

					if (c.getCustomerType() == "B") {
						customerName = c.getName();
					} else {
						customerName = c.getName();
					}

					address = c.getAddress();

				}

			}

			System.out.printf("%3s %s ", customerName, "\n");
			System.out.println(address.getStreet() + "\n" + address.getCity() + "," + address.getState()
					+ address.getZip() + " " + address.getCountry() + " \n");

//			Products Part
			System.out.println("Products: \n");

			System.out.println(
					"Code			Dicription				                 		Subtotal		Discount		Fees			Taxes			Total");

			System.out.println(
					"-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

			String productCode = null;

//			TODO: Fix Spacing for Thanks
			System.out.printf("%87s %33s", " \n \n THANK YOU FOR DOING BUSINESS WITH US!  \n \n \n \n", "\n");
			System.out.println(
					"+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=");

		}

	}

//	Sub-total and Discounts function
	public static List<Double> calculateInvoiceSubtotal(List<Product> productList, List<String> boughtList) {

//		0th element is the sub-total
		Double subTotal = 0.0;
		Double itemCost = 0.0;
//		1st element is the concession discount 
		Double concessionTotalDiscount = 0.0;
		Double concessionDiscount = 0.0;
		Double totalDiscount = 0.0;
		Double towingDiscount = 0.0;

		Map<String, Integer> itemsInInvoice = new HashMap<String, Integer>();

		itemsInInvoice.put("Towing", 0);
		itemsInInvoice.put("Repair", 0);
		itemsInInvoice.put("Rental", 0);
//		Although we're not using concession for a discount, 
//		we might needed it any time else so it would be better to have it here.
		itemsInInvoice.put("Concession", 0);

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
							itemsInInvoice.put("Rental", itemsInInvoice.get("Rental") + 1);

						} else if (p.getProductType().equals("T")) {
							Towing t = new Towing((Towing) p, Double.parseDouble(itemTokens[1]));
							t.setMilesTowed(Double.parseDouble(itemTokens[1]));
							itemCost = t.getTowingcost();
							subTotal += itemCost;

							itemsInInvoice.put("Towing", itemsInInvoice.get("Towing") + 1);

							if ((itemsInInvoice.get("Towing") > 0) && (itemsInInvoice.get("Rental") > 0)
									&& (itemsInInvoice.get("Repair") > 0)) {

//								TODO: This should be a discount not directly subtracted 
								towingDiscount += itemCost;

							}

						} else if (p.getProductType().equals("F")) {
							Repair f = new Repair((Repair) p, Double.parseDouble(itemTokens[1]));
							f.setHoursWorked(Double.parseDouble(itemTokens[1]));
							itemCost = f.getRepairCost();
							subTotal += itemCost;

							lrep.add(f);
							itemsInInvoice.put("Repair", itemsInInvoice.get("Repair") + 1);

						} else if (p.getProductType().equals("C")) {
							Concession c = new Concession((Concession) p, Integer.parseInt(itemTokens[1]));
							c.setQuantity(Integer.parseInt(itemTokens[1]));
							itemCost = c.getConcessionCost();
							subTotal += itemCost;
							itemsInInvoice.put("Concession", itemsInInvoice.get("Concession") + 1);
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
						itemsInInvoice.put("Concession", itemsInInvoice.get("Concession") + 1);

					}

				}

			}

		}

		totalDiscount = concessionTotalDiscount + (towingDiscount * -1);
		List<Double> finalResult = new ArrayList<Double>();
		finalResult.add(subTotal);
		finalResult.add(totalDiscount);

		return finalResult;

	}

}

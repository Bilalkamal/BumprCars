package com.bc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DisplayFunctions {

//	Create Writer Object

	static WriterFunction writer = new WriterFunction();

	static String report = "";

//Summary Report

	public static void summaryReport(List<Invoice> lInv, List<Customer> lc, ArrayList<Person> lpers,

			List<Product> lprod) throws IOException {

		Double loyaltyDiscount = 0.0;

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
					ownerName = p.getLastName() + "," + p.getFirstName();
				}

			}

//			Check Customer Account 
			String customerCode = s.getCustomerCode();

			EmailAddress emails = null;
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

			for (Customer c : lc) {
				if (c.getCustomerType().equals("P")) {

					if (c.getCustomerContactCode().getEmailAddress() != null) {
						emails = c.getCustomerContactCode().getEmailAddress();
					} else {

						List emptyList = new ArrayList<String>();
						emptyList.add("");
						emails = new EmailAddress(emptyList);
					}

					if (emails.getEmailAddress().size() >= 2) {

						loyaltyDiscount = Math.round((itemSubtotal * -0.05) * 100.0) / 100.0;
						itemDiscount += itemSubtotal * -0.05;

						total = itemSubtotal + itemDiscount + businessFee + taxes;
						loyaltyDiscount = total * -0.05;

						itemDiscount += loyaltyDiscount;
						itemDiscount = Math.round(itemDiscount * 100.0) / 100.0;
						total += loyaltyDiscount;
						total = Math.round(total * 100.0) / 100.0;

					} else {
						total = itemSubtotal + itemDiscount + businessFee + taxes;
						total = Math.round(total * 100.0) / 100.0;
					}

				} else {
					total = itemSubtotal + itemDiscount + businessFee + taxes;
					total = Math.round(total * 100.0) / 100.0;
				}
			}

			allSubtotals += itemSubtotal;
			allDiscounts += itemDiscount;
			allFees += businessFee;
			allTaxes += taxes;
			allTotals += total;

			writer.write(String.format("%-22s %-32s %-31s %-23s %-22s %-23s  %-23s %-22s \n", s.getInvoiceCode(),
					ownerName, customerName, "$  " + itemSubtotal, "$  " + itemDiscount, "$  " + businessFee,
					"$  " + taxes, "$  " + total));

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

//	Detailed Invoice

	public static void calculateDetailedInvoice(List<Invoice> lInv, List<Customer> lc, ArrayList<Person> lpers,
			List<Product> lprod) {

		writer.write(
				"Invoice Details: \n =+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+");

		for (Invoice inv : lInv) {

			writer.write("Invoice " + inv.getInvoiceCode() + "\n--------------------------------------");
			writer.write("Owner: \n");

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

			writer.write(String.format("%20s %20s ", ownerName + "\n",
					(emails.getEmailAddress().isEmpty() ? "[]" : emails.getEmailAddress()) + "\n"));

//			,   (emails.getEmailAddress().isEmpty() ? "[]" : emails.getEmailAddress())

			writer.write(address.getStreet() + "\n" + address.getCity() + "," + address.getState() + address.getZip()
					+ " " + address.getCountry() + " \n");

//			Customer data
			writer.write("Customer:");
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

			writer.write(String.format("%3s %s ", customerName, "\n"));
			writer.write(address.getStreet() + "\n" + address.getCity() + "," + address.getState() + address.getZip()
					+ " " + address.getCountry() + " \n");

//			Products Part
			writer.write("Products: \n");

			writer.write(
					"Code			Dicription				                 		Subtotal		Discount		Fees			Taxes			Total");

			writer.write(
					"-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

			List<String> boughtList = inv.getListOfProducts();

			System.out.println(boughtList);

			String productCode = null;
			String productDescription = null;
			String productInfo = null;
			Double productQuantity = 0.0;
			Double productRate = 0.0;

			for (String item : boughtList) {

				String itemTokens[] = item.split(":");

				for (Product p : lprod) {
					if (p.getProductCode().equals(itemTokens[0])) {
						productCode = p.getProductCode();
						productDescription = p.getProductLabel();

						if (p.getProductType() == "F") {
							productInfo = "hours of labor";
							Repair f = new Repair((Repair) p, Double.parseDouble(itemTokens[1]));
							f.setHoursWorked(Double.parseDouble(itemTokens[1]));
							productRate = f.getHourlyLaborCost();
							
						} else if (p.getProductType() == "R") {
							productInfo = "days";
							Rental r = new Rental((Rental) p, Integer.parseInt(itemTokens[1]));
							r.setDaysRented(Integer.parseInt(itemTokens[1]));
							productRate = r.getDailyCost();
						} else if (p.getProductType() == "T") {
							productInfo = "miles";
							Towing t = new Towing((Towing) p, Double.parseDouble(itemTokens[1]));
							t.setMilesTowed(Double.parseDouble(itemTokens[1]));
							productRate = t.getCostPerMile();
						} else if (p.getProductType() == "C") {
							productInfo = "units";
							Concession c = new Concession((Concession) p, Integer.parseInt(itemTokens[1]));
							c.setQuantity(Integer.parseInt(itemTokens[1]));
							productRate = c.getUnitCost();
							
						}

					}
					productQuantity = Double.parseDouble(itemTokens[1]);

				}

			}

//			TODO: Fix Spacing for Thanks
			writer.write(String.format("%87s %33s", " \n \n THANK YOU FOR DOING BUSINESS WITH US!  \n \n \n \n", "\n"));
			writer.write(
					"+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=");

		}

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

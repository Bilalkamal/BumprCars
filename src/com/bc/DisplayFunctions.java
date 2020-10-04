package com.bc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class DisplayFunctions {

//	Create Writer Object

	static WriterFunction writer = new WriterFunction();

//Summary Report

	public static void summaryReport(List<Invoice> lInv, List<Customer> lc, ArrayList<Person> lpers,

			List<Product> lprod) throws IOException {

		Double loyaltyDiscount = 0.0;

		writer.write("Executive Summary Report: \n");
		writer.write(
				"Code      Owner                  Customer Account      Subtotal      Discount        Fees        Taxes           Total \n");

		writer.write(
				"-----------------------------------------------------------------------------------------------------------------------------------\n");

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

//			writer.write(String.format("%-22s %-32s %-31s %-23s %-22s %-23s  %-23s %-22s \n", s.getInvoiceCode(),
//					ownerName, customerName, "$  " + itemSubtotal, "$  " + itemDiscount, "$  " + businessFee,
//					"$  " + taxes, "$  " + total));

			writer.write(String.format("%-9s %-22s %-21s %-13s %-15s %-10s  %-13s %-22s \n", s.getInvoiceCode(),
					ownerName, customerName, "$  " + itemSubtotal, "$  " + itemDiscount, "$  " + businessFee,
					"$  " + taxes, "$  " + total));

		}
		writer.write(
				"\n===================================================================================================================================\n");

		allSubtotals = Math.round(allSubtotals * 100.0) / 100.0;
		allDiscounts = Math.round(allDiscounts * 100.0) / 100.0;
		allFees = Math.round(allFees * 100.0) / 100.0;
		allTaxes = Math.round(allTaxes * 100.0) / 100.0;
		allTotals = Math.round(allTotals * 100.0) / 100.0;

		writer.write(String.format("%-54s %-13s %-14s  %-11s %-13s %-5s \n \n \n ", "TOTALS", "$  " + allSubtotals,
				"$  " + allDiscounts, "$  " + allFees, "$  " + allTaxes, "$  " + allTotals));

	}

//	Detailed Invoice

	public static void calculateDetailedInvoice(List<Invoice> lInv, List<Customer> lc, ArrayList<Person> lpers,
			List<Product> lprod) {

		Double allSubtotals =0.0;
		
		Double allDiscounts =0.0;
		Double allTaxes =0.0;
		Double allTotals =0.0;
		
		HashMap<String, Integer> towingDiscountMap = new HashMap<String, Integer>();
		towingDiscountMap.put("Towing", 0);
		towingDiscountMap.put("Rental", 0);
		towingDiscountMap.put("Repair", 0);
		writer.write(
				"Invoice Details: \n+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=\n");
		Boolean businessAccountCheck = null;
		Double businessTaxRate = 0.0425;
		Double personalTaxRate = 0.08;
		for (Invoice inv : lInv) {
			Double businessAccountFee = 75.5;

			writer.write("Invoice " + inv.getInvoiceCode() + "\n--------------------------------------\n");
			writer.write("Owner: \n");

			String ownerCode = inv.getOwnerCode();
			String ownerName = null;
			EmailAddress emails = null;
			Address address = null;

			Boolean loyaltyDiscountCheck;

			for (Person p : lpers) {
				if (p.getPersonCode().equals(ownerCode)) {
					ownerName = p.getLastName() + ", " + p.getFirstName();
					if (p.getEmailAddress() != null) {
						emails = p.getEmailAddress();
						if (emails.getEmailAddress().size() > 1) {
							loyaltyDiscountCheck = true;

						} else {
							loyaltyDiscountCheck = false;
						}
					} else {

						List emptyList = new ArrayList<String>();
						emptyList.add("");
						emails = new EmailAddress(emptyList);
					}

					address = p.getAddress();

				}

			}

//			TODO: Fix spacing Issues 

			writer.write(String.format("%22s %20s ", ownerName + "\n",
					(emails.getEmailAddress().isEmpty() ? "[]" : "      " + emails.getEmailAddress()) + "\n"));

//			,   (emails.getEmailAddress().isEmpty() ? "[]" : emails.getEmailAddress())

			writer.write("      " + address.getStreet() + "\n" + "      " + address.getCity() + "," + address.getState()
					+ address.getZip() + " " + address.getCountry() + " \n");

//			Customer data
			writer.write("Customer:\n");
			String customerCode = inv.getCustomerCode();

			String customerName = null;

			for (Customer c : lc) {
				if (c.getCustomerCode().equals(customerCode)) {

					if (c.getCustomerType().equals("B") ) {
						customerName = c.getName();
						businessAccountCheck = true;
					} else {
						customerName = c.getName();
						businessAccountCheck = false;
					}

					address = c.getAddress();

				}

			}

			writer.write(String.format("%25s %20s ", customerName, "\n"));
			writer.write("       " + address.getStreet() + "\n        " + address.getCity() + "  " + address.getState()
					+ "  " + address.getZip() + " " + address.getCountry() + " \n");

//			Products Part
			writer.write("Products: \n");

			writer.write(
					"Code			       Dicription				                                           		Subtotal		Discount			Taxes			Total\n");

			writer.write(
					"-----------------------------------------------------------------------------------------------------------------------------------------");

			List<String> boughtList = inv.getListOfProducts();

			String productCode = null;
			String productDescription = null;
			String productInfo = null;
			Double productQuantity = 0.0;
			Double productRate = 0.0;
			Double productSubtotal = 0.0;
			Double itemDiscount = 0.0;
			Double towingDiscount = 0.0;
			Double concessionDiscount = 0.0;
			Double loyaltyDiscount = 0.0;

			Double milesTowed = 0.0;
			Double cleaningFee = 0.0;
			Double deposit = 0.0;
			Double mileRate = 0.0;
			Double subtotalAfterDiscount = 0.0;

			Double hourlyLaborCost = 0.0;
			Double partsCost = 0.0;

			Integer concessionUnits = 0;

			Double itemTax = 0.0;
			
			Double itemTotal = 0.0;

			for (String item : boughtList) {

				String itemTokens[] = item.split(":");
				productQuantity = Double.parseDouble(itemTokens[1]);

				for (Product p : lprod) {
					if (p.getProductCode().equals(itemTokens[0])) {
						productCode = p.getProductCode();
						productDescription = p.getProductLabel();

//						Repairs
						if (p.getProductType().equals("F")) {
							productInfo = "hours of labor";
							Repair f = new Repair((Repair) p, Double.parseDouble(itemTokens[1]));
							f.setHoursWorked(Double.parseDouble(itemTokens[1]));

							productRate = f.getHourlyLaborCost();
							productSubtotal = f.getRepairCost();
							towingDiscountMap.put("Repair", towingDiscountMap.get("Repair") + 1);

							partsCost = f.getPartsCost();

							subtotalAfterDiscount = productSubtotal;

							if (businessAccountCheck == true) {
								itemTax = subtotalAfterDiscount * businessTaxRate;
							} else if (businessAccountCheck == false) {
								itemTax = subtotalAfterDiscount * personalTaxRate;
							}

						}
//						Rent 
						else if (p.getProductType().equals("R")) {
							productInfo = "days";
							Rental r = new Rental((Rental) p, Integer.parseInt(itemTokens[1]));
							r.setDaysRented(Integer.parseInt(itemTokens[1]));
							productRate = r.getDailyCost();
							productSubtotal = r.getRentCost();
							towingDiscountMap.put("Rental", towingDiscountMap.get("Rental") + 1);

							cleaningFee = r.getCleaningFee();
							deposit = r.getDeposit();

							subtotalAfterDiscount = productSubtotal;
							if (businessAccountCheck == true) {
								itemTax = subtotalAfterDiscount * businessTaxRate;
							} else {
								itemTax = subtotalAfterDiscount * personalTaxRate;
							}

						}

//						Towing
						else if (p.getProductType().equals("T")) {
							productInfo = "miles";
							Towing t = new Towing((Towing) p, Double.parseDouble(itemTokens[1]));
							t.setMilesTowed(Double.parseDouble(itemTokens[1]));
							milesTowed = t.getMilesTowed();
							towingDiscountMap.put("Towing", towingDiscountMap.get("Towing") + 1);

//							TODO: Remove this - Repeated with mileRate
							productRate = t.getCostPerMile();
							productSubtotal = t.getTowingcost();
							subtotalAfterDiscount = productSubtotal;
							if ((towingDiscountMap.get("Rental") > 0) && ((towingDiscountMap.get("Repair") > 0))
									&& (towingDiscountMap.get("Towing") > 0)) {
								towingDiscount -= productSubtotal;
								itemTax = 0.0;
								itemDiscount = towingDiscount;
								subtotalAfterDiscount = 0.0;

							} else {
								if (businessAccountCheck == true) {
									itemTax = subtotalAfterDiscount * businessTaxRate;
								} else {
									itemTax = subtotalAfterDiscount * personalTaxRate;
								}
							}
							mileRate = t.getCostPerMile();

						}
//						Concessions
						else if (p.getProductType().equals("C")) {
							productInfo = "units";
							Concession c = new Concession((Concession) p, Integer.parseInt(itemTokens[1]));
							c.setQuantity(Integer.parseInt(itemTokens[1]));
							productRate = c.getUnitCost();
							productSubtotal = c.getConcessionCost();

							if (itemTokens.length == 3) {
								concessionDiscount += productSubtotal * -0.1;
								itemDiscount = concessionDiscount;

							}
							
							
							subtotalAfterDiscount = productSubtotal + itemDiscount;
							if (businessAccountCheck == true) {
								itemTax = subtotalAfterDiscount * businessTaxRate;
							} else {
								itemTax = subtotalAfterDiscount * personalTaxRate;
							}
							concessionUnits = c.getQuantity();
							
							
							
						}

					}
					
				}
				
				
				itemTax = Math.round(itemTax * 100.0) / 100.0;
				itemTotal = (productSubtotal + itemDiscount) + itemTax;
				writer.write(String.format("%-1s %-15s %-25s %-1s %-1s %-1s  %-1s %s  %15s %5s %5s %-5s  %-5s %-5s %1s", "\n",
						productCode, productDescription, "(" + productQuantity, productInfo, "@", productRate + ")",
						" ", "$", productSubtotal, "$", itemDiscount, itemTax, itemTotal, "\n"));
				
				
			}
			writer.write("======================================================================================================================================== \n");
			writer.write("ITEM TOTALS: ");

//			TODO: Calculate Loyalty discount on the whole receipt after taxes  

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

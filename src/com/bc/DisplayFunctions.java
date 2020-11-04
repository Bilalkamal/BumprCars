/*
 * DisplayFunctions class is the class used to Write  the summary report 
 * and detailed report into output.txt file 
 */

package com.bc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DisplayFunctions {

//	Writer object that writes to the output file. 

	static WriterFunction writer = new WriterFunction();

//Summary Report - This function views the summary report for all invoices
//	It Includes: 1. The Invoice Code 2. The Owner 3. The Customer Account associated with the Invoice 4. The Subtotal for the whole invoice 5. The Discounts 
//	6. The Fees 7. The Taxes 8. The Total for the Invoice 9. The totals of all theses as a final line
	public static void summaryReport(List<Invoice> lInv, List<Customer> lc, ArrayList<Person> lpers,

			
			
			
			List<Product> lprod) throws IOException {
		Double loyaltyDiscount = 0.0;

		WriterFunction.write("Executive Summary Report: \n");
		WriterFunction.write(
				"Code      Owner                  Customer Account      Subtotal      Discount        Fees        Taxes           Total \n");

		WriterFunction.write(
				"-----------------------------------------------------------------------------------------------------------------------------------\n");

		Double allSubtotals = 0.0;
		Double allDiscounts = 0.0;
		Double allFees = 0.0;
		Double allTaxes = 0.0;
		Double allTotals = 0.0;

		for (Invoice s : lInv) {


			//Check Owner code

			String ownerCode = s.getOwnerCode();

			String ownerName = null;

			List<Double> subtotalResult = calculateSubtotal(lprod, s.getListOfProducts());
			Double itemSubtotal = Math.round(subtotalResult.get(0) * 100.0) / 100.0;
			Double itemDiscount = Math.round(subtotalResult.get(1) * 100.0) / 100.0;
			Double subtotalAfterDiscount = Math.round(subtotalResult.get(2) * 100.0) / 100.0;

			Double taxes = null;

			Double invoiceTotal = 0.0;

			for (Person p : lpers) {
				if (p.getPersonCode().equals(ownerCode)) {
					ownerName = p.getLastName() + "," + p.getFirstName();
				}

			}

//Check Customer Account 
			String customerCode = s.getCustomerCode();

			EmailAddress emails = null;
			String customerName = null;
			Double businessFee = null;
			for (Customer c : lc) {
				if (c.getCustomerCode().equals(customerCode)) {
					customerName = c.getName();
					if (c.getCustomerType().equals("B")) {
						businessFee = 75.50;

						taxes = (subtotalAfterDiscount) * 0.0425;
						taxes = Math.round(taxes * 100.0) / 100.0;

						invoiceTotal = subtotalAfterDiscount + businessFee + taxes;
						invoiceTotal = Math.round(invoiceTotal * 100.0) / 100.0;

					} else if (c.getCustomerType().equals("P")) {

						businessFee = 0.0;

						taxes = (subtotalAfterDiscount + itemDiscount) * 0.08;
						taxes = Math.round(taxes * 100.0) / 100.0;

						if (c.getCustomerContactCode().getEmailAddress() != null) {
							emails = c.getCustomerContactCode().getEmailAddress();
						} else {

							List<String> emptyList = new ArrayList<String>();
							emptyList.add("");
							emails = new EmailAddress(emptyList);
						}
						if (emails.getEmailAddress().size() >= 2) {
							loyaltyDiscount = (taxes + subtotalAfterDiscount) * -0.05;
							itemDiscount += loyaltyDiscount;

							invoiceTotal = subtotalAfterDiscount + itemDiscount + businessFee + taxes;
							invoiceTotal = Math.round(invoiceTotal * 100.0) / 100.0;

						} else {
							invoiceTotal = itemSubtotal + itemDiscount + businessFee + taxes;
							invoiceTotal = Math.round(invoiceTotal * 100.0) / 100.0;

						}

					}
				}

			}
//Calculate the total values for all of them 
			allSubtotals += itemSubtotal;
			allDiscounts += itemDiscount;
			allFees += businessFee;
			allTaxes += taxes;
			allTotals += invoiceTotal;

			WriterFunction.write(String.format("%-9s %-22s %-21s %-13s %-15s %-10s  %-13s %-22s \n", s.getInvoiceCode(),
					ownerName, customerName, "$  " + itemSubtotal, "$  " + itemDiscount, "$  " + businessFee,
					"$  " + taxes, "$  " + invoiceTotal));

		}
		WriterFunction.write(
				"\n===================================================================================================================================\n");

		allSubtotals = Math.round(allSubtotals * 100.0) / 100.0;
		allDiscounts = Math.round(allDiscounts * 100.0) / 100.0;
		allFees = Math.round(allFees * 100.0) / 100.0;
		allTaxes = Math.round(allTaxes * 100.0) / 100.0;
		allTotals = Math.round(allTotals * 100.0) / 100.0;

		WriterFunction.write(String.format("%-54s %-13s %-14s  %-11s %-13s %-5s \n \n \n ", "TOTALS",
				"$  " + allSubtotals, "$  " + allDiscounts, "$  " + allFees, "$  " + allTaxes, "$  " + allTotals));
	}

//	Detailed Invoice - This function calculates the details for each invoice.
//	It calculates each item, including product codes, product description, subtotal, discount, taxes, total, any other business fees or loyalty discounts.

	@SuppressWarnings("unchecked")
	public static void calculateDetailedInvoice(List<Invoice> lInv, List<Customer> lc, ArrayList<Person> lpers,
			List<Product> lprod) {

		Double grandTotal = 0.0;
		Double allSubtotals = 0.0;
		Double allDiscounts = 0.0;
		Double allTaxes = 0.0;
		Double allTotals = 0.0;

		WriterFunction.write(
				"Invoice Details: \n+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=\n");
		Boolean businessAccountCheck = null;
		Double businessTaxRate = 0.0425;
		Double personalTaxRate = 0.08;
		for (Invoice inv : lInv) {
			Double businessAccountFee = 75.5;

			WriterFunction.write("\nInvoice " + inv.getInvoiceCode() + "\n--------------------------------------\n");
			WriterFunction.write("Owner: \n");

			String ownerCode = inv.getOwnerCode();
			String ownerName = null;
			EmailAddress emails = null;
			Address address = null;

			Boolean loyaltyDiscountCheck = false;

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

						List<String> emptyList = new ArrayList<String>();
						emptyList.add("");
						emails = new EmailAddress(emptyList);
					}

					address = p.getAddress();

				}

			}

			WriterFunction.write(String.format("%22s %20s ", ownerName + "\n",
					(emails.getEmailAddress().isEmpty() ? "[]" : "      " + emails.getEmailAddress()) + "\n"));

//			,   (emails.getEmailAddress().isEmpty() ? "[]" : emails.getEmailAddress())

			WriterFunction.write("      " + address.getStreet() + "\n" + "      " + address.getCity() + ","
					+ address.getState() + address.getZip() + " " + address.getCountry() + " \n");

//			Customer data
			WriterFunction.write("Customer:\n");
			String customerCode = inv.getCustomerCode();

			String customerName = null;

			for (Customer c : lc) {
				if (c.getCustomerCode().equals(customerCode)) {

					if (c.getCustomerType().equals("B")) {
						customerName = c.getName();
						businessAccountCheck = true;
					} else {
						customerName = c.getName();
						businessAccountCheck = false;
					}

					address = c.getAddress();

				}

			}

			WriterFunction.write(String.format("%25s %20s ", customerName, "\n"));
			WriterFunction.write("       " + address.getStreet() + "\n        " + address.getCity() + "  "
					+ address.getState() + "  " + address.getZip() + " " + address.getCountry() + " \n");

//			Products Part
			WriterFunction.write("Products: \n");

			WriterFunction.write(
					"Code			       Dicription			                	                                       		Details \n");

			WriterFunction.write(
					"----------------------------------------------------------------------------------------------------------------------------------------- \n");

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

			Double partsCost = 0.0;

			Double concessionUnits = 0.0;

			Double itemTax = 0.0;

			Double itemTotal = 0.0;

			Boolean rentCheck = false;

			allSubtotals = 0.0;

			allDiscounts = 0.0;
			allTaxes = 0.0;
			allTotals = 0.0;

			HashMap<String, Integer> towingDiscountMap = new HashMap<String, Integer>();
			towingDiscountMap.put("Towing", 0);
			towingDiscountMap.put("Rental", 0);
			towingDiscountMap.put("Repair", 0);

			for (String item : boughtList) {
				String extraInfo = "";

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
							extraInfo = "\n                 (+ " + partsCost + " for parts)";
							subtotalAfterDiscount = productSubtotal;

							if (businessAccountCheck == true) {
								itemTax = subtotalAfterDiscount * businessTaxRate;
							} else if (businessAccountCheck == false) {
								itemTax = subtotalAfterDiscount * personalTaxRate;
							}

						}
//						Rent 
						else if (p.getProductType().equals("R")) {
							rentCheck = true;
							productInfo = "days";
							Rental r = new Rental((Rental) p, Double.parseDouble(itemTokens[1]));
							r.setDaysRented(Double.parseDouble(itemTokens[1]));
							productRate = r.getDailyCost();
							productSubtotal = r.getRentCost();
							towingDiscountMap.put("Rental", towingDiscountMap.get("Rental") + 1);

							cleaningFee = r.getCleaningFee();
							deposit = r.getDeposit();

							extraInfo = "\n                 (+ " + cleaningFee + " cleaning fee, -" + deposit
									+ " deposit refund)";
							subtotalAfterDiscount = productSubtotal;
							subtotalAfterDiscount = Math.round(subtotalAfterDiscount * 100.0) / 100.0;
							
							if (businessAccountCheck == true) {
								itemTax = subtotalAfterDiscount * businessTaxRate;
								itemTax = Math.round(itemTax * 100.0) / 100.0;
							} else {
								itemTax = subtotalAfterDiscount * personalTaxRate;
								itemTax = Math.round(itemTax * 100.0) / 100.0;
							}

						}
						

//						Towing
						else if (p.getProductType().equals("T")) {
							productInfo = "miles";
							Towing t = new Towing((Towing) p, Double.parseDouble(itemTokens[1]));
							t.setMilesTowed(Double.parseDouble(itemTokens[1]));
							milesTowed = t.getMilesTowed();
							towingDiscountMap.put("Towing", towingDiscountMap.get("Towing") + 1);
							extraInfo = "";
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
							Concession c = new Concession((Concession) p, Double.parseDouble(itemTokens[1]));
							c.setQuantity(Double.parseDouble(itemTokens[1]));
							productRate = c.getUnitCost();
							productSubtotal = c.getConcessionCost();
							extraInfo = "";
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
				productSubtotal = Math.round(productSubtotal * 100.0) / 100.0;
				itemTotal = Math.round(itemTotal * 100.0) / 100.0;

				allSubtotals += productSubtotal;
				allDiscounts += itemDiscount;
				allTaxes += itemTax;
				allTotals += itemTotal;

				WriterFunction.write(String.format("%-10s  %-10s %-5s %-5s %-15s %-2s %-9s %-9s %-9s  \n", productCode,
						productDescription, "(" + productQuantity, productInfo + "@", productRate + ") ",
						"[ (Subtotal=$" + productSubtotal + ")", "(Discount=$" + itemDiscount + ")",
						"(Taxes=$" + itemTax + ")", "(Total=$" + itemTotal + ")" + "]" + "\n"));

			}

			WriterFunction.write(
					"======================================================================================================================================== \n");

			allSubtotals = Math.round(allSubtotals * 100.0) / 100.0;
			allDiscounts = Math.round(allDiscounts * 100.0) / 100.0;
			allTaxes = Math.round(allTaxes * 100.0) / 100.0;
			allTotals = Math.round(allTotals * 100.0) / 100.0;

			WriterFunction.write(
					String.format("%-50s %-22s %-22s %-22s %-22s ", "ITEM TOTALS: ", "Subtotals: $" + allSubtotals,
							"Discounts: $" + allDiscounts, "Taxes: $" + allTaxes, "Total: $" + allTotals));

			Double fees = 0.0;
			if (businessAccountCheck == true) {
				WriterFunction.write(
						String.format("%-120s %-1s", "\nBUSINESS ACCOUNT FEE: ", "$ " + businessAccountFee + "\n"));
				grandTotal = allTotals + businessAccountFee;
				fees += businessAccountFee;
				grandTotal = Math.round(grandTotal * 100.0) / 100.0;
			} else if ((loyaltyDiscountCheck == true) && (businessAccountCheck != true)) {

				loyaltyDiscount = allTotals * -0.05;
				allDiscounts += loyaltyDiscount;
				grandTotal = allTotals + loyaltyDiscount;
				grandTotal = Math.round(grandTotal * 100.0) / 100.0;
				WriterFunction.write(String.format("%-120s %-1s", "\nLOYAL CUSTOMER DISCOUNT (5% OFF):  ",
						"$" + loyaltyDiscount + "\n"));

			} else {
				grandTotal = allTotals;
			}

			WriterFunction.write(String.format("%-100s %-1s", "GRAND TOTAL:  ", "                   $ " + grandTotal));

			WriterFunction.write(String.format("%87s %33s",
					" \n \n \n \n THANK YOU FOR DOING BUSINESS WITH US!  \n \n \n \n", "\n"));
			WriterFunction.write(
					"+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=");

		}

	}

// This function is used by the summary report to calculate the numbers needed for each invoice. 

	public static List<Double> calculateSubtotal(List<Product> productList, List<String> boughtList) {// 0th element is
																										// the subtotal
		Double subTotal = 0.0;
		Double itemCost = 0.0;
		// 1st element is the concession discount
		Double concessionTotalDiscount = 0.0;
		Double concessionDiscount = 0.0;

		// 3rd element is the taxes

		HashMap<String, Integer> towingDiscountMap = new HashMap<String, Integer>();
		towingDiscountMap.put("Rental", 0);
		towingDiscountMap.put("Repair", 0);
		towingDiscountMap.put("Towing", 0);
		Double towingDiscount = 0.0;

		List<Repair> lrep = new ArrayList<Repair>();

		for (String item : boughtList) {

			String itemTokens[] = item.split(":");

			if (itemTokens.length == 2) {

				for (Product p : productList) {

					if (p.getProductCode().equals(itemTokens[0])) {

						if (p.getProductType().equals("R")) {
							towingDiscountMap.put("Rental", towingDiscountMap.get("Rental") + 1);
							Rental r = new Rental((Rental) p, Double.parseDouble(itemTokens[1]));
							r.setDaysRented(Double.parseDouble(itemTokens[1]));
							itemCost = r.getRentCost();

							subTotal += itemCost;

						} else if (p.getProductType().equals("T")) {
							towingDiscountMap.put("Towing", towingDiscountMap.get("Towing") + 1);

							Towing t = new Towing((Towing) p, Double.parseDouble(itemTokens[1]));
							t.setMilesTowed(Double.parseDouble(itemTokens[1]));
							itemCost = t.getTowingcost();
							subTotal += itemCost;

							if ((towingDiscountMap.get("Rental") > 0) && ((towingDiscountMap.get("Repair") > 0))
									&& (towingDiscountMap.get("Towing") > 0)) {
								towingDiscount -= itemCost;

							}

						} else if (p.getProductType().equals("F")) {
							towingDiscountMap.put("Repair", towingDiscountMap.get("Repair") + 1);

							Repair f = new Repair((Repair) p, Double.parseDouble(itemTokens[1]));
							f.setHoursWorked(Double.parseDouble(itemTokens[1]));
							itemCost = f.getRepairCost();
							subTotal += itemCost;

							lrep.add(f);

						} else if (p.getProductType().equals("C")) {
							Concession c = new Concession((Concession) p, Double.parseDouble(itemTokens[1]));
							c.setQuantity(Double.parseDouble(itemTokens[1]));
							itemCost = c.getConcessionCost();
							subTotal += itemCost;
						}

					}
				}

			} else {

				for (Product p : productList) {
					if (p.getProductCode().equals(itemTokens[0])) {

						Concession c = new Concession((Concession) p, Double.parseDouble(itemTokens[1]), itemTokens[2]);
						c.setQuantity(Double.parseDouble(itemTokens[1]));

						itemCost = c.getConcessionCost();
						subTotal += itemCost;

						concessionDiscount = itemCost * -0.1;
						concessionTotalDiscount += concessionDiscount;

					}

				}

			}

		}

		Double towingAndConcessionDiscount = concessionTotalDiscount + towingDiscount;
		Double subtotalAfterDiscount = subTotal + towingAndConcessionDiscount;
		List<Double> finalResult = new ArrayList<Double>();
		finalResult.add(subTotal);

		finalResult.add(towingAndConcessionDiscount);
		finalResult.add(subtotalAfterDiscount);
		return finalResult;
	}
//
}

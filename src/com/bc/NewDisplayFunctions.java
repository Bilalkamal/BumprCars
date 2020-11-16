package com.bc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * This class has many methods to display Summary and detailed reports in good
 * format
 */

public class NewDisplayFunctions {
	// create objects of InvoiceCalculator and JDBCReader to use their methods in
	// this class
	InvoiceCalculator Calculator = new InvoiceCalculator();
	JDBCReader Jr = new JDBCReader();
	
	// this is the head method in this class which calls the sub-main methods for
	// displaying the reports
	public void displayReports(MyLinkedList<Invoice> lInv, List<Customer> lc, List<Person> lpers, List<Product> lprod,
			List<Invoice> listOfInvoicesunsorted) {
		printSummaryReport(lInv, listOfInvoicesunsorted);
		displayDetailedInvoice(lInv, lc, lpers, lprod);

	}
	/**
	 * This method takes date in form of lists to display them in detailed report
	 * 
	 * @param lInv  list of linked list of invoices sorted from highest to lowest
	 *              total
	 * @param lc    list of customers
	 * @param lpers list of persons (owners)
	 * @param lprod list of products
	 */
	public void displayDetailedInvoice(MyLinkedList<Invoice> lInv, List<Customer> lc, List<Person> lpers,
			List<Product> lprod) {

		System.out.println("Invoice Details:");

		for (int num = 0; num < lInv.getsize(); num++) {
			Invoice inv = lInv.retrieveItemAtIndex(num);
			System.out.println("+=".repeat(57));
//			Print Invoice Number 

			System.out.print("Invoice " + inv.getInvoiceCode() + "\n");
			System.out.println("--".repeat(20));

//			Print Owner & customer Details
			String ownerCode = inv.getOwnerCode();
			String customerCode = inv.getCustomerCode();
//			Verifying the availability of the Person and Customer 
			Person verifiedPerson = verifyPerson(ownerCode, lpers);
			Customer verifiedCustomer = verifyCustomer(customerCode, lc);

			displayDetailedInvoiceHeader(verifiedCustomer, verifiedPerson);
//			Print the Products 
			double totalSubtotals = Calculator.calculateInvoiceSubtotal(inv);
			double totalDiscounts = Calculator.calculateIndividualDiscounts(inv);
			double totalTaxes = Calculator.calculateTotalTaxes(inv);
			double totalTotal = Calculator.calculateInvoiceItemTotals(inv);
			printInvoiceInfo(inv);
			System.out.println("===".repeat(39));

			System.out
					.println("ITEM TOTALS:" + " ".repeat(46) + String.format("$ %-12.2f $ %-12.2f $ %-12.2f $ %-10.2f",
							totalSubtotals, totalDiscounts, totalTaxes, totalTotal));
			if (Calculator.checkBusinessAccount(inv)) {
				System.out.println("BUSINESS ACCOUNT FEE:" + " ".repeat(82) + "$ 75.50");

			} else if (Calculator.checkLoyaltyDiscount(inv)) {
				System.out.println("LOYAL CUSTOMER DISCOUNT (5% OFF)" + Calculator.calculateLoyaltyDiscount(inv));

			}

			System.out.println("GRAND TOTAL: " + " ".repeat(90) + "$ " + String.format("%.2f", Calculator.calculateGrandTotal(inv)));
			System.out.println("\n\n\n" + " ".repeat(30) + "THANK YOU FOR DOING BUSINESS WITH US!" + "\n\n\n");

		}

	}

//	A function to verify a person is in the list persons and return it if its verified
	private Person verifyPerson(String personCode, List<Person> lpers) {
		Person verifiedPerson = null;
		for (Person p : lpers) {
			if (p.getPersonCode().equals(personCode)) {
				verifiedPerson = p;
			}
		}
		return verifiedPerson;
	}

//	A function to verify a Customer is in the list Customers 
	private Customer verifyCustomer(String customerCode, List<Customer> lc) {
		Customer verifiedCustomer = null;
		for (Customer c : lc) {
			if (c.getCustomerCode().equals(customerCode)) {
				verifiedCustomer = c;
			}
		}
		return verifiedCustomer;
	}

//	Display Header Function
//	This function should receive the a person and a customer and print their details.
	private void displayDetailedInvoiceHeader(Customer c, Person p) {

//		TODO: Fix formatting Issue

//		Print Person
		printPersonInfo(p);
//		print Customer
		printCustomerInfo(c);

	}

//	A function that is given a person and prints his/her info (Name, EmailAddress, and Address)
	private void printPersonInfo(Person p) {
		System.out.print("Owner: \n");
		System.out.printf(" ".repeat(10) + "%s, %s \n", p.getLastName(), p.getFirstName());
		if (p.getEmailAddress().getEmailAddress().get(0) != null) {
			System.out.printf(" ".repeat(10) + "%s \n", p.getEmailAddress().getEmailAddress());
		} else {

			System.out.printf(" ".repeat(10) + "%s \n", "[NO E-MAILS]");
		}
		Address personAddress = p.getAddress();

		System.out.printf(" ".repeat(10) + "%s \n", personAddress.getStreet());
		System.out.printf(" ".repeat(10) + "%s, %s %s %s \n", personAddress.getCity(), personAddress.getState(),
				personAddress.getCountry(), personAddress.getZip());

	}

//	A function that is given a customer and prints his/her info (Name,Type, and Address)
	private void printCustomerInfo(Customer c) {

		System.out.print("Customer: \n");

		Boolean businessAccount = c.getCustomerType().equals("B");
		if (businessAccount) {
			System.out.printf(" ".repeat(10) + "%s %s \n", c.getName(), "(Business Account)");
		} else {
			System.out.printf(" ".repeat(10) + "%s %s \n", c.getName(), "(Personal Account)");
		}

		Address customerAddress = c.getAddress();

		System.out.printf(" ".repeat(10) + "%s \n", customerAddress.getStreet());
		System.out.printf(" ".repeat(10) + "%s, %s %s %s \n", customerAddress.getCity(), customerAddress.getState(),
				customerAddress.getCountry(), customerAddress.getZip());

	}

	private void printInvoiceInfo(Invoice I) {
		List<Product> productList = I.getListOfProducts();

		System.out.print("Products: \n");
		System.out.println("code" + " ".repeat(15) + "Description" + " ".repeat(27) + "Subtotal" + " ".repeat(7)
				+ "Discount" + " ".repeat(8) + "Taxes" + " ".repeat(12) + "Total");
		System.out.println("---".repeat(38));
		for (Product p : productList) {
			StringBuilder line = new StringBuilder();
			String productType = null;

			String Productcode = p.getProductCode();
			String Productlabel = p.getProductLabel();
			String ProductType = p.getProductType();
			double subtotal = 0.0;
			double discount = 0.0;
			double taxes = 0.0;
			double total = 0.0;
			// calculate product tax based on customer type
			if (Calculator.checkBusinessAccount(I)) {
				taxes = Calculator.calculateProductBusinessTax(p, I);
			} else {
				taxes = Calculator.calculateProductPersonTax(p, I);
			}

			total = Calculator.calculateItemTotal(I, p);

			// Calculations of subtotal,discount, and total for each product
			if (ProductType.equals("R")) {
				subtotal = Calculator.calculateRentalSubtotal((Rental) p);
				discount = 0.0;
			} else if (ProductType.equals("F")) {
				subtotal = Calculator.calculateRepairSubtotal((Repair) p);
				;
				discount = 0.0;
			} else if (ProductType.equals("T")) {
				subtotal = Calculator.calculateTowingSubtotal((Towing) p);
				discount = Calculator.getTowingDiscount((Towing) p, I);
			} else if (ProductType.equals("C")) {
				subtotal = Calculator.calculateConcessionSubtotal((Concession) p);
				discount = Calculator.getConcessionDiscount((Concession) p);
			}
			// creating the line
			line.append(Productcode + addSpace(Productcode, 9, 10));
			line.append(Productlabel + addSpace(Productlabel, 19, 20));
			String productSubtotal = "$ " + String.format("%1.2f", subtotal);
			line.append(productSubtotal + this.addSpace(productSubtotal, 10, 5));
			String productDiscount = "$ " + String.format("%1.2f", discount);
			line.append(productDiscount + this.addSpace(productDiscount, 10, 5));
			String productTaxes = "$ " + String.format("%1.2f", taxes);
			line.append(productTaxes + this.addSpace(productTaxes, 10, 5));
			String productTotal = "$ " + String.format("%1.2f", total);
			line.append(productTotal + this.addSpace(productTotal, 10, 5));
			// printing the created line
			System.out.println(line.toString());
//			System.out.println(String.format("%3s %25s $ %2.2f %s $ %10.2f $ %10.2f $ %10.2f",
//					Productcode + " ".repeat(10), Productlabel+" ".repeat(23), subtotal," ".repeat(10), discount,taxes,total));

		}

	}

	private void printSummaryReport(MyLinkedList<Invoice> Invoices, List<Invoice> listOfInvoices) {
		System.out.println("Executive Summary Report:\n");
		System.out.println(String.format("%s %10s %30s %20s %15s %10s %15s %13s", "Code", "Owner", "Customer Account",
				"Subtotal", "Discounts", "Fees", "Taxes", "Total"));
		System.out.println("---".repeat(43));
		double subTotal = Calculator.calculateAllInvoicesSubtotals(listOfInvoices);
		double discounts = Calculator.calculateAllInvoicesDiscounts(listOfInvoices);
		double fees = Calculator.calculateAllInvoicesFees(listOfInvoices);
		double total = Calculator.calculateAllInvoicesGrandTotals(listOfInvoices);
		double taxes = Calculator.calculateAllInvoicesTaxes(listOfInvoices);

		for (int num = 0; num < Invoices.getsize(); num++) {
			Invoice inv = Invoices.retrieveItemAtIndex(num);
			String invoiceCode = inv.getInvoiceCode();
			// geting owenr first name
			String owenrlastName = Jr.getPerson(Jr.getPersonId(inv.getOwnerCode())).getLastName();
			String owenrfirstName = Jr.getPerson(Jr.getPersonId(inv.getOwnerCode())).getFirstName();
			String ownerFullName = owenrlastName + ", " + owenrfirstName;
			String customerAccount = Jr.getCustomer(Jr.getCustomerId(inv.getCustomerCode())).getName();
			double invoiceSubtotal = Calculator.calculateInvoiceSubtotal(inv);
			double invoiceDiscounts = Calculator.calculateIndividualDiscounts(inv);
			double invoiceFees = discounts = 0.0;
			if (Calculator.checkBusinessAccount(inv)) {
				invoiceFees = 75.50;
			}
			double totalTaxes = Calculator.calculateTotalTaxes(inv);
			double totalTotal = Calculator.calculateInvoiceItemTotals(inv);

			StringBuilder line = new StringBuilder();
			line.append(invoiceCode + " ".repeat(5));
			line.append(ownerFullName + this.addSpace(ownerFullName, 16, 7));
			line.append(customerAccount + this.addSpace(customerAccount, 15, 10));
			String invSubtotal = "$ " + String.format("%1.2f", invoiceSubtotal);
			line.append(invSubtotal + this.addSpace(invSubtotal, 10, 5));

			String invDiscount = "$ " + String.format("%1.2f", invoiceDiscounts);
			line.append(invDiscount + this.addSpace(invDiscount, 10, 5));

			String invFees = "$ " + String.format("%1.2f", invoiceFees);
			line.append(invFees + this.addSpace(invFees, 10, 5));

			String invTaxes = "$ " + String.format("%1.2f", totalTaxes);
			line.append(invTaxes + this.addSpace(invTaxes, 10, 5));

			String invTotal = "$ " + String.format("%1.2f", totalTotal);
			line.append(invTotal + this.addSpace(invTotal, 10, 5));

			System.out.println(line.toString());
		}
		StringBuilder Lastline = new StringBuilder();
		Lastline.append(" ".repeat(59) + String.format("$ %-1.2f", subTotal) + " ".repeat(6));
		Lastline.append("$ " + String.format("%-10.2f", discounts));
		Lastline.append("$ " + String.format("%-10.2f", fees));
		Lastline.append("$ " + String.format("%-10.2f", taxes));
		Lastline.append("$ " + String.format("%-10.2f", total));
		System.out.println("===".repeat(43));
		System.out.println(Lastline.toString());
	}

	/**
	 * 
	 * @param substring
	 * @param largestLength
	 * @param repeats       number of spaces to be returned
	 * @return spaces depend on largest Substring Length and substring
	 */

	private String addSpace(String substring, int largestLength, int repeats) {
		int lengthDiffernce = largestLength - substring.length();
		if (lengthDiffernce == 0) {
			return " ".repeat(repeats);
		} else {
			return " ".repeat(repeats + lengthDiffernce);
		}

	}

	

}

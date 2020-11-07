package com.bc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;



public class NewDisplayFunctions {
	InvoiceCalculator iCalculator = new InvoiceCalculator();
	
	
	public void displayDetailedInvoice(List<Invoice> lInv, List<Customer> lc, List<Person> lpers, List<Product> lprod) {
		
//		Display header 
		System.out.println("Invoice Details:");

		for (Invoice inv : lInv) {
			String str = "+=".repeat(40);
			System.out.println(str);
//			Print Invoice Number 

			System.out.print(inv.getInvoiceCode() + "\n");
//			Print Owner Details
			String ownerCode = inv.getOwnerCode();
			String customerCode = inv.getCustomerCode();
//			Verifying the availability of the Person and Customer 
			Person verifiedPerson = verifyPerson(ownerCode, lpers);
			Customer verifiedCustomer = verifyCustomer(customerCode, lc);

			displayDetailedInvoiceHeader(verifiedCustomer, verifiedPerson);
 
			
		}

	}

//	A function to verify a person is in the list persons 
	private Person verifyPerson(String personCode, List<Person> lpers) {
		// TODO Auto-generated method stub

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
		System.out.printf("%10s, %s \n", p.getLastName(), p.getFirstName());
		if (p.getEmailAddress() != null) {
			System.out.printf("%10s \n", p.getEmailAddress().getEmailAddress());
		} else {

			System.out.printf("%10s \n", "[]");
		}
		Address personAddress = p.getAddress();

		System.out.printf("%10s \n", personAddress.getStreet());
		System.out.printf("%10s, %s %s %s \n", personAddress.getCity(), personAddress.getState(),
				personAddress.getCountry(), personAddress.getZip());

	}

//	A function that is given a customer and prints his/her info (Name,Type, and Address)
	private void printCustomerInfo(Customer c) {

		System.out.print("Customer: \n");

		Boolean businessAccount = c.getCustomerType().equals("B");
		if (businessAccount) {
			System.out.printf("%10s %s \n", c.getName(), "(Business Account)");
		} else {
			System.out.printf("%10s \n", c.getName());
		}

		Address customerAddress = c.getAddress();

		System.out.printf("%10s \n", customerAddress.getStreet());
		System.out.printf("%10s, %s %s %s \n", customerAddress.getCity(), customerAddress.getState(),
				customerAddress.getCountry(), customerAddress.getZip());

	}

}

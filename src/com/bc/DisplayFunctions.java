package com.bc;

import java.util.ArrayList;
import java.util.List;

public class DisplayFunctions {
	
//Summary Report
	
	public static void summaryReport(List<Invoice> lInv, List<Customer> lc, ArrayList<Person> lpers) {
		
		System.out.println("Code			Owner			Customer Account 		Subtotal		Discount		Fees		Taxes			Total");
		System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		for (Invoice s: lInv) {
			
			
			
			System.out.printf("%s %23s %28s %45s  \n", s.getInvoiceCode(),s.getOwnerCode(),s.getCustomerCode(), s.getListOfProducts()) ;
			
//			loop through products 
			
			
		}
		
		String customerName;
		String personName;
		for (Customer c: lc) {
			
			customerName = c.getName();
			System.out.println(customerName);
			
			
		}
		
		
		
		
		
	}

//Detailed Report 
	
	public static String checkOwner(String ownerCode, ArrayList<Person> lpers,List<Customer> lc ) {
		String owner = null;
		
		Person pers = null;
		String personCode = null;
		
		for (Person person : lpers) {
			if (ownerCode.equals(person.getPersonCode())) {
				pers = person;
			}
		
		return null;
	}


}

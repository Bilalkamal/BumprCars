package com.bc;

import java.util.List;

public class DisplayFunctions {
	
//Summary Report
	
	public static void summaryReport(List<Invoice> lInv) {
		
		System.out.println("Code			Owner			Customer Account 		Subtotal		Discount		Fees		Taxes			Total");
		
		for (Invoice s: lInv) {
			System.out.printf("%s %23s %28s  \n", s.getInvoiceCode(),s.getOwnerCode(),s.getCustomerCode());
			
			
		}
		
		
		
	}

//Detailed Report 
	
	


}

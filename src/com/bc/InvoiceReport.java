/*
 * InvoiceReport is the main class has the main  to run the whole program  
 */

//This program was written by Mohammed Al Wahaibi and Bilal Hamada

package com.bc;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import java.util.List;

public class InvoiceReport {

//This is the main function that generates the invoice reports, both summary and detailed reports. 
//	It parses the invoices and passes them to the display functions class to do its calculations and write them to an output file
	public static void main(String[] args) {

// writer object that writes to the output file. It clears it first, before adding any data. 
		WriterFunction writer = new WriterFunction();
		
		writer.clear();

		
		
		ParserFunctions pf = new ParserFunctions();
		ArrayList<Person> lpers = (ArrayList<Person>) ParserFunctions.parsePersons();
		List<Product> lprod = ParserFunctions.parseProducts();
		List<Customer> lc = ParserFunctions.parseCustomers(lpers);
		List<Invoice> lInv = ParserFunctions.parseInvoices(lprod);

//	create a display functions object.
		DisplayFunctions dp = new DisplayFunctions();
		try {
			DisplayFunctions.summaryReport(lInv, lc, lpers, lprod);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DisplayFunctions.calculateDetailedInvoice(lInv, lc, lpers, lprod);

	}

}

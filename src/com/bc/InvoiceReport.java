package com.bc;

import java.util.ArrayList;

import java.util.List;

public class InvoiceReport {

//	Done --> TO-DO: Create an Invoice Class - with the required Specifications
	
//	Done --> TO-DO: Parser function for Invoices.dat
//				This should be done on the parser functions class
//	TO-DO: Report Functions for the Invoices 
//	TO-DO: Calling the System Report from Data converter 
	public static void main(String[] args) {
		
//Clearing the current ouput.txt		
	
	ParserFunctions pf = new ParserFunctions();
	ArrayList<Person> lpers = (ArrayList<Person>) ParserFunctions.parsePersons();
	List<Product> lprod = ParserFunctions.parseProducts();
	List<Customer> lc = ParserFunctions.parseCustomers(lpers);
	List<Invoice> lInv = ParserFunctions.parseInvoices(lprod);	

	
	DisplayFunctions dp = new DisplayFunctions();
	String summaryReport = DisplayFunctions.summaryReport(lInv,lc,lpers,lprod);
	DisplayFunctions.detailedReport(lprod,lInv,lc,lpers,lprod);

	WriterFunction writer = new WriterFunction();
	
	WriterFunction.write(summaryReport);
	
	}
	
	
	
	
	
}

package com.bc;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

public class InvoiceReport {

//	Done --> TO-DO: Create an Invoice Class - with the required specs 
	
//	Done --> TO-DO: Parser function for Invoices.dat
//				This should be done on the parser functions class
//	TO-DO: Report Functions for the Invoices 
//	TO-DO: Calling the System Report from Data converter 
	public static void main(String[] args) {
		
		WriterFunction writer = new WriterFunction();
		writer.clear();
	ParserFunctions pf = new ParserFunctions();
	ArrayList<Person> lpers = (ArrayList<Person>) ParserFunctions.parsePersons();
	List<Product> lprod = ParserFunctions.parseProducts();
	List<Customer> lc = ParserFunctions.parseCustomers(lpers);
	List<Invoice> lInv = ParserFunctions.parseInvoices(lprod);	
	

	
	DisplayFunctions dp = new DisplayFunctions();
	try {
		DisplayFunctions.summaryReport(lInv,lc,lpers,lprod);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	DisplayFunctions.invoiceReport(lInv,lc,lpers,lprod);

	
	
	}
	
	
	
	
	
}

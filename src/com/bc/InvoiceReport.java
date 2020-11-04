/*
 * InvoiceReport is the main class has the main  to run the whole program  
 */

//This program was written by Mohammed Al Wahaibi and Bilal Hamada

package com.bc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

import com.bc.ext.InvoiceData;

public class InvoiceReport {

//This is the main function that generates the invoice reports, both summary and detailed reports. 
//	It parses the invoices and passes them to the display functions class to do its calculations and write them to an output file
	public static void main(String[] args) {
		JDBCReader jr = new JDBCReader();
		jr.createConnection();

		ParserFunctions pf = new ParserFunctions();
		ArrayList<Person> lpers = (ArrayList<Person>) ParserFunctions.parsePersons();
		List<Product> lprod = ParserFunctions.parseProducts();
		List<Customer> lc = ParserFunctions.parseCustomers(lpers);
		List<Invoice> lInv = ParserFunctions.parseInvoices(lprod);
		


//		New Display function 
		NewDisplayFunctions ndf = new NewDisplayFunctions();
		ndf.displayDetailedInvoice(lInv,lc, lpers,lprod);



	}


}

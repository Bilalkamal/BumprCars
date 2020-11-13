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
	public static void main(String[] args) throws IOException {
		JDBCReader jr = new JDBCReader();
		jr.createConnection();

		List<Product> listOfProducts = jr.loadAllProducts();
		List<Person> listOfPersons = jr.loadAllPersons();
		List<Customer> listOfCustomers = jr.loadAllCustomer();
		List<Invoice> listOfInvoices = jr.loadAllInvoices();
		MyLinkedList<Invoice> aDTInvoiceLinkedList = new MyLinkedList<Invoice>(new InvoiceComparator());
		
		for (Invoice inv: listOfInvoices) {
			
		}

//		New Display function 
		NewDisplayFunctions ndf = new NewDisplayFunctions();
//		ndf.displayDetailedInvoice(listOfInvoices,listOfCustomers, listOfPersons,listOfProducts);
		ndf.displayReports(listOfInvoices,listOfCustomers, listOfPersons,listOfProducts);


		
	}


}

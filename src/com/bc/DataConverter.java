package com.bc;

import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// Built by Mohammed Al-Wahaibi and Bilal Hamada


public class DataConverter extends ParserFunctions {

	static String personsPaths = "data/Persons.dat";
	static String customersPaths = "data/Customers.dat";
	static String productsPaths = "data/Products.dat";
	static String invoicesPaths = "data/Invoices.dat";

	public static void main(String[] args) {

		ArrayList<Person> parsePerson = (ArrayList<Person>) parsePersons(personsPaths);

		ArrayList<Customer> parseCustomer = (ArrayList<Customer>) parseCustomers(customersPaths, parsePerson);

		ArrayList<Product> parseProduct = (ArrayList<Product>) parseProducts(productsPaths);
		

		JasonWriter.printJason("data/Products.json", parseProduct, "assets");
		JasonWriter.printJason("data/Persons.json", parsePerson, "persons");
		JasonWriter.printJason("data/Customers.json", parseCustomer, "customers");
		
		
		
//		Invoice Tasks
		
		
		
		 ArrayList<Invoice> parseInvoice = (ArrayList<Invoice>) parseInvoices(invoicesPaths);
		 
         
		 for(Invoice invoice : parseInvoice) {
			 
	            List<String> lp = invoice.getListOfProducts();
	            parseListOfProducts(lp,parseProduct);
	            System.out.println("---------------");
	            
	            
	           
	            
	            
	        }

	}


	public static void parseListOfProducts(List<String> productList, ArrayList<Product> listOfProducts) {
		
		Product p = null;
		
		
		for (int i =0; i<productList.size();i++) {
			String product = productList.get(i);
			
			String productTokens[] = product.split(":");
			
			for (Product prod : listOfProducts) {
				if (productTokens[0].equals(prod.getProductCode())){
					System.out.println(prod.getProductCode());
					System.out.println(prod.getProductLabel());
					System.out.println(prod.getProductType());
				}
			}
			
			
		}
		
		
//		Person pers = null;
//		for (Person person : listOfPersons) {
//			if (customerContactCode.equals(person.getPersonCode())) {
//				pers = person;
//			}
//
//		}
		
		
		
	
		
		
	}
	
	
}




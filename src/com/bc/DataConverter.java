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


public class DataConverter extends ParserFunctions{

	static String personsPaths = "data/Persons.dat";
	static String customersPaths = "data/Customers.dat";
	static String productsPaths = "data/Products.dat";

	public static void main(String[] args) {

		ArrayList<Person> parsePerson = (ArrayList<Person>) parsePersons(personsPaths);

		ArrayList<Customer> parseCustomer = (ArrayList<Customer>) parseCustomers(customersPaths, parsePerson);

		ArrayList<Product> parseProduct = (ArrayList<Product>) parseProducts(productsPaths);

		JasonWriter.printJason("data/Products.json", parseProduct, "assets");
		JasonWriter.printJason("data/Persons.json", parsePerson, "persons");
		JasonWriter.printJason("data/Customers.json", parseCustomer, "customers");

	}



}

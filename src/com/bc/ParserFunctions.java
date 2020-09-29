package com.bc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ParserFunctions {

//	Parser Functions

//	Person Parser
	public static List<Person> parsePersons(String path) {

		File pf = new File(path);
		Scanner s;
		try {
			s = new Scanner(pf);

		} catch (FileNotFoundException fnfe) {
			throw new RuntimeException(fnfe);
		}

		List<String> myArrayList = new ArrayList<String>();

		while (s.hasNext()) {

			myArrayList.add(s.nextLine());

		}

		s.close();

		int numOfLines = Integer.parseInt(myArrayList.get(0));
		
		

		

		List<Person> myPersonList = new ArrayList<Person>();

		for (int i = 1; i < numOfLines; i++) {
			String str = myArrayList.get(i);

			Person p = null;
			String tokens[] = str.split(";");

			String personCode = tokens[0];

			String addressTokens[] = tokens[2].split(",");

			String street = addressTokens[0];
			String city = addressTokens[1];
			String state = addressTokens[2];
			String zip = addressTokens[3];
			String country = addressTokens[4];

			Address personAddress = new Address(street, city, state, zip, country);

			String pName[] = tokens[1].split(",");
			Name personName = new PersonName(pName[1], pName[0]);

//			without email address
			if (tokens.length == 3) {
				p = new Person(personCode, personName, personAddress);
			}
//			With Email Address
			else if (tokens.length == 4) {

				String emailAddressTokens[] = tokens[3].split(",");
				EmailAddress emails = new EmailAddress(Arrays.asList(emailAddressTokens));

				p = new Person(personCode, pName[1], pName[0], personAddress, emails);
			}

			myPersonList.add(p);

		}
		return myPersonList;

	}

// Customer Parser
	public static List<Customer> parseCustomers(String path, ArrayList<Person> listOfPersons) {

		File pf = new File(path);
		Scanner s;
		try {
			s = new Scanner(pf);

		} catch (FileNotFoundException fnfe) {
			throw new RuntimeException(fnfe);
		}

		List<String> myArrayList = new ArrayList<String>();

		while (s.hasNext()) {

			myArrayList.add(s.nextLine());

		}

		s.close();

		int numOfLines = Integer.parseInt(myArrayList.get(0));
		

		
		List<Customer> coustomersList = new ArrayList<Customer>();

		for (int i = 1; i < myArrayList.size(); i++) {
			Customer c = null;
			String tokens[] = (myArrayList.get(i)).split(";");
			String customerCode = tokens[0];
			String customerType = tokens[1];

			String customerName = tokens[2];
			String customerContactCode = tokens[3];

			String addressTokens[] = tokens[4].split(",");

			String street = addressTokens[0];
			String city = addressTokens[1];
			String state = addressTokens[2];
			String zip = addressTokens[3];
			String country = addressTokens[4];

			Address address = new Address(street, city, state, zip, country);

			Person pers = null;
			for (Person person : listOfPersons) {
				if (customerContactCode.equals(person.getPersonCode())) {
					pers = person;
				}

			}

			c = new Customer(customerCode, customerType, customerName, pers, address);

			coustomersList.add(c);

		}
		return coustomersList;
	}

//	Products Parser

	public static List<Product> parseProducts(String path) {

		File pf = new File(path);
		Scanner s;
		try {
			s = new Scanner(pf);

		} catch (FileNotFoundException fnfe) {
			throw new RuntimeException(fnfe);
		}

		List<String> myArrayList = new ArrayList<String>();

		while (s.hasNext()) {

			myArrayList.add(s.nextLine());

		}

		s.close();

		int numOfLines = Integer.parseInt(myArrayList.get(0));

		List<Product> myProductList = new ArrayList<Product>();

		for (int i = 1; i < numOfLines; i++) {
			String str = myArrayList.get(i);

			Product p = null;
			String tokens[] = str.split(";");

			String productCode = tokens[0];
			String type = tokens[1];
			String productLabel = tokens[2];

			if (tokens[1].equals("R")) {

				Double dailyCost = Double.parseDouble(tokens[3]);
				Double deposit = Double.parseDouble(tokens[4]);
				Double cleaningFee = Double.parseDouble(tokens[5]);
				p = new Rental(productCode, type, productLabel, dailyCost, deposit, cleaningFee);

			} else if (tokens[1].equals("F")) {

				Double partsCost = Double.parseDouble(tokens[3]);
				Double hourlyLaborCost = Double.parseDouble(tokens[4]);
				p = new Repair(productCode, type, productLabel, partsCost, hourlyLaborCost);

			} else if (tokens[1].equals("C")) {

				Double unitCost = Double.parseDouble(tokens[3]);
				p = new Concession(productCode, type, productLabel, unitCost);

			} else if (tokens[1].equals("T")) {

				Double costPerMile = Double.parseDouble(tokens[3]);
				p = new Towing(productCode, type, productLabel, costPerMile);

			}

			myProductList.add(p);

		}
		return myProductList;

	}
	
	
}

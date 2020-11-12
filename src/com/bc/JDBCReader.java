package com.bc;

import java.sql.Connection;
import com.bc.InvoiceReport;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import com.mysql.cj.xdevapi.PreparableStatement;

/**
 * @author to be added
 *
 */
public class JDBCReader {

	public static Connection conn = null;

	public static Connection createConnection() {

		String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://cse.unl.edu/bhamada?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String user = "bhamada";
		String password = "0XkBzH6Q";

		try {
			Class.forName(DRIVER_CLASS).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			if (conn == null || conn.isClosed()) {
				try {

					conn = DriverManager.getConnection(url, user, password);
					return conn;

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					throw new RuntimeException(e);
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return conn;

	}

	public static Integer getPersonId(String personCode) {
		Connection conn = createConnection();

		String queryGetPersonId = "select personId from Person where personCode = ? ";
		PreparedStatement preparedStatementGetPersonId = null;
		ResultSet resultSetGetPersonID = null;
		Integer personId = null;

		try {
			preparedStatementGetPersonId = conn.prepareStatement(queryGetPersonId);
			preparedStatementGetPersonId.setString(1, personCode);
			resultSetGetPersonID = preparedStatementGetPersonId.executeQuery();

			while (resultSetGetPersonID.next()) {
				personId = resultSetGetPersonID.getInt("personId");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		try {
			preparedStatementGetPersonId.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		if (personId != null) {
			return personId;
		} else {
			return 0;
		}

	}

	public static Person getPerson(int personId) {
		Person person = null;
		String getPerson = "select * from Person where personId = ? ;";

		PreparedStatement PSGetPerson = null;
		ResultSet RSGetPerson = null;
		try {
			PSGetPerson = conn.prepareStatement(getPerson);
			PSGetPerson.setInt(1, personId);
			RSGetPerson = PSGetPerson.executeQuery();
			while (RSGetPerson.next()) {

				String personCode = RSGetPerson.getString("personCode");
				String firstName = RSGetPerson.getString("firstName");
				String lastName = RSGetPerson.getString("lastName");
				Integer addressId = RSGetPerson.getInt("addressId");

				Address personAddress = getAddress(addressId);
				EmailAddress emails = getEmails(personId);

				if (emails == null) {
					person = new Person(personCode, firstName, lastName, personAddress);
				} else {
					person = new Person(personCode, firstName, lastName, personAddress, emails);
				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Closing the prepareStatement and the ResultSet
		try {
			if (RSGetPerson != null && !RSGetPerson.isClosed()) {
				RSGetPerson.close();
			}
			if (PSGetPerson != null && !PSGetPerson.isClosed()) {
				PSGetPerson.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return person;
	}

	public static Customer getCustomer(int customerId) {
		Customer customer = null;
		String getCustomer = "select * from Customer where customerId = ? ;";

		PreparedStatement PSGetCustomer = null;
		ResultSet RSGetCustomer = null;
		try {
			PSGetCustomer = conn.prepareStatement(getCustomer);
			PSGetCustomer.setInt(1, customerId);
			RSGetCustomer = PSGetCustomer.executeQuery();
			while (RSGetCustomer.next()) {

				String customerCode = RSGetCustomer.getString("customerCode");
				String customerType = RSGetCustomer.getString("customerType");
				String customerName = RSGetCustomer.getString("customerName");
				String customerContactCode = RSGetCustomer.getString("customerContactCode");
				Integer addressId = RSGetCustomer.getInt("addressId");
				int personId = getPersonId(customerContactCode);
				Address customerAddress = getAddress(addressId);
				Person contactPerson = getPerson(personId);
				customer = new Customer(customerCode, customerType, customerName, contactPerson, customerAddress);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Closing the prepareStatement and the ResultSet
		try {
			if (RSGetCustomer != null && !RSGetCustomer.isClosed()) {
				RSGetCustomer.close();
			}
			if (PSGetCustomer != null && !PSGetCustomer.isClosed()) {
				PSGetCustomer.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return customer;
	}

	/**
	 * this method return address object using addressId
	 * 
	 * @param addressId
	 * @return Address object
	 */

	public static Address getAddress(int addressId) {

		Address address = new Address(null, null, null, null, null);
		String queryGetAddress = "select * from Address where addressId = ? ;";

		PreparedStatement PSGetAddress = null;
		ResultSet RSGetAddress = null;
		try {
			PSGetAddress = conn.prepareStatement(queryGetAddress);
			PSGetAddress.setInt(1, addressId);
			RSGetAddress = PSGetAddress.executeQuery();
			while (RSGetAddress.next()) {

				String street = RSGetAddress.getString("street");
				String city = RSGetAddress.getString("city");
				String state = RSGetAddress.getString("state");
				String zip = RSGetAddress.getString("zip");
				String country = RSGetAddress.getString("country");

				address = new Address(street, city, state, zip, country);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Closing the prepareStatement and the ResultSet
		try {
			if (RSGetAddress != null && !RSGetAddress.isClosed()) {
				RSGetAddress.close();
			}
			if (PSGetAddress != null && !PSGetAddress.isClosed()) {
				PSGetAddress.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return address;
	}

	/**
	 * 
	 * @param emailId
	 * @return A list of Emails if the person have email/s
	 */
	public static EmailAddress getEmails(int personId) {

		EmailAddress listOfEmailAddresses = new EmailAddress();

//		EmailAddress EmailAddress = new EmailAddress(null);
		String queryGetEmails = "select * from Email where personId = ? ;";

		PreparedStatement PSGetEmails = null;
		ResultSet RSGetEmails = null;
		try {
			PSGetEmails = conn.prepareStatement(queryGetEmails);
			PSGetEmails.setInt(1, personId);
			RSGetEmails = PSGetEmails.executeQuery();

			while (RSGetEmails.next()) {

				String email = RSGetEmails.getString("emailAddress");

				listOfEmailAddresses.addEmailAddress(email);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Closing the prepareStatement and the ResultSet
		try {
			if (RSGetEmails != null && !RSGetEmails.isClosed()) {
				RSGetEmails.close();
			}
			if (PSGetEmails != null && !PSGetEmails.isClosed()) {
				PSGetEmails.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listOfEmailAddresses;

	}

	public static List<Person> loadAllPersons() {
		List<Person> lst = new ArrayList<Person>();

		String query1 = "select * from Person;";

		PreparedStatement PSGetPersons = null;
		ResultSet RSGetPersons = null;
		try {
			PSGetPersons = conn.prepareStatement(query1);

			RSGetPersons = PSGetPersons.executeQuery();
			while (RSGetPersons.next()) {
				Integer personId = RSGetPersons.getInt("personId");
				String personCode = RSGetPersons.getString("personCode");
				String firstName = RSGetPersons.getString("firstName");
				String lastName = RSGetPersons.getString("lastName");
				Integer addressId = RSGetPersons.getInt("addressId");

				Address personAddress = getAddress(addressId);
				EmailAddress emails = getEmails(personId);

				Person p = null;

				if (emails == null) {
					p = new Person(personCode, firstName, lastName, personAddress);
				} else {
					p = new Person(personCode, firstName, lastName, personAddress, emails);
				}

				lst.add(p);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Closing the prepareStatement and the ResultSet
		try {
			if (RSGetPersons != null && !RSGetPersons.isClosed()) {
				RSGetPersons.close();
			}
			if (PSGetPersons != null && !PSGetPersons.isClosed()) {
				PSGetPersons.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lst;
	}

	public static List<Customer> loadAllCustomer() {
		List<Customer> lst = new ArrayList<Customer>();

		String query1 = "select * from Customer;";

		PreparedStatement PSGetCustomer = null;
		ResultSet RSGetCustomer = null;
		try {
			PSGetCustomer = conn.prepareStatement(query1);

			RSGetCustomer = PSGetCustomer.executeQuery();
			while (RSGetCustomer.next()) {
				String customerCode = RSGetCustomer.getString("customerCode");
				String customerType = RSGetCustomer.getString("customerType");
				String customerName = RSGetCustomer.getString("customerName");
				String customerContactCode = RSGetCustomer.getString("customerContactCode");
				Integer addressId = RSGetCustomer.getInt("addressId");
				int personId = getPersonId(customerContactCode);
				Address customerAddress = getAddress(addressId);
				Person contactPerson = getPerson(personId);
				Customer customer = new Customer(customerCode, customerType, customerName, contactPerson,
						customerAddress);

				lst.add(customer);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Closing the prepareStatement and the ResultSet
		try {
			if (RSGetCustomer != null && !RSGetCustomer.isClosed()) {
				RSGetCustomer.close();
			}
			if (PSGetCustomer != null && !PSGetCustomer.isClosed()) {
				PSGetCustomer.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lst;
	}

	public static List<Product> loadAllProducts() {
		List<Product> lst = new ArrayList<Product>();

		String getProduct = "select * from Product;";

		PreparedStatement PSGetProduct = null;
		ResultSet RSGetProduct = null;
		try {
			PSGetProduct = conn.prepareStatement(getProduct);

			RSGetProduct = PSGetProduct.executeQuery();

			while (RSGetProduct.next()) {

				String productCode = RSGetProduct.getString("productCode");
				String productType = RSGetProduct.getString("productType");
				String productLabel = RSGetProduct.getString("productLabel");

				if (productType.equals("R")) {
					Double dailyCost = RSGetProduct.getDouble("dailyCost");
					Double deposit = RSGetProduct.getDouble("deposit");
					Double cleaningFee = RSGetProduct.getDouble("cleaningFee");
					Rental rental = new Rental(productCode, productType, productLabel, dailyCost, deposit, cleaningFee);
					
					lst.add(rental);
				} else if (productType.equals("F")) {
					Double partsCost = RSGetProduct.getDouble("partsCost");
					Double hourlyLaborCost = RSGetProduct.getDouble("hourlyLaborCost");
					Repair repair = new Repair(productCode, productType, productLabel, partsCost, hourlyLaborCost);

					lst.add(repair);

				} else if (productType.equals("C")) {
					Double unitCost = RSGetProduct.getDouble("unitCost");
					Concession concession = new Concession(productCode, productType, productLabel, unitCost);

					lst.add(concession);

				} else if (productType.equals("T")) {
					Double costPerMile = RSGetProduct.getDouble("costPerMile");
					Towing towing = new Towing(productCode, productType, productLabel, costPerMile);

					lst.add(towing);

				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Closing the prepareStatement and the ResultSet
		try {
			if (RSGetProduct != null && !RSGetProduct.isClosed()) {
				RSGetProduct.close();
			}
			if (PSGetProduct != null && !PSGetProduct.isClosed()) {
				PSGetProduct.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lst;
	}

	public static List<Invoice> loadAllInvoices() {
		List<Invoice> listOfInvoices = new ArrayList<Invoice>();

		String queryGetInvoiceString = "select * from Invoice;";

		PreparedStatement pSGetinvoice = null;
		ResultSet RSGetInvoice = null;
		try {
			pSGetinvoice = conn.prepareStatement(queryGetInvoiceString);

			RSGetInvoice = pSGetinvoice.executeQuery();
			while (RSGetInvoice.next()) {
				String invoiceCode = RSGetInvoice.getString("invoiceCode");
				Integer personId = RSGetInvoice.getInt("personId");
				Integer customerId = RSGetInvoice.getInt("customerId");
				Integer invoiceId = RSGetInvoice.getInt("invoiceId");

				Person p = getPerson(personId);
				String personCode = p.getPersonCode();
				Customer c = getCustomer(customerId);
				String customerCode = c.getCustomerCode();
				List <Product> listOfProducts = loadProductInvoice(invoiceId);
				Invoice invoice = new  Invoice( invoiceCode,  personCode,  customerCode,  listOfProducts);
				listOfInvoices.add(invoice);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Closing the prepareStatement and the ResultSet
		try {
			if (RSGetInvoice != null && !RSGetInvoice.isClosed()) {
				RSGetInvoice.close();
			}
			if (pSGetinvoice != null && !pSGetinvoice.isClosed()) {
				pSGetinvoice.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listOfInvoices;
	}

	// This Function gets the product list related to a certain invoice
	public static List<Product> loadProductInvoice(Integer invoiceId) {
		List<Product> lProducts = new ArrayList<Product>();
		String queryLoadProductString = "select pi.invoiceId, pi.productId, pi.daysRented,"
				+ " pi.hoursWorked, pi.quantity, pi.milesTowed,"
				+ " pi.repairCode, p.productCode from ProductInvoice pi "
				+ "join Product p on p.productId = pi.productId where pi.invoiceId = ?;";

		PreparedStatement psGetProducts = null;
		ResultSet rsGetProductSet = null;

		Integer productId = null;
		Double daysRented = null;
		Double hoursWorked = null;
		Double quantity = null;
		Double milesTowed = null;
		String repairCode = null;
		String productCode = null;

		try {
			psGetProducts = conn.prepareStatement(queryLoadProductString);
			psGetProducts.setInt(1, invoiceId);
			rsGetProductSet = psGetProducts.executeQuery();

			while (rsGetProductSet.next()) {
				productId = rsGetProductSet.getInt("productId");
				daysRented = rsGetProductSet.getDouble("daysRented");
				hoursWorked = rsGetProductSet.getDouble("hoursWorked");
				quantity = rsGetProductSet.getDouble("quantity");
				milesTowed = rsGetProductSet.getDouble("milesTowed");
				repairCode = rsGetProductSet.getString("repairCode");
				productCode = rsGetProductSet.getString("productCode");
				Product product = null;

				List<Product> currentProducts = loadAllProducts();

				for (Product p : currentProducts) {
					if (p.getProductCode().equals(productCode)) {
						if (p.getProductType().equals("R")) {
							product = new Rental((Rental) p, daysRented);
							
						} else if (p.getProductType().equals("F")) {
							product = new Repair((Repair) p, hoursWorked);
							
						} else if (p.getProductType().equals("T")) {
							product = new Towing((Towing) p, milesTowed);
							
						} else if (p.getProductType().equals("C") && (repairCode != null)) {
							product = new Concession((Concession) p, quantity, repairCode);
							
						} else if (p.getProductType().equals("C")) {
							product = new Concession((Concession) p, quantity);
							
						}
					}
				}

				lProducts.add(product);

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		try {
			psGetProducts.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return lProducts;

	}

}

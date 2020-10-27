package com.bc.ext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.bc.Address;
/* DO NOT change or remove the import statements beneath this.
* They are required for the webgrader to run this phase of the project.
* These lines may be giving you an error; this is fine. 
* These are webgrader code imports, you do not need to have these packages.
*/
import com.bc.Concession;
import com.bc.Invoice;
import com.bc.Customer;
import com.bc.Towing;
import com.bc.Person;
import com.bc.Product;
import com.bc.Rental;
import com.bc.Repair;

/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application. 16 methods in
 * total, add more if required. Do not change any method signatures or the
 * package name.
 * 
 * Adapted from Dr. Hasan's original version of this file
 * 
 * @author Chloe
 *
 */

public class InvoiceData {

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

		Connection conn = null;

		try {

			conn = DriverManager.getConnection(url, user, password);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}

		return conn;
	}

	/**
	 * 1. Method that removes every person record from the database
	 */
	public static void removeAllPersons() {
		/* TODO */

		Connection conn = createConnection();
//		The Queries to delete the Invoices
		String queryDisableSafeMode = "SET SQL_SAFE_UPDATES=0;";
		String queryDeleteProductInvoice = "delete from ProductInvoice;";
		String queryDeleteInvoice = "delete from Invoice;";
		String queryDeleteCustomer = "delete from Customer;";
		String queryDeleteEmail = "delete from Email;";
		String queryDeletePerson = "delete from Person;";
		String queryEnableSafeMode = "SET SQL_SAFE_UPDATES=1;";

//		Prepared Statement for executing the query.
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(queryDisableSafeMode);
			ps.executeUpdate();
			ps = conn.prepareStatement(queryDeleteProductInvoice);
			ps.executeUpdate();
			ps = conn.prepareStatement(queryDeleteInvoice);
			ps.executeUpdate();
			ps = conn.prepareStatement(queryDeleteCustomer);
			ps.executeUpdate();
			ps = conn.prepareStatement(queryDeleteEmail);
			ps.executeUpdate();
			ps = conn.prepareStatement(queryDeletePerson);
			ps.executeUpdate();
			ps = conn.prepareStatement(queryEnableSafeMode);
			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		Close the PS and Connection
		try {
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 2. Method to add a person record to the database with the provided data.
	 * 
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */

	public static void addPerson(String personCode, String firstName, String lastName, String street, String city,
			String state, String zip, String country) {
		/* TODO */

//		Create connection
		Connection conn = createConnection();

		Address address = new Address(street, city, state, zip, country);
//		create query to add a person 
		String query = "insert into Person (personCode, firstName, " + "lastName, address) values (?,?,?);";
//		Prepared Statement for executing the query.
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, personCode);
			ps.setString(2, firstName);
			ps.setString(2, lastName);
			ps.setString(4, address.toString());
			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		Close the prepared statement.
		try {
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 3. Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * 
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		/* TODO */
//		Create connection
		Connection conn = createConnection();

		String query = "insert into Email (emailAddress, personId) values (?,(select personId from Person  where personCode = ? ));";

//		Prepared Statement for executing the query.
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, personCode);
			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		Close the prepared statement.
		try {
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 4. Method that removes every customer record from the database
	 */
	public static void removeAllCusomters() {
		/* TODO */

		Connection conn = createConnection();
//		The Queries to delete the Invoices
		String queryDisableSafeMode = "SET SQL_SAFE_UPDATES=0;";
		String queryDeleteProductInvoice = "delete from ProductInvoice;";
		String queryDeleteInvoice = "delete from Invoice;";
		String queryDeleteCustomer = "delete from Customer;";
		String queryEnableSafeMode = "SET SQL_SAFE_UPDATES=1;";

//		Prepared Statement for executing the query.
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(queryDisableSafeMode);
			ps.executeUpdate();
			ps = conn.prepareStatement(queryDeleteProductInvoice);
			ps.executeUpdate();
			ps = conn.prepareStatement(queryDeleteInvoice);
			ps.executeUpdate();
			ps = conn.prepareStatement(queryDeleteCustomer);
			ps.executeUpdate();
			ps = conn.prepareStatement(queryEnableSafeMode);
			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		Close the PS and Connection
		try {
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 5. Method to add a customer record to the database with the provided data
	 * 
	 * @param customerCode
	 * @param customerType
	 * @param primaryContactPersonCode
	 * @param name
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addCustomer(String customerCode, String customerType, String primaryContactPersonCode,
			String name, String street, String city, String state, String zip, String country) {
		/* TODO */
//		Create connection
		Connection conn = createConnection();

		Address address = new Address(street, city, state, zip, country);
//		create query to add a person 
		String query = "insert into Customer (customerCode,customerType,customerName,customerAddress,customerContactCode"
				+ "personId )" + " values (?,?,?,?,(select personCode from Person where personCode = ?)"
				+ ",(select personId from Person where personCode = ? ));";
//		Prepared Statement for executing the query.
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, customerCode);
			ps.setString(2, customerType);
			ps.setString(3, name);
			ps.setString(4, address.toString());
			ps.setString(5, primaryContactPersonCode);
			ps.setString(6, primaryContactPersonCode);
			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		Close the prepared statement.
		try {
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 6. Removes all product records from the database
	 */
	public static void removeAllProducts() {
		/* TODO */
		Connection conn = createConnection();
//		The Queries to delete the Invoices
		String queryDisableSafeMode = "SET SQL_SAFE_UPDATES=0;";
		String queryDeleteProductInvoice = "delete from ProductInvoice;";
		String queryDeleteProduct = "delete from Product;";
		String queryEnableSafeMode = "SET SQL_SAFE_UPDATES=1;";

//		Prepared Statement for executing the query.
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(queryDisableSafeMode);
			ps.executeUpdate();
			ps = conn.prepareStatement(queryDeleteProductInvoice);
			ps.executeUpdate();
			ps = conn.prepareStatement(queryDeleteProduct);
			ps.executeUpdate();
			ps = conn.prepareStatement(queryEnableSafeMode);
			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		Close the PS and Connection
		try {
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 7. Adds a concession record to the database with the provided data.
	 * 
	 * @param productCode
	 * @param productLabel
	 * @param unitCost
	 */
	public static void addConcessionToInvoice(String productCode, String productLabel, double unitCost) {
		/* TODO */

//		Create connection
		Connection conn = createConnection();

	}

	/**
	 * 8. Adds a repair record to the database with the provided data.
	 * 
	 * @param productCode
	 * @param proudctLabel
	 * @param partsCost
	 * @param laborRate
	 */
	public static void addRepair(String productCode, String productLabel, double partsCost, double laborRate) {
		/* TODO */
//		Create Connection
		Connection conn = createConnection();
//		 "R"
		String queryAddRental = "insert into Product (productCode,productType,productLabel,"
				+ "partsCost,hourlyLaborCost) values (?,?,?,?,?)";

//		Prepared Statement for executing the query.
		PreparedStatement ps = null;


		try {
			ps = conn.prepareStatement(queryAddRental);
			ps.setString(1, productCode);
			ps.setString(2, "F");
			ps.setString(2, productLabel);
			ps.setDouble(4, partsCost);
			ps.setDouble(5, laborRate);
			ps.executeUpdate();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Close the prepared statement.
		try {
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}

	/**
	 * 9. Adds a towing record to the database with the provided data.
	 * 
	 * @param productCode
	 * @param productLabel
	 * @param costPerMile
	 */
	public static void addTowing(String productCode, String productLabel, double costPerMile) {
		/* TODO */
//		Create Connection
		Connection conn = createConnection();
//		 "R"
		String queryAddRental = "insert into Product (productCode,productType,productLabel,"
				+ "costPerMile) values (?,?,?,?)";

//		Prepared Statement for executing the query.
		PreparedStatement ps = null;


		try {
			ps = conn.prepareStatement(queryAddRental);
			ps.setString(1, productCode);
			ps.setString(2, "T");
			ps.setString(2, productLabel);
			ps.setDouble(4, costPerMile);
			ps.executeUpdate();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Close the prepared statement.
		try {
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * 10. Adds a rental record to the database with the provided data.
	 * 
	 * @param productCode
	 * @param productLabel
	 * @param dailyCost
	 * @param deposit
	 * @param cleaningFee
	 */
	public static void addRental(String productCode, String productLabel, double dailyCost, double deposit,
			double cleaningFee) {
		/* TODO */
//		Create Connection
		Connection conn = createConnection();
//		 "R"
		String queryAddRental = "insert into Product (productCode,productType,productLabel,"
				+ "dailyCost,deposit) values (?,?,?,?,?)";

//		Prepared Statement for executing the query.
		PreparedStatement ps = null;


		try {
			ps = conn.prepareStatement(queryAddRental);
			ps.setString(1, productCode);
			ps.setString(2, "R");
			ps.setString(2, productLabel);
			ps.setDouble(4, dailyCost);
			ps.setDouble(5, deposit);
			ps.executeUpdate();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Close the prepared statement.
		try {
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * 11. Removes all invoice records from the database
	 */
	public static void removeAllInvoices() {
		/* TODO */
		Connection conn = createConnection();
//		The Queries to delete the Invoices
		String queryDisableSafeMode = "SET SQL_SAFE_UPDATES=0;";
		String queryDeleteProductInvoice = "delete from ProductInvoice;";
		String queryDeleteInvoice = "delete from Invoice;";
		String queryEnableSafeMode = "SET SQL_SAFE_UPDATES=1;";

//		Prepared Statement for executing the query.
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(queryDisableSafeMode);
			ps.executeUpdate();
			ps = conn.prepareStatement(queryDeleteProductInvoice);
			ps.executeUpdate();
			ps = conn.prepareStatement(queryDeleteInvoice);
			ps.executeUpdate();
			ps = conn.prepareStatement(queryEnableSafeMode);
			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		Close the PS and Connection
		try {
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 12. Adds an invoice record to the database with the given data.
	 * 
	 * @param invoiceCode
	 * @param ownerCode
	 * @param customertCode
	 */
	public static void addInvoice(String invoiceCode, String ownerCode, String customerCode) {
		/* TODO */
	}

	/**
	 * 13. Adds a particular Towing (corresponding to <code>productCode</code> to an
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of miles towed
	 * 
	 * @param invoiceCode
	 * @param productCode
	 * @param milesTowed
	 */
	public static void addTowingToInvoice(String invoiceCode, String productCode, double milesTowed) {
		/* TODO */
	}

	/**
	 * 14. Adds a particular Repair (corresponding to <code>productCode</code> to an
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of hours worked
	 * 
	 * @param invoiceCode
	 * @param productCode
	 * @param hoursWorked
	 */
	public static void addRepairToInvoice(String invoiceCode, String productCode, double hoursWorked) {
		/* TODO */
	}

	/**
	 * 15. Adds a particular Concession (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with the
	 * given number of quantity. NOTE: repairCode may be null
	 * 
	 * @param invoiceCode
	 * @param productCode
	 * @param quantity
	 * @param repairCode
	 */
	public static void addConcession(String invoiceCode, String productCode, int quantity, String repairCode) {
		/* TODO */

//		Create Connection
		Connection conn = createConnection();
//		 "R"
		String queryAddRental = "insert into Product (productCode,productType,productLabel,"
				+ "quantity,repairCode) values (?,?,?,?,?)";

//		Prepared Statement for executing the query.
		PreparedStatement ps = null;


		try {
			ps = conn.prepareStatement(queryAddRental);
			ps.setString(1, productCode);
			ps.setString(2, "C");
			ps.setDouble(3, quantity);
			ps.setString(4, repairCode);
			ps.executeUpdate();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Close the prepared statement.
		try {
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
	}
	}
	/**
	 * 16. Adds a particular Rental (corresponding to <code>productCode</code> to an
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of days rented.
	 * 
	 * @param invoiceCode
	 * @param productCode
	 * @param daysRented
	 */
	public static void addRentalToInvoice(String invoiceCode, String productCode, double daysRented) {
		/* TODO */

	}

}

package com.bc;

public class Customer {
	private String customerCode;
	private String customerType;
	private String name;
	private String firstName;
	private String lastName;
	private Person customerContactCode;
	private Address address;
	
	
	
//	constructor method
	public Customer(String customerCode, String customerType, String name, Person customerContactCode, Address address) {
		
		this.customerCode = customerCode;
		this.customerType = customerType;
		this.name = name;
		this.customerContactCode = customerContactCode;
		this.address = address;
	}

	// getters and setters
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public Person getCustomerContactCode() {
		return customerContactCode;
	}
	public void setCustomerContactCode(Person customerContactCode) {
		this.customerContactCode = customerContactCode;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}

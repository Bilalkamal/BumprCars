package com.bc;

public class Person {

	private String code;
//	private Name name;
	private String firstName;
	private String lastName;
	private Address address;
	private EmailAddress emails;
	
	
	
	
//	Constructor Method
	
	

	public Person(String code, String firstName, String lastName, Address address, EmailAddress emails) {
		super();
		this.code = code;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.emails = emails;
	}
	
	public Person(String code, Name name, Address address) {
		super();
		this.code = code;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;

	}

	
	

	public String getPersonCode() {
		return code;
	}

	public void setPersonCode(String personCode) {
		this.code = personCode;
	}



	/**
	 * @return the firstName
	 */
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public EmailAddress getEmailAddress() {
		return emails;
	}

	public void setEmailAddress(EmailAddress emailAddress) {
		this.emails = emailAddress;
	}
	
	
	 

}

package com.bc;

import java.util.List;

public class EmailAddress {

	private List emailAddress;

//	Constructor Method
	public EmailAddress(List emailAddress) {
		super();
		this.emailAddress = emailAddress;
	}
	public EmailAddress() {
		super();
	}

	/**
	 * @return the emailAddress
	 */
	public List<String> getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddresses(List<String> emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public void addEmailAddress(String email) {
		this.emailAddress.add(email);
		
	}
	
	public Integer getLength() {
		return this.emailAddress.size();
		
	}

}

package com.bc;

import java.util.List;

public class EmailAddress {

	private List emailAddress;

//	Constructor Method
	public EmailAddress(List emailAddress) {
		super();
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the emailAddress
	 */
	public List getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(List emailAddress) {
		this.emailAddress = emailAddress;
	}

}

package com.bc;

import java.util.ArrayList;
import java.util.List;

public class EmailAddress {

	private List<String> emailAddress;

//	Constructor Method
	public EmailAddress(List<String> emailAddress) {
		super();
		this.emailAddress = emailAddress;
	}

	
	public EmailAddress() {
		this.emailAddress = new ArrayList<String>();
		}

	@Override
	public String toString() {
		return "EmailAddress [emailAddress=" + emailAddress + "]";
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

	public void setEmailAddress(List<String> emailAddress) {

		this.emailAddress = emailAddress;
	}
	
	public void addEmailAddress(String email) {
		this.emailAddress.add(email);


		}
	
	public Integer getLength() {
		return this.emailAddress.size();

		}
	


}

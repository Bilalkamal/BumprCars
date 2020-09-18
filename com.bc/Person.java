
public class Person {

	private String personCode;
	private Name name;
	private Address address;
	private EmailAddress emailAddress;
	
	
	
	
//	Constructor Method
	
	

	public Person(String personCode, Name name, Address address, EmailAddress emailAddress) {
		super();
		this.personCode = personCode;
		this.name = name;
		this.address = address;
		this.emailAddress = emailAddress;
	}
	
	public Person(String personCode, Name name, Address address) {
		super();
		this.personCode = personCode;
		this.name = name;
		this.address = address;

	}

	
	

	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public EmailAddress getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(EmailAddress emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	
	 

}

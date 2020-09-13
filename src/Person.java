
public class Person {

	private String personCode;
	private String name;
	private Address adress;
	private String emailAddress;
	
	
	
	
//	Constructor Method
	
	

	public Person(String personCode, String name, Address adress, String emailAddress) {
		super();
		this.personCode = personCode;
		this.name = name;
		this.adress = adress;
		this.emailAddress = emailAddress;
	}

	

	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAdress() {
		return adress;
	}

	public void setAdress(Address adress) {
		this.adress = adress;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	
	 

}

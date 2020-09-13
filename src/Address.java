
public class Address extends Person {
	
	
	private String street;
	private String city;
	private String state;
	private String country;
	public Address(String personCode, String name, Address adress, String emailAddress) {
		super(personCode, name, adress, emailAddress);
		this.adress=adress;
		// TODO Auto-generated constructor stub
	}
	
	
	
	//getters and setters
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}


	
	
	

}

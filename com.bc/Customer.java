
public class Customer {
	private String customerCode;
	private String customerType;
	private Name name;
	private String customerContactCode;
	private Address address;
	
	
	
//	constructor method
	public Customer(String customerCode, String customerType, Name name, String customerContactCode, Address address) {
		
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
	public String getCustomerContactCode() {
		return customerContactCode;
	}
	public void setCustomerContactCode(String customerContactCode) {
		this.customerContactCode = customerContactCode;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Name getName() {
		return name;
	}
	public void setName(Name name) {
		this.name = name;
	}
	

}

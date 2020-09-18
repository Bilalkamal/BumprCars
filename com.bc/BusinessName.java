
public class BusinessName extends Name{

	String name;

	public BusinessName(String name) {
		super();
		this.name = name;
	}

	/**
	 * @return the businessName
	 */
	public String getBusinessName() {
		return name;
	}

	/**
	 * @param businessName the businessName to set
	 */
	public void setBusinessName(String businessName) {
		this.name = businessName;
	}

	@Override
	public String toString() {
		return "BusinessName [businessName=" + name + "]";
	}
	
	
	
	
}

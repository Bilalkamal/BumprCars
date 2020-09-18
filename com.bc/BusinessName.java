
public class BusinessName extends Name{

	String businessName;

	public BusinessName(String businessName) {
		super();
		this.businessName = businessName;
	}

	/**
	 * @return the businessName
	 */
	public String getBusinessName() {
		return businessName;
	}

	/**
	 * @param businessName the businessName to set
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	@Override
	public String toString() {
		return "BusinessName [businessName=" + businessName + "]";
	}
	
	
	
	
}

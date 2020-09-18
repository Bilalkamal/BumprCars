
public class Product {
	
	private String productCode;
	private String productType;
	private String productLabel;
//	private Double dailyCost;
//	private Double deposit;
//	private Double cleaningFee;
//	private Double partsCost;
//	private Double hourlyLaborCost;
//	private Double unitCost;
//	private Double costPerMile;
	
	
	
//	Constructor Method
	public Product(String productCode, String productType, String productLabel) {
		
		this.productCode = productCode;
		this.productType = productType;
		this.productLabel = productLabel;
	}
	
	
	
	
	
	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}
	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}
	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}
	/**
	 * @return the productLabel
	 */
	public String getProductLabel() {
		return productLabel;
	}
	/**
	 * @param productLabel the productLabel to set
	 */
	public void setProductLabel(String productLabel) {
		this.productLabel = productLabel;
	}

	

	
	
}

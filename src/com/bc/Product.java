package com.bc;


public class Product {
	
	private String code;
	private String type;
	private String label;

	
	
//	Constructor Method
	public Product(String code, String type, String label) {
		
		this.code = code;
		this.type = type;
		this.label = label;
	}
	
	
	
	
	
	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return code;
	}
	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.code = productCode;
	}
	/**
	 * @return the productType
	 */
	public String getProductType() {
		return type;
	}
	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.type = productType;
	}
	/**
	 * @return the productLabel
	 */
	public String getProductLabel() {
		return label;
	}
	/**
	 * @param productLabel the productLabel to set
	 */
	public void setProductLabel(String productLabel) {
		this.label = productLabel;
	}

	

	
	
}


public class Product {
	
	private String productCode;
	private Character productType;
	private String productLabel;
	private Double dailyCost;
	private Double deposit;
	private Double cleaningFee;
	private Double partsCost;
	private Double hourlyLaborCost;
	private Double unitCost;
	private Double costPerMile;
	
//	Constructor Method
	
	public Product(String productCode, Character productType, String productLabel, Double dailyCost, Double deposit,
			Double cleaningFee, Double partsCost, Double hourlyLaborCost, Double unitCost, Double costPerMile) {
		this.productCode = productCode;
		this.productType = productType;
		this.productLabel = productLabel;
		this.dailyCost = dailyCost;
		this.deposit = deposit;
		this.cleaningFee = cleaningFee;
		this.partsCost = partsCost;
		this.hourlyLaborCost = hourlyLaborCost;
		this.unitCost = unitCost;
		this.costPerMile = costPerMile;
	}
	
//	Getters and Setters methods 
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public Character getProductType() {
		return productType;
	}
	public void setProductType(Character productType) {
		this.productType = productType;
	}
	public String getProductLabel() {
		return productLabel;
	}
	public void setProductLabel(String productLabel) {
		this.productLabel = productLabel;
	}
	public Double getDailyCost() {
		return dailyCost;
	}
	public void setDailyCost(Double dailyCost) {
		this.dailyCost = dailyCost;
	}
	public Double getDeposit() {
		return deposit;
	}
	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}
	public Double getCleaningFee() {
		return cleaningFee;
	}
	public void setCleaningFee(Double cleaningFee) {
		this.cleaningFee = cleaningFee;
	}
	public Double getPartsCost() {
		return partsCost;
	}
	public void setPartsCost(Double partsCost) {
		this.partsCost = partsCost;
	}
	public Double getHourlyLaborCost() {
		return hourlyLaborCost;
	}
	public void setHourlyLaborCost(Double hourlyLaborCost) {
		this.hourlyLaborCost = hourlyLaborCost;
	}
	public Double getUnitCost() {
		return unitCost;
	}
	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}
	public Double getCostPerMile() {
		return costPerMile;
	}
	public void setCostPerMile(Double costPerMile) {
		this.costPerMile = costPerMile;
	}
	
	
	
}

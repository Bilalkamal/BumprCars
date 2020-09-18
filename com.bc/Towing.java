
public class Towing  extends Product{

	
	private Double costPerMile;

	public Towing(String productCode, String productType, String productLabel, Double costPerMile) {
		super(productCode, productType, productLabel);
		this.costPerMile = costPerMile;
	}
	
	
	public String toString() {
		return this.getProductCode() + " " + this.getProductType() + " " + this.getProductLabel() + " " + 
				this.getCostPerMile()+ "\n";
		
		
		
	}
	

	/**
	 * @return the costPerMile
	 */
	public Double getCostPerMile() {
		return costPerMile;
	}

	/**
	 * @param costPerMile the costPerMile to set
	 */
	public void setCostPerMile(Double costPerMile) {
		this.costPerMile = costPerMile;
	}
	
	
	
}

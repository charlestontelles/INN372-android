package au.edu.qut.inn372.greenhat.bean;

import java.io.Serializable;

import org.ksoap2.serialization.SoapObject;

/**
 * Inverter Bean
 * 
 * @author Charleston Telles
 *
 */
public class Inverter extends AndroidAbstractBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2926103358171541087L;
	private double efficiency;
	private double lifespan;
	private double replacementCost;
	private double cost;
	
	/**
	 * Get the efficiency
	 * @return efficiency value of the inverter efficiency
	 */
	public double getEfficiency() {
		return efficiency;
	}
	
	/**
	 * Set the efficiency
	 * @param efficiency new value for the inverter efficiency
	 */
	public void setEfficiency(double efficiency) {
		this.efficiency = efficiency;
	}
	
	/**
	 * Get the lifespan
	 * @return lifespan value for the lifespan
	 */
	public double getLifespan() {
		return lifespan;
	}
	
	/**
	 * Set the lifespan
	 * @param lifespan new value for the lifespan
	 */
	public void setLifespan(double lifespan) {
		this.lifespan = lifespan;
	}
	
	/**
	 * Get the replacement cost
	 * @return replacementCost value of the replacement cost
	 */
	public double getReplacementCost() {
		return replacementCost;
	}
	
	/**
	 * Set the replacementCost
	 * @param replacementCost new value for the replacement cost
	 */
	public void setReplacementCost(double replacementCost) {
		this.replacementCost = replacementCost;
	}

	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * @param cost the cost to set
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	/**
	 * ATTENTION: ALL VALUES MUST BE CONVERTED TO STRING
	 */
	@Override
	protected void setSoapObject(int soapOperation) {
		switch (soapOperation) {
		case AndroidAbstractBean.OPERATION_CALC_ENERGY_PRODUCTION:
			this.soapObject = new SoapObject(AndroidAbstractBean.NAMESPACE,AndroidAbstractBean.OPERATION_CALC_ENERGY_PRODUCTION_NAME);
			this.soapObject = setDefaultSoapObject(this.soapObject);
			break;
		case AndroidAbstractBean.OPERATION_SAVE_CALCULATION:
			this.soapObject = new SoapObject(AndroidAbstractBean.NAMESPACE,AndroidAbstractBean.OPERATION_SAVE_CALCULATION_NAME);
			this.soapObject = setDefaultSoapObject(this.soapObject);
			break;
		default:
			this.soapObject = new SoapObject("", "inverter");
			this.soapObject = setDefaultSoapObject(this.soapObject);
			break;
		}
	}
	
	/**
	 * Adds all object properties to the given soap object and returns it
	 * @param currentSoapObject Soap object that fields are added to
	 * @return Amended soap object with fields
	 */
	private SoapObject setDefaultSoapObject(SoapObject currentSoapObject) {
		currentSoapObject.addProperty("efficiency", ""+this.efficiency);
		currentSoapObject.addProperty("lifespan", ""+this.lifespan);
		currentSoapObject.addProperty("replacementCost", ""+this.replacementCost);
		currentSoapObject.addProperty("cost", ""+this.cost);
		return currentSoapObject;
	}
}

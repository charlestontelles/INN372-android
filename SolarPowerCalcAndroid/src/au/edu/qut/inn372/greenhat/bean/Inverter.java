package au.edu.qut.inn372.greenhat.bean;

import org.ksoap2.serialization.SoapObject;

/**
 * Inverter Bean
 * 
 * @author Charleston Telles
 *
 */
public class Inverter extends AndroidAbstractBean{
	private double efficiency;
	private double lifespan;
	private double replacementCost;
	public double getEfficiency() {
		return efficiency;
	}
	public void setEfficiency(double efficiency) {
		this.efficiency = efficiency;
	}
	public double getLifespan() {
		return lifespan;
	}
	public void setLifespan(double lifespan) {
		this.lifespan = lifespan;
	}
	public double getReplacementCost() {
		return replacementCost;
	}
	public void setReplacementCost(double replacementCost) {
		this.replacementCost = replacementCost;
	}
	
	@Override
	protected void setSoapObject(int soapOperation) {
		this.soapObject = new SoapObject("", "inverter");
		this.soapObject.addProperty("efficiency", ""+this.efficiency);
		this.soapObject.addProperty("lifespan", ""+this.lifespan);
	}
}

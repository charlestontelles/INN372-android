package au.edu.qut.inn372.greenhat.bean;

import java.io.Serializable;
import java.util.ArrayList;

import org.ksoap2.serialization.SoapObject;

/**
 * Roof Bean
 * 
 * @author Charleston Telles
 *
 */
public class Roof extends AndroidAbstractBean implements Serializable {

	private static final long serialVersionUID = 5693650139345709242L;
	private double width;
	private double height;
	private double percentageNorth;
	private double percentageWest;
	private double efficiencyLossNorth;
	private double efficiencyLossWest;
	
	private ArrayList<Bank> banks;
	
	public Roof(){
		banks = new ArrayList<Bank>();
		for (int i=0; i < 2; i++){
			banks.add(new Bank());
		}
	}
	
	/**
	 * @return the banks
	 */
	public ArrayList<Bank> getBanks() {
		return banks;
	}

	/**
	 * @param banks the banks to set
	 */
	public void setBanks(ArrayList<Bank> banks) {
		this.banks = banks;
	}
	
	
	/**
	 * Get the efficiency loss on north roof
	 * @return efficiencyLossNorth on the north roof
	 */
	public double getEfficiencyLossNorth() {
		return efficiencyLossNorth;
	}
	
	/**
	 * Set the efficiency loss on north roof
	 * @param efficiencyLossNorth new value for the efficiencyLossNorth property
	 */
	public void setEfficiencyLossNorth(double efficiencyLossNorth) {
		this.efficiencyLossNorth = efficiencyLossNorth;
	}
	
	/**
	 * Get the efficiency loss on west roof
	 * @return efficiencyLossWest on the west roof
	 */
	public double getEfficiencyLossWest() {
		return efficiencyLossWest;
	}
	
	/**
	 * Set the efficiency loss on west roof
	 * @param efficiencyLossWest new value for the efficiencyLossWest property
	 */
	public void setEfficiencyLossWest(double efficiencyLossWest) {
		this.efficiencyLossWest = efficiencyLossWest;
	}
	
	/**
	 * Get the width
	 * @return width of the roof
	 */
	public double getWidth() {
		return width;
	}
	
	/**
	 * Set the width
	 * @param width new value of the roof
	 */
	public void setWidth(double width) {
		this.width = width;
	}
	
	/**
	 * Get the height
	 * @return height of the roof
	 */
	public double getHeight() {
		return height;
	}
	
	/**
	 * Set the height
	 * @param height new value for the roof
	 */
	public void setHeight(double height) {
		this.height = height;
	}
	
	/**
	 * Get the percentage on north roof
	 * @return percentageNorth of the north roof
	 */
	public double getPercentageNorth() {
		return percentageNorth;
	}
	
	/**
	 * Set the percentage on north roof
	 * @param percentageNorth new value of the north roof
	 */
	public void setPercentageNorth(double percentageNorth) {
		this.percentageNorth = percentageNorth;
	}
	
	/**
	 * Get the percentage on west roof
	 * @return percentageWest of the west roof
	 */
	public double getPercentageWest() {
		return percentageWest;
	}
	
	/**
	 * Set the percentage on west roof
	 * @param percentageWest new value for the west roof
	 */
	public void setPercentageWest(double percentageWest) {
		this.percentageWest = percentageWest;
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
			this.soapObject = new SoapObject("", "roof");
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
		currentSoapObject.addSoapObject(this.banks.get(0).getSoapObject(-1));
		currentSoapObject.addSoapObject(this.banks.get(0).getSoapObject(-1));
		currentSoapObject.addSoapObject(this.banks.get(0).getSoapObject(-1));
		currentSoapObject.addProperty("efficiencyLossNorth", ""+this.efficiencyLossNorth);
		currentSoapObject.addProperty("efficiencyLossWest", ""+this.efficiencyLossWest);
		currentSoapObject.addProperty("height", ""+this.height);
		currentSoapObject.addProperty("percentageNorth", ""+this.percentageNorth);
		currentSoapObject.addProperty("percentageWest", ""+this.percentageWest);
		currentSoapObject.addProperty("width", ""+this.width);
		return currentSoapObject;
	}

}

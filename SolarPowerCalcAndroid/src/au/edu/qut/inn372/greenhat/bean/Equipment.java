package au.edu.qut.inn372.greenhat.bean;

import java.io.Serializable;
import java.util.ArrayList;

import org.ksoap2.serialization.SoapObject;
/**
 * Equipment Bean
 * 
 * @author Charleston Telles
 *
 */
public class Equipment extends AndroidAbstractBean implements Serializable {

	private static final long serialVersionUID = 5455126267096688150L;
	private double cost;
	private double size;
	private ArrayList<Panel> panels;
	private int selectedNoOfPanels;
	
	private Inverter inverter;
	
	public Equipment() {
		inverter = new Inverter();
	}
	
	/**
	 * Get the inverter
	 * @return the inverter value of the inverter property
	 */
	public Inverter getInverter() {
		return inverter;
	}
	
	/**
	 * Set the inverter 
	 * @param inverter new value for the inverter property
	 */
	public void setInverter(Inverter inverter) {
		this.inverter = inverter;
	}
	
	/**
	 * Get the panels
	 * @return panels value of the panels property
	 */
	public ArrayList<Panel> getPanels() {
		return panels;
	}
	
	/**
	 * Set the panels
	 * @param panels new value for the panels property
	 */
	public void setPanels(ArrayList<Panel> panels) {
		this.panels = panels;
	}
	
	/**
	 * Get the selected panel
	 * @return selected panel value of the selected panel property
	 */
	public int getSelectedNoOfPanels() { 
		return selectedNoOfPanels; 
	} 
	
	/**
	 * Set the selected panel
	 * @param selected panel new value for the selected panel property
	 */
	public void setSelectedNoOfPanels(int selectedNoOfPanels) { 
		this.selectedNoOfPanels = selectedNoOfPanels; 
	}
	
	/**
	 * Get the cost
	 * @return cost value of the cost property
	 */
	public double getCost() {
		return cost;
	}
	
	/**
	 * Set the cost
	 * @param cost value for the cost property
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	/**
	 * Get the size of the system
	 * @return size value of the size property
	 */
	public double getSize() {
		return size;
	}
	
	/**
	 * Set the size of the system
	 * @param size value for the size property
	 */
	public void setSize(double size) {
		this.size = size;
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
			this.soapObject = new SoapObject("", "equipment");
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
		currentSoapObject.addSoapObject(this.inverter.getSoapObject(-1));
		currentSoapObject.addProperty("cost", ""+this.cost);
		currentSoapObject.addProperty("cost", ""+this.size);
		currentSoapObject.addProperty("panels", ""+this.panels); //Need to change this - how to send an array in a soap object?
		currentSoapObject.addProperty("selectedNoOfPanels", ""+this.selectedNoOfPanels);
		return currentSoapObject;
	}

}

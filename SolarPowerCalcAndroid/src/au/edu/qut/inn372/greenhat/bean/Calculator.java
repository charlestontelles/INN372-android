package au.edu.qut.inn372.greenhat.bean;

import java.io.Serializable;
import java.text.DecimalFormat;

import org.ksoap2.serialization.SoapObject;

public class Calculator extends AndroidAbstractBean implements Serializable {
	
	private static final long serialVersionUID = 5951374471213496241L;

	private Equipment equipment;
	
	private Customer customer;
	
	//This property will be removed later....
	private Panel panel;
	
	private double solarPower;
	private Calculation [] calculations;
	
	public Calculator() {
		equipment = new Equipment();
		customer = new Customer();
		panel = new Panel();
	}
	
	/*
	 * 
	 * THIS CONSTRUCTOR NEEDS TO CHANGE
	 * 
	 * 
	 */
	
	public Calculator(SoapObject soapObject, int soapOperation){
		
		equipment = new Equipment();
		if (soapObject != null)
			switch (soapOperation) {
			case AndroidAbstractBean.OPERATION_CALC_ENERGY_PRODUCTION:
				this.solarPower = new Double(((SoapObject)soapObject.getProperty(0)).getProperty("solarPower").toString());
				//System.out.println(soapObject.getProperty(0)); //Use this code to view the soap object that you receive back from the server
				break;
			case AndroidAbstractBean.OPERATION_SAVE_CALCULATION:
				break;
			default:
				break;
			}		
		
	}
	
	
	/**
	 * Get the customer
	 * @return customer value of the customer property
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * Set the customer
	 * @param customer value for the customer property
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	/**
	 * Get the daily solar power
	 * @return solarPower value of the solarPower property
	 */
	public double getSolarPower() {
		return solarPower;
	}

	/**
	 * Set the daily solar power
	 * @param solarPower value for the solarPower property
	 */
	public void setSolarPower(double solarPower) {
		this.solarPower = solarPower;
	}

	/**
	 * Set the equipment
	 * @param equipment value for the equipment property
	 */
	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
	
	/**
	 * Get the equipment
	 * @return equipment value of the equipment property
	 */
	public Equipment getEquipment() {
		return equipment;
	}

	/**
	 * Get the panel
	 * @return the panel
	 */
	public Panel getPanel() {
		return panel;
	}

	/**
	 * Set the panel
	 * @param panel the panel to set
	 */
	public void setPanel(Panel panel) {
		this.panel = panel;
	}

	/**
	 * Get the calculations for a specified range of years
	 * @return the calculations
	 */
	public Calculation[] getCalculations() {
		return calculations;
	}
	

	/**
	 * ATTENTION: ALL VALUES MUST BE CONVERTED TO STRING
	 */
	@Override
	protected void setSoapObject(int soapOperation) {
		SoapObject calculatorElement;
		switch (soapOperation) {
		case AndroidAbstractBean.OPERATION_CALC_ENERGY_PRODUCTION:
			this.soapObject = new SoapObject(AndroidAbstractBean.NAMESPACE,AndroidAbstractBean.OPERATION_CALC_ENERGY_PRODUCTION_NAME);
			calculatorElement = new SoapObject("", "calculator");
			calculatorElement = setDefaultSoapObject(calculatorElement);
			this.soapObject.addSoapObject(calculatorElement);
			break;
		case AndroidAbstractBean.OPERATION_SAVE_CALCULATION:
			this.soapObject = new SoapObject(AndroidAbstractBean.NAMESPACE,AndroidAbstractBean.OPERATION_SAVE_CALCULATION_NAME);
			calculatorElement = new SoapObject("", "calculator");
			calculatorElement = setDefaultSoapObject(calculatorElement);
			this.soapObject.addSoapObject(calculatorElement);
			break;
		default:
			this.soapObject = new SoapObject("", "calculator");
			calculatorElement = new SoapObject("", "calculator");
			calculatorElement = setDefaultSoapObject(calculatorElement);
			this.soapObject.addSoapObject(calculatorElement);
			break;
		}
	}
	
	/**
	 * Adds all object properties to the given soap object and returns it
	 * @param currentSoapObject Soap object that fields are added to
	 * @return Amended soap object with fields
	 */
	private SoapObject setDefaultSoapObject(SoapObject currentSoapObject) {
		currentSoapObject.addProperty("solarPower", ""+this.solarPower);
		currentSoapObject.addSoapObject(equipment.getSoapObject(-1));
		currentSoapObject.addSoapObject(customer.getSoapObject(-1));
		currentSoapObject.addSoapObject(panel.getSoapObject(-1));
		//Add Calculations
		return currentSoapObject;
	}
	
}

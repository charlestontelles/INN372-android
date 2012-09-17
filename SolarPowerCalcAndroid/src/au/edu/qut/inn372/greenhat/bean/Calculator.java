package au.edu.qut.inn372.greenhat.bean;

import java.io.Serializable;
import java.text.DecimalFormat;

import org.ksoap2.serialization.SoapObject;

public class Calculator extends AndroidAbstractBean implements Serializable {
	
	private static final long serialVersionUID = 5951374471213496241L;

	private Equipment equipment;
	
	private Customer customer;
	
	private Calculation [] calculations;
	
	private Calculation calculation;
	
	
	public Calculator() {
		equipment = new Equipment();
		customer = new Customer();
		calculations = new Calculation[25];
	}
	
	
	public Calculator(SoapObject soapObject, int soapOperation){
		//System.out.println(soapObject.toString());
		calculations = new Calculation[25];
		if (soapObject != null)
			switch (soapOperation) {
			case AndroidAbstractBean.OPERATION_CALC_ENERGY_PRODUCTION:
				//this.solarPower = new Double(((SoapObject)soapObject.getProperty(0)).getProperty("solarPower").toString());
				//Iterate through soap objects
				soapObject = (SoapObject)soapObject.getProperty(0);
				int calculationsCount = 0;
				for(int i=0; i<soapObject.getPropertyCount(); i++) {
					SoapObject curSoapObject = (SoapObject)soapObject.getProperty(i);
					if (i<25) {
						//calculations
						this.calculations[calculationsCount] = new Calculation(curSoapObject, soapOperation);
						calculationsCount++;
					}
					else {
						if (i<26) {
							i++;
							//customer
							//this.customer = new Customer(curSoapObject, soapOperation) //Should implement this but can save time by doing this later
						}
						else {
							//equipment
							//this.equipment = new Equipment(curSoapObject, soapOperation) //Should implement this but can save time by doing this later
						}
					}
				}
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
	 * Get the calculation
	 * @return the calculation
	 */
	public Calculation getCalculation() {
		return calculation;
	}

	/**
	 * Set the calculation
	 * @param calculation the calculation to set
	 */
	public void setCalculation(Calculation calculation) {
		this.calculation = calculation;
	}

	/**
	 * Get the calculations for a specified range of years
	 * @return the calculations
	 */
	public Calculation[] getCalculations() {
		return calculations;
	}
	/**
	 * Sets the calculations 
	 * @param calculations
	 */
	public void setCalculations(Calculation[] calculations) {
		this.calculations = calculations;
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
		case AndroidAbstractBean.OPERATION_GET_EQUIPMENTS:
			this.soapObject = new SoapObject(AndroidAbstractBean.NAMESPACE,AndroidAbstractBean.OPERATION_GET_EQUIPMENTS_NAME);
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
		currentSoapObject.addSoapObject(equipment.getSoapObject(-1));
		currentSoapObject.addSoapObject(customer.getSoapObject(-1));
		//for(Calculation curCalculation : this.getCalculations()) {
		//	currentSoapObject.addSoapObject(curCalculation.getSoapObject(-1));
		//}
		return currentSoapObject;
	}
	
}

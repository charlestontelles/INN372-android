package au.edu.qut.inn372.greenhat.bean;

import org.ksoap2.serialization.SoapObject;

public class Calculator extends AndroidAbstractBean{
	private Equipment equipment;
	private Customer customer;
	private double result;
	private double solarPower;
	
	public Calculator(){
		equipment = new Equipment();
		customer = new Customer();
	}
	
	public Calculator(SoapObject soapObject, int soapOperation){
		equipment = new Equipment();
		if (soapObject != null)
			switch (soapOperation) {
			case AndroidAbstractBean.OPERATION_CALC_ENERGY_PRODUCTION:
				this.solarPower = new Double(((SoapObject)soapObject.getProperty(0)).getProperty("solarPower").toString());
				break;
			case AndroidAbstractBean.OPERATION_SAVE_CALCULATION:
				break;
			default:
				break;
			}		
	}
	public Equipment getEquipment() {
		return equipment;
	}
	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
	public double getResult() {
		return result;
	}
	public void setResult(double result) {
		this.result = result;
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public double getSolarPower() {
		return solarPower;
	}

	public void setSolarPower(double solarPower) {
		this.solarPower = solarPower;
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
			calculatorElement = new SoapObject ("", "calculator");
			calculatorElement.addProperty("solarPower",""+this.solarPower);
			calculatorElement.addSoapObject(this.equipment.getSoapObject(-1));
			calculatorElement.addSoapObject(this.customer.getSoapObject(-1));
			this.soapObject.addSoapObject(calculatorElement);
			break;
		case AndroidAbstractBean.OPERATION_SAVE_CALCULATION:
			this.soapObject = new SoapObject(AndroidAbstractBean.NAMESPACE,AndroidAbstractBean.OPERATION_SAVE_CALCULATION_NAME);
			calculatorElement = new SoapObject ("", "calculator");
			calculatorElement = new SoapObject ("", "calculator");
			calculatorElement.addProperty("solarPower",""+this.solarPower);
			calculatorElement.addSoapObject(this.equipment.getSoapObject(-1));
			calculatorElement.addSoapObject(this.customer.getSoapObject(-1));
			this.soapObject.addSoapObject(calculatorElement);
			break;
		default:
			break;
		}
		
	}
	
}

package au.edu.qut.inn372.greenhat.bean;

import org.ksoap2.serialization.SoapObject;

/**
 * Customer Bean
 * 
 * @author Charleston Telles
 *
 */
public class Customer extends AndroidAbstractBean{

	private Location location;
	
	private Tariff tariff;
	
	private ElectricityUsage electricityUsage;
	
	public Customer() {
		location = new Location();
		tariff = new Tariff();
		electricityUsage = new ElectricityUsage();
	}
	
	/**
	 * Get the location
	 * @return location value of the location property
	 */
	public Location getLocation() {
		return location;
	}
	
	/**
	 * Set the location
	 * @param location new value for the location property 
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	
	/**
	 * Get the tariff
	 * @return tariff value of the tariff property
	 */
	public Tariff getTariff() {
		return tariff;
	}
	
	/**
	 * Set the tariff
	 * @param tariff new value for the tariff property
	 */
	public void setTariff(Tariff tariff) {
		this.tariff = tariff;
	}
	
	/**
	 * Get the daily average electricity usage
	 * @return electricityUsage value of the electricity usage property
	 */
	public ElectricityUsage getElectricityUsage() {
		return electricityUsage;
	}
	
	/**
	 * Set the daily average electricity usage
	 * @param electricityUsage new value for the elctricity usage property
	 */
	public void setElectricityUsage(ElectricityUsage electricityUsage) {
		this.electricityUsage = electricityUsage;
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
			this.soapObject = new SoapObject("", "customer");
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
		currentSoapObject.addSoapObject(this.electricityUsage.getSoapObject(-1));
		currentSoapObject.addSoapObject(this.location.getSoapObject(-1));
		currentSoapObject.addSoapObject(this.tariff.getSoapObject(-1));
		return currentSoapObject;
	}
}

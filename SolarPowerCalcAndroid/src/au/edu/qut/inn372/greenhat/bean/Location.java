package au.edu.qut.inn372.greenhat.bean;

import java.io.Serializable;

import org.ksoap2.serialization.SoapObject;

/**
 * Location Bean
 * 
 * @author Charleston Telles
 *
 */
public class Location extends AndroidAbstractBean implements Serializable {

	private static final long serialVersionUID = 4817475946932075809L;
	private double sunLightHours; //average daily hours of sunlight
	private String city;
	
	private Roof roof;

	public Location(SoapObject soapObject, int soapOperation){
		if (soapObject != null)
			switch (soapOperation) {
			case AndroidAbstractBean.OPERATION_GET_CALCULATIONS:
			default:
				this.sunLightHours = new Double(soapObject.getProperty("sunLightHours").toString());
				this.roof = new Roof((SoapObject)soapObject.getProperty("roof"), soapOperation);
				break;
			}
		
	}
	
	public Location() {
		roof = new Roof();
	}

	/**
	 * Get the roof
	 * @return roof value of the roof property
	 */
	public Roof getRoof() {
		return roof;
	}

	/**
	 * Set the roof
	 * @param roof new value for the roof property
	 */
	public void setRoof(Roof roof) {
		this.roof = roof;
	}

	/**
	 * Get the sunlight hours
	 * @return sunLightHours value of the sunlight hours
	 */
	public double getSunLightHours() {
		return sunLightHours;
	}

	/**
	 * Set the sunLightHours
	 * @param sunLightHours new value for the sunlight hours
	 */
	public void setSunLightHours(double sunLightHours) {
		this.sunLightHours = sunLightHours;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
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
			this.soapObject = new SoapObject("", "location");
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
		currentSoapObject.addProperty("sunLightHours", ""+this.sunLightHours);
		currentSoapObject.addSoapObject(this.roof.getSoapObject(-1));		
		return currentSoapObject;
	}

}

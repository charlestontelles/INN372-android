package au.edu.qut.inn372.greenhat.bean;

import org.ksoap2.serialization.SoapObject;

/**
 * ElectricityUsage Bean
 * 
 * @author Charleston Telles
 *
 */
public class ElectricityUsage extends AndroidAbstractBean{

	private double dailyAverageUsage; //current electricity usage
	private double dayTimeHourlyUsage;
	
	/**
	 * Get the daily electricity usage
	 * @return the dailyAverageUsage value of the daily average electricity usage
	 */
	public double getDailyAverageUsage() {
		return dailyAverageUsage;
	}
	
	/**
	 * Set the daily electricity usage
	 * @param dailyAverageUsage new value of the daily electricity usage
	 */
	public void setDailyAverageUsage(double dailyAverageUsage) {
		this.dailyAverageUsage = dailyAverageUsage;
	}
	
	/**
	 * Get the daytime hourly electricity usage
	 * @return dayTimeHourlyUsage value of the daytime hourly usage
	 */
	public double getDayTimeHourlyUsage() {
		return dayTimeHourlyUsage;
	}
	
	/**
	 * Set the daytime hourly usage
	 * @param dayTimeHourlyUsage new daytime hourly electricity usage
	 */
	public void setDayTimeHourlyUsage(double dayTimeHourlyUsage) {
		this.dayTimeHourlyUsage = dayTimeHourlyUsage;
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
			this.soapObject = new SoapObject("", "electricityUsage");
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
		currentSoapObject.addProperty("dailyAverageUsage",""+this.dailyAverageUsage);
		currentSoapObject.addProperty("dayTimeHourlyUsage",""+this.dayTimeHourlyUsage);		
		return currentSoapObject;
	}
}

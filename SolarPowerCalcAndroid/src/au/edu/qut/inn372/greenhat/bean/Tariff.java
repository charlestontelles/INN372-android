package au.edu.qut.inn372.greenhat.bean;

import java.io.Serializable;

import org.ksoap2.serialization.SoapObject;

public class Tariff extends AndroidAbstractBean implements Serializable {
	
	private static final long serialVersionUID = 3164471752108986206L;

	private double tariff11Fee;
	private double annualTariffIncrease = 5; //TODO Hardcoded Value
	private double feedInfee = 0.5; //TODO Hardcoded Value
	private double tariffFeePerYear;

	/**
	 * Get tariff 11 fee
	 * @return the tariff11Fee
	 */
	public double getTariff11Fee() {
		return tariff11Fee;
	}
	/**
	 * Set the tariff fee
	 * @param tariff11Fee the tariff11Fee to set to
	 */
	public void setTariff11Fee(double tariff11Fee) {
		this.tariff11Fee = tariff11Fee;
	}
	/**
	 * Get the annual tariff increase
	 * @return the annualTariffIncrease
	 */
	public double getAnnualTariffIncrease() {
		return annualTariffIncrease;
	}
	/**
	 * Set the annual tariff increase
	 * @param annualTariffIncrease the annualTariffIncrease to set to
	 */
	public void setAnnualTariffIncrease(double annualTariffIncrease) {
		this.annualTariffIncrease = annualTariffIncrease;
	}
	/**
	 * Get the feed in fee
	 * @return the feedInfee
	 */
	public double getFeedInfee() {
		return feedInfee;
	}
	/**
	 * Set the feed in fee
	 * @param feedInfee the feedInfee to set
	 */
	public void setFeedInfee(double feedInfee) {
		this.feedInfee = feedInfee;
	}

	/**
	 * Get the tariff per year
	 * @return tariffPerYear 
	 */
	public double getTariffPerYear(int year){
		return tariffFeePerYear;
	}
	
	/**
	 * Set the tariff fee per year
	 * @param tariffFeePerYear the tariffFeePerYear to set
	 */
	public void setTariffFeePerYear(double year) {
		this.tariffFeePerYear = tariff11Fee * ( Math.pow( (1+annualTariffIncrease/100), (year-1) ) );
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
			this.soapObject = new SoapObject("", "tariff");
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
		currentSoapObject.addProperty("tariff11Fee", ""+this.tariff11Fee);
		currentSoapObject.addProperty("annualTariffIncrease", ""+this.annualTariffIncrease);
		currentSoapObject.addProperty("feedInFee", ""+this.feedInfee);
		currentSoapObject.addProperty("tariffFeePerYear", ""+this.tariffFeePerYear);
		return currentSoapObject;
	}

}

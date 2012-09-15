package au.edu.qut.inn372.greenhat.bean;

import java.io.Serializable;

import org.ksoap2.serialization.SoapObject;

public class Calculation extends AndroidAbstractBean implements Serializable {
	
	private static final long serialVersionUID = -6841951699058445777L;
	/**
	 * 
	 */
	Integer year;
	Double tariff11Fee;
	Double dailySolarPower;
	Double replacementGeneration;
	Double exportedGeneration;
	Double dailySaving;
	Double annualSaving;
	Double cumulativeSaving;
	
	public Calculation() {
		
	}
		
	/**
	 * A calculation object contains info about year, tariff, solar power generation and savings.
	 * @param year
	 * @param tariff11Fee
	 * @param dailySolarPower
	 * @param replacementGeneration
	 * @param exportedGeneration
	 * @param dailySaving
	 * @param annualSaving
	 * @param cumulativeSaving
	 */
	public Calculation(Integer year, Double tariff11Fee, Double dailySolarPower, 
			Double replacementGeneration, Double exportedGeneration, Double dailySaving, 
				Double annualSaving, Double cumulativeSaving) {
		this.year = year;
		this.tariff11Fee = tariff11Fee;
		this.dailySolarPower = dailySolarPower;
		this.replacementGeneration = replacementGeneration;
		this.exportedGeneration = exportedGeneration;
		this.dailySaving = dailySaving;
		this.annualSaving = annualSaving;
		this.cumulativeSaving = cumulativeSaving;
	}

	/**
	 * Get the year for a calculation
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}

	/**
	 * Set the year for a calculation
	 * @param year the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
	}

	/**
	 * Get the tariff for a year
	 * @return the tariff11Fee
	 */
	public Double getTariff11Fee() {
		return tariff11Fee;
	}

	/**
	 * Set the tariff for a year
	 * @param tariff11Fee the tariff11Fee to set
	 */
	public void setTariff11Fee(Double tariff11Fee) {
		this.tariff11Fee = tariff11Fee;
	}

	/**
	 * Get the daily solar power generation for a year
	 * @return the dailySolarPower
	 */
	public Double getDailySolarPower() {
		return dailySolarPower;
	}

	/**
	 * Set the daily solar power generation for a year
	 * @param dailySolarPower the dailySolarPower to set
	 */
	public void setDailySolarPower(Double dailySolarPower) {
		this.dailySolarPower = dailySolarPower;
	}

	/**
	 * Get the solar power replacement generation
	 * @return the replacementGeneration
	 */
	public Double getReplacementGeneration() {
		return replacementGeneration;
	}

	/**Set the replacement solar power generation
	 * @param replacementGeneration the replacementGeneration to set
	 */
	public void setReplacementGeneration(Double replacementGeneration) {
		this.replacementGeneration = replacementGeneration;
	}

	/**
	 * Get the exported solar power generation
	 * @return the exportedGeneration
	 */
	public Double getExportedGeneration() {
		return exportedGeneration;
	}

	/**
	 * Set the exported solar power generation
	 * @param exportedGeneration the exportedGeneration to set
	 */
	public void setExportedGeneration(Double exportedGeneration) {
		this.exportedGeneration = exportedGeneration;
	}

	/**
	 * Get the daily savings for a year
	 * @return the dailySaving
	 */
	public Double getDailySaving() {
		return dailySaving;
	}

	/**
	 * Set the daily savings for a year
	 * @param dailySaving the dailySaving to set
	 */
	public void setDailySaving(Double dailySaving) {
		this.dailySaving = dailySaving;
	}

	/**
	 * Get the annual savings for a year
	 * @return the annualSaving
	 */
	public Double getAnnualSaving() {
		return annualSaving;
	}

	/**
	 * Set the annual savings for a year
	 * @param annualSaving the annualSaving to set
	 */
	public void setAnnualSaving(Double annualSaving) {
		this.annualSaving = annualSaving;
	}

	/**
	 * Get the cumulative savings for a year
	 * @return the cumulativeSaving
	 */
	public Double getCumulativeSaving() {
		return cumulativeSaving;
	}

	/**
	 * Set the cumulative savings for a year
	 * @param cumulativeSaving the cumulativeSaving to set
	 */
	public void setCumulativeSaving(Double cumulativeSaving) {
		this.cumulativeSaving = cumulativeSaving;
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
			this.soapObject = new SoapObject("", "calculation");
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
		currentSoapObject.addProperty("year", ""+this.year);
		currentSoapObject.addProperty("tariff11Fee", ""+this.tariff11Fee);
		currentSoapObject.addProperty("dailySolarPower", ""+this.dailySolarPower);
		currentSoapObject.addProperty("replacementGeneration", ""+this.replacementGeneration);
		currentSoapObject.addProperty("exportedGeneration", ""+this.exportedGeneration);
		currentSoapObject.addProperty("dailySaving", ""+this.dailySaving);
		currentSoapObject.addProperty("annualSaving", ""+this.annualSaving);
		currentSoapObject.addProperty("cumulativeSaving", ""+this.cumulativeSaving);
		return currentSoapObject;
	}
}

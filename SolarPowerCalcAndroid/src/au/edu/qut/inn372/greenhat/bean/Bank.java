package au.edu.qut.inn372.greenhat.bean;

import java.io.Serializable;

import org.ksoap2.serialization.SoapObject;


public class Bank extends AndroidAbstractBean implements Serializable {
	
	private static final long serialVersionUID = 295341398887435456L;
	private int bankId;
	private double angle;
	private String selectedOrientation;
	private int panelNo;
	
	//Added by Martin
	private Double efficiency;
	private Integer numberOfPanels;

	private Equipment equipment;
	
	public Bank() {
		equipment = new Equipment();
	}
	
	/**
	 * Get the selected panel
	 * @return selected panel value of the selected panel property
	 */
	public Equipment getEquipment(){
		return equipment;
	}
	
	/**
	 * Set the selected panel
	 * @param selected panel new value for the selected panel property
	 */
	public void setEquipment(Equipment equipment){
		this.equipment = equipment;
	}
	
	
	public int getPanelNo(){
		return panelNo;
	}
	
	public void setPanelNo(int panelNo){
		this.panelNo = panelNo;
	}
	
	/**
	 * Get the bankId
	 * @return bankId value of the bankId property
	 */
	public int getBankId(){
		return bankId;
	}
	
	/**
	 * Set the bankId
	 * @param bankId new value for the bankId property
	 */
	public void setBankId(int bankId){
		this.bankId = bankId;
	}
	
	/**
	 * Get the angle
	 * @return angle value of the angle property
	 */
	public double getAngle(){
		return angle;
	}
	
	/**
	 * Set the angle
	 * @param angle new value for the angle property
	 */
	public void setAngle(double angle){
		this.angle = angle;
	}
	
	/**
	 * Get the selected orientation
	 * @return selected orientation value of the selected orientation property
	 */
	public String getSelectedOrientation() { 
		return selectedOrientation; 
	} 
	
	/**
	 * Set the selected orientation
	 * @param selected orientation new value for the selected orientation property
	 */
	public void setSelectedOrientation(String selectedOrientation) { 
		this.selectedOrientation = selectedOrientation; 
	}

	/**
	 * @return the efficiency
	 */
	public Double getEfficiency() {
		return efficiency;
	}

	/**
	 * @param efficiency the efficiency to set
	 */
	public void setEfficiency(Double efficiency) {
		this.efficiency = efficiency;
	}

	/**
	 * @return the numberOfPanels
	 */
	public Integer getNumberOfPanels() {
		return numberOfPanels;
	}

	/**
	 * @param numberOfPanels the numberOfPanels to set
	 */
	public void setNumberOfPanels(Integer numberOfPanels) {
		this.numberOfPanels = numberOfPanels;
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
			this.soapObject = new SoapObject("", "bank");
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
		currentSoapObject.addSoapObject(this.equipment.getSoapObject(-1));
		currentSoapObject.addProperty("panelNo", ""+this.panelNo);
		currentSoapObject.addProperty("bankId", ""+this.bankId);
		currentSoapObject.addProperty("angle", ""+this.angle);
		currentSoapObject.addProperty("selectedOrientation", ""+this.selectedOrientation);
		currentSoapObject.addProperty("efficiency", this.efficiency);
		currentSoapObject.addProperty("numberOfPanels", ""+this.numberOfPanels);
		return currentSoapObject;
	}
}

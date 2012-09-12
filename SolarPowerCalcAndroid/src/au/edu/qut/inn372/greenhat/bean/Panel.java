package au.edu.qut.inn372.greenhat.bean;

import org.ksoap2.serialization.SoapObject;



/**
 * 
 * Panel Bean
 * 
 * @author Fabian Kohl
 *
 */

public class Panel extends AndroidAbstractBean{
	
	//Attributes
	private double width;
	private double height;
	private String position;
	private double efficiency_loss;
	private double power;
	
	//Constructor
	/**
	 * added this empty constructor because the jUnit-test needed it, FK
	 */
	public Panel(){
		super();
	}
	
	
	public Panel(SoapObject soapObject, int soapOperation){
		if (soapObject != null)
			switch (soapOperation) {
			case AndroidAbstractBean.OPERATION_CALC_EQUIPMENT:
				this.width = new Double(((SoapObject)soapObject.getProperty(0)).getProperty("width").toString());
				this.height = new Double(((SoapObject)soapObject.getProperty(0)).getProperty("height").toString());
				this.position = new String(((SoapObject)soapObject.getProperty(0)).getProperty("position").toString());
				this.efficiency_loss = new Double(((SoapObject)soapObject.getProperty(0)).getProperty("efficiency_loss").toString());
				this.power = new Double(((SoapObject)soapObject.getProperty(0)).getProperty("power").toString());
				break;
			default:
				break;
			}		
	}
	
	//Getter and Setter
	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public double getEfficiency_loss() {
		return efficiency_loss;
	}

	public void setEfficiency_loss(double efficiency_loss) {
		this.efficiency_loss = efficiency_loss;
	}
	
	public double getPower() {
		return power;
	}


	public void setPower(double power) {
		this.power = power;
	}


	/**TODO Complete implementation of getPanelEfficiency, at the moment it is just a getter-method
	 * 		for efficiency loss
	 * @return efficiency_loss
	 */
	public double getPanelEfficiency(){
		return this.efficiency_loss;
	}

	/**
	 * Marshalling Method
	 * 
	 * ATTENTION: ALL VALUES MUST BE CONVERTED TO STRING
	 */
	@Override
	protected void setSoapObject(int soapOperation) {
		// TODO Auto-generated method stub
		switch (soapOperation) {
		// "AndroidAbstractBean.OPERATION_CALC_ENERGY_PRODUCTION" is an int-value for the switch-case statement 
		case AndroidAbstractBean.OPERATION_CALC_ENERGY_PRODUCTION:
			this.soapObject = new SoapObject(AndroidAbstractBean.NAMESPACE, AndroidAbstractBean.OPERATION_CALC_ENERGY_PRODUCTION_NAME);
			SoapObject child = new SoapObject ("", "panel");
			child.addProperty("width", ""+this.width);
			child.addProperty("height", ""+this.height);
			child.addProperty("position", ""+this.position);
			child.addProperty("efficiency_loss", ""+this.efficiency_loss);
			child.addProperty("power", ""+this.power);
			this.soapObject.addSoapObject(child);
			break;
		default:
			this.soapObject = new SoapObject("", "panel");
			this.soapObject.addProperty("width", ""+this.width);
			this.soapObject.addProperty("height", ""+this.height);
			this.soapObject.addProperty("position", ""+this.position);
			this.soapObject.addProperty("efficiency_loss", ""+this.efficiency_loss);
			this.soapObject.addProperty("power", ""+this.power);
		}
	}
}

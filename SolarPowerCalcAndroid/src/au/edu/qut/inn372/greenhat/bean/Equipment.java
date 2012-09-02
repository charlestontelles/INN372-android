package au.edu.qut.inn372.greenhat.bean;

import org.ksoap2.serialization.SoapObject;
/**
 * Equipment Bean
 * 
 * @author Charleston Telles
 *
 */
public class Equipment extends AndroidAbstractBean{
	private double cost;
	private double size;
	
	private Inverter inverter;
		
	public Inverter getInverter() {
		return inverter;
	}

	public void setInverter(Inverter inverter) {
		this.inverter = inverter;
	}

	public Equipment(){
		inverter = new Inverter();
	}
	
	public Equipment(SoapObject soapObject, int soapOperation){
		if (soapObject != null)
			switch (soapOperation) {
			case AndroidAbstractBean.OPERATION_CALC_EQUIPMENT:
				this.size = new Double(((SoapObject)soapObject.getProperty(0)).getProperty("size").toString());
				this.cost = new Double(((SoapObject)soapObject.getProperty(0)).getProperty("cost").toString());
				break;
			default:
				break;
			}		
	}
	
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}

	/**
	 * ATTENTION: ALL VALUES MUST BE CONVERTED TO STRING
	 */
	@Override
	protected void setSoapObject(int soapOperation) {
		switch (soapOperation) {
		case AndroidAbstractBean.OPERATION_CALC_EQUIPMENT:
			this.soapObject = new SoapObject(AndroidAbstractBean.NAMESPACE,AndroidAbstractBean.OPERATION_CALC_EQUIPMENT_NAME);
			SoapObject child = new SoapObject ("", "equipment");
			child.addProperty("cost", ""+this.cost);
			child.addProperty("size", ""+this.size);
			this.soapObject.addSoapObject(child);
			break;
		default:
			this.soapObject = new SoapObject("", "equipment");
			this.soapObject.addProperty("cost", ""+this.cost);
			this.soapObject.addProperty("size", ""+this.size);
			this.soapObject.addSoapObject(this.inverter.getSoapObject(-1));
			break;
		}
		
	}
}

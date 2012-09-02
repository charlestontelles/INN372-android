package au.edu.qut.inn372.greenhat.bean;

import org.ksoap2.serialization.SoapObject;

/**
 * Customer Bean
 * 
 * @author Charleston Telles
 *
 */
public class Customer extends AndroidAbstractBean{
	private ElectricityUsage electricityUsage;
	private Location location;
	public Customer(){
		electricityUsage = new ElectricityUsage();
		location = new Location();
	}
	public ElectricityUsage getElectricityUsage() {
		return electricityUsage;
	}
	public void setElectricityUsage(ElectricityUsage electricityUsage) {
		this.electricityUsage = electricityUsage;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	@Override
	protected void setSoapObject(int soapOperation) {
		this.soapObject = new SoapObject("", "customer");
		this.soapObject.addSoapObject(this.electricityUsage.getSoapObject(-1));
		this.soapObject.addSoapObject(this.location.getSoapObject(-1));
	}
}

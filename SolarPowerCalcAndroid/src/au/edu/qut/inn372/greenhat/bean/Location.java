package au.edu.qut.inn372.greenhat.bean;

import org.ksoap2.serialization.SoapObject;

/**
 * Location Bean
 * 
 * @author Charleston Telles
 *
 */
public class Location extends AndroidAbstractBean{
	private double sunLightHours;
	private Roof roof;
	public Location(){
		roof = new Roof();
	}
	public double getSunLightHours() {
		return sunLightHours;
	}
	public void setSunLightHours(double sunLightHours) {
		this.sunLightHours = sunLightHours;
	}
	public Roof getRoof() {
		return roof;
	}
	public void setRoof(Roof roof) {
		this.roof = roof;
	}
	@Override
	protected void setSoapObject(int soapOperation) {
		this.soapObject = new SoapObject("", "location");
		this.soapObject.addProperty("sunLightHours", ""+this.sunLightHours);
		this.soapObject.addSoapObject(this.roof.getSoapObject(-1));		
	}

}

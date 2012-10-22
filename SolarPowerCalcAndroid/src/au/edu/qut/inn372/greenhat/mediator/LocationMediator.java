package au.edu.qut.inn372.greenhat.mediator;

import org.ksoap2.serialization.SoapObject;

import au.edu.qut.inn372.greenhat.bean.AndroidAbstractBean;
import au.edu.qut.inn372.greenhat.bean.Panel;
import au.edu.qut.inn372.greenhat.ws.SoapClient;

public class LocationMediator {
	private SoapClient soapClient = new SoapClient();
	
	private double latitude;
	private double longitude;
	private double averageSunlightHours;
	
	public double getAverageSunligthHours(){
		return this.averageSunlightHours;
	}
	
	public void setAverageSunlight(double averageSunlightHours){
		this.averageSunlightHours = averageSunlightHours;
	}
	
	public void retrieveAverageSunlight(){
		SoapObject soapObject = new SoapObject(AndroidAbstractBean.NAMESPACE,AndroidAbstractBean.OPERATION_GET_SUN_LIGHT_HOURS_NAME);
		soapObject.addProperty("latitude", ""+this.latitude);
		soapObject.addProperty("longitude", ""+this.longitude);
		SoapObject soapResponse = soapClient.synchronousRequest(soapObject);
		setAverageSunlight( new Double (soapResponse.getProperty("sunLightHours").toString()));
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}

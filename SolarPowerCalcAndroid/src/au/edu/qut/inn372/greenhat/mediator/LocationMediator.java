package au.edu.qut.inn372.greenhat.mediator;

import org.ksoap2.serialization.SoapObject;

import au.edu.qut.inn372.greenhat.bean.AndroidAbstractBean;
import au.edu.qut.inn372.greenhat.bean.Panel;
import au.edu.qut.inn372.greenhat.ws.SoapClient;

public class LocationMediator {
	private SoapClient soapClient = new SoapClient();
	
	
	private double averageSunlightHours;
	
	public double getAverageSunlightHours(){
		return this.averageSunlightHours;
	}
	
	public void setAverageSunligth(double averageSunlightHours){
		this.averageSunlightHours = averageSunlightHours;
	}
	
	public void getAverageSunlight(){
		SoapObject locationSoap = soapClient.synchronousRequest((new Panel()).getSoapObject(AndroidAbstractBean.OPERATION_GET_SUN_LIGHT_HOURS));
		locationSoap.getAttribute(0);
	}

}

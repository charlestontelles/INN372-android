package au.edu.qut.inn372.greenhat.bean;

import org.ksoap2.serialization.SoapObject;

/**
 * ElectricityUsage Bean
 * 
 * @author Charleston Telles
 *
 */
public class ElectricityUsage extends AndroidAbstractBean{
	private double dailyAverageUsage;
    private double dayTimeHourlyUsage;
	public double getDailyAverageUsage() {
		return dailyAverageUsage;
	}
	public void setDailyAverageUsage(double dailyAverageUsage) {
		this.dailyAverageUsage = dailyAverageUsage;
	}
	public double getDayTimeHourlyUsage() {
		return dayTimeHourlyUsage;
	}
	public void setDayTimeHourlyUsage(double dayTimeHourlyUsage) {
		this.dayTimeHourlyUsage = dayTimeHourlyUsage;
	}
	@Override
	protected void setSoapObject(int soapOperation) {
		this.soapObject = new SoapObject("", "electricityUsage");
		this.soapObject.addProperty("dailyAverageUsage",""+this.dailyAverageUsage);
		this.soapObject.addProperty("dayTimeHourlyUsage",""+this.dayTimeHourlyUsage);		
	}
}

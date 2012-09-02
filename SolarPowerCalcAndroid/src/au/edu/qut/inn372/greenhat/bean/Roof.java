package au.edu.qut.inn372.greenhat.bean;

import org.ksoap2.serialization.SoapObject;

/**
 * Roof Bean
 * 
 * @author Charleston Telles
 *
 */
public class Roof extends AndroidAbstractBean{
	private double efficiencyLossNorth;
	private double efficiencyLossWest;
	private double height;
	private double percentageNorth;
	private double percentageWest;
    public double getEfficiencyLossNorth() {
		return efficiencyLossNorth;
	}
	public void setEfficiencyLossNorth(double efficiencyLossNorth) {
		this.efficiencyLossNorth = efficiencyLossNorth;
	}
	public double getEfficiencyLossWest() {
		return efficiencyLossWest;
	}
	public void setEfficiencyLossWest(double efficiencyLossWest) {
		this.efficiencyLossWest = efficiencyLossWest;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getPercentageNorth() {
		return percentageNorth;
	}
	public void setPercentageNorth(double percentageNorth) {
		this.percentageNorth = percentageNorth;
	}
	public double getPercentageWest() {
		return percentageWest;
	}
	public void setPercentageWest(double percentageWest) {
		this.percentageWest = percentageWest;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	private double width;
	@Override
	protected void setSoapObject(int soapOperation) {
		this.soapObject = new SoapObject("", "roof");
		this.soapObject.addProperty("efficiencyLossNorth", ""+this.efficiencyLossNorth);
		this.soapObject.addProperty("efficiencyLossWest", ""+this.efficiencyLossWest);
		this.soapObject.addProperty("height", ""+this.height);
		this.soapObject.addProperty("percentageNorth", ""+this.percentageNorth);
		this.soapObject.addProperty("percentageWest", ""+this.percentageWest);
		this.soapObject.addProperty("width", ""+this.width);
	}
}

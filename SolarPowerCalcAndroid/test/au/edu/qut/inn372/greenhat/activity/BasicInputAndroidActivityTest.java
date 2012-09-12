package au.edu.qut.inn372.greenhat.activity;

import android.test.ActivityInstrumentationTestCase2;
import au.edu.qut.inn372.greenhat.bean.Calculator;
import au.edu.qut.inn372.greenhat.controller.CalculatorRemoteController;
import android.content.Intent;

public class BasicInputAndroidActivityTest extends
		ActivityInstrumentationTestCase2<BasicInputActivity> {
	
	BasicInputActivity activity;
	Calculator calc;
	CalculatorRemoteController controller = new CalculatorRemoteController();
	public final static String EXTRA_MESSAGE = "au.edu.qut.inn372.inn372.greenhat.activity.BasicInputActivity";
	public final static String EXTRA_MESSAGE2 = "au.edu.qut.inn372.inn372.greenhat.activity.BasicInputActivity2";
	
	public BasicInputAndroidActivityTest(){
		super("au.edu.qut.inn372.greenhat.activity", BasicInputActivity.class);
	}
	
	protected void setUp() throws Exception{
		super.setUp();
		 activity = getActivity();
		calc = new Calculator();
		calc.getEquipment().setSize(20);
		
		calc.getEquipment().getInverter().setEfficiency(10);
		// Roof
		calc.getCustomer().getLocation().getRoof().setEfficiencyLossNorth(10);
		calc.getCustomer().getLocation().getRoof().setEfficiencyLossWest(10);
		calc.getCustomer().getLocation().getRoof().setPercentageNorth(10);
		calc.getCustomer().getLocation().getRoof().setPercentageWest(10);
		// Location (day light hours)
		calc.getCustomer().getLocation().setSunLightHours(10);
		// Current usage
		calc.getCustomer().getElectricityUsage().setDailyAverageUsage(10);
	}
	
	public void testStartUp(){
		assertTrue(BasicInputActivity.class.getName().length() > 0);
	}
	
public void testCalculateDailyAv(){
		
		assertEquals(calc.getCustomer().getElectricityUsage().getDailyAverageUsage() == 10, true);
	}
	
	public void testCalculateGetSolarPower(){
		
		calc = controller.calcEnergyProduction(calc);
		//System.out.println(calc.getSolarPower());
		assertEquals(calc.getSolarPower() == 3.6000000000000005, true);
	}
	
	//only working when doing an Android Junit test
	public void testIntentExtraMessage(){
		Intent intent = new Intent();
		intent.putExtra(EXTRA_MESSAGE, ""+calc.getCustomer().getElectricityUsage().getDailyAverageUsage());
		assertTrue(BasicInputActivity.EXTRA_MESSAGE.length() >0);
	} 
	
	
	public void testIntentExtraMessage2(){
		Intent intent = new Intent();
		intent.putExtra(EXTRA_MESSAGE2, ""+calc.getCustomer().getElectricityUsage().getDailyAverageUsage());
		assertTrue(BasicInputActivity.EXTRA_MESSAGE2.length() >0);
		
	}
	
}

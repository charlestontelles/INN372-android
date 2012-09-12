package au.edu.qut.inn372.greenhat.activity;

import au.edu.qut.inn372.greenhat.bean.Calculator;
import au.edu.qut.inn372.greenhat.controller.CalculatorRemoteController;
import junit.framework.TestCase;

public class BasicInputActivityTest extends TestCase {
	Calculator calc = new Calculator();
	CalculatorRemoteController controller = new CalculatorRemoteController();
	public final static String EXTRA_MESSAGE = "au.edu.qut.inn372.inn372.greenhat.activity.BasicInputActivity";
	public final static String EXTRA_MESSAGE2 = "au.edu.qut.inn372.inn372.greenhat.activity.BasicInputActivity2";
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
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

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}

	public void testOnCreate(){
		assertTrue(BasicInputActivity.EXTRA_MESSAGE.length() >0);
	}
	
	
}

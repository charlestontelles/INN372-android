package au.edu.qut.inn372.greenhat.activity;

import java.text.DecimalFormat;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import au.edu.qut.inn372.greenhat.bean.Calculator;

public class PowerGenerationActivityTest extends
		ActivityInstrumentationTestCase2<PowerGeneration> {
	
	private static final Double DAILY_USAGE = new Double(10);
	private static final Double SOLAR_POWER = new Double(20);
	private static final int TIMEOUT = 10000;
	PowerGeneration activity;
	TabbedOutputActivity parentActivity;
	Calculator calculator;
	DecimalFormat df = new DecimalFormat("#.##");
	
	public PowerGenerationActivityTest(){
		super(PowerGeneration.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		
		calculator = new Calculator();
		calculator.setSolarPower(SOLAR_POWER);
		calculator.getCustomer().getElectricityUsage().setDailyAverageUsage(DAILY_USAGE);
		//Set up calculator parameters here for testing in later methods
		
		//Need to use an activityMonitor to get the activity since we are starting it in a thread
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(PowerGeneration.class.getName(), null, false);
		
		//Need to set up the parent as well as the activity depends upon it (otherwise would get null pointer exceptions)
		Intent tabbedOutputIntent = new Intent();
		tabbedOutputIntent.putExtra("Calculator", calculator);
		parentActivity = launchActivityWithIntent("au.edu.qut.inn372.greenhat.activity", TabbedOutputActivity.class, tabbedOutputIntent);
		
		//Change to tab being tested
		parentActivity.runOnUiThread(new Runnable() {
			public void run() {
				parentActivity.switchTab(TabbedOutputActivity.POWER_GEN_ID);
			}
		});
		
		activity = (PowerGeneration) activityMonitor.waitForActivityWithTimeout(TIMEOUT);
	}
	
	/**
	 * Explicitly destroy (finish) the parent tabbed activity to prevent exceptions with multiple tests
	 */
	protected void tearDown() throws Exception {
		parentActivity.finish();
		super.tearDown();
	}
	
	/**
	 * Test that the activity starts up successfully
	 */
	public void testStartUp(){
		assertTrue(PowerGeneration.class.getName().length() > 0);
		assertNotNull(activity);
	}
	
	/**
	 * Tests that the correct data is displayed
	 */
	public void testDisplayData() {
		//Test the 6 fields
		assertEquals("" + df.format(SOLAR_POWER), ((TextView)activity.findViewById(R.id.TextViewDailyField)).getText().toString());
		assertEquals("" + df.format(SOLAR_POWER*365), ((TextView)activity.findViewById(R.id.TextViewAnnualField)).getText().toString());
		assertEquals("" + df.format(SOLAR_POWER*365/4), ((TextView)activity.findViewById(R.id.TextViewQuaterlyField)).getText().toString());
		assertEquals("" + df.format((SOLAR_POWER-DAILY_USAGE)), ((TextView)activity.findViewById(R.id.TextViewNetField)).getText().toString());
		assertEquals("" + df.format((SOLAR_POWER-DAILY_USAGE)*365), ((TextView)activity.findViewById(R.id.TextViewAnnualNetField)).getText().toString());
		assertEquals("" + df.format((SOLAR_POWER-DAILY_USAGE)*365/4), ((TextView)activity.findViewById(R.id.TextViewQuaterlyNetField)).getText().toString());
	}
	
}

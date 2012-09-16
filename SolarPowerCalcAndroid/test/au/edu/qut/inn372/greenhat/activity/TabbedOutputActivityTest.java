package au.edu.qut.inn372.greenhat.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import au.edu.qut.inn372.greenhat.bean.Calculator;

public class TabbedOutputActivityTest extends
		ActivityInstrumentationTestCase2<TabbedOutputActivity> {
	
	private static final int TIMEOUT = 10000;
	private TabbedOutputActivity activity;
	private ActivityMonitor startupTabMonitor;
	
	public TabbedOutputActivityTest(){
		super(TabbedOutputActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		startupTabMonitor = getInstrumentation().addMonitor(OutputSummaryActivity.class.getName(), null, false);
		
		//An intent object needs to be created to pass calculator to the activity
		Intent activityIntent = new Intent();
		activityIntent.putExtra("Calculator", new Calculator());
		setActivityIntent(activityIntent);
		activity = getActivity();
	}
	
	/**
	 * Test that the activity and the first tab starts up correctly
	 */
	public void testStartUp(){
		assertTrue(TabbedActivity.class.getName().length() > 0);
		Activity startupTab = startupTabMonitor.waitForActivityWithTimeout(TIMEOUT);
		assertNotNull(activity);
		assertNotNull(startupTab);
	}
	
	/**
	 * Tests that the activity can switch to the power generation tab
	 */
	public void testTabPowerGeneration() {
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(PowerGeneration.class.getName(), null, false);
		activity.runOnUiThread(new Runnable() {
			public void run() {
				activity.switchTab(TabbedOutputActivity.POWER_GEN_ID);
			}
		});
		Activity nextActivity = activityMonitor.waitForActivityWithTimeout(TIMEOUT);
		assertNotNull(nextActivity);
	}
	
	/**
	 * Tests that the activity can switch to the financial tab
	 */
	public void testTabFinancial() {
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(FinancialOutputActivity.class.getName(), null, false);
		activity.runOnUiThread(new Runnable() {
			public void run() {
				activity.switchTab(TabbedOutputActivity.FINANCIAL_ID);
			}
		});
		Activity nextActivity = activityMonitor.waitForActivityWithTimeout(TIMEOUT);
		assertNotNull(nextActivity);
	}
	
	/**
	 * Tests that the activity can switch to the output summary tab
	 */
	public void testTabCustomerUsage() {
		//Need to switch to a different tab first so we can test 'switching back' to the original tab which is personal usage
		ActivityMonitor switchActivityMonitor = getInstrumentation().addMonitor(PowerGeneration.class.getName(), null, false);
		activity.runOnUiThread(new Runnable() {
			public void run() {
				activity.switchTab(TabbedOutputActivity.POWER_GEN_ID);
			}
		});
		Activity switchActivity = switchActivityMonitor.waitForActivityWithTimeout(TIMEOUT);
		assertNotNull(switchActivity);
		
		//Now we can try switching back to the original summary tab
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(OutputSummaryActivity.class.getName(), null, false);
		activity.runOnUiThread(new Runnable() {
			public void run() {
				activity.switchTab(TabbedOutputActivity.SUMMARY_ID);
			}
		});
		Activity nextActivity = activityMonitor.waitForActivityWithTimeout(TIMEOUT);
		assertNotNull(nextActivity);
	}
	
	/**
	 * Test that the get calculator method returns a calculator object
	 */
	public void testGetCalculator() {
		Calculator calculator = activity.getCalculator();
		assertNotNull(calculator);
	}
}

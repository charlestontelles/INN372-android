package au.edu.qut.inn372.greenhat.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import au.edu.qut.inn372.greenhat.bean.Calculator;

public class TabbedActivityTest extends
		ActivityInstrumentationTestCase2<TabbedActivity> {
	
	private static final int TIMEOUT = 10000;
	private TabbedActivity activity;
	private ActivityMonitor startupTabMonitor;
	
	public TabbedActivityTest(){
		super(TabbedActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		startupTabMonitor = getInstrumentation().addMonitor(CustomerUsageActivity.class.getName(), null, false);
		activity = getActivity();
	}
	
	/**
	 * Test that the activity starts up and that the first tab loads correctly
	 */
	public void testStartUp(){
		assertTrue(TabbedActivity.class.getName().length() > 0);
		Activity startupTab = startupTabMonitor.waitForActivityWithTimeout(TIMEOUT);
		assertNotNull(activity);
		assertNotNull(startupTab);
	}
	
	/**
	 * Tests that the activity can switch to the equipment tab
	 */
	public void testTabEquipment() {
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(EquipmentActivity.class.getName(), null, false);
		activity.runOnUiThread(new Runnable() {
			public void run() {
				activity.switchTab(TabbedActivity.EQUIPMENT_ID);
			}
		});
		Activity nextActivity = activityMonitor.waitForActivityWithTimeout(TIMEOUT);
		assertNotNull(nextActivity);
	}
	
	/**
	 * Tests that the activity can switch to the roof tab
	 */
	public void testTabRoof() {
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(RoofActivity.class.getName(), null, false);
		activity.runOnUiThread(new Runnable() {
			public void run() {
				activity.switchTab(TabbedActivity.ROOF_ID);
			}
		});
		Activity nextActivity = activityMonitor.waitForActivityWithTimeout(TIMEOUT);
		assertNotNull(nextActivity);
	}
	
	/**
	 * Tests that the activity can switch to the location tab
	 */
	public void testTabLocation() {
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(LocationActivity.class.getName(), null, false);
		activity.runOnUiThread(new Runnable() {
			public void run() {
				activity.switchTab(TabbedActivity.LOCATION_ID);
			}
		});
		Activity nextActivity = activityMonitor.waitForActivityWithTimeout(TIMEOUT);
		assertNotNull(nextActivity);
	}
	
	/**
	 * Tests that the activity can switch to the input tab
	 */
	public void testTabInput() {
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(BasicInputActivity.class.getName(), null, false);
		activity.runOnUiThread(new Runnable() {
			public void run() {
				activity.switchTab(TabbedActivity.INPUT_ID);
			}
		});
		Activity nextActivity = activityMonitor.waitForActivityWithTimeout(TIMEOUT);
		assertNotNull(nextActivity);
	}
	
	/**
	 * Tests that the activity can switch to the personal usage tab
	 */
	public void testTabCustomerUsage() {
		//Need to switch to a different tab first so we can test 'switching back' to the original tab which is personal usage
		ActivityMonitor switchActivityMonitor = getInstrumentation().addMonitor(EquipmentActivity.class.getName(), null, false);
		activity.runOnUiThread(new Runnable() {
			public void run() {
				activity.switchTab(TabbedActivity.EQUIPMENT_ID);
			}
		});
		Activity switchActivity = switchActivityMonitor.waitForActivityWithTimeout(TIMEOUT);
		assertNotNull(switchActivity);
		
		//Now try switching back to the tab we are testing (Customer usage)
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(CustomerUsageActivity.class.getName(), null, false);
		activity.runOnUiThread(new Runnable() {
			public void run() {
				activity.switchTab(TabbedActivity.USAGE_ID);
			}
		});
		Activity nextActivity = activityMonitor.waitForActivityWithTimeout(TIMEOUT);
		assertNotNull(nextActivity);
	}
	
	/**
	 * Test that the get calculator method returns a calculator
	 */
	public void testGetCalculator() {
		Calculator calculator = activity.getCalculator();
		assertNotNull(calculator);
	}
}

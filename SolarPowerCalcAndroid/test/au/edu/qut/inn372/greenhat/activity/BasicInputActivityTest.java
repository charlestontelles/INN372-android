package au.edu.qut.inn372.greenhat.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;

public class BasicInputActivityTest extends
		ActivityInstrumentationTestCase2<BasicInputActivity> {
	
	private static final int TIMEOUT = 10000;
	private static final String DAILY_USAGE = "4.5";
	private BasicInputActivity activity;
	private TabbedActivity parentActivity;
	
	public BasicInputActivityTest(){
		super(BasicInputActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		//Need to set up the parent (TabbedActivity) as well as the activity depends upon it (otherwise would get null pointer exceptions)
		parentActivity = launchActivity("au.edu.qut.inn372.greenhat.activity", TabbedActivity.class, null);
		
		//Need to use an activityMonitor to get the activity since we are starting it in a thread
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(BasicInputActivity.class.getName(), null, false);
		parentActivity.runOnUiThread(new Runnable() {
			public void run() {
				parentActivity.switchTab(TabbedActivity.INPUT_ID);
			}
		});
		activity = (BasicInputActivity) activityMonitor.waitForActivityWithTimeout(TIMEOUT);
	}
	
	@Override
	protected void tearDown() throws Exception {
		parentActivity.finish(); //Explicitly destroy (finish) the parent tabbed activity to prevent exceptions with multiple tests
		super.tearDown();
	}
	
	/**
	 * Tests that the activity starts up correctly
	 */
	public void testStartUp(){
		assertTrue(BasicInputActivity.class.getName().length() > 0);
		assertNotNull(activity);
	}
	
	/**
	 * Populates fields with appropriate test data
	 */
	private void populateTestData() {
		((EditText)activity.findViewById(R.id.editRoof_Usage_UsagePerDay)).setText(DAILY_USAGE);
		//TODO Add the rest of the input fields
	}
	
	/**
	 * Test that data is saved when the activity is paused
	 */
	public void testSaveData() {
		populateTestData();
		Instrumentation myInstr = getInstrumentation();
		myInstr.callActivityOnPause(activity); //This 'pauses' the activity which causes the saveData method to be called
		
		//Check that the data used in the populateTestData method has been saved to the bean hierarchy
		assertEquals(new Double(parentActivity.getCalculator().getCustomer().getElectricityUsage().getDailyAverageUsage()).toString(), DAILY_USAGE);
		//TODO Add the rest of the fields
	}
	
	/**
	 * Test that the data is loaded when the activity is paused
	 */
	public void testLoadData() {
		populateTestData();
		Instrumentation myInstr = getInstrumentation();
		myInstr.callActivityOnPause(activity); //This 'pauses' the activity which causes the saveData method to be called
		
		//Change values here to check that they are loaded from the calculator and not just the same as they were before pausing
		Double newDailyUsage = 5.5;
		parentActivity.getCalculator().getCustomer().getElectricityUsage().setDailyAverageUsage(newDailyUsage);
		//TODO add the rest of the fields
		
		myInstr.callActivityOnResume(activity); //This 'resumes' the activity which causes the loadData method to be called
		
		//Asserts here for all values to check they are being loaded
		assertEquals(((EditText)(activity.findViewById(R.id.editRoof_Usage_UsagePerDay))).getText().toString(), newDailyUsage.toString());
		//TODO Add the rest of the fields
	}
	
	/**
	 * Tests that the calculate button launches a powerGeneration Activity
	 */
	public void testCalculateActivityLaunch() {
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(TabbedOutputActivity.class.getName(), null, false);
		final Button button = (Button) activity.findViewById(R.id.buttonCalculate);
		activity.runOnUiThread(new Runnable() {
			public void run() {
				button.performClick();
			}
		});
		Activity nextActivity = activityMonitor.waitForActivityWithTimeout(TIMEOUT);
		assertNotNull(nextActivity);
		nextActivity.finish();
	}
	
}

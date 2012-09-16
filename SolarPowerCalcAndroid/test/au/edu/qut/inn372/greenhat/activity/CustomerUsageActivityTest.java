package au.edu.qut.inn372.greenhat.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;

public class CustomerUsageActivityTest extends
		ActivityInstrumentationTestCase2<CustomerUsageActivity> {
	
	private static final int TIMEOUT = 10000;
	private CustomerUsageActivity activity;
	private TabbedActivity parentActivity;
	
	public CustomerUsageActivityTest(){
		super(CustomerUsageActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		//Need to use an activityMonitor to get the activity
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(CustomerUsageActivity.class.getName(), null, false);
		
		//Need to set up the parent (TabbedActivity) as well as the activity depends upon it (otherwise would get null pointer exceptions)
		parentActivity = launchActivity("au.edu.qut.inn372.greenhat.activity", TabbedActivity.class, null);
		
		activity = (CustomerUsageActivity) activityMonitor.waitForActivityWithTimeout(TIMEOUT);
	}
	
	@Override
	protected void tearDown() throws Exception {
		if(activity.getState() == CustomerUsageActivity.STATE_PAUSED){
			//Need to return it to a 'resumed' state so it can shut down cleanly
			final Instrumentation myInstr = getInstrumentation();
			activity.runOnUiThread(new Runnable() {
				public void run() {
					myInstr.callActivityOnResume(activity); //This 'resumes' the activity which causes the loadData method to be called
				}
			});
			//The following loop waits until the state has paused before proceeding (since it is in a separate thread)
			while(activity.getState() == CustomerUsageActivity.STATE_PAUSED) {};
		}
		activity.finish();
		parentActivity.finish(); //Explicitly destroy (finish) the parent tabbed activity to prevent exceptions with multiple tests
		super.tearDown();
	}
	
	/**
	 * Tests that the activity starts up correctly
	 */
	public void testStartUp(){
		assertTrue(CustomerUsageActivity.class.getName().length() > 0);
		assertNotNull(activity);
	}
	
	/**
	 * Populates fields with appropriate test data
	 */
	private void populateTestData() {
		//Example from basic input activity
		//((EditText)activity.findViewById(R.id.editRoof_Usage_UsagePerDay)).setText(DAILY_USAGE); 
		//TODO Add the rest of the input fields
	}
	
	/**
	 * Test that data is saved when the activity is paused
	 */
	public void testSaveData() {
		final Instrumentation myInstr = getInstrumentation();
		activity.runOnUiThread(new Runnable() {
			public void run() {
				populateTestData();
				myInstr.callActivityOnPause(activity); //This 'pauses' the activity which causes the saveData method to be called
			}
		});
		//The following loop waits until the state has paused before proceeding (since it is in a separate thread)
		while(activity.getState() == CustomerUsageActivity.STATE_NORMAL) {};
		
		//Check that the data used in the populateTestData method has been saved to the bean hierarchy
		//Example is from basic input activity test:
		//assertEquals(new Double(parentActivity.getCalculator().getCustomer().getElectricityUsage().getDailyAverageUsage()).toString(), DAILY_USAGE);
		//TODO Add the rest of the fields
	}
	
	/**
	 * Test that the data is loaded when the activity is paused
	 */
	public void testLoadData() {
		final Instrumentation myInstr = getInstrumentation();
		activity.runOnUiThread(new Runnable() {
			public void run() {
				populateTestData();
				myInstr.callActivityOnPause(activity); //This 'pauses' the activity which causes the saveData method to be called
			}
		});
		//The following loop waits until the state has paused before proceeding (since it is in a separate thread)
		while(activity.getState() == CustomerUsageActivity.STATE_NORMAL) {};
		
		//Change values here to check that they are loaded from the calculator and not just the same as they were before pausing
		//Example is from basic input activity test:
		//Double newDailyUsage = 5.5;
		//parentActivity.getCalculator().getCustomer().getElectricityUsage().setDailyAverageUsage(newDailyUsage);
		//TODO add the rest of the fields
		
		activity.runOnUiThread(new Runnable() {
			public void run() {
				myInstr.callActivityOnResume(activity); //This 'resumes' the activity which causes the loadData method to be called
			}
		});
		//The following loop waits until the state has paused before proceeding (since it is in a separate thread)
		while(activity.getState() == CustomerUsageActivity.STATE_PAUSED) {};
		
		//Asserts here for all values to check they are being loaded
		//Example is from basic input activity test:
		//assertEquals(((EditText)(activity.findViewById(R.id.editRoof_Usage_UsagePerDay))).getText().toString(), newDailyUsage.toString());
		//TODO Add the rest of the fields
	}
	
	/**
	 * Tests that the next button launches the next activity
	 */
	public void testNextActivity() {
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(EquipmentActivity.class.getName(), null, false);
		final Button button = (Button) activity.findViewById(R.id.buttonCustomerUsage_Next);
		activity.runOnUiThread(new Runnable() {
			public void run() {
				button.performClick();
			}
		});
		Activity nextActivity = activityMonitor.waitForActivityWithTimeout(TIMEOUT);
		assertNotNull(nextActivity);
		nextActivity.finish();
	}
	
	
	/**
	 * Tests that the logout button login activity
	 */
	public void testLogoutActivity() {
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(LoginActivity.class.getName(), null, false);
		final Button button = (Button) activity.findViewById(R.id.buttonCustomerUsage_Back);
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

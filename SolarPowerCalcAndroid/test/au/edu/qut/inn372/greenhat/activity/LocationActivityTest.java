package au.edu.qut.inn372.greenhat.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.app.Activity;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;

public class LocationActivityTest extends
		ActivityInstrumentationTestCase2<LocationActivity> {
	
	private static final Double ROOF_WIDTH = new Double(1600);
	private static final Double ROOF_HEIGHT = new Double(1200);
	private static final Double SUNLIGHT_HOURS = new Double(3.0);
	private static final int SPINNER_POSITION = 2;
	private static final String CITY = "Melbourne";
	private static final int TIMEOUT = 10000;
	private LocationActivity activity;
	private TabbedActivity parentActivity;
	Spinner citySpinner;
	EditText sunlightHoursEdit;
	EditText roofWidthEdit;
	EditText roofHeightEdit;
	
	public LocationActivityTest(){
		super(LocationActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		//Need to set up the parent (TabbedActivity) as well as the activity depends upon it (otherwise would get null pointer exceptions)
		parentActivity = launchActivity("au.edu.qut.inn372.greenhat.activity", TabbedActivity.class, null);
		
		//Need to use an activityMonitor to get the activity since we are starting it in a thread
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(LocationActivity.class.getName(), null, false);
		parentActivity.runOnUiThread(new Runnable() {
			public void run() {
				parentActivity.switchTab(TabbedActivity.LOCATION_ID);
			}
		});
		activity = (LocationActivity) activityMonitor.waitForActivityWithTimeout(TIMEOUT);
		
		citySpinner = (Spinner)activity.findViewById(R.id.city_spinner);
		sunlightHoursEdit = (EditText)activity.findViewById(R.id.editLocation_Sunlight);
		roofWidthEdit = (EditText)activity.findViewById(R.id.editLocation_RoofWidth);
		roofHeightEdit = (EditText)activity.findViewById(R.id.editLocation_RoofHeight);
	}
	
	@Override
	protected void tearDown() throws Exception {
		if(activity.getState() == LocationActivity.STATE_PAUSED){
			//Need to return it to a 'resumed' state so it can shut down cleanly
			final Instrumentation myInstr = getInstrumentation();
			activity.runOnUiThread(new Runnable() {
				public void run() {
					myInstr.callActivityOnResume(activity); //This 'resumes' the activity which causes the loadData method to be called
				}
			});
			//The following loop waits until the state has paused before proceeding (since it is in a separate thread)
			while(activity.getState() == LocationActivity.STATE_PAUSED) {};
		}
		activity.finish();
		parentActivity.finish(); //Explicitly destroy (finish) the parent tabbed activity to prevent exceptions with multiple tests
		super.tearDown();
	}
	
	/**
	 * Tests that the activity starts up correctly
	 */
	public void testStartUp(){
		assertTrue(LocationActivity.class.getName().length() > 0);
		assertNotNull(activity);
	}
	
	/**
	 * Populates fields with appropriate test data
	 */
	private void populateTestData() {
		//Example from basic input activity
		//((EditText)activity.findViewById(R.id.editRoof_Usage_UsagePerDay)).setText(DAILY_USAGE); 
		citySpinner.setSelection(SPINNER_POSITION);
		sunlightHoursEdit.setText(SUNLIGHT_HOURS.toString());
		roofHeightEdit.setText(ROOF_HEIGHT.toString());
		roofWidthEdit.setText(ROOF_WIDTH.toString());
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
		while(activity.getState() == LocationActivity.STATE_NORMAL) {};
		
		//Check that the data used in the populateTestData method has been saved to the bean hierarchy
		//Example is from basic input activity test:
		//assertEquals(new Double(parentActivity.getCalculator().getCustomer().getElectricityUsage().getDailyAverageUsage()).toString(), DAILY_USAGE);
		assertEquals(CITY, parentActivity.getCalculator().getCustomer().getLocation().getCity());
		assertEquals(SUNLIGHT_HOURS, parentActivity.getCalculator().getCustomer().getLocation().getSunLightHours());
		assertEquals(ROOF_HEIGHT, parentActivity.getCalculator().getCustomer().getLocation().getRoof().getHeight());
		assertEquals(ROOF_WIDTH, parentActivity.getCalculator().getCustomer().getLocation().getRoof().getWidth());
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
		while(activity.getState() == LocationActivity.STATE_NORMAL) {};
		
		//Change values here to check that they are loaded from the calculator and not just the same as they were before pausing
		//Example is from basic input activity test:
		//Double newDailyUsage = 5.5;
		//parentActivity.getCalculator().getCustomer().getElectricityUsage().setDailyAverageUsage(newDailyUsage);
		//TODO add the rest of the fields
		Double newSunlightHours = new Double(5.5);
		Double newRoofHeight = new Double(1300);
		Double newRoofWidth = new Double(1100);
		String newCity = "Brisbane";
		int newCityPosition = 0;
		parentActivity.getCalculator().getCustomer().getLocation().setCity(newCity);
		parentActivity.getCalculator().getCustomer().getLocation().setSunLightHours(newSunlightHours);
		parentActivity.getCalculator().getCustomer().getLocation().getRoof().setWidth(newRoofWidth);
		parentActivity.getCalculator().getCustomer().getLocation().getRoof().setHeight(newRoofHeight);
		
		activity.runOnUiThread(new Runnable() {
			public void run() {
				myInstr.callActivityOnResume(activity); //This 'resumes' the activity which causes the loadData method to be called
			}
		});
		//The following loop waits until the state has paused before proceeding (since it is in a separate thread)
		while(activity.getState() == LocationActivity.STATE_PAUSED) {};
		
		//Asserts here for all values to check they are being loaded
		//Example is from basic input activity test:
		//assertEquals(((EditText)(activity.findViewById(R.id.editRoof_Usage_UsagePerDay))).getText().toString(), newDailyUsage.toString());
		//TODO Add the rest of the fields
		assertEquals(newCityPosition, citySpinner.getSelectedItemPosition());
		assertEquals(newSunlightHours.toString(), sunlightHoursEdit.getText().toString());
		assertEquals(newRoofHeight.toString(), roofHeightEdit.getText().toString());
		assertEquals(newRoofWidth.toString(), roofWidthEdit.getText().toString());
	}
	
	/**
	 * Tests that the next button launches the next activity
	 */
	public void testNextActivity() {
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(CustomerUsageActivity.class.getName(), null, false);
		final Button button = (Button) activity.findViewById(R.id.buttonLocation_Next);
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
	 * Tests that the back button returns to the previous activity
	 */
	public void testLogoutActivity() {
	
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(LoginActivity.class.getName(), null, false);
		final Button button = (Button) activity.findViewById(R.id.buttonLocation_Back);
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

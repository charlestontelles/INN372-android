package au.edu.qut.inn372.greenhat.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;

public class LoginActivityTest extends
		ActivityInstrumentationTestCase2<LoginActivity> {
	
	private static final int TIMEOUT = 10000;
	private LoginActivity activity;
	
	public LoginActivityTest(){
		super(LoginActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception{
		super.setUp();
		activity = getActivity();
	}
	
	/**
	 * Test that the activity starts up
	 */
	public void testStartUp(){
		assertTrue(LoginActivity.class.getName().length() > 0);
		assertNotNull(activity);
	}
	
	/**
	 * Tests that the login button launches a tabbedActivity
	 */
	public void testLogin() {
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(TabbedActivity.class.getName(), null, false);
		final Button button = (Button) activity.findViewById(R.id.buttonLogin);
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

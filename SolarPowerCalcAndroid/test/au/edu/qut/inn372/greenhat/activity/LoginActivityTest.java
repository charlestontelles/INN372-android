package au.edu.qut.inn372.greenhat.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
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
	 * Tests that the login button launches a UserHomepageActivity
	 */
	public void testLogin() {
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(UserHomepageActivity.class.getName(), null, false);
		final Button button = (Button) activity.findViewById(R.id.buttonLogin);
		final EditText edit = (EditText) activity.findViewById(R.id.editPassword);
		edit.setText("1234");
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

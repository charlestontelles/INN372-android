package au.edu.qut.inn372.greenhat.activity.test;


import android.test.ActivityInstrumentationTestCase2;
import au.edu.qut.inn372.greenhat.activity.LoginActivity;

import com.jayway.android.robotium.solo.Solo;

public class SetupActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {
	
	protected static final int TIMEOUT = 10000; //Timeout for all soap operations
	protected Solo solo;
	protected static final String username = "testUser";
	protected static final String password = "testUserPassword";

	public SetupActivityTest() {
		super("au.edu.qut.inn372.greenhat.activity", LoginActivity.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}

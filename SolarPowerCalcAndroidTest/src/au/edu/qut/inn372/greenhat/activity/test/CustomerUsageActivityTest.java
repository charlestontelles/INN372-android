package au.edu.qut.inn372.greenhat.activity.test;

import au.edu.qut.inn372.greenhat.activity.TabbedActivity;

//import au.edu.qut.inn372.greenhat.activity.R;

public class CustomerUsageActivityTest extends SetupTabbedActivityTest {

	public CustomerUsageActivityTest() {
		super(SetupTabbedActivityTest.NEW_CALCULATOR); //These tests are based on a new calculator
	}
	
	public void setUp() throws Exception {
		super.setUp();
		switchTab(TabbedActivity.USAGE_ID);
	}
	
	public void dummyTest() {
		assertTrue(true);
	}
	
}

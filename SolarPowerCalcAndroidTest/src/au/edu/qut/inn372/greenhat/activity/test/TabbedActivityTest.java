package au.edu.qut.inn372.greenhat.activity.test;

import au.edu.qut.inn372.greenhat.activity.TabbedActivity;


//import au.edu.qut.inn372.greenhat.activity.R;

public class TabbedActivityTest extends SetupTabbedActivityTest {

	public TabbedActivityTest() {
		super(SetupTabbedActivityTest.NEW_CALCULATOR); //These tests are based on a new calculator
	}
	
	public void testCycleTabs() {
		assertTrue(switchTab(TabbedActivity.USAGE_ID));
		assertTrue(switchTab(TabbedActivity.EQUIPMENT_ID));
		assertTrue(switchTab(TabbedActivity.ROOF_ID));
		assertTrue(switchTab(TabbedActivity.INPUT_ID));
		assertTrue(switchTab(TabbedActivity.LOCATION_ID));
	}
}

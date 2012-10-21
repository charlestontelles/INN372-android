package au.edu.qut.inn372.greenhat.activity.test;

import au.edu.qut.inn372.greenhat.activity.TabbedActivity;

//import au.edu.qut.inn372.greenhat.activity.R;

public class RoofActivityTest extends SetupCalculationTest {

	public RoofActivityTest() {
		super(SetupCalculationTest.NEW_CALCULATOR); //These tests are based on a new calculator
	}
	
	public void setUp() throws Exception {
		super.setUp();
		switchTab(TabbedActivity.ROOF_ID);
	}
	
}

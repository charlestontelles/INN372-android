package au.edu.qut.inn372.greenhat.activity.test;

import au.edu.qut.inn372.greenhat.activity.TabbedActivity;


//import au.edu.qut.inn372.greenhat.activity.R;

public class TabbedActivityTest extends SetupCalculationTest {

	public TabbedActivityTest() {
		super(SetupCalculationTest.NEW_CALCULATOR); //These tests are based on a new calculator
	}
	
	public void testCycleTabs() {
		//Test normal cycling
		assertTrue(switchTab(TabbedActivity.USAGE_ID));
		assertTrue(switchTab(TabbedActivity.EQUIPMENT_ID));
		assertTrue(switchTab(TabbedActivity.ROOF_ID));
		assertTrue(switchTab(TabbedActivity.INPUT_ID));
		assertTrue(switchTab(TabbedActivity.LOCATION_ID));
		assertTrue(switchTab(TabbedActivity.INPUT_ID));
		
		//test cycling with the navigation buttons
		solo.clickOnButton(backButtonString);
		solo.clickOnButton(backButtonString);
		solo.clickOnButton(backButtonString);
		solo.clickOnButton(backButtonString);
		assertTrue(solo.waitForActivity("LocationActivity", TIMEOUT));
		solo.clickOnButton(nextButtonString);
		solo.clickOnButton(nextButtonString);
		solo.clickOnButton(nextButtonString);
		solo.clickOnButton(nextButtonString);
		assertTrue(solo.waitForActivity("BasicInputActivity", TIMEOUT));
		
		//test a combination of cycling (navigation + clicking on tabs)
		assertTrue(switchTab(TabbedActivity.EQUIPMENT_ID));
		solo.clickOnButton(nextButtonString);
		assertTrue(solo.waitForActivity("RoofActivity", TIMEOUT));
		assertTrue(switchTab(TabbedActivity.USAGE_ID));
		solo.clickOnButton(backButtonString);
		assertTrue(solo.waitForActivity("LocationActivity", TIMEOUT));
	}
	
	//test the two navigation buttons as well

	//test menu functions (save and save as tempalte)
}

package au.edu.qut.inn372.greenhat.activity.test;

import android.widget.EditText;
import au.edu.qut.inn372.greenhat.activity.R;
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
	
	public void testSaveCalculator() {
		//first change something to check that it is actually saved
		switchTab(TabbedActivity.USAGE_ID);
		Double dailyUsage = 19.7;
		EditText dailyUsageEdit = (EditText)solo.getView(R.id.editCustomerUsage_CurrentUsage_UsagePerDay);
		solo.clearEditText(dailyUsageEdit);
		solo.enterText(dailyUsageEdit, dailyUsage.toString());
		
		solo.clickOnMenuItem("Save Calculation");
		solo.waitForText("Calculation Saved.");
		solo.clickOnButton("OK");
		
		//re-open the saved calculator (should be fifth on the list)
		solo.goBackToActivity("UserHomepageActivity");
		solo.waitForText("Welcome");
		solo.clickOnCheckBox(4);
		solo.clickOnButton("Load");
		solo.waitForActivity("TabbedActivity", TIMEOUT);
		switchTab(TabbedActivity.USAGE_ID);
		assertEquals(dailyUsageEdit.getText().toString(), dailyUsage.toString());
		
		//reset state
		solo.goBackToActivity("UserHomepageActivity");
		solo.waitForText("Welcome");
		solo.clickOnCheckBox(4);
		solo.clickOnButton("Delete");
		solo.waitForText("OK");
		solo.clickOnButton("OK");
	}
	
	public void testResaveKey() {
		//re-open the saved calculator (should be fifth on the list)
		solo.goBackToActivity("UserHomepageActivity");
		solo.waitForText("Welcome");
		solo.clickOnCheckBox(0); //load an incomplete calculator
		solo.clickOnButton("Load");
		solo.waitForActivity("TabbedActivity", TIMEOUT);
		//save again
		solo.clickOnMenuItem("Save Calculation");
		solo.waitForText("Calculation Saved.");
		solo.clickOnButton("OK");
		//check that this save didn't create a new calculator (since it was a loaded calculator)
		solo.goBackToActivity("UserHomepageActivity");
		assertTrue(solo.getCurrentCheckBoxes().size()==4);
		//order is destroyed so will need reset state for the next test - force this by deleting a calculator
		solo.clickOnCheckBox(0);
		solo.clickOnButton("Delete");
		solo.waitForText("OK");
		solo.clickOnButton("OK");
	}

	public void testSaveTemplate() {
		//save a template
		solo.clickOnMenuItem("Save as Template");
		solo.waitForText("Calculation Saved.");
		solo.clickOnButton("OK");
		solo.goBackToActivity("UserHomepageActivity");
		solo.waitForText("Welcome");
		//load it and hit save again - check that this DOES create a new calculator (So there should be 6 now)
		solo.clickOnCheckBox(4); //load an incomplete calculator
		solo.clickOnButton("Load");
		solo.waitForActivity("TabbedActivity", TIMEOUT);
		solo.clickOnMenuItem("Save Calculation");
		solo.waitForText("Calculation Saved.");
		solo.clickOnButton("OK");
		solo.goBackToActivity("UserHomepageActivity");
		assertTrue(solo.getCurrentCheckBoxes().size()==6);
		//cleanup
		solo.clickOnCheckBox(4);
		solo.clickOnCheckBox(5);
		solo.clickOnButton("Delete");
		solo.waitForText("OK");
		solo.clickOnButton("OK");
	}
}

package au.edu.qut.inn372.greenhat.activity.test;

import java.util.List;
import java.util.concurrent.Callable;

import android.widget.CheckBox;
import android.widget.EditText;
import au.edu.qut.inn372.greenhat.activity.TabbedActivity;


public class SetupCalculationTest extends SetupHomepageActivityTest {
	
	private int calculatorState = NEW_CALCULATOR;
	protected static final int NEW_CALCULATOR = 0;
	protected static final int EXISTING_CALCULATOR = 1;
	protected static final int COMPARE_CALCULATORS = 2;
	protected TabbedActivity tabbedActivity;
	protected String backButtonString = "<< Back";
	protected String nextButtonString = "Next >>";
	private List<CheckBox> checkboxes;
	

	protected SetupCalculationTest(int calculatorState) {
		super();
		this.calculatorState = calculatorState;
	}
	
	public void setUp() throws Exception {
		super.setUp();
		checkboxes = solo.getCurrentCheckBoxes();
		switch(calculatorState) {
		case(EXISTING_CALCULATOR):
			if(checkboxes.size()==0) {
				createAndSaveCalculation();
			}
			solo.clickOnCheckBox(0);
			solo.clickOnButton("Load Calculation");
			solo.waitForActivity("TabbedActivity", TIMEOUT);
			break;
		case(COMPARE_CALCULATORS):
			for(int i = checkboxes.size(); i < 2; i++) { //two completed calculations required
				createAndSaveCompleteCalculation();
			}
			//TODO Add checkbox selection logic so the test clicks on boxes with a complete status
			solo.clickOnCheckBox(0);
			solo.clickOnCheckBox(1);
			solo.clickOnButton("Compare Calculations");
			solo.waitForActivity("CompareOutputActivity", TIMEOUT);
			break;

		case(NEW_CALCULATOR):
		default:
			solo.clickOnButton("New Calculation");
			solo.waitForActivity("TabbedActivity", TIMEOUT);
			tabbedActivity = (TabbedActivity)solo.getCurrentActivity();
			break;
		}
	}
	
	private void createAndSaveCalculation() {
		solo.clickOnButton("New Calculation");
		solo.waitForActivity("TabbedActivity", TIMEOUT);
		solo.clickOnMenuItem("Save Calculation Inputs");
		solo.waitForText("Calculation Saved.");
		solo.clickOnButton("OK");
		solo.goBackToActivity("UserHomepageActivity");
	}
	
	private void createAndSaveCompleteCalculation() {
		solo.clickOnButton("New Calculation");
		solo.waitForActivity("TabbedActivity", TIMEOUT);
		tabbedActivity = (TabbedActivity)solo.getCurrentActivity();
		switchTab(TabbedActivity.INPUT_ID);
		solo.clickOnButton("Calculate");
		solo.waitForActivity("TabbedOutputActivity", TIMEOUT);
		solo.clickOnMenuItem("Save Calculation");
		solo.waitForText("Calculation Saved.");
		solo.clickOnButton("OK");
		solo.goBackToActivity("UserHomepageActivity");
	}
	
	protected boolean switchTab(final int targetTab) {
		tabbedActivity.runOnUiThread(new Runnable() {
			public void run() {
				tabbedActivity.switchTab(targetTab);
			}
		});
		String activityName = null;
		switch(targetTab) {
		case(TabbedActivity.LOCATION_ID):
			activityName = "LocationActivity";
			break;
		case(TabbedActivity.USAGE_ID):
			activityName = "CustomerUsageActivity";
			break;
		case(TabbedActivity.EQUIPMENT_ID):
			activityName = "EquipmentActivity";
			break;
		case(TabbedActivity.ROOF_ID):
			activityName = "RoofActivity";
			break;
		case(TabbedActivity.INPUT_ID):
			activityName = "BasicInputActivity";
			break;
		}
		return solo.waitForActivity(activityName, TIMEOUT);
	}
	
	protected void testSaveAttributeDouble(EditText editTextField, Double testValue, int targetTab, int currentTab, Callable<Double> calculatorAttribute) {
		solo.clearEditText(editTextField);
		solo.enterText(editTextField, testValue.toString());
		switchTab(targetTab);
		try {
			assertEquals(testValue, calculatorAttribute.call(), 1e-4);
		} catch (Exception e) {
			fail("Could not call calculator attribute");
		}
		switchTab(currentTab);
	}
	
}

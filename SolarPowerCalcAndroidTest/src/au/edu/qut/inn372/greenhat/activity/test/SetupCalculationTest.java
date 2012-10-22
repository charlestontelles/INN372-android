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
	protected TabbedActivity tabbedActivity;
	protected String backButtonString = "<";
	protected String nextButtonString = ">";
	

	protected SetupCalculationTest(int calculatorState) {
		super();
		this.calculatorState = calculatorState;
	}
	
	public void setUp() throws Exception {
		super.setUp();
		switch(calculatorState) {
		case(EXISTING_CALCULATOR):
			solo.clickOnCheckBox(INCOMPLETE_CHECKBOX);
			solo.clickOnButton("Load");
			break;
		case(NEW_CALCULATOR):
		default:
			solo.clickOnButton("New");
			break;
		}
		solo.waitForActivity("TabbedActivity", TIMEOUT);
		tabbedActivity = (TabbedActivity)solo.getCurrentActivity();
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
	
	protected void testSaveAttributeInteger(EditText editTextField, Integer testValue, int targetTab, int currentTab, Callable<Integer> calculatorAttribute) {
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

package au.edu.qut.inn372.greenhat.activity.test;

import java.util.List;
import java.util.concurrent.Callable;

import android.widget.CheckBox;
import android.widget.EditText;
import au.edu.qut.inn372.greenhat.activity.TabbedActivity;
import au.edu.qut.inn372.greenhat.activity.TabbedOutputActivity;


public class SetupCalculationOutputTest extends SetupHomepageActivityTest {
	
	private int calculatorState = SINGLE_CALCULATOR;
	protected static final int SINGLE_CALCULATOR = 0;
	protected static final int COMPARE_CALCULATORS = 1;
	protected TabbedOutputActivity tabbedActivity;
	protected String backButtonString = "<";
	protected String nextButtonString = ">";
	

	protected SetupCalculationOutputTest(int calculatorState) {
		super();
		this.calculatorState = calculatorState;
	}
	
	public void setUp() throws Exception {
		super.setUp();
		switch(calculatorState) {
		case(COMPARE_CALCULATORS):
			solo.clickOnCheckBox(COMPLETE_1_CHECKBOX);
			solo.clickOnCheckBox(COMPLETE_2_CHECKBOX);
			solo.clickOnButton("Compare");
			break;
		case(SINGLE_CALCULATOR):
		default:
			solo.clickOnCheckBox(COMPLETE_1_CHECKBOX);
			solo.clickOnButton("Load");
			break;
		}
		solo.waitForActivity("TabbedOutputActivity", TIMEOUT);
		tabbedActivity = (TabbedOutputActivity)solo.getCurrentActivity();
	}
	
	protected boolean switchTab(final int targetTab) {
		tabbedActivity.runOnUiThread(new Runnable() {
			public void run() {
				tabbedActivity.switchTab(targetTab);
			}
		});
		String activityName = null;
		switch(targetTab) {
		case(TabbedOutputActivity.SUMMARY_ID):
			activityName = "OutputSummaryActivity";
			break;
		case(TabbedOutputActivity.POWER_GEN_ID):
			activityName = "PowerGeneration";
			break;
		case(TabbedOutputActivity.FINANCIAL_ID):
			activityName = "FinancialOutputActivity";
			break;
		case(TabbedOutputActivity.SAVINGS_GRAPH_ID):
			activityName = "SavingsGraphActivity";
			break;
		case(TabbedOutputActivity.COST_GRAPH_ID):
			activityName = "CostGraphActivity";
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

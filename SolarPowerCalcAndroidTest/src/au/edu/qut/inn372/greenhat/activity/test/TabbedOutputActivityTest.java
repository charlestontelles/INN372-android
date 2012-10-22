package au.edu.qut.inn372.greenhat.activity.test;

import android.widget.EditText;
import au.edu.qut.inn372.greenhat.activity.R;
import au.edu.qut.inn372.greenhat.activity.TabbedActivity;
import au.edu.qut.inn372.greenhat.activity.TabbedOutputActivity;

public class TabbedOutputActivityTest extends SetupCalculationOutputTest {

	public TabbedOutputActivityTest() {
		super(SetupCalculationOutputTest.SINGLE_CALCULATOR); //These tests are based on a new calculator
	}
	
	public void testCycleTabs() {
		//Test normal cycling
		assertTrue(switchTab(TabbedOutputActivity.POWER_GEN_ID));
		assertTrue(switchTab(TabbedOutputActivity.FINANCIAL_ID));
		assertTrue(switchTab(TabbedOutputActivity.SAVINGS_GRAPH_ID));
		assertTrue(switchTab(TabbedOutputActivity.SUMMARY_ID));
		assertTrue(switchTab(TabbedOutputActivity.COST_GRAPH_ID));
		
		//test cycling with the navigation buttons
		solo.clickOnButton(backButtonString);
		solo.clickOnButton(backButtonString);
		solo.clickOnButton(backButtonString);
		solo.clickOnButton(backButtonString);
		assertTrue(solo.waitForActivity("OutputSummaryActivity", TIMEOUT));
		solo.clickOnButton(nextButtonString);
		solo.clickOnButton(nextButtonString);
		solo.clickOnButton(nextButtonString);
		solo.clickOnButton(nextButtonString);
		assertTrue(solo.waitForActivity("CostGraphActivity", TIMEOUT));
		
		//test a combination of cycling (navigation + clicking on tabs)
		assertTrue(switchTab(TabbedOutputActivity.FINANCIAL_ID));
		solo.clickOnButton(nextButtonString);
		assertTrue(solo.waitForActivity("SavingsGraphActivity", TIMEOUT));
		assertTrue(switchTab(TabbedOutputActivity.POWER_GEN_ID));
		solo.clickOnButton(backButtonString);
		assertTrue(solo.waitForActivity("OutputSummaryActivity", TIMEOUT));
	}

	public void testSaveCalculator() {
		//create a new calculator and save it - see if it was added to the list
		solo.goBackToActivity("UserHomepageActivity");
		solo.waitForText("Welcome");
		solo.clickOnButton("New");
		solo.waitForActivity("TabbedActivity", TIMEOUT);
		solo.clickOnButton(nextButtonString);
		solo.clickOnButton(nextButtonString);
		solo.clickOnButton(nextButtonString);
		solo.clickOnButton(nextButtonString);
		solo.clickOnButton("Calculate");
		solo.waitForActivity("TabbedOutputActivity", TIMEOUT);
		solo.clickOnMenuItem("Save Calculation");
		solo.waitForText("OK");
		solo.clickOnButton("OK");
		solo.goBackToActivity("UserHomepageActivity");
		assertTrue(solo.getCurrentCheckBoxes().size()==5);
		//cleanup
		solo.clickOnCheckBox(4);
		solo.clickOnButton("Delete");
		solo.waitForText("OK");
		solo.clickOnButton("OK");
	}
	
	public void testSaveTemplate() {
		solo.clickOnMenuItem("Save as Template");
		solo.waitForText("OK");
		solo.clickOnButton("OK");
		solo.goBackToActivity("UserHomepageActivity");
		assertTrue(solo.getCurrentCheckBoxes().size()==5);
		//check it's a template by loading and saving and seeing that a new calculator is created
		solo.clickOnCheckBox(4);
		solo.clickOnButton("Load");
		assertTrue(solo.waitForActivity("TabbedActivity"));
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

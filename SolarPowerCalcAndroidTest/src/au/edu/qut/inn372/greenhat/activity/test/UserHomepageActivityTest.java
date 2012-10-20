package au.edu.qut.inn372.greenhat.activity.test;

import java.util.ArrayList;
import java.util.List;

import android.widget.CheckBox;
//import au.edu.qut.inn372.greenhat.activity.R;

public class UserHomepageActivityTest extends SetupHomepageActivityTest {
	
	private List<CheckBox> checkboxes;
	
	public void setUp() throws Exception {
		super.setUp();
		checkboxes = solo.getCurrentCheckBoxes();
	}
	
	public void testNewCalculation() {
		solo.clickOnButton("New Calculation");
		assertTrue(solo.waitForActivity("TabbedActivity", TIMEOUT));
	}
	
	//test that you can select a calculator and open it
	public void testLoadCalculation() {
		if(checkboxes.size()==0) {
			createAndSaveCalculation();
		}
		solo.clickOnCheckBox(0);
		solo.clickOnButton("Load Calculation");
		assertTrue(solo.waitForActivity("TabbedActivity", TIMEOUT));
	}
	
	private void createAndSaveCalculation() {
		solo.clickOnButton("New Calculation");
		solo.waitForActivity("TabbedActivity", TIMEOUT);
		solo.clickOnMenuItem("Save Calculation Inputs");
		solo.waitForText("Calculation Saved.");
		solo.clickOnButton("OK");
		solo.goBackToActivity("UserHomepageActivity");
	}
	
	public void testCompareCalculation() {
		//create required calculations
		for(int i = checkboxes.size(); i < 2; i++) { //two completed calculations required
			createAndSaveCompleteCalculation();
		}
	}
	
	private void createAndSaveCompleteCalculation() {
		solo.clickOnButton("New Calculation");
		solo.waitForActivity("TabbedActivity", TIMEOUT);
		
	}
}

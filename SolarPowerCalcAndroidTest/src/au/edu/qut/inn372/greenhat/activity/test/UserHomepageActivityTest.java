package au.edu.qut.inn372.greenhat.activity.test;

import java.util.ArrayList;
import java.util.List;

import android.widget.CheckBox;
//import au.edu.qut.inn372.greenhat.activity.R;

public class UserHomepageActivityTest extends SetupHomepageActivityTest {
	
	/**
	 * Tests the new button
	 */
	public void testNewCalculation() {
		solo.clickOnButton("New");
		assertTrue(solo.waitForActivity("TabbedActivity", TIMEOUT));
	}
	
	/**
	 * Tests the load button
	 */
	public void testLoadCalculation() {
		//solo.clickInList(line)
		solo.clickOnCheckBox(INCOMPLETE_CHECKBOX);
		solo.clickOnButton("Load");
		assertTrue(solo.waitForActivity("TabbedActivity", TIMEOUT));
	}
	
	/**
	 * Tests the compare button
	 */
	public void testCompareCalculation() {
		solo.clickOnCheckBox(COMPLETE_1_CHECKBOX);
		solo.clickOnCheckBox(COMPLETE_2_CHECKBOX);
		solo.clickOnButton("Compare");
		assertTrue(solo.waitForActivity("TabbedOutputActivity", TIMEOUT));
	}
	
	/**
	 * Test the delete calculations button
	 */
	public void testDeleteCalculations() {
		solo.clickOnCheckBox(COMPLETE_2_CHECKBOX);
		solo.clickOnCheckBox(TEMPLATE_CHECKBOX);
		solo.clickOnButton("Delete");
		solo.waitForText("OK");
		solo.clickOnButton("OK");
		List<CheckBox> checkboxList = solo.getCurrentCheckBoxes();
		assert(checkboxList.size() == 2);
	}
	
	public void testLogout() {
		solo.clickOnButton("<< Logout");
		assertTrue(solo.waitForActivity("LoginActivity", TIMEOUT));
	}
	
}

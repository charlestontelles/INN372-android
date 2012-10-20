package au.edu.qut.inn372.greenhat.activity.test;

import java.util.ArrayList;
import java.util.List;

import android.widget.CheckBox;
//import au.edu.qut.inn372.greenhat.activity.R;

public class UserHomepageActivityTest extends SetupHomepageActivityTest {
	
	
	public void setUp() throws Exception {
		super.setUp();
	}
	
	public void testNewCalculation() {
		solo.clickOnButton("New Calculation");
		assertTrue(solo.waitForActivity("TabbedActivity", TIMEOUT));
	}
}

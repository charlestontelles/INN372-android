package au.edu.qut.inn372.greenhat.activity.test;


//import au.edu.qut.inn372.greenhat.activity.R;

public class UserHomepageActivityTest extends SetupHomepageActivityTest {
	
	public void testNewCalculation() {
		solo.clickOnButton("New Calculation");
		assertTrue(solo.waitForActivity("TabbedActivity", TIMEOUT));
	}

}

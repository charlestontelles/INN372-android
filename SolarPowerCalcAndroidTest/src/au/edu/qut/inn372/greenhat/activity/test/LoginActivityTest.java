package au.edu.qut.inn372.greenhat.activity.test;


import android.widget.EditText;
import au.edu.qut.inn372.greenhat.activity.R;

public class LoginActivityTest extends SetupActivityTest {
	
	private EditText editUsername;
	private EditText editPassword;

	public void setUp() throws Exception {
		super.setUp();
		editUsername = (EditText) solo.getView(R.id.editUser);
		editPassword = (EditText) solo.getView(R.id.editPassword);
		solo.clearEditText(editUsername);
		solo.clearEditText(editPassword);
	}
	
	public void testBadLogin() {
		solo.enterText(editUsername, "asefojia");
		solo.enterText(editPassword, "aw90fj2349");
		solo.clickOnButton("Login");
		assertTrue(solo.waitForText("Username or password is incorrect. Please try again.", 1, TIMEOUT));
	}
	
	public void testLogin() {
		solo.enterText(editUsername, username);
		solo.enterText(editPassword, password);
		solo.clickOnButton("Login");
		assertTrue(solo.waitForActivity("UserHomepageActivity", TIMEOUT));
	}
}

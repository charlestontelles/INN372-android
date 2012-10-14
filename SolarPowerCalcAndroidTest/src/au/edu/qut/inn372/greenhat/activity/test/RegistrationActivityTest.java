package au.edu.qut.inn372.greenhat.activity.test;


import java.math.BigInteger;
import java.security.SecureRandom;

import android.widget.EditText;
import au.edu.qut.inn372.greenhat.activity.R;

public class RegistrationActivityTest extends SetupActivityTest {
	
	private EditText editName;
	private EditText editEmail;
	private EditText editPassword;
	private EditText editPasswordRepeat;

	public void setUp() throws Exception {
		super.setUp();
		solo.clickOnButton("Register");
		solo.waitForActivity("RegistrationActivity", TIMEOUT);
		editName = (EditText) solo.getView(R.id.Edit_Registration_Username);
		editEmail = (EditText) solo.getView(R.id.Edit_Registration_Email);
		editPassword = (EditText) solo.getView(R.id.Edit_Registration_Password);
		editPasswordRepeat = (EditText) solo.getView(R.id.Edit_Registration_Password_Repeat);
		
		//If we ever put default values in, remember to clear them here
	}
	
	public void testRegister() {
		SecureRandom random = new SecureRandom();
		String username = new BigInteger(16, random).toString(8) + "@email.com"; //random character username
		String password = new BigInteger(16, random).toString(8);
		solo.enterText(editName, "Name");
		solo.enterText(editEmail, username);
		solo.enterText(editPassword, password);
		solo.enterText(editPasswordRepeat, password);
		//Select Customer from spinner
		solo.pressSpinnerItem(0, 0); //(0, 2) means 0 is the 0th spinner on this activity (Don't use r.id in this method call, 0 means move 0 positions downwards relative to the starting position)
		solo.clickOnButton("Register and Login");
		assertTrue(solo.waitForActivity("UserHomepageActivity", TIMEOUT));
		
		try {
			Thread.sleep(5000); //Need to give the server time to register the new user before trying to login
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//go back to login and check the user can login
		solo.goBackToActivity("LoginActivity");
		solo.waitForActivity("LoginActivity", TIMEOUT);
		EditText editLoginUsername = (EditText) solo.getView(R.id.editUser);
		EditText editLoginPassword = (EditText) solo.getView(R.id.editPassword);
		solo.clearEditText(editLoginUsername);
		solo.clearEditText(editLoginPassword);
		solo.enterText(editLoginUsername, username);
		solo.enterText(editLoginPassword, password);
		
		solo.clickOnButton("Login");
		assertTrue(solo.waitForActivity("UserHomepageActivity", TIMEOUT));
	}
	
	public void testNonMatchingPasswords() {
		SecureRandom random = new SecureRandom();
		String password = new BigInteger(16, random).toString(8);
		
		solo.enterText(editName,  "Name");
		solo.enterText(editEmail, "name@email.com");
		solo.enterText(editPassword, password);
		solo.enterText(editPasswordRepeat, password+"1");
		solo.clickOnButton("Register and Login");
		assertTrue(solo.waitForText("Passwords do not match. Please try again.", 1, TIMEOUT));
	}
	
	public void testBackButton() {
		solo.goBack(); //CLicking the back button removes the default onscreen keyboard, allowing the test to click on the back button
		solo.clickOnButton("Back");
		assertTrue(solo.waitForActivity("LoginActivity", TIMEOUT));
	}
}

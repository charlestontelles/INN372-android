package au.edu.qut.inn372.greenhat.activity.test;


import android.widget.EditText;
import au.edu.qut.inn372.greenhat.activity.R;

public class SetupHomepageActivityTest extends SetupActivityTest {
	
	private EditText editUsername;
	private EditText editPassword;

	public void setUp() throws Exception {
		super.setUp();
		login();
		if(!solo.waitForActivity("UserHomepageActivity", TIMEOUT)) { //if it times out, assume that the credentials were incorrect, so register a new user
			solo.clickOnButton("OK"); //dismiss error dialog
			registerLogin();
		}
	}
	
	private void login() {
		editUsername = (EditText) solo.getView(R.id.editUser);
		editPassword = (EditText) solo.getView(R.id.editPassword);
		solo.clearEditText(editUsername);
		solo.clearEditText(editPassword);
		solo.enterText(editUsername, username);
		solo.enterText(editPassword, password);
		solo.clickOnButton("Login");
	}
	
	private void registerLogin() {
		solo.clickOnButton("Register");
		solo.waitForActivity("RegistrationActivity", TIMEOUT);
		EditText editName = (EditText) solo.getView(R.id.Edit_Registration_Username);
		EditText editEmail = (EditText) solo.getView(R.id.Edit_Registration_Email);
		EditText editPassword = (EditText) solo.getView(R.id.Edit_Registration_Password);
		EditText editPasswordRepeat = (EditText) solo.getView(R.id.Edit_Registration_Password_Repeat);
		solo.enterText(editName, "Name");
		solo.enterText(editEmail, username);
		solo.enterText(editPassword, password);
		solo.enterText(editPasswordRepeat, password);
		solo.clickOnButton("Register and Login");
		solo.waitForActivity("UserHomepageActivity", TIMEOUT);
	}
}

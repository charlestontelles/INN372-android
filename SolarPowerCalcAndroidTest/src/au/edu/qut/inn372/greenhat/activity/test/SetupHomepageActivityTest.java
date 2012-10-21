package au.edu.qut.inn372.greenhat.activity.test;


import java.util.List;

import android.widget.CheckBox;
import android.widget.EditText;
import au.edu.qut.inn372.greenhat.activity.R;
import au.edu.qut.inn372.greenhat.activity.UserHomepageActivity;

public class SetupHomepageActivityTest extends SetupActivityTest {
	
	private EditText editUsername;
	private EditText editPassword;
	protected static final int INCOMPLETE_CHECKBOX = 0;
	protected static final int COMPLETE_1_CHECKBOX = 1;
	protected static final int COMPLETE_2_CHECKBOX = 2;
	protected static final int TEMPLATE_CHECKBOX = 3;

	public void setUp() throws Exception {
		super.setUp();
		login();
		if(!solo.waitForActivity("UserHomepageActivity", TIMEOUT)) { //if it times out, assume that the credentials were incorrect, so register a new user
			solo.clickOnButton("OK"); //dismiss error dialog
			registerLogin();
		}
		//Need to wait for activity to finish starting
		solo.waitForText("Welcome");
		
		//Make sure there are 4 calculators, first is 'incomplete', second two are 'complete and fourth is 'template
		//If there are 4 calculators, assume they are correct, otherwise regenerate them all
		List<CheckBox> checkboxList = solo.getCurrentCheckBoxes();
		if(checkboxList.size() != 4) {
			//Delete all existing calculators
			
			int count = 0;
			for(CheckBox curCheckBox : checkboxList) {
				solo.clickOnCheckBox(count);
				count += 1;
			}
			if(checkboxList.size() != 0) {
				solo.clickOnButton("Delete");
				solo.waitForText("OK");
				solo.clickOnButton("OK");
			}
			
			//wait for calculators to be deleted
			//Setup incomplete calculator
			setupNewCalculation(0);
			setupNewCalculation(1);
			setupNewCalculation(1);
			setupNewCalculation(2);
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
	
	protected void setupNewCalculation(int state) {
		//exit if the current activity is not the home page
		if(!solo.getCurrentActivity().getClass().toString().equals(UserHomepageActivity.class.toString())) {
			return;
		}
		//0 - incomplete
		//1 - complete
		//2 template
		switch(state) {
		case 1:
			solo.clickOnButton("New");
			solo.waitForActivity("TabbedActivity", TIMEOUT);
			solo.clickOnButton(">");
			solo.clickOnButton(">");
			solo.clickOnButton(">");
			solo.clickOnButton(">");
			solo.clickOnButton("Calculate");
			solo.waitForActivity("TabbedOutputActivity", TIMEOUT);
			solo.waitForText("System Summary");
			solo.clickOnMenuItem("Save Calculation");
			solo.waitForText("Calculation Saved.");
			solo.clickOnButton("OK");
			solo.goBackToActivity("UserHomepageActivity");
			solo.waitForText("Welcome");
			break;
		case 2:
			solo.clickOnButton("New");
			solo.waitForActivity("TabbedActivity", TIMEOUT);
			solo.clickOnMenuItem("Save as Template");
			solo.waitForText("Calculation Saved.");
			solo.clickOnButton("OK");
			solo.goBackToActivity("UserHomepageActivity");
			solo.waitForText("Welcome");
			break;
		case 0:
		default:
			solo.clickOnButton("New");
			solo.waitForActivity("TabbedActivity", TIMEOUT);
			solo.clickOnMenuItem("Save Calculation Inputs");
			solo.waitForText("Calculation Saved.");
			solo.clickOnButton("OK");
			solo.goBackToActivity("UserHomepageActivity");
			solo.waitForText("Welcome");
			break;
		}
	}
}

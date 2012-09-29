package au.edu.qut.inn372.greenhat.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import au.edu.qut.inn372.greenhat.bean.UserProfile;
import au.edu.qut.inn372.greenhat.mediator.UserProfileMediator;

/**
 * Activity corresponding to user registration
 *
 */
public class RegistrationActivity extends Activity {
	
	public final static int PASSWORD_ERROR_DIALOG = 0;
	private UserProfile userProfile;
	private UserProfileMediator userProfileMediator;

	@SuppressLint({ "NewApi", "NewApi", "NewApi" })
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        userProfileMediator = new UserProfileMediator();
        userProfile = userProfileMediator.getUserProfile();
    }
	
	/**
	 * Functionality to complete registration
	 * @param view
	 */
	public void register(View view) {
		if (validationCheck()) {
			saveData();
	    	Intent intent = new Intent(this, TabbedActivity.class);
	    	userProfileMediator.saveUserProfile(); //Soap call to register user
	    	intent.putExtra("UserProfile", userProfileMediator.getUserProfile());
	    	startActivity(intent);
		}
	}
	
	/**
	 * Finishes the activity - called by the 'back button' and returns control up the activity stack
	 * @param view
	 */
	public void finish(View view) {
		finish();
	}
	
	
	/**
	 * Saves current input data to the user profile bean
	 */
	private void saveData() {
		userProfile.setName(new String(((EditText)findViewById(R.id.Edit_Registration_Username)).getText().toString()));
		userProfile.setPassword(new String(((EditText)findViewById(R.id.Edit_Registration_Password)).getText().toString()));
		userProfile.setEmail(new String(((EditText)findViewById(R.id.Edit_Registration_Email)).getText().toString()));
		userProfile.setType(((Spinner)findViewById(R.id.user_type_spinner)).getSelectedItemPosition()+1);
	}
	
	/**
	 * Checks that both password fields match
	 */
	private boolean passwordCheck() {
		String passwordFieldOne = ((EditText)findViewById(R.id.Edit_Registration_Password)).getText().toString();
		String passwordFieldTwo = ((EditText)findViewById(R.id.Edit_Registration_Password_Repeat)).getText().toString();
		if(passwordFieldOne.equals(passwordFieldTwo)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Checks that all inputs are valid for registration submissions
	 */
	private boolean validationCheck() {
		boolean valid = true;
		if(!passwordCheck()) { 
			valid = false;
			showDialog(PASSWORD_ERROR_DIALOG);
		}
		//Add more checks here (eg check that email is valid)
		return valid;
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog;
		switch(id) {
		case PASSWORD_ERROR_DIALOG:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Passwords do not match. Please try again.");
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});
			dialog = builder.create();
			break;
		default:
			dialog = null;
			break;
		}
		return dialog;
	}
}

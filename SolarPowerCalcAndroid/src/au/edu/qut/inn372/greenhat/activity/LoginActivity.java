package au.edu.qut.inn372.greenhat.activity;

import java.io.Serializable;

import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import au.edu.qut.inn372.greenhat.bean.UserProfile;
import au.edu.qut.inn372.greenhat.mediator.UserProfileMediator;

public class LoginActivity extends Activity {
	
	public final static int VALIDATION_ERROR = 1;
	private UserProfileMediator userProfileMediator;
	private UserProfile userProfile;
	
	@SuppressLint({ "NewApi", "NewApi", "NewApi" })
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);  
        
        userProfileMediator = new UserProfileMediator();
        userProfile = userProfileMediator.getUserProfile();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
    
    /**
     * Login the user only if the inputs correspond to a user
     */
    public void login (View view){
    	saveData();
    	if(userProfileMediator.validateCredentials()) {
    		Intent intent = new Intent(this, TabbedActivity.class);
    		intent.putExtra("UserProfile", userProfileMediator.getUserProfile());
        	startActivity(intent);
    	}
    	else {
    		showDialog(VALIDATION_ERROR);
    	}
    }
    
    /**
     * Saves data in the input fields into the appropriate bean object
     */
    private void saveData() {
    	userProfile.setEmail(((EditText)findViewById(R.id.editUser)).getText().toString());
    	userProfile.setPassword(((EditText)findViewById(R.id.editPassword)).getText().toString());
    }
    
    /**
     * Starts a registration activity when the user clicks on the register button
     * @param view
     */
    public void register (View view) {
    	Intent intent = new Intent(this, RegistrationActivity.class);
    	startActivity(intent);
    }
    
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog;
		switch(id) {
		case VALIDATION_ERROR:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Username or password is incorrect. Please try again.");
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

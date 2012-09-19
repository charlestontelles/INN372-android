package au.edu.qut.inn372.greenhat.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Activity corresponding to user registration
 *
 */
public class RegistrationActivity extends Activity {

	@SuppressLint({ "NewApi", "NewApi", "NewApi" })
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }
	
	/**
	 * Functionality to complete registration
	 * @param view
	 */
	public void register(View view) {
		//TODO Registration
	}
	
	/**
	 * Finishes the activity - called by the 'back button' and returns control up the activity stack
	 * @param view
	 */
	public void finish(View view) {
		finish();
	}
}

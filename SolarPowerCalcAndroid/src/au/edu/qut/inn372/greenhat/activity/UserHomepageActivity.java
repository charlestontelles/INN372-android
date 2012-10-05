package au.edu.qut.inn372.greenhat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

public class UserHomepageActivity extends Activity {
	
	private TabbedActivity parentTabbedActivity;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);           
        setContentView(R.layout.activity_userhomepage);
        
    }
	
	/**
	 * Refers to the succeeding tab
	 * @param view
	 */
	public void viewNext(View view){
    	int targetActivity = TabbedActivity.LOCATION_ID;
    	parentTabbedActivity.switchTab(targetActivity);
	}
	
	
	
	/**
     * Refers to the preceding Tab
     * @param view
     */
    public void viewBack(View view){	    	
    	Intent intent = new Intent(this, LoginActivity.class);
    	
    	startActivity(intent);
    }

}

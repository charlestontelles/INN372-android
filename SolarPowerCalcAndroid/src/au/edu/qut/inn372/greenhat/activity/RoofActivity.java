package au.edu.qut.inn372.greenhat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RoofActivity extends Activity{
	
	//add this 
	public final static String EXTRA_MESSAGE = "au.edu.qut.inn372.inn372.greenhat.activity.RoofActivity";
	public final static String EXTRA_MESSAGE2 = "au.edu.qut.inn372.inn372.greenhat.activity.RoofActivity2";

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roof_input);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }
	
	/**
	 *
	 * Refers to the succeding tab
	 * @param view
	 */
	public void viewNext(View view){
    	TabbedActivity parentTabbedActivity = (TabbedActivity)this.getParent();
    	int targetActivity = TabbedActivity.LOCATION_ID;
    	parentTabbedActivity.switchTab(targetActivity);
	}
	
	/**
	 * Refers to the preceding Tab
	 * @param view
	 */
	public void viewBack(View view){
    	TabbedActivity parentTabbedActivity = (TabbedActivity)this.getParent();
    	int targetActivity = TabbedActivity.EQUIPMENT_ID;
    	parentTabbedActivity.switchTab(targetActivity);
	}

}

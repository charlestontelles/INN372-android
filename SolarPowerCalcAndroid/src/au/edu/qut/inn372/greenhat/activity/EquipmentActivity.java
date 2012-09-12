package au.edu.qut.inn372.greenhat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EquipmentActivity extends Activity{
	
	//add this 
	public final static String EXTRA_MESSAGE = "au.edu.qut.inn372.inn372.greenhat.activity.EquipmentActivity";
	public final static String EXTRA_MESSAGE2 = "au.edu.qut.inn372.inn372.greenhat.activity.EquipmentActivity2";

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_input);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }
	
	/**
	 * Forward to roof
	 * @param view
	 */
	public void viewRoof(View view){
    	TabbedActivity parentTabbedActivity = (TabbedActivity)this.getParent();
    	int targetActivity = TabbedActivity.ROOF_ID;
    	parentTabbedActivity.switchTab(targetActivity);
	}
	
	/**
	 * Go back to location
	 * @param view
	 */
	public void viewLocation(View view){
    	TabbedActivity parentTabbedActivity = (TabbedActivity)this.getParent();
    	int targetActivity = TabbedActivity.LOCATION_ID;
    	parentTabbedActivity.switchTab(targetActivity);
	}
}

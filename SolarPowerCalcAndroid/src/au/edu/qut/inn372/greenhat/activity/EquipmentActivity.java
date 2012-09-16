package au.edu.qut.inn372.greenhat.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import au.edu.qut.inn372.greenhat.bean.Equipment;

public class EquipmentActivity extends Activity{
	
	//add this 
	public final static String EXTRA_MESSAGE = "au.edu.qut.inn372.inn372.greenhat.activity.EquipmentActivity";
	public final static String EXTRA_MESSAGE2 = "au.edu.qut.inn372.inn372.greenhat.activity.EquipmentActivity2";
	public final static int STATE_NORMAL = 0;
	public final static int STATE_PAUSED = 1;
	private int state;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_input);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }
	
	/**
	 * Refers to the succeding tab
	 * @param view
	 */
	public void viewNext(View view){
    	TabbedActivity parentTabbedActivity = (TabbedActivity)this.getParent();
    	int targetActivity = TabbedActivity.ROOF_ID;
    	parentTabbedActivity.switchTab(targetActivity);
	}
	
	/**
	 * Refers to the preceding Tab
	 * @param view
	 */
	public void viewBack(View view){
    	TabbedActivity parentTabbedActivity = (TabbedActivity)this.getParent();
    	int targetActivity = TabbedActivity.USAGE_ID;
    	parentTabbedActivity.switchTab(targetActivity);
	}
	
	/**
	 * Saves current input data to the calculator bean
	 */
	private void saveData() {

	}
	
	/**
	 * Populates input fields with data in the calculator bean
	 */
	private void loadData() {
		TabbedActivity parentTabbedActivity = (TabbedActivity)this.getParent();
		ArrayList<Equipment> equipmentKits = parentTabbedActivity.getEquipmentKits();
		Spinner equipList = (Spinner)findViewById(R.id.spinnerEquipment_List);
		
		ArrayList<String> spinnerArray = new ArrayList<String>();
		for(Equipment curEquipmentKit : equipmentKits) {
			spinnerArray.add(curEquipmentKit.getKitName());
		}
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
		equipList.setAdapter(spinnerArrayAdapter);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		saveData();
		state = STATE_PAUSED;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		loadData();
		state = STATE_NORMAL;
	}
	
	/**
	 * Retrieves the state of the activity
	 * @return
	 */
	public int getState() {
		return state;
	}
}

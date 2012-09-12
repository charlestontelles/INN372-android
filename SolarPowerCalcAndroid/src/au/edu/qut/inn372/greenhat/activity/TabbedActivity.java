package au.edu.qut.inn372.greenhat.activity;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabbedActivity extends TabActivity {
	
	private TabHost tabHost;
	public static final int LOCATION_ID = 0;
	public static final int EQUIPMENT_ID = 1;
	public static final int ROOF_ID = 2;
	public static final int INPUT_ID = 3;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
 
        tabHost = getTabHost();
        
        addTab("Location", this, LocationActivity.class);
        addTab("Equipment", this, EquipmentActivity.class);
        addTab("Roof", this, RoofActivity.class);
        addTab("Input", this, BasicInputActivity.class);
    }
	
	private void addTab(String tabName, Context context, Class<?> newActivity) {
		TabSpec newSpec = tabHost.newTabSpec(tabName);
		newSpec.setIndicator(tabName);
		Intent newIntent = new Intent(context, newActivity);
		newSpec.setContent(newIntent);
		tabHost.addTab(newSpec);
	}
	
	public void switchTab(int tabID) {
		tabHost.setCurrentTab(tabID);
	}
}

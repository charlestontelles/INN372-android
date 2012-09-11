package au.edu.qut.inn372.greenhat.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabbedActivity extends TabActivity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
 
        TabHost tabHost = getTabHost();
 
        // Tab for Location
        TabSpec locationSpec = tabHost.newTabSpec("Location");
        locationSpec.setIndicator("Location");
        Intent locationIntent = new Intent(this, LocationActivity.class);
        locationSpec.setContent(locationIntent);
 
        // Tab for Equipment
        TabSpec equipmentSpec = tabHost.newTabSpec("Equipment");
        equipmentSpec.setIndicator("Equipment");
        Intent equipmentIntent = new Intent(this, EquipmentActivity.class);
        equipmentSpec.setContent(equipmentIntent);
 
        // Tab for Roof
        TabSpec roofSpec = tabHost.newTabSpec("Roof");
        roofSpec.setIndicator("Roof");
        Intent roofIntent = new Intent(this, RoofActivity.class);
        roofSpec.setContent(roofIntent);
        
        // Tab for Input
        TabSpec inputSpec = tabHost.newTabSpec("Input");
        inputSpec.setIndicator("Input");
        Intent inputIntent = new Intent(this, BasicInputActivity.class);
        inputSpec.setContent(inputIntent);
 
        // Adding all TabSpec to TabHost
        tabHost.addTab(locationSpec);
        tabHost.addTab(equipmentSpec);
        tabHost.addTab(roofSpec);
        tabHost.addTab(inputSpec);
    }
}

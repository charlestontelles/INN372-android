package au.edu.qut.inn372.greenhat.activity;

import java.util.ArrayList;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import au.edu.qut.inn372.greenhat.bean.Calculator;
import au.edu.qut.inn372.greenhat.bean.Equipment;
import au.edu.qut.inn372.greenhat.mediator.CalculatorMediator;
import au.edu.qut.inn372.greenhat.mediator.EquipmentKitsMediator;

public class TabbedActivity extends TabActivity {
	
	private TabHost tabHost;
	public static final int LOCATION_ID = 0;
	public static final int USAGE_ID = 1;
	public static final int EQUIPMENT_ID = 2;
	public static final int ROOF_ID = 3;
	public static final int INPUT_ID = 4;
	
	private CalculatorMediator calcMediator;
	private EquipmentKitsMediator equipKitsMediator;
	
	
	/**
	 * Constructor - sets up tabs
	 */
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
        
        calcMediator = new CalculatorMediator();
        equipKitsMediator = new EquipmentKitsMediator();
        equipKitsMediator.getEquipments(); //This is a WS call - might be better to move it to be part of the login process later
 
        tabHost = getTabHost();
        
        addTab("Location", this, LocationActivity.class); 
        addTab("Personal Usage", this, CustomerUsageActivity.class);
        addTab("Equipment", this, EquipmentActivity.class);
        addTab("Banks", this, RoofActivity.class); 
        addTab("Input", this, BasicInputActivity.class);
        
    }
	
	/**
	 * Creates a new tab
	 * @param tabName The name to be displayed for the tab
	 * @param context The context (should be 'this')
	 * @param newActivity The activity class to start (eg 'LocationActivity.class')
	 */
	private void addTab(String tabName, Context context, Class<?> newActivity) {
		TabSpec newSpec = tabHost.newTabSpec(tabName);
		newSpec.setIndicator(tabName);
		Intent newIntent = new Intent(context, newActivity);
		newSpec.setContent(newIntent);
		tabHost.addTab(newSpec);
	}
	
	/**
	 * Retrieves the calculator bean object
	 * @return The calculator bean
	 */
	public Calculator getCalculator() {
		return calcMediator.getCalculator();
	}
	
	public ArrayList<Equipment> getEquipmentKits() {
		return equipKitsMediator.getEquipmentKits();
	}
	
	public void calcEnergyProduction() {
		calcMediator.calcEnergyProduction();
	}
	
	/**
	 * Switches to the specified tab
	 * @param tabID ID of the tab to be switched to - ID's are public fields for this class
	 */
	public void switchTab(int tabID) {
		tabHost.setCurrentTab(tabID);
	}
}

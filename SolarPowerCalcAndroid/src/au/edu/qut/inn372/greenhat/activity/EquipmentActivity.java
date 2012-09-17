package au.edu.qut.inn372.greenhat.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import au.edu.qut.inn372.greenhat.bean.Calculator;
import au.edu.qut.inn372.greenhat.bean.Equipment;
import au.edu.qut.inn372.greenhat.bean.Roof;

public class EquipmentActivity extends Activity implements OnItemSelectedListener{
	
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
        Spinner spinner = (Spinner)findViewById(R.id.spinnerEquipment_List);
		spinner.setOnItemSelectedListener(this); 
        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }
	
	/**
	 * Refers to the succeeding tab
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
	 * Refresh the values of Systemcost, -size & Inverterefficiency after the Spinner was used
	 */
	public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
		
		TabbedActivity parentTabbedActivity = (TabbedActivity)this.getParent();
		ArrayList<Equipment> equipmentKits = parentTabbedActivity.getEquipmentKits();
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
		Calculator calculator = parentTabbedActivity.getCalculator();
        //Number of Panels
        TextView numberOfPanels = (TextView)findViewById(R.id.textEquipment_ViewPanelNumber);
        numberOfPanels.setText(new Integer(equipmentKits.get(pos).getTotalPanels()).toString());
        //System Cost
        TextView systemCost = (TextView)findViewById(R.id.textEquipment_ViewSystemCost);
        systemCost.setText(new Double(equipmentKits.get(pos).getCost()).toString());
        //System Size
        TextView systemSize = (TextView)findViewById(R.id.textEquipment_ViewSystemSize);
        systemSize.setText(new Double(equipmentKits.get(pos).getSize()).toString());
        //Inverter Efficieny
        TextView inverterEfficieny = (TextView)findViewById(R.id.textEquipment_ViewInverterEfficieny);
        inverterEfficieny.setText(new Double(equipmentKits.get(pos).getInverter().getEfficiency()).toString());
        
        TextView roofSizeCheck = (TextView)findViewById(R.id.textEquipment_RoofSizeCheck);
        roofSizeCheck.setText(panelsMessage(equipmentKits.get(pos).getTotalPanels()));
    }
	
	/**
	 * Must be implemented because of the interface
	 */
	public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
	
	/**
	 * Saves current input data to the calculator bean
	 */
	private void saveData() {
		TabbedActivity parentTabbedActivity = (TabbedActivity)this.getParent();
		ArrayList<Equipment> equipmentKits = parentTabbedActivity.getEquipmentKits();
		Calculator calculator = parentTabbedActivity.getCalculator();
		Spinner equipList = (Spinner)findViewById(R.id.spinnerEquipment_List);
		
		calculator.setEquipment(equipmentKits.get(equipList.getSelectedItemPosition()));
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
		
		equipList.setSelection(getEquipmentKitPosition(parentTabbedActivity.getCalculator().getEquipment().getKitName()));
		
		
        //Number of Panels
        TextView numberOfPanels = (TextView)findViewById(R.id.textEquipment_ViewPanelNumber);
        numberOfPanels.setText(new Integer(equipmentKits.get(equipList.getSelectedItemPosition()).getTotalPanels()).toString());
        
        //System Cost
        TextView systemCost = (TextView)findViewById(R.id.textEquipment_ViewSystemCost);
        systemCost.setText(new Double(equipmentKits.get(equipList.getSelectedItemPosition()).getCost()).toString());
       
        //System Size
        TextView systemSize = (TextView)findViewById(R.id.textEquipment_ViewSystemSize);
        systemSize.setText(new Double(equipmentKits.get(equipList.getSelectedItemPosition()).getSize()).toString());
       
        //Inverter Efficieny
        TextView inverterEfficieny = (TextView)findViewById(R.id.textEquipment_ViewInverterEfficieny);
        inverterEfficieny.setText(new Double(equipmentKits.get(equipList.getSelectedItemPosition()).getInverter().getEfficiency()).toString());
        
	}
	
	/**
	 * Returns the position in equipmentKits for the kit with the provided name
	 */
	private int getEquipmentKitPosition(String kitName) {
		TabbedActivity parentTabbedActivity = (TabbedActivity)this.getParent();
		ArrayList<Equipment> equipmentKits = parentTabbedActivity.getEquipmentKits();
		for(int i = 0; i < equipmentKits.size(); i++) {
			if(equipmentKits.get(i).getKitName().compareTo(kitName)==0) {
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * Returns a message stating whether all the panels fit on the roof or not
	 */
	private String panelsMessage(int numPanels) {
		saveData();
		TabbedActivity parentTabbedActivity = (TabbedActivity)this.getParent();
		Calculator calculator = parentTabbedActivity.getCalculator();
		Roof roof = calculator.getCustomer().getLocation().getRoof();
		double roofSize = (roof.getHeight() * roof.getWidth())/10000;
		double totalPanelSize = calculator.getEquipment().getPanels().get(0).getSize()*calculator.getEquipment().getTotalPanels();
		if(roofSize > totalPanelSize) {
			return "Panels will fit on your roof";
		}
		else {
			return "Panels will NOT fit on your roof";
		}
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

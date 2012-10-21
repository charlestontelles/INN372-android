package au.edu.qut.inn372.greenhat.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import au.edu.qut.inn372.greenhat.bean.Calculation;
import au.edu.qut.inn372.greenhat.bean.Calculator;
import au.edu.qut.inn372.greenhat.bean.Equipment;
import au.edu.qut.inn372.greenhat.bean.Panel;
import au.edu.qut.inn372.greenhat.bean.UserProfile;
import au.edu.qut.inn372.greenhat.mediator.CalculatorMediator;
import au.edu.qut.inn372.greenhat.mediator.EquipmentKitsMediator;
import au.edu.qut.inn372.greenhat.mediator.PanelMediator;

public class TabbedActivity extends TabActivity {
	
	private TabHost tabHost;
	
	public static final int LOCATION_ID = 0;
	public static final int USAGE_ID = 1;
	public static final int EQUIPMENT_ID = 2;
	public static final int ROOF_ID = 3;
	public static final int INPUT_ID = 4;
	public static final int MIN_TAB = LOCATION_ID;
	public static final int MAX_TAB = INPUT_ID;
	private static final int DIALOG_ALERT = 10;
	private static final int DIALOG_FAILED = 11;
	
	private CalculatorMediator calcMediator;
	private EquipmentKitsMediator equipKitsMediator;
	private PanelMediator panelMediator;

	private int currentTab;
	/**
	 * Constructor - sets up tabs
	 */
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
        
        equipKitsMediator = new EquipmentKitsMediator();
        equipKitsMediator.getEquipments(); //This is a WS call - might be better to move it to be part of the login process later
        panelMediator = new PanelMediator(); //WS call
        panelMediator.getPanels();
        
        //Switch to check for new or old calculator
        int type = (Integer) getIntent().getSerializableExtra("Type");
        calcMediator = new CalculatorMediator();
        switch(type){
        	case 1: //load a calculation
        		calcMediator.setCalculator((Calculator)getIntent().getSerializableExtra("Calculator"));
        		//If the calculation is complete, go to the output screen and calculate the result
        		if(calcMediator.getCalculator().getStatus() == 1) {
        			//if it is a complete calculation, go straight to results
        			calculate();
        		}
        		if(calcMediator.getCalculator().getStatus() == 2) {
        			//if it is a template, delete the key so when saving it will save a new calculation
        			calcMediator.getCalculator().setKey(null);
        			calcMediator.getCalculator().setName(null);
        			calcMediator.getCalculator().setStatus(0);
        		}
        		break;
        	case 0: //new calculation
        	default:
        		setupCalculatorDefaults();
        		//TODO Add user profile
        		break;
        }

        UserProfile userProfile = (UserProfile)getIntent().getSerializableExtra("UserProfile");
        calcMediator.getCalculator().getCustomer().setUserProfile(userProfile);
        
        tabHost = getTabHost();
        
        //setupCalculatorDefaults();
        
        
        addTab("Location", this, LocationActivity.class); 
        addTab("Personal Usage", this, CustomerUsageActivity.class);
        addTab("Equipment", this, EquipmentActivity.class);
        addTab("Banks", this, RoofActivity.class); 
        addTab("Summary", this, BasicInputActivity.class);
        
        currentTab = MIN_TAB;
    }
	
	/**
	 * Initialise default values - In future this will become a WS call that loads an extra calculator bean filled with defaults
	 */
	public void setupCalculatorDefaults() {
		getCalculator().getCustomer().getLocation().setCity("Brisbane");
		getCalculator().getCustomer().getLocation().setSunLightHours(4.5);
		getCalculator().getCustomer().getLocation().getRoof().setWidth(1000);
		getCalculator().getCustomer().getLocation().getRoof().setHeight(1500);
		getCalculator().getCustomer().getElectricityUsage().setDailyAverageUsage(40);
		getCalculator().getCustomer().getElectricityUsage().setDayTimeHourlyUsage(1);
		getCalculator().getCustomer().getTariff().setTariff11Fee(0.1941);
		getCalculator().getCustomer().getTariff().setFeedInfee(0.50);
		getCalculator().setEquipment(getEquipmentKits().get(0));
		getCalculator().getCustomer().getTariff().setAnnualTariffIncrease(1); // Probably shouldn't need this
		getCalculator().getCustomer().getTariff().setTariffFeePerYear(1); //Probably shouldn't need this
		getCalculator().getCustomer().getLocation().getRoof().getBanks().get(0).setNumberOfPanels(1);
		getCalculator().getCustomer().getLocation().getRoof().getBanks().get(1).setNumberOfPanels(1);
		getCalculator().getCustomer().getLocation().getRoof().getBanks().get(0).setAngle(45);
		getCalculator().getCustomer().getLocation().getRoof().getBanks().get(1).setAngle(45);
		getCalculator().getCustomer().getLocation().getRoof().getBanks().get(0).setSelectedOrientation("North");
		getCalculator().getCustomer().getLocation().getRoof().getBanks().get(1).setSelectedOrientation("West");
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
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_tabbed_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
        	case R.id.menu_tabbed_save:
        		saveCalculation();
        		return true;
        	case R.id.menu_tabbed_save_template:
        		String oldName = calcMediator.getCalculator().getName();
        		String oldKey = calcMediator.getCalculator().getKey();
        		int oldStatus = calcMediator.getCalculator().getStatus();
        		calcMediator.getCalculator().setName(null);
        		calcMediator.getCalculator().setKey(null);
        		calcMediator.getCalculator().setStatus(2);//template status
        		saveCalculation();
        		calcMediator.getCalculator().setName(oldName);
        		calcMediator.getCalculator().setKey(oldKey);
        		calcMediator.getCalculator().setStatus(oldStatus);
        		return true;
        	default:
        		return super.onOptionsItemSelected(item);
    	}
    }
	
	/**
	 * Customises the dialogs used in the Activity
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		Builder builder;
		AlertDialog dialog;
		switch (id) {
		case DIALOG_ALERT:
			// Create out AlterDialog
			builder = new AlertDialog.Builder(this);
			builder.setMessage("Calculation Saved.");
			builder.setCancelable(false);
			builder.setPositiveButton("OK", new OkOnClickListener());
			dialog = builder.create();
			dialog.show();
			break;
		case DIALOG_FAILED:
			builder = new AlertDialog.Builder(this);
			builder.setMessage("Error saving calculation");
			builder.setCancelable(false);
			builder.setPositiveButton("OK", new OkOnClickListener());
			dialog = builder.create();
			dialog.show();
			break;
		}
		return super.onCreateDialog(id);
	}
	/**
	 * handle on click button in the dialog
	 * 
	 */
	private final class OkOnClickListener implements
			DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	}
    
	/**
	 * Retrieves the calculator bean object
	 * @return The calculator bean
	 */
	public Calculator getCalculator() {
		return calcMediator.getCalculator();
	}
	
	/**
	 * Retrieve the list of equipments obtained from the WS call
	 * @return
	 */
	public ArrayList<Equipment> getEquipmentKits() {
		return equipKitsMediator.getEquipmentKits();
	}
	
	/**
	 * Perform the WS call to calculate energy production (and financial output)
	 */
	public void calcEnergyProduction() {
		calcMediator.calcEnergyProduction();
		calcMediator.getCalculator().setStatus(1);
	}
	
	public void calculate() {
		calcEnergyProduction();
		
		List<Calculator> calculatorResultList = new ArrayList<Calculator>();
		calculatorResultList.add(getCalculator());

		Intent intent = new Intent(this, TabbedOutputActivity.class);
		intent.putExtra("Calculators", (Serializable)calculatorResultList);

		startActivityForResult(intent, 1); //Allows us to retrieve the save calculation key if the user saves the results then hits back
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//allow us to get the calculator key if the user saved in the output
		if (requestCode == 1) {
			if(resultCode == 1) {
				getCalculator().setKey(data.getStringExtra("key"));
			}
		}
	}
	
	/**
	 * Perform the WS call to save the calculation
	 */
	public void saveCalculation() {
		//TODO: call the selected tab save data method
		((InputActivity)getLocalActivityManager().getCurrentActivity()).saveData(); //force child tab to 'save'
		String result = calcMediator.saveCalculation();
		if(result.equals("ok")) {
			showDialog(DIALOG_ALERT);
		}
		else {
			showDialog(DIALOG_FAILED);
		}
	}
	
	/**
	 * Switches to the specified tab
	 * @param tabID ID of the tab to be switched to - ID's are public fields for this class
	 */
	public void switchTab(int tabID) {
		tabHost.setCurrentTab(tabID);
		setTabId(tabID);
	}
	
	public void setTabId(int tabID) {
		currentTab = tabID;
		correctButtonLabels();
	}
	
	public void clickTabLeft(View view) {
		if(currentTab > MIN_TAB) {
			switchTab(currentTab-1);
		}
	}
	
	public void clickTabRight(View view) {
		if(currentTab < MAX_TAB) {
			switchTab(currentTab+1);
		}
	}
	
	private void correctButtonLabels() {
		Button leftButton = (Button)findViewById(R.id.inputButtonLeft);
		Button rightButton = (Button)findViewById(R.id.inputButtonRight);
		if(currentTab>MIN_TAB) {
			leftButton.setText(R.string.tab_left);
		}
		else {
			leftButton.setText("");
		}
		if(currentTab<MAX_TAB) {
			rightButton.setText(R.string.tab_right);
		}
		else {
			rightButton.setText("");
		}
	}
	
	/**
	 * Retrieve the list of panels obtained from the WS call
	 * @return
	 */
	public ArrayList<Panel> getPanels() {
		return panelMediator.getPanelList();
	}
	
	
}

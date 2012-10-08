package au.edu.qut.inn372.greenhat.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
	public static final int USERHOMEPAGE_ID = 0;
	public static final int LOCATION_ID = 1;
	public static final int USAGE_ID = 2;
	public static final int EQUIPMENT_ID = 3;
	public static final int ROOF_ID = 4;
	public static final int INPUT_ID = 5;
	
	private CalculatorMediator calcMediator;
	private EquipmentKitsMediator equipKitsMediator;
	private PanelMediator panelMediator;
	/**
	 * Constructor - sets up tabs
	 */
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
        
        calcMediator = new CalculatorMediator();
        equipKitsMediator = new EquipmentKitsMediator();
        equipKitsMediator.getEquipments(); //This is a WS call - might be better to move it to be part of the login process later
        panelMediator = new PanelMediator(); //WS call
        panelMediator.getPanels();
        
        UserProfile userProfile = (UserProfile)getIntent().getSerializableExtra("UserProfile");
        calcMediator.getCalculator().getCustomer().setUserProfile(userProfile);
        
        tabHost = getTabHost();
        
        setupCalculatorDefaults();
        
        addTab("Home", this, UserHomepageActivity.class);
        addTab("Location", this, LocationActivity.class); 
        addTab("Personal Usage", this, CustomerUsageActivity.class);
        addTab("Equipment", this, EquipmentActivity.class);
        addTab("Banks", this, RoofActivity.class); 
        addTab("Summary", this, BasicInputActivity.class);
        
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
	}
	
	/**
	 * Perform the WS call to save the calculation
	 */
	public String saveCalculation() {
		return calcMediator.saveCalculation();
	}
	
	/**
	 * Switches to the specified tab
	 * @param tabID ID of the tab to be switched to - ID's are public fields for this class
	 */
	public void switchTab(int tabID) {
		tabHost.setCurrentTab(tabID);
	}
	
	/**
	 * Retrieve the list of panels obtained from the WS call
	 * @return
	 */
	public ArrayList<Panel> getPanels() {
		return panelMediator.getPanelList();
	}
	
	/**
	 * Retrieve the list of calculations obtained from the WS call
	 */
	public List<Calculator> getCalculations(){
		return calcMediator.getCalculationList();
	}
	
	
}

package au.edu.qut.inn372.greenhat.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
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
import au.edu.qut.inn372.greenhat.bean.Inverter;
import au.edu.qut.inn372.greenhat.bean.Location;
import au.edu.qut.inn372.greenhat.bean.Panel;
import au.edu.qut.inn372.greenhat.bean.Roof;

public class EquipmentActivity extends Activity implements OnItemSelectedListener{
	
	public final static int STATE_NORMAL = 0;
	public final static int STATE_PAUSED = 1;
	public final static int DEFAULT = 0;
	private int state;
	private int equipKitPos = 0;
	private List<Inverter> inverters = new ArrayList<Inverter>();

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);           
        setContentView(R.layout.activity_equipment_input);
        Spinner spinner = (Spinner)findViewById(R.id.spinnerEquipment_List);
		spinner.setOnItemSelectedListener(this); 
		Spinner panelBrandSpinner = (Spinner)findViewById(R.id.spinnerRoof__panelBrand);
		panelBrandSpinner.setOnItemSelectedListener(this);
		setupInverterBrands();
		setupSpinner();
    }
	
	private void setupInverterBrands(){
		Inverter bPSolarInverter = new Inverter();
		bPSolarInverter.setId(1);
		bPSolarInverter.setBrand("BP Solar Inverters");
		bPSolarInverter.setCost(500);
		bPSolarInverter.setEfficiency(85);
		bPSolarInverter.setEfficiencyLoss(0.5);
		
		Inverter sharpSolarInverter = new Inverter();
		sharpSolarInverter.setId(1);
		sharpSolarInverter.setBrand("Sharp Solar Inverters");
		sharpSolarInverter.setCost(600);
		sharpSolarInverter.setEfficiency(95);
		sharpSolarInverter.setEfficiencyLoss(0.7);
		
		Inverter sunlinqSolarInverter = new Inverter();
		sunlinqSolarInverter.setId(1);
		sunlinqSolarInverter.setBrand("Sunlinq Portable Solar Inverters");
		sunlinqSolarInverter.setCost(400);
		sunlinqSolarInverter.setEfficiency(93);
		sunlinqSolarInverter.setEfficiencyLoss(0.7);
		
		Inverter sunPowerSolarInverter = new Inverter();
		sunPowerSolarInverter.setId(1);
		sunPowerSolarInverter.setBrand("SunPower Solar Inverters");
		sunPowerSolarInverter.setCost(400);
		sunPowerSolarInverter.setEfficiency(95);
		sunPowerSolarInverter.setEfficiencyLoss(0.8);
		
		Inverter sunTechSolarInverter = new Inverter();
		sunTechSolarInverter.setId(1);
		sunTechSolarInverter.setBrand("SunTech Solar Inverters");
		sunTechSolarInverter.setCost(400);
		sunTechSolarInverter.setEfficiency(95);
		sunTechSolarInverter.setEfficiencyLoss(0.7);
		
		Inverter powerFirmSolarPanel = new Inverter();
		powerFirmSolarPanel.setId(1);
		powerFirmSolarPanel.setBrand("Powerfilm Flexible Solar Inverters");
		powerFirmSolarPanel.setCost(400);
		powerFirmSolarPanel.setEfficiency(97);
		powerFirmSolarPanel.setEfficiencyLoss(0.7);

		Inverter sanyoSolarPanel = new Inverter();
		sanyoSolarPanel.setId(1);
		sanyoSolarPanel.setBrand("Sanyo Solar Inverters");
		sanyoSolarPanel.setCost(450);
		sanyoSolarPanel.setEfficiency(95);
		sanyoSolarPanel.setEfficiencyLoss(0.8);
		
		Inverter globalSolarPanel = new Inverter();
		globalSolarPanel.setId(1);
		globalSolarPanel.setBrand("Global Solar Inverters");
		globalSolarPanel.setCost(400);
		globalSolarPanel.setEfficiency(99);
		globalSolarPanel.setEfficiencyLoss(0.7);
		
		Inverter solarFunSolarPanel = new Inverter();
		solarFunSolarPanel.setId(1);
		solarFunSolarPanel.setBrand("Solarfun Inverters");
		solarFunSolarPanel.setCost(400);
		solarFunSolarPanel.setEfficiency(95);
		solarFunSolarPanel.setEfficiencyLoss(0.7);
		
		Inverter recSolarPanel = new Inverter();
		recSolarPanel.setId(1);
		recSolarPanel.setBrand("REC Solar Inverters");
		recSolarPanel.setCost(400);
		recSolarPanel.setEfficiency(95);
		recSolarPanel.setEfficiencyLoss(0.7);
	    
		inverters.add(bPSolarInverter);
		inverters.add(sharpSolarInverter);
		inverters.add(sunlinqSolarInverter);
		inverters.add(sunPowerSolarInverter);
		inverters.add(sunTechSolarInverter);
		inverters.add(powerFirmSolarPanel);
		inverters.add(sanyoSolarPanel);
		inverters.add(globalSolarPanel);
		inverters.add(solarFunSolarPanel);
		inverters.add(recSolarPanel);
	}
	
	/**
     * Sets up the inverter spinner with a list of brands
     */
    private void setupSpinner() {
		Spinner inverterSpinner = (Spinner)findViewById(R.id.spinnerRoof_inverterBrand);

		ArrayList<String> spinnerArray = new ArrayList<String>();
		for(Inverter curInverter : inverters) {
			spinnerArray.add(curInverter.getBrand());
		}
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
		inverterSpinner.setAdapter(spinnerArrayAdapter);
		inverterSpinner.setOnItemSelectedListener(this); 
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
	 * Refresh the values of System cost, -size & Inverter efficiency after the Spinner was used
	 */
	public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
		
		TabbedActivity parentTabbedActivity = (TabbedActivity)this.getParent();
		Calculator calculator = parentTabbedActivity.getCalculator();
		ArrayList<Equipment> equipmentKits = parentTabbedActivity.getEquipmentKits();
		
	    Spinner spnir = (Spinner)parent; 
	    if(spnir.getId() == R.id.spinnerEquipment_List){ 
	    	
	    	equipKitPos = pos;

	        //Number of Panels
	        TextView numberOfPanels = (TextView)findViewById(R.id.textEquipment_ViewPanelNumber);
	        numberOfPanels.setText(new Integer(equipmentKits.get(equipKitPos).getTotalPanels()).toString());
	        
	        //System Cost
	        TextView systemCost = (TextView)findViewById(R.id.textEquipment_ViewSystemCost);
	        systemCost.setText(new Double(equipmentKits.get(equipKitPos).getCost()).toString());
	        
	        //System Size
	        TextView systemSize = (TextView)findViewById(R.id.textEquipment_ViewSystemSize);
	        systemSize.setText(new Double(equipmentKits.get(equipKitPos).getSize()).toString());
	        
	        //Inverter Efficiency
	        TextView inverterEfficieny = (TextView)findViewById(R.id.textEquipment_ViewInverterEfficieny);
	        inverterEfficieny.setText(new Double(equipmentKits.get(equipKitPos).getInverter().getEfficiency()).toString());
	        
	        TextView roofSizeCheck = (TextView)findViewById(R.id.textEquipment_RoofSizeCheck);
	        roofSizeCheck.setText(panelsMessage(equipmentKits.get(equipKitPos).getTotalPanels()));
	
	        
	        String bank1AssignPanelsNumber = (new Integer(calculator.getEquipment().getTotalPanels()).toString());
			int panelConversionToInt = Integer.parseInt(bank1AssignPanelsNumber);
	
			calculator.getCustomer().getLocation().getRoof().getBanks().get(0).setNumberOfPanels(panelConversionToInt);
			calculator.getCustomer().getLocation().getRoof().getBanks().get(1).setNumberOfPanels(DEFAULT);
	    } 
	    else if(spnir.getId() == R.id.spinnerRoof__panelBrand) {
     		ArrayList<Panel> panels = parentTabbedActivity.getPanels();
		 	
     		for (Panel panel : panels) {
     			if (panel.getBrand().equalsIgnoreCase(spnir.getSelectedItem().toString())){
     				for(int index=0; index < panels.size(); index++){
     					calculator.getEquipment().getPanels().set(index, panel);
     				}
     			calculator.getEquipment().setCost(calculator.getEquipment().getTotalPanels() * panel.getCost() + calculator.getEquipment().getInverter().getCost());
     			}
     		}
     		
	        //System Cost
	        TextView systemCost = (TextView)findViewById(R.id.textEquipment_ViewSystemCost);
	        systemCost.setText(new Double(equipmentKits.get(equipKitPos).getCost()).toString());
	    }
	    else if(spnir.getId() == R.id.spinnerRoof_inverterBrand){
	    	TextView inverterEfficiency = (TextView)findViewById(R.id.textEquipment_ViewInverterEfficieny);
	    	inverterEfficiency.setText(new Double(getInverters(pos)).toString());
	    }
    }
	
	
	private double getInverters(int pos) {
		return inverters.get(pos).getEfficiency();
	}
	
	/**
	 * Must be implemented because of the interface - unused
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
		
		//Set spinner to correct position
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
        
        //int number = equipList.getSelectedItemPosition();
        
        //EditText bank1NumberOfPanels = (EditText)findViewById(R.id.editRoof_Banks_PanelsBank1);
        //bank1NumberOfPanels.setText(new Integer(equipmentKits.get(equipList.getSelectedItemPosition()).getTotalPanels()).toString());
        //Calculator calculator = parentTabbedActivity.getCalculator();
        //calculator.getCustomer().getLocation().getRoof().getBanks().get(0).setNumberOfPanels(number);
        
        
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

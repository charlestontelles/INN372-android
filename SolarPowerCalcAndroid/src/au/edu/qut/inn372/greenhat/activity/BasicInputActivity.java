package au.edu.qut.inn372.greenhat.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.support.v4.app.NavUtils;
import au.edu.qut.inn372.greenhat.bean.Bank;
import au.edu.qut.inn372.greenhat.bean.Calculation;
import au.edu.qut.inn372.greenhat.bean.Calculator;
import au.edu.qut.inn372.greenhat.bean.Customer;
import au.edu.qut.inn372.greenhat.bean.Location;
import au.edu.qut.inn372.greenhat.mediator.CalculatorMediator;

public class BasicInputActivity extends Activity {

	//add this 
	public final static String EXTRA_MESSAGE = "au.edu.qut.inn372.inn372.greenhat.activity.BasicInputActivity";
	public final static String EXTRA_MESSAGE2 = "au.edu.qut.inn372.inn372.greenhat.activity.BasicInputActivity2";
	public final static int STATE_NORMAL = 0;
	public final static int STATE_PAUSED = 1;
	private int state;
	
	private TabbedActivity parentTabbedActivity;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_input);
        parentTabbedActivity = (TabbedActivity)this.getParent();
        state = STATE_NORMAL;
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_basic_input, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void calculate(View view){
    	saveData();
    	
    	parentTabbedActivity.calcEnergyProduction();
    	
    	Intent intent = new Intent(this, TabbedOutputActivity.class);
    	intent.putExtra("Calculator", parentTabbedActivity.getCalculator());
    	
    	startActivity(intent);
    
    }
    
    public void reset(View view){
    	/*
		// Equipment
		((EditText)findViewById(R.id.editEquipment_Size)).setText("4.5");
		((EditText)findViewById(R.id.editEquiment_InverterEfficiency)).setText("90.0");
		// Roof
		((EditText)findViewById(R.id.editRoof_LossNorth)).setText("5.0");
		((EditText)findViewById(R.id.editRoof_LossWest)).setText("15.0");
		((EditText)findViewById(R.id.editRoof_PercentageNorth)).setText("90.0");
		((EditText)findViewById(R.id.editRoof_PercentageWest)).setText("10.0");
		// Location (day light hours)
		((EditText)findViewById(R.id.editSunlight_Daylight)).setText("4.5");
		// Current usage
		((EditText)findViewById(R.id.editUser_UsagePerDay)).setText("50");
		*/
    }

	/**
	 * Saves current input data to the calculator bean
	 */
	private void saveData() {
		
		Calculator calculator = parentTabbedActivity.getCalculator();
		//Current Energy User Details
		calculator.getCustomer().getElectricityUsage().setDailyAverageUsage(new Double(((EditText)findViewById(R.id.editRoof_Usage_UsagePerDay)).getText().toString()));
		calculator.getCustomer().getElectricityUsage().setDayTimeHourlyUsage(new Double(((EditText)findViewById(R.id.editRoof_Usage_UsagePerDaylight)).getText().toString()));
		calculator.getCustomer().getTariff().setFeedInfee(new Double(((EditText)findViewById(R.id.editRoof_Usage_FeedInFee)).getText().toString()));
		calculator.getCustomer().getTariff().setAnnualTariffIncrease(new Double(((EditText)findViewById(R.id.editRoof_Usage_FeeIncrease)).getText().toString()));
		calculator.getCustomer().getTariff().setTariff11Fee(new Double(((EditText)findViewById(R.id.editRoof_Usage_Tariff)).getText().toString()));
		//Roof
		
		//Sunlight Details
		calculator.getCustomer().getLocation().setSunLightHours(new Double(((EditText)findViewByIds(R.id.editRoof_Sunlight_Daylight)).getText().toString()));
		
		testData();
	}
	
	/**
	 * Populates input fields with data in the calculator bean
	 */
	private void loadData() {
		Calculator calculator = parentTabbedActivity.getCalculator();
		//Current Energy User Details
		EditText inputDailyAverage = (EditText)findViewById(R.id.editRoof_Usage_UsagePerDay);
		inputDailyAverage.setText(new Double(calculator.getCustomer().getElectricityUsage().getDailyAverageUsage()).toString());
		
		EditText inputTimeHourlyUsage = (EditText)findViewById(R.id.editRoof_Usage_UsagePerDaylight);
		inputTimeHourlyUsage.setText(new Double(calculator.getCustomer().getElectricityUsage().getDayTimeHourlyUsage()).toString());
		
		EditText inputFeedInFee = (EditText)findViewById(R.id.editRoof_Usage_FeedInFee);
		inputFeedInFee.setText(new Double(calculator.getCustomer().getTariff().getFeedInfee()).toString());
		
		EditText inputAnnualTariffIncrease = (EditText)findViewById(R.id.editRoof_Usage_FeeIncrease);
		inputAnnualTariffIncrease.setText(new Double(calculator.getCustomer().getTariff().getAnnualTariffIncrease()).toString());
		
		EditText inputAnnualTariff11Cost = (EditText)findViewById(R.id.editRoof_Usage_Tariff);
		inputAnnualTariff11Cost.setText(new Double(calculator.getCustomer().getTariff().getTariff11Fee()).toString());
		
		//Equipment
		EditText inputSystemSize = (EditText)findViewById(R.id.editEquipment_Size);
		//TODO loading functionality
		EditText inputInverterEfficiency = (EditText)findViewById(R.id.editEquiment_InverterEfficiency);
		//TODO loading functionality
		
		//Roof
		
		//Location
		
		//Sunlight Details
		EditText inputDailyHours = (EditText)findViewById(R.id.editRoof_Sunlight_Daylight);
		inputDailyHours.setText(new Double(calculator.getCustomer().getLocation().getSunLightHours()).toString());
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
	
	private void testData() {
		
		Calculator calculator = parentTabbedActivity.getCalculator();
		calculator.setEquipment(parentTabbedActivity.getEquipmentKits().get(0));
		Customer customer = calculator.getCustomer();
		customer.getElectricityUsage().setDailyAverageUsage(3);
		customer.getElectricityUsage().setDayLightElectricityUsage(3);
		customer.getElectricityUsage().setDayTimeHourlyUsage(3);
		Location location = customer.getLocation();
		location.setCity("Brisbane");
		location.setRegion("QLD");
		location.setSunLightHours(8);
		ArrayList<Bank> banks = new ArrayList<Bank>();
		Bank bank1 = new Bank();
		bank1.setAngle(45);
		bank1.setAngleEfficiencyLoss(5);
		bank1.setBankId(0);
		bank1.setEfficiency(6);
		bank1.setNumberOfPanels(3);
		bank1.setOrientation(10);
		bank1.setOrientationEfficiencyLoss(10);
		bank1.setPowerOutput(500);
		bank1.setSelectedOrientation("North");
		Bank bank2 = new Bank();
		bank2.setAngle(45);
		bank2.setAngleEfficiencyLoss(5);
		bank2.setBankId(0);
		bank2.setEfficiency(6);
		bank2.setNumberOfPanels(3);
		bank2.setOrientation(10);
		bank2.setOrientationEfficiencyLoss(10);
		bank2.setPowerOutput(500);
		bank2.setSelectedOrientation("West");
		banks.add(bank1);
		banks.add(bank2);
		location.getRoof().setBanks(banks);
		location.getRoof().setEfficiencyLossNorth(50);
		location.getRoof().setEfficiencyLossWest(30);
		location.getRoof().setHeight(3);
		location.getRoof().setPercentageNorth(90);
		location.getRoof().setPercentageWest(10);
		location.getRoof().setWidth(2);
		customer.getTariff().setAnnualTariffIncrease(1);
		customer.getTariff().setFeedInfee(2);
		customer.getTariff().setTariff11Fee(3);
		customer.getTariff().setTariffFeePerYear(4);
		
	}
	
}

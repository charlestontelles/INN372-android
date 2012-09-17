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
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import au.edu.qut.inn372.greenhat.bean.Bank;
import au.edu.qut.inn372.greenhat.bean.Calculation;
import au.edu.qut.inn372.greenhat.bean.Calculator;
import au.edu.qut.inn372.greenhat.bean.Customer;
import au.edu.qut.inn372.greenhat.bean.Location;
import au.edu.qut.inn372.greenhat.bean.Roof;
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
    	loadData();
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
		calculator.getCustomer().getTariff().setTariff11Fee(new Double(((EditText)findViewById(R.id.editRoof_Usage_Tariff)).getText().toString()));
		//Roof
		EditText bank1AngleView = (EditText)findViewById(R.id.editRoof_Bank1_Angle);
		TextView bank1OrientationView = (TextView)findViewById(R.id.editRoof_Bank1_Orientaton);
		EditText bank2AngleView = (EditText)findViewById(R.id.editRoof_Bank2_Angle);
		TextView bank2OrientationView = (TextView)findViewById(R.id.editRoof_Bank2_Orientation);
		
		Roof roof = calculator.getCustomer().getLocation().getRoof();
		roof.getBanks().get(0).setNumberOfPanels(new Integer((int)Math.round(new Double(((EditText)findViewById(R.id.editRoof_Bank1_NumPanels)).getText().toString()))));
		roof.getBanks().get(0).setAngle(new Double(bank1AngleView.getText().toString()));
		
		roof.getBanks().get(1).setNumberOfPanels(new Integer((int)Math.round(new Double(((EditText)findViewById(R.id.editRoof_Bank2_NumPanels)).getText().toString()))));
		roof.getBanks().get(1).setAngle(new Double(bank2AngleView.getText().toString()));
		
		//Sunlight Details
		calculator.getCustomer().getLocation().setSunLightHours(new Double(((EditText)findViewById(R.id.editRoof_Sunlight_Daylight)).getText().toString()));
	}
	
	/**
	 * Populates input fields with data in the calculator bean
	 */
	private void loadData() {
		Calculator calculator = parentTabbedActivity.getCalculator();
		
		
		//Equipment
		TextView systemSizeView = (TextView)findViewById(R.id.editEquipment_Size);
		systemSizeView.setText(new Double(calculator.getEquipment().getSize()).toString());
		
		TextView inverterEfficiencyView = (TextView)findViewById(R.id.editEquiment_InverterEfficiency);
		inverterEfficiencyView.setText(new Double(calculator.getEquipment().getInverter().getEfficiency()).toString());
		
		
		
		//Usage
		EditText inputDailyAverage = (EditText)findViewById(R.id.editRoof_Usage_UsagePerDay);
		inputDailyAverage.setText(new Double(calculator.getCustomer().getElectricityUsage().getDailyAverageUsage()).toString());
		
		EditText inputTimeHourlyUsage = (EditText)findViewById(R.id.editRoof_Usage_UsagePerDaylight);
		inputTimeHourlyUsage.setText(new Double(calculator.getCustomer().getElectricityUsage().getDayTimeHourlyUsage()).toString());
		
		EditText inputFeedInFee = (EditText)findViewById(R.id.editRoof_Usage_FeedInFee);
		inputFeedInFee.setText(new Double(calculator.getCustomer().getTariff().getFeedInfee()).toString());
			
		EditText inputAnnualTariff11Cost = (EditText)findViewById(R.id.editRoof_Usage_Tariff);
		inputAnnualTariff11Cost.setText(new Double(calculator.getCustomer().getTariff().getTariff11Fee()).toString());
		
		
		//Roof
		EditText bank1NumPanelsView = (EditText)findViewById(R.id.editRoof_Bank1_NumPanels);
		EditText bank1AngleView = (EditText)findViewById(R.id.editRoof_Bank1_Angle);
		TextView bank1OrientationView = (TextView)findViewById(R.id.editRoof_Bank1_Orientaton);
		EditText bank2NumPanelsView = (EditText)findViewById(R.id.editRoof_Bank2_NumPanels);
		EditText bank2AngleView = (EditText)findViewById(R.id.editRoof_Bank2_Angle);
		TextView bank2OrientationView = (TextView)findViewById(R.id.editRoof_Bank2_Orientation);
		
		bank1NumPanelsView.setText(new Integer(calculator.getCustomer().getLocation().getRoof().getBanks().get(0).getNumberOfPanels()).toString());
		bank1AngleView.setText(new Double(calculator.getCustomer().getLocation().getRoof().getBanks().get(0).getAngle()).toString());
		bank1OrientationView.setText(calculator.getCustomer().getLocation().getRoof().getBanks().get(0).getSelectedOrientation());
		
		bank2NumPanelsView.setText(new Integer(calculator.getCustomer().getLocation().getRoof().getBanks().get(1).getNumberOfPanels()).toString());
		bank2AngleView.setText(new Double(calculator.getCustomer().getLocation().getRoof().getBanks().get(1).getAngle()).toString());
		bank2OrientationView.setText(calculator.getCustomer().getLocation().getRoof().getBanks().get(1).getSelectedOrientation());
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
	
}

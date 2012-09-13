package au.edu.qut.inn372.greenhat.activity;

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
import au.edu.qut.inn372.greenhat.bean.Calculator;
import au.edu.qut.inn372.greenhat.mediator.CalculatorMediator;

public class BasicInputActivity extends Activity {

	//add this 
	public final static String EXTRA_MESSAGE = "au.edu.qut.inn372.inn372.greenhat.activity.BasicInputActivity";
	public final static String EXTRA_MESSAGE2 = "au.edu.qut.inn372.inn372.greenhat.activity.BasicInputActivity2";
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_input);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        
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
    	Intent intent = new Intent(this, PowerGeneration.class);
    	
    	
    	try {
			Calculator calculator = new Calculator();
			// Equipment
			calculator.getEquipment().setSize(new Double(((EditText)findViewById(R.id.editEquipment_Size)).getText().toString()));
			calculator.getEquipment().getInverter().setEfficiency(new Double(((EditText)findViewById(R.id.editEquiment_InverterEfficiency)).getText().toString()));
			// Roof
			calculator.getCustomer().getLocation().getRoof().setEfficiencyLossNorth(new Double(((EditText)findViewById(R.id.editRoof_LossNorth)).getText().toString()));
			calculator.getCustomer().getLocation().getRoof().setEfficiencyLossWest(new Double(((EditText)findViewById(R.id.editRoof_LossWest)).getText().toString()));
			calculator.getCustomer().getLocation().getRoof().setPercentageNorth(new Double(((EditText)findViewById(R.id.editRoof_PercentageNorth)).getText().toString()));
			calculator.getCustomer().getLocation().getRoof().setPercentageWest(new Double(((EditText)findViewById(R.id.editRoof_PercentageWest)).getText().toString()));
			// Location (day light hours)
			calculator.getCustomer().getLocation().setSunLightHours(new Double(((EditText)findViewById(R.id.editSunlight_Daylight)).getText().toString()));
			// Current usage
			calculator.getCustomer().getElectricityUsage().setDailyAverageUsage(new Double(((EditText)findViewById(R.id.editUser_UsagePerDay)).getText().toString()));
			
			CalculatorMediator controller = new CalculatorMediator();
			intent.putExtra(EXTRA_MESSAGE2, ""+calculator.getCustomer().getElectricityUsage().getDailyAverageUsage());
			calculator = controller.calcEnergyProduction(calculator);
			
			intent.putExtra(EXTRA_MESSAGE, ""+calculator.getSolarPower());
			
    	} catch (Exception e) {
			// TODO: handle exception
		}	
    	
    	startActivity(intent);
    
    }
    
    public void reset(View view){
		// Equipment
		((EditText)findViewById(R.id.editEquipment_Size)).setText("0.0");
		((EditText)findViewById(R.id.editEquiment_InverterEfficiency)).setText("0.0");
		// Roof
		((EditText)findViewById(R.id.editRoof_LossNorth)).setText("0.0");
		((EditText)findViewById(R.id.editRoof_LossWest)).setText("0.0");
		((EditText)findViewById(R.id.editRoof_PercentageNorth)).setText("0.0");
		((EditText)findViewById(R.id.editRoof_PercentageWest)).setText("0.0");
		// Location (day light hours)
		((EditText)findViewById(R.id.editSunlight_Daylight)).setText("0.0");
		// Current usage
		((EditText)findViewById(R.id.editUser_UsagePerDay)).setText("0.0");
    }

}

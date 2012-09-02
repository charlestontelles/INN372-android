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
import au.edu.qut.inn372.greenhat.controller.CalculatorRemoteController;

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
    
    public void Calculate(View view){
    	Intent intent = new Intent(this, PowerGeneration.class);
    	
    	
    	try {
			Calculator calculator = new Calculator();
			// Equipment
			calculator.getEquipment().setSize(new Double(((EditText)findViewById(R.id.editEquipmentSize)).getText().toString()));
			calculator.getEquipment().getInverter().setEfficiency(new Double(((EditText)findViewById(R.id.editEquimentInverterEfficiency)).getText().toString()));
			// Roof
			calculator.getCustomer().getLocation().getRoof().setEfficiencyLossNorth(new Double(((EditText)findViewById(R.id.editRoofLossNorth)).getText().toString()));
			calculator.getCustomer().getLocation().getRoof().setEfficiencyLossWest(new Double(((EditText)findViewById(R.id.editRoofLossWest)).getText().toString()));
			calculator.getCustomer().getLocation().getRoof().setPercentageNorth(new Double(((EditText)findViewById(R.id.editRoofNorth)).getText().toString()));
			calculator.getCustomer().getLocation().getRoof().setPercentageWest(new Double(((EditText)findViewById(R.id.editRoofWest)).getText().toString()));
			// Location (day light hours)
			calculator.getCustomer().getLocation().setSunLightHours(new Double(((EditText)findViewById(R.id.editDayLight)).getText().toString()));
			// Current usage
			calculator.getCustomer().getElectricityUsage().setDailyAverageUsage(new Double(((EditText)findViewById(R.id.editUsagePerDay)).getText().toString()));
			
			CalculatorRemoteController controller = new CalculatorRemoteController();
			intent.putExtra(EXTRA_MESSAGE2, ""+calculator.getCustomer().getElectricityUsage().getDailyAverageUsage());
			calculator = controller.calcEnergyProduction(calculator);
			
			intent.putExtra(EXTRA_MESSAGE, ""+calculator.getSolarPower());
			
    	} catch (Exception e) {
			// TODO: handle exception
		}	
    	
    	startActivity(intent);
    
    }

}

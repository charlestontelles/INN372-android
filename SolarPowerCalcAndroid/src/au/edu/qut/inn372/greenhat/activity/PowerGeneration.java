package au.edu.qut.inn372.greenhat.activity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

public class PowerGeneration extends Activity{
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.power_generation);
        generateView();
;
        
	}   
	
	public void generateView() {
    	TextView dailyField = (TextView)findViewById(R.id.TextViewDailyField);
    	TextView annualField = (TextView)findViewById(R.id.TextViewAnnualField);
    	TextView quaterlyField = (TextView)findViewById(R.id.TextViewQuaterlyField);
    	TextView netField = (TextView)findViewById(R.id.TextViewNetField);
        
    	Intent intent = getIntent();
        String message = intent.getStringExtra(BasicInputActivity.EXTRA_MESSAGE);
        String message2 = intent.getStringExtra(BasicInputActivity.EXTRA_MESSAGE2);
        
        dailyField.setText(message);
        annualField.setText(""+new Double(message)*365);
        quaterlyField.setText(""+new Double(message)*91);
        netField.setText(""+(new Double(message).doubleValue() - new Double(message2).doubleValue()));
    }	
	
}

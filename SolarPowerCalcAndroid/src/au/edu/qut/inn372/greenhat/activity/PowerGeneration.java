package au.edu.qut.inn372.greenhat.activity;



import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import au.edu.qut.inn372.greenhat.bean.Calculator;
import au.edu.qut.inn372.greenhat.bean.Equipment;
import au.edu.qut.inn372.greenhat.controller.CalculatorRemoteController;

public class PowerGeneration extends Activity{
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.power_generation);
        generateView();
    }
	
	public void generateView() {
    	
    	//TextView daily= (TextView)findViewById(R.id.TextViewAnnualCurrentField);
    	TextView daily = (TextView)findViewById(R.id.TextViewDaily);
    	TextView annual = (TextView)findViewById(R.id.TextViewAnnual);
    	TextView quaterly = (TextView)findViewById(R.id.TextViewQuaterly);
    	TextView net = (TextView)findViewById(R.id.TextViewNet);
    	
    	//Retrieve Values
    	Intent intent = getIntent();
    	//String message = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);
    	
    	//Send for calculation
    	
    	
    	//Set Text for the table
    	//daily.setText();
    	//annual.setText();
    	//quaterly.setText();
    	//net.setText();
    }
}

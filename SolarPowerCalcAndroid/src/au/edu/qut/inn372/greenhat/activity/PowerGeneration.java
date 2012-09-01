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
        //generateView();
	}   
	
	public void generateView() {
    	
    	//TextView daily= (TextView)findViewById(R.id.TextViewAnnualCurrentField);
    	TextView dailyField = (TextView)findViewById(R.id.TextViewDailyField);
    	//TextView annualField = (TextView)findViewById(R.id.TextViewAnnualField);
    	//TextView quaterlyField = (TextView)findViewById(R.id.TextViewQuaterlyField);
    	//TextView netField = (TextView)findViewById(R.id.TextViewNetField);
    	
    	//Retrieve Values
    	//Intent intent = getIntent();
    	//String message = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);
    	//String cost = intent.getStringExtra(BasicInputActivity.EXTRA_MESSAGE);
    	
    	
    	//String size =
    	
    	
    	//Send for calculation
    	
    	/*
    	try {
			Calculator calculator = new Calculator();
			calculator.getEquipment().setCost(new Double(inputCost.getText().toString()));
			calculator.getEquipment().setSize(new Double(inputSize.getText().toString()));
			CalculatorRemoteController controller = new CalculatorRemoteController();
			calculator = controller.calcEnergyProduction(calculator);
			textView.setText("Daily Soler Power =" + calculator.getResult() + " KW(p)");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
    	/*
    	Calculator calculator = new Calculator();
    	calculator.getEquipment().setCost(Double.parseDouble(cost));
		//calculator.getEquipment().setSize(new Double(inputSize.getText().toString()));
		CalculatorRemoteController controller = new CalculatorRemoteController();
		calculator = controller.calcEnergyProduction(calculator);
    	
    	//Set Text for the table
    	dailyField.setText(""+calculator.getResult());
    	//annualField.setText();
    	//quaterlyField.setText();
    	//netField.setText();
    	
    	*/
    	
    	setContentView(dailyField);
    	//setContentView(annualField);
    	//setContentView(quaterlyField);
    	//setContentView(netField);
    }
	
	
	
}

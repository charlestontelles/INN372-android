package au.edu.qut.inn372.greenhat.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import au.edu.qut.inn372.greenhat.bean.Calculator;
import au.edu.qut.inn372.greenhat.mediator.CalculatorMediator;

/**
 * Activity corresponding to user registration
 *
 */
public class RegistrationActivity extends Activity {
	
	//Added by Martins
	public final static int STATE_NORMAL = 0;
	public final static int STATE_PAUSED = 1;
	private int state;
	private CalculatorMediator calculatorMediator;

	@SuppressLint({ "NewApi", "NewApi", "NewApi" })
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);   
        //Added by Martins
        calculatorMediator = new CalculatorMediator(); //creates a new calculator
    }
	
	/**
	 * Functionality to complete registration
	 * @param view
	 */
	public void register(View view) {
		saveData();
    	Intent intent = new Intent(this, TabbedActivity.class);
    	
    	//Added by Martin
    	//intent.putExtra("CalculatorMediator", getCalculatorMediator());
    	calculatorMediator.saveUserProfile();
    	
    	startActivity(intent);
	}
	
	/**
	 * Finishes the activity - called by the 'back button' and returns control up the activity stack
	 * @param view
	 */
	public void finish(View view) {
		finish();
	}
	
	
    //Added by Martins
	/**
	 * Saves current input data to the calculator bean
	 */
	private void saveData() {
		calculatorMediator.getCalculator().getCustomer().getUserProfile().setName(new String(((EditText)findViewById(R.id.Edit_Registration_Username)).getText().toString()));
		calculatorMediator.getCalculator().getCustomer().getUserProfile().setPassword(new String(((EditText)findViewById(R.id.Edit_Registration_Password)).getText().toString()));
		calculatorMediator.getCalculator().getCustomer().getUserProfile().setEmail(new String(((EditText)findViewById(R.id.Edit_Registration_Email)).getText().toString()));
		calculatorMediator.getCalculator().getCustomer().getUserProfile().setType(1);
		
	}
	
	@Override
	public void onPause() {
		super.onPause();
		saveData();
		state = STATE_PAUSED;
	}
}

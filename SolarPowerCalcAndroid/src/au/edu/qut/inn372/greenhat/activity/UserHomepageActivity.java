package au.edu.qut.inn372.greenhat.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class UserHomepageActivity extends Activity {
	
	TabbedActivity parentTabbedActivity;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);           
        setContentView(R.layout.activity_userhomepage);
        
    }
	
	/**
	 * Refers to the succeeding tab
	 * @param view
	 */
	public void viewNext(View view){
		TabbedActivity parentTabbedActivity = (TabbedActivity)this.getParent();
    	int targetActivity = TabbedActivity.LOCATION_ID;
    	parentTabbedActivity.switchTab(targetActivity);
	}
	
	/**
     * Refers to the preceding Tab
     * @param view
     */
    public void viewBack(View view){	    	
    	Intent intent = new Intent(this, LoginActivity.class);
    	
    	startActivity(intent);
    }
    
    @Override
	public void onResume() {
		super.onResume();
		TabbedActivity parentTabbedActivity = (TabbedActivity)this.getParent();
		//adds the user name to the welcome message
		TextView welcomeUserName = (TextView)findViewById(R.id.textUserHomepage_WelcomeName);
		welcomeUserName.setText(new Integer(parentTabbedActivity.getCalculator().getCustomer().getUserProfile().getName()).toString());
	}
    
    /**TODO: UNDER CONSTRUCTION by Fabian
     * Builds the table of calculations
     *//*
    public void buildCalculationTable(){
    	ArrayList<Calculation> allCalculations = parentTabbedActivity.getAllCalculation();
    }*/
    
    /**TODO: Complete by Fabian
     * Begin with a new calculation
     */
    public void newCalculation(View view){
    	TabbedActivity parentTabbedActivity = (TabbedActivity)this.getParent();
    	//Load default values, gonna be a ws-call in the future
    	parentTabbedActivity.setupCalculatorDefaults();
    	//switch to the next tab
    	int targetActivity = TabbedActivity.LOCATION_ID;
    	parentTabbedActivity.switchTab(targetActivity);
    }
    
    /**TODO: Complete by Fabian
     * Loads the selected calculation
     */
    public void editCalculation(View view){
    	TabbedActivity parentTabbedActivity = (TabbedActivity)this.getParent();
    	int targetActivity = TabbedActivity.LOCATION_ID;
    	parentTabbedActivity.switchTab(targetActivity);
    }
    
    /**TODO: Needs to be completed
     * Let the user compare different calculations with each other
     */
    public void compareCalculation(View view){
    	TabbedActivity parentTabbedActivity = (TabbedActivity)this.getParent();
    	int targetActivity = TabbedActivity.LOCATION_ID;
    	parentTabbedActivity.switchTab(targetActivity);
    }

}

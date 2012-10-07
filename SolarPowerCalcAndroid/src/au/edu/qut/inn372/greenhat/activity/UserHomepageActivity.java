package au.edu.qut.inn372.greenhat.activity;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import au.edu.qut.inn372.greenhat.bean.Calculation;
import android.graphics.Typeface;


public class UserHomepageActivity extends Activity {
	
	
	
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
		welcomeUserName.setText(new String(parentTabbedActivity.getCalculator().getCustomer().getUserProfile().getName()).toString());
		
		//buildCalculationTable(); 
	}
    
    /**TODO: UNDER CONSTRUCTION by Fabian
     * Builds the table of calculations
     */
    public void buildCalculationTable(){
    	TabbedActivity parentTabbedActivity = (TabbedActivity)this.getParent();
    	//Get the latest calculations list
    	List<Calculation> allCalculations = parentTabbedActivity.getCalculations();
    	
    	//Get the table
    	TableLayout calcTable = (TableLayout) findViewById(R.id.tableUserHomepage_Calulations);
    	
    	//Create rows and fill the table with content 
    	int i = 0;
    	while (allCalculations.get(i) != null && i < 25){ // 25 is the size of the calculations-array
    	
    		//Parameters for one row
    		TableRow tableRow = new TableRow(this);
    		TextView columnName = new TextView(this);
    		TextView columnDateTime = new TextView(this);
    		TextView columnStatus = new TextView(this);
    		CheckBox checkBox = new CheckBox(this);
    		ImageView line = new ImageView(this);
    		
    		//Fill in the content
    		// columnName.setText(new String(allCalculations.get(i).getName())); TODO: Attribute "name" not yet implemented
    		columnName.setText("Name "+i);
    		//columnDateTime.setText(allCalculations.get(i).getDate()); TODO: Attribute "date" not yet implemented
    		columnDateTime.setText("Date "+i);
    		//columnStatus.setText(allCalculations.get(i).getStatus()); TODO: Attribute "status" not yet implemented
    		columnStatus.setText("Status "+i);
    		
    		checkBox.setLayoutParams(new LayoutParams(30, 30));
    		line.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 2));
    		
    		
    		tableRow.addView(checkBox);
    		tableRow.addView(columnName);
    		tableRow.addView(columnDateTime);
    		tableRow.addView(columnStatus);
    		
    		calcTable.addView(tableRow);
    		calcTable.addView(line);
  		
    	}
    }
    
    /**TODO: Complete by Fabian
     * Begin with a new calculation
     */
    public void newCalculation(View view){
    	TabbedActivity parentTabbedActivity = (TabbedActivity)this.getParent();
    	//Load default values, will be a ws-call in the future
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
    
    /**TODO: Needs to be completetd
     * Uses the selected calculation as template
     */
    public void templateCalculation(View view){
    	TabbedActivity parentTabbedActivity = (TabbedActivity)this.getParent();
    	int targetActivity = TabbedActivity.LOCATION_ID;
    	parentTabbedActivity.switchTab(targetActivity);
    }

}

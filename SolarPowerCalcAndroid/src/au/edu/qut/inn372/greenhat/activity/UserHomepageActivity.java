package au.edu.qut.inn372.greenhat.activity;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import au.edu.qut.inn372.greenhat.bean.AndroidAbstractBean;
import au.edu.qut.inn372.greenhat.bean.Calculation;
import au.edu.qut.inn372.greenhat.bean.Calculator;
import au.edu.qut.inn372.greenhat.bean.UserProfile;
import au.edu.qut.inn372.greenhat.ws.CalculatorSoapClient;
import android.graphics.Typeface;


public class UserHomepageActivity extends Activity {
	
	private CalculatorSoapClient soapClient = new CalculatorSoapClient();
	private UserProfile userProfile;
	private boolean[] checkBoxSelected;
	private List<Calculator> allCalculations;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);           
        setContentView(R.layout.activity_userhomepage);
        userProfile = (UserProfile)getIntent().getSerializableExtra("UserProfile");
        
      //Get the latest calculations list
    	allCalculations = getCalculationList();
    	checkBoxSelected = new boolean[allCalculations.size()];
		
		buildCalculationTable(); 
    }
	
	public List<Calculator> getCalculationList(){
		SoapObject soap = soapClient.synchronousRequest(userProfile.getSoapObject(AndroidAbstractBean.OPERATION_GET_CALCULATIONS));
		List<Calculator> calculations = new ArrayList<Calculator>(); //changed List to ArrayList
		int numCalculations = soap.getPropertyCount();  
		for(int i = 0; i< numCalculations ; i++) {
			try {
				SoapObject curCalculation = (SoapObject)soap.getProperty(i);
				calculations.add(new Calculator(curCalculation, AndroidAbstractBean.OPERATION_GET_CALCULATIONS));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return calculations;	
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
		//adds the user name to the welcome message
		TextView welcomeUserName = (TextView)findViewById(R.id.textUserHomepage_WelcomeName);
		welcomeUserName.setText(new String(userProfile.getName()).toString());
		
	}
    
    /**
     * Builds the table of calculators
     */
    private void buildCalculationTable(){    	
    	//Get the table
    	TableLayout calcTable = (TableLayout) findViewById(R.id.tableUserHomepage_Calulations);
    	
    	//Create rows and fill them with content
    	int i = 0;
    	while (i < allCalculations.size()){ 
    	
    		//Parameters for one row
    		TableRow tableRow = new TableRow(this);
    		TextView columnName = new TextView(this);
    		TextView columnDateTime = new TextView(this);
    		TextView columnStatus = new TextView(this);
    		CheckBox checkBox = new CheckBox(this);
    		
    		//Fill in the content
    		columnName.setText(new String(allCalculations.get(i).getName())); 
    		columnDateTime.setText(allCalculations.get(i).getFormatedDateTime()); 
    		columnStatus.setText(allCalculations.get(i).getStatusName()); 
    		
    		//Prepare checkboxes
    		checkBox.setId(i);
    		checkBox.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (((CheckBox) v).isChecked()) {
						if (checkBoxSelected[v.getId()]== true){
							checkBoxSelected[v.getId()] = false;
						}
						else {checkBoxSelected[v.getId()] = true;}
    				}
				}
			});
    		
    		tableRow.addView(checkBox);
    		tableRow.addView(columnName);
    		tableRow.addView(columnDateTime);
    		tableRow.addView(columnStatus);
    		
    		calcTable.addView(tableRow);
    		
    		i++;
  		
    	}
    }
    
    /**
     * Starts a new calculation
     */
    public void newCalculation(View view){
    	//Create intent to start TabbedActivity
    	int type = 0; //0 = newCalculation
    	Intent intent = new Intent(this, TabbedActivity.class);
		intent.putExtra("UserProfile", userProfile);
		intent.putExtra("Type", type);
    	startActivity(intent);
    }
    
    /**TODO: needs to be completed, is not working properly
     * Loads the selected calculation
     */
    public void editCalculation(View view){
    	//Create intent to start TabbedActivity
    	int type = 1; //1 = editCalculation
    	
    	Intent intent = new Intent(this, TabbedActivity.class);
		intent.putExtra("UserProfile", userProfile);
		intent.putExtra("Type", type);
		intent.putExtra("Calculator", allCalculations.get(getSelectedCalculator())); //passes the selected calculator from table to TabbedActivity
		
    	startActivity(intent);
    }
    
    /**TODO: Needs to be completed
     * Let the user delete one calculation TODO: implement the possibility to delete several calculations
     */
    public void deleteCalculation(View view){
    	soapClient.synchronousRequest(allCalculations.get(getSelectedCalculator()).getSoapObject(AndroidAbstractBean.OPERATION_DELETE_CALCULATION));
    	//update allCalculations and refresh the table 
    	allCalculations = getCalculationList();
    	buildCalculationTable();    	
    }
    
    /**TODO: Needs to be completed
     * Let the user compare different calculations with each other
     */
    public void compareCalculation(View view){
    	//Create intent to start TabbedActivity
    	int type = 2; //2 = compareCalculation
    	
    	Intent intent = new Intent(this, TabbedActivity.class);
		intent.putExtra("UserProfile", userProfile);
		intent.putExtra("Type", type);
		
    	startActivity(intent);
    }
    
    
    /**
     * Finds the ID of the checked calculation
     * @return
     */
    private int getSelectedCalculator(){
    	int selectedID = 0;
    	while(checkBoxSelected[selectedID]== false){selectedID++;}
    	return selectedID;
    }

}

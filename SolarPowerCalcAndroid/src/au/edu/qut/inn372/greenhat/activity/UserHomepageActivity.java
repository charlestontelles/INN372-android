package au.edu.qut.inn372.greenhat.activity;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
	private TableLayout calcTable;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);           
        setContentView(R.layout.activity_userhomepage);
        userProfile = (UserProfile)getIntent().getSerializableExtra("UserProfile");
        
      //Get the latest calculations list
    	allCalculations = getCalculationList();
    	checkBoxSelected = new boolean[allCalculations.size()];
		
		 
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
    	finish();
    }
    
    @Override
	public void onResume() {
		super.onResume();
		//adds the user name to the welcome message
		TextView welcomeUserName = (TextView)findViewById(R.id.textUserHomepage_WelcomeName);
		welcomeUserName.setText(new String(userProfile.getName()).toString());
		
		cleanTable();
		allCalculations = getCalculationList();
		buildCalculationTable();
	}
    
    /**
     * Removes the table from the linear layout
     */
    private void cleanTable(){
    	LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayoutUserHomepage_table);
    	linearLayout.removeViewInLayout(calcTable);	
    }
    
    /**
     * Builds the table of calculators
     */
    private void buildCalculationTable(){
    	//Get LinearLayout
    	LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayoutUserHomepage_table);
    	//Create the table
    	calcTable = new TableLayout(this);
    	
    	linearLayout.addView(calcTable);
    	
    	//Table completely generated in Java-Code, TODO: take a look at TabbedOutput for layout
    	
    	//Create head row
    	TableRow tableHeadRow = new TableRow(this);
    	TextView columnHeadCheckbox = new TextView(this);
		TextView columnHeadName = new TextView(this);
		columnHeadName.setText("Name");
		TextView columnHeadDateTime = new TextView(this);
		columnHeadDateTime.setText("Date");
		TextView columnHeadStatus = new TextView(this);
		columnHeadStatus.setText("Status");
		tableHeadRow.addView(columnHeadCheckbox);
		tableHeadRow.addView(columnHeadName);
		tableHeadRow.addView(columnHeadDateTime);
		tableHeadRow.addView(columnHeadStatus);
		calcTable.addView(tableHeadRow);
    	
    	int i = 0;
    	//Create rows and fill them with content
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
					//checkBoxSelected[v.getId()] = !checkBoxSelected[v.getId()];
					
						if (checkBoxSelected[v.getId()]== false){
							checkBoxSelected[v.getId()] = true;
						}
						else {checkBoxSelected[v.getId()] = false;}
    				
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
    	//Create int to pass on to TabbedActivity 
    	int type = 1; //1 = editCalculation
    	
    	//TODO Check for several selected calculations and throw exception of not correct selected
    	try {
			if (getSelectedCalculation().size()!=1){
				if (getSelectedCalculation().size()==0){
				//no calc selected
					throw new NoneSelectedException();
				}
				else{
				//more than one calc selected
					throw new TooMuchSelectedException();
				}
			}
		
    	
    	Intent intent = new Intent(this, TabbedActivity.class);
		intent.putExtra("UserProfile", userProfile);
		intent.putExtra("Type", type);
		intent.putExtra("Calculator", getSelectedCalculation().get(0)); //passes the selected calculator from table to TabbedActivity
		
    	startActivity(intent);
    	
    	} catch (NoneSelectedException e) {
			// TODO Auto-generated catch block
			// 1. Instantiate an AlertDialog.Builder with its constructor
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setPositiveButton(R.string.userhomepage_button_ok, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               // User clicked OK button
		           }
		       });

			// 2. Chain together various setter methods to set the dialog characteristics
			builder.setMessage(e.getMessage())
			       .setTitle("No Calculation selected!");

			// 3. Get the AlertDialog from create()
			AlertDialog dialog = builder.create();
			dialog.show();	
			
			e.printStackTrace();
		} catch (TooMuchSelectedException e) {
			// TODO Auto-generated catch block
			
			// 1. Instantiate an AlertDialog.Builder with its constructor
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setPositiveButton(R.string.userhomepage_button_ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
	               // User clicked OK button
	           }
			});

			// 2. Chain together various setter methods to set the dialog characteristics
			builder.setMessage(e.getMessage())
			       .setTitle("Too much Calculations selected!");

			// 3. Get the AlertDialog from create()
			AlertDialog dialog = builder.create();
			dialog.show();	
			
			e.printStackTrace();
		}
    }
    
    /**
     * Let the user delete one calculation TODO: implement the possibility to delete several calculations
     */
    public void deleteCalculation(View view){
    	try {
			//remove locally
			if (getSelectedCalculation().size() == 1){
				//only one selected, then remove first of the list
				allCalculations.remove(getSelectedCalculation().get(0));
			} 
			else{
				if (getSelectedCalculation().size() != 0){
				//several selected, iterate through list
					for (int i=0; i < getSelectedCalculation().size(); i++ ){
						allCalculations.remove(getSelectedCalculation().get(i));
					}
				}
				else { //none selected 
					throw new NoneSelectedException();
				}
			}
			//remove cloud
			if (getSelectedCalculation().size() == 1){
				//only one selected, then remove first of the list
				Calculator selCalc = getSelectedCalculation().get(0);
				soapClient.synchronousRequest(selCalc.getSoapObject(AndroidAbstractBean.OPERATION_DELETE_CALCULATION));allCalculations.remove(getSelectedCalculation().get(0));
			} 
			else{
				//several selected, iterate through list
				for (int i=0; i < getSelectedCalculation().size(); i++ ){
					Calculator selCalc = getSelectedCalculation().get(i);
					soapClient.synchronousRequest(selCalc.getSoapObject(AndroidAbstractBean.OPERATION_DELETE_CALCULATION));allCalculations.remove(getSelectedCalculation().get(i));
				}
			}
			//clean and refresh the table 
			cleanTable();
			buildCalculationTable();
			
			} catch (NoneSelectedException e) {
				// TODO Auto-generated catch block
				// 1. Instantiate an AlertDialog.Builder with its constructor
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setPositiveButton(R.string.userhomepage_button_ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
			               // User clicked OK button
					}
				});
	
				// 2. Chain together various setter methods to set the dialog characteristics
				builder.setMessage(e.getMessage())
				       .setTitle("No Calculation selected!");
	
				// 3. Get the AlertDialog from create()
				AlertDialog dialog = builder.create();
				dialog.show();
				e.printStackTrace();
			}    	
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
     * Returns a list with the selected calculators
     * @return
     */
    private List<Calculator> getSelectedCalculation(){
    	//TODO: check for no selected
    	List <Calculator> selectedCalculators = new ArrayList<Calculator>();
    	
			for (int i =0; i < allCalculations.size(); i++ ){
				if (checkBoxSelected[i] == true){
					selectedCalculators.add(allCalculations.get(i));
				}		
			}
			return selectedCalculators;
    }
    	
 }
    
    class NoneSelectedException extends Exception{
    	NoneSelectedException(){
    		super("Please select a calculation");
    	}
    }
    
    class TooMuchSelectedException extends Exception{
    	TooMuchSelectedException(){
    		super("Please select only one calculation");
    	}
    }



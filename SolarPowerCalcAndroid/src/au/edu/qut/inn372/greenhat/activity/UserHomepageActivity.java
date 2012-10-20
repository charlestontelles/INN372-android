package au.edu.qut.inn372.greenhat.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import au.edu.qut.inn372.greenhat.bean.Calculator;
import au.edu.qut.inn372.greenhat.bean.UserProfile;
import au.edu.qut.inn372.greenhat.mediator.AllCalculationsMediator;


public class UserHomepageActivity extends Activity {
	
	private UserProfile userProfile;
	private boolean[] checkBoxSelected;
	private List<Calculator> allCalculations;
	private TableLayout calcTable;
	private AllCalculationsMediator allCalculationsMediator;
	
	private final static int NO_CALCULATORS_SELECTED = 0;
	private final static int TOO_MANY_CALCULATORS_SELECTED = 1;
	private final static int TOO_FEW_CALCULATORS_SELECTED = 2;
	private final static int CONFIRM_DELETE = 3;

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhomepage);
        userProfile = (UserProfile)getIntent().getSerializableExtra("UserProfile");
        allCalculationsMediator = new AllCalculationsMediator(userProfile);
        allCalculations = allCalculationsMediator.getCalculations();
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
		
		//TODO Loading dialog
		//retrieve calculation list
		allCalculationsMediator.getCalculationList();
		//reset state
		regenerateActivityState();
	}
    
    /**
     * Regenerates the layout and activity state if the number of calculations has changed or just been loaded
     */
    private void regenerateActivityState() {
    	cleanTable();
    	checkBoxSelected = new boolean[allCalculations.size()];
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
    	
    	//Create rows and fill them with content
    	for(int i = 0; i < allCalculations.size(); i++){ 
    		
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
					checkBoxSelected[v.getId()] = !checkBoxSelected[v.getId()];
				}
			});
    		
    		tableRow.addView(checkBox);
    		tableRow.addView(columnName);
    		tableRow.addView(columnDateTime);
    		tableRow.addView(columnStatus);
    		
    		calcTable.addView(tableRow);
    	}
    }
    
    /**
     * Starts a new calculation
     */
    public void newCalculation(View view){
    	startActivity(0);
    }
    
    /**
     * Loads the selected calculation
     */
    public void editCalculation(View view){
    	List<Calculator> selectedCalculations = getSelectedCalculation();
    	
    	if(selectedCalculations.size()<1) {
    		showDialog(NO_CALCULATORS_SELECTED);
    		return;
    	}
    	else if (selectedCalculations.size()>1) {
    		showDialog(TOO_MANY_CALCULATORS_SELECTED);
    		return;
    	}
		
    	startActivity(1); //start new edit calculation
    }
    
    /**
     * Let the user delete one calculation
     */
    public void deleteCalculation(View view){
    	//Check number of selected calculators is not zero
    	if(getSelectedCalculation().size() < 1) {
    		showDialog(NO_CALCULATORS_SELECTED);
    	}
    	else {
    		showDialog(CONFIRM_DELETE);
    	}
    }
    
    /**
     * Performs the calculation deletion and table regeneration
     */
    private void deleteCalculations() {
    	allCalculationsMediator.deleteCalculations(getSelectedCalculation());
    	regenerateActivityState();
    }
    
    /**TODO: Needs to be completed
     * Let the user compare different calculations with each other
     */
    public void compareCalculation(View view){
    	List<Calculator> selectedCalculations = getSelectedCalculation();
    	
    	if(selectedCalculations.size()<2) {
    		showDialog(TOO_FEW_CALCULATORS_SELECTED);
    		return;
    	}
    	else if (selectedCalculations.size()>2) {
    		showDialog(TOO_MANY_CALCULATORS_SELECTED);
    		return;
    	}
    	
    	//startActivity(2);
    	//TODO Compare calculations activity called here
    	/*
    	Intent intent = new Intent(this, TabbedActivity.class);
		intent.putExtra("UserProfile", userProfile);
		intent.putExtra("Type", type);
		
    	startActivity(intent);
    	*/
    }
    
    private void startActivity(int type) {
    	//TODO move soap calls (equipment kits, panel kits, inverter) here and pass as extras to the intent
    		//could also put a loading dialog here
    	Intent intent;
    	switch(type) {
    	case 2: //compare calculations
    		intent = null;
    		//TODO
    		//Intent intent = new Intent(this, CompareOutputActivity.class);
    		break;
    	case 1: //edit calculation
    		intent = new Intent(this, TabbedActivity.class);
    		intent.putExtra("UserProfile", userProfile);
    		intent.putExtra("Type", type);
    		intent.putExtra("Calculator", getSelectedCalculation().get(0));
    		break;
    	case 0: //new calculation
    	default:
    		intent = new Intent(this, TabbedActivity.class);
    		intent.putExtra("UserProfile", userProfile);
    		intent.putExtra("Type", type);
    		break;
    	}
    	startActivity(intent);
    }
    
    /**
     * Returns a list with the selected calculators
     * @return
     */
    private List<Calculator> getSelectedCalculation(){
    	
    	List <Calculator> selectedCalculators = new ArrayList<Calculator>();
    	
		for (int i =0; i < allCalculations.size(); i++ ){
			if (checkBoxSelected[i] == true){
				selectedCalculators.add(allCalculations.get(i));
			}		
		}
		return selectedCalculators;
 	}
    
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch(id) {
		case NO_CALCULATORS_SELECTED:
			builder.setPositiveButton(R.string.userhomepage_button_ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
		               dialog.dismiss();
				}
			});
			builder.setMessage("No calculation selected!")
			       .setTitle("Error");
			dialog = builder.create();
			dialog.show();
			break;
		case TOO_MANY_CALCULATORS_SELECTED:
			builder.setPositiveButton(R.string.userhomepage_button_ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
		               dialog.dismiss();
		           }
				});

				// 2. Chain together various setter methods to set the dialog characteristics
				builder.setMessage("Too many calculations selected!")
				       .setTitle("Error");

				// 3. Get the AlertDialog from create()
				dialog = builder.create();
				dialog.show();	
			break;
		case TOO_FEW_CALCULATORS_SELECTED:
			builder.setPositiveButton(R.string.userhomepage_button_ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
		               dialog.dismiss();
		           }
				});

				// 2. Chain together various setter methods to set the dialog characteristics
				builder.setMessage("Not enough calculations selected!")
				       .setTitle("Error");

				// 3. Get the AlertDialog from create()
				dialog = builder.create();
				dialog.show();	
			break;
		case CONFIRM_DELETE:
    		builder.setPositiveButton(R.string.userhomepage_button_ok, new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
                   // User clicked OK button
    				dialog.dismiss();
    				deleteCalculations();
               }
    		});
    		
    		builder.setNegativeButton(R.string.userhomepage_button_cancel, new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	               // User cancelled the dialog
    	        	   dialog.dismiss();
    	           }
    	       });

    		builder.setMessage(R.string.userhomepage_dialog_deleteconfirmation)
    		       .setTitle("Confirm Delete");

    		dialog = builder.create();
    		dialog.show();
		default:
			dialog = null;
			break;
		}
		return dialog;
	}
}
    


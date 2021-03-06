package au.edu.qut.inn372.greenhat.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import au.edu.qut.inn372.greenhat.bean.Calculator;
import au.edu.qut.inn372.greenhat.bean.UserProfile;
import au.edu.qut.inn372.greenhat.mediator.AllCalculationsMediator;
import au.edu.qut.inn372.greenhat.mediator.CalculatorMediator;


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
		TextView welcomeUserName = (TextView)findViewById(R.id.textUserHomepage_Welcome);
		welcomeUserName.setText("Welcome " + userProfile.getName().toString());
		
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
    	tableHeadRow.addView(columnHeadCheckbox);
    	addHeadingView(tableHeadRow, "Name");
    	addHeadingView(tableHeadRow, "Date");
    	addHeadingView(tableHeadRow, "Status");

		calcTable.addView(tableHeadRow);
    	
    	//Create rows and fill them with content
    	for(int i = 0; i < allCalculations.size(); i++){ 
    		
    		//Parameters for one row
    		TableRow tableRow = new TableRow(this);
    		
    		//Prepare checkboxes
    		CheckBox checkBox = new CheckBox(this);
    		checkBox.setId(i);
    		checkBox.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					checkBoxSelected[v.getId()] = !checkBoxSelected[v.getId()];
				}
			});
    		tableRow.addView(checkBox);
    		
    		addDataView(tableRow, allCalculations.get(i).getName());
    		addDataView(tableRow, allCalculations.get(i).getFormatedDateTime());
    		addDataView(tableRow, allCalculations.get(i).getStatusName());
    		
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
    	//TODO Add a check to only accept calculators marked as "Completed"
    	
    	startActivity(2);
    }
    
    private void startActivity(int type) {
    	//TODO move soap calls (equipment kits, panel kits, inverter) here and pass as extras to the intent
    		//could also put a loading dialog here
    	Intent intent;
    	switch(type) {
    	case 2: //compare calculations
    		intent = new Intent(this, TabbedOutputActivity.class);
    		List<Calculator> calculatorResultList = new ArrayList<Calculator>();
    		for(Calculator curCalculator : getSelectedCalculation()) {
    			//TODO Remove this when functionality is implemented correctly
    			//re-perform calculations until ws call and unmarshalling is functional for completed calculation retrieval
    			CalculatorMediator calcMediator = new CalculatorMediator(curCalculator);
    			calcMediator.calcEnergyProduction();
    			curCalculator = calcMediator.getCalculator();
    			calculatorResultList.add(curCalculator);
    		}
    		intent.putExtra("Calculators", (Serializable)calculatorResultList);
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
	
	private void addHeadingView(TableRow row, String heading) {
    	TextView newView = (TextView) getLayoutInflater().inflate(
				R.layout.output_text_heading_view, null);
    	newView.setText(heading);
		row.addView(newView);
    }
	
    /**
     * Add a new TextView contain a string to a row. This is separate from addHeadingView so that a different textview layout can be used if needed
     * @param row
     * @param heading
     */
    private void addDataView(TableRow row, String value) {
    	TextView newView = (TextView) getLayoutInflater().inflate(
				R.layout.output_text_view, null);
    	newView.setText(value);
		row.addView(newView);
    }
}
    


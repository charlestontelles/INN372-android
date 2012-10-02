package au.edu.qut.inn372.greenhat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import au.edu.qut.inn372.greenhat.bean.Calculator;

public class RoofActivity extends Activity{
	
	public final static int STATE_NORMAL = 0;
	public final static int STATE_PAUSED = 1;
	private int state;
	
	private TabbedActivity parentTabbedActivity;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roof_input);
        parentTabbedActivity = (TabbedActivity)this.getParent();
    }
	
	/**
	 *
	 * Refers to the succeding tab
	 * @param view
	 */
	public void viewNext(View view){
    	TabbedActivity parentTabbedActivity = (TabbedActivity)this.getParent();
    	int targetActivity = TabbedActivity.INPUT_ID;
    	parentTabbedActivity.switchTab(targetActivity);
	}
	
	/**
	 * Refers to the preceding Tab
	 * @param view
	 */
	public void viewBack(View view){
    	TabbedActivity parentTabbedActivity = (TabbedActivity)this.getParent();
    	int targetActivity = TabbedActivity.EQUIPMENT_ID;
    	parentTabbedActivity.switchTab(targetActivity);
	}
	
	/**
	 * Saves current input data to the calculator bean
	 */
	private void saveData() {
		Calculator calculator = parentTabbedActivity.getCalculator();
	
		calculator.getCustomer().getLocation().getRoof().getBanks().get(0).setNumberOfPanels(new Integer((int)Math.round(new Double(((EditText)findViewById(R.id.editRoof_Banks_PanelsBank1)).getText().toString()))));
		calculator.getCustomer().getLocation().getRoof().getBanks().get(0).setAngle(new Double(((EditText)findViewById(R.id.editRoof__AngleOfBank1)).getText().toString()));
		calculator.getCustomer().getLocation().getRoof().getBanks().get(1).setNumberOfPanels(new Integer((int)Math.round(new Double(((EditText)findViewById(R.id.editRoof_Banks_PanelsBank2)).getText().toString()))));
		calculator.getCustomer().getLocation().getRoof().getBanks().get(1).setAngle(new Double(((EditText)findViewById(R.id.editRoof__AngleOfBank2)).getText().toString()));
		
		Spinner bank1Orientation = (Spinner)findViewById(R.id.spinnerRoof__OrientationOfBank1);
		Spinner bank2Orientation = (Spinner)findViewById(R.id.spinnerRoof__OrientationOfBank2);
		
		calculator.getCustomer().getLocation().getRoof().getBanks().get(0).setSelectedOrientation(bank1Orientation.getSelectedItem().toString());
		calculator.getCustomer().getLocation().getRoof().getBanks().get(1).setSelectedOrientation(bank2Orientation.getSelectedItem().toString());
		
		
	}
	
	/**
	 * Populates input fields with data in the calculator bean
	 */
	private void loadData() {
		Calculator calculator = parentTabbedActivity.getCalculator();
		
		//Number of panels from equipment:
		TextView numberOfTotalPanels = (TextView)findViewById(R.id.textRoof_ShowNumberOfPanels);
		numberOfTotalPanels.setText(new Integer(calculator.getEquipment().getTotalPanels()).toString());
		
		//Edit fields
		EditText numberOfPanels1 = (EditText)findViewById(R.id.editRoof_Banks_PanelsBank1);
		numberOfPanels1.setText(new Double(calculator.getCustomer().getLocation().getRoof().getBanks().get(0).getNumberOfPanels()).toString());
		
		EditText angle1 = (EditText)findViewById(R.id.editRoof__AngleOfBank1);
		angle1.setText(new Double(calculator.getCustomer().getLocation().getRoof().getBanks().get(0).getAngle()).toString());
		
		EditText numberOfPanels2 = (EditText)findViewById(R.id.editRoof_Banks_PanelsBank2);
		numberOfPanels2.setText(new Double(calculator.getCustomer().getLocation().getRoof().getBanks().get(1).getNumberOfPanels()).toString());
		
		EditText angle2 = (EditText)findViewById(R.id.editRoof__AngleOfBank2);
		angle2.setText(new Double(calculator.getCustomer().getLocation().getRoof().getBanks().get(1).getAngle()).toString());
	
	
		
		//TODO Load spinner state
		
	}
	
	@Override
	public void onPause() {
		super.onPause();
		saveData();
		state = STATE_PAUSED;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		loadData();
		state = STATE_NORMAL;
	}
	
	/**
	 * Retrieves the state of the activity
	 * @return
	 */
	public int getState() {
		return state;
	}

}

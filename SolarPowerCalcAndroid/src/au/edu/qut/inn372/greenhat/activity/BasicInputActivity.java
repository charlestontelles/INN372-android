package au.edu.qut.inn372.greenhat.activity;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import au.edu.qut.inn372.greenhat.bean.Calculator;
import au.edu.qut.inn372.greenhat.bean.Roof;

public class BasicInputActivity extends Activity {
	// Constant for identifying the dialog
	private static final int DIALOG_ALERT = 10;
	public final static int STATE_NORMAL = 0;
	public final static int STATE_PAUSED = 1;
	private int state;

	private TabbedActivity parentTabbedActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basic_input);
		parentTabbedActivity = (TabbedActivity) this.getParent();
		state = STATE_NORMAL;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_basic_input, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	/**
	 * Customises the dialogs used in the Activity
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_ALERT:
			// Create out AlterDialog
			Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Calculation Saved.");
			builder.setCancelable(false);
			builder.setPositiveButton("OK", new OkOnClickListener());
			AlertDialog dialog = builder.create();
			dialog.show();
		}
		return super.onCreateDialog(id);
	}
	/**
	 * handle on click button in the dialog
	 * 
	 */
	private final class OkOnClickListener implements
			DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	}

	/**
	 * Performs the calculations (Soap call) and changed to output activity
	 * 
	 * @param view
	 */
	public void calculate(View view) {
		saveData();

		parentTabbedActivity.calcEnergyProduction();

		Intent intent = new Intent(this, TabbedOutputActivity.class);
		intent.putExtra("Calculator", parentTabbedActivity.getCalculator());

		startActivity(intent);

	}

	/**
	 * Performs the calculations (Soap call) and changed to output activity
	 * 
	 * @param view
	 */
	public void save(View view) {

		String result = parentTabbedActivity.saveCalculation();

		showDialog(DIALOG_ALERT);

	}

	/**
	 * Performed when the reset button is pressed This functionality should be
	 * changed to reset all values to defaults rather than just reloading data
	 * 
	 * @param view
	 */
	public void reset(View view) {
		loadData();
	}

	/**
	 * Saves current input data to the calculator bean
	 */
	private void saveData() {

		Calculator calculator = parentTabbedActivity.getCalculator();

		// Current Energy User Details
		calculator
				.getCustomer()
				.getElectricityUsage()
				.setDailyAverageUsage(
						new Double(
								((EditText) findViewById(R.id.editRoof_Usage_UsagePerDay))
										.getText().toString()));
		calculator
				.getCustomer()
				.getElectricityUsage()
				.setDayTimeHourlyUsage(
						new Double(
								((EditText) findViewById(R.id.editRoof_Usage_UsagePerDaylight))
										.getText().toString()));
		calculator
				.getCustomer()
				.getTariff()
				.setFeedInfee(
						new Double(
								((EditText) findViewById(R.id.editRoof_Usage_FeedInFee))
										.getText().toString()));
		calculator
				.getCustomer()
				.getTariff()
				.setTariff11Fee(
						new Double(
								((EditText) findViewById(R.id.editRoof_Usage_Tariff))
										.getText().toString()));
		// Roof

		EditText bank1AngleView = (EditText) findViewById(R.id.editRoof_Bank1_Angle);
		TextView bank1OrientationView = (TextView) findViewById(R.id.editRoof_Bank1_Orientaton);
		EditText bank2AngleView = (EditText) findViewById(R.id.editRoof_Bank2_Angle);
		TextView bank2OrientationView = (TextView) findViewById(R.id.editRoof_Bank2_Orientation);

		Roof roof = calculator.getCustomer().getLocation().getRoof();
		roof.getBanks()
				.get(0)
				.setNumberOfPanels(
						new Integer(
								(int) Math
										.round(new Double(
												((EditText) findViewById(R.id.editRoof_Bank1_NumPanels))
														.getText().toString()))));
		roof.getBanks().get(0)
				.setAngle(new Double(bank1AngleView.getText().toString()));

		roof.getBanks()
				.get(1)
				.setNumberOfPanels(
						new Integer(
								(int) Math
										.round(new Double(
												((EditText) findViewById(R.id.editRoof_Bank2_NumPanels))
														.getText().toString()))));
		roof.getBanks().get(1)
				.setAngle(new Double(bank2AngleView.getText().toString()));

		// Sunlight Details
		calculator
				.getCustomer()
				.getLocation()
				.setSunLightHours(
						new Double(
								((EditText) findViewById(R.id.editRoof_Sunlight_Daylight))
										.getText().toString()));
	}

	/**
	 * Populates input fields with data in the calculator bean
	 */
	private void loadData() {
		Calculator calculator = parentTabbedActivity.getCalculator();

		// Equipment
		TextView systemSizeView = (TextView) findViewById(R.id.editEquipment_Size);
		systemSizeView.setText(new Double(calculator.getEquipment().getSize())
				.toString());

		TextView inverterEfficiencyView = (TextView) findViewById(R.id.editEquiment_InverterEfficiency);
		inverterEfficiencyView.setText(new Double(calculator.getEquipment()
				.getInverter().getEfficiency()).toString());

		// Usage
		EditText inputDailyAverage = (EditText) findViewById(R.id.editRoof_Usage_UsagePerDay);
		inputDailyAverage.setText(new Double(calculator.getCustomer()
				.getElectricityUsage().getDailyAverageUsage()).toString());

		EditText inputTimeHourlyUsage = (EditText) findViewById(R.id.editRoof_Usage_UsagePerDaylight);
		inputTimeHourlyUsage.setText(new Double(calculator.getCustomer()
				.getElectricityUsage().getDayTimeHourlyUsage()).toString());

		EditText inputFeedInFee = (EditText) findViewById(R.id.editRoof_Usage_FeedInFee);
		inputFeedInFee.setText(new Double(calculator.getCustomer().getTariff()
				.getFeedInfee()).toString());

		EditText inputAnnualTariff11Cost = (EditText) findViewById(R.id.editRoof_Usage_Tariff);
		inputAnnualTariff11Cost.setText(new Double(calculator.getCustomer()
				.getTariff().getTariff11Fee()).toString());

		// Roof
		EditText bank1NumPanelsView = (EditText) findViewById(R.id.editRoof_Bank1_NumPanels);
		EditText bank1AngleView = (EditText) findViewById(R.id.editRoof_Bank1_Angle);
		TextView bank1OrientationView = (TextView) findViewById(R.id.editRoof_Bank1_Orientaton);
		EditText bank2NumPanelsView = (EditText) findViewById(R.id.editRoof_Bank2_NumPanels);
		EditText bank2AngleView = (EditText) findViewById(R.id.editRoof_Bank2_Angle);
		TextView bank2OrientationView = (TextView) findViewById(R.id.editRoof_Bank2_Orientation);

		bank1NumPanelsView.setText(new Integer(calculator.getCustomer()
				.getLocation().getRoof().getBanks().get(0).getNumberOfPanels())
				.toString());
		bank1AngleView.setText(new Double(calculator.getCustomer()
				.getLocation().getRoof().getBanks().get(0).getAngle())
				.toString());
		bank1OrientationView.setText(calculator.getCustomer().getLocation()
				.getRoof().getBanks().get(0).getSelectedOrientation());

		bank2NumPanelsView.setText(new Integer(calculator.getCustomer()
				.getLocation().getRoof().getBanks().get(1).getNumberOfPanels())
				.toString());
		bank2AngleView.setText(new Double(calculator.getCustomer()
				.getLocation().getRoof().getBanks().get(1).getAngle())
				.toString());
		bank2OrientationView.setText(calculator.getCustomer().getLocation()
				.getRoof().getBanks().get(1).getSelectedOrientation());

		// Sunlight Details
		EditText inputDailyHours = (EditText) findViewById(R.id.editRoof_Sunlight_Daylight);
		inputDailyHours.setText(new Double(calculator.getCustomer()
				.getLocation().getSunLightHours()).toString());
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
	 * 
	 * @return
	 */
	public int getState() {
		return state;
	}

}

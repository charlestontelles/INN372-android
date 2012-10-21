package au.edu.qut.inn372.greenhat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import au.edu.qut.inn372.greenhat.bean.Calculator;

public class CustomerUsageActivity extends Activity implements InputActivity {
	
		public final static int STATE_NORMAL = 0;
		public final static int STATE_PAUSED = 1;
		private int state;
		
		private TabbedActivity parentTabbedActivity;

		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_customer_usage_input);
	        //getActionBar().setDisplayHomeAsUpEnabled(true);
	        parentTabbedActivity = (TabbedActivity)this.getParent();
	    }
		
		/**
		 * Saves current input data to the calculator bean
		 */
		public void saveData() {
			Calculator calculator = parentTabbedActivity.getCalculator();
			calculator.getCustomer().getElectricityUsage().setDailyAverageUsage(new Double(((EditText)findViewById(R.id.editCustomerUsage_CurrentUsage_UsagePerDay)).getText().toString()));
			calculator.getCustomer().getElectricityUsage().setDayTimeHourlyUsage(new Double(((EditText)findViewById(R.id.editCustomerUsage_CurrentUsage_UsagePerHour)).getText().toString()));
			calculator.getCustomer().getTariff().setTariff11Fee(new Double(((EditText)findViewById(R.id.editCustomerUsage_CurrentTariff_Tariff)).getText().toString()));
			calculator.getCustomer().getTariff().setFeedInfee(new Double(((EditText)findViewById(R.id.editCustomerUsage_CurrentTariff_FeedIn)).getText().toString()));
			//TODO Insert input fields for other information
		}
		
		/**
		 * Populates input fields with data in the calculator bean
		 */
		private void loadData() {
			Calculator calculator = parentTabbedActivity.getCalculator();
			EditText inputDailyAverage = (EditText)findViewById(R.id.editCustomerUsage_CurrentUsage_UsagePerDay);
			inputDailyAverage.setText(new Double(calculator.getCustomer().getElectricityUsage().getDailyAverageUsage()).toString());
			
			EditText inputDayTimeHourly = (EditText)findViewById(R.id.editCustomerUsage_CurrentUsage_UsagePerHour);
			inputDayTimeHourly.setText(new Double(calculator.getCustomer().getElectricityUsage().getDayTimeHourlyUsage()).toString());
			
			EditText inputTariff11Fee = (EditText)findViewById(R.id.editCustomerUsage_CurrentTariff_Tariff);
			inputTariff11Fee.setText(new Double(calculator.getCustomer().getTariff().getTariff11Fee()).toString());
			
			EditText inputFeedInFee = (EditText)findViewById(R.id.editCustomerUsage_CurrentTariff_FeedIn);
			inputFeedInFee.setText(new Double(calculator.getCustomer().getTariff().getFeedInfee()).toString());
			
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
			((TabbedActivity)this.getParent()).setTabId(TabbedActivity.USAGE_ID);
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

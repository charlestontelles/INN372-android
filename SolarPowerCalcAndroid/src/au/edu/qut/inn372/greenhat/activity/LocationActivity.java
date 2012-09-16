package au.edu.qut.inn372.greenhat.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class LocationActivity extends Activity{
	
	//add this 
		public final static String EXTRA_MESSAGE = "au.edu.qut.inn372.inn372.greenhat.activity.LocationActivity";
		public final static String EXTRA_MESSAGE2 = "au.edu.qut.inn372.inn372.greenhat.activity.LocationActivity2";
		public final static int STATE_NORMAL = 0;
		public final static int STATE_PAUSED = 1;
		private int state;
			
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_location_input);
	        //getActionBar().setDisplayHomeAsUpEnabled(true);
	    }
	    
	    /*@Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.location_input, menu);
	        return true;
	    }*/

	    
	    /*@Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	            case android.R.id.home:
	                NavUtils.navigateUpFromSameTask(this);
	                return true;
	        }
	        return super.onOptionsItemSelected(item);
	    }*/
	    
	    /**
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
	    	int targetActivity = TabbedActivity.ROOF_ID;
	    	parentTabbedActivity.switchTab(targetActivity);
	    }
	    
		/**
		 * Saves current input data to the calculator bean
		 */
		private void saveData() {
			
		}
		
		/**
		 * Populates input fields with data in the calculator bean
		 */
		private void loadData() {
			
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


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
}


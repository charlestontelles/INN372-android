package au.edu.qut.inn372.greenhat.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import au.edu.qut.inn372.greenhat.bean.Calculator;
import au.edu.qut.inn372.greenhat.bean.Location;

public class LocationActivity extends Activity implements OnItemSelectedListener {
	
		public final static int STATE_NORMAL = 0;
		public final static int STATE_PAUSED = 1;
		private int state;
		private List<Location> locations = new ArrayList<Location>();
		private Calculator calculator;
			
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_location_input);
	        setupLocations();
	        setupSpinner();
	        TabbedActivity parentTabbedActivity = (TabbedActivity)this.getParent();
	        calculator = parentTabbedActivity.getCalculator();
	    }
	    
	    /**
	     * Sets up the local locations list with a set of location and corresponding sunlight hours
	     * NOTE: This will eventually be replaced with a WS call
	     */
	    private void setupLocations() {
			Location brisbane = new Location();
			brisbane.setCity("Brisbane");
			brisbane.setSunLightHours(9/2.0);
			Location sydney = new Location();
			sydney.setCity("Sydney");
			sydney.setSunLightHours(8/2.0);
			Location melbourne = new Location();
			melbourne.setCity("Melbourne");
			melbourne.setSunLightHours(5/2.0);
			Location canberra = new Location();
			canberra.setCity("Canberra");
			canberra.setSunLightHours(6/2.0);
			Location perth = new Location();
			perth.setCity("Perth");
			perth.setSunLightHours(8/2.0);
			Location hobart = new Location();
			hobart.setCity("Hobart");
			hobart.setSunLightHours(5/2.0);
			Location darwin = new Location();
			darwin.setCity("Darwin");
			darwin.setSunLightHours(9/2.0);
			
			locations.add(brisbane);
			locations.add(sydney);
			locations.add(melbourne);
			locations.add(canberra);
			locations.add(perth);
			locations.add(hobart);
			locations.add(darwin);
	    }
	    
	    /**
	     * Sets up the location spinner with a list of locations
	     */
	    private void setupSpinner() {
			Spinner citySpinner = (Spinner)findViewById(R.id.city_spinner);

			ArrayList<String> spinnerArray = new ArrayList<String>();
			for(Location curLocation : locations) {
				spinnerArray.add(curLocation.getCity());
			}
			ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
			citySpinner.setAdapter(spinnerArrayAdapter);
			citySpinner.setOnItemSelectedListener(this); 
	    }
	    
	    /**
	     * Refers to the succeding tab
	     * @param view
	     */
	    public void viewNext(View view){
	    	TabbedActivity parentTabbedActivity = (TabbedActivity)this.getParent();
	    	int targetActivity = TabbedActivity.USAGE_ID;
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
	    
		/**
		 * Saves current input data to the calculator bean
		 */
		private void saveData() {
			Spinner citySpinner = (Spinner)findViewById(R.id.city_spinner);
			EditText sunlightHours = (EditText)findViewById(R.id.editLocation_Sunlight);
			EditText roofWidth = (EditText)findViewById(R.id.editLocation_RoofWidth);
			EditText roofHeight = (EditText)findViewById(R.id.editLocation_RoofHeight);
			Location location = calculator.getCustomer().getLocation();
			location.setCity((String)citySpinner.getSelectedItem());
			location.setSunLightHours(new Double(sunlightHours.getText().toString()));
			location.getRoof().setWidth(new Double(roofWidth.getText().toString()));
			location.getRoof().setHeight(new Double(roofHeight.getText().toString()));
		}
		
		/**
		 * Populates input fields with data in the calculator bean
		 */
		private void loadData() {
			Spinner citySpinner = (Spinner)findViewById(R.id.city_spinner);
			EditText sunlightHours = (EditText)findViewById(R.id.editLocation_Sunlight);
			EditText roofWidth = (EditText)findViewById(R.id.editLocation_RoofWidth);
			EditText roofHeight = (EditText)findViewById(R.id.editLocation_RoofHeight);
			Location location = calculator.getCustomer().getLocation();
			citySpinner.setSelection(getCityPosition(location.getCity()));
			sunlightHours.setText(new Double(location.getSunLightHours()).toString());
			roofWidth.setText(new Double(location.getRoof().getWidth()).toString());
			roofHeight.setText(new Double(location.getRoof().getHeight()).toString());
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

		/**
		 * Updates the sunlight hours when a location is selected
		 */
		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			EditText sunlightHours = (EditText)findViewById(R.id.editLocation_Sunlight);
			sunlightHours.setText(new Double(getSunlightHours(pos)).toString());
		}

		/**
		 * Looks up the sunlight hours corresponding to a city
		 * @param city The selected city
		 */
		private double getSunlightHours(int pos) {
			return locations.get(pos).getSunLightHours();
		}
		
		/**
		 * Returns the position in the city list for the given city name
		 * @param city The city name
		 * @return The city's position in the List
		 */
		private int getCityPosition(String city) {
			for(int i = 0; i < locations.size(); i++) {
				if(locations.get(i).getCity() == city)
					return i;
			}
			return 0;
		}
		
		/**
		 * Unused method required by interface
		 */
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
}


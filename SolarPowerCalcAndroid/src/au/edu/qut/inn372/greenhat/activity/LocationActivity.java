package au.edu.qut.inn372.greenhat.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.itextpdf.text.pdf.TextField;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import au.edu.qut.inn372.greenhat.bean.Calculator;
import au.edu.qut.inn372.greenhat.bean.Location;

public class LocationActivity extends Activity implements OnItemSelectedListener, LocationListener {//need to comment LocationListener for emulator
	
		public final static int STATE_NORMAL = 0;
		public final static int STATE_PAUSED = 1;
		private int state;
		private List<Location> locations = new ArrayList<Location>();
		private Calculator calculator;
		private LocationManager locationManager;
		private Geocoder geocoder;
			
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_location_input);
	        setupLocations();
	        setupSpinner();
	        TabbedActivity parentTabbedActivity = (TabbedActivity)this.getParent();
	        calculator = parentTabbedActivity.getCalculator();
	        //need to comment from here for emulator
	        //Initializing fields for location
	        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
	        geocoder = new Geocoder(this);
	        
	        //TODO: Enter criteria to find a suitable provider
	        LocationProvider provider = locationManager.getProvider(LocationManager.NETWORK_PROVIDER);
	        
	    	//TODO implement message to show when no provider available
	    	
	    	//TODO verify location provider is enabled
	    	
	        //get the last know location
	    	android.location.Location location = locationManager.getLastKnownLocation(provider.getName());
	    	
	    	//call method to get location details and show them
	    	this.onLocationChanged(location); //to here
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
			//TODO: implement dialog that asks before leaving
			
			finish();
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
			//removes the locationManager to save battery
			locationManager.removeUpdates(this);//need to comment for emulator
			state = STATE_PAUSED;
		}
		
		@Override
		public void onResume() {
			super.onResume();
			//refreshes the locationManager
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, this);//need to comment this for emulator
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
		
		public void homeButton (View view){
			TabbedActivity parentTabbedActivity = (TabbedActivity)this.getParent();
			parentTabbedActivity.finish();
		}
		
		/**
		 * Gets the latest location details and shows them
		 */
		public void onLocationChanged(android.location.Location location) {
			// TODO Auto-generated method stub
			double lat = (double) location.getLatitude();
			double lon = (double) location.getLongitude();
			TextView locationLat = (TextView)findViewById(R.id.textLocation_AutomaticLocationShow_Latitude);
			TextView locationLon = (TextView)findViewById(R.id.textLocation_AutomaticLocationShow_Longitude);
			TextView locationShow = (TextView)findViewById(R.id.textLocation_AutomaticLocationShow_Zip);
			
		    
		    locationLat.setText(""+lat);
		    locationLon.setText(""+lon);
			
			try {
		      List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 10); //<10>
		      
		      Address address = addresses.get(0);
		      
		      //locationShow.setText(address.getAddressLine(1));
		      locationShow.setText(address.getPostalCode());
		      
		    } catch (IOException e) {
		    	e.getStackTrace();
		    	//Log.e("LocateMe", "Could not get Geocoder data", e);
		    }
		}

		 
		public void onProviderEnabled(String provider) {
			Toast.makeText(this, "Enabled new provider " + provider,
	        Toast.LENGTH_SHORT).show();
	  	}

	  
	 	public void onProviderDisabled(String provider) {
	 		Toast.makeText(this, "Disabled provider " + provider,
	        Toast.LENGTH_SHORT).show();
	 	}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
}




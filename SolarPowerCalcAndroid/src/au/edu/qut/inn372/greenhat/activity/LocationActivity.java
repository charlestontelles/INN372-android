package au.edu.qut.inn372.greenhat.activity;

import java.io.IOException;
import java.security.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.itextpdf.text.pdf.TextField;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
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
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import au.edu.qut.inn372.greenhat.bean.Calculator;
import au.edu.qut.inn372.greenhat.bean.Location;
import au.edu.qut.inn372.greenhat.ws.SoapClient;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class LocationActivity extends MapActivity implements LocationListener, InputActivity {//need to comment LocationListener for emulator
	
		public final static int STATE_NORMAL = 0;
		public final static int STATE_PAUSED = 1;
		public final static int DIALOG_ENABLE_LOCATION = 1;
		public final static int NO_LOCATION_SERVICE_AVAILABLE = 2;
		private int state;
		private List<Location> locations = new ArrayList<Location>();
		private Calculator calculator;
		private LocationManager locationManager;
		private Geocoder geocoder;
		private boolean locationProviderEnabled = false;
		private Boolean mapViewInitialised = false;
		protected MapView mapView; 
		TabbedActivity parentTabbedActivity;
			
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_location_input);
	        //setupLocations();
	        //setupSpinner();
	        parentTabbedActivity = (TabbedActivity)this.getParent(); //made it global
	        calculator = parentTabbedActivity.getCalculator();
	        
	        
	        //Initializing fields for location
	        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
	        geocoder = new Geocoder(this);
	    	
	        mapView = (MapView) findViewById(R.id.mapview);
	        mapView.setBuiltInZoomControls(true);
	    }
	    
	    /**
	     * Checks if the location provider is valid.
	     * Sets the locationProviderEnabled variable
	     * Launches a dialog for user action if the location provider is not present
	     */
	    private void hasLocationProvider() {
	    	//writes the status of Location_Provider into a string, which content than can be checked
	        String providerEnabled = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
	        if (providerEnabled.contains("network") == true){
	        	locationProviderEnabled = true;
	        	}
        	else{
        		locationProviderEnabled = false;
        		showDialog(DIALOG_ENABLE_LOCATION);
        	}
	    }
	    
	    /**
	     * Checks that the location object is valid
	     * @return true if the location can be retrieved
	     */
	    private void launchLocationUpdate() {
	    	//TODO: Enter criteria to find a suitable provider
	        LocationProvider provider = locationManager.getProvider(LocationManager.NETWORK_PROVIDER);
	        //TODO implement message to show when no provider available
	    	//get the last known location
	    	android.location.Location location = locationManager.getLastKnownLocation(provider.getName());
	    	//call method to get location details and show them
	    	this.onLocationChanged(location);
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
	    
//	    /**
//	     * Sets up the location spinner with a list of locations
//	     */
//	    private void setupSpinner() {
//			Spinner citySpinner = (Spinner)findViewById(R.id.city_spinner);
//
//			ArrayList<String> spinnerArray = new ArrayList<String>();
//			for(Location curLocation : locations) {
//				spinnerArray.add(curLocation.getCity());
//			}
//			ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
//			citySpinner.setAdapter(spinnerArrayAdapter);
//			citySpinner.setOnItemSelectedListener(this); 
//	    }
	    
		/**
		 * Saves current input data to the calculator bean
		 */
		public void saveData() {
			//Spinner citySpinner = (Spinner)findViewById(R.id.city_spinner);
			//EditText sunlightHours = (EditText)findViewById(R.id.editLocation_Sunlight);
			//EditText roofWidth = (EditText)findViewById(R.id.editLocation_RoofWidth);
			//EditText roofHeight = (EditText)findViewById(R.id.editLocation_RoofHeight);
			Location location = calculator.getCustomer().getLocation();
			//location.setCity((String)citySpinner.getSelectedItem());
			//location.setSunLightHours(new Double("4.5"));
			//location.getRoof().setWidth(new Double(roofWidth.getText().toString()));
			//location.getRoof().setHeight(new Double(roofHeight.getText().toString()));
		}
		
		/**
		 * Populates input fields with data in the calculator bean
		 */
		private void loadData() {
			//Spinner citySpinner = (Spinner)findViewById(R.id.city_spinner);
			//EditText sunlightHours = (EditText)findViewById(R.id.editLocation_Sunlight);
			//EditText roofWidth = (EditText)findViewById(R.id.editLocation_RoofWidth);
			//EditText roofHeight = (EditText)findViewById(R.id.editLocation_RoofHeight);
			Location location = calculator.getCustomer().getLocation();
			//citySpinner.setSelection(getCityPosition(location.getCity()));
			//sunlightHours.setText(new Double(location.getSunLightHours()).toString());
			//roofWidth.setText(new Double(location.getRoof().getWidth()).toString());
			//roofHeight.setText(new Double(location.getRoof().getHeight()).toString());
		}
		
		@Override
		public void onPause() {
			super.onPause();
			saveSunlightHours();
			saveData();
			//removes the locationManager to save battery
			if (locationProviderEnabled) {
				locationManager.removeUpdates(this);//need to comment for emulator
			}
			state = STATE_PAUSED;
		}
		
		@Override
		public void onResume() {
			super.onResume();
			((TabbedActivity)this.getParent()).setTabId(TabbedActivity.LOCATION_ID);
			//refreshes the locationManager
			hasLocationProvider(); //Need to check if the user has disabled or enabled the location provider while activity has been paused
			if (locationProviderEnabled) {
				locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, this);//need to comment this for emulator
				launchLocationUpdate();
			}
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

//		/**
//		 * Updates the sunlight hours when a location is selected
//		 */
//		public void onItemSelected(AdapterView<?> parent, View view, int pos,
//				long id) {
//			EditText sunlightHours = (EditText)findViewById(R.id.editLocation_Sunlight);
//			sunlightHours.setText(new Double(getSunlightHours(pos)).toString());
//		}

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
		
		/**
		 * Gets the latest location details and shows them
		 */
		public void onLocationChanged(android.location.Location location) {
			
			if (locationProviderEnabled) {
				if (location != null) {
					// TODO Auto-generated method stub
					double lat = (double) location.getLatitude();
					double lon = (double) location.getLongitude();
					//TextView locationLat = (TextView)findViewById(R.id.textLocation_AutomaticLocationShow_Latitude);
					//TextView locationLon = (TextView)findViewById(R.id.textLocation_AutomaticLocationShow_Longitude);
					//TextView locationShow = (TextView)findViewById(R.id.textLocation_Currentlocation_Zip);
					TextView locationStreet = (TextView)findViewById(R.id.editLocation_Currentlocation_Street); 
					
				    
				    //locationLat.setText(""+lat);
				    //locationLon.setText(""+lon);
				    
				    if (mapViewInitialised == false){
					    //MapView mapView = (MapView) findViewById(R.id.mapview);
					    MapController mapController = mapView.getController();
					    GeoPoint geoPoint = new GeoPoint((int) (lat* 1E6), (int) (lon * 1E6));
					    mapController.animateTo(geoPoint);
					    mapController.setZoom(17);
					    
					  //---Add a location marker---
				        MapOverlay mapOverlay = new MapOverlay(geoPoint);
				        List<Overlay> listOfOverlays = mapView.getOverlays();
				        listOfOverlays.clear();
				        listOfOverlays.add(mapOverlay);  
					    mapView.invalidate();
					    
					    //get the average sunlight hours for the location
					    
					    
					    mapViewInitialised=true;
				    }
				    
					
					try {
				      List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); //<10>
				      
				      //Address address = addresses.get(0);
				      
				      String add = "";
	                    if (addresses.size() > 0) 
	                    {
	                        for (int i=0; i<addresses.get(0).getMaxAddressLineIndex(); 
	                             i++)
	                        { 
	                        	System.out.println(""+i+" = "+addresses.get(0).getAddressLine(i));
	                        	add += addresses.get(0).getAddressLine(i) + "\n";}
	                    }
				      
				      //locationShow.setText(address.getAddressLine(1));
				      //locationShow.setText(address.getPostalCode());
				      locationStreet.setText(add);
				      
				    } catch (IOException e) {
				    	e.getStackTrace();
				    	//Log.e("LocateMe", "Could not get Geocoder data", e);
				    }
				}
				else {
					//If location could not be retrieved, display an error
					showDialog(NO_LOCATION_SERVICE_AVAILABLE);
				}
			}
		}

		 
		public void onProviderEnabled(String provider) {
			locationProviderEnabled = true;
			Toast.makeText(this, "Enabled new provider " + provider,
	        Toast.LENGTH_SHORT).show();
	  	}

	  
	 	public void onProviderDisabled(String provider) {
	 		locationProviderEnabled = false;
	 		Toast.makeText(this, "Disabled provider " + provider,
	        Toast.LENGTH_SHORT).show();
	 	}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
		/**
		 * Starts the location service, used when it is not enabled
		 */
		private void enableLocationSettings() {
		    Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		    startActivity(settingsIntent);
		}
		
		
		/**
		 * Creates different types of dialog selected with id
		 */
		@Override
		  protected Dialog onCreateDialog(int id) {
			Dialog dialog;
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    switch (id) {
		    case DIALOG_ENABLE_LOCATION:
			    // Create out AlterDialog
			    
			    builder.setMessage("To find your location with your device please enable your location service.");
			    builder.setCancelable(true);
			    builder.setPositiveButton("Enable Location Service", new EnableLocationServiceListener());
			    builder.setNegativeButton("Enter Location Manually", new EnterLocationManuallyListener());
			    dialog = builder.create();
		      	break;
		    case NO_LOCATION_SERVICE_AVAILABLE:
			    builder.setMessage("No Locationservice enabled! Please Enter Your Location Manually");
			    builder.setCancelable(true);
			    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
			    dialog = builder.create();
			    break;
		    default:
		    	dialog = null;
		    	break;
		    }
		    return dialog;
		  }
		
		/**
		 * Inner class to react on user input within dialog
		 * Shows a toast to inform about manual input options
		 * @author FK
		 *
		 */
		private final class EnterLocationManuallyListener implements
	      DialogInterface.OnClickListener {
	    public void onClick(DialogInterface dialog, int which) {
	      Toast.makeText(getApplicationContext(), "Use the map or predefined cities to enter your Location.",
	          Toast.LENGTH_LONG).show();
	    }
	  }

	  /**
	   * Inner class to react on user input within dialog
	   * Launches location settings menu to give the user the possibility to enable location services
	   * @author FK
	   *
	   */
	  private final class EnableLocationServiceListener implements
	      DialogInterface.OnClickListener {
	    public void onClick(DialogInterface dialog, int which) {
	      LocationActivity.this.enableLocationSettings();
	    }
	  }
	  
	  @Override
	    protected boolean isRouteDisplayed() {
	        return false;
	    }
	  
	  /**
	   * Private inner class to create an overlay for google maps
	   * @author FK
	   *
	   */
	  private class MapOverlay extends com.google.android.maps.Overlay
	    {
		  	private GeoPoint geoPoint;
		  
		  	public MapOverlay(GeoPoint geoPoint){
		  		this.geoPoint = geoPoint;
		  	}
		  	
		  	@Override
	        public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when){
		  		
	            super.draw(canvas, mapView, shadow);                   
	 
	            //---translate the GeoPoint to screen pixels---
	            Point screenPts = new Point();
	            mapView.getProjection().toPixels(geoPoint, screenPts);
	 
	            //---add the marker---
	            Bitmap bmp = BitmapFactory.decodeResource(
	                getResources(), R.drawable.push_pin);            
	            canvas.drawBitmap(bmp, screenPts.x, screenPts.y-50, null);         
	            return true;
	        }
		  	
		  	//TODO: Try different approach for updating location choosing
		  	@Override
		  	public boolean onTap(GeoPoint p, MapView mapView){
		  		 Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
	                
	                try {
	                    List<Address> addresses = geoCoder.getFromLocation(p.getLatitudeE6()/1E6,p.getLongitudeE6()/1E6, 1);
	 
	                    String add = "";
	                    if (addresses.size() > 0) 
	                    {
	                        for (int i=0; i<addresses.get(0).getMaxAddressLineIndex(); 
	                             i++)
	                        { 
	                        	System.out.println(""+i+" = "+addresses.get(0).getAddressLine(i));
	                        	add += addresses.get(0).getAddressLine(i) + "\n";}
	                    }
	                    
	                  //---Add a location marker---
	                    GeoPoint geoPoint = new GeoPoint((int) (addresses.get(0).getLatitude()*1E6), (int) (addresses.get(0).getLongitude()*1E6));
				        MapOverlay mapOverlay = new MapOverlay(geoPoint);
				        List<Overlay> listOfOverlays = mapView.getOverlays();
				        listOfOverlays.clear();
				        listOfOverlays.add(mapOverlay);  
					    mapView.invalidate();
	 
	                    Toast.makeText(getBaseContext(), add, Toast.LENGTH_SHORT).show();
	                    
	                    TextView changeLocation = (TextView) findViewById(R.id.editLocation_Currentlocation_Street);
	                    changeLocation.setText(add);
	                    
	                    //save the current longitude and latitude in the LocationMediator 
	                    parentTabbedActivity.getLocationMediator().setLatitude(new Double (addresses.get(0).getLatitude()));
	          		    parentTabbedActivity.getLocationMediator().setLongitude(new Double (addresses.get(0).getLongitude()));
	                }
	                catch (IOException e) {                
	                    e.printStackTrace();
	                } 
	                catch (IndexOutOfBoundsException e) {                
	                    e.printStackTrace();
	                    Toast.makeText(getBaseContext(), "No Location Data availabe!" ,Toast.LENGTH_SHORT).show(); //Catch exception thrown when user selects a point with no location data available
	                } 
	                return true;
		  	}  
	    }
	  
	  public void saveSunlightHours(){
		  parentTabbedActivity.getLocationMediator().retrieveAverageSunlight();
		  double averageSunlightHours = parentTabbedActivity.getLocationMediator().getAverageSunligthHours();
		  calculator.getCustomer().getLocation().setSunLightHours(averageSunlightHours);
	  }
		
}




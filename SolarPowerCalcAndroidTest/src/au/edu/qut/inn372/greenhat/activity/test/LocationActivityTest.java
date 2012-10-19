package au.edu.qut.inn372.greenhat.activity.test;

import java.util.concurrent.Callable;

import android.widget.EditText;
import au.edu.qut.inn372.greenhat.activity.R;
import au.edu.qut.inn372.greenhat.activity.TabbedActivity;


//import au.edu.qut.inn372.greenhat.activity.R;

public class LocationActivityTest extends SetupTabbedActivityTest {

	private EditText sunlightHoursEdit;
	private EditText roofWidthEdit;
	private EditText roofHeightEdit;
	private Double roofWidth = 1234.0;
	private Double roofHeight = 2345.0;
	private int currentTab = TabbedActivity.LOCATION_ID;
	private int targetTab = TabbedActivity.USAGE_ID;
	
	public LocationActivityTest() {
		super(SetupTabbedActivityTest.NEW_CALCULATOR); //These tests are based on a new calculator
		
	}
	
	public void setUp() throws Exception {
		super.setUp();
		sunlightHoursEdit = (EditText)solo.getView(R.id.editLocation_Sunlight);
		roofWidthEdit = (EditText)solo.getView(R.id.editLocation_RoofWidth);
		roofHeightEdit = (EditText)solo.getView(R.id.editLocation_RoofHeight);
		
	}
	
	public void testSaveData() {
		testSaveAttributeDouble(roofWidthEdit, roofWidth, targetTab, currentTab, new Callable<Double>() {
			public Double call() {
				return tabbedActivity.getCalculator().getCustomer().getLocation().getRoof().getWidth();
			}
		});
		testSaveAttributeDouble(roofHeightEdit, roofHeight, targetTab, currentTab, new Callable<Double>() {
			public Double call() {
				return tabbedActivity.getCalculator().getCustomer().getLocation().getRoof().getHeight();
			}
		});
	}
	
	public void testLoadData() {
		//test saving by setting calculator properties then switching back to the tab
		switchTab(TabbedActivity.USAGE_ID);
		tabbedActivity.getCalculator().getCustomer().getLocation().getRoof().setWidth(roofWidth);
		tabbedActivity.getCalculator().getCustomer().getLocation().getRoof().setHeight(roofHeight);
		switchTab(TabbedActivity.LOCATION_ID);
		assertEquals(roofWidthEdit.getText().toString(), roofWidth.toString());
		assertEquals(roofHeightEdit.getText().toString(), roofHeight.toString());
		//TODO add test for sunlgiht hours
	}
	
	public void testBack() {
		solo.clickOnButton("Back to Home");
		assertTrue(solo.waitForActivity("UserHomepageActivity"));
	}

	public void testNext() {
		solo.clickOnButton(nextButtonString);
		assertTrue(solo.waitForActivity("CustomerUsageActivity"));
	}
	
	//TODO add test for spinner - check that it updates the daylight hours - do this later as it might change or be removed
	
	//TODO add test for automatic location detection - do this later as we don't know how it will end up looking
}

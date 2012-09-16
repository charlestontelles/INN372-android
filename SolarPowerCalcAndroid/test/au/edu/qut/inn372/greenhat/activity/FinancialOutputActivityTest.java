package au.edu.qut.inn372.greenhat.activity;

import java.text.DecimalFormat;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import au.edu.qut.inn372.greenhat.bean.Calculation;
import au.edu.qut.inn372.greenhat.bean.Calculator;

public class FinancialOutputActivityTest extends
		ActivityInstrumentationTestCase2<FinancialOutputActivity> {
	
	private static final int SYSTEM_COST = 20000;
	private static final double REPLACED_POWER = 3.0;
	private static final int SOLAR_POWER = 15;
	private static final double ANNUAL_GROWTH = 1.05;
	private static final int NUM_YEARS = 25;
	private static final int TIMEOUT = 10000;
	private FinancialOutputActivity activity;
	private TabbedOutputActivity parentActivity;
	private Calculator calculator;
	private DecimalFormat df = new DecimalFormat("#.##");
	private Calculation[] testCalculations;
	
	public FinancialOutputActivityTest(){
		super(FinancialOutputActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		calculator = new Calculator();
		setupTestData();
		
		//Need to use an activityMonitor to get the activity since we are starting it in a thread
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(FinancialOutputActivity.class.getName(), null, false);
		
		//Need to set up the parent as well as the activity depends upon it (otherwise would get null pointer exceptions)
		Intent tabbedOutputIntent = new Intent();
		tabbedOutputIntent.putExtra("Calculator", calculator);
		parentActivity = launchActivityWithIntent("au.edu.qut.inn372.greenhat.activity", TabbedOutputActivity.class, tabbedOutputIntent);
		
		//Change to tab being tested
		parentActivity.runOnUiThread(new Runnable() {
			public void run() {
				parentActivity.switchTab(TabbedOutputActivity.FINANCIAL_ID);
			}
		});
		
		activity = (FinancialOutputActivity) activityMonitor.waitForActivityWithTimeout(TIMEOUT);
	}
	
	@Override
	protected void tearDown() throws Exception {
		parentActivity.finish(); //Explicitly destroy (finish) the parent tabbed activity to prevent exceptions with multiple tests
		super.tearDown();
	}
	
	/**
	 * Test that the activity starts up successfully
	 */
	public void testStartUp(){
		assertTrue(FinancialOutputActivity.class.getName().length() > 0);
		assertNotNull(activity);
	}
	
	/**
	 * Sets up test data in the calculator object that is required to check that it is displayed correctly
	 */
	private void setupTestData() {
		testCalculations = calculator.getCalculations();
		for(int year=0; year<NUM_YEARS; year++) {
			double growthIndex = java.lang.Math.pow(ANNUAL_GROWTH,year);
			testCalculations[year] = new Calculation();
			testCalculations[year].setYear(year);
			testCalculations[year].setTariff11Fee(new Double(0.1*growthIndex));
			testCalculations[year].setDailySolarPower(new Double(SOLAR_POWER*(2-growthIndex)));
			testCalculations[year].setReplacementGeneration(REPLACED_POWER);
			testCalculations[year].setExportedGeneration(testCalculations[year].getDailySolarPower()-testCalculations[year].getReplacementGeneration());
			testCalculations[year].setDailySaving(testCalculations[year].getTariff11Fee()*testCalculations[year].getReplacementGeneration() + 0.5*testCalculations[year].getExportedGeneration());
			testCalculations[year].setAnnualSaving(testCalculations[year].getDailySaving()*365);
			if (year==0) {
				testCalculations[year].setCumulativeSaving(testCalculations[year].getAnnualSaving());
			}
			else {
				testCalculations[year].setCumulativeSaving(testCalculations[year].getAnnualSaving()+testCalculations[year-1].getCumulativeSaving());
			}
		}
		calculator.getEquipment().setCost(SYSTEM_COST);
	}
	
	/**
	 * Tests that the correct data is displayed
	 */
	public void testDisplayData() {
		for(int year=0; year<NUM_YEARS; year++) {
			//Test year fields
			TextView yearView = (TextView)activity.findViewById(year*3);
			assertEquals(""+df.format(testCalculations[year].getYear()+1), yearView.getText().toString());
			
			//Test cumulative savings fields
			TextView savingsView = (TextView)activity.findViewById(year*3+1);
			assertEquals(""+df.format(testCalculations[year].getCumulativeSaving()), savingsView.getText().toString());
			
			//Test ROI fields
			TextView ROIView = (TextView)activity.findViewById(year*3+2);
			assertEquals(""+df.format(testCalculations[year].getCumulativeSaving()/calculator.getEquipment().getCost()), ROIView.getText().toString());
		}
	}
	
}

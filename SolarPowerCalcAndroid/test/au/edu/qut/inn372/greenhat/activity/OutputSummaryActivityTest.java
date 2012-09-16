package au.edu.qut.inn372.greenhat.activity;

import java.text.DecimalFormat;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import au.edu.qut.inn372.greenhat.bean.Calculator;

public class OutputSummaryActivityTest extends
		ActivityInstrumentationTestCase2<OutputSummaryActivity> {
	
	private static final int TIMEOUT = 10000;
	private static final Double SYSTEM_COST = new Double(45000);
	private static final Double SYSTEM_SIZE = new Double(4.5);
	private OutputSummaryActivity activity;
	private TabbedOutputActivity parentActivity;
	private Calculator calculator;
	private DecimalFormat df = new DecimalFormat("#.##");
	
	public OutputSummaryActivityTest(){
		super(OutputSummaryActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		calculator = new Calculator();
		calculator.getEquipment().setSize(SYSTEM_SIZE);
		calculator.getEquipment().setCost(SYSTEM_COST);
		
		//Need to use an activityMonitor to get the activity since we are starting it in a thread
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(OutputSummaryActivity.class.getName(), null, false);
		
		//Need to set up the parent as well as the activity depends upon it (otherwise would get null pointer exceptions)
		Intent tabbedOutputIntent = new Intent();
		tabbedOutputIntent.putExtra("Calculator", calculator);
		parentActivity = launchActivityWithIntent("au.edu.qut.inn372.greenhat.activity", TabbedOutputActivity.class, tabbedOutputIntent);
		activity = (OutputSummaryActivity) activityMonitor.waitForActivityWithTimeout(TIMEOUT);
	}
	
	@Override
	protected void tearDown() throws Exception {
		parentActivity.finish(); //Explicitly destroy (finish) the parent tabbed activity to prevent exceptions with multiple tests
		super.tearDown();
	}
	
	/**
	 * Test that the activity started up correctly
	 */
	public void testStartUp(){
		assertTrue(OutputSummaryActivity.class.getName().length() > 0);
		assertNotNull(activity);
	}
	
	/**
	 * Tests that the correct data is displayed
	 */
	public void testDisplayData() {
		assertEquals(SYSTEM_SIZE.toString(), ((TextView)activity.findViewById(R.id.TextViewSystemSizeField)).getText().toString());
		assertEquals("$"+df.format(SYSTEM_COST), ((TextView)activity.findViewById(R.id.TextViewSystemCostField)).getText().toString());
		//TODO Need to add payback period test
	}
	
}

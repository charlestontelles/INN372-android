package au.edu.qut.inn372.greenhat.activity.test;

//import java.util.List;

import com.jayway.android.robotium.solo.Solo;

import android.widget.EditText;
import au.edu.qut.inn372.greenhat.activity.R;
import au.edu.qut.inn372.greenhat.activity.TabbedActivity;
import au.edu.qut.inn372.greenhat.activity.TabbedOutputActivity;

public class TabbedOutputComparisonActivityTest extends SetupCalculationOutputTest {

	public TabbedOutputComparisonActivityTest() {
		super(SetupCalculationOutputTest.COMPARE_CALCULATORS); //These tests are based on a new calculator
	}
	
	public void testCalculatorPassedToActivity() {
		assertTrue(tabbedActivity.getCalculators().size()>1);
	}
	//test that both calculators exist in the tabbedoutput activity
	
	//test that menu items are removed
	public void testMenuItemsRemoved() {
		//bring up the menu
		solo.sendKey(Solo.MENU);
		//check that the menu items are not there anymore
		assertFalse(solo.searchText("Save Calculation"));
		assertFalse(solo.searchText("Save as Template"));
		assertFalse(solo.searchText("Generate Pdf"));
	}
	
}

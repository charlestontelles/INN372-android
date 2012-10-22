package au.edu.qut.inn372.greenhat.activity.test;

import java.util.concurrent.Callable;

import android.widget.EditText;
import au.edu.qut.inn372.greenhat.activity.TabbedActivity;

import au.edu.qut.inn372.greenhat.activity.R;

public class EquipmentActivityTest extends SetupCalculationTest {
	
	private int currentTab = TabbedActivity.EQUIPMENT_ID;
	private int targetTab = TabbedActivity.USAGE_ID;
	
	private Double roofHeight = 1234.0;
	private Double roofWidth = 2345.0;
	
	private EditText editRoofHeight;
	private EditText editRoofWidth;

	public EquipmentActivityTest() {
		super(SetupCalculationTest.NEW_CALCULATOR); //These tests are based on a new calculator
	}
	
	public void setUp() throws Exception {
		super.setUp();
		switchTab(currentTab);
		editRoofHeight = (EditText)solo.getView(R.id.editEquipment_roofsize_height);
		editRoofWidth = (EditText)solo.getView(R.id.editEquipment_roofsize_width);
	}
	
	public void testLoadData() {
		switchTab(targetTab);
		tabbedActivity.getCalculator().getCustomer().getLocation().getRoof().setHeight(roofHeight);
		tabbedActivity.getCalculator().getCustomer().getLocation().getRoof().setWidth(roofWidth);
		switchTab(currentTab);
		assertEquals(editRoofHeight.getText().toString(), roofHeight.toString());
		assertEquals(editRoofWidth.getText().toString(), roofWidth.toString());
	}
	
	public void testSaveData() {
		testSaveAttributeDouble(editRoofHeight, roofHeight, targetTab, currentTab, new Callable<Double>() {
			public Double call() {
				return tabbedActivity.getCalculator().getCustomer().getLocation().getRoof().getHeight();
			}
		});
		testSaveAttributeDouble(editRoofWidth, roofWidth, targetTab, currentTab, new Callable<Double>() {
			public Double call() {
				return tabbedActivity.getCalculator().getCustomer().getLocation().getRoof().getWidth();
			}
		});
	}

}

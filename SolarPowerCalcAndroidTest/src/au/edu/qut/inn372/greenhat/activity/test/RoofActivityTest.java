package au.edu.qut.inn372.greenhat.activity.test;

import java.util.concurrent.Callable;

import android.widget.EditText;
import android.widget.TextView;
import au.edu.qut.inn372.greenhat.activity.TabbedActivity;

import au.edu.qut.inn372.greenhat.activity.R;
import au.edu.qut.inn372.greenhat.bean.Roof;

public class RoofActivityTest extends SetupCalculationTest {
	
	private int currentTab = TabbedActivity.ROOF_ID;
	private int targetTab = TabbedActivity.USAGE_ID;
	
	private Integer bank1Panels = 3;
	private Double bank1Angle = 47.2;
	private Integer bank2Panels = 1;
	private Double bank2Angle = 28.3;
	
	private EditText editBank1Panels;
	private EditText editBank1Angle;
	private EditText editBank2Panels;
	private EditText editBank2Angle;

	public RoofActivityTest() {
		super(SetupCalculationTest.NEW_CALCULATOR);
	}
	
	public void setUp() throws Exception {
		super.setUp();
		switchTab(currentTab);
		editBank1Panels = (EditText)solo.getView(R.id.editRoof_Banks_PanelsBank1);
		editBank1Angle = (EditText)solo.getView(R.id.editRoof__AngleOfBank1);
		editBank2Panels = (EditText)solo.getView(R.id.editRoof_Banks_PanelsBank2);
		editBank2Angle = (EditText)solo.getView(R.id.editRoof__AngleOfBank2);
	}
	
	public void testLoadData() {
		switchTab(targetTab);
		//setup calculator values
		Roof roof = tabbedActivity.getCalculator().getCustomer().getLocation().getRoof();
		roof.getBanks().get(0).setNumberOfPanels(bank1Panels);
		roof.getBanks().get(0).setAngle(bank1Angle);
		roof.getBanks().get(1).setNumberOfPanels(bank2Panels);
		roof.getBanks().get(1).setAngle(bank2Angle);
		switchTab(currentTab);
		assertEquals(new Integer(new Double(editBank1Panels.getText().toString()).intValue()).toString(), bank1Panels.toString());
		assertEquals(editBank1Angle.getText().toString(), bank1Angle.toString());
		assertEquals(new Integer(new Double(editBank2Panels.getText().toString()).intValue()).toString(), bank2Panels.toString());
		assertEquals(editBank2Angle.getText().toString(), bank2Angle.toString());
	}
	
	public void testSaveData() {
		testSaveAttributeDouble(editBank1Angle, bank1Angle, targetTab, currentTab, new Callable<Double>() {
			public Double call() {
				return tabbedActivity.getCalculator().getCustomer().getLocation().getRoof().getBanks().get(0).getAngle();
			}
		});
		testSaveAttributeDouble(editBank2Angle, bank2Angle, targetTab, currentTab, new Callable<Double>() {
			public Double call() {
				return tabbedActivity.getCalculator().getCustomer().getLocation().getRoof().getBanks().get(1).getAngle();
			}
		});
		testSaveAttributeInteger(editBank1Panels, bank1Panels, targetTab, currentTab, new Callable<Integer>() {
			public Integer call() {
				return tabbedActivity.getCalculator().getCustomer().getLocation().getRoof().getBanks().get(0).getNumberOfPanels();
			}
		});
		testSaveAttributeInteger(editBank2Panels, bank2Panels, targetTab, currentTab, new Callable<Integer>() {
			public Integer call() {
				return tabbedActivity.getCalculator().getCustomer().getLocation().getRoof().getBanks().get(1).getNumberOfPanels();
			}
		});
	}
	
	
	
}

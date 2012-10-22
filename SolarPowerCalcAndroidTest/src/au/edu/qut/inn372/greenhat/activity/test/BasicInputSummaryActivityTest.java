package au.edu.qut.inn372.greenhat.activity.test;

import java.util.concurrent.Callable;

import android.widget.EditText;
import android.widget.TextView;
import au.edu.qut.inn372.greenhat.activity.R;
import au.edu.qut.inn372.greenhat.activity.TabbedActivity;

public class BasicInputSummaryActivityTest  extends SetupCalculationTest {
	
	private int currentTab = TabbedActivity.INPUT_ID;
	private int targetTab = TabbedActivity.USAGE_ID;
	
	private TextView textEquipmentSize;
	private TextView textEquipmentInverterEfficiency;
	private TextView textBank1Orientation;
	private TextView textBank2Orientation;
	
	private EditText editBank1Panels;
	private EditText editBank1Angle;
	private EditText editBank2Panels;
	private EditText editBank2Angle;
	private EditText editSunlightHours;
	private EditText editDailyEnergy;
	private EditText editDaylightEnergy;
	private EditText editFeedInFee;
	private EditText editTariff11;
	
	private Double equipmentSize = 3.2;
	private Double equipmentInverterEfficiency = 97.2;
	private String bank1Orientation = "West";
	private String bank2Orientation = "East";
	
	private Integer bank1Panels = 4;
	private Double bank1Angle = 13.4;
	private Integer bank2Panels = 3;
	private Double bank2Angle = 14.1;
	private Double sunlightHours = 3.9;
	private Double dailyEnergy = 39.1;
	private Double daylightEnergy = 4.2;
	private Double feedInFee = 0.41;
	private Double tariff11 = 0.2411;
	


	public BasicInputSummaryActivityTest() {
		super(SetupCalculationTest.NEW_CALCULATOR);
	}
	
	public void setUp() throws Exception {
		super.setUp();
		switchTab(currentTab);
		textEquipmentSize = (TextView)solo.getView(R.id.editEquipment_Size);
		textEquipmentInverterEfficiency = (TextView)solo.getView(R.id.editEquiment_InverterEfficiency);
		textBank2Orientation = (TextView)solo.getView(R.id.editRoof_Bank2_Orientation);
		textBank1Orientation = (TextView)solo.getView(R.id.editRoof_Bank1_Orientaton);
		
		editBank1Panels = (EditText)solo.getView(R.id.editRoof_Bank1_NumPanels);
		editBank1Angle = (EditText)solo.getView(R.id.editRoof_Bank1_Angle);
		editBank2Panels = (EditText)solo.getView(R.id.editRoof_Bank2_NumPanels);
		editBank2Angle = (EditText)solo.getView(R.id.editRoof_Bank2_Angle);
		editSunlightHours = (EditText)solo.getView(R.id.editRoof_Sunlight_Daylight);
		editDailyEnergy = (EditText)solo.getView(R.id.editRoof_Usage_UsagePerDay);
		editDaylightEnergy = (EditText)solo.getView(R.id.editRoof_Usage_UsagePerDaylight);
		editFeedInFee = (EditText)solo.getView(R.id.editRoof_Usage_FeedInFee);
		editTariff11 = (EditText)solo.getView(R.id.editRoof_Usage_Tariff);
	}
	
	public void testLoadData() {
		switchTab(TabbedActivity.LOCATION_ID);
		setCalculatorValues();
		//switch to another tab and set all the values again to check that default values aren't saving again when we switch tabs
		switchTab(targetTab);
		setCalculatorValues();
		switchTab(currentTab);
		assertEquals(textEquipmentSize.getText().toString(), equipmentSize.toString());
		assertEquals(textEquipmentInverterEfficiency.getText().toString(), equipmentInverterEfficiency.toString());
		assertEquals(textBank1Orientation.getText().toString(), bank1Orientation.toString());
		assertEquals(textBank2Orientation.getText().toString(), bank2Orientation.toString());
		assertEquals(editBank1Panels.getText().toString(), bank1Panels.toString());
		assertEquals(editBank1Angle.getText().toString(), bank1Angle.toString());
		assertEquals(editBank2Panels.getText().toString(), bank2Panels.toString());
		assertEquals(editBank2Angle.getText().toString(), bank2Angle.toString());
		assertEquals(editSunlightHours.getText().toString(), sunlightHours.toString());
		assertEquals(editDailyEnergy.getText().toString(), dailyEnergy.toString());
		assertEquals(editDaylightEnergy.getText().toString(), daylightEnergy.toString());
		assertEquals(editFeedInFee.getText().toString(), feedInFee.toString());
		assertEquals(editTariff11.getText().toString(), tariff11.toString());
	}
	
	private void setCalculatorValues() {
		tabbedActivity.getCalculator().getEquipment().setSize(equipmentSize);
		tabbedActivity.getCalculator().getEquipment().getInverter().setEfficiency(equipmentInverterEfficiency);
		tabbedActivity.getCalculator().getCustomer().getLocation().getRoof().getBanks().get(0).setSelectedOrientation(bank1Orientation);
		tabbedActivity.getCalculator().getCustomer().getLocation().getRoof().getBanks().get(1).setSelectedOrientation(bank2Orientation);
		tabbedActivity.getCalculator().getCustomer().getLocation().getRoof().getBanks().get(0).setNumberOfPanels(bank1Panels);
		tabbedActivity.getCalculator().getCustomer().getLocation().getRoof().getBanks().get(0).setAngle(bank1Angle);
		tabbedActivity.getCalculator().getCustomer().getLocation().getRoof().getBanks().get(1).setNumberOfPanels(bank2Panels);
		tabbedActivity.getCalculator().getCustomer().getLocation().getRoof().getBanks().get(1).setAngle(bank2Angle);
		tabbedActivity.getCalculator().getCustomer().getLocation().setSunLightHours(sunlightHours);
		tabbedActivity.getCalculator().getCustomer().getElectricityUsage().setDailyAverageUsage(dailyEnergy);
		tabbedActivity.getCalculator().getCustomer().getElectricityUsage().setDayTimeHourlyUsage(daylightEnergy);
		tabbedActivity.getCalculator().getCustomer().getTariff().setFeedInfee(feedInFee);
		tabbedActivity.getCalculator().getCustomer().getTariff().setTariff11Fee(tariff11);
	}
	
	public void testSaveData() {
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
		testSaveAttributeDouble(editSunlightHours, sunlightHours, targetTab, currentTab, new Callable<Double>() {
			public Double call() {
				return tabbedActivity.getCalculator().getCustomer().getLocation().getSunLightHours();
			}
		});
		testSaveAttributeDouble(editDailyEnergy, dailyEnergy, targetTab, currentTab, new Callable<Double>() {
			public Double call() {
				return tabbedActivity.getCalculator().getCustomer().getElectricityUsage().getDailyAverageUsage();
			}
		});
		testSaveAttributeDouble(editDaylightEnergy, daylightEnergy, targetTab, currentTab, new Callable<Double>() {
			public Double call() {
				return tabbedActivity.getCalculator().getCustomer().getElectricityUsage().getDayTimeHourlyUsage();
			}
		});
		testSaveAttributeDouble(editFeedInFee, feedInFee, targetTab, currentTab, new Callable<Double>() {
			public Double call() {
				return tabbedActivity.getCalculator().getCustomer().getTariff().getFeedInfee();
			}
		});
		testSaveAttributeDouble(editTariff11, tariff11, targetTab, currentTab, new Callable<Double>() {
			public Double call() {
				return tabbedActivity.getCalculator().getCustomer().getTariff().getTariff11Fee();
			}
		});
	}
	
}

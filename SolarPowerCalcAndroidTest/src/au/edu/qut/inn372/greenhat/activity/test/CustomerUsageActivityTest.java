package au.edu.qut.inn372.greenhat.activity.test;

import java.util.concurrent.Callable;

import android.widget.EditText;
import au.edu.qut.inn372.greenhat.activity.R;
import au.edu.qut.inn372.greenhat.activity.TabbedActivity;

//import au.edu.qut.inn372.greenhat.activity.R;

public class CustomerUsageActivityTest extends SetupCalculationTest {
	
	private EditText dailyUsageEdit;
	private EditText daylightHoursUsageEdit;
	private EditText tariff11Edit;
	private EditText feedInFeeEdit;
	private Double dailyUsage = 36.2;
	private Double daylightUsage = 3.4;
	private Double tariff11 = 0.2021;
	private Double feedInFee = 0.3542;
	private int currentTab = TabbedActivity.USAGE_ID;
	private int targetTab = TabbedActivity.EQUIPMENT_ID;

	public CustomerUsageActivityTest() {
		super(SetupCalculationTest.NEW_CALCULATOR); //These tests are based on a new calculator
	}
	
	public void setUp() throws Exception {
		super.setUp();
		switchTab(currentTab);
		dailyUsageEdit = (EditText)solo.getView(R.id.editCustomerUsage_CurrentUsage_UsagePerDay);
		daylightHoursUsageEdit = (EditText)solo.getView(R.id.editCustomerUsage_CurrentUsage_UsagePerHour);
		tariff11Edit = (EditText)solo.getView(R.id.editCustomerUsage_CurrentTariff_Tariff);
		feedInFeeEdit = (EditText)solo.getView(R.id.editCustomerUsage_CurrentTariff_FeedIn);
	}
	
	public void testLoadData() {
		switchTab(targetTab);
		tabbedActivity.getCalculator().getCustomer().getElectricityUsage().setDailyAverageUsage(dailyUsage);
		tabbedActivity.getCalculator().getCustomer().getElectricityUsage().setDayTimeHourlyUsage(daylightUsage);
		tabbedActivity.getCalculator().getCustomer().getTariff().setTariff11Fee(tariff11);
		tabbedActivity.getCalculator().getCustomer().getTariff().setFeedInfee(feedInFee);
		switchTab(currentTab);
		assertEquals(dailyUsageEdit.getText().toString(), dailyUsage.toString());
		assertEquals(daylightHoursUsageEdit.getText().toString(), daylightUsage.toString());
		assertEquals(tariff11Edit.getText().toString(), tariff11.toString());
		assertEquals(feedInFeeEdit.getText().toString(), feedInFee.toString());
	}
	
	public void testSaveData() {
		testSaveAttributeDouble(dailyUsageEdit, dailyUsage, targetTab, currentTab, new Callable<Double>() {
			public Double call() {
				return tabbedActivity.getCalculator().getCustomer().getElectricityUsage().getDailyAverageUsage();
			}
		});
		testSaveAttributeDouble(daylightHoursUsageEdit, daylightUsage, targetTab, currentTab, new Callable<Double>() {
			public Double call() {
				return tabbedActivity.getCalculator().getCustomer().getElectricityUsage().getDayTimeHourlyUsage();
			}
		});
		testSaveAttributeDouble(tariff11Edit, tariff11, targetTab, currentTab, new Callable<Double>() {
			public Double call() {
				return tabbedActivity.getCalculator().getCustomer().getTariff().getTariff11Fee();
			}
		});
		testSaveAttributeDouble(feedInFeeEdit, feedInFee, targetTab, currentTab, new Callable<Double>() {
			public Double call() {
				return tabbedActivity.getCalculator().getCustomer().getTariff().getFeedInfee();
			}
		});
	}
}

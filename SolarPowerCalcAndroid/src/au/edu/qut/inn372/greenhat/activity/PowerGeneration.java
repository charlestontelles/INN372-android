package au.edu.qut.inn372.greenhat.activity;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import au.edu.qut.inn372.greenhat.bean.Calculation;
import au.edu.qut.inn372.greenhat.bean.Calculator;

public class PowerGeneration extends Activity {

	private Calculation[] testCalculations;
	private Calculator calculator;
	private int paybackPeriod = 20;
	DecimalFormat df = new DecimalFormat("#.##");
	private TabbedOutputActivity parentTabbedActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.power_generation);
		parentTabbedActivity = (TabbedOutputActivity) getParent();
		calculator = parentTabbedActivity.getCalculator();
		generateView();

	}

	private void generateView() {
		TextView dailyField = (TextView) findViewById(R.id.TextViewDailyField);
		TextView annualField = (TextView) findViewById(R.id.TextViewAnnualField);
		TextView quaterlyField = (TextView) findViewById(R.id.TextViewQuaterlyField);
		TextView netField = (TextView) findViewById(R.id.TextViewNetField);
		TextView quaterlyNet = (TextView) findViewById(R.id.TextViewQuaterlyNetField);
		TextView annualNet = (TextView) findViewById(R.id.TextViewAnnualNetField);
		
		Double dailySolar = new Double(calculator.getCalculations()[0].getDailySolarPower());
		Double dailyNet = new Double(dailySolar - calculator.getCustomer().getElectricityUsage().getDailyAverageUsage());

		
		dailyField.setText("" + df.format(dailySolar));
		annualField.setText("" + df.format(dailySolar * 365));
		quaterlyField.setText(""
				+ df.format(dailySolar * 365 / 4));
		netField.setText(""
				+ (df.format(dailyNet)));
		annualNet.setText(""
				+ (df.format((dailyNet) * 365)));
		quaterlyNet.setText(""
				+ (df.format((dailyNet) * 365 / 4)));
		
	}
}

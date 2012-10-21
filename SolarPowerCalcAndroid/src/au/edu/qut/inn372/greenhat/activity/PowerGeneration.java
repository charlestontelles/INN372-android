package au.edu.qut.inn372.greenhat.activity;

import java.text.DecimalFormat;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import au.edu.qut.inn372.greenhat.bean.Calculator;

public class PowerGeneration extends Activity {

	private List<Calculator> calculatorList;
	DecimalFormat df = new DecimalFormat("#.##");
	private TabbedOutputActivity parentTabbedActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.power_generation);
		parentTabbedActivity = (TabbedOutputActivity) getParent();
		calculatorList = parentTabbedActivity.getCalculators();
		generateView();

	}

	/**
	 * Updates views with data from calculator bean
	 */
	private void generateView() {

		TableLayout table = (TableLayout) findViewById(R.id.TableLayoutOutputPowerGen);
		
		TableRow titleRow = new TableRow(this);
		
		addHeadingView(titleRow, "Power Generation\n(kWh)");
    	for(Calculator curCalculation : calculatorList) {
    		addHeadingView(titleRow, curCalculation.getName());
    	}
    	
    	table.addView(titleRow);
    	
    	//data
    	TableRow daily = new TableRow(this);
    	TableRow annual = new TableRow(this);
    	TableRow quarterly = new TableRow(this);
    	TableRow dailyNet = new TableRow(this);
    	TableRow quarterlyNet = new TableRow(this);
    	TableRow annualNet = new TableRow(this);
    	
    	addHeadingView(daily, "Daily");
    	addHeadingView(annual, "Annual");
    	addHeadingView(quarterly, "Quarterly");
    	addHeadingView(dailyNet, "Daily Net");
    	addHeadingView(quarterlyNet, "Quarterly Net");
    	addHeadingView(annualNet, "Annual Net");
		
		for(Calculator curCalculator : calculatorList) {
			Double dailySolarGen = new Double(curCalculator.getCalculations()[0].getDailySolarPower());
			Double dailyNetGen = new Double(dailySolarGen - curCalculator.getCustomer().getElectricityUsage().getDailyAverageUsage());
			addDataView(daily, "" + df.format(dailySolarGen));
			addDataView(annual, "" + df.format(dailySolarGen * 365));
			addDataView(quarterly, "" + df.format(dailySolarGen * 365 / 4));
			addDataView(dailyNet, "" + (df.format(dailyNetGen)));
			addDataView(quarterlyNet, "" + (df.format((dailyNetGen) * 365 / 4)));
			addDataView(annualNet, "" + (df.format((dailyNetGen) * 365)));
		}
		
		table.addView(daily);
		table.addView(quarterly);
		table.addView(annual);
		table.addView(dailyNet);
		table.addView(quarterlyNet);
		table.addView(annualNet);
	}
	
	private void addHeadingView(TableRow row, String heading) {
    	TextView newView = (TextView) getLayoutInflater().inflate(
				R.layout.output_text_heading_view, null);
    	newView.setText(heading);
		row.addView(newView);
    }
    
    /**
     * Add a new TextView contain a string to a row. This is separate from addHeadingView so that a different textview layout can be used if needed
     * @param row
     * @param heading
     */
    private void addDataView(TableRow row, String value) {
    	TextView newView = (TextView) getLayoutInflater().inflate(
				R.layout.output_text_view, null);
    	newView.setText(value);
		row.addView(newView);
    }
    
    @Override
    public void onBackPressed() {
    	parentTabbedActivity.onBackPressed();
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	parentTabbedActivity.setTabId(TabbedOutputActivity.POWER_GEN_ID);
    }
}

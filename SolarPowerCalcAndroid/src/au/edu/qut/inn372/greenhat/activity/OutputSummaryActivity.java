package au.edu.qut.inn372.greenhat.activity;

import java.text.DecimalFormat;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import au.edu.qut.inn372.greenhat.bean.Calculator;

public class OutputSummaryActivity extends Activity {
	
	private List<Calculator> calculatorList;
	private Calculator calculator;
	private TabbedOutputActivity parentTabbedActivity;
	DecimalFormat df = new DecimalFormat("#.#");
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output_summary);
        parentTabbedActivity = (TabbedOutputActivity)getParent();
        calculatorList = parentTabbedActivity.getCalculators();
        generateView();
	}
	
	/**
	 * Updates views with information from calculator bean
	 */
    private void generateView() {
    	TableLayout table = (TableLayout) findViewById(R.id.TableLayoutOutputSummary);
    	
    	//Heading (Calculator labels)
    	TableRow titleRow = new TableRow(this);
    	
    	//TextView titleYearView = (TextView)getLayoutInflater().inflate(R.layout.output_text_view, null);
    	//titleYearView.setText("Year");
    	//titleRow.addView(titleYearView);
    	
    	titleRow.addView(new LinearLayout(this));
    	
    	
    	for(Calculator curCalculation : calculatorList) {
    		TextView calcHeadingView = (TextView) getLayoutInflater().inflate(R.layout.output_text_view, null);
    		calcHeadingView.setText(curCalculation.getName());
    		titleRow.addView(calcHeadingView);
    	}
    	
    	table.addView(titleRow);
    	
    	//data
    	TableRow systemSize = new TableRow(this);
    	TableRow systemCost = new TableRow(this);
    	TableRow paybackPeriod = new TableRow(this);
    	
    	TextView systemSizeTitle = (TextView) getLayoutInflater().inflate(
				R.layout.output_text_view, null);
    	systemSizeTitle.setText("System Size\n(kW)");
		systemSize.addView(systemSizeTitle);
		
		TextView systemCostTitle = (TextView) getLayoutInflater().inflate(
				R.layout.output_text_view, null);
    	systemCostTitle.setText("System Cost\n($)");
		systemCost.addView(systemCostTitle);
    	
		TextView paybackPeriodTitle = (TextView) getLayoutInflater().inflate(
				R.layout.output_text_view, null);
    	paybackPeriodTitle.setText("Payback Period\n(years)");
		paybackPeriod.addView(paybackPeriodTitle);
		
		for(Calculator curCalculator : calculatorList) {
			TextView newSystemSize = (TextView) getLayoutInflater().inflate(
					R.layout.output_text_view, null);
			newSystemSize.setText("" + df.format(curCalculator.getEquipment().getSize()));
			systemSize.addView(newSystemSize);
			
			TextView newSystemCost = (TextView) getLayoutInflater().inflate(
					R.layout.output_text_view, null);
	    	newSystemCost.setText(""+df.format(curCalculator.getEquipment().getCost()));
			systemCost.addView(newSystemCost);
	    	
			TextView newPaybackPeriod = (TextView) getLayoutInflater().inflate(
					R.layout.output_text_view, null);
	    	newPaybackPeriod.setText(""+df.format(curCalculator.getCalculations()[0].getPaybackPeriod()));
			paybackPeriod.addView(newPaybackPeriod);
		}
		
		table.addView(systemSize);
		table.addView(systemCost);
		table.addView(paybackPeriod);
    	
    	/*
		TextView systemSizeField = (TextView)findViewById(R.id.TextViewSystemSizeField);
		TextView systemCostField = (TextView)findViewById(R.id.TextViewSystemCostField);
		TextView paybackPeriodField = (TextView)findViewById(R.id.TextViewPaybackPeriodField);
		
		systemSizeField.setText(""+df.format(calculator.getEquipment().getSize()));
		systemCostField.setText("$"+df.format(calculator.getEquipment().getCost()));
		paybackPeriodField.setText(""+df.format(calculator.getCalculations()[0].getPaybackPeriod()));
		*/
    }
}

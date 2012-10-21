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
    	
    	titleRow.addView(new LinearLayout(this)); //blank spacer view
    	for(Calculator curCalculation : calculatorList) {
    		addHeadingView(titleRow, curCalculation.getName());
    	}
    	
    	table.addView(titleRow);
    	
    	//data
    	TableRow systemSize = new TableRow(this);
    	TableRow systemCost = new TableRow(this);
    	TableRow paybackPeriod = new TableRow(this);
    	
    	addHeadingView(systemSize, "System Size\n(kW)");
    	addHeadingView(systemCost, "System Cost\n($)");
    	addHeadingView(paybackPeriod, "Payback Period\n(years)");
		
		for(Calculator curCalculator : calculatorList) {
			addDataView(systemSize, "" + df.format(curCalculator.getEquipment().getSize()));
			addDataView(systemCost, ""+df.format(curCalculator.getEquipment().getCost()));
			addDataView(paybackPeriod, ""+df.format(curCalculator.getCalculations()[0].getPaybackPeriod()));
		}
		
		table.addView(systemSize);
		table.addView(systemCost);
		table.addView(paybackPeriod);
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
}

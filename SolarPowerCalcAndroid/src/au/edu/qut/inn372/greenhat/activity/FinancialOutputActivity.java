package au.edu.qut.inn372.greenhat.activity;

import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import au.edu.qut.inn372.greenhat.bean.Calculation;
import au.edu.qut.inn372.greenhat.bean.Calculator;

public class FinancialOutputActivity extends Activity {

	private List<Calculator> calculatorList;
	private TabbedOutputActivity parentTabbedActivity;
	DecimalFormat df = new DecimalFormat("#.##");


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_financial_output);
		parentTabbedActivity = (TabbedOutputActivity) getParent();
		calculatorList = parentTabbedActivity.getCalculators();
		generateView();

	}


	/**
	 * Generates the table of financial outputs (Programatically generating
	 * views)
	 */
	private void generateView() {
		
    	TableLayout table = (TableLayout) findViewById(R.id.TableLayoutOutputFinancial);
    	
    	//Heading (Calculator labels)
    	TableRow titleRow = new TableRow(this);
    	
    	titleRow.addView(new LinearLayout(this)); //Blank view to fill up the first cell
    	
    	for(Calculator curCalculation : calculatorList) {
    		TextView calcHeadingView = (TextView) getLayoutInflater().inflate(R.layout.output_text_heading_view, null);
    		calcHeadingView.setText(curCalculation.getName());
    		TableRow.LayoutParams params = new TableRow.LayoutParams();
    		params.span = 2; // set the title to span 2 columns (i.e. savings + roi)
    		titleRow.addView(calcHeadingView, params);
    		//titleRow.addView(new TextView(this));
    	}
    	
    	table.addView(titleRow);
    	
    	//column names
    	TableRow columnHeadingRow = new TableRow(this);
    	
    	addHeadingView(columnHeadingRow, "Year");
    	
    	for(Calculator curCalculation : calculatorList) {
    		addHeadingView(columnHeadingRow, "Savings ($)");
    		addHeadingView(columnHeadingRow, "ROI");
    	}
    	
    	table.addView(columnHeadingRow);
    	
    	
    	//data
    	//get total number of years required to process
    	int maxYears = 0;
    	Calculator maxYearsCalculator = null;
    	for(Calculator curCalculator : calculatorList) {
    		if(curCalculator.getCalculations().length > maxYears) {
    			maxYears = curCalculator.getCalculations().length;
    			maxYearsCalculator = curCalculator;
    		}
    	}
    	
    	//create a new row for each year
    	for(int year = 0; year < maxYears; year++) {
    		TableRow newRow = new TableRow(this);
    		
    		addHeadingView(newRow, "" + df.format(maxYearsCalculator.getCalculations()[year].getYear() + 1));
    		
    		for(Calculator curCalculator : calculatorList) {
    			//check that the year exists for this calculator
    			if(year < curCalculator.getCalculations().length) {
	    			//savings
	    			addDataView(newRow, "" + df.format(curCalculator.getCalculations()[year].getCumulativeSaving()));
	    			//ROI
	    			addDataView(newRow, "" + df.format(curCalculator.getCalculations()[year].getCumulativeSaving()
	    									/ curCalculator.getEquipment().getCost()));
    			}
    			else {
    				addDataView(newRow, ""); //blank savings
    				addDataView(newRow, ""); //blank ROI
    			}
    		}
    		table.addView(newRow);
    	}
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

package au.edu.qut.inn372.greenhat.activity;

import java.text.DecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import au.edu.qut.inn372.greenhat.bean.Calculation;
import au.edu.qut.inn372.greenhat.bean.Calculator;

public class FinancialOutputActivity extends Activity {
	
	private Calculator calculator;
	private TabbedOutputActivity parentTabbedActivity;
	DecimalFormat df = new DecimalFormat("#.##");
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_output);
        parentTabbedActivity = (TabbedOutputActivity)getParent();
        calculator = parentTabbedActivity.getCalculator();
        generateView();
        
	}
        
	/**
	 * Generates the table of financial outputs (Programatically generating views)
	 */
    private void generateView() {
		TableLayout table = (TableLayout)findViewById(R.id.TableLayoutOutput);    		
   		//add a new table row for each year of calculation data
   		for(Calculation curCalculation: calculator.getCalculations()) {
 			TableRow newRow = new TableRow(this);
    			
   			//Add entries to the row
   			TextView yearView = (TextView) getLayoutInflater().inflate(R.layout.output_text_view, null);
   			yearView.setText(""+df.format(curCalculation.getYear()+1));
   			yearView.setId(curCalculation.getYear()*3);
   			newRow.addView(yearView);
   			
   			TextView savingsView = (TextView) getLayoutInflater().inflate(R.layout.output_text_view, null);
   			savingsView.setText(""+df.format(curCalculation.getCumulativeSaving()));
   			savingsView.setId(curCalculation.getYear()*3+1);
   			newRow.addView(savingsView);
   			
   			TextView ROIView = (TextView) getLayoutInflater().inflate(R.layout.output_text_view, null);
   			ROIView.setText(""+df.format(curCalculation.getCumulativeSaving()/calculator.getEquipment().getCost()));
   			ROIView.setId(curCalculation.getYear()*3+2);
   			newRow.addView(ROIView);
   			
   			table.addView(newRow);
        }

        
	}
}

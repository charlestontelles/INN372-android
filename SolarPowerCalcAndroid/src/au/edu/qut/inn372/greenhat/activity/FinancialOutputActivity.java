package au.edu.qut.inn372.greenhat.activity;

import java.io.FileOutputStream;
import java.text.DecimalFormat;

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
		parentTabbedActivity = (TabbedOutputActivity) getParent();
		calculator = parentTabbedActivity.getCalculator();
		generateView();

	}


	/**
	 * Generates the table of financial outputs (Programatically generating
	 * views)
	 */
	private void generateView() {
		TableLayout table = (TableLayout) findViewById(R.id.TableLayoutOutput);
		// add a new table row for each year of calculation data
		for (Calculation curCalculation : calculator.getCalculations()) {
			TableRow newRow = new TableRow(this);

			// Add entries to the row
			TextView yearView = (TextView) getLayoutInflater().inflate(
					R.layout.output_text_view, null);
			yearView.setText("" + df.format(curCalculation.getYear() + 1));
			yearView.setId(curCalculation.getYear() * 3);
			newRow.addView(yearView);

			TextView savingsView = (TextView) getLayoutInflater().inflate(
					R.layout.output_text_view, null);
			savingsView.setText(""
					+ df.format(curCalculation.getCumulativeSaving()));
			savingsView.setId(curCalculation.getYear() * 3 + 1);
			newRow.addView(savingsView);

			TextView ROIView = (TextView) getLayoutInflater().inflate(
					R.layout.output_text_view, null);
			ROIView.setText(""
					+ df.format(curCalculation.getCumulativeSaving()
							/ calculator.getEquipment().getCost()));
			ROIView.setId(curCalculation.getYear() * 3 + 2);
			newRow.addView(ROIView);

			table.addView(newRow);
		}

	}

	/**
	 * Refers to the preceding Tab
	 * 
	 * @param view
	 */
	public void viewBackOutput(View view) {
		TabbedOutputActivity parentOutputTabbedActivity = (TabbedOutputActivity) this.getParent();
		int targetActivity = TabbedOutputActivity.POWER_GEN_ID;
		parentOutputTabbedActivity.switchTab(targetActivity);
	}

	public void viewMoreInfo(View view){
    	TabbedOutputActivity parentTabbedOutputActivity = (TabbedOutputActivity)this.getParent();
    	int targetActivity = TabbedOutputActivity.SAVINGS_GRAPH_ID;
    	parentTabbedOutputActivity.switchTab(targetActivity);
    }
		
}

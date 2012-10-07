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
	// Constant for identifying the dialog
	private static final int DIALOG_ALERT = 10;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_financial_output);
		parentTabbedActivity = (TabbedOutputActivity) getParent();
		calculator = parentTabbedActivity.getCalculator();
		generateView();

	}
	/**
	 * Customises dialogs to be used within the activity
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_ALERT:
			// Create out AlterDialog
			Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("SolarPowerReport.pdf created");
			builder.setCancelable(false);
			builder.setPositiveButton("OK", new OkOnClickListener());
			AlertDialog dialog = builder.create();
			dialog.show();
		}
		return super.onCreateDialog(id);
	}
	/**
	 * handle on click button in the dialog
	 * 
	 */
	private final class OkOnClickListener implements
			DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
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
		TabbedOutputActivity parentOutputTabbedActivity = (TabbedOutputActivity) this
				.getParent();
		int targetActivity = TabbedOutputActivity.POWER_GEN_ID;
		parentOutputTabbedActivity.switchTab(targetActivity);
	}

	/**
	 * Exports the financial calculation to a PDF
	 * 
	 * TODO: talk to customer to check which information he needs
	 * in the report
	 * 
	 * @param view
	 */
	public void exportPDF(View view){
		String FILE = "/SolarPowerReport.pdf";
		try {
			Document document = new Document();
			boolean mExternalStorageAvailable = false;
			boolean mExternalStorageWriteable = false;
			String state = Environment.getExternalStorageState();
			if (Environment.MEDIA_MOUNTED.equals(state)) {
				// We can read and write the media
				mExternalStorageAvailable = mExternalStorageWriteable = true;
			} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
				// We can only read the media
				mExternalStorageAvailable = true;
				mExternalStorageWriteable = false;
			} else {
				// Something else is wrong. It may be one of many other states, but all we need
				//  to know is we can neither read nor write
				mExternalStorageAvailable = mExternalStorageWriteable = false;
			}
			String file = null;
			if(mExternalStorageWriteable) {
				file = Environment.getExternalStorageDirectory().getPath() + FILE;
			}

			PdfWriter.getInstance(document,new FileOutputStream(file));
			document.open();
			Paragraph p = new Paragraph("Calculation Report");
			document.add(p);
			p = new Paragraph("  ");
			document.add(p);
			PdfPTable table = new PdfPTable(3);
		    PdfPCell c1 = new PdfPCell(new Phrase("Year"));
		    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    table.addCell(c1);

		    c1 = new PdfPCell(new Phrase("Cumulative Savings ($)"));
		    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    table.addCell(c1);

		    c1 = new PdfPCell(new Phrase("ROI"));
		    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    table.addCell(c1);
		    table.setHeaderRows(1);
			for (Calculation curCalculation : calculator.getCalculations()) {
			    table.addCell("" + df.format(curCalculation.getYear() + 1));
			    table.addCell("" + df.format(curCalculation.getCumulativeSaving()));
			    table.addCell("" + df.format(curCalculation.getCumulativeSaving() / calculator.getEquipment().getCost()));
			}
			document.add(table);
			
			document.close();

			showDialog(DIALOG_ALERT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}

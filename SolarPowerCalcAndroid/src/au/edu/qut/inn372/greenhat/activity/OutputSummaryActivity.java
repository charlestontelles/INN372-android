package au.edu.qut.inn372.greenhat.activity;

import java.text.DecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import au.edu.qut.inn372.greenhat.bean.Calculator;

public class OutputSummaryActivity extends Activity {
	
	private Calculator calculator;
	private TabbedOutputActivity parentTabbedActivity;
	DecimalFormat df = new DecimalFormat("#.##");
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output_summary);
        parentTabbedActivity = (TabbedOutputActivity)getParent();
        calculator = parentTabbedActivity.getCalculator();
        generateView();

	}
    private void generateView() {
		TextView systemSizeField = (TextView)findViewById(R.id.TextViewSystemSizeField);
		TextView systemCostField = (TextView)findViewById(R.id.TextViewSystemCostField);
		TextView paybackPeriodField = (TextView)findViewById(R.id.TextViewPaybackPeriodField);
		
		systemSizeField.setText(""+df.format(calculator.getEquipment().getSize()));
		systemCostField.setText("$"+df.format(calculator.getEquipment().getCost()));
		paybackPeriodField.setText(""+df.format(10.0));
    }

}

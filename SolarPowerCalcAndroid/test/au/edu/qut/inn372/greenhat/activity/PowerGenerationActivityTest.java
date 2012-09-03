package au.edu.qut.inn372.greenhat.activity;

import java.text.DecimalFormat;

import android.widget.TextView;
import junit.framework.TestCase;
import android.app.Activity;


public class PowerGenerationActivityTest extends TestCase {

	PowerGeneration activity;
	TextView dailyField;
	TextView annualField;
	TextView quaterlyField;
	TextView netField;
	TextView quaterlyNet;
	TextView annualNet;
	DecimalFormat df;
	Double solarPower;
	Double daily;
	
	protected void setUp() throws Exception {
		super.setUp();
		//activity = getActivity();
		//dailyField = (TextView)activity.findViewById(R.id.TextViewDailyField);
    	//annualField = (TextView)activity.findViewById(R.id.TextViewAnnualField);
    	//quaterlyField = (TextView)activity.findViewById(R.id.TextViewQuaterlyField);
    	//netField = (TextView)activity.findViewById(R.id.TextViewNetField);
        //quaterlyNet = (TextView)activity.findViewById(R.id.TextViewQuaterlyNetField);
        //annualNet = (TextView)activity.findViewById(R.id.TextViewAnnualNetField);
        //df = new DecimalFormat("#.##");
        solarPower = 110.0;
        daily = 30.0;
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testOnCreate(){
		assertTrue(PowerGeneration.class.getName().length() > 0);
	}
	
	/*public void testGenerateViewDailyField(){
		
        dailyField.setText(""+df.format(new Double(solarPower)));
        annualField.setText(""+df.format(new Double(solarPower)*365));
        quaterlyField.setText(""+df.format(new Double(solarPower)*91));
        netField.setText(""+(df.format(new Double(solarPower).doubleValue() - new Double(daily).doubleValue())));
        annualNet.setText(""+ new Double(daily) * 365);
        quaterlyNet.setText(""+ new Double(daily) * 365 / 4);
	}*/
	
	
	

}

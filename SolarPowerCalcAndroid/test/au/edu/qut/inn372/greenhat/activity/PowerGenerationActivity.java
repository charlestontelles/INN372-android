package au.edu.qut.inn372.greenhat.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;
import au.edu.qut.inn372.greenhat.activity.*;
import au.edu.qut.inn372.greenhat.bean.Calculator;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

public class PowerGenerationActivity extends ActivityInstrumentationTestCase2<PowerGeneration> {

	PowerGeneration activity;
	Intent intent;
	public final static String EXTRA_MESSAGE = "au.edu.qut.inn372.inn372.greenhat.activity.BasicInputActivity";
	
	public PowerGenerationActivity() {
		super("au.edu.qut.inn372.greenhat.activity", PowerGeneration.class);
		// TODO Auto-generated constructor stub
	}

	protected void setUp() throws Exception {
		super.setUp();
		activity = getActivity();
		intent.putExtra(EXTRA_MESSAGE, "120");
	
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/*
	public void testDailyField(){
		TextView dailyField = (TextView)activity.findViewById(R.id.TextViewDailyField);
		String daily = intent.getStringExtra(EXTRA_MESSAGE);
		 dailyField.setText(daily);
		 assertEquals(dailyField.getText().equals("120"), true);		
		 //assertNull(dailyField);
	}*/
	

}

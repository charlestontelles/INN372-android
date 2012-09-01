package au.edu.qut.inn372.greenhat.activity;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.support.v4.app.NavUtils;

public class BasicInputActivity extends Activity {

	//add this 
	public final static String EXTRA_MESSAGE = "au.edu.qut.inn372.inn372.greenhat.activity.BasicInputActivity";
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_input);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_basic_input, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void Calculate(View view){
    	Intent intent = new Intent(this, PowerGeneration.class);
    	//add this (Joachim) to receive the intent in the output page
    	EditText equipmentCost = (EditText) findViewById(R.id.editEquipmentCost);
    	EditText equipmentSize = (EditText)findViewById(R.id.editEquipmentSize);
    	String cost = equipmentCost.getText().toString();
    	String size = equipmentSize.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, cost);
        //intent.putExtra(EXTRA_MESSAGE, size);
    	
    	startActivity(intent);
    
    }

}

package au.edu.qut.inn372.greenhat.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;
import au.edu.qut.inn372.greenhat.activity.*;
import au.edu.qut.inn372.greenhat.activity.R;
import au.edu.qut.inn372.greenhat.bean.Calculator;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

public class LoginActivityAndroidTest extends
		ActivityInstrumentationTestCase2<LoginActivity> {
	
	LoginActivity activity;
	
	public LoginActivityAndroidTest(){
		super("au.edu.qut.inn372.greenhat.activity", LoginActivity.class);
	}
	
	protected void setUp() throws Exception{
		super.setUp();
		 activity = getActivity();
		
	}
	
	public void testLogin(){
		
		EditText login = (EditText)activity.findViewById(R.id.editUser);
		assertNotNull(login);
	}
	
	public void testPWD(){
		EditText pwd = (EditText)activity.findViewById(R.id.editPassword);
		assertNotNull(pwd);
	}
	
	public void testStartUp(){
		assertTrue(LoginActivity.class.getName().length() > 0);
	}
}

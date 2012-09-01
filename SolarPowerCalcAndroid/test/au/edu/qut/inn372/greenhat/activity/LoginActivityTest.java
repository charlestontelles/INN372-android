package au.edu.qut.inn372.greenhat.activity;

import junit.framework.TestCase;

public class LoginActivityTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}

	public void testOnCreate(){
		assertTrue(LoginActivity.class.getName().length() > 0);
	}
}

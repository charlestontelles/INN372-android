package au.edu.qut.inn372.greenhat.activity;

import junit.framework.TestCase;

public class BasicInputActivityTest extends TestCase {

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
		assertTrue(BasicInputActivity.EXTRA_MESSAGE.length() >0);
	}
}

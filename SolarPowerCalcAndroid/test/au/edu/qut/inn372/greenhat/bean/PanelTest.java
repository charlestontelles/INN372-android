package au.edu.qut.inn372.greenhat.bean;

import junit.framework.TestCase;

public class PanelTest extends TestCase {
	
	private Panel panel;
	protected void setUp() throws Exception {
		super.setUp();
		panel = new Panel();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	//TODO: Add test for getPanelEfficiency()
	
	public void testGetWidth(){
		panel.setWidth(20);
		assertTrue(panel.getWidth()==20);
	}

	public void testSetWidth(){
		panel.setWidth(10);
		assertTrue(panel.getWidth()==10);
	}

	public void testGetHeight(){
		panel.setHeight(20);
		assertTrue(panel.getHeight()==20);
	}

	public void testSetHeight(){
		panel.setHeight(10);
		assertTrue(panel.getHeight()==10);
	}
	
	public void testGetPosition(){
		panel.setPosition("NW");
		assertTrue(panel.getPosition()=="NW");
	}

	public void testSetPosition(){
		panel.setPosition("S");
		assertTrue(panel.getPosition()=="S");
	}
	
	public void testGetEfficiency(){
		panel.setEfficiency_loss(20.0);
		assertTrue(panel.getEfficiency_loss()==20.0);
	}

	public void testSetEfficiency(){
		panel.setEfficiency_loss(10.0);
		assertTrue(panel.getEfficiency_loss()==10.0);
	}
	
	public void testGetPower(){
		panel.setPower(20.0);
		assertTrue(panel.getPower()==20.0);
	}

	public void testSetPower(){
		panel.setPower(10.0);
		assertTrue(panel.getPower()==10.0);
	}

}

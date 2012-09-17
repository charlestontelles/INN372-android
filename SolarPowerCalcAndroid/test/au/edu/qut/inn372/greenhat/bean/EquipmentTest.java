package au.edu.qut.inn372.greenhat.bean;

import junit.framework.TestCase;

public class EquipmentTest extends TestCase {
	private Equipment equipment;
	private Panel panel1;
	private Panel panel2;
	protected void setUp() throws Exception {
		super.setUp();
		equipment = new Equipment();
		//Panels needed for testing getNumberOfPanels()
		panel1 = new Panel();
		panel2 = new Panel();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void testGetCost(){
		equipment.setCost(20);
		assertTrue(equipment.getCost()==20);
	}

	public void testSetCost(){
		equipment.setCost(10);
		assertTrue(equipment.getCost()==10);
	}

	public void testGetSize(){
		equipment.setSize(20);
		assertTrue(equipment.getSize()==20);
	}

	public void testSetSize(){
		equipment.setSize(10);
		assertTrue(equipment.getSize()==10);
	}
	
	public void testGetTotalPanels(){
		equipment.addPanel(panel1);
		equipment.addPanel(panel2);
		assertTrue(equipment.getTotalPanels()==2);
	}
}

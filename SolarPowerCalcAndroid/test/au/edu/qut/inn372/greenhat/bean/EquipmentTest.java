package au.edu.qut.inn372.greenhat.bean;

import junit.framework.TestCase;

public class EquipmentTest extends TestCase {
	private Equipment equipment;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		equipment = new Equipment();
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
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

}

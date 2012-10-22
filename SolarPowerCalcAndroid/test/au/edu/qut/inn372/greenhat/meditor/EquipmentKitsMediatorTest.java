package au.edu.qut.inn372.greenhat.meditor;

import au.edu.qut.inn372.greenhat.mediator.EquipmentKitsMediator;
import junit.framework.TestCase;

public class EquipmentKitsMediatorTest  extends TestCase {
	
	private EquipmentKitsMediator equipmentKitsMediator;

	protected void setUp() throws Exception {
		super.setUp();
		equipmentKitsMediator = new EquipmentKitsMediator();
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testGetPanels() {
		equipmentKitsMediator.getEquipments();
		assertNotNull(equipmentKitsMediator.getEquipmentKits());
		assertTrue(equipmentKitsMediator.getEquipmentKits().size()>0);
	}

}

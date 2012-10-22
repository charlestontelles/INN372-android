package au.edu.qut.inn372.greenhat.meditor;

import au.edu.qut.inn372.greenhat.mediator.PanelMediator;
import junit.framework.TestCase;

public class PanelMediatorTest  extends TestCase {
	
	private PanelMediator panelMediator;

	protected void setUp() throws Exception {
		super.setUp();
		panelMediator = new PanelMediator();
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetPanels() {
		panelMediator.getPanels();
		assertNotNull(panelMediator.getPanelList());
		assertTrue(panelMediator.getPanelList().size()>0);
	}

}

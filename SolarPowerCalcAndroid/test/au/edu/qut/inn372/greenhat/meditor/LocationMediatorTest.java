package au.edu.qut.inn372.greenhat.meditor;

import au.edu.qut.inn372.greenhat.mediator.LocationMediator;
import junit.framework.TestCase;

public class LocationMediatorTest  extends TestCase {
	
	private LocationMediator locationMediator;

	protected void setUp() throws Exception {
		super.setUp();
		locationMediator = new LocationMediator();
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testGetSetLatitude() {
		double defaultLat = locationMediator.getLatitude();
		locationMediator.setLatitude(-27.3);
		assertTrue(locationMediator.getLatitude() != defaultLat);
	}
	
	public void testGetSetLongitude() {
		double defaultLong = locationMediator.getLongitude();
		locationMediator.setLongitude(153.2);
		assertTrue(locationMediator.getLongitude() != defaultLong);
	}
	
	public void testGetSetSunlightHours() {
		double defaultSunlight = locationMediator.getAverageSunligthHours();
		locationMediator.setAverageSunlight(3.7);
		assertTrue(locationMediator.getAverageSunligthHours() != defaultSunlight);
	}
	
	public void testRetrieveAverageSunlight() {
		double defaultSunlight = locationMediator.getAverageSunligthHours();
		locationMediator.setLatitude(-27.3);
		locationMediator.setLongitude(153.2);
		locationMediator.retrieveAverageSunlight();
		assertTrue(locationMediator.getAverageSunligthHours() != defaultSunlight);
	}



}

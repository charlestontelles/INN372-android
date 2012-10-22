package au.edu.qut.inn372.greenhat.meditor;


import java.util.ArrayList;
import java.util.List;

import au.edu.qut.inn372.greenhat.bean.UserProfile;
import au.edu.qut.inn372.greenhat.mediator.AllCalculationsMediator;
import au.edu.qut.inn372.greenhat.mediator.UserProfileMediator;
import au.edu.qut.inn372.greenhat.bean.Calculator;

import junit.framework.TestCase;

public class AllCalculationsMediatorTest  extends TestCase {
	
	private AllCalculationsMediator allCalculationsMediator;
	private UserProfile userProfile;
	
	protected void setUp() throws Exception {
		super.setUp();
		UserProfileMediator userProfileMediator = new UserProfileMediator();
		userProfileMediator.getUserProfile().setEmail("charles@greenhat.com");
		userProfileMediator.getUserProfile().setPassword("1234");
		userProfileMediator.validateCredentials();
		userProfile = userProfileMediator.getUserProfile();
		allCalculationsMediator = new AllCalculationsMediator(userProfile);
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testGetUserProfile() {
		assertEquals(userProfile, allCalculationsMediator.getUserProfile());
	}
	
	public void testSetUserProfile() {
		UserProfile newUserProfile = new UserProfile();
		allCalculationsMediator.setUserProfile(newUserProfile);
		assertEquals(newUserProfile, allCalculationsMediator.getUserProfile());
	}
	
	public void testGetSetCalculations() {
		List<Calculator> calcList = new ArrayList<Calculator>();
		allCalculationsMediator.setCalculations(calcList);
		assertEquals(calcList, allCalculationsMediator.getCalculations());
	}
	
	public void testGetCalculationList() {
		allCalculationsMediator.getCalculationList();
		assertNotNull(allCalculationsMediator.getCalculations());
		assertTrue(allCalculationsMediator.getCalculations().size()>0);
	}
	
	public void testDeleteCalculator() {
		//needs improving
		List<Calculator> calcList = new ArrayList<Calculator>();
		allCalculationsMediator.deleteCalculations(calcList);
	}

}

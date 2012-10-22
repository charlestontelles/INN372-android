package au.edu.qut.inn372.greenhat.meditor;

import au.edu.qut.inn372.greenhat.bean.UserProfile;
import au.edu.qut.inn372.greenhat.mediator.UserProfileMediator;
import junit.framework.TestCase;

public class UserProfileMediatorTest  extends TestCase {
	
	private UserProfileMediator userProfileMediator;
	private UserProfile userProfile;
	
	protected void setUp() throws Exception {
		super.setUp();
		userProfileMediator = new UserProfileMediator();
		userProfile = userProfileMediator.getUserProfile();
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSaveUserProfile() {
		userProfile.setName("name1");
		userProfile.setEmail("email1");
		userProfile.setPassword("password1");
		userProfile.setType(1);
		userProfileMediator.saveUserProfile();
		assertNotNull(userProfileMediator.getUserProfile().getKey());
	}

	public void testValidateCredentialsTrue() {
		//validate using the predefined known test user credentials
		userProfile.setEmail("charles@greenhat.com");
		userProfile.setPassword("1234");
		assertTrue(userProfileMediator.validateCredentials());
	}
	
	public void testValidateCredentialsFalse() {
		userProfile.setEmail("asdfqwerzxcv");
		userProfile.setPassword("uiopjk;lnm,.");
		assertFalse(userProfileMediator.validateCredentials());
	}
	
}

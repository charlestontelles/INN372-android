package au.edu.qut.inn372.greenhat.mediator;

import java.io.Serializable;

import org.ksoap2.serialization.SoapObject;

import au.edu.qut.inn372.greenhat.bean.AndroidAbstractBean;
import au.edu.qut.inn372.greenhat.bean.UserProfile;
import au.edu.qut.inn372.greenhat.ws.SoapClient;


public class UserProfileMediator implements Serializable{

	private static final long serialVersionUID = 8900672707083642377L;

	private SoapClient soapClient = new SoapClient();
	private UserProfile userProfile;
	
	/**
	 * Constructor which initialises the User Profile bean object
	 */
	public UserProfileMediator() {
		userProfile = new UserProfile();
	}
	
	/**
	 * Returns the User Profile bean object
	 * @return The User Profile bean object
	 */
	public UserProfile getUserProfile() {
		return userProfile;
	}
	
	/**
	 * Performs remote soap call to register a user
	 * User profile field is the new return user profile object including key
	 */
	public void saveUserProfile(){
		SoapObject soap = userProfile.getSoapObject(AndroidAbstractBean.OPERATION_SAVE_USER_PROFILE);
		userProfile = new UserProfile( soapClient.synchronousRequest(soap), AndroidAbstractBean.OPERATION_SAVE_USER_PROFILE);
	}
	
	/**
	 * Validates user profile credentials (email and password fields required)
	 * @return True if the fields correspond to a user profile
	 */
	public boolean validateCredentials(){
		SoapObject soap = userProfile.getSoapObject(AndroidAbstractBean.OPERATION_VALIDATE_CREDENTIALS);
		SoapObject validateResponse = soapClient.synchronousRequest(soap);
		SoapObject response = (SoapObject)validateResponse.getProperty("result");
		if (response.getProperty("type").toString().equals("0")) {
			return false;
		}
		else {
			//Save the returned user profile details
			userProfile.setKey(response.getProperty("key").toString());
			userProfile.setName(response.getProperty("name").toString());
			userProfile.setType(new Integer(response.getProperty("type").toString()));
			return true;
		}
	}
	
}

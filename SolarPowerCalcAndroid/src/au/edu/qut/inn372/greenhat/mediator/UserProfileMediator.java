package au.edu.qut.inn372.greenhat.mediator;

import java.io.Serializable;

import org.ksoap2.serialization.SoapObject;

import au.edu.qut.inn372.greenhat.bean.AndroidAbstractBean;
import au.edu.qut.inn372.greenhat.bean.UserProfile;
import au.edu.qut.inn372.greenhat.ws.CalculatorSoapClient;


public class UserProfileMediator implements Serializable{

	private static final long serialVersionUID = 8900672707083642377L;

	private CalculatorSoapClient soapClient = new CalculatorSoapClient();
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
	
	/*
	public void validateCredentials(){
		//Calculator resultCalculator = new Calculator(soapClient.synchronousRequest(calculator.getSoapObject(AndroidAbstractBean.OPERATION_CALC_ENERGY_PRODUCTION)),AndroidAbstractBean.OPERATION_CALC_ENERGY_PRODUCTION);
		//updateCalculator(resultCalculator);
		//UserProfile userProfile = new UserProfile(soapClient.synchronousRequest(calculator.getCustomer().getUserProfile().getSoapObject(AndroidAbstractBean.OPERATION_VALIDATE_CREDENTIALS)),AndroidAbstractBean.OPERATION_VALIDATE_CREDENTIALS);
		//calculator.getCustomer().setUserProfile(userProfile);
	}
	*/
}

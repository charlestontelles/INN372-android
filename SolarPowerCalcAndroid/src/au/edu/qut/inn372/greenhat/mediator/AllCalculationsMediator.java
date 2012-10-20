package au.edu.qut.inn372.greenhat.mediator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import au.edu.qut.inn372.greenhat.bean.AndroidAbstractBean;
import au.edu.qut.inn372.greenhat.bean.Calculation;
import au.edu.qut.inn372.greenhat.bean.Calculator;
import au.edu.qut.inn372.greenhat.bean.Equipment;
import au.edu.qut.inn372.greenhat.bean.UserProfile;
import au.edu.qut.inn372.greenhat.ws.SoapClient;
/**
 * 
 * @author Charleston Telles
 *
 */
public class AllCalculationsMediator implements Serializable{

	private static final long serialVersionUID = 1490185445160551004L;
	private SoapClient soapClient = new SoapClient();
	private List<Calculator> allCalculations;
	private UserProfile userProfile;
	
	/**
	 * Constructor which initialises the calculator bean object
	 */
	public AllCalculationsMediator(UserProfile userProfile) {
		allCalculations = new ArrayList<Calculator>();
		this.userProfile = userProfile;
	}
	
	public UserProfile getUserProfile() {
		return userProfile;
	}
	
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	
	/**
	 * Returns the calculator bean object in the calculator mediator
	 * @return The calculator bean object
	 */
	public List<Calculator> getCalculations() {
		return allCalculations;
	}
	
	public void setCalculations(List<Calculator> allCalculations){
		this.allCalculations = allCalculations;
	}
	
	public void getCalculationList() {
		SoapObject soap = soapClient.synchronousRequest(userProfile.getSoapObject(AndroidAbstractBean.OPERATION_GET_CALCULATIONS));
		allCalculations.clear(); //clear the list if it isn't empty
		int numCalculations = soap.getPropertyCount();  
		for(int i = 0; i< numCalculations ; i++) {
			try {
				SoapObject curCalculation = (SoapObject)soap.getProperty(i);
				allCalculations.add(new Calculator(curCalculation, AndroidAbstractBean.OPERATION_GET_CALCULATIONS));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void deleteCalculations(List<Calculator> selectedCalculators) {
		for (Calculator curCalculator : selectedCalculators){
			soapClient.synchronousRequest(curCalculator.getSoapObject(AndroidAbstractBean.OPERATION_DELETE_CALCULATION));
			allCalculations.remove(curCalculator);
		}
	}
}

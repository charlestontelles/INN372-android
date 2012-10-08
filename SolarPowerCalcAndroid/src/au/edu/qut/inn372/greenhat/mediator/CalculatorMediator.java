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
import au.edu.qut.inn372.greenhat.ws.CalculatorSoapClient;
/**
 * 
 * @author Charleston Telles
 *
 */
public class CalculatorMediator implements Serializable{

	private static final long serialVersionUID = 8900672707083642377L;

	private CalculatorSoapClient soapClient = new CalculatorSoapClient();
	private Calculator calculator;
	
	/**
	 * Constructor which initialises the calculator bean object
	 */
	public CalculatorMediator() {
		calculator = new Calculator();
	}
	
	/**
	 * Returns the calculator bean object in the calculator mediator
	 * @return The calculator bean object
	 */
	public Calculator getCalculator() {
		return calculator;
	}
	
	/**
	 * Performs remote soap call to perform calculations on the bean object
	 */
	public void calcEnergyProduction(){
		Calculator resultCalculator = new Calculator(soapClient.synchronousRequest(calculator.getSoapObject(AndroidAbstractBean.OPERATION_CALC_ENERGY_PRODUCTION)),AndroidAbstractBean.OPERATION_CALC_ENERGY_PRODUCTION);
		updateCalculator(resultCalculator);
	}
	
	/**
	 * Performs remote soap call to save a calculation
	 */
	public String saveCalculation(){
		SoapObject soap = soapClient.synchronousRequest(calculator.getSoapObject(AndroidAbstractBean.OPERATION_SAVE_CALCULATION));
		String result = soap.getProperty("result").toString();
		return result;
	}
	
	/**
	 * Puts all results in the received result object into the existing calculator object
	 * @param resultCalculator The result object returns from the WS call
	 */
	private void updateCalculator(Calculator resultCalculator) {
		calculator.setCalculations(resultCalculator.getCalculations());
	}
	
	/**TODO: UNDER CONSTRUCTION by Fabian
	 * Performs remote soap call to return the latest list of calculations
	 */
	/*public List<Calculation> getCalculationList(){
		SoapObject soap = soapClient.synchronousRequest(calculator.getSoapObject(AndroidAbstractBean.OPERATION_GET_CALCULATIONS));
		
		List<Calculation> calculations = (List<Calculation>) soap.getProperty("calculators"); //soap is null, gets no proper response
		return calculations;		
	}
	*/
	public List<Calculator> getCalculationList(){
		SoapObject soap = soapClient.synchronousRequest(calculator.getCustomer().getUserProfile().getSoapObject(AndroidAbstractBean.OPERATION_GET_CALCULATIONS));
		List<Calculator> calculations = new ArrayList<Calculator>(); //changed List to ArrayList
		int numCalculations = soap.getPropertyCount();
		for(int i = 0; i< numCalculations ; i++) {
			SoapObject curCalculation = (SoapObject)soap.getProperty(i);
			calculations.add(new Calculator(curCalculation, AndroidAbstractBean.OPERATION_GET_CALCULATIONS));
		}
		return calculations;	
	}
}

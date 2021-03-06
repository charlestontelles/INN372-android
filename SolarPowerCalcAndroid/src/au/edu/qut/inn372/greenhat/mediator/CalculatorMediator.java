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
public class CalculatorMediator implements Serializable{

	private static final long serialVersionUID = 8900672707083642377L;

	private SoapClient soapClient = new SoapClient();
	private Calculator calculator;
	
	/**
	 * Constructor which initialises the calculator bean object
	 */
	public CalculatorMediator() {
		calculator = new Calculator();
	}
	
	/**
	 * Constructor which initialises the calculator bean object
	 */
	public CalculatorMediator(Calculator calculator) {
		this.calculator = calculator;
	}
	
	/**
	 * Returns the calculator bean object in the calculator mediator
	 * @return The calculator bean object
	 */
	public Calculator getCalculator() {
		return calculator;
	}
	
	public void setCalculator(Calculator calculator){
		this.calculator = calculator;
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
		if(result.startsWith("error")) {
			return "failed";
		}
		else {
			calculator.setKey(result);
			return "ok";
		}
	}
	
	/**
	 * Puts all results in the received result object into the existing calculator object
	 * @param resultCalculator The result object returns from the WS call
	 */
	private void updateCalculator(Calculator resultCalculator) {
		calculator.setCalculations(resultCalculator.getCalculations());
	}
}

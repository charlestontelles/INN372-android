package au.edu.qut.inn372.greenhat.mediator;

import au.edu.qut.inn372.greenhat.bean.AndroidAbstractBean;
import au.edu.qut.inn372.greenhat.bean.Calculator;
import au.edu.qut.inn372.greenhat.ws.CalculatorSoapClient;
/**
 * 
 * @author Charleston Telles
 *
 */
public class CalculatorMediator {
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
	 * Puts all results in the received result object into the existing calculator object
	 * @param resultCalculator The result object returns from the WS call
	 */
	private void updateCalculator(Calculator resultCalculator) {
		calculator.setCalculations(resultCalculator.getCalculations());
	}
}

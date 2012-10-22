package au.edu.qut.inn372.greenhat.meditor;


import au.edu.qut.inn372.greenhat.bean.AndroidAbstractBean;
import au.edu.qut.inn372.greenhat.bean.Calculator;
import au.edu.qut.inn372.greenhat.mediator.CalculatorMediator;
import au.edu.qut.inn372.greenhat.ws.SoapClient;
import junit.framework.TestCase;

public class CalculatorMediatorTest extends TestCase {
	private CalculatorMediator calculatorMediator;
	private Calculator calculator = new Calculator();
	private SoapClient soapClient = new SoapClient();
	
	public CalculatorMediatorTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		calculatorMediator = new CalculatorMediator();
		//Setup data for the calcuation
		calculator.getCustomer().getElectricityUsage().setDailyAverageUsage(40.0);
		calculator.getCustomer().getElectricityUsage().setDayTimeHourlyUsage(1.0);
		calculator.getCustomer().getTariff().setTariff11Fee(0.1941);
		calculator.getCustomer().getTariff().setFeedInfee(0.5);
		
		calculator.getCustomer().getLocation().getRoof().setHeight(1500);
		calculator.getCustomer().getLocation().getRoof().setWidth(1500);
		calculator.getEquipment().setCost(1500.0);
		calculator.getEquipment().setSize(2.5);
		calculator.getEquipment().getInverter().setEfficiency(85);
		calculator.getEquipment().getInverter().setCost(1000);
		
		
		
		
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	
	public void testGetCalculator(){
		calculatorMediator.setCalculator(calculator);
		assertNotNull(calculatorMediator.getCalculator());
	}
	
	public void testSetCalculator(){
		calculatorMediator.setCalculator(calculator);
		assertNotNull(calculatorMediator.getCalculator());
	}
	
	public void testCalcEnergyProduction(){
		calculatorMediator.calcEnergyProduction();
		assertNotNull(calculator.getCalculations());
	}
	
	//TODO Test for saveCalculation()
	
	//TODO Test for updateCalculator()
}

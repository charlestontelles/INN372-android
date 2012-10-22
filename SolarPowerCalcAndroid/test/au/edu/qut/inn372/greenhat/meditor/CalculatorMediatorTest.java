package au.edu.qut.inn372.greenhat.meditor;


import au.edu.qut.inn372.greenhat.bean.AndroidAbstractBean;
import au.edu.qut.inn372.greenhat.bean.Calculator;
import au.edu.qut.inn372.greenhat.mediator.CalculatorMediator;
import au.edu.qut.inn372.greenhat.mediator.EquipmentKitsMediator;
import au.edu.qut.inn372.greenhat.ws.SoapClient;
import junit.framework.TestCase;

public class CalculatorMediatorTest extends TestCase {
	private CalculatorMediator calculatorMediator;
	private Calculator calculator = new Calculator();
	
	public CalculatorMediatorTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		calculatorMediator = new CalculatorMediator();
		//Setup data for the calcuation
		
		EquipmentKitsMediator equipmentKitsMediator = new EquipmentKitsMediator();
		equipmentKitsMediator.getEquipments();
		
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
		calculator.getCustomer().getLocation().setCity("Brisbane");
		calculator.getCustomer().getLocation().setSunLightHours(4.5);
		calculator.setEquipment(equipmentKitsMediator.getEquipmentKits().get(0));
		calculator.getCustomer().getTariff().setAnnualTariffIncrease(1);
		calculator.getCustomer().getTariff().setTariffFeePerYear(1);
		calculator.getCustomer().getLocation().getRoof().getBanks().get(0).setNumberOfPanels(1);
		calculator.getCustomer().getLocation().getRoof().getBanks().get(1).setNumberOfPanels(1);
		calculator.getCustomer().getLocation().getRoof().getBanks().get(0).setAngle(45);
		calculator.getCustomer().getLocation().getRoof().getBanks().get(1).setAngle(45);
		calculator.getCustomer().getLocation().getRoof().getBanks().get(0).setSelectedOrientation("North");
		calculator.getCustomer().getLocation().getRoof().getBanks().get(1).setSelectedOrientation("West");
		calculator.getCustomer().getUserProfile().setKey("TestKey");
		
		calculatorMediator.setCalculator(calculator);
		
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSetGetCalculator() {
		Calculator testCalculator = new Calculator();
		calculatorMediator.setCalculator(testCalculator);
		assertEquals(calculatorMediator.getCalculator(), testCalculator);
	}
	
	public void testCalcEnergyProduction(){
		calculatorMediator.calcEnergyProduction();
		assertNotNull(calculator.getCalculations());
		assertTrue(calculator.getCalculations().length > 0);
		assertTrue(calculator.getCalculations()[0].getPaybackPeriod() != 0);
		assertTrue(calculator.getCalculations()[0].getDailySolarPower() != 0);
	}
	
	public void testSaveCalculator() {
		assertEquals(calculatorMediator.saveCalculation(), "ok");
	}
}

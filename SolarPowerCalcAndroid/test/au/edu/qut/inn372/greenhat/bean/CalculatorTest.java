package au.edu.qut.inn372.greenhat.bean;

import junit.framework.TestCase;

public class CalculatorTest extends TestCase {
	private Calculator calculator;
	public CalculatorTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		calculator = new Calculator();
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetEquipment() {
		Equipment equipment = new Equipment();
		equipment.setCost(20);
		calculator.setEquipment(equipment);
		assertTrue(calculator.getEquipment().getCost()==20);
	}

	public void testSetEquipment() {
		Equipment equipment = new Equipment();
		equipment.setCost(10);
		calculator.setEquipment(equipment);
		assertTrue(calculator.getEquipment().getCost()==10);
	
	}

	/*
	public void testGetResult() {
		calculator.setResult(10);
		assertTrue(calculator.getResult()==10);
	}

	public void testSetResult() {
		calculator.setResult(10);
		assertTrue(calculator.getResult()==10);
	}
	*/

}

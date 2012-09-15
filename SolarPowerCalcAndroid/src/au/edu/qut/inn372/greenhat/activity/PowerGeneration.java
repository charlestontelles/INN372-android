package au.edu.qut.inn372.greenhat.activity;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import au.edu.qut.inn372.greenhat.bean.Calculation;
import au.edu.qut.inn372.greenhat.bean.Calculator;

public class PowerGeneration extends Activity{
	
	private Calculation[] testCalculations;
	private Calculator calculator;
	private int paybackPeriod = 20;
	DecimalFormat df = new DecimalFormat("#.##");
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.power_generation);
        calculator = (Calculator)getIntent().getSerializableExtra("Calculator");
        testCalculationsSetup();
        generateView();

        
	}   
	
	private void generateView() {
		generateGeneralOutputView();
		generateAverageSolarPowerGenerationView();
		generateFinancialView(testCalculations);
    }
	
	private void generateGeneralOutputView() {
		
		TextView systemSizeField = (TextView)findViewById(R.id.TextViewSystemSizeField);
		TextView systemCostField = (TextView)findViewById(R.id.TextViewSystemCostField);
		TextView paybackPeriodField = (TextView)findViewById(R.id.TextViewPaybackPeriodField);
		
		systemSizeField.setText(""+df.format(calculator.getEquipment().getSize()));
		systemCostField.setText(""+df.format(calculator.getEquipment().getCost()));
		paybackPeriodField.setText(""+df.format(paybackPeriod));
	}
	
	private void generateAverageSolarPowerGenerationView() {
		TextView dailyField = (TextView)findViewById(R.id.TextViewDailyField);
    	TextView annualField = (TextView)findViewById(R.id.TextViewAnnualField);
    	TextView quaterlyField = (TextView)findViewById(R.id.TextViewQuaterlyField);
    	TextView netField = (TextView)findViewById(R.id.TextViewNetField);
        TextView quaterlyNet = (TextView)findViewById(R.id.TextViewQuaterlyNetField);
        TextView annualNet = (TextView)findViewById(R.id.TextViewAnnualNetField);
        
        dailyField.setText(""+df.format(calculator.getSolarPower()));
        annualField.setText(""+df.format(calculator.getSolarPower()*365));
        quaterlyField.setText(""+df.format(calculator.getSolarPower()*365/4));
        netField.setText(""+(df.format(calculator.getSolarPower() - calculator.getCustomer().getElectricityUsage().getDailyAverageUsage())));
        annualNet.setText(""+(df.format((calculator.getSolarPower() - calculator.getCustomer().getElectricityUsage().getDailyAverageUsage())*365)));
        quaterlyNet.setText(""+(df.format((calculator.getSolarPower() - calculator.getCustomer().getElectricityUsage().getDailyAverageUsage())*365/4)));
	}
	
	private void generateFinancialView(Calculation[] resultCalculations) {
		TableLayout table = (TableLayout)findViewById(R.id.TableLayoutOutput);
		
		//Incomplete
		//add table rows and views to the table
		for(Calculation curCalculation: resultCalculations) {
			//New Row 
			TableRow newRow = new TableRow(this);
			//Add entries to the row
			TextView yearView = new TextView(this);
			yearView.setText(""+df.format(curCalculation.getYear()));
			newRow.addView(yearView);
			table.addView(newRow);
		}
		
	}
	
	/**
	 * Test data for formulating the output screen
	 */
	private void testCalculationsSetup() {
		testCalculations = new Calculation[25];
		for(int year=0; year<25; year++) {
			double growthIndex = java.lang.Math.pow(1.05,year);
			testCalculations[year] = new Calculation();
			testCalculations[year].setYear(year);
			testCalculations[year].setTariff11Fee(new Double(0.1*growthIndex));
			testCalculations[year].setDailySolarPower(new Double(15*(2-growthIndex)));
			testCalculations[year].setReplacementGeneration(3.0);
			testCalculations[year].setExportedGeneration(testCalculations[year].getDailySolarPower()-testCalculations[year].getReplacementGeneration());
			testCalculations[year].setDailySaving(testCalculations[year].getTariff11Fee()*testCalculations[year].getReplacementGeneration() + 0.5*testCalculations[year].getExportedGeneration());
			testCalculations[year].setAnnualSaving(testCalculations[year].getDailySaving()*365);
			if (year==0) {
				testCalculations[year].setCumulativeSaving(testCalculations[year].getAnnualSaving());
			}
			else {
				testCalculations[year].setCumulativeSaving(testCalculations[year].getAnnualSaving()+testCalculations[year-1].getAnnualSaving());
			}
		}
		
		calculator.setSolarPower(18.0);
		calculator.getEquipment().setSize(4.5);
		calculator.getEquipment().setCost(15000);
		
	}
	
}

package au.edu.qut.inn372.greenhat.activity;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.TimeChart;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ScrollView;
import au.edu.qut.inn372.greenhat.bean.Calculation;
import au.edu.qut.inn372.greenhat.bean.Calculator;

public class CostGraphActivity extends Activity {
	
	private TabbedOutputActivity parentTabbedActivity;
	private Calculator calculator;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cost_graph);
		parentTabbedActivity = (TabbedOutputActivity) getParent();
		calculator = parentTabbedActivity.getCalculator();
		
		GraphicalView savingsChart = ChartFactory.getLineChartView(this, getCostsDataset(), getCostsRenderer());
		setContentView(savingsChart);
	}
	
	private XYMultipleSeriesDataset getCostsDataset() {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		Calculation[] calculations = calculator.getCalculations();
		
		XYSeries existingCosts = new XYSeries("Annual Electricity Cost without Solar");
		XYSeries solarCosts = new XYSeries("Annual Electricity Cost with Solar");
		for(Calculation curCalculation : calculations) {
			double annualCost = curCalculation.getTariff11Fee()*calculator.getCustomer().getElectricityUsage().getDailyAverageUsage()*365;
			System.out.println(curCalculation.getTariff11Fee());
			System.out.println(calculator.getCustomer().getElectricityUsage().getDailyAverageUsage());
			System.out.println(annualCost);
			//System.out.println(annualCost - curCalculation.getAnnualSaving());
			existingCosts.add(curCalculation.getYear(), annualCost);
			solarCosts.add(curCalculation.getYear(), annualCost - curCalculation.getAnnualSaving());
		}
		dataset.addSeries(existingCosts);
		dataset.addSeries(solarCosts);
		
		return dataset;
	}
	
	private XYMultipleSeriesRenderer getCostsRenderer() {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		XYSeriesRenderer existingCosts = new XYSeriesRenderer();
		existingCosts.setColor(Color.RED);
		existingCosts.setPointStyle(PointStyle.X);
		existingCosts.setFillBelowLine(false);
		existingCosts.setFillPoints(false);
		renderer.addSeriesRenderer(existingCosts);
		XYSeriesRenderer solarCosts = new XYSeriesRenderer();
		solarCosts.setColor(Color.BLUE);
		solarCosts.setPointStyle(PointStyle.X);
		renderer.addSeriesRenderer(solarCosts);
		//renderer.setAxesColor(Color.DKGRAY);
		//renderer.setLabelsColor(Color.LTGRAY);
		return renderer;
	}
}

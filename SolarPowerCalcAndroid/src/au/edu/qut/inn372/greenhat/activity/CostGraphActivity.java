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
		
		XYSeries existingCosts = new XYSeries("Without Solar");
		XYSeries solarCosts = new XYSeries("With Solar");
		for(Calculation curCalculation : calculations) {
			double annualCost = curCalculation.getTariff11Fee()*calculator.getCustomer().getElectricityUsage().getDailyAverageUsage()*365;
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
		existingCosts.setPointStyle(PointStyle.POINT);
		existingCosts.setLineWidth(3.0f);
		renderer.addSeriesRenderer(existingCosts);
		XYSeriesRenderer solarCosts = new XYSeriesRenderer();
		solarCosts.setColor(Color.BLUE);
		solarCosts.setPointStyle(PointStyle.POINT);
		solarCosts.setLineWidth(3.0f);
		renderer.addSeriesRenderer(solarCosts);
		
		renderer.setAxesColor(Color.BLACK);
		renderer.setLabelsColor(Color.BLACK);
		renderer.setXLabelsColor(Color.BLACK);
		renderer.setYLabelsColor(0, Color.BLACK);
		renderer.setMarginsColor(Color.WHITE);
		renderer.setYLabels(16); //Needs to be about double what we want - not sure why...it works as expected on the other graph - TP
		renderer.setXTitle("Year");
		renderer.setYTitle("Annual Electricity Cost ($)");
		renderer.setPanEnabled(false); //don't think this is working as expected
		renderer.setZoomEnabled(false); //don't think this is working as expected
		return renderer;
	}
}

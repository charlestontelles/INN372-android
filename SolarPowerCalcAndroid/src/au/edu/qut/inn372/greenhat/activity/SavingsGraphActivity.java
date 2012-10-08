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
import android.view.View;
import android.widget.ScrollView;
import au.edu.qut.inn372.greenhat.bean.Calculation;
import au.edu.qut.inn372.greenhat.bean.Calculator;

public class SavingsGraphActivity extends Activity {
	
	private TabbedOutputActivity parentTabbedActivity;
	private Calculator calculator;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_savings_graph);
		parentTabbedActivity = (TabbedOutputActivity) getParent();
		calculator = parentTabbedActivity.getCalculator();
		
		GraphicalView savingsChart = ChartFactory.getLineChartView(this, getSavingsDataset(), getSavingsRenderer());
		setContentView(savingsChart);
	}
	
	private XYMultipleSeriesDataset getSavingsDataset() {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		Calculation[] calculations = calculator.getCalculations();
		XYSeries savings = new XYSeries("Cumulative Savings");
		for(Calculation curCalculation : calculations) {
			savings.add(curCalculation.getYear(), curCalculation.getCumulativeSaving());
		}
		dataset.addSeries(savings);
		
		//payback period
		XYSeries paybackPeriod = new XYSeries("Initial Investment");
		//start point
		double systemCost = calculator.getEquipment().getCost();
		paybackPeriod.add(calculations[0].getYear(), systemCost);
		paybackPeriod.add(calculations[calculations.length-1].getYear(), systemCost);
		dataset.addSeries(paybackPeriod);
		return dataset;
	}
	
	private XYMultipleSeriesRenderer getSavingsRenderer() {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		XYSeriesRenderer savings = new XYSeriesRenderer();
		savings.setColor(Color.BLUE);
		savings.setPointStyle(PointStyle.POINT);
		savings.setLineWidth(3.0f);
		renderer.addSeriesRenderer(savings);
		
		XYSeriesRenderer paybackPeriod = new XYSeriesRenderer();
		paybackPeriod.setColor(Color.GREEN);
		paybackPeriod.setPointStyle(PointStyle.POINT);
		paybackPeriod.setLineWidth(3.0f);
		renderer.addSeriesRenderer(paybackPeriod);
		
		renderer.setAxesColor(Color.BLACK);
		renderer.setLabelsColor(Color.BLACK);
		renderer.setXLabelsColor(Color.BLACK);
		renderer.setYLabelsColor(0, Color.BLACK);
		renderer.setMarginsColor(Color.WHITE);
		renderer.setYLabels(8);
		renderer.setXTitle("Year");
		renderer.setYTitle("Savings ($)");
		renderer.setPanEnabled(false); //don't think this is working as expected
		renderer.setZoomEnabled(false); //don't think this is working as expected
		return renderer;
	}
}

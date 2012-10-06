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
		return dataset;
	}
	
	private XYMultipleSeriesRenderer getSavingsRenderer() {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		XYSeriesRenderer series1 = new XYSeriesRenderer();
		series1.setColor(Color.BLUE);
		series1.setPointStyle(PointStyle.X);
		series1.setFillBelowLine(false);
		series1.setFillPoints(false);
		renderer.addSeriesRenderer(series1);
		//renderer.setAxesColor(Color.DKGRAY);
		//renderer.setLabelsColor(Color.LTGRAY);
		return renderer;
	}
}

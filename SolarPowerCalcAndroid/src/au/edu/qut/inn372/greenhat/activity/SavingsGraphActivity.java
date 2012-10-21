package au.edu.qut.inn372.greenhat.activity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
	private List<Calculator> calculatorList;
	private static final int COLOR_LIST[] = {Color.BLUE, Color.GREEN, Color.BLACK, Color.RED, Color.CYAN, Color.YELLOW, Color.MAGENTA, Color.rgb(224, 115, 27)};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_savings_graph);
		parentTabbedActivity = (TabbedOutputActivity) getParent();
		calculatorList = parentTabbedActivity.getCalculators();
		
		GraphicalView savingsChart = ChartFactory.getLineChartView(this, getSavingsDataset(), getSavingsRenderer());
		setContentView(savingsChart);
	}
	
	private XYMultipleSeriesDataset getSavingsDataset() {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		for(Calculator curCalculator : calculatorList) {
			XYSeries savings = new XYSeries("Cumulative Savings - " + curCalculator.getName());
			Calculation[] calculations = curCalculator.getCalculations();
			for(Calculation curCalculation : calculations) {
				savings.add(curCalculation.getYear(), curCalculation.getCumulativeSaving());
			}
			dataset.addSeries(savings);
			
			//payback period
			XYSeries paybackPeriod = new XYSeries("Initial Investment - " + curCalculator.getName());
			//start point
			double systemCost = curCalculator.getEquipment().getCost();
			paybackPeriod.add(calculations[0].getYear(), systemCost);
			paybackPeriod.add(calculations[calculations.length-1].getYear(), systemCost);
			dataset.addSeries(paybackPeriod);
		}
		
		return dataset;
	}
	
	private XYMultipleSeriesRenderer getSavingsRenderer() {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		int renderCount = 0; //which color to use for the current render
		for(Calculator curCalculator : calculatorList) {
			XYSeriesRenderer savings = new XYSeriesRenderer();
			savings.setColor(COLOR_LIST[renderCount]);
			savings.setPointStyle(PointStyle.POINT);
			savings.setLineWidth(3.0f);
			renderer.addSeriesRenderer(savings);
			
			renderCount = incrementRenderCount(renderCount);
			
			XYSeriesRenderer paybackPeriod = new XYSeriesRenderer();
			paybackPeriod.setColor(COLOR_LIST[renderCount]);
			paybackPeriod.setPointStyle(PointStyle.POINT);
			paybackPeriod.setLineWidth(3.0f);
			renderer.addSeriesRenderer(paybackPeriod);
			
			renderCount = incrementRenderCount(renderCount);
		}
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
	
	private int incrementRenderCount(int curRenderCount) {
		if (curRenderCount == COLOR_LIST.length - 1) {
			curRenderCount = 0;
		}
		else {
			curRenderCount += 1;
		}
		return curRenderCount;
	}
	
    @Override
    public void onBackPressed() {
    	parentTabbedActivity.onBackPressed();
    }
}

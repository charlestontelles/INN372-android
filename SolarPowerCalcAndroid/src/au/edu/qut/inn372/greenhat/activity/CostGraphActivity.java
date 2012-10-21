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

public class CostGraphActivity extends Activity {
	
	private TabbedOutputActivity parentTabbedActivity;
	private List<Calculator> calculatorList;
	private static final int COLOR_LIST[] = {Color.BLUE, Color.GREEN, Color.BLACK, Color.RED, Color.CYAN, Color.YELLOW, Color.MAGENTA, Color.rgb(224, 115, 27)};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cost_graph);
		parentTabbedActivity = (TabbedOutputActivity) getParent();
		calculatorList = parentTabbedActivity.getCalculators();
		
		GraphicalView savingsChart = ChartFactory.getLineChartView(this, getCostsDataset(), getCostsRenderer());
		setContentView(savingsChart);
	}
	
	private XYMultipleSeriesDataset getCostsDataset() {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		for(Calculator curCalculator : calculatorList) {
			Calculation[] calculations = curCalculator.getCalculations();
			XYSeries existingCosts = new XYSeries("Without Solar - " + curCalculator.getName());
			XYSeries solarCosts = new XYSeries("With Solar - " + curCalculator.getName());
			for(Calculation curCalculation : calculations) {
				double annualCost = curCalculation.getTariff11Fee()*curCalculator.getCustomer().getElectricityUsage().getDailyAverageUsage()*365;
				existingCosts.add(curCalculation.getYear(), annualCost);
				solarCosts.add(curCalculation.getYear(), annualCost - curCalculation.getAnnualSaving());
			}
			dataset.addSeries(existingCosts);
			dataset.addSeries(solarCosts);
		}
		return dataset;
	}
	
	private XYMultipleSeriesRenderer getCostsRenderer() {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		int renderCount = 0; //which color to use for the current render
		for(Calculator curCalculator : calculatorList) {
			XYSeriesRenderer existingCosts = new XYSeriesRenderer();
			existingCosts.setColor(COLOR_LIST[renderCount]);
			existingCosts.setPointStyle(PointStyle.POINT);
			existingCosts.setLineWidth(3.0f);
			renderer.addSeriesRenderer(existingCosts);
			
			renderCount = incrementRenderCount(renderCount);
			
			XYSeriesRenderer solarCosts = new XYSeriesRenderer();
			solarCosts.setColor(COLOR_LIST[renderCount]);
			solarCosts.setPointStyle(PointStyle.POINT);
			solarCosts.setLineWidth(3.0f);
			renderer.addSeriesRenderer(solarCosts);
			
			renderCount = incrementRenderCount(renderCount);
		}
		
		
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
    
    @Override
    public void onResume() {
    	super.onResume();
    	parentTabbedActivity.setTabId(TabbedOutputActivity.COST_GRAPH_ID);
    }
}

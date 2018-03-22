package utils;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

@SuppressWarnings("serial")
public class Plot extends JFrame {

	public Plot(double[] avgValues, double[] bestValues) {
		super("Genetic Algorithm");
		JPanel chartPanel = createChartPanel(avgValues, bestValues);
		add(chartPanel, BorderLayout.CENTER);
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private JPanel createChartPanel(double[] avgValues, double[] bestValues) {
		String chartTitle = "Fitness Chart";
		String xAxisLabel = "Generations";
		String yAxisLabel = "Fitness Value";

		XYDataset dataset = createDataset(avgValues, bestValues);

		JFreeChart chart = ChartFactory.createXYLineChart(chartTitle,
				xAxisLabel, yAxisLabel, dataset);

		return new ChartPanel(chart);
	}

	private XYDataset createDataset(double[] avgValues, double[] bestValues) {

		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series1 = new XYSeries("Average Fitness");
		XYSeries series2 = new XYSeries("Best Fitness");

		for (int i = 0; i < avgValues.length; i++) {
			series1.add(i, avgValues[i]);
			series2.add(i, bestValues[i]);
		}

		dataset.addSeries(series1);
		dataset.addSeries(series2);

		return dataset;
	}

}

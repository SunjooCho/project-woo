package com.chiwanpark.woo.view;

import com.chiwanpark.woo.model.TimeSeriesDataset;
import com.chiwanpark.woo.model.TimeSeriesDatum;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.List;

@Component
public class TimeSeriesChartView extends JInternalFrame {
  public TimeSeriesChartView(String title, TimeSeriesDataset dataset) {
    super(title);

    XYDataset jfreeDataset = createDataSet(dataset);
    ChartPanel pnChart = createChart(title, jfreeDataset);

    setContentPane(pnChart);
    setSize(640, 320);
    setVisible(true);
    setClosable(true);
    setResizable(true);
  }

  private XYDataset createDataSet(TimeSeriesDataset dataset) {
    TimeSeriesCollection collection = new TimeSeriesCollection();

    for (int i = 0; i < dataset.size(); ++i) {
      String seriesName = dataset.getSeriesName(i);
      List<TimeSeriesDatum<Double>> data = dataset.getData(i);

      TimeSeries series = new TimeSeries(seriesName);

      for (TimeSeriesDatum<Double> datum : data) {
        series.add(new Second(datum.getDate()), datum.getDatum());
      }

      collection.addSeries(series);
    }

    return collection;
  }

  private ChartPanel createChart(String title, XYDataset dataset) {
    JFreeChart chart = ChartFactory.createTimeSeriesChart(title, "Date", "Value", dataset);
    ChartPanel pnChart = new ChartPanel(chart, true);
    pnChart.setPopupMenu(null);

    return pnChart;
  }
}
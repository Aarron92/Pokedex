package com.soob.pokedex.charts;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;

import java.util.List;

public interface BarChartInterface
{
    default void populateChart(HorizontalBarChart chart, List<BarEntry> entries)
    {
        // add the chart details
        setStandardLookAndFeel(chart);

        // set the X axis up
        setStandardXAxis(chart);

        // set the Y axis up
        setStandardYAxis(chart);

        // create the data set and add it to the chart
        BarDataSet dataSet = createBarDataSet(chart, entries);
        setDataUsedOnTheChart(dataSet, chart);
    }

    /**
     * Set various details around the charts look and feel - description is set to empty so
     * nothing is displayed
     */
    default void setStandardLookAndFeel(HorizontalBarChart baseStatsChart)
    {
        Description description = new Description();
        description.setText("");
        baseStatsChart.setDescription(description);
        baseStatsChart.getLegend().setEnabled(false);
        baseStatsChart.setDrawBarShadow(false);
        baseStatsChart.setPinchZoom(false);
        baseStatsChart.setDrawValueAboveBar(false);
    }

    /**
     * Set the standard X axis details
     */
    void setStandardXAxis(HorizontalBarChart chart);

    /**
     * Set the standard Y axis details
     */
    void setStandardYAxis(HorizontalBarChart chart);

    default BarDataSet createBarDataSet(HorizontalBarChart chart, List<BarEntry> entries)
    {
        BarDataSet barDataSet = new BarDataSet(entries, getBarChartTitle());

        // make the bar values integers instead of floats
        barDataSet.setValueFormatter(getValueFormatter());

        // Set bar shadows
        chart.setDrawBarShadow(true);
        barDataSet.setBarShadowColor(Color.argb(40, 150, 150, 150));

        //Set the colors for bars
        barDataSet.setColors(getEntryColours(chart.getContext()));

        return barDataSet;
    }

    List<Integer> getEntryColours(Context context);

    String getBarChartTitle();

    IValueFormatter getValueFormatter();

    /**
     * TODO: STOP THE GRAPH FROM BEING PINCH-AND-ZOOMABLE
     */
    default void setDataUsedOnTheChart(BarDataSet barDataset, HorizontalBarChart chart)
    {
        BarData data = new BarData(barDataset);

        // Set the bar width - to increase the spacing between the bars set the value of barWidth
        // to < 1f
        data.setBarWidth(0.9f);

        // Finally set the data and refresh the graph
        chart.setData(data);
        chart.invalidate();

        // Add animation to the graph
        chart.animateY(1000);
    }
}

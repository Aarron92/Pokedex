package com.soob.pokedex.charts;

import android.graphics.Color;

import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.soob.pokedex.R;
import com.soob.pokedex.activities.PokemonDetailsActivity;
import com.soob.pokedex.entities.BaseStats;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for creating a chart to display information related to base stats
 * TODO: THE TWO CHART FACTORY CALSS (NOT REALLY FACTORY BUT WHATEVER) CLASS SHOULD PROBABLY SHARE
 *  INTERFACE/SUPER AS THE METHODS ARE BASICALLY THE SAME, JUST DIFFERENT IMPLS
 */
public class BaseStatsChartFactory
{
    public static void createChart(PokemonDetailsActivity activity, BaseStats baseStats)
    {
        // create the bar chart within the relevant activity
        HorizontalBarChart baseStatsChart = activity.findViewById(R.id.detailsBaseStatsChart);

        // add the chart details
        setStandardLookAndFeel(baseStatsChart);

        // set the Y axes up
        setStandardYAxis(baseStatsChart);

        // set the X axes up
        setStandardXAxis(baseStatsChart);

        // create the data set and add it to the chart
        BarDataSet dataSet = createBarDataSet(baseStatsChart, baseStats);
        setDataUsedOnTheChart(dataSet, baseStatsChart);
    }

    /**
     * Set various details around the charts look and feel - description is set to empty so
     * nothing is displayed
     */
    private static void setStandardLookAndFeel(HorizontalBarChart baseStatsChart)
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
     * Set the standard Y axis details
     */
    private static void setStandardYAxis(HorizontalBarChart baseStatsChart)
    {
        // set up the Y-axis - not having labels or anything
        YAxis yLeft = baseStatsChart.getAxisLeft();
        yLeft.setAxisMinimum(0f);
        yLeft.setAxisMaximum(255f);
        yLeft.setEnabled(false);

        YAxis yRight = baseStatsChart.getAxisRight();
        yRight.setDrawAxisLine(true);
        yRight.setDrawGridLines(false);
        yRight.setEnabled(false);
    }

    /**
     * Set the standard X axis details
     */
    private static void setStandardXAxis(HorizontalBarChart baseStatsChart)
    {
        XAxis xAxis = baseStatsChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setLabelCount(6);

        // now add the labels to be added on the X-axis (displayed in reverse order)
        String[] statsChartLabels = new String[6];
        statsChartLabels[0] = "Speed";
        statsChartLabels[1] = "Sp. Defense";
        statsChartLabels[2] = "Sp. Attack";
        statsChartLabels[3] = "Defense";
        statsChartLabels[4] = "Attack";
        statsChartLabels[5] = "HP";
        xAxis.setValueFormatter(new XAxisValueFormatter(statsChartLabels));
    }

    /**
     * Create the data set and set how it's displayed
     */
    private static BarDataSet createBarDataSet(HorizontalBarChart baseStatsChart, BaseStats baseStats)
    {
        //Set bar entries and add necessary formatting
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(5f, baseStats.getHp()));
        entries.add(new BarEntry(4f, baseStats.getAttack()));
        entries.add(new BarEntry(3f, baseStats.getDefence()));
        entries.add(new BarEntry(2f, baseStats.getSpecialAttack()));
        entries.add(new BarEntry(1f, baseStats.getSpecialDefence()));
        entries.add(new BarEntry(0f, baseStats.getSpeed()));

        BarDataSet barDataSet = new BarDataSet(entries, "Base Stats");

        //Set the colors for bars
        barDataSet.setColors(
                ContextCompat.getColor(baseStatsChart.getContext(), R.color.base_stat_colour_hp),
                ContextCompat.getColor(baseStatsChart.getContext(), R.color.base_stat_colour_atk),
                ContextCompat.getColor(baseStatsChart.getContext(), R.color.base_stat_colour_def),
                ContextCompat.getColor(baseStatsChart.getContext(), R.color.base_stat_colour_sp_atk),
                ContextCompat.getColor(baseStatsChart.getContext(), R.color.base_stat_colour_sp_def),
                ContextCompat.getColor(baseStatsChart.getContext(), R.color.base_stat_colour_speed));

        // make the bar values integers instead of floats
        barDataSet.setValueFormatter(new IntValueFormatter());

        // Set bar shadows
        baseStatsChart.setDrawBarShadow(true);
        barDataSet.setBarShadowColor(Color.argb(40, 150, 150, 150));

        return barDataSet;
    }

    /**
     * TODO: STOP THE GRAPH FROM BEING PINCH-AND-ZOOMABLE
     */
    private static void setDataUsedOnTheChart(BarDataSet barDataSet, HorizontalBarChart baseStatsChart)
    {
        BarData data = new BarData(barDataSet);

        // Set the bar width - to increase the spacing between the bars set the value of barWidth to < 1f
        data.setBarWidth(0.9f);

        // Finally set the data and refresh the graph
        baseStatsChart.setData(data);
        baseStatsChart.invalidate();

        // Add animation to the graph
        baseStatsChart.animateY(1000);
    }

    /**
     * Class used to format the base stats chart data X Axis
     */
    static class XAxisValueFormatter implements IAxisValueFormatter
    {
        private final String[] values;

        public XAxisValueFormatter(String[] values)
        {
            this.values = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis)
        {
            // "value" represents the position of the label on the axis (x or y)
            return this.values[(int) value];
        }
    }

    /**
     * Formatter to make the Y bar values integers instead of floating point
     */
    static class IntValueFormatter implements IValueFormatter
    {
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler)
        {
            return String.valueOf((int) value);
        }
    }
}

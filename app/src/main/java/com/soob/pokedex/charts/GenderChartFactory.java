package com.soob.pokedex.charts;

import android.graphics.Color;

import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.soob.pokedex.R;
import com.soob.pokedex.activities.PokemonDetailsActivity;
import com.soob.pokedex.inputlisteners.service.details.GenderService;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for creating a chart related to a Pokemon's gender ratio
 */
public class GenderChartFactory
{
    public static void createGenderRatioChart(PokemonDetailsActivity activity, int genderRatio)
    {
        // create the bar chart within the relevant activity
        HorizontalBarChart genderRatioChart = activity.findViewById(R.id.detailsGenderRatioChart);

        // add the chart details
        setStandardLookAndFeel(genderRatioChart);

        // set the Y axes up
        setStandardYAxis(genderRatioChart);

        // create the data set and add it to the chart
        BarDataSet dataSet = createBarDataSet(genderRatioChart, genderRatio);
        setDataUsedOnTheChart(dataSet, genderRatioChart);
    }

    /**
     * Set various details around the charts look and feel - description is set to empty so
     * nothing is displayed
     */
    private static void setStandardLookAndFeel(HorizontalBarChart genderRatioChart)
    {
        Description description = new Description();
        description.setText("");
        genderRatioChart.setDescription(description);
        genderRatioChart.getLegend().setEnabled(false);
        genderRatioChart.setDrawBarShadow(false);
        genderRatioChart.setPinchZoom(false);
        genderRatioChart.setDrawValueAboveBar(false);
        genderRatioChart.getXAxis().setEnabled(false);
    }

    /**
     * Set the standard Y axis details
     */
    private static void setStandardYAxis(HorizontalBarChart genderRatioChart)
    {
        // set up the Y-axis - not having labels or anything
        YAxis yLeft = genderRatioChart.getAxisLeft();
        yLeft.setAxisMinimum(0f);
        yLeft.setAxisMaximum(100f);
        yLeft.setEnabled(false);

        YAxis yRight = genderRatioChart.getAxisRight();
        yRight.setDrawAxisLine(true);
        yRight.setDrawGridLines(false);
        yRight.setEnabled(false);
    }

    /**
     * Create the data set and set how it's displayed
     */
    private static BarDataSet createBarDataSet(HorizontalBarChart genderRatioChart, int genderRatio)
    {
        // Set bar entries and add necessary formatting
        List<BarEntry> entries = new ArrayList<>();

        entries.add(
                new BarEntry(0f, new float[]
                        {
                                GenderService.getGenderRatioCalculatedMale(genderRatio),
                                GenderService.getGenderRatioCalculatedFemale(genderRatio)
                        }
                ));

        BarDataSet barDataSet = new BarDataSet(entries, "Gender Ratio");

        //Set the colors for bars
        barDataSet.setColors(
                ContextCompat.getColor(genderRatioChart.getContext(), R.color.gender_ratio_boy),
                ContextCompat.getColor(genderRatioChart.getContext(), R.color.gender_ratio_girl));

        // make the bar values integers instead of floats
        barDataSet.setValueFormatter(new GenderValueFormatter());
        barDataSet.setValueTextColor(R.color.white);

        //Set bar shadows
        genderRatioChart.setDrawBarShadow(true);
        barDataSet.setBarShadowColor(Color.argb(40, 150, 150, 150));

        return barDataSet;
    }

    /**
     * TODO: STOP THE GRAPH FROM BEING PINCH-AND-ZOOMABLE
     */
    private static void setDataUsedOnTheChart(BarDataSet barDataSet, HorizontalBarChart genderRatioChart)
    {
        BarData data = new BarData(barDataSet);

        //Set the bar width - to increase the spacing between the bars set the value of barWidth to < 1f
        data.setBarWidth(0.9f);

        //Finally set the data and refresh the graph
        genderRatioChart.setData(data);
        genderRatioChart.invalidate();

        //Add animation to the graph
        genderRatioChart.animateY(1000);
    }

    /**
     * Formatter to make the Y bar values integers instead of floating point when the number is a
     * whole number, and add percentages next to the gender value
     */
    static class GenderValueFormatter implements IValueFormatter
    {
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler)
        {
            String returnValue;

            // if the value is a whole number, return it without decimal places
            if(value % 1 == 0)
            {
                returnValue = (int) value + "%";
            }
            else
            {
                returnValue = value + "%";
            }
            return returnValue;
        }
    }
}

package com.soob.pokedex.charts.gender;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.soob.pokedex.R;
import com.soob.pokedex.charts.BarChartInterface;
import com.soob.pokedex.inputlisteners.service.details.GenderService;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation providing specifics for creating a bar chart for Gender data
 */
public class GenderChart implements BarChartInterface
{
    @Override
    public void setStandardXAxis(HorizontalBarChart chart)
    {
        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setLabelCount(0);
    }

    @Override
    public void setStandardYAxis(HorizontalBarChart chart)
    {
        // set up the Y-axis - not having labels or anything
        YAxis yLeft = chart.getAxisLeft();
        yLeft.setAxisMinimum(0f);
        yLeft.setAxisMaximum(100f);
        yLeft.setEnabled(false);

        YAxis yRight = chart.getAxisRight();
        yRight.setDrawAxisLine(false);
        yRight.setDrawGridLines(false);
        yRight.setEnabled(false);
    }

    @Override
    public List<Integer> getEntryColours(Context context)
    {
        List<Integer> colours = new ArrayList<>();

        colours.add(ContextCompat.getColor(context, R.color.gender_ratio_boy));
        colours.add(ContextCompat.getColor(context, R.color.gender_ratio_girl));

        return colours;
    }

    @Override
    public String getBarChartTitle()
    {
        return "Gender Ratio";
    }

    @Override
    public IValueFormatter getValueFormatter()
    {
        return new GenderValueFormatter();
    }

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

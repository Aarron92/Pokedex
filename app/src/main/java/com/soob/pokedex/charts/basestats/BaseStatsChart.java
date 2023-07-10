package com.soob.pokedex.charts.basestats;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.soob.pokedex.R;
import com.soob.pokedex.charts.BarChartInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation providing specifics for creating a bar chart for Base Stat data
 */
public class BaseStatsChart implements BarChartInterface
{
    @Override
    public void setStandardXAxis(HorizontalBarChart chart)
    {
        XAxis xAxis = chart.getXAxis();
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

    @Override
    public void setStandardYAxis(HorizontalBarChart chart)
    {
        // set up the Y-axis - not having labels or anything
        YAxis yLeft = chart.getAxisLeft();
        yLeft.setAxisMinimum(0f);
        yLeft.setAxisMaximum(255f);
        yLeft.setEnabled(false);

        YAxis yRight = chart.getAxisRight();
        yRight.setDrawAxisLine(true);
        yRight.setDrawGridLines(false);
        yRight.setEnabled(false);
    }

    @Override
    public List<Integer> getEntryColours(Context context)
    {
        List<Integer> colours = new ArrayList<>();

        colours.add(ContextCompat.getColor(context, R.color.base_stat_colour_hp));
        colours.add(ContextCompat.getColor(context, R.color.base_stat_colour_atk));
        colours.add(ContextCompat.getColor(context, R.color.base_stat_colour_def));
        colours.add(ContextCompat.getColor(context, R.color.base_stat_colour_sp_atk));
        colours.add(ContextCompat.getColor(context, R.color.base_stat_colour_sp_def));
        colours.add(ContextCompat.getColor(context, R.color.base_stat_colour_speed));

        return colours;
    }

    @Override
    public String getBarChartTitle()
    {
        return "Base Stats";
    }

    @Override
    public IValueFormatter getValueFormatter()
    {
        return new BaseStatValueFormatter();
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
    static class BaseStatValueFormatter implements IValueFormatter
    {
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler)
        {
            return String.valueOf((int) value);
        }
    }
}

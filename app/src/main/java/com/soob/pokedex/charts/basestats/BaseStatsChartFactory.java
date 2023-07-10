package com.soob.pokedex.charts.basestats;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarEntry;
import com.soob.pokedex.R;
import com.soob.pokedex.activities.PokemonDetailsActivity;
import com.soob.pokedex.entities.BaseStats;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory for creating a chart to display information related to base stats
 *
 * TODO: Might be worth making an abstract superclass for these factories to reuse again, data bit
 *  makes things a bit annoying though
 */
public class BaseStatsChartFactory
{
    public static void createBaseStatsChart(PokemonDetailsActivity activity, BaseStats baseStats)
    {
        // create the bar chart within the relevant activity
        HorizontalBarChart barChart = activity.findViewById(R.id.detailsBaseStatsChart);

        BaseStatsChart baseStatsChart = new BaseStatsChart();

        List<BarEntry> dataEntries = createDataEntries(baseStats);

        baseStatsChart.populateChart(barChart, dataEntries);
    }

    private static List<BarEntry> createDataEntries(BaseStats baseStats)
    {
        List<BarEntry> entries = new ArrayList<>();

        entries.add(new BarEntry(5f, baseStats.getHp()));
        entries.add(new BarEntry(4f, baseStats.getAttack()));
        entries.add(new BarEntry(3f, baseStats.getDefence()));
        entries.add(new BarEntry(2f, baseStats.getSpecialAttack()));
        entries.add(new BarEntry(1f, baseStats.getSpecialDefence()));
        entries.add(new BarEntry(0f, baseStats.getSpeed()));

        return entries;
    }
}

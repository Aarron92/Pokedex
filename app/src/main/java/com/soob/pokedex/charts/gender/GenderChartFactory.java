package com.soob.pokedex.charts.gender;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarEntry;
import com.soob.pokedex.R;
import com.soob.pokedex.activities.PokemonDetailsActivity;
import com.soob.pokedex.inputlisteners.service.details.GenderService;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory for creating a chart related to a Pokemon's gender ratio
 *
 * TODO: Might be worth making an abstract superclass for these factories to reuse again, data bit
 *  makes things a bit annoying though
 */
public class GenderChartFactory
{
    public static void createGenderRatioChart(PokemonDetailsActivity activity, int genderRatio)
    {
        // create the bar chart within the relevant activity
        HorizontalBarChart barChart = activity.findViewById(R.id.detailsGenderRatioChart);

        GenderChart genderChart = new GenderChart();

        List<BarEntry> dataEntries = createDataEntries(genderRatio);

        genderChart.populateChart(barChart, dataEntries);
    }

    private static List<BarEntry> createDataEntries(int genderRatio)
    {
        List<BarEntry> entries = new ArrayList<>();

        entries.add(
                new BarEntry(0f, new float[]
                        {
                                GenderService.getGenderRatioCalculatedMale(genderRatio),
                                GenderService.getGenderRatioCalculatedFemale(genderRatio)
                        }
                ));

        return entries;
    }
}

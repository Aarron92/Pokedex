package com.soob.pokedex.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.soob.pokedex.adapters.PokemonDetailsAbilitiesAdapter;
import com.soob.pokedex.enums.TypeColourEnum;
import com.soob.pokedex.entities.Pokemon;
import com.soob.pokedex.web.querythreads.PokemonDetailsQueryThreadCallable;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity class for the page showing the full details of a Pokemon
 */
public class PokemonDetailsActivity extends AppCompatActivity
{
    /**
     * The Pokemon containing all of the various details
     */

    /**
     * CardView showing the summary data - number, name, species and types
     */
    private CardView summaryCardView;

    /**
     * CardView showing the more detailed overview information - height, weight, information and
     * gender ratio (TBA)
     */
    private CardView informationCardView;

    /**
     * The RecyclerView used for the layout of the abilities details section
     */
    private RecyclerView abilitiesRecyclerView;

    /**
     * The adapter used to bind the UI and the data
     */
    private PokemonDetailsAbilitiesAdapter dataAdapter;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // call super class onCreate()
        super.onCreate(savedInstanceState);

        // set the screen to be displayed - in this case the details screen of an individual Pokemon
        setContentView(R.layout.activity_pokemon_details);

        // initialise the UI elements
        this.summaryCardView = findViewById(R.id.detailsSummaryCardView);
        this.informationCardView = findViewById(R.id.detailsInformationCardView);
        this.abilitiesRecyclerView = findViewById(R.id.detailsAbilitiesRecyclerView);
        this.abilitiesRecyclerView.setHasFixedSize(true);
        this.abilitiesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // TODO: STOP IT SCROLLING WITHIN THE CARD SOMEHOW

        // get the name of the Pokemon that was passed in from the Dex list when it was passed in
        Intent intent = getIntent();
        String pokemonNumber = intent.getExtras().get(DexListActivity.POKEMON_NUMBER_KEY).toString();
        String pokemonName = intent.getExtras().get(DexListActivity.POKEMON_NAME_KEY).toString();

        try
        {
            // populate the data on the screen
            populateDataOnScreen(pokemonNumber, pokemonName);
        }
        catch(Exception exc)
        {
            Toast.makeText(getApplicationContext(),
                    String.format("Uh oh, something went wrong when trying to get details about the Pokémon %s!", pokemonName),
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     *
     * @param pokemonNumber
     * @param pokemonName
     * @throws Exception
     */
    private void populateDataOnScreen(final String pokemonNumber, final String pokemonName) throws Exception
    {
        // query the API to get the full details about the Pokemon
        Pokemon pokemon =
                new PokemonDetailsQueryThreadCallable(this, this.abilitiesRecyclerView,
                        this.dataAdapter).execute(pokemonNumber, pokemonName);

        // summary containing number, name, species and type etc
        populateSummaryCard(pokemon);

        // information about height, weight and flavour text etc
        populateInformationCard(pokemon);

        // set the background of the screen to match the Pokemon's primary type
        LinearLayout linearLayout = this.findViewById(R.id.detailsActivityLayout);
        linearLayout.setBackgroundColor(ContextCompat.getColor(this.getApplicationContext(),
                getTypeColourKey(pokemon.getPrimaryType())));

        // the abilities section is not set explicitly but is instead handled by and adapter in the
        // querying thread so nothing to do here

        // set the gender ration display using a stacked horizontal bar chart
        setGenderRatioDataChart(pokemon);

        // set the base stats display using a horizontal bar chart
        setBaseStatsDataChart(pokemon);
        TextView baseStatTotalTextView = this.findViewById(R.id.detailsBaseStatsTotal);
        final String baseStateTotalString = "Total: " + pokemon.getBaseStatsTotal();
        baseStatTotalTextView.setText(baseStateTotalString);
    }

    /**
     *
     * @param pokemon
     */
    private void populateSummaryCard(final Pokemon pokemon)
    {
        // artwork
        ImageView artwork = this.summaryCardView.findViewById(R.id.detailsPokemonArtworkImageView);
        artwork.setImageBitmap(pokemon.getArtwork());

        // number
        TextView numberText = this.summaryCardView.findViewById(R.id.detailsPokemonNumberTextView);
        final String numberString = "#" + pokemon.getNumber();
        numberText.setText(numberString);

        // name
        TextView nameText = this.summaryCardView.findViewById(R.id.detailsPokemonNameTextView);
        nameText.setText(pokemon.getName());

        // species - TODO: NOT RETRIEVED IN THE RESPONSE YET
        // TextView speciesText = this.summaryCardView.findViewById(R.id.detailsPokemonSpeciesTextView);
        // speciesText.setText();

        // primary type
        TextView primaryTypeText = this.summaryCardView.findViewById(R.id.detailsPokemonPrimaryTypeTextView);
        primaryTypeText.setBackgroundColor(
                ContextCompat.getColor(this.getApplicationContext(), getTypeColourKey(pokemon.getPrimaryType())));
        primaryTypeText.setText(pokemon.getPrimaryType().toUpperCase());

        // secondary type - not all Pokemon have this so if not just set the text to dash to signify
        // the Pokemon not having one
        TextView secondaryTypeText = this.summaryCardView.findViewById(R.id.detailsPokemonSecondaryTypeTextView);
        final String secondaryType = pokemon.getSecondaryType() != null ? pokemon.getSecondaryType().toUpperCase() : "-";
        secondaryTypeText.setBackgroundColor(
                ContextCompat.getColor(this.getApplicationContext(), getTypeColourKey(secondaryType)));
        secondaryTypeText.setText(secondaryType);
    }

    /**
     *
     * @param type
     * @return
     */
    private int getTypeColourKey(final String type)
    {
        // use the null type as default
        int colourKey = R.color.type_colour_null;

        // loop through the colours and find the relevant one
        for(TypeColourEnum typeColourEnum : TypeColourEnum.values())
        {
            if(type.toLowerCase().equals(typeColourEnum.getKey()))
            {
                colourKey = typeColourEnum.getValue();
            }
        }
        return colourKey;
    }

    /**
     *
     * @param pokemon
     */
    private void populateInformationCard(final Pokemon pokemon)
    {
        // height - TODO: Not yet retrieved so not set yet
        TextView heightText = this.findViewById(R.id.detailsPokemonHeightValueTextView);
        //heightText.setText(pokemon.getHeight());

        // weight - TODO: Not yet retrieved so not set yet
        TextView weightText = this.findViewById(R.id.detailsPokemonWeightValueTextView);
        //weightText.setText(pokemon.getWeight());

        // flavour text
        TextView flavourText = this.findViewById(R.id.detailsPokemonFlavourTextTextView);
        flavourText.setText(pokemon.getFlavourText());
    }

    /**
     * Create Bar chart for the Base Stats
     *
     * TODO: STOP THE GRAPH FROM BEING PINCH-AND-ZOOMABLE
     *
     * TODO: Probably worth pulling this into a generic bar chart class for base stats and gender ratio
     */
    private void setGenderRatioDataChart(final Pokemon pokemon)
    {
        // create the bar chart based from the layout object
        HorizontalBarChart genderRatioChart = findViewById(R.id.detailsGenderRatioChart);

        // set various details around the charts look and feel - description is set to empty so
        // nothing is displayed
        Description description = new Description();
        description.setText("");
        genderRatioChart.setDescription(description);
        genderRatioChart.getLegend().setEnabled(false);
        genderRatioChart.setDrawBarShadow(false);
        genderRatioChart.setPinchZoom(false);
        genderRatioChart.setDrawValueAboveBar(false);
        genderRatioChart.getXAxis().setEnabled(false);

        // set up the Y-axis - not having labels or anything
        YAxis yLeft = genderRatioChart.getAxisLeft();
        yLeft.setAxisMinimum(0f);
        yLeft.setAxisMaximum(100f);
        yLeft.setEnabled(false);

        YAxis yRight = genderRatioChart.getAxisRight();
        yRight.setDrawAxisLine(true);
        yRight.setDrawGridLines(false);
        yRight.setEnabled(false);

        //Set bar entries and add necessary formatting
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, new float[]
                {pokemon.getGenderRatioCalcuatedMale(), pokemon.getGenderRatioCalcuatedFemale()}));

        BarDataSet barDataSet = new BarDataSet(entries, "Bar Data Set");

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
     * Create Bar chart for the Base Stats
     *
     * TODO: Probably worth pulling this into a generic bar chart class for base stats and gender ratio
     */
    private void setBaseStatsDataChart(final Pokemon pokemon)
    {
        // create the bar chart based from the layout object
        HorizontalBarChart baseStatsChart = findViewById(R.id.detailsBaseStatsChart);

        // set various details around the charts look and feel - description is set to empty so
        // nothing is displayed
        Description description = new Description();
        description.setText("");
        baseStatsChart.setDescription(description);
        baseStatsChart.getLegend().setEnabled(false);
        baseStatsChart.setDrawBarShadow(false);
        baseStatsChart.setPinchZoom(false);
        baseStatsChart.setDrawValueAboveBar(false);

        // display the axis on the left
        XAxis xAxis = baseStatsChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setLabelCount(6);

        // set up the Y-axis - not having labels or anything
        YAxis yLeft = baseStatsChart.getAxisLeft();
        yLeft.setAxisMinimum(0f);
        yLeft.setAxisMaximum(255f);
        yLeft.setEnabled(false);

        YAxis yRight = baseStatsChart.getAxisRight();
        yRight.setDrawAxisLine(true);
        yRight.setDrawGridLines(false);
        yRight.setEnabled(false);

        // now add the labels to be added on the vertical X-axis (displayed in reverse order)
        String[] statsChartLabels = new String[6];
        statsChartLabels[0] = "Speed";
        statsChartLabels[1] = "Sp. Defense";
        statsChartLabels[2] = "Sp. Attack";
        statsChartLabels[3] = "Defense";
        statsChartLabels[4] = "Attack";
        statsChartLabels[5] = "HP";
        xAxis.setValueFormatter(new XAxisValueFormatter(statsChartLabels));

        //Set bar entries and add necessary formatting
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(5f, pokemon.getBaseStats().getHp()));
        entries.add(new BarEntry(4f, pokemon.getBaseStats().getAttack()));
        entries.add(new BarEntry(3f, pokemon.getBaseStats().getDefence()));
        entries.add(new BarEntry(2f, pokemon.getBaseStats().getSpecialAttack()));
        entries.add(new BarEntry(1f, pokemon.getBaseStats().getSpecialDefence()));
        entries.add(new BarEntry(0f, pokemon.getBaseStats().getSpeed()));

        BarDataSet barDataSet = new BarDataSet(entries, "Bar Data Set");

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

        //Set bar shadows
        baseStatsChart.setDrawBarShadow(true);
        barDataSet.setBarShadowColor(Color.argb(40, 150, 150, 150));
        BarData data = new BarData(barDataSet);

        //Set the bar width - to increase the spacing between the bars set the value of barWidth to < 1f
        data.setBarWidth(0.9f);

        //Finally set the data and refresh the graph
        baseStatsChart.setData(data);
        baseStatsChart.invalidate();

        //Add animation to the graph
        baseStatsChart.animateY(2000);
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

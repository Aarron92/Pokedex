package com.soob.pokedex.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soob.pokedex.entities.evolution.EvolutionChain;
import com.soob.pokedex.fragments.EvolutionChainFragment_3Stage;
import com.soob.pokedex.R;
import com.soob.pokedex.adapters.PokemonDetailsAbilitiesAdapter;
import com.soob.pokedex.charts.basestats.BaseStatsChartFactory;
import com.soob.pokedex.charts.gender.GenderChartFactory;
import com.soob.pokedex.entities.Pokemon;
import com.soob.pokedex.inputlisteners.service.details.PokemonService;
import com.soob.pokedex.inputlisteners.service.details.TypesService;

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
     * gender ratio
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

        // get the name and number of the Pokemon that was passed in from the Dex list
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
     */
    private void populateDataOnScreen(final String pokemonNumber, final String pokemonName) throws Exception
    {
        // create a Pokemon object with all of it's details. This requires queries to be done on a
        // thread separate from the main UI thread
        Pokemon pokemon = PokemonService.createPokemonInSeparateThread(
                this, this.abilitiesRecyclerView, pokemonNumber, pokemonName);

        // set the background of the screen to match the Pokemon's primary type
        setBackgroundColourToMatchPrimaryType(pokemon.getPrimaryType());

        // summary containing number, name, species and type etc
        populateSummaryCard(pokemon);

        // information about height, weight and flavour text etc
        populateInformationCard(pokemon);

        // the abilities section is not set explicitly but is instead handled by an adapter in the
        // querying thread so nothing to do here

        // set the gender ratio display using a stacked horizontal bar chart
        GenderChartFactory.createGenderRatioChart(this, pokemon.getGenderRatio());

        // set the base stats display using a horizontal bar chart
        BaseStatsChartFactory.createBaseStatsChart(this, pokemon.getBaseStats());
        setBaseStatsTotal(pokemon.getBaseStatsTotal());

        // display the evolution chain and triggers
        loadEvolutionFragment(pokemon.getEvolutionChain());

        // display any different forms
        // TODO
    }

    private void setBackgroundColourToMatchPrimaryType(String primaryType)
    {
        LinearLayout linearLayout = this.findViewById(R.id.detailsActivityLayout);

        linearLayout.setBackgroundColor(ContextCompat.getColor(this.getApplicationContext(),
                TypesService.getTypeColourKey(primaryType)));
    }

    /**
     *
     * @param pokemon
     */
    private void populateSummaryCard(final Pokemon pokemon)
    {
        // artwork
        ImageView artworkView = this.summaryCardView.findViewById(R.id.detailsPokemonArtworkImageView);
        artworkView.setImageBitmap(pokemon.getArtwork());

        // number
        TextView numberTextView = this.summaryCardView.findViewById(R.id.detailsPokemonNumberTextView);
        String numberText = "#" + pokemon.getNumber() + " ";
        numberTextView.setText(numberText);

        // name
        TextView nameTextView = this.summaryCardView.findViewById(R.id.detailsPokemonNameTextView);
        nameTextView.setText(pokemon.getName());

        // species
        // TODO: NOT RETRIEVED IN THE RESPONSE YET

        // types
        setTypeText(pokemon.getPrimaryType(), true);
        setTypeText(pokemon.getSecondaryType(), false);
    }

    private void setTypeText(String type, boolean isPrimaryType)
    {
        TextView typeText;

        if(isPrimaryType)
        {
            typeText = this.summaryCardView.findViewById(R.id.detailsPokemonPrimaryTypeTextView);
        }
        // not all Pokemon have a secondary type, so if not, just set the text to a dash
        else
        {
            typeText =  this.summaryCardView.findViewById(R.id.detailsPokemonSecondaryTypeTextView);

            if(type == null || type.isEmpty())
            {
                type = "-";
            }
        }

        typeText.setText(type.toUpperCase());

        int colour = ContextCompat.getColor(this.getApplicationContext(), TypesService.getTypeColourKey(type));
        typeText.setBackgroundColor(colour);
    }

    /**
     *
     */
    private void populateInformationCard(final Pokemon pokemon)
    {
        // height
        TextView heightTextView = this.findViewById(R.id.detailsPokemonHeightValueTextView);
        String heightText = pokemon.getHeight() + "m";
        heightTextView.setText(heightText);

        // weight
        TextView weightTextView = this.findViewById(R.id.detailsPokemonWeightValueTextView);
        String weightText = pokemon.getWeight() + "kg";
        weightTextView.setText(weightText);

        // flavour text
        // TODO: Eventually want to make this so you can swipe through and choose which version you want
        TextView flavourTextView = this.findViewById(R.id.detailsPokemonFlavourTextTextView);
        flavourTextView.setText(pokemon.getFlavourText());
    }

    private void setBaseStatsTotal(int baseStatsTotal)
    {
        TextView baseStatTotalTextView = this.findViewById(R.id.detailsBaseStatsTotal);
        String baseStateTotalString = "Total: " + baseStatsTotal;

        baseStatTotalTextView.setText(baseStateTotalString);
    }

    /**
     * Load the relevant fragment to display the specific Pokemon's evolution chain
     */
    private void loadEvolutionFragment(EvolutionChain evolutionChain)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // TODO: Placeholder while getting things working
        boolean isStandardEvolutionChain = true;

        if(isStandardEvolutionChain)
        {
            fragmentTransaction.replace(R.id.detailsEvolutionChainRelativeLayout,
                    new EvolutionChainFragment_3Stage(evolutionChain));
        }

        fragmentTransaction.commit();
    }
}

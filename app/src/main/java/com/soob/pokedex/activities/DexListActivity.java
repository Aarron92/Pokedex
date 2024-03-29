package com.soob.pokedex.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soob.pokedex.DexListSingleton;
import com.soob.pokedex.enums.RegionalDexEnum;
import com.soob.pokedex.inputlisteners.DexClickListener;
import com.soob.pokedex.adapters.PokemonDexListViewAdapter;
import com.soob.pokedex.R;
import com.soob.pokedex.entities.PokemonSummary;
import com.soob.pokedex.inputlisteners.service.DexListService;

import java.util.Objects;

/**
 * Activity class that shows the Dex as a list
 *
 * TODO: WHEN GOING BACK TO THIS PAGE FROM DETAILS VIEW - NEED TO MAINTAIN THE CURRENT PLACE
 */
public class DexListActivity extends AppCompatActivity implements DexClickListener
{
    /**
     * RecyclerView in which the Pokemon data is displayed on the UI
     */
    private RecyclerView recyclerView;

    /**
     * The adapter used to bind the UI and the data
     */
    private PokemonDexListViewAdapter dataAdapter;

    /**
     * The key used to lookup the number field passed to the next activity so it knows which details
     * to look up
     */
    protected final static String POKEMON_NUMBER_KEY = "pokemonNumber";

    /**
     * The key used to lookup the name field passed to the next activity so it knows which details
     * to look up
     */
    protected final static String POKEMON_NAME_KEY = "pokemonName";

    /**
     * When the activity is created this method will be called to do everything needed at the start
     *
     * The Bundle parameter is the saved state of the application (typically non-persistent, dynamic
     * data) it can be passed back to onCreate if the activity needs to be recreated (e.g. if the
     * orientation of the device changes). If no data is supplied then the parameter is null and it
     * is essentially a fresh state
     *
     * @param savedInstanceState state of the application to create the activity with
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dex_list);

        // initialise the UI elements
        this.recyclerView = findViewById(R.id.pokedexListRecyclerView);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // get the specific Dex was passed in from the home screen so we know which Dex to get
        RegionalDexEnum dexToGet = DexListSingleton.getInstance().getRegionalDex();

        // get the relevant Pokedex data
        DexListService.queryForPokedex(this, this.recyclerView);

        this.recyclerView.scrollToPosition(DexListSingleton.getInstance().getScrollPosition());
    }

    /**
     * Implementation for the click listener on the Dex list screen
     *
     * @param pokemonSummary the entry the user clicks on
     */
    @Override
    public void onItemClick(final PokemonSummary pokemonSummary)
    {
        // remember the scroll position for when we come back
        int currentScrollPosition = ((LinearLayoutManager) Objects.requireNonNull(
                this.recyclerView.getLayoutManager())).findFirstCompletelyVisibleItemPosition();
        DexListSingleton.getInstance().setScrollPosition(currentScrollPosition);

        /*
         * An Intent is an object that provides runtime binding between separate components e.g. two
         * activities. The Intent represents the app's intent to do something. The constructor takes
         * two parameters. First a Context which is use to refer to this Activity (which is a subclass
         * of Activity. The second parameter is a Class parameter of the app component to which the
         * system will deliver the Intent, in this case the next Activity to start
         */

        // go to the full details screen of the given Pokemon with the name of the Pokemon that was
        // clicked on as an extra parameter to be able to know which Pokemon's full data needs to be
        // loaded
        Intent goToDexListIntent = new Intent(this, PokemonDetailsActivity.class);
        goToDexListIntent.putExtra(POKEMON_NUMBER_KEY, pokemonSummary.getNumber());
        goToDexListIntent.putExtra(POKEMON_NAME_KEY, pokemonSummary.getName());
        startActivity(goToDexListIntent);
    }
}
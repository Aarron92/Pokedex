package com.soob.pokedex.web.querythreads;

import android.app.Activity;

import androidx.recyclerview.widget.RecyclerView;

import com.soob.pokedex.activities.PokemonDetailsActivity;
import com.soob.pokedex.adapters.PokemonDetailsAbilitiesAdapter;
import com.soob.pokedex.adapters.PokemonDexListViewAdapter;
import com.soob.pokedex.entities.Pokemon;
import com.soob.pokedex.inputlisteners.service.details.PokemonService;

import java.io.IOException;

/**
 * Concrete class that implements ApiQueryThreadCallable to make a query to PokeAPI to get the
 * complete details of a specific Pokemon for the details screen
 *
 * TODO: Need to find out why the main details page call is slower than the dex list one
 *
 * TODO: STILL NEED TO DO THE EXTRA TIDY UP BITS HERE
 */
public class PokemonDetailsQueryThreadCallable extends ApiQueryThreadCallable<Pokemon>
{
    /**
     * The Activity where this thread runner was instantiated from - e.g. PokemonDetailsActivity
     */
    private final Activity activity;

    /**
     * RecyclerView in which the Pokemon data is displayed on the UI
     */
    private final RecyclerView recyclerView;

    /**
     * The adapter used to bind the UI and the data
     */
    private PokemonDetailsAbilitiesAdapter dataAdapter;

    /**
     * Constructor
     */
    public PokemonDetailsQueryThreadCallable(final PokemonDetailsActivity activity,
                                             final RecyclerView recyclerView)
    {
        super(activity);

        this.activity = activity;
        this.recyclerView = recyclerView;
    }

    /**
     * What to do before running the main background task - in this case, nothing
     */
    public void onPreExecute()
    {
        // do nothing
    }

    /**
     * What to do during the main background task when returned data is expected - implemented as
     * needed by calling activity
     */
    @Override
    public Pokemon doInBackground(Pokemon pokemon)
    {
        // get the Pokemon with all of it's details
        PokemonService.getPokemonWithAllDetails(pokemon);

        // create the adapter that will bind the data for the dynamic abilities display
        // TODO: LOOK AT IF THIS IS EVEN NEEDED LIKE THIS
        this.dataAdapter = new PokemonDetailsAbilitiesAdapter(this.activity, pokemon.getAbilities());

        return pokemon;
    }

    /**
     * What to do after running the main background task - in this case, set the data in the
     * abilities part of the UI
     */
    public void onPostExecute()
    {
        // take the adapter and set it on the view so that the data will be bound to the UI
        this.recyclerView.setAdapter(this.dataAdapter);
    }
}
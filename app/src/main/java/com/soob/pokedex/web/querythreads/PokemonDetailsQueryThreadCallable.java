package com.soob.pokedex.web.querythreads;

import android.app.Activity;

import androidx.recyclerview.widget.RecyclerView;

import com.soob.pokedex.activities.PokemonDetailsActivity;
import com.soob.pokedex.adapters.PokemonDetailsAbilitiesAdapter;
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
public class PokemonDetailsQueryThreadCallable extends ApiQueryThreadCallable<Pokemon, String>
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
     *
     * @param activity the activity that this was called from
     */
    public PokemonDetailsQueryThreadCallable(final PokemonDetailsActivity activity, final RecyclerView recyclerView,
                                             final PokemonDetailsAbilitiesAdapter dataAdapter)
    {
        super(activity);

        this.activity = activity;
        this.recyclerView = recyclerView;
        this.dataAdapter = dataAdapter;
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
     *
     * @param parameters pokemon number and name to get the details for
     */
    @Override
    public Pokemon doInBackground(String... parameters) throws IOException
    {
        // TODO: THIS IS DOING ALL OF THE CALLS FOR THE DIFFERENT DETAILS ON THE SAME THREAD,
        //  MIGHT BE BETTER TO SPLIT THEM OUT AND DO THEM ALL SEPARATELY?
        return PokemonService.getPokemonWithAllDetails(parameters);
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
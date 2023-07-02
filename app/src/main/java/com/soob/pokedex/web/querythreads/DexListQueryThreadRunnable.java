package com.soob.pokedex.web.querythreads;

import androidx.recyclerview.widget.RecyclerView;

import com.soob.pokedex.activities.DexListActivity;
import com.soob.pokedex.adapters.PokemonDexListViewAdapter;
import com.soob.pokedex.entities.PokemonSummary;
import com.soob.pokedex.inputlisteners.service.DexListService;

import java.util.List;

/**
 * Concrete class that implements ApiQueryThreadRunnable to make a query to PokeAPI to get the
 * complete list of Pokemon for the Dex screen - currently will only work with DexListActivity
 */
public class DexListQueryThreadRunnable extends ApiQueryThreadRunnable
{
    /**
     * The Activity where this thread runner was instantiated from - e.g. DexListActivity
     */
    private final DexListActivity activity;

    /**
     * RecyclerView in which the Pokemon data is displayed on the UI
     */
    private final RecyclerView recyclerView;

    /**
     * The adapter used to bind the UI and the data
     */
    private PokemonDexListViewAdapter dataAdapter;

    /**
     * Constructor
     *
     * @param activity activity that this is being called from
     * @param recyclerView teh recyclerview making up the UI layout
     */
    public DexListQueryThreadRunnable(final DexListActivity activity, final RecyclerView recyclerView)
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
     * What to do during the main background task - get the list of Pokemon for the Dex screen
     */
    public void doInBackground()
    {
        // get a list of summary objects for every Pokemon
        List<PokemonSummary> pokemonSummaryList = DexListService.getPokemonSummaryListForDex();

        // TODO: MOVE THIS?
        // create the adapter that will bind the data and the click listener
        this.dataAdapter = new PokemonDexListViewAdapter(this.activity, pokemonSummaryList, this.activity);
    }

    /**
     * What to do after running the main background task - set the data to the UI
     */
    public void onPostExecute()
    {
        // take the adapter and set it on the view so that the data will be bound to the UI
        this.recyclerView.setAdapter(this.dataAdapter);
    }
}
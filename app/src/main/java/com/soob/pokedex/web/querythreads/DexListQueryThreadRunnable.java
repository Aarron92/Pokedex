package com.soob.pokedex.web.querythreads;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.soob.pokedex.activities.DexListActivity;
import com.soob.pokedex.adapters.PokemonDexListViewAdapter;
import com.soob.pokedex.entities.PokemonSummary;
import com.soob.pokedex.web.pokeapi.PokeApiClient;
import com.soob.pokedex.web.pokeapi.artwork.ArtworkApiClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Concrete class that implements ApiQueryThreadRunnable to make a query to PokeAPI to get the
 * complete list of Pokemon for the Dex screen - currently will only work with DexListActivity
 */
public class DexListQueryThreadRunnable extends ApiQueryThreadRunnable
{
    /**
     * The Activity where this thread runner was instantiated from - e.g. DexListActivity
     */
    private DexListActivity activity;

    /**
     * RecyclerView in which the Pokemon data is displayed on the UI
     */
    private RecyclerView recyclerView;

    /**
     * The adapter used to bind the UI and the data
     */
    private PokemonDexListViewAdapter dataAdapter;

    /**
     * Constructor
     *
     * @param activity activity that this is being called from
     * @param recyclerView teh recyclerview making up the UI layout
     * @param dataAdapter the data adapter that binds data to the UI
     */
    public DexListQueryThreadRunnable(final DexListActivity activity, final RecyclerView recyclerView,
                                      final PokemonDexListViewAdapter dataAdapter)
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
     * What to do during the main background task - get the list of Pokemon for the Dex screen
     */
    public void doInBackground()
    {
        getFullDexList();
    }

    /**
     * What to do after running the main background task - set the data to the UI
     */
    public void onPostExecute()
    {
        // take the adapter and set it on the view so that the data will be bound to the UI
        this.recyclerView.setAdapter(this.dataAdapter);
    }

    /**
     *
     * @return
     */
    private void getFullDexList()
    {
        Call<JsonElement> pokedexQueryCall = PokeApiClient.getInstance().getPokeApi().getPokedexList();

        List<PokemonSummary> pokemonSummaryList = new ArrayList<>();

        try
        {
            Response<JsonElement> pokedexQueryResponse = pokedexQueryCall.execute();

            // make sure the response body is not null before trying to do anything with it
            if(pokedexQueryResponse.body() != null)
            {
                JsonArray jsonResultsArray = ((JsonObject) pokedexQueryResponse.body()).get("results").getAsJsonArray();

                // loop through all of the entries in the JSON and create a PokemonSummary object for each one
                for (int i = 0; i < jsonResultsArray.size(); i++)
                {
                    int dexNum = i + 1;

                    PokemonSummary pokemonSummary = new PokemonSummary();
                    // pokemonSummary.setArtwork(artwork);
                    pokemonSummary.setNumber("#" + dexNum);
                    pokemonSummary.setName(jsonResultsArray.get(i).getAsJsonObject().get("name").getAsString().toUpperCase());
                    pokemonSummaryList.add(pokemonSummary);
                }
            }
        }
        catch(IOException exc)
        {
            Toast.makeText(this.activity.getApplicationContext(),
                    "Uh oh, something went wrong when trying to get data from the web!",
                    Toast.LENGTH_LONG).show();
        }

        // create the adapter that will bind the data and the click listener
        this.dataAdapter =
                new PokemonDexListViewAdapter(this.activity, pokemonSummaryList, this.activity);
    }
}
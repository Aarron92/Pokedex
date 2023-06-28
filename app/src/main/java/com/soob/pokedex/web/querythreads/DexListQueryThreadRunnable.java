package com.soob.pokedex.web.querythreads;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.soob.pokedex.activities.DexListActivity;
import com.soob.pokedex.adapters.PokemonDexListViewAdapter;
import com.soob.pokedex.entities.PokemonSummary;
import com.soob.pokedex.enums.RegionalDexEnum;
import com.soob.pokedex.web.pokeapi.PokeApiClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
     * Enum denoting the specific Pokedex we want to query for (National, Kanto, Johto etc)
     */
    private final RegionalDexEnum dexToGet;

    /**
     * Constructor
     *
     * @param activity activity that this is being called from
     * @param recyclerView teh recyclerview making up the UI layout
     * @param dataAdapter the data adapter that binds data to the UI
     * @param dexToGet the Pokedex to get the data for (National, Kanto, Johto etc)
     */
    public DexListQueryThreadRunnable(final DexListActivity activity, final RecyclerView recyclerView,
                                      final PokemonDexListViewAdapter dataAdapter, RegionalDexEnum dexToGet)
    {
        super(activity);

        this.activity = activity;
        this.recyclerView = recyclerView;
        this.dataAdapter = dataAdapter;
        this.dexToGet = dexToGet;
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
        getPokedexList();
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
     * Queries the relevant Dex list to get all of the Pokemon relevant to a specific regional Dex
     * (or the National one)
     */
    private void getPokedexList()
    {
        Call<JsonElement> pokedexQueryCall = callRelevantDex();

        List<PokemonSummary> pokemonSummaryList = new ArrayList<>();

        try
        {
            Response<JsonElement> pokedexQueryResponse = pokedexQueryCall.execute();

            // make sure the response body is not null before trying to do anything with it
            if(pokedexQueryResponse.body() != null)
            {
                JsonArray jsonResultsArray = ((JsonObject) pokedexQueryResponse.body()).get("results").getAsJsonArray();

                // loop through all of the entries in the JSON and create a PokemonSummary object for each one
                int offset = this.dexToGet.getOffset();
                for (int i = offset; i < (jsonResultsArray.size() + offset); i++)
                {
                    int dexNum = i + 1;

                    PokemonSummary pokemonSummary = new PokemonSummary();
                    // TODO: Artwork is not returned in list query so is not used yet
//                    pokemonSummary.setArtwork(artwork);
                    pokemonSummary.setNumber(dexNum);
                    pokemonSummary.setName(jsonResultsArray.get(i - offset).getAsJsonObject().get("name").getAsString().toUpperCase());
                    pokemonSummaryList.add(pokemonSummary);
                }
            }
        }
        catch(IOException exc)
        {
            // TODO: This needs to be run on the UI thread. otherwise will crash the app
//            Toast.makeText(this.activity.getApplicationContext(),
//                    "Uh oh, something went wrong when trying to get data from the web!",
//                    Toast.LENGTH_LONG).show();
        }

        // create the adapter that will bind the data and the click listener
        this.dataAdapter =
                new PokemonDexListViewAdapter(this.activity, pokemonSummaryList, this.activity);
    }

    private Call<JsonElement> callRelevantDex(){

        Call<JsonElement> pokedexQueryCall;

        switch(this.dexToGet) {
            case NATIONAL:
                pokedexQueryCall = PokeApiClient.getInstance().getPokeApi().getPokedexList_National();
                break;
            case KANTO:
                pokedexQueryCall = PokeApiClient.getInstance().getPokeApi().getPokedexList_Kanto();
                break;
            case JOHTO:
                pokedexQueryCall = PokeApiClient.getInstance().getPokeApi().getPokedexList_Johto();
                break;
            case HOENN:
                pokedexQueryCall = PokeApiClient.getInstance().getPokeApi().getPokedexList_Hoenn();
                break;
            case SINNOH:
                pokedexQueryCall = PokeApiClient.getInstance().getPokeApi().getPokedexList_Sinnoh();
                break;
            case UNOVA:
                pokedexQueryCall = PokeApiClient.getInstance().getPokeApi().getPokedexList_Unova();
                break;
            case KALOS:
                pokedexQueryCall = PokeApiClient.getInstance().getPokeApi().getPokedexList_Kalos();
                break;
            case ALOLA:
                pokedexQueryCall = PokeApiClient.getInstance().getPokeApi().getPokedexList_Alola();
                break;
            case GALAR:
                pokedexQueryCall = PokeApiClient.getInstance().getPokeApi().getPokedexList_Galar();
                break;
            case PALDEA:
                pokedexQueryCall = PokeApiClient.getInstance().getPokeApi().getPokedexList_Paldea();
                break;
            default:
                // TODO: Add exception, but for now just return all if something went wrong
                pokedexQueryCall = PokeApiClient.getInstance().getPokeApi().getPokedexList_National();
        }

        return pokedexQueryCall;
    }
}
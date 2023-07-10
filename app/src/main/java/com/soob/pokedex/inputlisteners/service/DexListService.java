package com.soob.pokedex.inputlisteners.service;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.soob.pokedex.DexListSingleton;
import com.soob.pokedex.activities.DexListActivity;
import com.soob.pokedex.entities.PokemonSummary;
import com.soob.pokedex.web.pokeapi.PokeApiClient;
import com.soob.pokedex.web.querythreads.DexListQueryThreadRunnable;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Service for doing all the bits related to get the Pokedex information
 */
public class DexListService
{

    /**
     * Kick off a new thread to query PokeAPI to get the data for the Pokedex list.
     *
     * This is done on a separate thread from the UI and makes a call back to this service to do all
     * of the work to create the summary objects
     */
    public static void queryForPokedex(DexListActivity activity, RecyclerView recyclerView)
    {
        // query the web API for the list of Pokemon data on a separate thread
        DexListQueryThreadRunnable dexListQueryThread = new DexListQueryThreadRunnable(activity, recyclerView);
        dexListQueryThread.execute();
    }

    /**
     * Queries the relevant Dex list to get all of the Pokemon relevant to a specific regional Dex
     * (or the National one)
     */
    public static List<PokemonSummary> getPokemonSummaryListForDex()
    {
        // query the relevant Pokedex
        Response<JsonElement> pokedexQueryResponse = PokeApiClientService.queryMainPokeApi(callRelevantDex());

        List<PokemonSummary> pokemonSummaryList = new ArrayList<>();

        // from the response, set the details of the Pokemon summary
        setPokemonSummaryDetails(pokemonSummaryList, pokedexQueryResponse);

        return pokemonSummaryList;
    }

    /**
     * After calling PokeAPI to get a dex list, create a summary object for each Pokemon in the dex
     * so that we can display details in the dex list view
     */
    private static void setPokemonSummaryDetails(List<PokemonSummary> pokemonSummaryList, Response<JsonElement> pokedexQueryResponse)
    {
        // make sure the response body is not null before trying to do anything with it
        if(pokedexQueryResponse.body() != null)
        {
            JsonArray jsonResultsArray = ((JsonObject) pokedexQueryResponse.body()).get("results").getAsJsonArray();

            // get the specific Dex was set from the home screen so we know which Dex to get
            int offset = DexListSingleton.getInstance().getRegionalDex().getOffset();

            // loop through all of the entries in the JSON and create a PokemonSummary object for each one
            for (int i = offset; i < (jsonResultsArray.size() + offset); i++)
            {
                int dexNum = i + 1;

                PokemonSummary pokemonSummary = new PokemonSummary();

                // TODO: Artwork is not returned in list query so is not used yet
                // pokemonSummary.setArtwork(artwork);
                pokemonSummary.setNumber(dexNum);
                pokemonSummary.setName(jsonResultsArray.get(i - offset).getAsJsonObject().get("name").getAsString().toUpperCase());
                pokemonSummaryList.add(pokemonSummary);
            }
        }
    }

    /**
     * Make a call to PokeAPI for a specific range of Pokemon, depending on the selected dex
     */
    private static Call<JsonElement> callRelevantDex(){

        Call<JsonElement> pokedexQueryCall;

        switch(DexListSingleton.getInstance().getRegionalDex())
        {
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

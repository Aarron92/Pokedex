package com.soob.pokedex.inputlisteners.service.details;

import android.graphics.Bitmap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.soob.pokedex.entities.evolution.EvolutionChain;
import com.soob.pokedex.entities.evolution.EvolutionChainStage;
import com.soob.pokedex.entities.Pokemon;
import com.soob.pokedex.inputlisteners.service.PokeApiClientService;
import com.soob.pokedex.web.pokeapi.PokeApiClient;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Service for dealing with bit surrounding a Pokemon's evolution chain
 *
 * TODO: As this is getting more complex, need to look into making each part of the activity load
 *  and show a little loading wheel
 */
public class EvolutionChainService
{

    public static void setPokemonEvolutionChain(Pokemon pokemon, JsonObject pokemonSpeciesDetailsJson)
    {
        // query the API for the evolution chain details of the specific Pokemon - the number of
        // the evolution chain is available from the species data
        // TODO: CAN PROBABLY JUST HAVE A GENERAL CALL TO GET THE EVO CHAIN URL INSTEAD OF PULLING THE NUMBER OUT
        String evolutionChainUrl = pokemonSpeciesDetailsJson.get("evolution_chain").getAsJsonObject().get("url").getAsString();
        String evolutionChainNumber = pullNumberOutOfUrl(evolutionChainUrl);

        // get the names of the Pokemon in the evolution chain
        // TODO: Not sure if I actually need the Linked impl anymore, but left in for now
        LinkedHashMap<Integer, String> pokemonInEvoChain = queryForPokemonInTheEvolutionChain(evolutionChainNumber);

        // now create a full object for evo chain that details the name, number and artwork for
        // each Pokemon in the chain
        EvolutionChain evolutionChain = buildFullEvolutionChain(pokemonInEvoChain);

        pokemon.setEvolutionChain(evolutionChain);
    }

    /**
     * Given a URL with the format like "https://pokeapi.co/api/v2/pokemon-species/345/", pull the
     * number out at the end
     *
     * This does some extra checks to try and keep it robust against formatting changes
     */
    private static String pullNumberOutOfUrl(String url) {

        if (url.endsWith("/"))
        {
            url = url.substring(0, url.lastIndexOf("/"));
        }

        return url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * Query PokeAPI for the evolution chain details of a Pokemon
     *
     * The evolution chain number is the number as specified by PokeAPI and doesn't line up with a dex
     * number. This is pulled from the response of the species details query
     *
     * This method builds up a map of the names and dex numbers of the Pokemon in the evolution
     * chain, which can be used later for populating the actual evolution chain data
     */
    public static LinkedHashMap<Integer, String> queryForPokemonInTheEvolutionChain(String evolutionChainNumber)
    {
        LinkedHashMap<Integer, String>  evolutionChain = new LinkedHashMap<>();

        // query the API for the evolution chain details of the specific Pokemon
        Call<JsonElement> evolutionsDetailsCall =
                PokeApiClient.getInstance().getPokeApi().getEvolutionChain(evolutionChainNumber);

        Response<JsonElement> evolutionDetailsResponse = PokeApiClientService.queryMainPokeApi(evolutionsDetailsCall);

        if(evolutionDetailsResponse.body() != null)
        {
            getPokemonInEvolutionChain(evolutionDetailsResponse.body().getAsJsonObject().get("chain"), evolutionChain);
        }

        return evolutionChain;
    }

    /**
     * Recursively go through the evolution chain query response to get the Pokemon in it
     *
     * Do this recursively due to the recursive structure that PokeAPI uses where each step in the
     * evolution contains details of the next step via a nested JSON structure
     */
    private static void getPokemonInEvolutionChain(JsonElement evolutionChainJson, LinkedHashMap<Integer, String>  mapOfEvolutions)
    {
        JsonObject currentJsonLayer = evolutionChainJson.getAsJsonObject();

        JsonObject pokemonJson = currentJsonLayer.getAsJsonObject().get("species").getAsJsonObject();
        String pokemonName = pokemonJson.get("name").getAsString();

        // get the dex number from the species URL
        String speciesUrl = pokemonJson.get("url").getAsString();
        String pokemonNumber = pullNumberOutOfUrl(speciesUrl);

        mapOfEvolutions.put(Integer.valueOf(pokemonNumber), pokemonName);

        JsonArray evolvesToArray = currentJsonLayer.get("evolves_to").getAsJsonArray();

        // standard evolution chains have a single 'evolves to' array because each Pokemon can only
        // evolve into one other Pokemon
        if(evolvesToArray.size() == 1)
        {
            getPokemonInEvolutionChain(evolvesToArray.get(0), mapOfEvolutions);
        }
        // non-standard evolution chains are where Pokemon can evolve into multiple potential
        // different Pokemon or don't follow the standard pattern (e.g Eevee)
        else if(evolvesToArray.size() > 1)
        {
            for(int i = 0; i < evolvesToArray.size(); i++)
            {
                getPokemonInEvolutionChain(evolvesToArray.get(i), mapOfEvolutions);
            }
        }
    }

    /**
     * Given a list of Pokemon names, build the evolution chain object detailing the name, number
     * and artwork for each Pokemon
     *
     * TODO: We already have the details of the Pokemon on the current page so should look to reuse
     *
     * TODO: This will just be proof of concept to get working for the 3-stage evo chain for now
     *
     * TODO: This is going to be making a few calls to PokeApi so worth looking into how to make
     *  each section load individually with a little loading wheel
     */
    private static EvolutionChain buildFullEvolutionChain(LinkedHashMap<Integer, String> pokemonMap)
    {
        LinkedList<EvolutionChainStage> stages = new LinkedList<>();

        // loop through each Pokemon in the provided map
        for(Map.Entry<Integer, String> pokemonEntry : pokemonMap.entrySet())
        {
            // create an evo stage object to add to the chain
            EvolutionChainStage evolutionStage = new EvolutionChainStage();

            // set the Pokemon's dex number
            evolutionStage.setDexNumber(pokemonEntry.getKey());

            // set the Pokemon's name
            String name = pokemonEntry.getValue();
            String nameWithCapital = name.substring(0, 1).toUpperCase() + name.substring(1);
            evolutionStage.setName(nameWithCapital);

            // get and set the Pokemon's artwork by querying the PokeApi for the relevant artwork
            Bitmap artwork = ArtworkService.queryForPokemonArtwork(String.valueOf(pokemonEntry.getKey()));
            evolutionStage.setArtwork(artwork);

            // set the trigger for a evolving to a particular stage

            stages.add(evolutionStage);
        }

        return new EvolutionChain(stages);
    }
}
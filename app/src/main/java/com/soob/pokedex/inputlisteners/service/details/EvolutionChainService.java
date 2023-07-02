package com.soob.pokedex.inputlisteners.service.details;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.soob.pokedex.entities.Pokemon;
import com.soob.pokedex.inputlisteners.service.PokeApiClientService;
import com.soob.pokedex.web.pokeapi.PokeApiClient;

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Service for dealing with bit surrounding a Pokemon's evolution chain
 */
public class EvolutionChainService
{

    public static void setPokemonEvolutionChain(Pokemon pokemon, JsonObject pokemonSpeciesDetailsJson)
    {
        // query the API for the evolution chain details of the specific Pokemon - the number of
        // the evolution chain is available from the species data
        // TODO: CAN PROBABLY JUST HAVE A GENERAL CALL TO GET THE EVO CHAIN INSTEAD OF PULLING THE NUMBER OUT
        // TODO: THIS WILL GO IN THE SPECIES DETAILS SERVICE FOR NOW
        String evolutionChainNumber = getEvolutionChainNumber(pokemonSpeciesDetailsJson);

        LinkedList<String> evolutionChain =
                EvolutionChainService.queryForEvolutionChain(evolutionChainNumber);

        pokemon.setEvolutionChain(evolutionChain);
    }

    /**
     * Pull out the evolution chain number for a specific Pokemon as it is not the same as the dex
     * number - the number of the evolution chain is available from the species data
     */
    public static String getEvolutionChainNumber(JsonObject pokemonDetailsJson)
    {
        String evolutionChainNumber = pokemonDetailsJson.get("evolution_chain")
                .getAsJsonObject().get("url").getAsString();

        evolutionChainNumber = evolutionChainNumber
                .replace("https://pokeapi.co/api/v2/evolution-chain", "")
                .replace("/", "");

        return evolutionChainNumber;
    }

    /**
     * Query PokeAPI for the evolution chain details of a Pokemon
     *
     * The evolution chain number is the number as specified by PokeAPI and doesn't line up with a dex
     * number. This is pulled from the response of the species details query
     */
    public static LinkedList<String> queryForEvolutionChain(String evolutionChainNumber)
    {
        LinkedList<String> evolutionChain = new LinkedList<>();

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
     * Recursively go the evolution chain to get the Pokemon in it
     *
     * Do this recursively due to the recursive structure that PokeAPI uses where by step in the
     * evolution contains details of the next step via a nested JSON structure
     */
    private static LinkedList<String> getPokemonInEvolutionChain(JsonElement evolutionChainJson, LinkedList<String> listOfEvolutions)
    {
        JsonObject currentJsonLayer = evolutionChainJson.getAsJsonObject();

        String pokemon = currentJsonLayer.getAsJsonObject().get("species").getAsJsonObject().get("name").getAsString();
        listOfEvolutions.add(pokemon);

        JsonArray evolvesToArray = currentJsonLayer.get("evolves_to").getAsJsonArray();

        // standard evolution chains have a single 'evolves to' array because each Pokemon can only
        // evolve into one other Pokemon
        if(evolvesToArray.size() == 1)
        {
            getPokemonInEvolutionChain(evolvesToArray.get(0), listOfEvolutions);
        }
        // non-standard evolution chains are where Pokemon can evolve into multiple potential
        // different Pokemon (e.g Eevee)
        else if(evolvesToArray.size() > 1)
        {
            for(int i = 0; i < evolvesToArray.size(); i++)
            {
                getPokemonInEvolutionChain(evolvesToArray.get(i), listOfEvolutions);
            }
        }

        return listOfEvolutions;
    }
}
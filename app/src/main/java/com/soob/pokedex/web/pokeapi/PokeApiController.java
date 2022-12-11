package com.soob.pokedex.web.pokeapi;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Interface representing the PokeAPI and the various endpoints
 */
public interface PokeApiController
{
    /**
     * Base part of the API URL
     */
    String BASE_URL = "https://pokeapi.co/api/v2/";

    /**
     * The endpoint to retrieve all Pokemon in the National Dex - used in the main Dex view page
     *
     * 1154 gets the current total number of Pokemon but the API returns megas and other forms such
     * as dynamax by using numbers past this
     *
     * @return a JSON response with all of the Pokemon in a list format
     */
    @GET("pokemon?limit=1154&offset=0")
    Call<JsonElement> getPokedexList_National();

    /**
     * The endpoint to retrieve all Pokemon in the Kanto Dex
     *
     * Returns only the first 151 Pokemon, i.e the Kanto Dex
     *
     * @return a JSON response with all of the Kanto Pokemon in a list format
     *
     * TODO: Once others are implemented, will need to come back and check megas, other forms etc
     */
    @GET("pokemon?limit=151&offset=0")
    Call<JsonElement> getPokedexList_Kanto();

    /**
     * The endpoint to retrieve all Pokemon in the Kanto Dex
     *
     * Returns only the Pokemon that appear in the Johto specific Dex
     *
     * @return a JSON response with all of the Johto Pokemon in a list format
     *
     * TODO: Once others are implemented, will need to come back and check megas, other forms etc
     */
    @GET("pokemon?limit=100&offset=151")
    Call<JsonElement> getPokedexList_Johto();

    /**
     * The endpoint to retrieve an individual Pokemon's details - used on the details page for an
     * individual Pokemon entry
     *
     * @param name the name of the Pokemon whose details should be retrieved
     * @return a JSON response containing all of the details for the specified Pokemon
     */
    @GET("pokemon/{name}/")
    Call<JsonElement> getSpecificPokemon(@Path(value = "name", encoded = true) String name);

    /**
     * The endpoint to retrieve additional details about a Pokemon species such as flavour text,
     * gender ration, evolution chain etc
     *
     * @param name the name of the Pokemon whose details should be retrieved
     * @return a JSON response containing all of the details for the specified Pokemon
     */
    @GET("pokemon-species/{name}/")
    Call<JsonElement> getSpeciesDetails(@Path(value = "name", encoded = true) String name);
}

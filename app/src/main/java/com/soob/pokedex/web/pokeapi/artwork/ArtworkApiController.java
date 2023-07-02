package com.soob.pokedex.web.pokeapi.artwork;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Interface representing the PokeAPI and the various endpoints
 */
public interface ArtworkApiController
{
    /**
     * Base part of the API URL
     */
    String BASE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/";

    /**
     * The endpoint to retrieve an individual Pokemon's artwork, specified by it's National Dex
     * number e.g. 1 retrieves Bulbasaur, 2 retrieves Ivysaur etc
     *
     * @param id the number of the Pokemon in the National Dex
     * @return a PNG image file of the specified Pokemon's artwork
     */
    @GET("{id}.png")
    Call<ResponseBody> getPokemonArtwork(@Path(value = "id", encoded = true) String id);
}

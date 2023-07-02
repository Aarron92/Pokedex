package com.soob.pokedex.inputlisteners.service.details;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.soob.pokedex.inputlisteners.service.PokeApiClientService;
import com.soob.pokedex.web.pokeapi.artwork.ArtworkApiClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Service for dealing with Pokemon artwork
 */
public class ArtworkService
{
    /**
     * Get the artwork for a specific Pokemon from PokeAPI (specified by it's Pokemon number)
     */
    public static Bitmap queryForPokemonArtwork(final String pokemonNumber)
    {
        // query the API for the artwork
        Call<ResponseBody> artworkQueryCall =
                    ArtworkApiClient.getInstance().getArtworkApi().getPokemonArtwork(pokemonNumber);

        Response<ResponseBody> artworkQueryResponse = PokeApiClientService.queryArtPokeApi(artworkQueryCall);
        Bitmap artworkBmp = null;

        // make sure the response body is not null before trying to do anything with it
        if (artworkQueryResponse.body() != null)
        {
            // convert the image to a Bitmap and set it on the Pokemon object
            artworkBmp = BitmapFactory.decodeStream(artworkQueryResponse.body().byteStream());
        }

        return artworkBmp;
    }
}

package com.soob.pokedex.web.pokeapi.artwork;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class that manages the Retrofit interface to send web requests to the API URLs to get the
 * official artwork for a Pokemon
 */
public class ArtworkApiClient
{
    /**
     * Static instance of the API client
     */
    private static ArtworkApiClient client = null;

    /**
     * The interface that will be used to send the various GET requests to the API
     */
    private ArtworkApiController artworkApiClient;

    private ArtworkApiClient()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(this.artworkApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.artworkApiClient = retrofit.create(ArtworkApiController.class);
    }

    public static synchronized ArtworkApiClient getInstance()
    {
        if (client == null)
        {
            client = new ArtworkApiClient();
        }
        return client;
    }

    public ArtworkApiController getArtworkApi()
    {
        return this.artworkApiClient;
    }
}

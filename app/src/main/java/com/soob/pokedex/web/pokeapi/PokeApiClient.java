package com.soob.pokedex.web.pokeapi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class that manages the Retrofit interface to send web requests to the PokeAPI URLs to get
 * whatever data back that it needs
 */
public class PokeApiClient
{
    /**
     * Static instance of the API client
     */
    private static PokeApiClient client = null;

    /**
     * The interface that will be used to send the various GET requests to the API
     */
    private PokeApiController pokeApiController;

    private PokeApiClient()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(this.pokeApiController.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.pokeApiController = retrofit.create(PokeApiController.class);
    }

    public static synchronized PokeApiClient getInstance()
    {
        if (client == null)
        {
            client = new PokeApiClient();
        }
        return client;
    }

    public PokeApiController getPokeApi()
    {
        return this.pokeApiController;
    }
}

package com.soob.pokedex.inputlisteners.service;

import com.google.gson.JsonElement;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Service for common functionality around calling the PokeAPI service
 */
public class PokeApiClientService
{
    /**
     * Try to make a call to the main PokeAPI and if it fails, catch the exception and show an
     * error message in a toast pop-up
     */
    public static Response<JsonElement> queryMainPokeApi(Call<JsonElement> apiCall)
    {
        Response<JsonElement> response = null;

        try
        {
            response = apiCall.execute();
        }
        catch (IOException exc)
        {
            // TODO: This needs to be run on the UI thread. otherwise will crash the app
//            Toast.makeText(this.activity.getApplicationContext(),
//                    "Uh oh, something went wrong when trying to get data from the web!",
//                    Toast.LENGTH_LONG).show();
        }

        return response;
    }

    /**
     * Try to make a call to the artwork PokeAPI and if it fails, catch the exception and show an
     * error message in a toast pop-up
     */
    public static Response<ResponseBody> queryArtPokeApi(Call<ResponseBody> apiCall)
    {
        Response<ResponseBody> response = null;

        try
        {
            response = apiCall.execute();
        }
        catch (IOException exc)
        {
            // TODO: This needs to be run on the UI thread. otherwise will crash the app
//            Toast.makeText(this.activity.getApplicationContext(),
//                    "Uh oh, something went wrong when trying to get data from the web!",
//                    Toast.LENGTH_LONG).show();
        }

        return response;
    }
}

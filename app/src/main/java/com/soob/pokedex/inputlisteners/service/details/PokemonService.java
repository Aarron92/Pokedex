package com.soob.pokedex.inputlisteners.service.details;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.soob.pokedex.activities.DexListActivity;
import com.soob.pokedex.entities.Pokemon;
import com.soob.pokedex.inputlisteners.service.PokeApiClientService;
import com.soob.pokedex.web.pokeapi.PokeApiClient;
import com.soob.pokedex.web.querythreads.DexListQueryThreadRunnable;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Main service for creating a Pokemon and handling the details shown on the main Pokemon screen.
 * Makes calls out to the other services as needed
 */
public class PokemonService
{
    /**
     * Kick off a new thread to query PokeAPI to get the details data for a specifc Pokemon
     *
     * This is done on a separate thread from the UI and makes a call back to this service to do all
     * of the work to create the actual Pokemon object and set the various details
     */
    public static void queryForPokedex(DexListActivity activity, RecyclerView recyclerView)
    {
        // query the web API for the list of Pokemon data on a separate thread

        // TODO: IMPL OF THIS AND DATA ADAPTER FOR ABILITIES
    }

    /**
     * Get all the details for a specific Pokemon and return a Pokemon object with everything set
     *
     * @param pokemonDetails the number and name of the Pokemon to look up
     * @return a POJO with the details of the Pokemon
     * @throws IOException thrown is something goes wrong making the call to the API
     */
    public static Pokemon getPokemonWithAllDetails(final String... pokemonDetails) throws IOException
    {
        String pokemonNumber = pokemonDetails[0];
        String pokemonName = pokemonDetails[1];

        // create a new Pokemon with name and dex number
        Pokemon pokemon = new Pokemon();
        pokemon.setNumber(pokemonNumber);
        pokemon.setName(pokemonName);

        // query the API for the rest of the Pokemon details
        setPokemonDetails(pokemon);
        setSpeciesDetails(pokemon);

        // set the artwork to be displayed
        pokemon.setArtwork(ArtworkService.queryForPokemonArtwork(pokemonNumber));

        return pokemon;
    }

    public static void setPokemonDetails(Pokemon pokemon)
    {
        // query the API for the details of the specific Pokemon
        Response<JsonElement> specificDetailsResponse = queryForSpecificPokemonDetails(pokemon.getName());

        // make sure the response body is not null before trying to do anything with it
        if (specificDetailsResponse.body() != null)
        {
            // TODO: SHOULD PROBABLY COME UP WITH A MODEL/CLASS THAT THIS CAN BE MAPPED TO AUTOMATICALLY
            JsonObject responseBody = ((JsonObject) specificDetailsResponse.body());

            //type(s)
            TypesService.setPokemonTypes(pokemon, responseBody);

            // height and weight
            HeightAndWeightService.setPokemonHeightAndWeight(pokemon);

            // abilities
            AbilitiesService.setPokemonAbilities(pokemon, responseBody);

//            // TODO: FIGURE OUT WHERE TO MOVE THIS
            // create the adapter that will bind the abilities data
//            this.dataAdapter = new PokemonDetailsAbilitiesAdapter(this.activity, abilitiesMap);

            // set the base stats
            BaseStatsService.setPokemonBaseStats(pokemon, responseBody);
        }
    }

    public static void setSpeciesDetails(Pokemon pokemon)
    {
        // query the API for the additional details of the Pokemon's species (such as flavour text, gender etc)
        Response<JsonElement> speciesDetailsResponse = queryForSpeciesDetails(pokemon.getName());

        // make sure the response body is not null before trying to do anything with it
        if (speciesDetailsResponse.body() != null) {
            // TODO: AS ABOVE, SHOULD PROBABLY COME UP WITH A MODEL/CLASS THAT THIS CAN BE MAPPED TO AUTOMATICALLY
            JsonObject responseBody = ((JsonObject) speciesDetailsResponse.body());

            // flavour text
            FlavourTextService.setPokemonFlavourText(pokemon, responseBody);

            // gender ratio
            GenderService.setPokemonGender(pokemon, responseBody);

            // evolution chain
            EvolutionChainService.setPokemonEvolutionChain(pokemon, responseBody);
        }
    }

    private static Response<JsonElement> queryForSpecificPokemonDetails(String pokemonName)
    {
        Call<JsonElement> specificPokemonCall =
                PokeApiClient.getInstance().getPokeApi().getSpecificPokemon(pokemonName.toLowerCase());

        return PokeApiClientService.queryMainPokeApi(specificPokemonCall);
    }

    private static Response<JsonElement> queryForSpeciesDetails(String pokemonName)
    {
        Call<JsonElement> speciesDetailsCall =
                PokeApiClient.getInstance().getPokeApi().getSpeciesDetails(pokemonName.toLowerCase());

        return PokeApiClientService.queryMainPokeApi(speciesDetailsCall);
    }
}

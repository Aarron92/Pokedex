package com.soob.pokedex.inputlisteners.service.details;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.soob.pokedex.activities.PokemonDetailsActivity;
import com.soob.pokedex.entities.Pokemon;
import com.soob.pokedex.inputlisteners.service.PokeApiClientService;
import com.soob.pokedex.web.pokeapi.PokeApiClient;
import com.soob.pokedex.web.querythreads.PokemonDetailsQueryThreadCallable;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Main service for creating a Pokemon and handling the details shown on the main Pokemon screen.
 * Makes calls out to the other services as needed
 *
 * This service is intended to be called on a different thread from the main thread via
 * PokemonDetialsQueryThreadCallable class
 */
public class PokemonService
{
    /**
     * Kick off a new thread to query PokeAPI to get the details data for a specific Pokemon. The
     * Pokemon with all of it's details set is then returned
     *
     * This is done on a separate thread from the UI and makes a call back to this service to do all
     * of the work to create the actual Pokemon object and set the various details
     */
    public static Pokemon createPokemonInSeparateThread(PokemonDetailsActivity activity,
                                                        RecyclerView abilitiesRecyclerView,
                                                        final String... pokemonDetails) throws Exception
    {
        String pokemonNumber = pokemonDetails[0];
        String pokemonName = pokemonDetails[1];

        // create a new Pokemon with name and dex number
        Pokemon pokemon = new Pokemon();
        pokemon.setNumber(pokemonNumber);
        pokemon.setName(pokemonName);

        // TODO: THIS IS KINDA JANK, SHOULD PROBABLY IMPROVE
        // query the web API for Pokemon data on a separate thread
        PokemonDetailsQueryThreadCallable detailsQueryThreadCallable =
                new PokemonDetailsQueryThreadCallable(activity, abilitiesRecyclerView);
        detailsQueryThreadCallable.execute(pokemon);

        return pokemon;
    }

    /**
     * Given a Pokemon object, get all the details for that specific Pokemon and set the details
     *
     * TODO: THIS WHOLE THREADING STUFF IS QUITE MESSY AND WOULD RUN THE RISK OF THIS BEING CALLED
     * FROM OTHER PLACES WHEN IT SHOULDN'T - NEEDS SORTING OUT
     */
    public static void getPokemonWithAllDetails(Pokemon pokemon)
    {
        // query the API for the rest of the Pokemon details
        setPokemonDetails(pokemon);
        setSpeciesDetails(pokemon);

        // set the artwork to be displayed
        pokemon.setArtwork(ArtworkService.queryForPokemonArtwork(pokemon.getNumber()));
    }

    private static void setPokemonDetails(Pokemon pokemon)
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

            // set the base stats
            BaseStatsService.setPokemonBaseStats(pokemon, responseBody);
        }
    }

    private static void setSpeciesDetails(Pokemon pokemon)
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

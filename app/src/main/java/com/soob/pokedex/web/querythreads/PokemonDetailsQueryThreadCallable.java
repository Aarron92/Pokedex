package com.soob.pokedex.web.querythreads;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.soob.pokedex.activities.PokemonDetailsActivity;
import com.soob.pokedex.adapters.PokemonDetailsAbilitiesAdapter;
import com.soob.pokedex.entities.BaseStats;
import com.soob.pokedex.entities.Pokemon;
import com.soob.pokedex.enums.BaseStatEnum;
import com.soob.pokedex.web.pokeapi.PokeApiClient;
import com.soob.pokedex.web.pokeapi.artwork.ArtworkApiClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Concrete class that implements ApiQueryThreadCallable to make a query to PokeAPI to get the
 * complete details of a specific Pokemon for the details screen
 *
 * TODO: Need to find out why the main details page call is slower than the dex list one
 */
public class PokemonDetailsQueryThreadCallable extends ApiQueryThreadCallable<Pokemon, String>
{
    /**
     * The Activity where this thread runner was instantiated from - e.g. PokemonDetailsActivity
     */
    private Activity activity;

    /**
     * RecyclerView in which the Pokemon data is displayed on the UI
     */
    private RecyclerView recyclerView;

    /**
     * The adapter used to bind the UI and the data
     */
    private PokemonDetailsAbilitiesAdapter dataAdapter;

    /**
     * Constructor
     *
     * @param activity the activity that this was called from
     */
    public PokemonDetailsQueryThreadCallable(final PokemonDetailsActivity activity, final RecyclerView recyclerView,
                                             final PokemonDetailsAbilitiesAdapter dataAdapter)
    {
        super(activity);

        this.activity = activity;
        this.recyclerView = recyclerView;
        this.dataAdapter = dataAdapter;
    }

    /**
     * What to do before running the main background task - in this case, nothing
     */
    public void onPreExecute()
    {
        // do nothing
    }

    /**
     * What to do during the main background task when returned data is expected - implemented as
     * needed by calling activity
     *
     * @param parameters pokemon number and name to get the details for
     */
    @Override
    public Pokemon doInBackground(String... parameters) throws IOException
    {
        return getPokemonDetails(parameters);
    }

    /**
     * What to do after running the main background task - set the data in the abilites part of the UI
     */
    public void onPostExecute()
    {
        // take the adapter and set it on the view so that the data will be bound to the UI
        this.recyclerView.setAdapter(this.dataAdapter);
    }

    /**
     * Query the API for details on a specific Pokemon and return the relevant details on Pokemon POJO
     *
     * @param pokemonDetails the number and name of the Pokemon to look up
     * @return a POJO with the details of the Pokemon
     * @throws IOException thrown is something goes wrong making the call to the API
     */
    private Pokemon getPokemonDetails(final String... pokemonDetails) throws IOException
    {
        String pokemonNumber = pokemonDetails[0];
        String pokemonName = pokemonDetails[1];

        // query the API and create a Pokemon object from the results
        Pokemon pokemon = queryForPokemon(pokemonNumber, pokemonName);

        // set the artwork to be displayed
        pokemon.setArtwork(queryForPokemonArtwork(pokemonNumber));

        return pokemon;
    }

    /**
     *
     * @param pokemonNumber
     * @return
     * @throws IOException
     */
    private Bitmap queryForPokemonArtwork(final String pokemonNumber) throws IOException
    {
        // query the other API for the artwork
        Call<ResponseBody> artworkQueryCall =
                ArtworkApiClient.getInstance().getArtworkApi().getPokemonArtwork(pokemonNumber);
        Response<ResponseBody> artworkQueryResponse = artworkQueryCall.execute();

        Bitmap artworkBmp = null;

        // make sure the response body is not null before trying to do anything with it
        if(artworkQueryResponse.body() != null)
        {
            // convert the image to a Bitmap and set it on the Pokemon object
            artworkBmp = BitmapFactory.decodeStream(artworkQueryResponse.body().byteStream());
        }

        return artworkBmp;
    }

    /**
     *
     * @param pokemonNumber
     * @param pokemonName
     * @return
     * @throws IOException
     */
    private Pokemon queryForPokemon(final String pokemonNumber, final String pokemonName) throws IOException
    {
        String lowercaseName = pokemonName.toLowerCase();

        // TODO: look into improve threading here to do multiple calls together instead of one at time
        // query the API for the details - make sure to use lower case for the Pokemon name as the API is case-sensitive
        Call<JsonElement> specificPokemonCall =
                PokeApiClient.getInstance().getPokeApi().getSpecificPokemon(lowercaseName);
        Response<JsonElement> specificDetailsResponse = specificPokemonCall.execute();

        // query the API for the additional details of the species - such as flavour text, gender etc
        Call<JsonElement> speciesDetailsCall =
                PokeApiClient.getInstance().getPokeApi().getSpeciesDetails(lowercaseName);
        Response<JsonElement> speciesDetailsResponse = speciesDetailsCall.execute();

        Pokemon pokemon = new Pokemon();

        // make sure the response body is not null before trying to do anything with it
        if (specificDetailsResponse.body() != null)
        {
            JsonObject responseBody = ((JsonObject) specificDetailsResponse.body());

            // number
            pokemon.setNumber(Integer.parseInt(responseBody.get("id").getAsString()));

            // name
            pokemon.setName(pokemonName);

            // array of types
            JsonArray typeArray = responseBody.getAsJsonArray("types");

            // primary type
            pokemon.setPrimaryType(typeArray.get(0).getAsJsonObject().get("type")
                    .getAsJsonObject().get("name").getAsString());

            // secondary type - which not all Pokemon have
            if (typeArray.size() > 1)
            {
                pokemon.setSecondaryType(typeArray.get(1).getAsJsonObject().get("type")
                        .getAsJsonObject().get("name").getAsString());
            }

            // height and weight
//            pokemon.setHeight();
//            pokemon.setWeight();

            // abilities - map of the ability and whether it is a hidden ability or not
            Map<String, Boolean> abilitiesMap = getPokemonAbilities(responseBody);
            pokemon.setAbilities(abilitiesMap);

            // create the adapter that will bind the abilities data
            this.dataAdapter =
                    new PokemonDetailsAbilitiesAdapter(this.activity, abilitiesMap);

            // base stats
            pokemon.setBaseStats(getBaseStats(responseBody));
        }
        if(speciesDetailsResponse.body() != null)
        {
            // flavour text
            JsonArray flavourTextEntries =
                    speciesDetailsResponse.body().getAsJsonObject().get("flavor_text_entries").getAsJsonArray();

            String flavourText = "";

            // take the flavour text entries and find the first English one
            // TODO: currently just using Gen 1 entry but will eventually update, can try and make it swipe-able
            for(JsonElement flavourTextEntry : flavourTextEntries)
            {
                if(flavourTextEntry.getAsJsonObject().get("language").getAsJsonObject().get("name").getAsString().equals("en"))
                {
                    flavourText = flavourTextEntry.getAsJsonObject().get("flavor_text").getAsString();
                }
            }

            pokemon.setFlavourText(flavourText.replace("\n", "").replace("\t", ""));

            // gender ratio
            pokemon.setGenderRatio(speciesDetailsResponse.body().getAsJsonObject().get("gender_rate").getAsInt());

            // query the API for the evolution chain details of the specific Pokemon - the number of
            // the evolution chain is available from the species data
            // TODO: CAN PROBABLY JUST HAVE A GENERAL CALL TO GET THE EVO CHAIN INSTEAD OF PULLING THE NUMBER OUT
            String evolutionChainNumber = speciesDetailsResponse.body()
                    .getAsJsonObject().get("evolution_chain")
                    .getAsJsonObject().get("url").getAsString();

            evolutionChainNumber = evolutionChainNumber
                    .replace("https://pokeapi.co/api/v2/evolution-chain", "")
                    .replace("/", "");

            Call<JsonElement> evolutionsDetailsCall =
                    PokeApiClient.getInstance().getPokeApi().getEvolutionChain(evolutionChainNumber);
            Response<JsonElement> evolutionDetailsResponse = evolutionsDetailsCall.execute();

            if(evolutionDetailsResponse.body() != null)
            {
                LinkedList<String> evolutionChain = new LinkedList<>();
                evolutionChain = getPokemonInEvolutionChain(evolutionDetailsResponse.body().getAsJsonObject().get("chain"), evolutionChain);
                System.out.println(evolutionChain);
                // evolution chain
//               pokemon.setEvolutionChain(evolutionDetailsResponse);
            }

        }
        return pokemon;
    }

    @NonNull
    private Map<String, Boolean> getPokemonAbilities(final JsonObject responseBody)
    {
        Map<String, Boolean> abilitiesMap = new HashMap<>();
        JsonArray abilitiesArray = responseBody.getAsJsonArray("abilities");
        for(JsonElement ability : abilitiesArray)
        {
            String abilityName = ability.getAsJsonObject().get("ability").getAsJsonObject().get("name").getAsString();
            boolean isHidden = ability.getAsJsonObject().get("is_hidden").getAsBoolean();
            abilitiesMap.put(abilityName, isHidden);
        }
        return abilitiesMap;
    }

    private BaseStats getBaseStats(JsonObject responseBody)
    {
        Map<String, Integer> baseStatsMap = new HashMap<>();
        JsonArray baseStatsArray = responseBody.getAsJsonArray("stats");
        BaseStats baseStats = new BaseStats();
        for(JsonElement baseStat : baseStatsArray)
        {
            int statValue = baseStat.getAsJsonObject().get("base_stat").getAsInt();
            String statName = baseStat.getAsJsonObject().get("stat").getAsJsonObject().get("name").getAsString();
            baseStatsMap.put(statName, statValue);
        }
        for(Map.Entry<String, Integer> entry : baseStatsMap.entrySet())
        {
            // loop through and set the relevant field based on the name compared to BaseStatEnum
            if(entry.getKey().equals(BaseStatEnum.HP.getKey()))
            {
                baseStats.setHp(entry.getValue());
            }
            else if(entry.getKey().equals(BaseStatEnum.ATTACK.getKey()))
            {
                baseStats.setAttack(entry.getValue());
            }
            if(entry.getKey().equals(BaseStatEnum.DEFENCE.getKey()))
            {
                baseStats.setDefence(entry.getValue());
            }
            else if(entry.getKey().equals(BaseStatEnum.SP_ATTACK.getKey()))
            {
                baseStats.setSpecialAttack(entry.getValue());
            }
            if(entry.getKey().equals(BaseStatEnum.SP_DEFENCE.getKey()))
            {
                baseStats.setSpecialDefence(entry.getValue());
            }
            else if(entry.getKey().equals(BaseStatEnum.SPEED.getKey()))
            {
                baseStats.setSpeed(entry.getValue());
            }
        }

        return baseStats;
    }

    /**
     * Recursively go the evolution chain to get the Pokemon in it
     *
     * Do this recursively due to the recursive structure that PokeAPI uses where by step in the
     * evolution contains details of the next step via a nested JSON structure
     *
     * TODO: THIS WILL ONLY WORK WITH STANDARD EVO CHAINS - POKEMON LIKE EEVEE WON'T WORK YET
     */
    private LinkedList<String> getPokemonInEvolutionChain(JsonElement evolutionChainJson, LinkedList<String> mapOfEvolutions)
    {
        JsonObject currentJsonLayer = evolutionChainJson.getAsJsonObject();

        String pokemon = currentJsonLayer.getAsJsonObject().get("species").getAsJsonObject().get("name").getAsString();
        mapOfEvolutions.add(pokemon);

        JsonArray evolvesToArray = currentJsonLayer.get("evolves_to").getAsJsonArray();
        if(evolvesToArray.size() == 1)
        {
            getPokemonInEvolutionChain(evolvesToArray.get(0), mapOfEvolutions);
        }
        else if(evolvesToArray.size() > 1)
        {
            // TODO: NON-STANDARD CHAINS (LIKE EEVEE) WILL GO IN HERE
        }

        return mapOfEvolutions;
    }
}
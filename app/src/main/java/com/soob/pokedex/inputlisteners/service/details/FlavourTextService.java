package com.soob.pokedex.inputlisteners.service.details;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.soob.pokedex.entities.Pokemon;

/**
 * Service for all the bits around a Pokemon's flavour text
 */
public class FlavourTextService
{
    public static void setPokemonFlavourText(Pokemon pokemon, JsonObject pokemonSpeciesDetailsJson)
    {
        JsonArray flavourTextArray =
                pokemonSpeciesDetailsJson.get("flavor_text_entries").getAsJsonArray();

        String flavourText = "";

        // take the flavour text entries and find the first English one
        // TODO: currently just using Gen 1 entry but will eventually update, can try and make it swipe-able
        for(JsonElement flavourTextEntry : flavourTextArray)
        {
            if(flavourTextEntry.getAsJsonObject().get("language").getAsJsonObject().get("name").getAsString().equals("en"))
            {
                flavourText = flavourTextEntry.getAsJsonObject().get("flavor_text").getAsString();
            }
        }

        pokemon.setFlavourText(flavourText.replace("\n", "").replace("\t", ""));
    }
}

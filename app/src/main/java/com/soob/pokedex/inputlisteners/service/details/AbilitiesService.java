package com.soob.pokedex.inputlisteners.service.details;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.soob.pokedex.entities.Pokemon;

import java.util.HashMap;
import java.util.Map;

/**
 * Service for dealing with stuff related to Pokemon abilities
 */
public class AbilitiesService
{
    // TODO: THESE SERVICES SHOULD PROBABLY JUST PULL OUT THE RELEVANT BIT AND RETURN IT, THEN EVERYTHING CAN BE SET FROM THE MAIN SERVICE
    public static void setPokemonAbilities(Pokemon pokemon, JsonObject pokemonDetailsJson)
    {
        JsonArray abilitiesArray = pokemonDetailsJson.getAsJsonArray("abilities");

        Map<String, Boolean> abilitiesMap = new HashMap<>();

        // go through the abilities and them to a map with the name of the ability and whether it
        // is a hidden ability or not
        for(JsonElement ability : abilitiesArray)
        {
            String abilityName = ability.getAsJsonObject().get("ability").getAsJsonObject().get("name").getAsString();
            boolean isHidden = ability.getAsJsonObject().get("is_hidden").getAsBoolean();

            abilitiesMap.put(abilityName, isHidden);
        }

        pokemon.setAbilities(abilitiesMap);
    }
}

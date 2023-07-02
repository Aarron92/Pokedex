package com.soob.pokedex.inputlisteners.service.details;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.soob.pokedex.entities.Pokemon;

/**
 * Service for dealing with everything related to Pokemon types
 */
public class TypesService
{
    public static void setPokemonTypes(Pokemon pokemon, JsonObject pokemonDetailsJson)
    {
        JsonArray types = pokemonDetailsJson.getAsJsonArray("types");

        // primary type
        pokemon.setPrimaryType(types.get(0).getAsJsonObject().get("type")
                .getAsJsonObject().get("name").getAsString());

        // secondary type - which not all Pokemon have
        if (types.size() > 1)
        {
            pokemon.setSecondaryType(types.get(1).getAsJsonObject().get("type")
                    .getAsJsonObject().get("name").getAsString());
        }
    }
}

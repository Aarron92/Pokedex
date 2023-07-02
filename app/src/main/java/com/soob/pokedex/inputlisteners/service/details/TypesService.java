package com.soob.pokedex.inputlisteners.service.details;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.soob.pokedex.R;
import com.soob.pokedex.entities.Pokemon;
import com.soob.pokedex.enums.TypeColourEnum;

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

    /**
     * Get the colour associated with a given type
     */
    public static int getTypeColourKey(final String type)
    {
        // use the null type as default
        int colourKey = R.color.type_colour_null;

        // loop through the colours and find the relevant one
        for(TypeColourEnum typeColourEnum : TypeColourEnum.values())
        {
            if(type.toLowerCase().equals(typeColourEnum.getKey()))
            {
                colourKey = typeColourEnum.getValue();
            }
        }
        return colourKey;
    }
}

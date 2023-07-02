package com.soob.pokedex.inputlisteners.service.details;

import com.google.gson.JsonObject;
import com.soob.pokedex.entities.Pokemon;

/**
 * Service for doing all the bits related to Pokemon's gender
 */
public class GenderService
{
    public static void setPokemonGender(Pokemon pokemon, JsonObject pokemonSpeciesDetailsJson)
    {
        pokemon.setGenderRatio(pokemonSpeciesDetailsJson.get("gender_rate").getAsInt());
    }

    /**
     * Gender ratio is determined by a value of 1 to 8, with it representing n/8 chance of being
     * female. This will convert the value to a value n/100 chance of being male
     *
     * e.g. a genderRatio of 4 means a 50% chance of being female
     *
     * @return chance of being female as proportion of 100
     */
    public static float getGenderRatioCalculatedFemale(int genderRatio)
    {
        // multiply by 12.5 because 100/8 = 12.5
        return genderRatio * 12.5f;
    }

    /**
     * Gender ratio is determined by a value of 1 to 8, with it representing n/8 chance of being
     * female. This will convert the value to a value n/100 chance of being male
     *
     * e.g. a genderRatio of 4 means a 50% chance of being male
     *
     * @return chance of being male as a proportion of 100
     */
    public static float getGenderRatioCalculatedMale(int genderRatio)
    {
        // multiply by 12.5 because 100/8 = 12.5 and subtract from 100 to inverse from female
        return 100f - (genderRatio * 12.5f);
    }
}

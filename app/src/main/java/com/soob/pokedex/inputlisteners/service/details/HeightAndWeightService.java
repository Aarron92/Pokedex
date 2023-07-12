package com.soob.pokedex.inputlisteners.service.details;

import com.google.gson.JsonObject;
import com.soob.pokedex.entities.Pokemon;

/**
 * Service for dealing with everything related to a Pokemon's height and weight
 */
public class HeightAndWeightService
{
    public static void setPokemonHeightAndWeight(Pokemon pokemon, JsonObject pokemonDetailsJson)
    {
        int heightInDecimetres = pokemonDetailsJson.get("height").getAsInt();
        int weightInHectograms = pokemonDetailsJson.get("weight").getAsInt();

        double heightInMetres = convertHeightToMetres(heightInDecimetres);
        double weightInKg = convertWeightToKg(weightInHectograms);

        pokemon.setHeight(heightInMetres);
        pokemon.setWeight(weightInKg);
    }

    private static double convertHeightToMetres(int heightInDecimetres)
    {
        return (double) heightInDecimetres / 10;
    }

    private static double convertWeightToKg(int weightInHectograms)
    {
        return (double) weightInHectograms / 10;
    }
}
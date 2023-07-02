package com.soob.pokedex.inputlisteners.service.details;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.soob.pokedex.entities.BaseStats;
import com.soob.pokedex.entities.Pokemon;
import com.soob.pokedex.enums.BaseStatEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Service for handling all the stuff related to a Pokemon's base stats
 */
public class BaseStatsService
{
    public static void setPokemonBaseStats(Pokemon pokemon, JsonObject pokemonDetailsJson)
    {
        JsonArray baseStatsArray = pokemonDetailsJson.getAsJsonArray("stats");

        Map<String, Integer> baseStatsMap = new HashMap<>();
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

        pokemon.setBaseStats(baseStats);
    }
}

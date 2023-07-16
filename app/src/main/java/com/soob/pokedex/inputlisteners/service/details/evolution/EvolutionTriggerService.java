package com.soob.pokedex.inputlisteners.service.details.evolution;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.soob.pokedex.NameUtils;
import com.soob.pokedex.entities.evolution.EvolutionTrigger;

/**
 * Class for working with the evolution triggers
 */
public class EvolutionTriggerService
{
    // TODO: NEED TO ADD COVERAGE FOR POKEMON WHOSE EVO METHOD CHANGED IN LATER GENS - e.g MAGNEZONE
    // TODO: PROBABLY NEED TO PUT THESE IN AN ENUM OR UTIL OR SOMETHING
    final static String GENDER_TEXT_FORMAT = "(%s only)";
    final static String HELD_ITEM_TEXT_FORMAT = "while holding %s";
    final static String KNOWN_MOVE_TEXT_FORMAT = "while knowing %s";
    final static String KNOWN_MOVE_TYPE_TEXT_FORMAT = "while knowing a %s Type move";
    final static String LOCATION_TEXT_FORMAT = "in %s";
    final static String AFFECTION_TEXT_FORMAT = "with max Friendship";
    final static String BEAUTY_TEXT_FORMAT = "with max Beauty stat";
    final static String HAPPINESS_TEXT_FORMAT = "with max Happiness stat";
    final static String OVERWORLD_RAIN_TEXT_FORMAT = "with rain in the overworld";
    final static String SPECIES_IN_PARTY_TEXT_FORMAT = "with a %s Pokémon in party";
    final static String TYPE_IN_PARTY_TEXT_FORMAT = "with a %s type Pokémon in party";
    final static String PHYS_STAT_ATK_GRT_TEXT_FORMAT = "with Attack > Defense";
    final static String PHYS_STAT_DEF_GRT_TEXT_FORMAT = "with Defense > Attack";
    final static String PHYS_STAT_ATK_DEF_EQ_TEXT_FORMAT = "with Attack = Defense";
    final static String TIME_DAY_TEXT_FORMAT = "during the day";
    final static String TIME_NIGHT_TEXT_FORMAT = "at night";
    final static String TRADE_WITH_SPECIES_TEXT_FORMAT = "with %s";
    final static String UPSIDE_DOWN_TEXT_FORMAT = "with game system upside down";

    // TODO: Make this a builder?
    public static EvolutionTrigger buildEvolutionTriggerDetails(String pokemonName, JsonObject evolutionDetails) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();

        EvolutionTrigger evolutionTrigger = mapper.readValue(evolutionDetails.toString(), EvolutionTrigger.class);
        evolutionTrigger.setPokemonName(pokemonName);

        return evolutionTrigger;
    }

    public static String prettyPrintTriggerText(EvolutionTrigger evolutionTrigger)
    {
        String pokemonName = evolutionTrigger.getPokemonName();

        String triggerText = "";

        if (evolutionTrigger.getEvolutionTriggerType().equalsIgnoreCase("level-up"))
        {
            // StringBuilder used as there are a lot of variations here
            StringBuilder stringBuilder = new StringBuilder();

            // first add the level up text
            if(evolutionTrigger.getMinimumLevel() != null)
            {
                stringBuilder.append("Lv. ");
                stringBuilder.append(evolutionTrigger.getMinimumLevel());
            }
            else
            {
                stringBuilder.append("Level up ");
            }

            // now build up the rest of the bits as needed
            stringBuilder.append(appendTriggerDetailsString(evolutionTrigger.getHeldItem(), HELD_ITEM_TEXT_FORMAT));
            stringBuilder.append(appendTriggerDetailsString(evolutionTrigger.getKnownMove(), KNOWN_MOVE_TEXT_FORMAT));
            stringBuilder.append(appendTriggerDetailsString(evolutionTrigger.getKnownMoveType(), KNOWN_MOVE_TYPE_TEXT_FORMAT));
            stringBuilder.append(appendTriggerDetailsInteger(evolutionTrigger.getMinimumAffection(), AFFECTION_TEXT_FORMAT));
            stringBuilder.append(appendTriggerDetailsInteger(evolutionTrigger.getMinimumBeauty(), BEAUTY_TEXT_FORMAT));
            stringBuilder.append(appendTriggerDetailsInteger(evolutionTrigger.getMinimumHappiness(), HAPPINESS_TEXT_FORMAT));
            stringBuilder.append(appendTriggerDetailsBoolean(evolutionTrigger.needsOverworldRain(), OVERWORLD_RAIN_TEXT_FORMAT));
            stringBuilder.append(appendTriggerDetailsString(evolutionTrigger.getSpeciesInParty(), SPECIES_IN_PARTY_TEXT_FORMAT));
            stringBuilder.append(appendTriggerDetailsString(evolutionTrigger.getTypeInParty(), TYPE_IN_PARTY_TEXT_FORMAT));

            // TODO: CAN PROBABLY EXTRACT THESE OUT TOO
            if(evolutionTrigger.getRelativePhysicalStats() != null)
            {
                if(evolutionTrigger.getRelativePhysicalStats() == 1)
                {
                    stringBuilder.append(PHYS_STAT_ATK_GRT_TEXT_FORMAT);
                }
                else if(evolutionTrigger.getRelativePhysicalStats() == 0)
                {
                    stringBuilder.append(PHYS_STAT_ATK_DEF_EQ_TEXT_FORMAT);
                }
                else if(evolutionTrigger.getRelativePhysicalStats() == -1)
                {
                    stringBuilder.append(PHYS_STAT_DEF_GRT_TEXT_FORMAT);
                }
            }
            if(evolutionTrigger.getTimeOfDay() != null)
            {
               if(evolutionTrigger.getTimeOfDay().equalsIgnoreCase("day"))
               {
                   stringBuilder.append(TIME_DAY_TEXT_FORMAT);
               }
               else if(evolutionTrigger.getTimeOfDay().equalsIgnoreCase("night"))
               {
                    stringBuilder.append(TIME_NIGHT_TEXT_FORMAT);
               }
            }
            stringBuilder.append(appendTriggerDetailsBoolean(evolutionTrigger.turnUpsideDown(), UPSIDE_DOWN_TEXT_FORMAT));
            stringBuilder.append(appendTriggerDetailsString(evolutionTrigger.getLocation(), LOCATION_TEXT_FORMAT));
            stringBuilder.append(appendTriggerDetailsInteger(evolutionTrigger.getGender(), GENDER_TEXT_FORMAT));

            triggerText = stringBuilder.toString();
        }
        if (evolutionTrigger.getEvolutionTriggerType().equalsIgnoreCase("trade"))
        {
            // StringBuilder used as there are some variations here
            StringBuilder stringBuilder = new StringBuilder();

            // first add the standard trade text
            stringBuilder.append("Trade");

            // now build up the rest of the bits as needed
            stringBuilder.append(appendTriggerDetailsString(evolutionTrigger.getHeldItem(), HELD_ITEM_TEXT_FORMAT));
            stringBuilder.append(appendTriggerDetailsString(evolutionTrigger.getTradeSpecies(), TRADE_WITH_SPECIES_TEXT_FORMAT));

            triggerText = stringBuilder.toString();
        }
        if (evolutionTrigger.getEvolutionTriggerType().equalsIgnoreCase("use-item"))
        {
            triggerText = "Use " + NameUtils.removeHyphensAndCapitalise(evolutionTrigger.getItem());
        }
        // applies only to Nincada -> Shedinja
        if (evolutionTrigger.getEvolutionTriggerType().equalsIgnoreCase("shed"))
        {
            triggerText = "Lv. " + evolutionTrigger.getMinimumLevel()
                    + " with an empty spot in party and Pokéball in bag";
        }
        // applies only to Milcery -> Alcremie
        if (evolutionTrigger.getEvolutionTriggerType().equalsIgnoreCase("spin"))
        {
            triggerText = "Hold a Sweet and spin";
        }
        // applies only Kubfu -> Urshifu (dark)
        if (evolutionTrigger.getEvolutionTriggerType().equalsIgnoreCase("tower-of-darkness"))
        {
            triggerText = "Use Scroll of Darkness";
        }
        // applies only Kubfu -> Urshifu (water)
        if (evolutionTrigger.getEvolutionTriggerType().equalsIgnoreCase("tower-of-water"))
        {
            triggerText = "Use Scroll of Water";
        }
        // applies only to Galarian Farfetch'd -> Sirfetch'd
        if(evolutionTrigger.getEvolutionTriggerType().equalsIgnoreCase("three-critical-hits"))
        {
            triggerText = "Land 3 critical hits in a single battle";
        }
        // applies only to Galarian Yamask -> Runerigus
        if(evolutionTrigger.getEvolutionTriggerType().equalsIgnoreCase("take-damage"))
        {
            triggerText = "Take 49+ damage and travel under the large rock arch in Dusty Bowl";
        }
        // applies to a few different Pokemon with specific circumstances
        if(evolutionTrigger.getEvolutionTriggerType().equalsIgnoreCase("other"))
        {
            if(pokemonName.equalsIgnoreCase("Pawmot") || pokemonName.equalsIgnoreCase("Brambleghast") || pokemonName.equalsIgnoreCase("Rabsca"))
            {
                triggerText = "Walk 1,000 steps in Let's Go! mode and level up";
            }
            if(pokemonName.equalsIgnoreCase("Maushold"))
            {
                triggerText = "Lv. 25";
            }
            if(pokemonName.equalsIgnoreCase("Palafin"))
            {
                triggerText = "Lv. 38 with another player in Union Circle";
            }
            if(pokemonName.equalsIgnoreCase("Annihilape"))
            {
                triggerText = "Use Rage Fist 20 times and level up";
            }
            if(pokemonName.equalsIgnoreCase("Kingambit"))
            {
                triggerText = "Level up after defeating 3 Bisharp holding Leader's Crest";
            }
            if(pokemonName.equalsIgnoreCase("Gholdengo"))
            {
                triggerText = "Collect 999 Gimmighoul Coins and level up";
            }
        }
        return triggerText;
    }

    private static String appendTriggerDetailsString(String evolutionTriggerDetails, String textFormat)
    {
        StringBuilder stringBuilder = new StringBuilder();

        if(evolutionTriggerDetails != null)
        {
            stringBuilder.append(" ");
            if(textFormat.contains("%s"))
            {
                // Upgrade is formatted weird in PokeAPI response so deal with it here
                String itemText = evolutionTriggerDetails.equalsIgnoreCase("up-grade") ? "Upgrade" : evolutionTriggerDetails;
                String formattedDetails = NameUtils.removeHyphensAndCapitalise(itemText);
                stringBuilder.append(String.format(textFormat, formattedDetails));
            }
            else
            {
                stringBuilder.append(textFormat);
            }
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }

    private static String appendTriggerDetailsBoolean(Boolean evolutionTriggerDetails, String textFormat)
    {
        StringBuilder stringBuilder = new StringBuilder();

        if(evolutionTriggerDetails)
        {
            stringBuilder.append(" ");
            stringBuilder.append(textFormat);
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }

    private static String appendTriggerDetailsInteger(Integer evolutionTriggerDetails, String textFormat)
    {
        StringBuilder stringBuilder = new StringBuilder();

        if(evolutionTriggerDetails != null)
        {
            stringBuilder.append(" ");
            stringBuilder.append(textFormat);
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }
}

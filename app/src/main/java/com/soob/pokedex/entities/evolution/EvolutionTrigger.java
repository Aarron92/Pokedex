package com.soob.pokedex.entities.evolution;

import com.soob.pokedex.enums.EvolutionTriggerEnum;

/**
 * Class for handling the different triggers for a Pokemon's evolution
 *
 * TODO: Building this as I go so for now just works with levelling then will add more as I go
 */
public class EvolutionTrigger
{
    EvolutionTriggerEnum evolutionTriggerType;

    int minimumLevel;

//    gender:null
//    held_item:null
//    item:null
//    known_move:null
//    known_move_type:null
//    location:null
//    min_affection:null
//    min_beauty:null
//    min_happiness:null
//    min_level:16
//    needs_overworld_rain:false
//    party_species:null
//    party_type:null
//    relative_physical_stats:null
//    time_of_day:""
//    trade_species:null


    public EvolutionTrigger()
    {
        // empty constructor
    }

    public EvolutionTriggerEnum getEvolutionTriggerType()
    {
        return evolutionTriggerType;
    }

    public void setEvolutionTriggerType(EvolutionTriggerEnum evolutionTriggerType)
    {
        this.evolutionTriggerType = evolutionTriggerType;
    }

    public int getMinimumLevel()
    {
        return minimumLevel;
    }

    public void setMinimumLevel(int minimumLevel)
    {
        this.minimumLevel = minimumLevel;
    }
}
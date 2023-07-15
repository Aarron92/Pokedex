package com.soob.pokedex.entities.evolution;

import android.graphics.Bitmap;

/**
 * Class holding details of a particular stage in a Pokemon's evolution chain.
 * Means that we can just pass just the details needed to display the evolution chain without the
 * need for a full Pokemon object
 *
 * TODO: The class PokemonSummary already does all of this, this might actually be redundant
 */
public class EvolutionChainStage
{
    /**
     * The dex number of a particular stage
     */
    int dexNumber;

    /**
     * The name of the particular Pokemon stage
     */
    String name;

    /**
     * The artwork of the particular Pokemon stage
     */
    Bitmap artwork;

    /**
     * The trigger the particular stage (e.g. level up, trade etc). The trigger indicates what is
     * required to evolve INTO a particular stage, not what is required for a Pokemon to begin
     * evolving
     *
     * i.e. the trigger for the Spearow line will be null for the Spearow stage but level 20 for Fearow
     */
    EvolutionTrigger trigger;

    public EvolutionChainStage()
    {
        // empty constructor
    }

    public EvolutionChainStage(int dexNumber, String name, Bitmap artwork, EvolutionTrigger trigger)
    {
        this.dexNumber = dexNumber;
        this.name = name;
        this.artwork = artwork;
        this.trigger = trigger;
    }

    public int getDexNumber()
    {
        return dexNumber;
    }

    public void setDexNumber(int dexNumber)
    {
        this.dexNumber = dexNumber;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Bitmap getArtwork()
    {
        return artwork;
    }

    public void setArtwork(Bitmap artwork)
    {
        this.artwork = artwork;
    }

    public EvolutionTrigger getTrigger()
    {
        return trigger;
    }

    public void setTrigger(EvolutionTrigger trigger)
    {
        this.trigger = trigger;
    }
}

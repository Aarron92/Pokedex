package com.soob.pokedex.entities;

import android.graphics.Bitmap;

/**
 * Entity class for a Pokemon Summary, an object used to represent the basic attributes of a Pokemon,
 * that are displayed in the main Dex list view
 *
 * TODO: Whole class could maybe be removed and just use the Pokemon one if possible as its not
 *   using different data
 */
public class PokemonSummary
{
    /**
     * The artwork of the Pokemon, stored as a Bitmap
     */
    private Bitmap artwork;

    /**
     * The number of the Pokemon e.g. #1
     */
    private int number;

    /**
     * The name of the Pokemon e.g. Bulbasaur
     */
    private String name;

    /**
     * Empty Constructor
     */
    public PokemonSummary()
    {}

    public Bitmap getArtwork()
    {
        return this.artwork;
    }

    public void setArtwork(final Bitmap artwork)
    {
        this.artwork = artwork;
    }

    public int getNumber()
    {
        return this.number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}

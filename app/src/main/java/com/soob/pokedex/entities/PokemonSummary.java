package com.soob.pokedex.entities;

import android.graphics.Bitmap;

/**
 * Entity class for a Pokemon Summary, an object used to represent the basic attributes of a Pokemon,
 * that are displayed in the main Dex list view
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
    private String number;

    /**
     * The name of the Pokemon e.g. Bulbasaur
     */
    private String name;

    /**
     * Empty Constructor
     */
    public PokemonSummary()
    {}

    public PokemonSummary(final Bitmap artwork, final String number, final String name)
    {
        this.artwork = artwork;
        this.number = number;
        this.name = name;
    }

    public Bitmap getArtwork()
    {
        return this.artwork;
    }

    public void setArtwork(final Bitmap artwork)
    {
        this.artwork = artwork;
    }

    public String getNumber()
    {
        return this.number;
    }

    public void setNumber(String number)
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

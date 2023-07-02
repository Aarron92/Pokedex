package com.soob.pokedex.entities;

import android.graphics.Bitmap;

import java.util.LinkedList;
import java.util.Map;

public class Pokemon
{
    // TODO: NOT ALL FIELDS ARE COVERED HERE, JUST SOME FOR NOW
    private Bitmap artwork;
    private String number;
    private String name;
    private String primaryType;
    private String secondaryType;
    private int height;
    private int weight;
    private String flavourText;
    private int genderRatio;
    private Map<String, Boolean> abilities;
    private BaseStats baseStats;
    private int baseStatsTotal;
    private LinkedList<String> evolutionChain;

    public Pokemon()
    {
    }

    public Pokemon(final Bitmap artwork, final String number, final String name,
                   final String primaryType, final String secondaryType,
                   final int height, final int weight,
                   final String flavourText, final int genderRatio,
                   final Map<String, Boolean> abilities, final BaseStats baseStats,
                   final LinkedList<String> evolutionChain)
    {
        this.artwork = artwork;
        this.number = number;
        this.name = name;
        this.primaryType = primaryType;
        this.secondaryType = secondaryType;
        this.height = height;
        this.weight = weight;
        this.flavourText = flavourText;
        this.genderRatio = genderRatio;
        this.abilities = abilities;
        this.baseStats = baseStats;
        this.evolutionChain = evolutionChain;
    }

    public Bitmap getArtwork()
    {
        return this.artwork;
    }

    public void setArtwork(Bitmap artwork)
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

    public String getPrimaryType()
    {
        return this.primaryType;
    }

    public void setPrimaryType(String primaryType)
    {
        this.primaryType = primaryType;
    }

    public String getSecondaryType()
    {
        return this.secondaryType;
    }

    public void setSecondaryType(String secondaryType)
    {
        this.secondaryType = secondaryType;
    }

    public int getHeight()
    {
        return this.height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getWeight()
    {
        return this.weight;
    }

    public void setWeight(int weight)
    {
        this.weight = weight;
    }

    public String getFlavourText()
    {
        return this.flavourText;
    }

    public void setFlavourText(String flavourText)
    {
        this.flavourText = flavourText;
    }

    public Map<String, Boolean> getAbilities()
    {
        return this.abilities;
    }

    public void setAbilities(final Map<String, Boolean> abilities)
    {
        this.abilities = abilities;
    }

    public BaseStats getBaseStats()
    {
        return this.baseStats;
    }

    public void setBaseStats(final BaseStats baseStats)
    {
        this.baseStats = baseStats;
    }

    public LinkedList<String> getEvolutionChain()
    {
        return this.evolutionChain;
    }

    public void setEvolutionChain(final LinkedList<String> evolutionChain)
    {
        this.evolutionChain = evolutionChain;
    }

    public int getBaseStatsTotal()
    {
        return getBaseStats().getTotal();
    }

    public int getGenderRatio()
    {
        return this.genderRatio;
    }

    public void setGenderRatio(final int genderRatio)
    {
        this.genderRatio = genderRatio;
    }
}

package com.soob.pokedex.enums;

import com.soob.pokedex.R;

/**
 * Enum used to look-up type colours from the type name
 */
public enum TypeColourEnum
{
    BUG("bug", R.color.type_colour_bug),
    DARK("dark", R.color.type_colour_dark),
    DRAGON("dragon", R.color.type_colour_dragon),
    ELECTRIC("electric", R.color.type_colour_electric),
    FAIRY("fairy", R.color.type_colour_fairy),
    FIGHTING("fighting", R.color.type_colour_fighting),
    FIRE("fire", R.color.type_colour_fire),
    FLYING("flying", R.color.type_colour_flying),
    GHOST("ghost", R.color.type_colour_ground),
    GRASS("grass", R.color.type_colour_grass),
    GROUND("ground", R.color.type_colour_ground),
    ICE("ice", R.color.type_colour_ice),
    NORMAL("normal", R.color.type_colour_normal),
    POISON("poison", R.color.type_colour_poison),
    PSYCHIC("psychic", R.color.type_colour_psychic),
    ROCK("rock", R.color.type_colour_rock),
    STEEL("steel", R.color.type_colour_steel),
    WATER("water", R.color.type_colour_water),
    NULL("-", R.color.type_colour_null);

    /**
     * Key - name of the type
     */
    private final String key;

    /**
     * Value - the type's colour lookup for the colors.xml reference file
     */
    private final int value;

    TypeColourEnum(final String key, final int value)
    {
        this.key = key;
        this.value = value;
    }

    /**
     * Get the key
     *
     * @return the Enum key
     */
    public String getKey()
    {
        return this.key;
    }

    /**
     * Get the value
     *
     * @return the Enum value
     */
    public int getValue()
    {
        return this.value;
    }
}

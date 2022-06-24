package com.soob.pokedex.enums;

/**
 * Enum for the base stats
 */
public enum BaseStatEnum
{
    HP("hp"),
    ATTACK("attack"),
    DEFENCE("defense"),
    SP_ATTACK("special-attack"),
    SP_DEFENCE("special-defense"),
    SPEED("speed");

    /**
     * Key - name of the base stat
     */
    private final String key;

    BaseStatEnum(final String key)
    {
        this.key = key;
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
}

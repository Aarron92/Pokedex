package com.soob.pokedex.enums;

/**
 * Enum for the evolution triggers
 */
public enum EvolutionTriggerEnum
{
    LEVEL_UP("level-up");

    /**
     * Key - evolution trigger type
     */
    private final String key;

    EvolutionTriggerEnum(final String key)
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

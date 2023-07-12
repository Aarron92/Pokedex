package com.soob.pokedex.enums;

/**
 * Enum for the evolution triggers
 */
public enum EvolutionTriggerEnum
{
    // TODO: WILL LIKELY NEED EXTRA SHIT IN HERE FOR THINGS LIKE LEVEL UP AT NIGHT, TRADE WITH ITEM ETC
    LEVEL_UP("Lv. "),
    TRADE("Trade"),
    USE_ITEM(""),
    SHED(""),
    SPIN(""),
    TOWER_OF_DARKNESS(""),
    TOWER_OF_WATERS(""),
    THREE_CRITICAL_HITS(""),
    TAKE_DAMAGE(""),
    OTHER(""),
    AGILE_STYLE_MOVE(""),
    STRONG_STYLE_MOVE(""),
    RECOIL_DAMAGE("");

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

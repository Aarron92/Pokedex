package com.soob.pokedex.enums;

/**
 * Enum for the different Pokedexes
 */
public enum RegionalDexEnum
{
    NATIONAL(0),
    KANTO(0),
    JOHTO(151),
    HOENN(251),
    SINNOH(386),
    UNOVA(494),
    KALOS(649),
    ALOLA(721),
    GALAR(809),
    PALDEA(904);

    /**
     * Offset used to display the correct number next to Pokemon in other Dex lists e.g. Chikorita
     * is #152 not #1
     */
    private int offset;

    RegionalDexEnum(int offset)
    {
        this.offset = offset;
    }

    public int getOffset(){
        return this.offset;
    }
}

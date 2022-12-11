package com.soob.pokedex.enums;

/**
 * Enum for the different Pokedexes
 */
public enum DexEnum
{
    NATIONAL(0),
    KANTO(0),
    JOHTO(151),
    HOENN(0),
    SINNOH(0),
    UNOVA(0),
    KALOS(0),
    ALOLA(0),
    GALAR(0),
    PALDEA(0);
    // TODO: Other offset not needed yet so not added

    /**
     * Offset used to display the correct number next to Pokemon in other Dex lists e.g. Chikorita
     * is #152 not #1
     */
    private int offset;

    DexEnum(int offset)
    {
        this.offset = offset;
    }

    public int getOffset(){
        return this.offset;
    }
}

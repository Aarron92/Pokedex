package com.soob.pokedex;

import com.soob.pokedex.enums.RegionalDexEnum;

/**
 * Singleton class that is used to store the DexEnum (whichever regional dex is being looked at)
 * and a scroll position. This allows the back button to go back to where the user was when they
 * go from an individual Pokemon details page to the main dex list view
 */
public final class DexListSingleton {

    private static DexListSingleton INSTANCE;

    /**
     * The regional dex being used - starts with a default state of the National Dex
     */
    private RegionalDexEnum regionalDex = RegionalDexEnum.NATIONAL;

    /**
     * The scroll position of the dex list to return to - starts with a default position of 0
     */
    private int scrollPosition = 0;

    private DexListSingleton()
    {
    }

    public static DexListSingleton getInstance()
    {
        if(INSTANCE == null)
        {
            INSTANCE = new DexListSingleton();
        }
        return INSTANCE;
    }

    public RegionalDexEnum getRegionalDex()
    {
        return this.regionalDex;
    }

    public void setRegionalDex(RegionalDexEnum regionalDex)
    {
        this.regionalDex = regionalDex;
    }

    public int getScrollPosition()
    {
        return this.scrollPosition;
    }

    public void setScrollPosition(int scrollPosition)
    {
        this.scrollPosition = scrollPosition;
    }
}

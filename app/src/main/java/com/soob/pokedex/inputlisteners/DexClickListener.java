package com.soob.pokedex.inputlisteners;

import com.soob.pokedex.entities.PokemonSummary;

/**
 * Interface for listening for click events on the entries in the Dex list view
 */
public interface DexClickListener
{
    /**
     * When the user clicks on a PokemonSummary object (presented in a CardView) on the DexList page
     *
     * @param pokemonSummary the entry the user clicks on
     */
    void onItemClick(final PokemonSummary pokemonSummary);
}

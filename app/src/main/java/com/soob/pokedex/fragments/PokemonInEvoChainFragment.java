package com.soob.pokedex.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soob.pokedex.R;
import com.soob.pokedex.entities.evolution.EvolutionChainStage;

/**
 * Basic fragment for showing a simple Pokemon card in the evolution chain fragment
 */
public class PokemonInEvoChainFragment extends Fragment
{
    private final EvolutionChainStage evolutionStagePokemon;

    public PokemonInEvoChainFragment(EvolutionChainStage pokemon)
    {
        this.evolutionStagePokemon = pokemon;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokemon_in_evo_chain, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        populatePokemonDetails();
    }

    private void populatePokemonDetails()
    {
        if(getView() != null)
        {
            // first set the Pokemon image from the artwork that was retrieved in an earlier call
            ImageView artworkImgView = (ImageView) getView().findViewById(R.id.evoFragmentArtwork);
            artworkImgView.setImageBitmap(this.evolutionStagePokemon.getArtwork());

            // now set the Pokemon name
            String nameAndNumber = "#" + this.evolutionStagePokemon.getDexNumber() + "\n" + this.evolutionStagePokemon.getName();
            TextView nameTextView = (TextView) getView().findViewById(R.id.evoFragmentNameText);
            nameTextView.setText(nameAndNumber);
        }
    }
}
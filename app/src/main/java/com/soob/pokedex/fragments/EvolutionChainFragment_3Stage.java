package com.soob.pokedex.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soob.pokedex.R;
import com.soob.pokedex.entities.evolution.EvolutionChain;

/**
 * Fragment for a standard 3 stage evolution chain
 *
 * e.g. Bulbasaur -> Ivysaur -> Venusaur
 *
 * TODO: Might not be the best approach of doing it this way, but working for now
 */
public class EvolutionChainFragment_3Stage extends Fragment
{
    private final EvolutionChain evolutionChain;

    public EvolutionChainFragment_3Stage(EvolutionChain evolutionChain)
    {
        this.evolutionChain = evolutionChain;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_evolution_chain_3_stage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        createEvolutionStageFragment(R.id.fragment_3_stage_stage1, 0);
        createEvolutionStageFragment(R.id.fragment_3_stage_stage2, 1);
        createEvolutionStageFragment(R.id.fragment_3_stage_stage3, 2);
    }

    private void createEvolutionStageFragment(int fragmentContainerId, int evolutionChainStage)
    {
        Fragment childFragment = new PokemonInEvoChainFragment(this.evolutionChain.getStages().get(evolutionChainStage));
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(fragmentContainerId, childFragment).commit();
    }
}
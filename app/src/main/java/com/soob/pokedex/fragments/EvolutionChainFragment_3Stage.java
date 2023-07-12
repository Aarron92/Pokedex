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
 * TODO: Really this should probably be reusable for standard evolution lines of 1, 2, 3 and just
 *  add/remove the extra stages as necessary, but haven't figured out how to do that yet
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
        // stage 1
        createEvolutionStageFragment(R.id.fragment_3_stage_stage1, 0);

        // stage 2 (with trigger)
        createEvolutionTriggerArrowFragment(R.id.fragment_3_stage_arrow1, 1);
        createEvolutionStageFragment(R.id.fragment_3_stage_stage2, 1);

        // stage 3 (with trigger)
        createEvolutionTriggerArrowFragment(R.id.fragment_3_stage_arrow2, 2);
        createEvolutionStageFragment(R.id.fragment_3_stage_stage3, 2);
    }

    private void createEvolutionStageFragment(int fragmentContainerId, int evolutionChainStage)
    {
        Fragment childFragment = new PokemonInEvoChainFragment(this.evolutionChain.getStages().get(evolutionChainStage));
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(fragmentContainerId, childFragment).commit();
    }

    private void createEvolutionTriggerArrowFragment(int fragmentContainerId, int evolutionChainStage)
    {
        Fragment childFragment = new EvolutionArrowFragment(this.evolutionChain.getStages().get(evolutionChainStage).getTrigger());
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(fragmentContainerId, childFragment).commit();
    }
}
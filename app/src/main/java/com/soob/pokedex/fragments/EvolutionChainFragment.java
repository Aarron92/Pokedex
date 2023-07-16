package com.soob.pokedex.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.soob.pokedex.R;
import com.soob.pokedex.entities.evolution.EvolutionChain;
import com.soob.pokedex.enums.EvoArrowImgEnum;

/**
 * Fragment for a standard evolution chain with 1-3 stages
 *
 * TODO: Really this should probably be reusable for standard evolution lines of 1, 2, 3 and just
 *  add/remove the extra stages as necessary, but haven't figured out how to do that yet
 */
public class EvolutionChainFragment extends Fragment
{
    private final EvolutionChain evolutionChain;
    private final int numberOfStages;
    private final boolean isStandardChain;

    public EvolutionChainFragment(EvolutionChain evolutionChain)
    {
        this.evolutionChain = evolutionChain;
        this.numberOfStages = evolutionChain.getStages().size();
        this.isStandardChain = evolutionChain.isStandardChain();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        int fragmentId;

        if(numberOfStages == 4 && !this.isStandardChain)
        {
            fragmentId = R.layout.fragment_evolution_chain_3_stage_with_alt;
        }
        else if(numberOfStages == 3 && isStandardChain)
        {
            fragmentId = R.layout.fragment_evolution_chain_3_stage;
        }
        else if(numberOfStages == 3 && !isStandardChain)
        {
            fragmentId = R.layout.fragment_evolution_chain_2_stage_with_alt;
        }
        else if(numberOfStages == 2)
        {
            fragmentId = R.layout.fragment_evolution_chain_2_stage;
        }
        else
        {
            fragmentId = R.layout.fragment_evolution_chain_1_stage;
        }

        // Inflate the layout for this fragment
        return inflater.inflate(fragmentId, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        // 4 stages refers to chains where there a Pokemon goes through 3 stages, but the last stage is one of several
        if(numberOfStages == 4 && !this.isStandardChain)
        {
            create3StageWithAltEvolutionChain();
        }
        else if(numberOfStages == 3 && isStandardChain)
        {
            create3StageEvolutionChain();
        }
        else if(numberOfStages == 3 && !isStandardChain)
        {
            create2StageWithAltEvolutionChain();
        }
        else if(numberOfStages == 2)
        {
            create2StageEvolutionChain();
        }
        else
        {
            create1StageEvolutionChain();
        }
    }

    private void create3StageWithAltEvolutionChain()
    {
        // stage 1
        createEvolutionStageFragment(R.id.fragment_3_stage_alt_stage1, 0);

        // stage 2 (with trigger)
        createEvolutionTriggerArrowFragment(R.id.fragment_3_stage_alt_arrow1, 1, EvoArrowImgEnum.RIGHT_ARROW);
        createEvolutionStageFragment(R.id.fragment_3_stage_alt_stage2, 1);

        // stage 3 (with trigger)
        createEvolutionTriggerArrowFragment(R.id.fragment_3_stage_alt_arrow2, 2, EvoArrowImgEnum.DIAGONAL_UP_RIGHT_ARROW);
        createEvolutionStageFragment(R.id.fragment_3_stage_alt_stage3, 2);

        // alternative stage 3 (with trigger)
        createEvolutionTriggerArrowFragment(R.id.fragment_3_stage_alt_arrow2_other, 3, EvoArrowImgEnum.DIAGONAL_DOWN_RIGHT_ARROW);
        createEvolutionStageFragment(R.id.fragment_3_stage_alt_stage3_other, 3);
    }

    private void create3StageEvolutionChain()
    {
        // stage 1
        createEvolutionStageFragment(R.id.fragment_3_stage_stage1, 0);

        // stage 2 (with trigger)
        createEvolutionTriggerArrowFragment(R.id.fragment_3_stage_arrow1, 1, EvoArrowImgEnum.RIGHT_ARROW);
        createEvolutionStageFragment(R.id.fragment_3_stage_stage2, 1);

        // stage 3 (with trigger)
        createEvolutionTriggerArrowFragment(R.id.fragment_3_stage_arrow2, 2, EvoArrowImgEnum.RIGHT_ARROW);
        createEvolutionStageFragment(R.id.fragment_3_stage_stage3, 2);
    }

    private void create2StageEvolutionChain()
    {
        // stage 1
        createEvolutionStageFragment(R.id.fragment_2_stage_stage1, 0);

        // stage 2 (with trigger)
        createEvolutionTriggerArrowFragment(R.id.fragment_2_stage_arrow1, 1, EvoArrowImgEnum.RIGHT_ARROW);
        createEvolutionStageFragment(R.id.fragment_2_stage_stage2, 1);
    }

    private void create2StageWithAltEvolutionChain()
    {
        // stage 1
        createEvolutionStageFragment(R.id.fragment_2_stage_alt_stage1, 0);

        // stage 2 (with trigger)
        createEvolutionTriggerArrowFragment(R.id.fragment_2_stage_alt_arrow1, 1, EvoArrowImgEnum.DIAGONAL_UP_RIGHT_ARROW);
        createEvolutionStageFragment(R.id.fragment_2_stage_alt_stage2, 1);

        // alternative stage 2 (with trigger)
        createEvolutionTriggerArrowFragment(R.id.fragment_2_stage_alt_arrow1_other, 2, EvoArrowImgEnum.DIAGONAL_DOWN_RIGHT_ARROW);
        createEvolutionStageFragment(R.id.fragment_2_stage_alt_stage2_other, 2);
    }

    private void create1StageEvolutionChain()
    {
        // stage 1
        createEvolutionStageFragment(R.id.fragment_1_stage_stage1, 0);
    }

    private void createEvolutionStageFragment(int fragmentContainerId, int evolutionChainStage)
    {
        Fragment childFragment = new PokemonInEvoChainFragment(this.evolutionChain.getStages().get(evolutionChainStage));
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(fragmentContainerId, childFragment).commit();
    }

    private void createEvolutionTriggerArrowFragment(int fragmentContainerId, int evolutionChainStage, EvoArrowImgEnum arrowImgEnum)
    {
        Fragment childFragment = new EvolutionArrowFragment(this.evolutionChain.getStages().get(evolutionChainStage).getTrigger(), arrowImgEnum);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(fragmentContainerId, childFragment).commit();
    }
}
package com.soob.pokedex.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.soob.pokedex.R;
import com.soob.pokedex.entities.evolution.EvolutionTrigger;
import com.soob.pokedex.enums.EvolutionTriggerEnum;

/**
 * Fragment for evolution trigger arrow
 */
public class EvolutionArrowFragment extends Fragment
{
    private final EvolutionTrigger evolutionTrigger;

    public EvolutionArrowFragment(EvolutionTrigger evolutionTrigger)
    {
        this.evolutionTrigger = evolutionTrigger;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_evo_chain_arrow, container, false);
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
            // TODO: ARROW IS ALWAYS RIGHT FACING BUT COULD REUSE THIS FOR OTHER DIRECTIONS WHEN NEEDED
            // set the arrow image

            // set the trigger details
            // TODO: LIKE BEFORE, JUST WORKS FOR LEVEL UP FOR NOW
            String triggerText = determineTriggerText();
            TextView nameTextView = (TextView) getView().findViewById(R.id.evoArrowTriggerText);
            nameTextView.setText(triggerText);
        }
    }

    /**
     * Determines the text to how on the evolution trigger arrow based on evolution conditions
     *
     * e.g. Lv. 20, Lv. 36 at night, Use Fire Stone etc
     *
     * TODO: This is probably going to get very messy, need to put this in it's own service and try
     *  to avoid a huge class of if statements
     */
    private String determineTriggerText()
    {
        String evolutionTrigger = this.evolutionTrigger.getEvolutionTriggerType().getKey();
        String triggerText = evolutionTrigger;

        if(evolutionTrigger.equals(EvolutionTriggerEnum.LEVEL_UP.getKey()))
        {
            triggerText = evolutionTrigger + this.evolutionTrigger.getMinimumLevel();
        }
        else if(evolutionTrigger.equals(EvolutionTriggerEnum.TRADE.getKey()))
        {
            triggerText = evolutionTrigger;
        }

        return triggerText;
    }
}

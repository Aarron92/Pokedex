package com.soob.pokedex.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.soob.pokedex.R;
import com.soob.pokedex.entities.evolution.EvolutionTrigger;
import com.soob.pokedex.enums.EvoArrowImgEnum;
import com.soob.pokedex.inputlisteners.service.details.evolution.EvolutionTriggerService;

/**
 * Fragment for evolution trigger arrow
 */
public class EvolutionArrowFragment extends Fragment
{
    private final EvolutionTrigger evolutionTrigger;
    private EvoArrowImgEnum evoArrowImg;

    public EvolutionArrowFragment(EvolutionTrigger evolutionTrigger, EvoArrowImgEnum evoArrowImg)
    {
        this.evolutionTrigger = evolutionTrigger;
        this.evoArrowImg = evoArrowImg;
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
        populateTriggerArrowDetails();
    }

    private void populateTriggerArrowDetails()
    {
        if(getView() != null)
        {
            // set the arrow image
            ImageView imageView = getView().findViewById(R.id.evoArrowArtwork);
            if(evoArrowImg.equals(EvoArrowImgEnum.RIGHT_ARROW))
            {
                imageView.setImageResource(R.drawable.evo_arrow_right);
            }
            else if(evoArrowImg.equals(EvoArrowImgEnum.DIAGONAL_UP_RIGHT_ARROW))
            {
                imageView.setImageResource(R.drawable.evo_arrow_up_right);
            }
            if(evoArrowImg.equals(EvoArrowImgEnum.DIAGONAL_DOWN_RIGHT_ARROW))
            {
                imageView.setImageResource(R.drawable.evo_arrow_down_right);
            }

            // set the trigger details
            String triggerText = EvolutionTriggerService.prettyPrintTriggerText(this.evolutionTrigger);
            TextView nameTextView = (TextView) getView().findViewById(R.id.evoArrowTriggerText);
            nameTextView.setText(triggerText);
        }
    }
}

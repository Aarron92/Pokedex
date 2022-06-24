package com.soob.pokedex.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.soob.pokedex.R;
import com.soob.pokedex.entities.Pokemon;
import com.soob.pokedex.entities.PokemonSummary;
import com.soob.pokedex.inputlisteners.DexClickListener;

import java.util.List;
import java.util.Map;

/**
 * Adapter class for the RecyclerView used for the Abilities part of the Pokemon Details screen
 */
public class PokemonDetailsAbilitiesAdapter extends RecyclerView.Adapter<PokemonDetailsAbilitiesAdapter.PokemonDetailsAbilitiesViewHolder>
{
    /**
     * The Context of the Adpater. Context is essentially global information about an application's
     * current environment. This is need to inflate the layout using the LayoutInflater so that the
     * View can be passed to the ViewHolder class
     */
    private Context context;

    /**
     * Map of the abilities that the Pokemon has
     */
    private Map<String, Boolean> abilitiesMap;

    /**
     *
     * @param context
     * @param abilitiesMap
     */
    public PokemonDetailsAbilitiesAdapter(final Context context,
                                          final Map<String, Boolean> abilitiesMap)
    {
        this.context = context;
        this.abilitiesMap = abilitiesMap;
    }

    /**
     * Will create a ViewHolder instance which is used to hold the UI elements
     *
     * @param parent the parent ViewGroup into which the new View will be added after it is bound to
     *               an adapter position
     * @param viewType the type of the new View
     * @return a new instance of the PokemonDexListViewHolder
     */
    @NonNull
    @Override
    public PokemonDetailsAbilitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // create a LayoutInflater - these are used to instantiate a layout XML file into its
        // corresponding View objects using the Context
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);

        // create a View with the ID of the layout we want to inflate and the View group that will
        // behave as a parent to the view - in this case however, we don't want a parent so use null
        View view = layoutInflater.inflate(R.layout.layout_details_abilities, null);

        // create a ViewHolder instance with the View that contains the list layout and return it
        return new PokemonDetailsAbilitiesViewHolder(view);
    }

    /**
     * Binds data to the ViewHolder
     *
     * @param holder the ViewHolder with the UI elements to bind the data to
     * @param position the position of a given item within the adapter's data set
     */
    @Override
    public void onBindViewHolder(@NonNull PokemonDetailsAbilitiesViewHolder holder, int position)
    {
        // get the data for the abilities
        String ability = this.abilitiesMap.keySet().toArray()[position].toString();
        Boolean isHidden = (Boolean) this.abilitiesMap.values().toArray()[position];

        // bind the data to the UI elements insider the holder
        holder.abilityName.setText(ability);
        holder.isHidden.setText(isHidden.toString());
    }

    /**
     * Return the size of the adapter's data set - i.e. the number of Pokemon in the List
     *
     * @return the size of the data set
     */
    @Override
    public int getItemCount()
    {
        int size = 0;

        if(this.abilitiesMap != null)
        {
          size = this.abilitiesMap.size();
        }

        return size;
    }

    /**
     * Inner class to hold the View. This class creates the UI elements in the list layout to be
     * populated
     */
    static class PokemonDetailsAbilitiesViewHolder extends RecyclerView.ViewHolder
    {
        /**
         * The top level card view
         */
        CardView cardView;

        /**
         * The TextView containing the Ability name
         */
        TextView abilityName;

        /**
         * The TextView containing the Ability info
         */
        TextView isHidden;

        /**
         * Constructor
         *
         * @param view View in the ViewHolder
         */
        public PokemonDetailsAbilitiesViewHolder(View view)
        {
            super(view);

            // the card view containing the various other child views
            this.cardView = view.findViewById(R.id.abilitiesEntryCardView);

            // find the various inner View objects from the view parameter using their IDs
            this.abilityName = view.findViewById(R.id.abilitiesEntryNameTextView);
            this.isHidden = view.findViewById(R.id.abilitiesEntryHiddenTextView);
        }
    }
}

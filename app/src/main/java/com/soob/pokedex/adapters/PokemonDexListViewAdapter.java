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

import com.soob.pokedex.inputlisteners.DexClickListener;
import com.soob.pokedex.R;
import com.soob.pokedex.entities.PokemonSummary;

import java.util.List;

/**
 * Adapter class for the RecyclerView used on the DexListActivity screen
 *
 * RecyclerView's require two things; the Adapter and the ViewHolder. The ViewHolder is what hold's
 * the RecyclerView on-screen and the Adapter binds the data to the actual view so that it can be
 * displayed. The ViewHolder is an inner class within this Adapter class
 */
public class PokemonDexListViewAdapter extends RecyclerView.Adapter<PokemonDexListViewAdapter.PokemonDexListViewHolder>
{
    /**
     * The Context of the Adpater. Context is essentially global information about an application's
     * current environment. This is need to inflate the layout using the LayoutInflater so that the
     * View can be passed to the ViewHolder class
     */
    private Context context;

    /**
     * List of PokemonSummary objects that makes up the data set
     */
    private List<PokemonSummary> pokemonSummaryList;

    /**
     * Listener for the PokemonSummary items
     */
    private DexClickListener dexClickListener;

    /**
     * Constructor
     *
     * @param context the context to use
     * @param pokemonSummaryList the PokemonSummary data set as a List
     */
    public PokemonDexListViewAdapter(final Context context, final List<PokemonSummary> pokemonSummaryList,
                                     final DexClickListener dexClickListener)
    {
        this.context = context;
        this.pokemonSummaryList = pokemonSummaryList;
        this.dexClickListener = dexClickListener;
    }

    /**
     * Will create a ViewHolder instance which is used to hold the UI elements
     *
     * This method is called when RecyclerView needs a new RecyclerView.ViewHolder of the given type
     * to represent an item. The new ViewHolder should be constructed with a new View that can
     * represent the items of the given type
     *
     * The new ViewHolder will be used to display items of the adapter using
     * onBindViewHolder(RecyclerView.ViewHolder, int, List)
     *
     * Since it will be re-used to display different items in the data set, it is a good idea to
     * cache references to sub views of the View to avoid unnecessary View.findViewById(int) calls.
     *
     * @param parent the parent ViewGroup into which the new View will be added after it is bound to
     *               an adapter position
     * @param viewType the type of the new View
     * @return a new instance of the PokemonDexListViewHolder
     */
    @NonNull
    @Override
    public PokemonDexListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // create a LayoutInflater - these are used to instantiate a layout XML file into its
        // corresponding View objects using the Context
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);

        // create a View with the ID of the layout we want to inflate and the View group that will
        // behave as a parent to the view - in this case however, we don't want a parent so use null
        View view = layoutInflater.inflate(R.layout.layout_dex_list, null);

        // create a ViewHolder instance with the View that contains the list layout and return it
        return new PokemonDexListViewHolder(view);
    }

    /**
     * Binds data to the ViewHolder
     *
     * @param holder the ViewHolder with the UI elements to bind the data to
     * @param position the position of a given item within the adapter's data set
     */
    @Override
    public void onBindViewHolder(@NonNull PokemonDexListViewHolder holder, int position)
    {
        // get the data for the Pokemon from the data set list
        PokemonSummary pokemonSummary = this.pokemonSummaryList.get(position);

        // bind the click listener to the cards
        holder.cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dexClickListener.onItemClick(pokemonSummaryList.get(holder.getAdapterPosition()));
            }
        });

        // bind the data to the UI elements insider the holder
//        holder.imageView.setImageBitmap(pokemonSummary.getArtwork()); // TODO: Not returned in query list so not used yet
        holder.numberTextView.setText(context.getResources().getString(R.string.dex_list_number, pokemonSummary.getNumber()));
        holder.nameTextView.setText(pokemonSummary.getName());
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

        if(this.pokemonSummaryList != null)
        {
          size = this.pokemonSummaryList.size();
        }

        return size;
    }

    /**
     * Inner class to hold the View. This class creates the UI elements in the list layout to be
     * populated
     */
    static class PokemonDexListViewHolder extends RecyclerView.ViewHolder
    {
        /**
         * The top level card view
         */
        CardView cardView;

        /**
         * The image of the Pokemon
         */
        ImageView imageView;

        /**
         * The TextView containing the Pokemon's name
         */
        TextView nameTextView;

        /**
         * The TextView containing the Pokemon's number
         */
        TextView numberTextView;

        /**
         * Constructor
         *
         * @param view View in the ViewHolder
         */
        public PokemonDexListViewHolder(View view)
        {
            super(view);

            // the card view containing the various other child views
            this.cardView = view.findViewById(R.id.dexListPokemonEntryCardView);

            // find the various inner View objects from the view parameter using their IDs
            this.imageView = view.findViewById(R.id.dexListPokemonArtworkImageView);
            this.numberTextView = view.findViewById(R.id.dexListPokemonNumberTextView);
            this.nameTextView = view.findViewById(R.id.dexListPokemonNameTextView);
        }
    }
}

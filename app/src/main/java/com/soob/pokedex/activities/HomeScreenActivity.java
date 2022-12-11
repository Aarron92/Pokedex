package com.soob.pokedex.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.soob.pokedex.DexListSingleton;
import com.soob.pokedex.R;
import com.soob.pokedex.enums.RegionalDexEnum;

/**
 * TODO: Probably need to look into using Fragments and not Activities for logic where possible
 *
 * Main activity class that will be the first interface the user sees when the app is launched
 */
public class HomeScreenActivity extends AppCompatActivity
{
    /**
     * When the activity is created this method will be called to do everything needed at the start
     *
     * The Bundle parameter is the saved state of the application (typically non-persistent, dynamic
     * data) it can be passed back to onCreate if the activity needs to be recreated (e.g. if the
     * orientation of the device changes). If no data is supplied then the parameter is null and it
     * is essentially a fresh state
     *
     * @param savedInstanceState state of the application to create the activity with
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // call super class onCreate()
        super.onCreate(savedInstanceState);

        // set the screen to be displayed - in this case the homescreen
        setContentView(R.layout.activity_home_screen);

        // add the click listeners to the button for the different Pokedex buttons
        addClickListenerToDexButton(findViewById(R.id.nationalDexButton), RegionalDexEnum.NATIONAL);
        addClickListenerToDexButton(findViewById(R.id.kantoDexButton), RegionalDexEnum.KANTO);
        addClickListenerToDexButton(findViewById(R.id.johtoDexButton), RegionalDexEnum.JOHTO);
        addClickListenerToDexButton(findViewById(R.id.hoennDexButton), RegionalDexEnum.HOENN);
        addClickListenerToDexButton(findViewById(R.id.sinnohDexButton), RegionalDexEnum.SINNOH);
        addClickListenerToDexButton(findViewById(R.id.unovaDexButton), RegionalDexEnum.UNOVA);
        addClickListenerToDexButton(findViewById(R.id.kalosDexButton), RegionalDexEnum.KALOS);
        addClickListenerToDexButton(findViewById(R.id.alolaDexButton), RegionalDexEnum.ALOLA);
        addClickListenerToDexButton(findViewById(R.id.galarDexButton), RegionalDexEnum.GALAR);
        addClickListenerToDexButton(findViewById(R.id.paldeaDexButton), RegionalDexEnum.PALDEA);
    }

    /**
     * Set the click listener for each of the Dex buttons along with the specified enum
     *
     * @param button - button to add the listener to
     * @param regionalDexEnum - the corresponding enum
     */
    private void addClickListenerToDexButton(Button button, RegionalDexEnum regionalDexEnum){
        // add the click listener to the button for the specified dex
        button.setOnClickListener(view -> openDexList(regionalDexEnum));
    }

    /**
     * When the user presses the button on the home page to open the Dex list
     */
    public void openDexList(RegionalDexEnum regionalDexEnum)
    {
        // rather than use extra data on the intent to keep track of the Dex - use the dedicated
        // singleton class which tracks the chosen Dex and the scroll position of the list
        DexListSingleton.getInstance().setRegionalDex(regionalDexEnum);
        DexListSingleton.getInstance().setScrollPosition(0);

        Intent goToDexListIntent = new Intent(this, DexListActivity.class);
        startActivity(goToDexListIntent);
    }
}
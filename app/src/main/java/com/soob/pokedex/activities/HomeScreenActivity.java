package com.soob.pokedex.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.soob.pokedex.R;
import com.soob.pokedex.enums.RegionalDexEnum;

/**
 * TODO: Probably need to look into using Fragments and not Activities for logic where possible
 *
 * Main activity class that will be the first interface the user sees when the app is launched
 */
public class HomeScreenActivity extends AppCompatActivity
{
    public static final String DEX_NAME_KEY = "DEX_NAME_KEY";

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
    }

    /**
     * Set the click listener for each of the Dex buttons along with the specified enum
     *
     * @param button - button to add the listener to
     * @param regionalDexEnum - the correspodning enum
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
        /*
         * An Intent is an object that provides runtime binding between separate components e.g. two
         * activities. The Intent represents the app's intent to do something. The constructor takes
         * two parameters. First a Context which is use to refer to this Activity (which is a subclass
         * of Activity. The second parameter is a Class parameter of the app component to which the
         * system will deliver the Intent, in this case the next Activity to start
         */
        Intent goToDexListIntent = new Intent(this, DexListActivity.class);
        goToDexListIntent.putExtra(DEX_NAME_KEY, regionalDexEnum);

        // start the new Activity i.e. switch to the other UI component
        startActivity(goToDexListIntent);
    }
}
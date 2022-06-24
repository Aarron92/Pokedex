package com.soob.pokedex.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.soob.pokedex.R;

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

        // add the click listener to the button
        Button letsGoButton = (Button) findViewById(R.id.letsGoButton);
        letsGoButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openDexList(view);
            }
        });
    }

    /**
     * When the user presses the button on the home page to open the Dex list
     */
    public void openDexList(View view)
    {
        /*
         * An Intent is an object that provides runtime binding between separate components e.g. two
         * activities. The Intent represents the app's intent to do something. The constructor takes
         * two parameters. First a Context which is use to refer to this Activity (which is a subclass
         * of Activity. The second parameter is a Class parameter of the app component to which the
         * system will deliver the Intent, in this case the next Activity to start
         */
        Intent goToDexListIntent = new Intent(this, DexListActivity.class);

        // start the new Activity i.e. switch to the other UI component
        startActivity(goToDexListIntent);
    }
}
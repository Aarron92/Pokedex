package com.soob.pokedex.web.querythreads;

import android.app.Activity;

import java.util.concurrent.Callable;

/**
 * Abstract class to make queries to online APIs such as PokeAPI to get data in a background thread
 *
 * Uses Runnable as the data is passed straight to the UI so now need to return anything from the
 * Runnable execution
 */
public abstract class ApiQueryThreadRunnable
{
    /**
     * Activity to be passed to the task
     */
    private Activity activity;

    /**
     * Constructor
     *
     * @param activity activity that this is being called from
     */
    public ApiQueryThreadRunnable(Activity activity)
    {
        this.activity = activity;
    }

    /**
     * Method that kicks off the task
     */
    public void execute()
    {
        startBackground();
    }

    /**
     * Task to run in the background where there is no return on=bject. Uses standard Thread and
     * Runnable implementation since there is nothing to return
     */
    private void startBackground()
    {
        // do anything that is needed before the background task runs - e.g display loading wheel
        onPreExecute();

        new Thread(() ->
        {
            // perform the background task
            doInBackground();

            // update the UI in the post-execute
            this.activity.runOnUiThread(() -> onPostExecute());

        }).start();
    }

    /**
     * What to do before running the main background task - implemented as needed by calling activity
     */
    public abstract void onPreExecute();

    /**
     * What to do during the main background task when no returned data is expected - implemented
     * as needed by calling activity
     */
    public abstract void doInBackground();

    /**
     * What to do after running the main background task - implemented as needed by calling activity
     */
    public abstract void onPostExecute();
}
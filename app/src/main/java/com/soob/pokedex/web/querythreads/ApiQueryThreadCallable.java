package com.soob.pokedex.web.querythreads;

import android.app.Activity;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Abstract class to make queries to online APIs such as PokeAPI to get data in a background thread
 *
 * Uses Callable so that the running thread can return data
 */
public abstract class ApiQueryThreadCallable<R>
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
    public ApiQueryThreadCallable(Activity activity)
    {
        this.activity = activity;
    }

    /**
     * Execute the Callable thread
     *
     * @param parameter generic parameter to pass to the thread
     * @return data returned by the thread
     * @throws Exception thrown is something goes
     */
    public R execute(R parameter) throws Exception
    {
        return startBackground(parameter);
    }

    /**
     * Task to run in the background where there is a return object. Uses standard Callable instead
     * of a Thread implementation so that it can return a value from the concurrently run thread.
     * Also has a parameter to be passed in so that it can be manipulated and returned as needed
     *
     * @param parameters generic varargs parameter to pass to the thread
     * @return generic return type to return whatever is needed
     * @throws Exception thrown is something goes wrong with the Callable call
     */
    private R startBackground(R parameters) throws Exception
    {
        // need to run on the ExecutorService to run the callable on a new thread as you cannot run
        // a network call on the main thread
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // do anything that is needed before the background task runs - e.g display loading wheel
        onPreExecute();

        Callable<R> callable = () ->
        {
            // perform the background task
            R returnObj = doInBackground(parameters);

            // update the UI in the post-execute
            activity.runOnUiThread(() -> onPostExecute());

            return returnObj;
        };

        // submit the Callable to the ExecutorService so that it runs and we can get the result
        Future<R> future = executorService.submit(callable);
        R result = future.get();

        // remember to shut the service down to make sure tasks are run and then resources are freed
        executorService.shutdown();

        return result;
    }

    /**
     * What to do before running the main background task - implemented as needed by calling activity
     */
    public abstract void onPreExecute();

    /**
     * What to do during the main background task when returned data is expected - implemented as
     * needed by calling activity
     *
     * @param parameters varargs parameter to be used in determining return value
     */
    public abstract R doInBackground(R parameters) throws IOException;

    /**
     * What to do after running the main background task - implemented as needed by calling activity
     */
    public abstract void onPostExecute();
}
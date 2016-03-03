package com.example.android.myapplication;

import android.app.Application;

import com.example.android.myapplication.api.ExampleAPI;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.squareup.okhttp.OkHttpClient;

import de.greenrobot.event.EventBus;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class ExampleApp extends Application
{
    public static final String API_BASE_URL = "http://echo.jsontest.com/";

    private static ExampleApp mInstance = null;
    private static ExampleAPI mAPI;
    private static JobManager mJobManager;

    public static ExampleApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        /** RETROFIT */

        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        mAPI = retrofit.create(ExampleAPI.class);

        /** PRIORITY JOB MANAGER */

        Configuration config = new Configuration.Builder(this.getApplicationContext())
                .minConsumerCount(0)        // kill all consumers when no job available
                .maxConsumerCount(3)        // no more than 3 consumers running at the same time
                .loadFactor(3)              // each consumer processes 3 jobs
                .consumerKeepAlive(60)      // wait for 1 minute (time in seconds)
                .build();

        mJobManager = new JobManager(getApplicationContext(), config);

        mInstance = this;
    }

    public static EventBus getEventBus() {
        return EventBus.getDefault();
    }

    public ExampleAPI getAPI() {
        return mAPI;
    }

    public static JobManager getJobManager() {
        return mJobManager;
    }

}

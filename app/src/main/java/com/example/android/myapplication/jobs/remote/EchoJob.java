package com.example.android.myapplication.jobs.remote;

import com.example.android.myapplication.ExampleApp;
import com.example.android.myapplication.api.ExampleAPI;
import com.example.android.myapplication.domain.model.Echo;
import com.example.android.myapplication.jobs.Priority;
import com.example.android.myapplication.jobs.remote.callback.EchoCallback;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import retrofit.Call;

public class EchoJob extends Job
{
    private ExampleAPI mAPI;
    private Call<Echo> mRequest;

    public EchoJob() {
        super(new Params(Priority.MID).requireNetwork());

        mAPI     = ExampleApp.getInstance().getAPI();
        mRequest = mAPI.getEcho();
    }

    @Override
    public void onAdded() {
        // Save to disk or do nothing
    }

    @Override
    public void onRun() throws Throwable {
        Thread.sleep(10000);

        mRequest.enqueue(new EchoCallback());
    }

    @Override
    protected void onCancel() {
        // Job has exceeded retry limit
    }
}

package com.example.android.myapplication.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.myapplication.ExampleApp;
import com.example.android.myapplication.R;
import com.example.android.myapplication.domain.event.EchoEvent;
import com.example.android.myapplication.jobs.remote.EchoJob;
import com.path.android.jobqueue.JobManager;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
{
    private ExampleApp mApp;
    private JobManager mJobManager;

    @Bind(R.id.button_callforecho)
    Button mButtonCallForEcho;

    @Bind(R.id.progress_callforecho)
    ProgressBar mProgressCallForEcho;

    @Bind(R.id.text_echo)
    TextView mTextEcho;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mJobManager = ExampleApp.getJobManager();
        mApp        = ExampleApp.getInstance();

        initViews();
    }

    @Override
    public void onResume() {
        super.onResume();

        // subscribe for events
        if (!ExampleApp.getEventBus().isRegistered(this))
             ExampleApp.getEventBus().register(this);
    }

    @Override
    public void onStop() {
        // unsubscribe for events
        if (ExampleApp.getEventBus().isRegistered(this))
            ExampleApp.getEventBus().unregister(this);

        super.onStop();
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(EchoEvent event){
        mTextEcho.setText("ONE: " + event.echo.one);
        mButtonCallForEcho.setVisibility(View.VISIBLE);
        mProgressCallForEcho.setVisibility(View.GONE);
    }

    private void initViews() {
        mButtonCallForEcho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextEcho.setText("Processing ...");
                mButtonCallForEcho.setVisibility(View.GONE);
                mProgressCallForEcho.setVisibility(View.VISIBLE);

                mJobManager.addJobInBackground(new EchoJob());
            }
        });
    }
}

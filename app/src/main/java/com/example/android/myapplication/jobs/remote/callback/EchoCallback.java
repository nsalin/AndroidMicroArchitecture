package com.example.android.myapplication.jobs.remote.callback;

import com.example.android.myapplication.ExampleApp;
import com.example.android.myapplication.domain.event.EchoEvent;
import com.example.android.myapplication.domain.model.Echo;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.annotation.Annotation;

import retrofit.Callback;
import retrofit.Converter;
import retrofit.Response;
import retrofit.Retrofit;

public class EchoCallback implements Callback<Echo>
{
    @Override
    public void onResponse(Response<Echo> response, Retrofit retrofit) {
        EchoEvent event = new EchoEvent();
        Echo      echo  = null;

        Converter<ResponseBody, Echo> converter = retrofit.responseConverter(Echo.class, new Annotation[0]);

        try {
            echo = response.isSuccess() ? response.body() : converter.convert(response.errorBody());

            event.echo = echo;
            event.http_status = response.code();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        ExampleApp.getEventBus().post(event);
    }

    @Override
    public void onFailure(Throwable t) {

    }
}

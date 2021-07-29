package com.example.untitled3;

import android.app.Application;

import timber.log.Timber;

//import android.support.v7.app.AppCompatActivity;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Log
        Timber.plant(new Timber.DebugTree());
    }
}

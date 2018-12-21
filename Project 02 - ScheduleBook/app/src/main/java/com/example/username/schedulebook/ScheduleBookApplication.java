package com.example.username.schedulebook;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by user.name on 2017/10/22.
 */

public class ScheduleBookApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        
    }
}

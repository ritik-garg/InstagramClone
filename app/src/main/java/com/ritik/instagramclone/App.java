package com.ritik.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("x8ds81xK2pw9abCq03Y7zZcxrRxck2rxNmwegbAM")
                // if defined
                .clientKey("H3Kbuwmy1UiWMVG0VEbBLCF1LTeEtSnphOidu3ta")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}

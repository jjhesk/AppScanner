package com.vcoinapp.vendor;

import android.app.Application;

/**
 * Created by hesk on 11/23/2014.
 */
public class MainThread extends Application {
    /*private static MainThread ourInstance = new MainThread();

    public static MainThread getInstance() {
        return ourInstance;
    }*/
    private static RedemptionClaimManager main;

    public void onCreate() {
        main = new RedemptionClaimManager(getApplicationContext());
    }

    public RedemptionClaimManager getRM() {
        return main;
    }
}

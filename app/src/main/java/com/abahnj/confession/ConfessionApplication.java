package com.abahnj.confession;

import android.app.Application;

import com.github.orangegangsters.lollipin.lib.managers.LockManager;

/**
 * Created by abahnj on 8/11/2016.
 */
public class ConfessionApplication extends Application {
    @SuppressWarnings("unchecked")
    @Override
    public void onCreate() {
        super.onCreate();

        LockManager<ConfessionPinActivity> lockManager = LockManager.getInstance();
        lockManager.enableAppLock(this, ConfessionPinActivity.class);
        lockManager.getAppLock().setLogoId(R.drawable.logo_pd);
        lockManager.getAppLock().setShouldShowForgot(false);
        lockManager.getAppLock().setOnlyBackgroundTimeout(true);
    }
}

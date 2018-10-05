package com.example.principal.codechallenge

import android.app.Activity
import android.app.Application
import com.example.principal.codechallenge.dependencyInjection.AppInjector
import com.squareup.leakcanary.LeakCanary
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class CodeChallengeApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector : DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this)

        AppInjector().init(this)
    }

    override fun activityInjector() : DispatchingAndroidInjector<Activity> {
        return dispatchingActivityInjector
    }
}
package com.jeff.template

import android.app.Activity
import android.app.Application
import android.app.Service
import androidx.fragment.app.Fragment
import dagger.android.*
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import javax.inject.Inject

class MyApplication : Application(), HasActivityInjector, HasServiceInjector,
    HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var dispatchingServiceInjector: DispatchingAndroidInjector<Service>

    @Inject
    lateinit var dispatchingSupportFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate() {
        super.onCreate()

        //initializeCalligraphy()

        if (BuildConfig.DEBUG) {
            initializeTimber()
        }

        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

    override fun serviceInjector(): AndroidInjector<Service> {
        return dispatchingServiceInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingSupportFragmentInjector
    }

    private fun initializeTimber() {
        Timber.plant(Timber.DebugTree())
    }

}
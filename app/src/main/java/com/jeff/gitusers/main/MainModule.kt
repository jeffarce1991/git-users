package com.jeff.template.main

import com.jeff.template.ActivityScope
import com.jeff.template.main.presenter.MainPresenterModule
import com.jeff.template.main.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainPresenterModule::class])
    fun mainActivity(): MainActivity
}
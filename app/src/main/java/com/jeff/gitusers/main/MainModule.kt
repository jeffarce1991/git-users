package com.jeff.gitusers.main

import com.jeff.gitusers.ActivityScope
import com.jeff.gitusers.main.list.presenter.MainPresenterModule
import com.jeff.gitusers.main.list.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainPresenterModule::class])
    fun mainActivity(): MainActivity
}
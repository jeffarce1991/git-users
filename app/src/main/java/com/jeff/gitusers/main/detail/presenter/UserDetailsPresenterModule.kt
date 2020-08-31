package com.jeff.gitusers.main.detail.presenter

import dagger.Binds
import dagger.Module

@Module
abstract class UserDetailsPresenterModule {

    @Binds
    abstract fun bindUserDetailsPresenter(
        defaultUserDetailsPresenter: DefaultUserDetailsPresenter
    ): UserDetailsPresenter
}
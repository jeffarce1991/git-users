package com.jeff.gitusers.webservices.internet

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RxInternetModule {

    @Binds
    @Singleton
    abstract fun bindRxInternet(defaultRxInternet: DefaultRxInternet): RxInternet
}
package com.jeff.gitusers

import android.app.Application
import com.jeff.gitusers.database.DatabaseModule
import com.jeff.gitusers.webservices.internet.RxInternetModule
import com.jeff.gitusers.main.MainModule
import com.jeff.gitusers.supplychain.user.UserUseCaseModule
import com.jeff.gitusers.utilities.UtilityModule
import com.jeff.gitusers.webservices.api.ApiModule
import com.jeff.gitusers.webservices.usecase.WebServiceUseCaseModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = [AndroidSupportInjectionModule::class,
    AndroidSupportInjectionModule::class,
    MainModule::class,
    AppModule::class,
    RxInternetModule::class,
    UtilityModule::class,
    DatabaseModule::class,
    ApiModule::class,
    WebServiceUseCaseModule::class,
    UserUseCaseModule::class])
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(myApplication: MyApplication)
}
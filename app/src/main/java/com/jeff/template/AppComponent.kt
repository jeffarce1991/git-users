package com.jeff.template

import android.app.Application
import com.jeff.template.database.DatabaseModule
import com.jeff.template.webservices.internet.RxInternetModule
import com.jeff.template.main.MainModule
import com.jeff.template.supplychain.photo.PhotoUseCaseModule
import com.jeff.template.utilities.UtilityModule
import com.jeff.template.webservices.api.ApiModule
import com.jeff.template.webservices.usecase.WebServiceUseCaseModule
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
    PhotoUseCaseModule::class])
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
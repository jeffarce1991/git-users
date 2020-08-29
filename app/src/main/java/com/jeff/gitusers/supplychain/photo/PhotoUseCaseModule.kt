package com.jeff.template.supplychain.photo

import dagger.Binds
import dagger.Module

@Module
abstract class PhotoUseCaseModule {

    @Binds
    abstract fun bindPhotoLoader(defaultPhotoLoader: DefaultPhotoLoader): PhotoLoader
}
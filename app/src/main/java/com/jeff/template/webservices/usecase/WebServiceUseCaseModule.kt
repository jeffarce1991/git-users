package com.jeff.template.webservices.usecase

import com.jeff.template.webservices.usecase.loader.DefaultPhotoRemoteLoader
import com.jeff.template.webservices.usecase.loader.PhotoRemoteLoader
import dagger.Binds
import dagger.Module

@Module
interface WebServiceUseCaseModule {

    @Binds
    fun bindPhotoRemoteLoader(
            defaultPhotoRemoteLoader: DefaultPhotoRemoteLoader):
            PhotoRemoteLoader
}
package com.jeff.gitusers.webservices.usecase

import com.jeff.gitusers.webservices.usecase.loader.DefaultPhotoRemoteLoader
import com.jeff.gitusers.webservices.usecase.loader.PhotoRemoteLoader
import dagger.Binds
import dagger.Module

@Module
interface WebServiceUseCaseModule {

    @Binds
    fun bindPhotoRemoteLoader(
            defaultPhotoRemoteLoader: DefaultPhotoRemoteLoader):
            PhotoRemoteLoader
}
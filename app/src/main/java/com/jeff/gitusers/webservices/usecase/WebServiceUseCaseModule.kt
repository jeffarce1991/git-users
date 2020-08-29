package com.jeff.gitusers.webservices.usecase

import com.jeff.gitusers.webservices.usecase.loader.DefaultUserRemoteLoader
import com.jeff.gitusers.webservices.usecase.loader.UserRemoteLoader
import dagger.Binds
import dagger.Module

@Module
interface WebServiceUseCaseModule {

    @Binds
    fun bindPhotoRemoteLoader(
            defaultPhotoRemoteLoader: DefaultUserRemoteLoader):
            UserRemoteLoader
}
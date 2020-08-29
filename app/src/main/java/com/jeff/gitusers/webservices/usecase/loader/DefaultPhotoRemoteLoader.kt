package com.jeff.template.webservices.usecase.loader

import com.jeff.template.webservices.api.ApiFactory
import com.jeff.template.webservices.api.photos.PhotosApi
import com.jeff.template.webservices.dto.PhotoDto
import com.jeff.template.webservices.transformer.ResponseCodeNot200SingleTransformer
import io.reactivex.Single
import javax.inject.Inject

class DefaultPhotoRemoteLoader @Inject
constructor(private val apiFactory: ApiFactory): PhotoRemoteLoader {

    override fun loadAll(): Single<List<PhotoDto>> {
        return apiFactory.create(PhotosApi::class.java)
            .flatMap { it.loadPhotos() }
            .compose(ResponseCodeNot200SingleTransformer())
            .flatMap { response ->
                Single.just(response.body()!!) }
    }
}
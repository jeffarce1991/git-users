package com.jeff.template.webservices.usecase.loader

import com.jeff.template.webservices.dto.PhotoDto
import io.reactivex.Single

interface PhotoRemoteLoader {

    fun loadAll(): Single<List<PhotoDto>>
}
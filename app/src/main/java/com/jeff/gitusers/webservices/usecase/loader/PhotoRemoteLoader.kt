package com.jeff.gitusers.webservices.usecase.loader

import com.jeff.gitusers.webservices.dto.PhotoDto
import io.reactivex.Single

interface PhotoRemoteLoader {

    fun loadAll(): Single<List<PhotoDto>>
}
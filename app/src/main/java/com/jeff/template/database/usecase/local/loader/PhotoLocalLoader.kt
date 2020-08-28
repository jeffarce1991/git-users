package com.jeff.template.database.usecase.local.loader

import com.jeff.template.database.local.Photo
import io.reactivex.Single

interface PhotoLocalLoader {
    fun loadAll(): Single<List<Photo>>
}
package com.jeff.gitusers.database.usecase.local.loader

import com.jeff.gitusers.database.local.Photo
import io.reactivex.Single

interface PhotoLocalLoader {
    fun loadAll(): Single<List<Photo>>
}
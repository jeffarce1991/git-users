package com.jeff.gitusers.supplychain.photo

import com.jeff.gitusers.database.local.Photo
import io.reactivex.Single

interface PhotoLoader {

    fun loadAll(): Single<List<Photo>>

    fun loadAllFromLocal(): Single<List<Photo>>
}
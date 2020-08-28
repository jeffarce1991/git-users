package com.jeff.template.supplychain.photo

import com.jeff.template.database.local.Photo
import io.reactivex.Single

interface PhotoLoader {

    fun loadAll(): Single<List<Photo>>

    fun loadAllFromLocal(): Single<List<Photo>>
}
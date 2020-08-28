package com.jeff.template.database.usecase.local.saver

import com.jeff.template.database.local.Photo
import io.reactivex.Completable
import io.reactivex.Observable

interface PhotoLocalSaver {

    fun save(photo: Photo): Completable

    fun saveAll(photos: List<Photo>): Observable<List<Photo>>
}

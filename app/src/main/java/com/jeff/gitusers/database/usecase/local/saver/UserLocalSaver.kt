package com.jeff.gitusers.database.usecase.local.saver

import com.jeff.gitusers.database.local.Photo
import com.jeff.gitusers.database.local.User
import io.reactivex.Completable
import io.reactivex.Observable

interface UserLocalSaver {

    fun save(user: User): Completable

    fun saveAll(users: List<User>): Observable<List<User>>
}

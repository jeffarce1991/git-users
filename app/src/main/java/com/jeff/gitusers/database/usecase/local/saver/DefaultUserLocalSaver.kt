package com.jeff.gitusers.database.usecase.local.saver

import com.jeff.gitusers.database.local.Photo
import com.jeff.gitusers.database.local.User
import com.jeff.gitusers.database.room.dao.PhotoDao
import com.jeff.gitusers.database.room.dao.UserDao
import io.reactivex.Completable
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class DefaultUserLocalSaver @Inject
constructor(private val dao: UserDao) : UserLocalSaver {

    override fun save(user: User): Completable {
        return Completable.fromAction { dao.insert(user)}
    }

    override fun saveAll(users: List<User>): Observable<List<User>> {
        return Completable.fromCallable {
            dao.insert(users)
            Timber.d("==q saveAll Done")
            Completable.complete()
        }.andThen(Observable.fromCallable { users })
    }

}
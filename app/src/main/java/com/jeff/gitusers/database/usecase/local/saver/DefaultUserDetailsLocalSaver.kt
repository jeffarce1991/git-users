package com.jeff.gitusers.database.usecase.local.saver

import com.jeff.gitusers.database.local.Photo
import com.jeff.gitusers.database.local.User
import com.jeff.gitusers.database.local.UserDetails
import com.jeff.gitusers.database.room.dao.PhotoDao
import com.jeff.gitusers.database.room.dao.UserDao
import com.jeff.gitusers.database.room.dao.UserDetailsDao
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class DefaultUserDetailsLocalSaver @Inject
constructor(private val dao: UserDetailsDao) : UserDetailsLocalSaver {

    override fun save(details: UserDetails): Observable<UserDetails> {
        return Completable.fromCallable {
            dao.insert(details)
            Timber.d("==q saveAll Done")
            Completable.complete()
        }.andThen(Observable.fromCallable { details })
    }

}
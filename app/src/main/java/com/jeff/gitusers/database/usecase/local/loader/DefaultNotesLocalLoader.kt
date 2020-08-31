package com.jeff.gitusers.database.usecase.local.loader

import com.jeff.gitusers.database.local.Notes
import com.jeff.gitusers.database.local.User
import com.jeff.gitusers.database.local.UserDetails
import com.jeff.gitusers.database.room.dao.NotesDao
import com.jeff.gitusers.database.room.dao.UserDao
import io.reactivex.Single
import javax.inject.Inject

class DefaultNotesLocalLoader @Inject
constructor(private val dao: NotesDao): NotesLocalLoader {

    override fun loadAll(): Single<List<Notes>> {
        return Single.fromCallable { dao.loadAll() }
    }
    override fun loadById(id: Int): Single<Notes> {
        return Single.fromCallable { dao.loadById(id) }
    }


}

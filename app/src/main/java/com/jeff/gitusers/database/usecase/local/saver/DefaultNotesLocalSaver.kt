package com.jeff.gitusers.database.usecase.local.saver

import com.jeff.gitusers.database.local.Notes
import com.jeff.gitusers.database.local.User
import com.jeff.gitusers.database.room.dao.NotesDao
import com.jeff.gitusers.database.room.dao.UserDao
import io.reactivex.Completable
import javax.inject.Inject

class DefaultNotesLocalSaver  @Inject
constructor(private val dao: NotesDao) : NotesLocalSaver {

    override fun save(notes: Notes): Completable {
        return Completable.fromAction { dao.insert(notes)}
    }
}

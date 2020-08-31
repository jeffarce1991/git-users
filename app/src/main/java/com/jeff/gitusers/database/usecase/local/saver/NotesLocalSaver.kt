package com.jeff.gitusers.database.usecase.local.saver

import com.jeff.gitusers.database.local.Notes
import com.jeff.gitusers.database.local.User
import io.reactivex.Completable

interface NotesLocalSaver {

    fun save(notes: Notes): Completable

}

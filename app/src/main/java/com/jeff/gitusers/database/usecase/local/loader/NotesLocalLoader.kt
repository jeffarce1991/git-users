package com.jeff.gitusers.database.usecase.local.loader

import com.jeff.gitusers.database.local.Notes
import com.jeff.gitusers.database.local.User
import io.reactivex.Single

interface NotesLocalLoader {
    fun loadById(id: Int): Single<Notes>
    fun loadAll(): Single<List<Notes>>
}

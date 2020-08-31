package com.jeff.gitusers.database.usecase.local.saver

import com.jeff.gitusers.database.local.UserDetails
import io.reactivex.Completable
import io.reactivex.Observable

interface UserDetailsLocalSaver {

    fun save(details: UserDetails): Observable<UserDetails>
}

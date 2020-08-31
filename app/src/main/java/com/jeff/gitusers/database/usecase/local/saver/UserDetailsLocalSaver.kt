package com.jeff.gitusers.database.usecase.local.saver

import com.jeff.gitusers.database.local.Photo
import com.jeff.gitusers.database.local.User
import com.jeff.gitusers.database.local.UserDetails
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface UserDetailsLocalSaver {

    fun save(details: UserDetails): Observable<UserDetails>
}

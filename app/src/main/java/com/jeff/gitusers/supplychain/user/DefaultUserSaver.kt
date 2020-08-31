package com.jeff.gitusers.supplychain.user

import com.jeff.gitusers.database.local.UserDetails
import com.jeff.gitusers.database.usecase.local.loader.UserDetailsLocalLoader
import com.jeff.gitusers.database.usecase.local.loader.UserLocalLoader
import com.jeff.gitusers.database.usecase.local.saver.UserDetailsLocalSaver
import com.jeff.gitusers.database.usecase.local.saver.UserLocalSaver
import com.jeff.gitusers.webservices.internet.RxInternet
import com.jeff.gitusers.webservices.usecase.loader.UserRemoteLoader
import io.reactivex.Completable
import javax.inject.Inject

class DefaultUserSaver  @Inject
constructor(
            private val localDetailsSaver: UserDetailsLocalSaver,
            private val localDetailsLoader: UserDetailsLocalLoader,
            private val rxInternet: RxInternet
): UserSaver {

}

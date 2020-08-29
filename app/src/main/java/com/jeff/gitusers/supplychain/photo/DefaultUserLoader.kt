package com.jeff.gitusers.supplychain.photo

import com.jeff.gitusers.database.local.Photo
import com.jeff.gitusers.database.local.User
import com.jeff.gitusers.database.usecase.local.loader.PhotoLocalLoader
import com.jeff.gitusers.database.usecase.local.saver.PhotoLocalSaver
import com.jeff.gitusers.main.mapper.UserDtoToUserMapper
import com.jeff.gitusers.webservices.internet.RxInternet
import com.jeff.gitusers.webservices.usecase.loader.UserRemoteLoader
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class DefaultUserLoader @Inject
constructor(private val remoteLoader: UserRemoteLoader,
            private val localLoader: PhotoLocalLoader,
            private val localSaver: PhotoLocalSaver,
            private val rxInternet: RxInternet): UserLoader{

    override fun loadInitialUsers(): Single<List<User>> {
        return rxInternet.isConnected()
            .andThen(remoteLoader.loadInitialUsers())
            .flatMapObservable { list -> Observable.fromIterable(list) }
            .flatMap(UserDtoToUserMapper())
            .toList()
            //.flatMap { photos -> Single.fromObservable(localSaver.saveAll(photos)) }
            .flatMap { photos -> Single.just(photos) }
    }

    override fun loadMoreUsers(id: Int): Single<List<User>> {
        return rxInternet.isConnected()
            .andThen(remoteLoader.loadMoreUsers(id))
            .flatMapObservable { list -> Observable.fromIterable(list) }
            .flatMap(UserDtoToUserMapper())
            .toList()
            //.flatMap { photos -> Single.fromObservable(localSaver.saveAll(photos)) }
            .flatMap { photos -> Single.just(photos) }
    }

    override fun loadInitialUsersLocally(): Single<List<User>> {
        return Single.just(listOf(User(), User()))
        /*return rxInternet.notConnected()
            .andThen(localLoader.loadAll())
            .flatMap { photos -> Single.just(photos)}*/
    }

}
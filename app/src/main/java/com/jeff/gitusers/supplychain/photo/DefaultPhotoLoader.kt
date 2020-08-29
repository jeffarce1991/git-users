package com.jeff.gitusers.supplychain.photo

import com.jeff.gitusers.database.local.Photo
import com.jeff.gitusers.database.usecase.local.loader.PhotoLocalLoader
import com.jeff.gitusers.database.usecase.local.saver.PhotoLocalSaver
import com.jeff.gitusers.main.mapper.PhotoDtoToPhotoMapper
import com.jeff.gitusers.webservices.internet.RxInternet
import com.jeff.gitusers.webservices.usecase.loader.PhotoRemoteLoader
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class DefaultPhotoLoader @Inject
constructor(private val remoteLoader: PhotoRemoteLoader,
            private val localLoader: PhotoLocalLoader,
            private val localSaver: PhotoLocalSaver,
            private val rxInternet: RxInternet): PhotoLoader{

    override fun loadAll(): Single<List<Photo>> {
        return rxInternet.isConnected()
            .andThen(remoteLoader.loadAll())
            .flatMapObservable { list -> Observable.fromIterable(list) }
            .flatMap(PhotoDtoToPhotoMapper())
            .toList()
            .flatMap { photos -> Single.fromObservable(localSaver.saveAll(photos)) }
            .flatMap { photos -> Single.just(photos) }

        //return session.activeAdministeredAccount()
        //.map { administeredAccount -> administeredAccount.tenantId }
        //.flatMap { tenantId -> remoteLoader.loadAvailableExpenses(tenantId) }
    }

    override fun loadAllFromLocal(): Single<List<Photo>> {
        return rxInternet.notConnected()
            .andThen(localLoader.loadAll())
            /*.flatMap {
                when {
                    it.isEmpty() -> Single.error(
                        Throwable(EmptyResultException()))
                    else -> Single.just(it)
                }
            }*/
            .flatMap { photos -> Single.just(photos)}
    }

}
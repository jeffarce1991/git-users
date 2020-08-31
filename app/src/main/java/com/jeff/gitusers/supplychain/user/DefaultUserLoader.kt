package com.jeff.gitusers.supplychain.user

import com.jeff.gitusers.database.local.User
import com.jeff.gitusers.database.local.UserDetails
import com.jeff.gitusers.database.usecase.local.loader.UserLocalLoader
import com.jeff.gitusers.database.usecase.local.saver.UserLocalSaver
import com.jeff.gitusers.main.mapper.UserDtoToUserMapper
import com.jeff.gitusers.webservices.dto.UserDetailsDto
import com.jeff.gitusers.webservices.internet.RxInternet
import com.jeff.gitusers.webservices.usecase.loader.UserRemoteLoader
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class DefaultUserLoader @Inject
constructor(private val remoteLoader: UserRemoteLoader,
            private val localLoader: UserLocalLoader,
            private val localSaver: UserLocalSaver,
            private val rxInternet: RxInternet): UserLoader{

    override fun loadUsers(): Single<List<User>> {
        return loadInitialUsersRemotely()
            .onErrorResumeNext { loadUsersLocally() }
    }

    override fun loadMoreUsers(id: Int): Single<List<User>> {
        return rxInternet.isConnected()
            .andThen(remoteLoader.loadMoreUsers(id))
            .flatMapObservable { list -> Observable.fromIterable(list) }
            .flatMap(UserDtoToUserMapper())
            .toList()
            .flatMap { photos -> Single.fromObservable(localSaver.saveAll(photos)) }
            .flatMap { photos -> Single.just(photos) }
    }

    override fun loadUsersLocally(): Single<List<User>> {
        return localLoader.loadAll()
            .flatMap { users -> Single.just(users)}
    }

    override fun loadInitialUsersRemotely(): Single<List<User>> {
        return remoteLoader.loadInitialUsers()
            .flatMapObservable { list -> Observable.fromIterable(list) }
            .flatMap(UserDtoToUserMapper())
            .toList()
            .flatMap { photos -> Single.fromObservable(localSaver.saveAll(photos)) }
            .flatMap { photos -> Single.just(photos) }
    }

    override fun loadUserDetailsRemotely(login: String): Single<UserDetails> {
        return remoteLoader.loadUserDetails(login)
            .map { mapUserDetailsDtoToUserDetails(it) }
            //.flatMap { photos -> Single.fromObservable(localSaver.saveAll(photos)) }
            .flatMap { Single.just(it) }
    }

    private fun mapUserDetailsDtoToUserDetails(dto: UserDetailsDto): UserDetails {
        return UserDetails(
            dto.id,
            dto.login,
            dto.name,
            dto.company,
            dto.blog,
            dto.location,
            dto.email,
            dto.bio,
            dto.twitterUsername,
            dto.publicRepos,
            dto.publicGists,
            dto.followers,
            dto.following,
            dto.createdAt,
            dto.updatedAt
        )
    }

}
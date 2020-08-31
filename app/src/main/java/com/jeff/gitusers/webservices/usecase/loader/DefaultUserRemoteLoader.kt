package com.jeff.gitusers.webservices.usecase.loader

import com.jeff.gitusers.webservices.api.ApiFactory
import com.jeff.gitusers.webservices.api.git.UsersApi
import com.jeff.gitusers.webservices.dto.UserDetailsDto
import com.jeff.gitusers.webservices.dto.UserDto
import com.jeff.gitusers.webservices.transformer.ResponseCodeNot200SingleTransformer
import io.reactivex.Single
import javax.inject.Inject

class DefaultUserRemoteLoader @Inject
constructor(private val apiFactory: ApiFactory): UserRemoteLoader {

    override fun loadInitialUsers(): Single<List<UserDto>> {
        return apiFactory.create(UsersApi::class.java)
            .flatMap { it.loadInitialUsers() }
            .compose(ResponseCodeNot200SingleTransformer())
            .flatMap { response ->
                Single.just(response.body()!!) }
    }

    override fun loadMoreUsers(id: Int): Single<List<UserDto>> {
        return apiFactory.create(UsersApi::class.java)
            .flatMap { it.loadMoreUsers(id) }
            .compose(ResponseCodeNot200SingleTransformer())
            .flatMap { response ->
                Single.just(response.body()!!) }
    }

    override fun loadUserDetails(login: String): Single<UserDetailsDto> {
        return apiFactory.create(UsersApi::class.java)
            .flatMap { it.loadUserDetails(login) }
            .compose(ResponseCodeNot200SingleTransformer())
            .flatMap { response ->
                Single.just(response.body()!!) }
    }
}
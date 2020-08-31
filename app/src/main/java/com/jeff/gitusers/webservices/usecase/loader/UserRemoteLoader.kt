package com.jeff.gitusers.webservices.usecase.loader

import com.jeff.gitusers.webservices.dto.PhotoDto
import com.jeff.gitusers.webservices.dto.UserDetailsDto
import com.jeff.gitusers.webservices.dto.UserDto
import io.reactivex.Single

interface UserRemoteLoader {

    fun loadInitialUsers(): Single<List<UserDto>>
    fun loadMoreUsers(id: Int): Single<List<UserDto>>

    fun loadUserDetails(login: String): Single<UserDetailsDto>
}
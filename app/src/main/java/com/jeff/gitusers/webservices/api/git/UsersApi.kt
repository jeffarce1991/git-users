package com.jeff.gitusers.webservices.api.git

import com.jeff.gitusers.webservices.dto.UserDto
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UsersApi {

    @GET("users?since=0")
    fun loadInitialUsers(): Single<Response<List<UserDto>>>

    @GET("users")
    fun loadMoreUsers(@Query(value = "since") id: Int?): Single<Response<List<UserDto>>>


    @GET("users/{login}")
    fun loadUserDetails(@Path("login") id: String): Single<Response<UserDto>>
}
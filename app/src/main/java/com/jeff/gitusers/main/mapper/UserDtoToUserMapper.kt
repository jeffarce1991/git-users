package com.jeff.gitusers.main.mapper

import com.jeff.gitusers.database.local.Photo
import com.jeff.gitusers.database.local.User
import com.jeff.gitusers.webservices.dto.PhotoDto
import com.jeff.gitusers.webservices.dto.UserDto
import io.reactivex.Observable
import io.reactivex.functions.Function

class UserDtoToUserMapper : Function<UserDto, Observable<User>> {

    @Throws(Exception::class)
    override fun apply(userDto: UserDto): Observable<User> {
        return Observable.fromCallable {
            val user = User(
                userDto.id,
                userDto.login,
                userDto.avatarUrl,
                userDto.url
            )
            user
        }
    }
}
package com.jeff.gitusers.database.usecase.local

import com.jeff.gitusers.database.usecase.local.loader.DefaultUserDetailsLocalLoader
import com.jeff.gitusers.database.usecase.local.loader.DefaultUserLocalLoader
import com.jeff.gitusers.database.usecase.local.loader.UserDetailsLocalLoader
import com.jeff.gitusers.database.usecase.local.loader.UserLocalLoader
import com.jeff.gitusers.database.usecase.local.saver.DefaultUserDetailsLocalSaver
import com.jeff.gitusers.database.usecase.local.saver.DefaultUserLocalSaver
import com.jeff.gitusers.database.usecase.local.saver.UserDetailsLocalSaver
import com.jeff.gitusers.database.usecase.local.saver.UserLocalSaver
import dagger.Binds
import dagger.Module

@Module
interface LocalUseCaseModule {
    @Binds
    fun bindUserLocalLoader(implementation: DefaultUserLocalLoader): UserLocalLoader

    @Binds
    fun bindUserLocalSaver(implementation: DefaultUserLocalSaver): UserLocalSaver

    @Binds
    fun bindUserDetailsLocalLoader(implementation: DefaultUserDetailsLocalLoader): UserDetailsLocalLoader

    @Binds
    fun bindUserDetailsLocalSaver(implementation: DefaultUserDetailsLocalSaver): UserDetailsLocalSaver
}
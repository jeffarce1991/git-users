package com.jeff.gitusers.database.usecase.local

import com.jeff.gitusers.database.usecase.local.loader.*
import com.jeff.gitusers.database.usecase.local.saver.*
import dagger.Binds
import dagger.Module

@Module
interface LocalUseCaseModule {
    @Binds
    fun bindNotesLocalSaver(implementation: DefaultNotesLocalSaver): NotesLocalSaver

    @Binds
    fun bindNotesLocalLoader(implementation: DefaultNotesLocalLoader): NotesLocalLoader


    @Binds
    fun bindUserLocalLoader(implementation: DefaultUserLocalLoader): UserLocalLoader

    @Binds
    fun bindUserLocalSaver(implementation: DefaultUserLocalSaver): UserLocalSaver

    @Binds
    fun bindUserDetailsLocalLoader(implementation: DefaultUserDetailsLocalLoader): UserDetailsLocalLoader

    @Binds
    fun bindUserDetailsLocalSaver(implementation: DefaultUserDetailsLocalSaver): UserDetailsLocalSaver
}
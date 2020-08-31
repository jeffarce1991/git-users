package com.jeff.gitusers.supplychain.user

import dagger.Binds
import dagger.Module

@Module
abstract class UserUseCaseModule {

    @Binds
    abstract fun bindUserLoader(defaultUserLoader: DefaultUserLoader): UserLoader

    @Binds
    abstract fun bindUserSaver(defaultUserSaver: DefaultUserSaver): UserSaver


}
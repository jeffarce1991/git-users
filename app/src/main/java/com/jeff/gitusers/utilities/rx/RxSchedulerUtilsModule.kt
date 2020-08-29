package com.jeff.gitusers.utilities.rx

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Note: [object] keyword is a class that is singleton/single-instance
 */
@Module
interface RxSchedulerUtilsModule {

    @Singleton
    @Binds
    fun bindRxSchedulerUtils(defaultRxSchedulerUtils: DefaultRxSchedulerUtils):
            RxSchedulerUtils
}
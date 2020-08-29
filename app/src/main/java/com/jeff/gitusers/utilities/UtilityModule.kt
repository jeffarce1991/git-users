package com.jeff.gitusers.utilities

import com.jeff.gitusers.utilities.rx.RxSchedulerUtilsModule
import dagger.Module


@Module(includes = [RxSchedulerUtilsModule::class])
abstract class UtilityModule {

}
package com.jeff.template.utilities

import com.jeff.template.utilities.rx.RxSchedulerUtilsModule
import dagger.Module


@Module(includes = [RxSchedulerUtilsModule::class])
abstract class UtilityModule {

}
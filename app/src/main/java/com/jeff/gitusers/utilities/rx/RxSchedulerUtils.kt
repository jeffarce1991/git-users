package com.jeff.gitusers.utilities.rx

import io.reactivex.CompletableTransformer
import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer


/**
 * Transformer for any RxJava stream so it subscribes to a separate thread and is
 * observed on the android UI thread.
 *
 * @author Julious Igmen
 */
interface RxSchedulerUtils {

    fun forCompletable(): CompletableTransformer

    fun <T> forFlowable(): FlowableTransformer<T, T>

    fun <T> forObservable(): ObservableTransformer<T, T>

    fun <T> forSingle(): SingleTransformer<T, T>

    fun <T> forObservableWithDelayError(): ObservableTransformer<T, T>
}

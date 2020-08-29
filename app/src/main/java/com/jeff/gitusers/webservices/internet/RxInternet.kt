package com.jeff.template.webservices.internet

import io.reactivex.Completable
import io.reactivex.annotations.SchedulerSupport

interface RxInternet {

    /**
     * A stream that completes if the device is currently connected to the internet, otherwise
     * emits an error. It is the subscriber's responsibility to add a delay, timeout, or to repeat
     * the stream as it only executes once.
     *
     * This always uses [SchedulerSupport.IO] to make sure the network call doesn't fail on the
     * device due to main-thread-network-call exceptions.
     */
    @SchedulerSupport(SchedulerSupport.IO)
    fun connected(): Completable

    /**
     * The complete opposite of [connected], except it waits until the device is not connected to
     * the internet, emitting a complete signal. The delay between checks is defined through
     * [delayBetweenChecksInSeconds], with a default value of 5 seconds.
     *
     * This always uses [SchedulerSupport.IO] to make sure the network call doesn't fail on the
     * device due to main-thread-network-call exceptions.
     */
    @SchedulerSupport(SchedulerSupport.IO)
    fun notConnected(delayBetweenChecksInSeconds: Long = 5): Completable


    @SchedulerSupport(SchedulerSupport.IO)
    fun isConnected(): Completable

}

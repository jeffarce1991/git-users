package com.jeff.gitusers.webservices.observer

interface BaseSessionAwareObserver {

    fun onSessionExpiredError()

    fun onCommonError(e: Throwable)
}

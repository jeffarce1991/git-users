package com.jeff.template.webservices.observer

interface BaseSessionAwareObserver {

    fun onSessionExpiredError()

    fun onCommonError(e: Throwable)
}

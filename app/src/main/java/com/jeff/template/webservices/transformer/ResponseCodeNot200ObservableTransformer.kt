package com.jeff.template.webservices.transformer

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import retrofit2.HttpException
import retrofit2.Response

class ResponseCodeNot200ObservableTransformer<T> : ObservableTransformer<Response<T>, Response<T>> {
    override fun apply(upstream: Observable<Response<T>>): ObservableSource<Response<T>> {
        return upstream.flatMap {response ->
            val notSuccess = response.code() != 200 &&
                    response.code() != 201 &&
                    response.code() != 202

            if (notSuccess) {
                return@flatMap Observable.error<Response<T>>(HttpException(response))
            }

            return@flatMap Observable.just(response)
        }
    }
}
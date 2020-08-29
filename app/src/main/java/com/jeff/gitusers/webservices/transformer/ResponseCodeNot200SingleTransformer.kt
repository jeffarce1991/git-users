package com.jeff.gitusers.webservices.transformer

import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer
import retrofit2.HttpException
import retrofit2.Response

/**
 * Makes a [io.reactivex.Single] that emits a [retrofit2.Response.code] that is not
 * [java.net.HttpURLConnection.HTTP_OK] to instead throw an [HttpException].
 */
class ResponseCodeNot200SingleTransformer<T> : SingleTransformer<Response<T>, Response<T>> {
    override fun apply(upstream: Single<Response<T>>): SingleSource<Response<T>> {
        return upstream.flatMap { response: Response<T> ->
            val notSuccess = response.code() != 200 &&
                    response.code() != 201 &&
                    response.code() != 202

            if (notSuccess) {
                return@flatMap Single.error<Response<T>>(HttpException(response))
            }

            return@flatMap Single.just(response)
        }
    }
}
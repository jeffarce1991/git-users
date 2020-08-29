package com.jeff.template.webservices.transformer

import com.jeff.template.webservices.exception.SessionExpiredException
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer
import retrofit2.Response
import java.net.HttpURLConnection

/**
 * Makes a [Single] that throws an error due to the [Response.code] being
 * [HttpURLConnection.HTTP_UNAUTHORIZED] to instead throw a [SessionExpiredException].
 */
class SessionExpiredSingleTransformer<T> : SingleTransformer<Response<T>, Response<T>> {
    override fun apply(upstream: Single<Response<T>>): SingleSource<Response<T>> {
        return upstream.flatMap { response: Response<T> ->
            if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                return@flatMap Single.error<Response<T>>(SessionExpiredException())
            }
            return@flatMap Single.just(response)
        }
    }
}
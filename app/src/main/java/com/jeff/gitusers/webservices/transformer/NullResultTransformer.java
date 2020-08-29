package com.jeff.gitusers.webservices.transformer;


import com.jeff.gitusers.utilities.exception.NullResultException;

import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import retrofit2.Response;

/**
 * Makes a {@link Single} that errors due to a null emit an
 * {@link NullResultException} instead.
 */
public class NullResultTransformer<T>
    implements SingleTransformer<Response<T>, Response<T>> {

    @Override
    public Single<Response<T>> apply(Single<Response<T>> upstream) {
        return upstream.flatMap(
            response -> {
                if (null == response.body()) return Single.error(new NullResultException());
                return Single.just(response);
            }
        );
    }
}

package com.jeff.gitusers.webservices.api;

import com.jeff.gitusers.BuildConfig;
import com.jeff.gitusers.Constants;

import javax.inject.Inject;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

class DefaultApiFactory implements ApiFactory {

    @Inject
    DefaultApiFactory() {}

    @Override
    public <T> Single<T> create(Class<T> apiClass) {
        return retrofit()
                   .map(retrofit -> retrofit.create(apiClass));
    }

    private Single<OkHttpClient.Builder> addLoggingInterceptor(OkHttpClient.Builder builder) {
        return Single.fromCallable(
            () -> {
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                    builder.addInterceptor(loggingInterceptor);
                }

                return builder;
            });
    }

    private Single<OkHttpClient> client() {
        return Single.fromCallable(OkHttpClient.Builder::new)
                     .flatMap(this::addLoggingInterceptor)
                     .map(OkHttpClient.Builder::build);
    }

    private Single<Retrofit> intoRetrofit(OkHttpClient client) {
        return Single.fromCallable(
            () -> new Retrofit.Builder()
                      .baseUrl(Constants.Gateways.JSONPLACEHOLDER)
                      .client(client)
                      .addConverterFactory(GsonConverterFactory.create())
                      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                      .build()
        );
    }

    private Single<Retrofit> retrofit() {
        return client().flatMap(this::intoRetrofit);
    }

}

package com.jeff.gitusers.webservices.api;

import io.reactivex.Single;

/**
 * A reactive API for different REST API classes. Currently, there are multiple types of
 * REST API implementations that we want:
 *
 * <li>- An implementation with a Cookie header for Form Authentication</li>
 * <li>- An implementation with an Authorization header for API keys authentication</li>
 * <li>- An implementation without any authentication headers</li>
 */
public interface ApiFactory {

    /**
     * Create an implementation of an API class.
     */
    <T> Single<T> create(Class<T> apiClass);
}

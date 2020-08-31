package com.jeff.gitusers.main.detail.presenter

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.jeff.gitusers.database.local.User
import com.jeff.gitusers.database.local.UserDetails
import com.jeff.gitusers.main.detail.view.UserDetailsView
import com.jeff.gitusers.supplychain.user.UserLoader
import com.jeff.gitusers.utilities.rx.RxSchedulerUtils
import com.jeff.gitusers.webservices.exception.NoInternetException
import com.jeff.gitusers.webservices.internet.RxInternet
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DefaultUserDetailsPresenter @Inject
constructor(
    private val rxInternet: RxInternet,
    private val rxScheduler: RxSchedulerUtils,
    private val loader: UserLoader
) : MvpBasePresenter<UserDetailsView>(),
    UserDetailsPresenter {

    lateinit var view: UserDetailsView

    lateinit var disposable: Disposable

    override fun loadUserDetails(login: String, id: Int) {
        rxInternet.isConnected()
            .andThen(loader.loadUserDetailsRemotely(login))
            .compose(rxScheduler.forSingle())
            .subscribe(object : SingleObserver<UserDetails>{
                override fun onSuccess(t: UserDetails) {
                    Timber.d("==q onSuccess loadUserDetails $t")

                    view.setUserDetails(t)
                    view.stopShimmerAnimations()
                    view.hideShimmerPlaceholders()
                    dispose()
                }

                override fun onSubscribe(d: Disposable) {
                    view.startShimmerAnimations()
                    disposable = d
                }

                override fun onError(e: Throwable) {
                    Timber.e("==q onError $e")

                    if (e is NoInternetException) {
                        loadUserDetailsLocally(id)
                    } else {
                        view.stopShimmerAnimations()
                        dispose()
                    }
                }
            })
    }

    override fun loadUserDetailsLocally(id: Int) {
        loader.loadUserDetailsLocally(id)
            .delay(1 , TimeUnit.SECONDS)
            .compose(rxScheduler.forSingle())
            .subscribe(object : SingleObserver<UserDetails>{
                override fun onSubscribe(d: Disposable) {
                    Timber.d("==q loadUserDetailsLocally onSubscribe")
                    disposable = d
                    view.startShimmerAnimations()
                }

                override fun onSuccess(t: UserDetails) {
                    Timber.d("==q loadUserDetailsLocally onSuccess $t")
                        view.setUserDetails(t)
                        view.showMessage("Cached details loaded.")

                        view.stopShimmerAnimations()
                        view.hideShimmerPlaceholders()
                    dispose()
                }

                override fun onError(e: Throwable) {
                    Timber.d("==q loadUserDetailsLocally Failed $e")

                    if (e is NullPointerException) {
                        view.showMessage("No existing cached details.")
                        view.stopShimmerAnimations()
                    } else {
                        view.showMessage(e.message!!)
                    }
                    dispose()

                }
            })
    }

    override fun attachView(view: UserDetailsView) {
        super.attachView(view)
        this.view = view
    }

    private fun dispose() {
        if (!disposable.isDisposed) disposable.dispose()
    }

    override fun detachView(retainInstance: Boolean) {
        dispose()
    }
}
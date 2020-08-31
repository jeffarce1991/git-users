package com.jeff.gitusers.main.detail.presenter

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.jeff.gitusers.database.local.UserDetails
import com.jeff.gitusers.main.detail.view.UserDetailsView
import com.jeff.gitusers.supplychain.user.UserLoader
import com.jeff.gitusers.utilities.rx.RxSchedulerUtils
import com.jeff.gitusers.webservices.internet.RxInternet
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import timber.log.Timber
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

    override fun loadUserDetails(login: String) {
        rxInternet.isConnected()
            .andThen(loader.loadUserDetailsRemotely(login))
            .compose(rxScheduler.forSingle())
            .subscribe(object : SingleObserver<UserDetails>{
                override fun onSuccess(t: UserDetails) {
                    Timber.d("==q onSuccess loadUserDetails $t")

                    view.setUserDetails(t)

                    dispose()
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onError(e: Throwable) {
                    Timber.e("==q onError $e")
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
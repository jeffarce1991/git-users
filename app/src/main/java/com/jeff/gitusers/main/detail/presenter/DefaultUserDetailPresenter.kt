package com.jeff.gitusers.main.detail.presenter

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.jeff.gitusers.database.local.User
import com.jeff.gitusers.main.detail.view.UserDetailView
import com.jeff.gitusers.main.list.view.MainView
import com.jeff.gitusers.supplychain.user.UserLoader
import com.jeff.gitusers.utilities.rx.RxSchedulerUtils
import com.jeff.gitusers.webservices.internet.RxInternet
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class DefaultUserDetailPresenter @Inject
constructor(
    private val rxInternet: RxInternet,
    private val schedulerUtils: RxSchedulerUtils
    //private val loader: UserDetailLoader
) : MvpBasePresenter<UserDetailView>(),
    UserDetailPresenter {

    lateinit var view: UserDetailView

    lateinit var disposable: Disposable

    override fun attachView(view: UserDetailView) {
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
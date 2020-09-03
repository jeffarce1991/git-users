package com.jeff.gitusers.main.list.presenter

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.jeff.gitusers.database.local.User
import com.jeff.gitusers.main.list.presenter.MainPresenter.Companion.REQUEST_LOAD_INITIAL_USERS
import com.jeff.gitusers.main.list.presenter.MainPresenter.Companion.REQUEST_LOAD_MORE_USERS
import com.jeff.gitusers.main.list.presenter.MainPresenter.Companion.REQUEST_LOAD_USERS_LOCALLY
import com.jeff.gitusers.webservices.exception.NoInternetException
import com.jeff.gitusers.webservices.internet.RxInternet
import com.jeff.gitusers.main.list.view.MainView
import com.jeff.gitusers.supplychain.user.UserLoader
import com.jeff.gitusers.utilities.rx.RxSchedulerUtils
import io.reactivex.*
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DefaultMainPresenter @Inject
constructor(
    private val rxInternet: RxInternet,
    private val rxSchedulerUtils: RxSchedulerUtils,
    private val loader: UserLoader
) : MvpBasePresenter<MainView>(),
    MainPresenter {


    lateinit var view: MainView

    lateinit var users: MutableList<User>

    private var disposable: Disposable? = null
    private var queueStreamDisposable: Disposable? = null
    private var reConnectStreamDisposable: Disposable? = null
    private var reconnectDisposable: Disposable? = null

    private var queuedRequestList = mutableListOf<Int>()
    private var argList = ArrayList<Int>()

    /**
     * Starts a stream when offline and emits [whenOnlineLoadDataRemotely] every 3 seconds.
     *
     */
    override fun startReconnectStream() {
        rxInternet.notConnected()
                .andThen(Observable.timer(3, TimeUnit.SECONDS))
                .compose(rxSchedulerUtils.forObservable())
                .doOnNext{ whenOnlineLoadDataRemotely() }
                .doOnSubscribe { reConnectStreamDisposable = it }
                .repeat()
                .subscribe()
    }

    /**
     * @property whenOnlineLoadDataRemotely
     *
     * Retry loading remote data immediately once online.
     *
     */
    override fun whenOnlineLoadDataRemotely() {
        rxInternet.isConnected()
            .compose(rxSchedulerUtils.forCompletable())
            .subscribe(object : CompletableObserver{
                override fun onComplete() {
                    queue(REQUEST_LOAD_INITIAL_USERS)
                    Timber.d("==x Online, reloading data.")
                    view.showMessage("Automatically reloading data from remote.")
                    dispose(reconnectDisposable)
                    dispose(reConnectStreamDisposable)
                }

                override fun onSubscribe(d: Disposable) {
                    reconnectDisposable = d
                }

                override fun onError(e: Throwable) {
                    Timber.d("==x Offline")
                    Timber.e(e)
                    dispose(reconnectDisposable)
                }
            })
    }


    /**
     * @property startQueueStream
     *
     * Starts a stream that emits [checkQueuedList] every seconds.
     *
     */
     override fun startQueueStream() {
        Observable.timer(1, TimeUnit.SECONDS)
            .compose(rxSchedulerUtils.forObservable())
            .doOnNext{ checkQueuedList() }
            .doOnSubscribe { queueStreamDisposable = it }
            .repeat()
            .subscribe()
    }

    /**
     * Invokes [validateRequest] until queuedList is empty
     * and makes sure that disposable [isDisposed].
     *
     */
    private fun checkQueuedList() {
        if(queuedRequestList.isNotEmpty()) {
            isDisposed(disposable) { validateRequest() }
        }
    }
    /**
     * Invokes index 0 of [queuedRequestList]
     * If request has argument get it from [argList]
     *
     */
    private fun validateRequest() {
        if (argList.isEmpty()) {
            invoke(queuedRequestList[0])
        } else {
            invoke(queuedRequestList[0], argList[0])
            argList.removeAt(0)
        }
        queuedRequestList.removeAt(0)
    }

    /**
     * Invokes request base on given [request]*
     */
    private fun invoke(request: Int) {
        when (request) {
            REQUEST_LOAD_INITIAL_USERS -> {
                loadInitialUsers()
            }
            REQUEST_LOAD_USERS_LOCALLY -> {
                loadUsersLocally()
            }
        }
    }

    /**
     * Invokes request with arg base on given [request]*
     */
    private fun invoke(method: Int, params: Int) {
        when (method) {
            REQUEST_LOAD_MORE_USERS -> {
                loadMoreUsers(params)
            }
        }
    }

    /**
     * Add requested method to [queuedRequestList] and queue it up.
     *
     */
    override fun queue(request: Int) {
        queuedRequestList.add(request)
    }


    /**
     * Add requested method to [queuedRequestList] and queue it up.
     * with argument
     */
    override fun queue(request: Int, arg: Int) {
        queuedRequestList.add(request)
        argList.add(arg)
    }

    /**
     * @property isDisposed
     * This function makes sure network calls is limited to 1 request at a time.
     *
     * Check if [d] is already disposed
     * and then invokes [onDisposed]
     * else must wait for [d] to be disposed first.
     *
     */
    private fun isDisposed(d: Disposable?, onDisposed: () -> Unit) {
        d?.let {
            when {
                it.isDisposed -> {
                    Timber.d("==x Invoking func now.")
                    onDisposed()
                }
                else -> {
                    Timber.d("==x Call dispose() first.")
                }
            }
        }.run {
            if (d == null) {
                onDisposed()
                Timber.d("==x disposable is null.")
            }
        }
    }

    override fun loadInitialUsers() {
            rxInternet.isConnected()
            .delay(2,TimeUnit.SECONDS)
            .andThen(loader.loadInitialUsersRemotely())
            .compose(rxSchedulerUtils.forSingle())
            .subscribe(object : SingleObserver<List<User>>{
                override fun onSuccess(t: List<User>) {
                    Timber.d("==q onSuccess" )
                    view.hideProgress()
                    if (t.isNotEmpty()) {
                        users = t as MutableList<User>
                        view.setSearchQueryListener(users)
                        view.generateInitialUsers(t)
                        view.showMessage("${t.size} Users loaded remotely.")
                    }
                    dispose()
                }

                override fun onSubscribe(d: Disposable) {
                    Timber.d("==q onSubscribed" )
                    view.showProgress()
                    disposable = d
                }

                override fun onError(e: Throwable) {
                    Timber.d("==q onError $e" )
                    e.printStackTrace()

                    view.hideProgress()
                    view.showMessage(e.message!!)

                    if (e is NoInternetException) {
                        view.showMessage(e.message!! + ", Cached data will be loaded.")
                        loadUsersLocally()
                    } else {
                        dispose()
                    }
                }
            })

    }

    override fun loadMoreUsers(fromId: Int) {
            loader.loadMoreUsers(fromId)
                .compose(rxSchedulerUtils.forSingle())
                .subscribe(object : SingleObserver<List<User>> {
                    override fun onSuccess(t: List<User>) {
                        Timber.d("==q loadMoreUsers onSuccess $t")
                        view.hideProgress()
                        if (t.isNotEmpty()) {
                            users.addAll(t as MutableList<User>)
                            view.setSearchQueryListener(users)
                            view.generateMoreUsers(t)
                            view.showMessage("${t.size} more Users loaded remotely.")
                        }
                        dispose()
                    }

                    override fun onSubscribe(d: Disposable) {
                        Timber.d("==q onSubscribe")
                        view.showProgress()
                        disposable = d
                    }

                    override fun onError(e: Throwable) {
                        Timber.d("==q onError $e")
                        e.printStackTrace()

                        view.hideProgress()
                        view.showMessage(e.message!!)

                        if (e is NoInternetException) {
                            //getPhotosFromLocal()
                        } else {
                            //dispose()
                        }
                        dispose()
                    }
                })
    }

    override fun loadUsersLocally() {
        loader.loadUsersLocally()
            .delay(2000 ,TimeUnit.MILLISECONDS)
            .compose(rxSchedulerUtils.forSingle())
            .subscribe(object : SingleObserver<List<User>>{
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                    view.showProgress()
                }

                override fun onSuccess(t: List<User>) {
                    Timber.d("==q loadAll onSuccess ${t.size}")

                    view.hideProgress()

                    if (t.isNotEmpty()) {
                        view.setSearchQueryListener(t)
                        view.generateInitialUsers(t)
                        view.showMessage("${t.size} cached data loaded.")
                    } else {
                        view.showMessage("No existing cached data.")
                    }
                    dispose()
                }

                override fun onError(e: Throwable) {
                    Timber.d("==q Load Photos Failed $e")

                    view.hideProgress()
                    view.showMessage(e.message!!)
                    dispose()

                }
            })
    }

    override fun attachView(view: MainView) {
        super.attachView(view)
        this.view = view
    }

    fun dispose(d: Disposable?) {
        if (!d!!.isDisposed) d.dispose()
    }

    override fun dispose() {
        if (!disposable!!.isDisposed) disposable!!.dispose()
    }

    override fun disposeAllStreams() {
        if (!queueStreamDisposable!!.isDisposed) queueStreamDisposable!!.dispose()
        if (!reConnectStreamDisposable!!.isDisposed) reConnectStreamDisposable!!.dispose()
    }

    override fun detachView(retainInstance: Boolean) {
        dispose()
    }

}
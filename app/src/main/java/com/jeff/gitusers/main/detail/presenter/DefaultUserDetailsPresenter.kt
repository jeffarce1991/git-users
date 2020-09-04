package com.jeff.gitusers.main.detail.presenter

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.jeff.gitusers.database.local.Notes
import com.jeff.gitusers.database.local.UserDetails
import com.jeff.gitusers.database.usecase.local.loader.NotesLocalLoader
import com.jeff.gitusers.database.usecase.local.loader.UserLocalLoader
import com.jeff.gitusers.database.usecase.local.saver.NotesLocalSaver
import com.jeff.gitusers.database.usecase.local.saver.UserDetailsLocalSaver
import com.jeff.gitusers.database.usecase.local.saver.UserLocalSaver
import com.jeff.gitusers.main.detail.view.UserDetailsView
import com.jeff.gitusers.supplychain.user.UserLoader
import com.jeff.gitusers.supplychain.user.UserSaver
import com.jeff.gitusers.utilities.rx.RxSchedulerUtils
import com.jeff.gitusers.webservices.exception.NoInternetException
import com.jeff.gitusers.webservices.internet.RxInternet
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DefaultUserDetailsPresenter @Inject
constructor(
    private val rxInternet: RxInternet,
    private val rxScheduler: RxSchedulerUtils,
    private val userLoader: UserLoader,
    private val userSaver: UserSaver,
    private val notesLocalSaver: NotesLocalSaver,
    private val notesLocalLoader: NotesLocalLoader
) : MvpBasePresenter<UserDetailsView>(),
    UserDetailsPresenter {

    lateinit var view: UserDetailsView

    lateinit var disposable: Disposable

    override fun loadUserDetails(login: String, id: Int) {
        rxInternet.isConnected()
            .andThen(userLoader.loadUserDetailsRemotely(login))
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
                        view.hideShimmerPlaceholders()
                        dispose()
                    }
                }
            })
    }

    override fun loadUserDetailsLocally(id: Int) {
        userLoader.loadUserDetailsLocally(id)
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
                        view.hideShimmerPlaceholders()
                    } else {
                        view.showMessage(e.message!!)
                    }
                    dispose()
                }
            })
    }

    override fun updateNotes(newNotes: String, id: Int) {
        notesLocalSaver.save(Notes(id, newNotes))
            .compose(rxScheduler.forCompletable())
            .subscribe(object : CompletableObserver{
                override fun onComplete() {
                    view.showMessage("Notes saved!")
                    dispose()
                }

                override fun onSubscribe(d: Disposable) {
                    Timber.d("==q Notes saving.")
                    disposable = d
                }

                override fun onError(e: Throwable) {
                    Timber.d("==q Notes saving failed.")
                    view.showMessage(e.message!!)
                    dispose()
                }
            })
    }

    override fun loadNotes(id: Int) {
        notesLocalLoader.loadById(id)
            .compose(rxScheduler.forSingle())
            .subscribe(object : SingleObserver<Notes>{
                override fun onSuccess(t: Notes) {
                    view.setNotes(t)
                    Timber.d("==q Notes Loaded!")
                    //view.showMessage("Notes loaded!")
                    dispose()
                }

                override fun onSubscribe(d: Disposable) {
                    Timber.d("==q Notes Loading..")
                    disposable = d
                }

                override fun onError(e: Throwable) {
                    Timber.d("==q Notes Loading failed.")
                    Timber.e(e)

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
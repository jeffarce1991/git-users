package com.jeff.gitusers.main.list.presenter

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.jeff.gitusers.database.local.Photo
import com.jeff.gitusers.database.local.User
import com.jeff.gitusers.webservices.exception.NoInternetException
import com.jeff.gitusers.webservices.internet.RxInternet
import com.jeff.gitusers.main.list.view.MainView
import com.jeff.gitusers.supplychain.user.UserLoader
import com.jeff.gitusers.webservices.dto.PhotoDto
import com.jeff.gitusers.utilities.rx.RxSchedulerUtils
import io.reactivex.*
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DefaultMainPresenter @Inject
constructor(
    private val rxInternet: RxInternet,
    private val schedulerUtils: RxSchedulerUtils,
    private val loader: UserLoader
) : MvpBasePresenter<MainView>(),
    MainPresenter {

    lateinit var view: MainView

    lateinit var disposable: Disposable

    lateinit var users: MutableList<User>

    override fun loadInitialUsers() {
        rxInternet.isConnected()
            .andThen(loader.loadInitialUsersRemotely())
            .compose(schedulerUtils.forSingle())
            .subscribe(object : SingleObserver<List<User>>{
                override fun onSuccess(t: List<User>) {
                    Timber.d("==q onSuccess $t" )
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
        .compose(schedulerUtils.forSingle())
        .subscribe(object : SingleObserver<List<User>>{
            override fun onSuccess(t: List<User>) {
                Timber.d("==q loadMoreUsers onSuccess $t" )
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
                Timber.d("==q onSubscribe" )
                view.showProgress()
                disposable = d
            }

            override fun onError(e: Throwable) {
                Timber.d("==q onError $e" )
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

    fun loadUsersLocally(){
        loader.loadUsersLocally()
            .delay(3 ,TimeUnit.SECONDS)
            .compose(schedulerUtils.forSingle())
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

    private fun mapPhotoDtosToPhotos(photoDtos: List<PhotoDto>): List<Photo> {
        val photos = mutableListOf<Photo>()
        for (photoDto in photoDtos) {
            val photo = Photo(
                photoDto.id,
                photoDto.albumId,
                photoDto.title,
                photoDto.url,
                photoDto.thumbnailUrl
            )
            photos.add(photo)
        }
        return photos
    }

    override fun attachView(view: MainView) {
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
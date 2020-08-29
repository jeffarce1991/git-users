package com.jeff.gitusers.main.presenter

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.jeff.gitusers.database.local.Photo
import com.jeff.gitusers.database.local.User
import com.jeff.gitusers.database.usecase.local.loader.PhotoLocalLoader
import com.jeff.gitusers.database.usecase.local.saver.PhotoLocalSaver
import com.jeff.gitusers.webservices.exception.NoInternetException
import com.jeff.gitusers.webservices.internet.RxInternet
import com.jeff.gitusers.main.view.MainView
import com.jeff.gitusers.supplychain.photo.UserLoader
import com.jeff.gitusers.webservices.dto.PhotoDto
import com.jeff.gitusers.utilities.rx.RxSchedulerUtils
import io.reactivex.*
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

class DefaultMainPresenter @Inject
constructor(
    private val internet: RxInternet,
    private val localLoader: PhotoLocalLoader,
    private val localSaver: PhotoLocalSaver,
    private val schedulerUtils: RxSchedulerUtils,
    private val loader: UserLoader
) : MvpBasePresenter<MainView>(),
    MainPresenter {

    lateinit var view: MainView

    lateinit var disposable: Disposable

    override fun loadInitialUsers() {
        internet.isConnected()
            .andThen(loader.loadInitialUsers())
            .compose(schedulerUtils.forSingle())
            .subscribe(object : SingleObserver<List<User>>{
                override fun onSuccess(t: List<User>) {
                    Timber.d("==q onError $t" )
                    view.hideProgress()
                    if (t.isNotEmpty()) {
                        view.generateInitialUsers(t)
                        view.showToast("Data loaded Remotely")
                    } else {
                        view.showLoadingDataFailed()
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

                    if (e is NoInternetException) {
                        getPhotosFromLocal()
                    } else {
                        dispose()
                    }
                }
            })
    }

    override fun loadMoreUsers(fromId: Int) {internet.isConnected()
        .andThen(loader.loadMoreUsers(fromId))
        .compose(schedulerUtils.forSingle())
        .subscribe(object : SingleObserver<List<User>>{
            override fun onSuccess(t: List<User>) {
                Timber.d("==q loadMoreUsers onSuccess $t" )
                view.hideProgress()
                if (t.isNotEmpty()) {
                    view.generateMoreUsers(t)
                    view.showToast("Data loaded Remotely")
                } else {
                    view.showLoadingDataFailed()
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

                if (e is NoInternetException) {
                    getPhotosFromLocal()
                } else {
                    dispose()
                }
            }
        })
    }

    override fun getPhoto(id: Int) {
            /*internet.isConnected()
                .andThen(getApi().loadPhotoById(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<Response<PhotoDto>> {

                    override fun onSubscribe(d: Disposable) {
                        view.showProgress()

                        disposable = d
                        Timber.d("==q onSubscribe")
                    }

                    override fun onSuccess(t: Response<PhotoDto>) {
                        view.hideProgress()
                        view.generateDataList(mapPhotoDtosToPhotos(listOf(t.body()!!)))

                        dispose()
                        Timber.d("==q onSuccess")
                    }
                    override fun onError(e: Throwable) {
                        view.hideProgress()

                        dispose()
                        Timber.e(e)
                        Timber.d("==q onError")
                        e.printStackTrace()
                    }
                })*/
    }


    fun getPhotosFromLocal(){
        /*loader.loadAllFromLocal()
            .compose(schedulerUtils.forSingle())
            .subscribe(object : SingleObserver<List<Photo>>{
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                    view.showProgress()
                }

                override fun onSuccess(t: List<Photo>) {
                    Timber.d("==q loadAll onSuccess ${t.size}")

                    view.hideProgress()

                    if (t.isNotEmpty()) {
                        view.showToast("Data loaded Locally")
                        view.generateDataList(t)
                    } else {
                        view.showLoadingDataFailed()
                    }
                    dispose()
                }

                override fun onError(e: Throwable) {
                    Timber.d("==q Load Photos Failed $e")

                    view.hideProgress()
                    dispose()

                }
            })*/
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
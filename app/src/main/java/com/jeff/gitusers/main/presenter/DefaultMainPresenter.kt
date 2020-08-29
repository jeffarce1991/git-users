package com.jeff.template.main.presenter

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.jeff.template.Constants
import com.jeff.template.database.local.Photo
import com.jeff.template.database.usecase.local.loader.PhotoLocalLoader
import com.jeff.template.database.usecase.local.saver.PhotoLocalSaver
import com.jeff.template.webservices.exception.NoInternetException
import com.jeff.template.webservices.internet.RxInternet
import com.jeff.template.main.view.MainView
import com.jeff.template.supplychain.photo.PhotoLoader
import com.jeff.template.webservices.dto.PhotoDto
import com.jeff.template.webservices.api.photos.PhotosApi
import com.jeff.template.webservices.api.RetrofitClientInstance
import com.jeff.template.utilities.rx.RxSchedulerUtils
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class DefaultMainPresenter @Inject
constructor(
    private val internet: RxInternet,
    private val localLoader: PhotoLocalLoader,
    private val localSaver: PhotoLocalSaver,
    private val schedulerUtils: RxSchedulerUtils,
    private val loader: PhotoLoader
) : MvpBasePresenter<MainView>(),
    MainPresenter {

    lateinit var view: MainView

    lateinit var disposable: Disposable

    private fun getApi(): PhotosApi {

        /*Create handle for the RetrofitInstance interface*/
        return RetrofitClientInstance.getRxRetrofitInstance(
            Constants.Gateways.JSONPLACEHOLDER
        )!!.create(
            PhotosApi::class.java
        )
    }

    override fun getPhotos() {
        internet.isConnected()
            .andThen(loader.loadAll())
            .compose(schedulerUtils.forSingle())
            .subscribe(object : SingleObserver<List<Photo>>{
                override fun onSuccess(t: List<Photo>) {
                    Timber.d("==q onError $t" )
                    view.hideProgress()
                    if (t.isNotEmpty()) {
                        view.generateDataList(t)
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

    override fun getPhoto(id: Int) {
            internet.isConnected()
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
                })
    }


    fun getPhotosFromLocal(){
        loader.loadAllFromLocal()
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
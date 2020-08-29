package com.jeff.gitusers.main.view

import com.hannesdorfmann.mosby.mvp.MvpView
import com.jeff.gitusers.database.local.Photo

interface MainView : MvpView {
     fun hideProgress()
     fun showProgress()
     fun showProgressRemote()
     fun showProgressLocal()

     fun showLoadingDataFailed()
     fun showToast(message: String)
     fun generateDataList(photos: List<Photo>)
}
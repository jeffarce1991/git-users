package com.jeff.template.main.view

import com.hannesdorfmann.mosby.mvp.MvpView
import com.jeff.template.database.local.Photo

interface MainView : MvpView {
     fun hideProgress()
     fun showProgress()
     fun showProgressRemote()
     fun showProgressLocal()

     fun showLoadingDataFailed()
     fun showToast(message: String)
     fun generateDataList(photos: List<Photo>)
}
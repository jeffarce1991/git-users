package com.jeff.template.main.presenter

import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.jeff.template.main.view.MainView

interface MainPresenter: MvpPresenter<MainView> {
    fun getPhoto(id: Int)
    fun getPhotos()
}
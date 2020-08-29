package com.jeff.gitusers.main.presenter

import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.jeff.gitusers.main.view.MainView

interface MainPresenter: MvpPresenter<MainView> {
    fun getPhoto(id: Int)
    fun getPhotos()
}
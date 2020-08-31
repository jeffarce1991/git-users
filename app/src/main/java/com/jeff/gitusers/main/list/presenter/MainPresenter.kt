package com.jeff.gitusers.main.list.presenter

import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.jeff.gitusers.main.list.view.MainView

interface MainPresenter: MvpPresenter<MainView> {
    fun loadInitialUsers()
    fun loadMoreUsers(fromId: Int)
}
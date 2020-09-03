package com.jeff.gitusers.main.list.presenter

import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.jeff.gitusers.main.list.view.MainView

interface MainPresenter: MvpPresenter<MainView> {
    fun loadInitialUsers()
    fun loadUsersLocally()
    fun loadMoreUsers(fromId: Int)

    fun startQueueStream()
    fun queue(request: Int, arg : Int)
    fun queue(request: Int)

    fun dispose()
    fun disposeStream()

    companion object {
        const val REQUEST_LOAD_MORE_USERS = 0
        const val REQUEST_LOAD_USERS_LOCALLY = 1
        const val REQUEST_LOAD_INITIAL_USERS = 2
    }
}
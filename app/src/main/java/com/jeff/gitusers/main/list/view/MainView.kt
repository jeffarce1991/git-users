package com.jeff.gitusers.main.list.view

import com.hannesdorfmann.mosby.mvp.MvpView
import com.jeff.gitusers.database.local.User

interface MainView : MvpView {
     fun hideProgress()
     fun showProgress()
     fun showProgressRemote()
     fun showProgressLocal()
     fun showMessage(message: String)

     fun showLoadingDataFailed()
     fun showToast(message: String)
     fun generateInitialUsers(users: List<User>)
     fun generateMoreUsers(users: List<User>)

     fun setSearchQueryListener(users: List<User>)
}
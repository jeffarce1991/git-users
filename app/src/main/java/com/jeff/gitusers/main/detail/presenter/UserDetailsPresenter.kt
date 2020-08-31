package com.jeff.gitusers.main.detail.presenter

import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.jeff.gitusers.database.local.UserDetails
import com.jeff.gitusers.main.detail.view.UserDetailsView

interface UserDetailsPresenter : MvpPresenter<UserDetailsView> {

    fun loadUserDetails(login: String, id: Int)
    fun loadUserDetailsLocally(id: Int)
    fun updateNotes(newNotes: String, id: Int)
    fun loadNotes(id: Int)
}

package com.jeff.gitusers.main.detail.view

import com.hannesdorfmann.mosby.mvp.MvpView
import com.jeff.gitusers.database.local.UserDetails

interface UserDetailsView : MvpView {
    fun setUserDetails(userDetails: UserDetails)

    fun stopShimmerAnimations()
    fun startShimmerAnimations()
    fun hideShimmerPlaceholders()

}

package com.jeff.gitusers.webservices.dto

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName

class UserDetailsDto(

    @field:SerializedName("id") var id: Int,
    @field:SerializedName("login") var login: String,

    //DETAILS
    @field:SerializedName("name") var name: String,
    @Nullable
    @field:SerializedName("company") var company: String? = null,
    @field:SerializedName("blog") var blog: String? = null,
    @field:SerializedName("location") var location: String? = null,
    @field:SerializedName("email") var email: String? = null,
    @field:SerializedName("bio") var bio: String? = null,
    @field:SerializedName("twitter_username") var twitterUsername: String? = null,
    @field:SerializedName("public_repos") var publicRepos: Int? = null,
    @field:SerializedName("public_gists") var publicGists: Int? = null,
    @field:SerializedName("followers") var followers: Int? = null,
    @field:SerializedName("following") var following: Int? = null,
    @field:SerializedName("created_at") var createdAt: String? = null,
    @field:SerializedName("updated_at") var updatedAt: String? = null
)
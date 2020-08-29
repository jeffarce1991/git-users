package com.jeff.gitusers.webservices.dto

import com.google.gson.annotations.SerializedName

open class UserDto(
    @field:SerializedName("login") var login: String,
    @field:SerializedName("id") var id: Int,
    @field:SerializedName("node_id") var nodeId: String,
    @field:SerializedName("avatar_url") var avatarUrl: String,
    @field:SerializedName("gravatar_id") var gravatarId: String,
    @field:SerializedName("url") var url: String
)
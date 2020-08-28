package com.jeff.template.webservices.dto

import com.google.gson.annotations.SerializedName

class PhotoDto(
    @field:SerializedName("albumId") var albumId: Int,
    @field:SerializedName("id") var id: Int,
    @field:SerializedName("title") var title: String,
    @field:SerializedName("url") var url: String,
    @field:SerializedName("thumbnailUrl") var thumbnailUrl: String
)
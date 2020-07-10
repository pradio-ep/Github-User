package com.pradioep.githubuser.model

import com.google.gson.annotations.SerializedName

data class ResponseError (
    @SerializedName("message")
    val message: String,
    @SerializedName("documentation_url")
    val documentation_url: String
)
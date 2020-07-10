package com.pradioep.githubuser.model

import com.google.gson.annotations.SerializedName

data class ResponseSuccess (
    @SerializedName("total_count")
    val total_count: Int,
    @SerializedName("incomplete_results")
    val incomplete_results: Boolean,
    @SerializedName("items")
    val items: ArrayList<Item>
)
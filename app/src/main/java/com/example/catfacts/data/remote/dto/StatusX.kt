package com.example.catfacts.data.remote.dto


import com.google.gson.annotations.SerializedName

data class StatusX(
    @SerializedName("sentCount")
    val sentCount: Int,
    @SerializedName("verified")
    val verified: Boolean
)
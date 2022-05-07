package com.example.catfacts.data.remote.dto


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: Name,
    @SerializedName("photo")
    val photo: String
)
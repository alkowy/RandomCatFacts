package com.example.catfacts.data.remote.dto


import com.example.catfacts.domain.model.CatFactModel
import com.google.gson.annotations.SerializedName

data class CatFactRandomDto(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("deleted")
    val deleted: Boolean,
    @SerializedName("_id")
    val id: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("status")
    val status: StatusX,
    @SerializedName("text")
    val text: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("used")
    val used: Boolean,
    @SerializedName("user")
    val user: String,
    @SerializedName("__v")
    val v: Int
)
fun CatFactRandomDto.toCatFact() : CatFactModel {
    return CatFactModel(
        id = id,
        text = text,
        updatedAt = updatedAt
    )
}
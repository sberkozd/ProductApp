package com.sberkozd.unknown.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Detail(
    @Json(name = "limit")
    val limit: Int?,
    @Json(name = "products")
    val products: List<Product?>?,
    @Json(name = "skip")
    val skip: Int?,
    @Json(name = "total")
    val total: Int?
) : Parcelable
package com.sberkozd.unknown.network

import com.sberkozd.unknown.models.Product
import com.sberkozd.unknown.responses.ProductListResponse
import retrofit2.Response
import retrofit2.http.GET

interface UnknownService {

    @GET("products")
    suspend fun getList(): Response<ProductListResponse>
}
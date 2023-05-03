package com.sberkozd.productapp.network

import com.sberkozd.productapp.responses.ProductListResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {

    @GET("products")
    suspend fun getList(): Response<ProductListResponse>
}
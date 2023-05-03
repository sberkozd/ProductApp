package com.sberkozd.productapp.ui.home

import com.sberkozd.productapp.di.NetworkRepository
import com.sberkozd.productapp.di.NetworkResult
import com.sberkozd.productapp.network.ProductService
import com.sberkozd.productapp.responses.ProductListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeRepository @Inject constructor(private val productService: ProductService) :
    NetworkRepository {

    fun getProductList(
        onError: suspend (String?) -> Unit,
    ) = flow {

        val result: NetworkResult<ProductListResponse> = wrapNetworkResult({
            productService.getList()
        })

        when (result) {
            is NetworkResult.SuccessfulNetworkResult -> {
                emit(result.body.products)
            }
            is NetworkResult.ErrorNetworkResult -> {
                onError("Check internet connection")
            }
            is NetworkResult.EmptyNetworkResult -> {
                onError("No data found")
            }
            is NetworkResult.ExceptionResult -> {
                onError(result.errorMessage)
            }
        }
    }.flowOn(Dispatchers.IO)
}
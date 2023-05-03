package com.sberkozd.unknown.ui.home

import com.sberkozd.unknown.di.NetworkRepository
import com.sberkozd.unknown.di.NetworkResult
import com.sberkozd.unknown.models.Product
import com.sberkozd.unknown.network.UnknownService
import com.sberkozd.unknown.responses.ProductListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeRepository @Inject constructor(private val unknownService: UnknownService) :
    NetworkRepository {

    fun getProductList(
        onError: suspend (String?) -> Unit,
    ) = flow {

        val result: NetworkResult<ProductListResponse> = wrapNetworkResult({
            unknownService.getList()
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
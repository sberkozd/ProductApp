package com.sberkozd.productapp.responses

import com.sberkozd.productapp.models.Product

data class ProductListResponse (
    val products: List<Product>?
)
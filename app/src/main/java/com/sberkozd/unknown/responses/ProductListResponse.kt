package com.sberkozd.unknown.responses

import com.sberkozd.unknown.models.Product

data class ProductListResponse (
    val products: List<Product>?
)
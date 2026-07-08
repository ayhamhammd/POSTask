package com.ayham.postask.data.remote.mapper

import com.ayham.postask.data.remote.dto.ProductDto
import com.ayham.postask.domain.model.ProductModel

fun ProductDto.toDomain(): ProductModel = ProductModel(
    id = id,
    name = name,
    priceCents = priceCents,
    taxable = taxable,
    stock = stock,
)

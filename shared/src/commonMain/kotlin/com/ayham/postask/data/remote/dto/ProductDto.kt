package com.ayham.postask.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: String,
    val name: String,
    val priceCents: Long,
    val taxable: Boolean,
    val stock: Int,
)

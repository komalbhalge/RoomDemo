package com.kb.choco.network

import com.kb.choco.data.db.entities.ProductEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {

    @GET("products")
    suspend fun getProducts(
        @Query("token") token: String
    ): List<ProductEntity>
}